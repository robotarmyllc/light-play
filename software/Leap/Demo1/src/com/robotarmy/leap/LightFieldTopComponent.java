/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.leap;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.robotarmy.dmx.Universe;
import com.robotarmy.leap.lightgrid.Grid;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.swing.Timer;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.robotarmy.leap//LightField//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "LightFieldTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "com.robotarmy.leap.LightFieldTopComponent")
@ActionReference(path = "Menu/Window" , position = 335 )
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_LightFieldAction",
        preferredID = "LightFieldTopComponent"
)
@Messages({
    "CTL_LightFieldAction=LightField",
    "CTL_LightFieldTopComponent=LightField Window",
    "HINT_LightFieldTopComponent=This is a LightField window"
})
@SuppressWarnings("serial")
public final class LightFieldTopComponent extends TopComponent implements ActionListener {
    //Logger log = Logger.getLogger("LightFieldTopComponent");
    private static final Logger LOGGER = Logger.getGlobal();

    private final Controller controller = new Controller();
    private boolean leapConnected = false;
    private final Universe universe;
    private final Timer timer;
    private Grid grid = new Grid();
    //private final InputOutput io;

    public LightFieldTopComponent() {
        initComponents();
        setName(Bundle.CTL_LightFieldTopComponent());
        setToolTipText(Bundle.HINT_LightFieldTopComponent());
        brushSizePanel.configure("Brush Size", 1, 20, grid.getBrushSize());
        brushSizePanel.getSlider().addChangeListener(grid);
        add(grid);

        // DMX Universe
        universe = Universe.getUniverse(0);
        // Logging tab
        //io = IOProvider.getDefault().getIO("Log", false);
        //io.select();
        LOGGER.config("[LightField] I/O Initialized.\n");
        //io.getErr().println("Hello from standard err");  //this text should appear in red

        timer = new Timer(30, this);
        timer.setInitialDelay(3000);
        timer.start();
    }

    // Called by Timer at about 30FPS
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (controller.isConnected()) {
            if (!leapConnected) {
                //leapConnected = true;  // Don't need to track this anymore
                leapConnected(controller);
            }
            leapFrame(controller);
        } else if (leapConnected) {
            leapDisconnected(controller);
        }
        
        updateRobots();
    }

    public void leapInit(Controller controller) {
    }

    public void leapConnected(Controller controller) {
        LOGGER.config("[LightField] LEAP Connected.\n");
        leapConnected = true;
    }

    public void leapDisconnected(Controller controller) {
        leapConnected = false;
        LOGGER.config("[LightField] LEAP Disconnected.\n");
    }

    public void leapExited(Controller controller) {
    }

    public void leapFrame(Controller controller) {
        Frame frame = controller.frame();

        if (!frame.hands().isEmpty()) {
            Iterator<Hand> hands = frame.hands().iterator();
            boolean leftDone = false;
            boolean rightDone = false;

            while (hands.hasNext()) {
                Hand h = hands.next();
                if ( leftDone && rightDone ) {
                    // So many hands!!!!
                }
                
                
                if (h.isLeft() && !leftDone) {
                    leftDone = true;  // There can be only one!
                    grid.brushXY(
                            Math.abs(h.palmVelocity().getX())*0.008f,
                            (int)(h.palmPosition().getX()/10) + (Grid.FWIDTH/2), 
                            (int)(h.palmPosition().getZ()/10) + (Grid.FHEIGHT/2), 
                            new Color(50,255-clamp((int)h.palmPosition().getY()/3), 0)
                    );
                }

                if (h.isRight() && !rightDone) {
                    rightDone = true;  // There can be only one!
                    grid.brushXY(
                            Math.abs(h.palmVelocity().getX())*0.008f,
                            (int)(h.palmPosition().getX()/10) + (Grid.FWIDTH/2), 
                            (int)(h.palmPosition().getZ()/10) + (Grid.FHEIGHT/2), 
                            new Color(50, 255-clamp((int)h.palmPosition().getY()/3),0)
                    );
                    //Vector velocity = h.palmVelocity();
                    //Vector pos = h.sphereCenter();
                }
            }
        }
    }
    
    private int clamp(int n) {
        if (n > 255) {
            return 255;
        }
        if (n < 0) {
            return 0;
        }
        return n;
    }

    private void updateRobots() {
        // Skip bot 0.  it's a reserved slot.
        for ( int i=1; i<RobotCoords.ROBOT.length; i++) {
            Color c = grid.getColor(RobotCoords.ROBOT[i]);
            setBotLED(i, c);
        }
    }
    
    private void setBotLED(int n, Color c) {
        universe.write((n * 6),
                c.getRed(), c.getGreen(), c.getBlue() // LEDS
                //clamp(x), clamp(y), clamp(z) // Position
        );
    }

    
        /**
         * This method is called from within the constructor to initialize the
         * form. WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        northPanel = new javax.swing.JPanel();
        brushSizePanel = new com.robotarmy.leap.ui.SliderWidgetPanel();

        setLayout(new java.awt.BorderLayout());

        northPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        northPanel.add(brushSizePanel);

        add(northPanel, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.robotarmy.leap.ui.SliderWidgetPanel brushSizePanel;
    private javax.swing.JPanel northPanel;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
//        io.getOut().close();
//        io.getErr().close();
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
