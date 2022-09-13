/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.dmx;

/**
 *
 * @author mark
 */
public class DmxElement {
    private int universe;
    private int baseChannel;
    
    public DmxElement( int universe, int baseChannel ) {
        this.universe = universe;
        this.baseChannel = baseChannel;
    }

    public int getUniverse() {
        return universe;
    }

    public int getBaseChannel() {
        return baseChannel;
    }
    
    public void setUniverse(int u) {
        this.universe = u;
    }
    
    public void setBaseChannel(int ch) {
        this.baseChannel = ch;
    }
    
}
