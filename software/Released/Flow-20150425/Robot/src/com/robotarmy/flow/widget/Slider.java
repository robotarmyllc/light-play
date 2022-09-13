/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.widget;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;

/**
 *
 * @author mark
 */
@SuppressWarnings("serial")
public class Slider extends JPanel {
    private final JLabel label = new JLabel();
    private final JSlider slider = new JSlider(0, 255, 127);
    
    public Slider( String lbl ){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label.setText(lbl);
        slider.setName(lbl);
        slider.setOrientation(SwingConstants.VERTICAL);
        setOpaque(false);
        
        add(slider);
        add(label);
    }

    public void setValue(int n) {
        if ( n < 0 ) { n=0; }
        if ( n>255 ) { n=255; }
        slider.setValue(n);
    }

    public int getValue() {
        return slider.getValue();
    }

    void addChangeListener(ChangeListener l) {
        slider.addChangeListener(l);
    }
    

}
