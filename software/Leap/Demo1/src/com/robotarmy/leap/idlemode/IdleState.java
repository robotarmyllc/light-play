/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.leap.idlemode;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author mark
 */
public class IdleState {

    private static IdleState instance = null;
    private IdleEnablePanel button = null;
    
    private IdleState() {
    }
    
    public static final IdleState getInstance() {
        if (instance == null) {
            instance = new IdleState();
        }
        
        return instance;
    }
    
    protected static final IdleState getInstance(IdleEnablePanel button) {
        if (instance == null) {
            instance = getInstance();
        }
        
        instance.setButton(button);
        
        return instance;
    }
    
    public JPanel getIdleButton() {
        return button;
    }
    
    private void setButton(IdleEnablePanel button) {
        this.button = button;
    }
    
    public void setSelected(boolean state) {
        button.setSelected(state);
    }

    public void setColor( Color c) {
        button.setColor(c);
    }
    
    public boolean isSelected() {
        return button.isSelected();
    }
}
