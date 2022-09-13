/*
 * Fountain Cell
 *
 * Represents the data state for a cell.
 *
 */
package com.robotarmy.flow.fountain;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mark
 */
public class FountainCell {

    private final int DEF_X = 127;
    private final int DEF_Y = 127;
    private final int DEF_Z = 0;

    private int x = DEF_X;
    private int y = DEF_Y;
    private int z = DEF_Z;
    
    private int robot = 0;  // Robot number (DIP Switch setting)

    public FountainCell() {
    }

    public FountainCell( int x, int y, int z) {
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
        this.x = limit(x);
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
        this.y = limit(y);
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
        this.z = limit(z);
    }

    public void set(Color c) {
        this.x = c.getRed();
        this.y = c.getGreen();
        this.z = c.getBlue();
    }

    public void set( int x, int y, int z ) {
        setX(x);
        setY(y);
        setZ(z);
    }
    
    /**
     * Limit the range of a number to 0-255.
     * @param n
     * @return 
     */
    public static int limit(int n) {
        int nn = n;
        if (nn < 0) {
            nn = 0;
        }
        if (nn > 255) {
            nn = 255;
        }

        return nn;
    }

    void addX(int x) {
        setX(this.x+x);
    }

    void addY(int y) {
        setY(this.y+y);
    }

    void addZ(int z) {
        setZ(this.z+z);
    }

    void clear() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * @return the robot
     */
    public int getRobot() {
        return robot;
    }

    /**
     * @param robot the robot to set
     */
    public void setRobot(int robot) {
        if ( robot >= 0 && robot < 86 ) {
            this.robot = robot;
        } else {
            this.robot = 0; // Not used
            Logger.getGlobal().log(Level.SEVERE, "Application tried to set a robot that is out of range: {0}", robot);
        }
    }

}
