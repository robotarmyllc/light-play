/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow;

import com.robotarmy.flow.widget.DeltaWidgetPanel;
import java.awt.Component;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author mark
 */
public class Position3DPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        Position3D p = (Position3D) getValue();
        if (p == null) {
            return "No Position Set";
        }
        return p.getX() + "," + p.getY() + "," + p.getZ();
    }

//    @Override
//    public void setAsText(String s) {
//        try {
//            setValue(new SimpleDateFormat("MM/dd/yy HH:mm:ss").parse(s));
//        } catch (ParseException pe) {
//            IllegalArgumentException iae = new IllegalArgumentException("Could not parse date");
//            throw iae;
//        }
//    }
    @Override
    public Component getCustomEditor() {
        return new DeltaWidgetPanel((Position3D) getValue());
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

}
