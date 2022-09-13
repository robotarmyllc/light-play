//
//  DMX4OSX.cpp
//  
//
//  Copyright 2015 Robot Army LLC   ( http://robotarmy.com )
//
//
//  NOTE:  You must have drivers in D2XX mode!   If you can see /dev/tty-usb...
//         devices then this won't work.  See: http://www.dmxis.com/macdriverfix/
//---------------------------------------------------------------------------

#include "dmx4osx.h"

//#define DEBUG

using namespace std;

// Move this to the .h file.
void *txThread(void *);
void getSerialNumbers(vector<string>& );
int  setPortSettings(FT_HANDLE);
void printDeviceInfoList(void);

typedef struct {
    pthread_t thread;
    int    thread_id;
    FT_HANDLE  ftHandle;
    char   serialNum[16];
    int    universe;
    int     running;
    unsigned char dmxBuffer[513];
} Device;

unordered_map<int, Device>devList;
//unsigned char dmxBuffer[2][513];
uintptr_t handle = 0;   // Index of last given handle

/*
 * getList()
 *
 * Format:   LocId:Serial:Description
 */
JNIEXPORT jobjectArray JNICALL Java_com_robotarmy_dmx_DMX_getList
(JNIEnv *env, jclass _class){
    jobjectArray ret;
    DWORD num = 0;
    FT_STATUS status = FT_CreateDeviceInfoList(&num);
    if (status != FT_OK) {
        cerr << "CreateDeviceInfoList: " << status;
        ret = (jobjectArray)env->NewObjectArray(
            0, env->FindClass("java/lang/String"),
                 env->NewStringUTF(""));
        return ret;
    } else if (num <= 0) {
        //cout << "No FTDI devices found.\n";
        
        ret = (jobjectArray)env->NewObjectArray(
            0, env->FindClass("java/lang/String"),
                 env->NewStringUTF(""));
        return ret;
    }

//    // Allocate storage for list based on numDevices
//    FT_DEVICE_LIST_INFO_NODE* devInfo = new FT_DEVICE_LIST_INFO_NODE[num];
//
//    // Get the device information list
//    if (FT_GetDeviceInfoList(devInfo, &num) == FT_OK) {

        ret= (jobjectArray)env->NewObjectArray(num,
                env->FindClass("java/lang/String"),
                env->NewStringUTF(""));
        
        vector<string> list; // a vector of stings.

        getSerialNumbers(list );
        int i=0;
        for (vector<string>::iterator   n = list.begin();
                                        n != list.end();
                                        ++n) {
            //cout << "Item" << " :: " << *n;
            env->SetObjectArrayElement( ret,i,env->NewStringUTF( (char*)((*n).c_str()) ) );
            i++;
        }
//    }
    
    return(ret);
}
    

/**
 * connect()
 *
 */
JNIEXPORT jint JNICALL Java_com_robotarmy_dmx_DMX_connect
(JNIEnv *env, jclass _class, jstring _serial, jint _universe, jint _mode ){
    FT_HANDLE ftHandle;
    char *serial;
    serial = const_cast<char *>( env->GetStringUTFChars( _serial, 0) );
    
    //TODO
    // Check if serial is already used.
    
    // Connect to specified port
    FT_STATUS status = FT_OpenEx((void*)serial, FT_OPEN_BY_SERIAL_NUMBER, &ftHandle);
    if ( status == FT_OK ) {
        handle++;
        
        Device dev;
        
        // Init DMX buffer for this universe
        for ( int i=0; i<513; i++ ) {
            dev.dmxBuffer[i] = 0;
        }
             
        //dev.serialNum = serial;
        strcpy(dev.serialNum, serial);
        dev.universe = _universe;
        dev.ftHandle = ftHandle;
        
        //devList[handle] = dev;
        devList.insert({{handle,dev}});
        
        // Create the thread
        if( pthread_create(&dev.thread, NULL, txThread, (PVOID)handle) != 0 ) {
            cout << "Error:unable to create thread," << endl;
            
            // TODO: Close the port
            
            // Remove the device from devList
            dev.running = 0;
            devList.erase(handle);
            handle--;
            
            return -100;
        }
        
        cout << "Opened " << serial << " as handle # " << handle << endl;
    } else {
        env->ReleaseStringUTFChars(_serial, serial);
        cerr << "Could not open [" << serial << "] returned status " << status << "\n";
        FT_Close(ftHandle);
        return -status;  // Invert status
    }

    env->ReleaseStringUTFChars(_serial, serial);
    
    // Set up port settings.
    setPortSettings( ftHandle );
    
    // Start timer loop.

    return handle;
}


/**
 * disconnect()
 *
 */
JNIEXPORT jboolean JNICALL Java_com_robotarmy_dmx_DMX_disconnect
(JNIEnv *env, jclass _class, jint _handle){

#ifdef DEBUG
    cout << "Closing [" << devList[_handle].serialNum << "]" << endl;
#endif
    // Stop timer loop.
    //Device dev = devList[_handle];
    //char * serial = devList[_handle].serialNum;
    devList[_handle].running = 0;
    usleep(500000);
    FT_Purge(devList[_handle].ftHandle, FT_PURGE_RX|FT_PURGE_TX);
    if (FT_Close(devList[_handle].ftHandle) != FT_OK ) {
        cout << "Could not close handle index: " << _handle << endl;
        return false;
    }
#ifdef DEBUG
    cout << "Closed  [" << devList[_handle].serialNum << "]" << endl;
#endif
    devList.erase(_handle);

    return true;
}


/**
 * setValue( int channel, int value)
 *
 * Returns status as int.  0
 * 0-255
 */
