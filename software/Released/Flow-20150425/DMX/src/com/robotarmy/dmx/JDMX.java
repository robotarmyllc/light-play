/*
 * JNA DMX Class
 */
package com.robotarmy.dmx;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import jftdi.D2XX;
import jftdi.ID2XX;
import jftdi.ID2XX.FtDeviceListInfoNode;

/**
 *
 * @author mark
 */
public class JDMX {

    private static final Logger LOG = Logger.getLogger("JDMX");

    public static String[] getSerialNumbers() {
        ID2XX ftdiLib = D2XX.INSTANCE;
        PointerByReference NUM_DEVS = new PointerByReference();

        ID2XX.FtStatus callCreateList = ftdiLib.FT_CreateDeviceInfoList(NUM_DEVS);
        if ( !callCreateList.equals(D2XX.FT_OK) ) {
            Pointer value = NUM_DEVS.getValue();
            long nativeValue = Pointer.nativeValue(value);
            LOG.warning("No FTDI devices seem to be plugged in.\n");
            return (new String[0]);
        }

        // This should probably never happen.
        if (NUM_DEVS.getValue() == null) {
            LOG.severe("Could not get Device Info List!\n");
            return (new String[0]);
        }

        long num = Pointer.nativeValue(NUM_DEVS.getValue());

        LOG.log(Level.SEVERE, "Found {0} devices.\n", num);

        FtDeviceListInfoNode.ByReference node = new FtDeviceListInfoNode.ByReference();
        FtDeviceListInfoNode[] nodes = (FtDeviceListInfoNode[]) node.toArray((int) num );

        // Get the device information list
        // C-Code:   if (FT_GetDeviceInfoList(devInfo, &num) == FT_OK) {
        if (!ftdiLib.FT_GetDeviceInfoList(node, NUM_DEVS).equals(D2XX.FT_OK) ) {
            LOG.severe("Failed getting info on devices.\n");
            return (new String[0]);
        }

        Pointer p = nodes[0].SerialNumber.getValue();
        // extract the null-terminated string from the Pointer
        String val = p.getString(0);
        System.out.println("Serial: " + val);
    
        //nodes[0].read();
        
        String[] serNums = new String[(int)num];
        //FtDeviceListInfoNode.ByReference[] toArray = ptr.toArray((int)num);

        // Return the serial numbers
        //FtDeviceListInfoNode[] toArray = (FtDeviceListInfoNode[]) ptr.toArray((int) num);
        int i=0;
//        for ( FtDeviceListInfoNode item: nodes ) {
//            serNums[i]=item.toString();
//            //serNums[i] = item.SerialNumber.toString();
//            i++;
//        }
        
        serNums[0] = "1";
        serNums[1] = "2";
        
        return serNums;
    }
}
