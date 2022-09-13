/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.fountain;

/**
 *
 * @author mark
 */
public class FountainRobot {

    private int addr;
    private int x;
    private int y;

    public FountainRobot(int addr, int x, int y) {
        this.addr = addr;
        this.x = x;
        this.y = y;
    }

    /**
     * @return the addr
     */
    public int getAddr() {
        return addr;
    }

    /**
     * @param addr the addr to set
     */
    public void setAddr(int addr) {
        this.addr = addr;
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
        this.x = x;
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
}
