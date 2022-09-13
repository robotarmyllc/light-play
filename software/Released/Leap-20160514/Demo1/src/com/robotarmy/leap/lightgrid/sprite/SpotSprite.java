/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.leap.lightgrid.sprite;

import java.awt.Color;


public class SpotSprite extends Sprite {

    private Color color = Color.BLUE;
    
    @Override
    public void update() {
        // Fade out the color
        color = new Color(
                this.color.getRed()   - 5,
                this.color.getGreen() - 5,
                this.color.getBlue()  - 5
        );
    }

    
}
