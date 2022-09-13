/*
 * CM0104 - Six Channel DMX Strobe.
 * 
 * Channel 0 : Config Channel
 * Channel 1-6 : LEDs on Bar  Right to Left (when looking at)
 *
 */
package com.robotarmy.leap;

import com.robotarmy.dmx.Universe;

/**
 *
 * @author mark
 */
public class StrobeBar {

    private final int universe;
    private final int channel;

    public StrobeBar( int universe, int channel ) {
        this.universe = universe;
        this.channel = channel;        
    }
    
    public void setValues( int c1, int c2, int c3, int c4, int c5, int c6 ) {
        Universe u = Universe.getUniverse(universe);
        u.write(channel+1, c1);
        u.write(channel+2, c2);
        u.write(channel+3, c3);
        u.write(channel+4, c4);
        u.write(channel+5, c5);
        u.write(channel+6, c6);
    }
    
    public void solo( int led, int val ) {
        int slot[] = { 0,0,0,0,0,0 };
        slot[led] = clamp(val); 
        setValues(slot[0], slot[1], slot[2], slot[3], slot[4], slot[5]);
    }
    
    public void blackOut() {
        setValues(0, 0, 0, 0, 0, 0);
    }
    
    private int clamp( int val ) {
        if (val < 0) {
            return 0;
        }
        if (val > 255) {
            return 255;
        }
        return val;
    }
}
