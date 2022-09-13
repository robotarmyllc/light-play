package com.juanjo.openDmx;

public class OpenDmx {

	static {
		System.loadLibrary("lpdmx");
	}
	
	public static final int OPENDMX_TX=0;
	public static final int OPENDMX_RX=1;
    
    native public static String[] getList();
    
    // add method for list of opened devices
    // add method for device details of an opened device
	
	native public static int connect(String _serial, int _universe, int _mode);
	native public static boolean disconnect(int _handle);
	
    // Returns -1 on err.  Returns -2 for channel out of bounds.
	native public static int setValue(int _channel, int _data);
    // Returns value [0-255], -1 for error, or -2 for channel out of bounds.
	native public static int getValue(int _channel);
}