JNIEXPORT jint JNICALL Java_com_robotarmy_dmx_DMX_setValue
(JNIEnv *_env, jclass _class, jint _handle, jint _channel, jint _value){
    
    if(_channel<0) return -2;
    if(_channel>511) return -2;
        
    if ( devList.count(_handle) > 0 ) {
        devList[_handle].dmxBuffer[_channel+1]=_value;
        return 0;
    }
 
    return -1;
}


/**
 * getValue( int channel )
 *
 * Returns value 0-255
 */
JNIEXPORT jint JNICALL Java_com_robotarmy_dmx_DMX_getValue
(JNIEnv *_env, jclass _class, jint _handle, jint _channel){
    
    if(_channel<0)   return -2;
    if(_channel>511) return -2;
        
    if ( devList.count(_handle) > 0 ) {
        return (jint)devList[_handle].dmxBuffer[_channel+1];
    }
    
    return -1;
}



/**
 *  TX Thread
 *
 */
void *txThread(PVOID _handle) {
    DWORD bytesWritten;
    long h = (long)_handle;

//    unordered_map<int, Device>::iterator find = devList.find(h);
//    if ( find == devList.end() ) {
//        cout << "Can't find device for handle [" << _handle << "]!";
//        return NULL;
//    }
    
    //Device d = find->second;
//    d.running  = 1;
    devList[h].running = 1;
#ifdef DEBUG
    cout << "Starting Thread for [" << devList[h].serialNum << "] on universe " << devList[h].universe << endl;
#endif
    
    //while (1) {
    while (devList[h].running) {
        //if ( d.running != 1 ) break;
#ifdef DEBUG
        cout << "." << endl;
#endif
        // Transmit the packet
        // Set comm break
        if ( FT_SetBreakOn(devList[h].ftHandle) != FT_OK ) {
            //  Abort?
        }
        // Clear Comm break
        if ( FT_SetBreakOff(devList[h].ftHandle) != FT_OK ) {
            //  Abort?
        }
        // Write buffer
        if ( FT_Write(devList[h].ftHandle, devList[h].dmxBuffer, sizeof(devList[h].dmxBuffer), &bytesWritten)  != FT_OK ){
            // Abort?
            //cout << "DMX Packet Write Fail. U: " << d.universe << "running:" << d.running << "  Handle: "<< d.ftHandle << endl;
        };
        
        // Wait  66mS
        usleep(33000);

    }
    cout << "Thread " << devList[h].serialNum << " Ended." << endl;
    
    pthread_exit(NULL);
}


///  UTILITIES

void getSerialNumbers(vector<string>& list ) {
    FT_STATUS ftStatus;
    FT_DEVICE_LIST_INFO_NODE *devInfo;
    DWORD numDevs;
    // create the device information list
    ftStatus = FT_CreateDeviceInfoList(&numDevs);
    if (ftStatus == FT_OK) {
        printf("Number of devices is %u\n",numDevs);
    }
    if (numDevs > 0) {
        // allocate storage for list based on numDevs
        devInfo =(FT_DEVICE_LIST_INFO_NODE*)malloc(sizeof(FT_DEVICE_LIST_INFO_NODE)*numDevs);
        // get the device information list
        ftStatus = FT_GetDeviceInfoList(devInfo,&numDevs);
        if (ftStatus == FT_OK) {
            for (int i = 0; i < numDevs; i++) {
                stringstream ss;
                ss << devInfo[i].LocId << ":" << devInfo[i].SerialNumber << ":" << devInfo[i].Description;
                list.push_back(ss.str());
            }
        }
    }
}

int setPortSettings(FT_HANDLE ftHandle) {
    // Baud for DMX is 250000
    DWORD BaudRate=250000L;    
    // Data Characteristics are 8-bit, 2-stop, no parity
    // Flow control  NONE
    
    if (FT_SetBaudRate(ftHandle, BaudRate) != FT_OK) {
        cerr << "Set Baud Rate Failed.\n";
        return -1;
    }
    if (FT_SetDataCharacteristics(ftHandle,
                FT_BITS_8, FT_STOP_BITS_2, FT_PARITY_NONE) != FT_OK ) {
        cerr << "Set Bits/STOP/Parity Failed.\n";
        return -1;
    }
    if (FT_SetFlowControl(ftHandle, FT_FLOW_NONE, 0x00, 0x00) != FT_OK ) {
        cerr << "Set Flow Control Failed.\n";
        return -1;
    }
    return FT_OK;
}


//
// print Device Info List --  For debugging
//
void printDeviceInfoList(void) {
    FT_STATUS ftStatus;
    FT_DEVICE_LIST_INFO_NODE *devInfo;
    DWORD numDevs;
    // create the device information list
    ftStatus = FT_CreateDeviceInfoList(&numDevs);
    if (ftStatus == FT_OK) {
        printf("Number of devices is %u\n",numDevs);
    }
    if (numDevs > 0) {
        // allocate storage for list based on numDevs
        devInfo =(FT_DEVICE_LIST_INFO_NODE*)malloc(sizeof(FT_DEVICE_LIST_INFO_NODE)*numDevs);
        // get the device information list
        ftStatus = FT_GetDeviceInfoList(devInfo,&numDevs);
        if (ftStatus == FT_OK) {
            for (int i = 0; i < numDevs; i++) {
                printf("Dev %d:\n",i);
                printf("  Flags=0x%x\n",devInfo[i].Flags);
                printf("  Type=0x%x\n",devInfo[i].Type);
                printf("  ID=0x%x\n",devInfo[i].ID);
                printf("  LocId=0x%x\n",devInfo[i].LocId);
                printf("  SerialNumber=%s\n",devInfo[i].SerialNumber);
                printf("  Description=%s\n",devInfo[i].Description);
                printf("  ftHandle=%p\n",devInfo[i].ftHandle);
            }
        }
    }
}