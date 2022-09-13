/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.leap.idlemode;

import com.robotarmy.leap.DeltaRobot;

/**
 *
 * @author mark
 */
public class RandomIdleMode implements IdleMode {

    private final DeltaRobot[] bots;
    private int countDown;
    private final int duration;

    public RandomIdleMode(DeltaRobot[] bots, int duration) {
        this.bots = bots;
        this.duration = duration;
        
        setup();
    }

    @Override
    public final void setup() {
        for (DeltaRobot d : bots) {
            d.setTarget(
                    (int) (Math.random() * 255),  // X
                    (int) (Math.random() * 255),  // Y
                    //127,
                    //127,
                    //127,
                    (int) (Math.random() * 200) + 40,  // Z
                    (int) (Math.random() * 200) + 1  // Time
                    
            );
        }
        this.countDown = duration;
    }

    @Override
    public boolean update() {
        if (countDown > 0) {
            for (DeltaRobot d : bots) {
                d.update();
            }
            countDown--;
            
            return countDown <= 0;
        } else {
            return true;
        }
    }

}
