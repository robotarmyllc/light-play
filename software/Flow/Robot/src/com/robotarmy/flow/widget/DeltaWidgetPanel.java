/*
 * RGB Sliders
 */
package com.robotarmy.flow.widget;

import com.robotarmy.dmx.DmxChangeListener;
import com.robotarmy.dmx.DmxChannelEvent;
import com.robotarmy.flow.Position3D;
import java.awt.Color;
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
import org.openide.util.Exceptions;

/**
 *
 * @author mark
 */
public class DeltaWidgetPanel extends JPanel implements ChangeListener {

    private static final Logger LOG = Logger.getLogger("DeltaWidgetPanel");
    private static final long serialVersionUID = 150364502447223L;

    private final ArrayList<DmxChangeListener> listeners = new ArrayList<>();

    private static final String Z = "Z";

    private static final int X_CHANNEL = 3;
    private static final int Y_CHANNEL = 4;
    private static final int Z_CHANNEL = 5;

    private final Slider zSlider = new Slider(Z);
    private final DeltaRobot delta = new DeltaRobot();
    
    private final Position3D pos;

    public DeltaWidgetPanel(Position3D pos) {
        LOG.setLevel(Level.FINEST);

        this.pos = pos;
        
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(320, 290));
        setBackground(Color.GRAY);
        //setBounds(new Rectangle(50, 130));
        add(Box.createRigidArea(new Dimension(15, 0)));

        delta.setPosition(pos);
        
        add(delta);
        add(zSlider);

        zSlider.addChangeListener(this);
        delta.addChangeListener(this);
        
        //setEnabled(false);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        try {
            if (e.getSource().getClass().equals(DeltaRobot.class)) {
                DeltaRobot d = (DeltaRobot) e.getSource();
                pos.setX(d.getPosition().getX());
                pos.setY(d.getPosition().getY());
                pos.setZ(d.getPosition().getZ());
                zSlider.setValue(d.getPosition().getZ());
//                notifyListeners(new DmxChannelEvent(new DmxChannelEvent[]{
//                    new DmxChannelEvent(X_CHANNEL, d.getPosition().getX()),
//                    new DmxChannelEvent(Y_CHANNEL, d.getPosition().getY()),
//                    new DmxChannelEvent(Z_CHANNEL, d.getPosition().getZ())
//                }));
            } else {
                int offset;
                JSlider slider = (JSlider) e.getSource();
                //LOG.log(Level.CONFIG, "Slider Moved: {0}\n", ((JSlider) e.getSource()).getName());
                switch (slider.getName()) {
                    case Z:
                        offset = Z_CHANNEL;
                        break;
                    default:
                        LOG.warning("Unknown slider changed!\n");
                        return;  // Should never get here.
                }

                pos.setZ(slider.getValue());
                delta.setPosition(pos);
//                notifyListeners(new DmxChannelEvent(offset, slider.getValue()));
            }
        } catch (ClassCastException ex) {
            LOG.warning("Tried to handle state change for non JSlider object!\n");
            Exceptions.printStackTrace(ex);
        }

    }

    public void addChangeListener(DmxChangeListener l) {
        listeners.add(l);
    }

    private void notifyListeners(DmxChannelEvent e) {
        for (DmxChangeListener l : listeners) {
            l.stateChanged(e);
        }
    }

    public void reset() {
        delta.reset();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        delta.setEnabled(enabled);
    }

    public void setPosition(Position3D d) {
        delta.setPosition(d);
    }

    public Position3D getPosition() {
        return delta.getPosition();
    }

}
