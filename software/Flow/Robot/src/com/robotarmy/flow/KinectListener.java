/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow;

import javax.vecmath.Point3i;

/**
 *
 * @author mark
 */
public interface KinectListener {
    
    public void kinectPositionChanged(Point3i p);
    
}
