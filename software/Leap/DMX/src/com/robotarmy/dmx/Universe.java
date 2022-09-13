/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.dmx;

import java.util.ArrayList;

/**
 *
 * @author mark
 */
public class Universe {
    static final Universe[] cosmi = {new Universe(0), new Universe(1), new Universe(2)};
    
    private final int n;   
    private final ArrayList<UniverseListener> listeners;
    
    private Universe( int n) {
        this.listeners = new ArrayList<>();
        this.n = n;
    }
    
    public static Universe getUniverse(int i) {
        if ( i < 0 || i > 2 ) {
            return null;
        }
        return cosmi[i];
    }
    
    public int getUniverseID() {
        return n;
    }
    
    public void write( int channel, int val ) {
        for ( UniverseListener dmx: listeners ) {
            dmx.write( channel, val );
        }
    }
    
    public void write( int startChannel, int r, int g, int b, int x, int y, int z ) {
        // TODO:  startChannel can't be higher than 511-6 (505);
        // Chanel 1 in dmx is index 0.  So we decrement input channel.
        int chan = startChannel-1;
        for ( UniverseListener dmx: listeners ) {
            dmx.write( chan,   r );
            dmx.write( chan+1, g );
            dmx.write( chan+2, b );
            dmx.write( chan+3, x );
            dmx.write( chan+4, y );
            dmx.write( chan+5, z );
        }        
    }
    
    public void write( int startChannel, int v1, int v2, int v3 ) {
        // TODO:  startChannel can't be higher than 511-6 (505);
        // Chanel 1 in dmx is index 0.  So we decrement input channel.
        int chan = startChannel-1;
        for ( UniverseListener dmx: listeners ) {
            dmx.write( chan,   v1 );
            dmx.write( chan+1, v2 );
            dmx.write( chan+2, v3 );
        }        
    }
    
    public int read( int startChannel ) {
        try {
            return listeners.get(0).read(startChannel);
        } catch (NullPointerException ex ) {
            return 128;
        }
    }

    void addListener(UniverseListener dmx) {
        if ( !listeners.contains(dmx) ) {
            listeners.add(dmx);
        }
    }

    void removeListener(UniverseListener l) {
        listeners.remove(l);
    }
}
