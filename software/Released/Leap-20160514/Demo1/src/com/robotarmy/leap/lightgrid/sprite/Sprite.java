/*
 * Sprite
 * 
 * An animated element drawn on the grid.  Has a 'position' and 'scale'. Moves 
 * in the direction of velocity.   Updates at 'rate'.
 */
package com.robotarmy.leap.lightgrid.sprite;

import com.leapmotion.leap.Vector;

/**
 *
 * @author mark
 */
public abstract class Sprite {
    private final Vector position = new Vector();
    private final Vector velocity = new Vector();
    
    public abstract void update();
    
    public   void setPosition(Vector position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
        this.position.setZ(position.getZ());
    }
    
    public Vector getPosition() {
        return position;
    }
    
    public   void setVelocity(Vector velocity) {
        this.velocity.setX(velocity.getX());
        this.velocity.setY(velocity.getY());
        this.velocity.setZ(velocity.getZ());
    }
    
    public Vector getVelocity() {
        return velocity;
    }
    
}
