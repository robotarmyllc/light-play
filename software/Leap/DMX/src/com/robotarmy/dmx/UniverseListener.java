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
interface UniverseListener {
    public void write( int channel, int val );
    public int   read( int channel );
}
