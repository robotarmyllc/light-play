/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.object;

/**
 *
 * @author mark
 */
public class RootObject extends AFlowObject {
    public final static String TYPE = "root";

    public RootObject() {
        super(null);
        setName("root");
        setDescription(TYPE);
    }

    @Override
    public RootObject getRoot() {
        return this;
    }

    @Override
    public String getType() {
        return TYPE;
    }    
    
}
