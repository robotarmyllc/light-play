/*
 * Robot Army
 *
 */
package com.robotarmy.dmx;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.Exceptions;

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
//    private int connectTries = 0;

    private DmxManager(String serial) {
        this.serial = serial;
        instances.add(this);
    }

    public static synchronized String[] getSerials() {
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
//        connectTries++;
        
        if (_handle <= 0) {
//            if ( _handle == -3 && connectTries < 100) { // Could not get handle.
//                // Try again.
//                connect();
//            } else {
                throw new DmxConnectException(serial, universe, getMode(), _handle);
//            }
        }

//        connectTries = 0;
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
        // Return if we are already that universe.
        if ( this.universe == universe ) {
            return;
        }
        
        if ( this.universe != -1 ) {
           //throw new IllegalStateException("Can only set universe for a node one time!\n");
           if ( hasConnection() ) {
               try {
                   disconnect();
               } catch (DmxDisconnectException ex) {
                   Exceptions.printStackTrace(ex);
               }
           }
           Universe.getUniverse(this.universe).removeListener(this);
        }
        
        this.universe = universe;
        Universe.getUniverse(universe).addListener(this);
        
        // TODO:  Alllow switching to new universe.
        //  - disconnect
        //  - register universe
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
        if (!hasConnection() ) {
            //LOG.log(Level.WARNING, "Error writing value to DMX universe {0}!  eCode={1}  chan={2}  val={3}\n", new Object[]{universe, rc,channel,val});
            return;
        }
        int rc = DMX.setValue(handle, channel, val);
        if ( rc != 0) {
            LOG.log(Level.SEVERE, "Error writing value to DMX universe {0}!  eCode={1}  chan={2}  val={3}\n", new Object[]{universe, rc,channel,val});
        }
    }

    @Override
    public int read(int channel) {
        return DMX.getValue(universe, channel);
    }
}
