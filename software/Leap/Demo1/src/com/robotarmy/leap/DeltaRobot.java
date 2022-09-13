/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.leap;

import java.util.logging.Logger;

/**
 *
 * @author mark
 */
public class DeltaRobot {
    private static final Logger LOGGER = Logger.getGlobal();

    private static double NX = 127.0;
    private static double NY = 127.0;
    private static double NZ = 40.0;
    
    public int channel;
    public double x = NX;
    public double y = NY;
    public double z = NZ;
    public double rate = (Math.random() * 3.0) + 0.5;  // Decay rate steps per update()
    public int steps = 100; // How many steps from target to actual.
    

    // Change by these amounts at each update.
//    private double dX = 0.0;
//    private double dY = 0.0;
//    private double dZ = 0.0;

    // Target position.
    private double tX = NX;
    private double tY = NY;
    private double tZ = NZ;
    
    // Begin values
    private double bX;
    private double bY;
    private double bZ;
    
    // Scaling factor for output value.
    private double sX;
    private double sY;
    private double sZ;
    
    private int decayWait = 0;
    private double dT = 0.0;
    private double time = 0.0;

    public DeltaRobot(int channel) {
        this.channel = channel;
    }

    public void setTarget(int x, int y, int z, int steps) {
        this.tX = x;
        this.tY = y;
        this.tZ = z;
        this.bX = this.x;
        this.bY = this.y;
        this.bZ = this.z;
        
        this.steps = steps;
        this.decayWait = (int) ((Math.random() * steps) + 1 );

//        this.dX = (tX - this.x) / steps;
//        this.dY = (tX - this.y) / steps;
//        this.dZ = (tX - this.z) / steps;
        
        // Scaling factor for output result
        this.sX = (tX - this.x) / Math.PI;
        this.sY = (tY - this.y) / Math.PI;
        this.sZ = (tZ - this.z) / Math.PI;
        
        dT = 2 * Math.PI / steps;
        time = -Math.PI;
        
        // Time for atan input is -pi to +pi
        // Value output is -pi/2 to pi/2
        //y = Math.atan(x);
        
//        if ( channel == 48 ) {
//            LOGGER.log(Level.FINE, "[DeltaRobot] setTarget({0},{1},{2})  dT={3}  scale={4},{5},{6}\n", 
//                    new Object[]{tX, tY, tZ, dT,sX,sY,sZ});
//        }
    }

    public void update() {
        time += dT;
        double a = Math.atan(time) + (Math.PI/2);  // outputs zero to PI.
        
        x = bX + (sX * a);
        y = bY + (sY * a);
        z = bZ + (sZ * a);
        
//        if ( channel == 48 ) {
//            LOGGER.log(Level.FINE, "[DeltaRobot] update({0},{1},{2})  time={3}  atan={4}\n", new Object[]{x, y, z, time,a});
//        }

//        if (tX != x) {
//            if (Math.abs(tX - x) > Math.abs(dX)) {
//                x += dX;
//            } else {
//                x = tX;
//                dX = 0.0;
//            }
//        }
//        if (tY != y) {
//            if (Math.abs(tY - y) > Math.abs(dY)) {
//                y += dY;
//            } else {
//                y = tY;
//                dY = 0.0;
//            }
//        }
//        if (tZ != z) {
//            if (Math.abs(tZ - z) > Math.abs(dZ)) {
//                z += dZ;
//            } else {
//                z = tZ;
//                dZ = 0.0;
//            }
//        }
    }

    public void decay() {
        if (decayWait > 0) {
            decayWait--;
            return;
        }
        if (x > (NX + rate)) {
            x -= rate;
        } else if (x < (NX - rate)) {
            x += rate;
        }
        if (y > (NY + rate)) {
            y -= rate;
        } else if (y < (NY - rate)) {
            y += rate;
        }
        if (z > (NZ + rate)) {
            z -= rate;
        } else if (z < (NZ - rate)) {
            z += rate;
        }
    }
}
