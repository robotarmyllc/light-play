/*
 * DMX drivers
 *
 * Copyright 2015  Robot Army LLC    http://robot-army.com
 */
package com.robotarmy.dmx;

/**
 *
 * @author mark
 */
public class DMX {

    static {
        System.loadLibrary("DMX4OSX");
    }

    public static final int DMX_TX = 0;
    public static final int DMX_RX = 1;

    /**
     * Get a list of FTDI devices.
     * 
     * @return packed @String in format <LOCID>:<SERNUM>:<DESCRIPTION>
     */
    native public static String[] getList();

    // TODO: add method for list of opened devices
    // TODO: add method for device details of an opened device
    
    /**
     * Connect to the named <SERNUM> FTDI device.
     * 
     * @param _serial String of the device to open  (FTxxxxxx)
     * @param _universe Universe for device to operate on.
     * @param _mode Transmit or Receive. @DMX_TX  or @DMX_RX (not yet supported)
     * @return handle of opened port (1-MAXINT) or  negative value (see FT prog guide)
     */
    native public static int connect(String _serial, int _universe, int _mode);

    /**
     * Disconnect from open port.
     * 
     * @param _handle value provided by @connect() when the port was opened.
     * @return 
     */
    native public static boolean disconnect(int _handle);

    /**
     * Set value for the @handle &  @channel.
     * 
     * @param _handle Handle to use.
     * @param _channel  Channel to set.
     * @param _data     Value to set to  (0-255)
     * @return -1 on err.  Return -2 for channel out of bounds.
     */
    native public static int setValue(int _handle, int _channel, int _data);

    /**
     * 
     * @param _handle Universe to use.
     * @param _channel  Channel to get.
     * @return value [0-255], -1 for error, or -2 for channel out of bounds.
     */
    native public static int getValue(int _handle, int _channel);

}
