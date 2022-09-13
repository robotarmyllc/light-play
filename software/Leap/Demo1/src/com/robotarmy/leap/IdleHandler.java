/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.leap;

import com.robotarmy.leap.idlemode.IdleMode;
import com.robotarmy.leap.idlemode.RandomIdleMode;
import java.awt.Point;
import java.util.Arrays;

/**
 *
 * @author mark
 */
public class IdleHandler {

    public static final int MODE_RANDOM = 1;
    
    private static final int COUNT_DOWN_DURATION = 70;
    
    private final DeltaRobot[][] lightField = new DeltaRobot[23][14];
    //private int mode = -1;
    private IdleMode mode = null;
    private int lastMode = 0;
    
    private final StrobeBar bar = new StrobeBar(1, 6);
    private int barLit = 0;

    //private DeltaRobot[] modeBots = null;
    //private int modeInTime = 0;
    //private int[] modeIncrement = new int[3];  // X,Y,Z
    //private int countDown = 0;

    IdleHandler(DeltaRobot[][] fingerBotL, DeltaRobot[][] fingerBotR) {
        mapBots(fingerBotL);
        mapBots(fingerBotR);
    }

    /**
     * Map this 2D list of bots to our uniform field model used here.
     *
     * @param bots
     */
    private void mapBots(DeltaRobot[][] bots) {
        for (DeltaRobot[] bb : bots) {
            for (DeltaRobot b : bb) {
                Point p = RobotCoords.ROBOT[b.channel];
                lightField[p.x][p.y] = b;
            }
        }
    }

    public void setMode(int modeNum) {
        switch (modeNum) {
            case MODE_RANDOM:
                lastMode = modeNum;
                // Pick bots for animation.
                mode = new RandomIdleMode(
                        pickBots((int) ((Math.random()*20)+6)), 
                        (int) ((Math.random()*COUNT_DOWN_DURATION)+1)
                );
                break;
            default:
                this.mode = null;
                lastMode = 0;
                break;
        }
    }

    public void update() {
        if ( mode != null && mode.update() ) {
            setMode(lastMode);  // Loop it!
        }
        
        if ( mode != null ) {
            bar.solo(barLit/3, 255);
            barLit++;
            if ( barLit/3 > 5 ) {
                barLit = 0;
            }
        }
    }

    private DeltaRobot[] pickBots(int n) {
        DeltaRobot[] bots = new DeltaRobot[n];

        for (int i = 0; i < bots.length; i++) {
            DeltaRobot d;
            do {
                int rx = (int) (Math.random() * lightField.length);
                int ry = (int) (Math.random() * lightField[rx].length);
                d = lightField[rx][ry];
            } while (d == null || Arrays.asList(bots).contains(d));
            bots[i] = d;
        }
        
        return bots;

    }

    void clear() {
        mode = null;
        lastMode = 0;
        bar.blackOut();
    }

}
