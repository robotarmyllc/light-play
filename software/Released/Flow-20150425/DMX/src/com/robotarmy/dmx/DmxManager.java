/*
 * Robot Army
 *
 */
package com.robotarmy.dmx;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * DmxManager -- Manage the system's Native DMX instances.
 *
 * @author mark
 */
public class DmxManager implements UniverseListener {
    private static final Logger LOG = Logger.getLogger("DMX_Manager");

    private static final ArrayList<DmxManager> instances = new ArrayList<>();

    private int handle = -1;
    private final String serial;
    private int universe = -1;
    private int mode = DMX.DMX_TX;

    private DmxManager(String serial) {
        this.serial = serial;
        instances.add(this);
    }

    public static String[] getSerials() {
        return DMX.getList();
    }
    
    /**
     *
     * @param serial
     * @return instance of this class associated with 'serial'
     */
    public static DmxManager getInstance(String serial) {
        
        // TODO  check whether serial is a useable device.
        

        for (DmxManager mgr : instances) {
            if (mgr.getSerial().equals(serial)) {
                return mgr;
            }
        }

        return new DmxManager(serial);
    }

    public void connect() throws DmxConnectException {
        // Return if we are already connected.
        if (hasConnection()) {
            return;
        }

        int _handle = DMX.connect(serial, universe, getMode());

        if (_handle <= 0) {
            throw new DmxConnectException();
        }

        this.handle = _handle;
    }
    
    public void disconnect() throws DmxDisconnectException {
        if ( hasConnection() ) {
            if ( DMX.disconnect(handle) ) {
                handle = -1;
            } else {
                throw new DmxDisconnectException();
            }
        }       
    }
            

    public String getSerial() {
        return serial;
    }

    public boolean hasConnection() {
        return handle > 0;
    }
    
    /**
     * @return the universe number
     */
    public int getUniverse() {
        return universe;
    }

    /**
     * @param universe the universe to set
     */
    public void setUniverse(int universe) {
        if ( this.universe != -1 ) {
           throw new IllegalStateException("Can only set universe for a node one time!\n");
        }
        this.universe = universe;
        Universe.getUniverse(universe).addListener(this);
    }

    /**
     * @return the mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public void write(int channel, int val) {
        if ( DMX.setValue(universe, channel, val) != 0) {
            LOG.severe("Error writing value to DMX!\n");
        }
    }
}
