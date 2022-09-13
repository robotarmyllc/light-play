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
public class DmxConnectException extends Exception {

    DmxConnectException(String serial, int universe, int mode, int err) {
        super("Serial:" + serial + "  universe:" + universe + "   mode:"+ mode + "  error: " + err );
        
        
    }
    
}
