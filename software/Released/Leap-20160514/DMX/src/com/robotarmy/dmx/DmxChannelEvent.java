/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.dmx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author mark
 */
public class DmxChannelEvent {

    private List<DmxChannelEvent> events = new ArrayList<>();

    private Data data = null;
    
    public DmxChannelEvent(int offset, int value) {
        data = new Data( offset, value );
    }

    public DmxChannelEvent(DmxChannelEvent[] events) {
        this.events = Arrays.asList(events);
    }

    public List<DmxChannelEvent> getEvents() {
        return events;
    }
    
    public boolean hasData() {
        return data != null;
    }
    
    public boolean hasEvents() {
        return ( !events.isEmpty() );
    }
    
    public int getOffset() {
        if ( hasData() ) {
            return data.offset;
        }
        return -1; 
    }
    
    public int getValue() {
        if ( hasData() ) {
            return data.value;
        }
        return -1; 
    }
    
    private class Data {

        public final int offset;
        public final int value;

        private Data(int offset, int value) {
            this.offset = offset;
            this.value = value;
        }

    }
}
