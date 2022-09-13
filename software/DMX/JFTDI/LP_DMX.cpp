//
//  LP_DMX.cpp
//  
//
//  Created by Mark Koch on 1/11/15.
//
//
//  NOTE:  You must have drivers in D2XX mode!   If you can see /dev/tty-usb...
//         devices then this won't work.  See: http://www.dmxis.com/macdriverfix/

//---------------------------------------------------------------------------

#include <STDDEF.H>
#include <iostream>
#include "LP_DMX.h"
#include "com_juanjo_openDmx_OpenDmx.h"
#include <vector>
#include <sstream>
#include <unordered_map>
#include <pthread.h>
#include <unistd.h>

using namespace std;
void *txThread(void *);
void getSerialNumbers(vector<string>& );
int setPortSettings(FT_HANDLE);
void printDeviceInfoList(void);

typedef struct {
    pthread_t thread;
    int    thread_id;
    FT_HANDLE  ftHandle;
    char   *serialNum;
    int    universe;
    int     running;
} Device;

unordered_map<int, Device* >devList;
unsigned char dmxBuffer[513];
int handle = 0;   // Index of last given handle

/*
 * getList()
 *
 * Format:   LocId:Serial:Description
 */
JNIEXPORT jobjectArray JNICALL Java_com_juanjo_openDmx_OpenDmx_getList
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
        cout << "No FTDI devices found.\n";
        
        ret = (jobjectArray)env->NewObjectArray(
            0, env->FindClass("java/lang/String"),
                 env->NewStringUTF(""));
        return ret;
    }

    // Allocate storage for list based on numDevices
    FT_DEVICE_LIST_INFO_NODE* devInfo = new FT_DEVICE_LIST_INFO_NODE[num];

    // Get the device information list
    if (FT_GetDeviceInfoList(devInfo, &num) == FT_OK) {

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
    }
    
    return(ret);
}
    

/**
 * connect()
 *
 */
JNIEXPORT jint JNICALL Java_com_juanjo_openDmx_OpenDmx_connect
(JNIEnv *env, jclass _class, jstring _serial, jint _universe, jint _mode ){
    FT_HANDLE ftHandle;
    char *serial;
    serial = const_cast<char *>( env->GetStringUTFChars( _serial, 0) );
    
    //TODO
    // Check if serial is already used.
    
    // Connect to specified port
    if ( FT_OpenEx((void*)serial, FT_OPEN_BY_SERIAL_NUMBER, &ftHandle) == FT_OK ) {
        handle++;
        
        // Init DMX buffer for this universe
        for ( int i=0; i<513; i++ ) {
            dmxBuffer[i] = 0;
        }
             
        //struct device dev;
        Device dev;

        //dev.serialNum = "00000000\0";
        dev.serialNum = serial;
        dev.universe = _universe;
        dev.ftHandle = ftHandle;
        
        devList[handle] = &dev;
        
        //
        if( pthread_create(&dev.thread, NULL, txThread, (PVOID)handle) != 0 ) {
            cout << "Error:unable to create thread," << endl;
            
            // TODO: Close the port
            
            // TODO: Remove the device from devList
            devList[handle] = NULL;
            dev.running = 0;
            return -1;
        }
        
        // Store handle index and ftHandle.
        //handleList[handle] = ftHandle;
        //serialList[handle] = serial;
        //universeList[handle] = _universe;
        
        cout << "Opened " << dev.serialNum << " as handle # " << handle << endl;
    } else {
        env->ReleaseStringUTFChars(_serial, serial);
        cerr << "Could not open [" << serial << "]\n";
        return -1;
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
JNIEXPORT jboolean JNICALL Java_com_juanjo_openDmx_OpenDmx_disconnect
(JNIEnv *env, jclass _class, jint _handle){

    // Stop timer loop.
    Device dev = *devList[_handle];
    //devList[_handle].running = 0;
    
    
    if (FT_Close(dev.ftHandle) != FT_OK ) {
        cout << "Could not close handle index: " << _handle << endl;
        return false;
    }
    cout << "Closed [" << dev.serialNum << "]" << endl;
    
    return true;
}


/**
 * setValue( int channel, int value)
 *
 * Returns status as int.  0
 * 0-255
 */
JNIEXPORT int JNICALL Java_com_juanjo_openDmx_OpenDmx_setValue
(JNIEnv *_env, jclass _class, jint _channel, jint _value){
    
    if(_channel<0) return -2;
    if(_channel>511) return -2;
    
    dmxBuffer[_channel+1]=_value;
    
    return 0;
}


/**
 * getValue( int channel )
 *
 * Returns value 0-255
 */
JNIEXPORT jint JNICALL Java_com_juanjo_openDmx_OpenDmx_getValue
(JNIEnv *_env, jclass _class, jint _channel){
    
    if(_channel<0)   return -2;
    if(_channel>511) return -2;
    
    return (jint)dmxBuffer[_channel+1];
}



/**
 *  TX Thread
 *
 */
void *txThread(PVOID _handle) {
    DWORD bytesWritten;
    long h = (long)_handle;

    Device d = *devList[h];
    d.running  = 1;
    
    cout << "Starting Thread for [" << d.serialNum << "]" << endl;
    
    while (d.running) {
        
        // Transmit the packet
        // Set comm break
        if ( FT_SetBreakOn(d.ftHandle) != FT_OK ) {
            //  Abort?
        }
        // Clear Comm break
        if ( FT_SetBreakOff(d.ftHandle) != FT_OK ) {
            //  Abort?
        }
        // Write buffer
        if ( FT_Write(d.ftHandle, dmxBuffer, sizeof(dmxBuffer), &bytesWritten) ){
            // Abort?
        };
        
        // Wait  66mS
        usleep(33000);

    }
    
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
        printf("Number of devices is %d\n",numDevs);
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
        printf("Number of devices is %d\n",numDevs);
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

