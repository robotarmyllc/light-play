/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.project;

/**
 *
 * @author mark
 */
public class ProjectChangeEvent {
    public static enum EVENT_TYPE {       
        FILE_LOADED, FILE_SAVED, DATA_CHANGED, CLOSED
    }
    
    private final EVENT_TYPE type;
    
    public ProjectChangeEvent( EVENT_TYPE type ) {
        this.type = type;
    }
    
    public EVENT_TYPE getType() {
        return type;
    }
}
