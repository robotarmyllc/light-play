/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.leap.lightgrid;

import java.awt.Color;
import javax.vecmath.Point3f;

/**
 *
 * @author mark
 */
public class ColorElement {
    private final Point3f target  = new Point3f(0, 0, 0);
    private final Point3f current = new Point3f(0, 0, 0);
    private float rate = 8f;

    
    /**
     * @return the target
     */
    public Point3f getTarget() {
        return target;
    }

    public void setTarget(float x, float y, float z) {
        target.x = x;
        target.y = y;
        target.z = z;
        clamp(target);
    }
    
    /**
     * @return the current
     */
    public Point3f getCurrent() {
        return current;
    }

    /**
     * @return the rate
     */
    public float getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(float rate) {
        this.rate = rate;
    }
    
    public Color getColor() {
        clamp(current);
        return new Color((int)(current.x), (int)(current.y), (int)(current.z));
    }
    
    public void setTarget(Color c) {
        target.x = c.getRed();
        target.y = c.getGreen();
        target.z = c.getBlue();
        clamp(target);
    }
    
    private void clamp( Point3f p) {
        if ( p.x < 0) {
            p.x = 0;
        }
        if ( p.y < 0) {
            p.y = 0;
        }
        if ( p.z < 0) {
            p.z = 0;
        }
        if ( p.x > 255.0f) {
            p.x = 255;
        }
        if ( p.y > 255.0f) {
            p.y = 255;
        }
        if ( p.z > 255.0f) {
            p.z = 255;
        }
        
    }
    /**
     * Called at each time frame to update current values to approach target
     * specified rate.
     */
    public void processTarget() {
        clamp(target);
        float dX = getTarget().x - getCurrent().x;
        if ( Math.abs(dX) < rate ) {
            dX = 0.0f;
        }
        if ( dX < 0.0f ) {
            // subract rate
            getCurrent().x -= rate;
        } else if ( dX > 0.0f ) {
            //add rate.
            getCurrent().x += rate;
        } else {
            // No change
            getCurrent().x = getTarget().x;  // Equalize current and target.
        }

        float dY = getTarget().y - getCurrent().y;
        if ( Math.abs(dY) < rate ) {
            dY = 0.0f;
        }
        if ( dY < 0.0f ) {
            // subract rate
            getCurrent().y -= rate;
        } else if ( dY > 0.0f ) {
            //add rate.
            getCurrent().y += rate;
        } else {
            // No change
            getCurrent().y = getTarget().y;  // Equalize current and target.
        }

        float dZ = getTarget().z - getCurrent().x;
        if ( Math.abs(dZ) < rate ) {
            dZ = 0.0f;
        }
        if ( dZ < 0.0f ) {
            // subract rate
            getCurrent().z -= rate;
        } else if ( dZ > 0.0f ) {
            //add rate.
            getCurrent().z += rate;
        } else {
            // No change
            getCurrent().z = getTarget().z;  // Equalize current and target.
        }
        
        clamp(current);
    }

    
}
