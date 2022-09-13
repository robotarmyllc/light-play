/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.widget;

import com.robotarmy.dmx.DmxChangeListener;
import com.robotarmy.dmx.DmxChannelEvent;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author mark
 */
public class RGBPanel extends JPanel implements ChangeListener {

    private static final Logger LOG = Logger.getLogger("DeltaWidgetPanel");

    private final ArrayList<DmxChangeListener> listeners = new ArrayList<>();
    
    private static final String R = "R";
    private static final String G = "G";
    private static final String B = "B";

    private final Slider rSlider = new Slider(R);
    private final Slider gSlider = new Slider(G);
    private final Slider bSlider = new Slider(B);
    private final int R_CHANNEL = 0;
    private final int G_CHANNEL = 1;
    private final int B_CHANNEL = 2;

    public RGBPanel(int u) {
        LOG.setLevel(Level.FINEST);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(1,120));
        add(rSlider);
        add(Box.createRigidArea(new Dimension(30, 0)));
        add(gSlider);
        add(Box.createRigidArea(new Dimension(30, 0)));
       add(bSlider);

        rSlider.addChangeListener(this);
        gSlider.addChangeListener(this);
        bSlider.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
            int offset;
            JSlider slider = (JSlider) e.getSource();
            //LOG.log(Level.CONFIG, "Slider Moved: {0}\n", ((JSlider) e.getSource()).getName());
            switch (slider.getName()) {
                case R:
                    offset = R_CHANNEL;
                    break;
                case G:
                    offset = G_CHANNEL;
                    break;
                case B:
                    offset = B_CHANNEL;
                    break;
                default:
                    LOG.warning("Unknown slider changed!\n");
                    return;  // Should never get here.
                }

                notifyListeners(new DmxChannelEvent(offset, slider.getValue()));
            //universe.write(BASE_CHANNEL + offset, slider.getValue());
    }

    public void setR(int n) {
        rSlider.setValue(n);
    }

    public void setG(int n) {
        gSlider.setValue(n);
    }

    public void setB(int n) {
        bSlider.setValue(n);
    }

    public int getR() {
        return rSlider.getValue();
    }

    public int getG() {
        return gSlider.getValue();
    }

    public int getB() {
        return bSlider.getValue();
    }

    public void addChangeListener(DmxChangeListener l) {
        listeners.add(l);
    }
    
    private void notifyListeners(DmxChannelEvent e) {
        for ( DmxChangeListener l: listeners ) {
            l.stateChanged(e);
        }
    }

    public void reset() {
        rSlider.setValue(128);
        gSlider.setValue(128);
        bSlider.setValue(128);
    }

}
