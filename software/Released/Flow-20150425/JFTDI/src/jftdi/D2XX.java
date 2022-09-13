/*
 * JNA class for nativly accessing FTDI D2XX Drivers.
 */
package jftdi;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;

/**
 *
 * @author mark
 */
public class D2XX {
    public static ID2XX INSTANCE = (ID2XX) Native.loadLibrary("ftd2xx", ID2XX.class);
    public class FtStatus extends NativeLong {};
    
    public static final ID2XX.FtStatus FT_OK = new ID2XX.FtStatus();

    private D2XX() {
        // Use get instance.
    }
        
}
