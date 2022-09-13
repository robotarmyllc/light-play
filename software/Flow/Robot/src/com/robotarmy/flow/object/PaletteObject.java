/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.object;

/**
 *
 * @author mark
 */
public class PaletteObject extends AFlowObject {
    public final static String TYPE = "palette";
    
    private int address=0;
    private int x=0;
    private int y=0;

    public PaletteObject(AFlowObject newParent) {
        super(newParent);
        
        setDescription(TYPE);
    }
    
    /**
     * 
     * @return type
     */
    @Override
    public String getType() {
        return TYPE;
    }
    
    /**
     * 
     * @param address  DMX address
     */
    public void setAddress(int address) {
        this.address = address;
    }
    
    /**
     * 
     * @param x 
     */
    public void setX( int x ) {
        this.x = x;
    }

    /**
     * 
     * @param y 
     */
    public void setY( int y ) {
        this.y = y;
    }

    /**
     * @return the DMX address
     */
    public int getAddress() {
        return address;
    }

    /**
     * @return the x position
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y position
     */
    public int getY() {
        return y;
    }
}
