/*
 * Stores a 3D postion for the end-effector
 * 
 * Range for X, Y, or Z is  [0-255]
 *
 */
package com.robotarmy.flow;

/**
 *
 * @author mark
 */
public class Position3D {

    private int x=127;
    private int y=127;
    private int z=127;

    public Position3D() {}
    
    public Position3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        if (x < 0) {
            this.x = 0;
        } else if (x > 255) {
            this.x = 255;
        } else {
            this.x = x;
        }
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the z
     */
    public int getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(int z) {
        this.z = z;
    }

}
