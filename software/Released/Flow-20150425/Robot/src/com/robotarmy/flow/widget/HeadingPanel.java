/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.widget;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author mark
 */
public class HeadingPanel extends JPanel {

    private final String UNSET = "???";
    
    JLabel universeLabel = new JLabel("Universe:");
    JLabel baseAddressLabel = new JLabel("Base Address:");

    JLabel universeValueLabel = new JLabel(UNSET);
    JLabel baseAddressValueLabel = new JLabel(UNSET);

    public HeadingPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(universeLabel);
        add(universeValueLabel);
        add(Box.createRigidArea(new Dimension(30, 5)));
        add(baseAddressLabel);
        add(baseAddressValueLabel);
    }

    public void setUniverse(int u) {
        if (u >= 0) {
            universeValueLabel.setText(String.valueOf(u));
        } else {
            universeValueLabel.setText(UNSET);
            baseAddressValueLabel.setText(UNSET);
        }
    }

    public void setBaseAddress(int addr) {
        baseAddressValueLabel.setText(String.valueOf(addr));
    }
}
