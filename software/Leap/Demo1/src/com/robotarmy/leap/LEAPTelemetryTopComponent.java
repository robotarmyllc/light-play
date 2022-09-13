/*
 * Displays relevant telemetry received from the LEAP.
 */
package com.robotarmy.leap;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;
import com.robotarmy.dmx.Universe;
import com.robotarmy.leap.idlemode.IdleState;
import com.robotarmy.leap.ui.HandPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.robotarmy.leap//LEAPTelemetry//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "LEAPTelemetryTopComponent",
        iconBase = "com/robotarmy/leap/RAlogo24.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "explorer", openAtStartup = true, position = 333)
@ActionID(category = "Window", id = "com.robotarmy.leap.LEAPTelemetryTopComponent")
@ActionReference(path = "Menu/Window" , position = 334 )
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_FooBarAction",
        preferredID = "LEAPTelemetryTopComponent"
)
@Messages({
    "CTL_FooBarAction=LEAPTelemetry",
    "CTL_FooBarTopComponent=LEAP Telemetry",
    "HINT_FooBarTopComponent=LEAP Telemetry Window"
})

@SuppressWarnings("serial")
public final class LEAPTelemetryTopComponent extends TopComponent implements ActionListener {
    private static final Logger LOGGER = Logger.getGlobal();

    //private final LeapHandler handler = new LeapHandler();
    private final Controller controller = new Controller();
    private final Universe universe;
    private final Timer timer;
    private static final int IDLE_WAIT = 250;  // 1000 = about a minute.
    private int idleTimeOut = IDLE_WAIT;

    private static final float X_RANGE = 200.0f;  // +/- range of leap raw values (-200 to +200)
    private static final float Y_RANGE = 130.0f;  // +/- range of leap raw values (-200 to +200)
    private static final float X_RATIO = 255.0f / (2 * X_RANGE);
    private static final float Y_RATIO = 255.0f / (2 * Y_RANGE);
    private static final float X_OFFSET = 80.0f;       // Offset the values for hands. They both can't take up the same space over the controller.
    
    
    private static final float Z_BOTTOM = 100.0f;  //  Lowest point of the hand.
    private static final float Z_TOP = 500.0f;     // Highest position of hand.
    private static final float Z_RANGE = Z_TOP - Z_BOTTOM;
    private static final float Z_RATIO = 255.0f / Z_RANGE;

    private final DeltaRobot[][] fingerBotL = {
        {   new DeltaRobot(15),new DeltaRobot(16),new DeltaRobot(40),
            new DeltaRobot(41),new DeltaRobot(39),new DeltaRobot(38),
            new DeltaRobot(47),new DeltaRobot(46),new DeltaRobot(49)    }, // Thumb
        {   new DeltaRobot(17),new DeltaRobot(18),new DeltaRobot(19),
            new DeltaRobot(20),new DeltaRobot(21),new DeltaRobot(22),
            new DeltaRobot(23),new DeltaRobot(24),new DeltaRobot(27),
            new DeltaRobot(28)                                          },
        {   new DeltaRobot(1), new DeltaRobot(2), new DeltaRobot(3),
            new DeltaRobot(6), new DeltaRobot(7), new DeltaRobot(25),
            new DeltaRobot(26),new DeltaRobot(29),new DeltaRobot(30)    },
        {   new DeltaRobot(4), new DeltaRobot(5), new DeltaRobot(8),
            new DeltaRobot(9), new DeltaRobot(31),new DeltaRobot(32),
            new DeltaRobot(33),new DeltaRobot(34),new DeltaRobot(35)    },
        {   new DeltaRobot(10),new DeltaRobot(11),new DeltaRobot(12),
            new DeltaRobot(13),new DeltaRobot(14)                       } // Pinky
    };
    
    private final DeltaRobot[][] fingerBotR = {
        {   new DeltaRobot(36),new DeltaRobot(42),new DeltaRobot(54),
            new DeltaRobot(37),new DeltaRobot(53),new DeltaRobot(48),
            new DeltaRobot(43),new DeltaRobot(44),new DeltaRobot(45)    }, // Thumb
        {   new DeltaRobot(50),new DeltaRobot(51),new DeltaRobot(52),
            new DeltaRobot(55),new DeltaRobot(56),new DeltaRobot(59),
            new DeltaRobot(60),new DeltaRobot(61),new DeltaRobot(62),
            new DeltaRobot(63)  },
        {   new DeltaRobot(73),new DeltaRobot(74),new DeltaRobot(75),
            new DeltaRobot(76),new DeltaRobot(77),new DeltaRobot(57),
            new DeltaRobot(58),new DeltaRobot(67),new DeltaRobot(68)    },
        {   new DeltaRobot(71),new DeltaRobot(72),new DeltaRobot(81),
            new DeltaRobot(82),new DeltaRobot(64),new DeltaRobot(65),
            new DeltaRobot(66),new DeltaRobot(69),new DeltaRobot(70)    },
        {   new DeltaRobot(78),new DeltaRobot(79),new DeltaRobot(80),
            new DeltaRobot(83),new DeltaRobot(84)                       } // Pinky
    };
    
    private boolean leapConnected = false;
    private InputOutput io = null;
    
    //private float fingerAmplitutde = 10.0f;
    
    private final IdleHandler idleHandler;

    public LEAPTelemetryTopComponent() {
        initComponents();
        setName(Bundle.CTL_FooBarTopComponent());
        setToolTipText(Bundle.HINT_FooBarTopComponent());
        //putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
        //putClientProperty(TopComponent.PROP_DRAGGING_DISABLED, Boolean.TRUE);
        //putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);

        upDownSlider.configure("Z Center:", 0, 600, 320);
        averageSlider.configure("XYZ Average:", 1, 20, 5);
        handPanelL.setLabel("Left Hand");
        handPanelR.setLabel("Right Hand");
        frameNumberPanel.setTitle("Frame ID");
        frameNumberPanel.setText("-------");
        timestampPanel.setTitle("Timestamp");
        timestampPanel.setText("---------");
        handsCountPanel.setTitle("# Hands");
        fingerCountPanel.setTitle("# Fingers");
        motionPanel.setTitle("Motion");

        // Moved to componentOpened()
        // Logging tab
        //io = IOProvider.getDefault().getIO("Log", false);
        //io.select();
        //io.getOut().println("Hello from standard out");
        //io.getErr().println("Hello from standard err");  //this text should appear in red

        //upDownLabel.setText(String.valueOf(upDownSlider.getValue()));
        
        
        // DMX Universe
        universe = Universe.getUniverse(0);
        
        // Allow the app to get frames even if it doesn't have mouse focus.
        controller.setPolicy(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);

        timer = new Timer(30, this);
        timer.setInitialDelay(3000);
        timer.start();
        
        idleHandler = new IdleHandler(fingerBotL, fingerBotR);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        handPanelL = new com.robotarmy.leap.ui.HandPanel();
        handPanelR = new com.robotarmy.leap.ui.HandPanel();
        frameNumberPanel = new com.robotarmy.leap.ui.NumberPanel();
        timestampPanel = new com.robotarmy.leap.ui.NumberPanel();
        handsCountPanel = new com.robotarmy.leap.ui.NumberPanel();
        fingerCountPanel = new com.robotarmy.leap.ui.NumberPanel();
        averageSlider = new com.robotarmy.leap.ui.SliderWidgetPanel();
        upDownSlider = new com.robotarmy.leap.ui.SliderWidgetPanel();
        motionPanel = new com.robotarmy.leap.ui.BorderPanel();
        motionButton = new javax.swing.JToggleButton();

        jPanel1.setPreferredSize(new java.awt.Dimension(350, 630));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 63, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        handsCountPanel.setPreferredSize(new java.awt.Dimension(160, 50));

        fingerCountPanel.setPreferredSize(new java.awt.Dimension(160, 50));

        motionButton.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(motionButton, org.openide.util.NbBundle.getMessage(LEAPTelemetryTopComponent.class, "LEAPTelemetryTopComponent.motionButton.text")); // NOI18N
        motionButton.setToolTipText(org.openide.util.NbBundle.getMessage(LEAPTelemetryTopComponent.class, "LEAPTelemetryTopComponent.motionButton.toolTipText")); // NOI18N
        motionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                motionButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout motionPanelLayout = new javax.swing.GroupLayout(motionPanel);
        motionPanel.setLayout(motionPanelLayout);
        motionPanelLayout.setHorizontalGroup(
            motionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, motionPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(motionButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        motionPanelLayout.setVerticalGroup(
            motionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(motionButton)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(handsCountPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(frameNumberPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timestampPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fingerCountPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(441, 441, 441))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(handPanelL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(handPanelR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(averageSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(upDownSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addComponent(motionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(motionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(frameNumberPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timestampPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(handsCountPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fingerCountPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(handPanelR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(handPanelL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(upDownSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(averageSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void motionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_motionButtonActionPerformed
        if (motionButton.isSelected()) {
            LOGGER.config("[LEAP Telemetry] User enabled DMX to motors.\n");
        } else {
            LOGGER.config("[LEAP Telemetry] User disabled DMX to motors.\n");
        }
    }//GEN-LAST:event_motionButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.robotarmy.leap.ui.SliderWidgetPanel averageSlider;
    private com.robotarmy.leap.ui.NumberPanel fingerCountPanel;
    private com.robotarmy.leap.ui.NumberPanel frameNumberPanel;
    private com.robotarmy.leap.ui.HandPanel handPanelL;
    private com.robotarmy.leap.ui.HandPanel handPanelR;
    private com.robotarmy.leap.ui.NumberPanel handsCountPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton motionButton;
    private com.robotarmy.leap.ui.BorderPanel motionPanel;
    private com.robotarmy.leap.ui.NumberPanel timestampPanel;
    private com.robotarmy.leap.ui.SliderWidgetPanel upDownSlider;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        openLoggingTab();
    }

    @Override
    public void componentClosed() {
        //io.getOut().close();
        //io.getErr().close();
        
        closeLoggingTab();
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

    public void leapInit(Controller controller) {
    }

    public void leapConnected(Controller controller) {
        leapConnected = true;
    }

    public void leapDisconnected(Controller controller) {
        leapConnected = false;
    }

    public void leapExited(Controller controller) {
    }

    public void leapFrame(Controller controller) {
        Frame frame = controller.frame();
        frameNumberPanel.setValue(frame.id());
        timestampPanel.setValue(frame.timestamp());
        handsCountPanel.setValue(frame.hands().count());
        fingerCountPanel.setValue(frame.fingers().count());

        if (!frame.hands().isEmpty()) {
            idleTimeOut = IDLE_WAIT; // Reset the idle countdown.
            idleHandler.clear();
            IdleState.getInstance().setColor(Color.darkGray);
            
            Iterator<Hand> hands = frame.hands().iterator();
            boolean leftDone = false;
            boolean rightDone = false;

            while (hands.hasNext()) {
                Hand h = hands.next();
                if (h.isLeft() && !leftDone) {
                    leftDone = true;  // There can be only one!
                    doBots(h, fingerBotL);
                    Vector avh = averageHandPosition(h);
                    handPanelL.update(calcX(avh.getX()), calcY(avh.getZ()), calcZ(avh.getY()));
                }

                if (h.isRight() && !rightDone) {
                    rightDone = true;  // There can be only one!
                    doBots(h, fingerBotR);
                    Vector avh = averageHandPosition(h);
                    handPanelR.update(calcX(avh.getX()), calcY(avh.getZ()), calcZ(avh.getY()));
                }
            }
            
            if ( !leftDone ) {// || !rightDone ) {
                decay(fingerBotL);
            }
            if ( !rightDone ) {
                decay(fingerBotR);
            }
            
            update();
            
        } else {
            idleTimeOut--;
            decay(fingerBotL);
            decay(fingerBotR);
            
            if ( idleTimeOut < 0 ) {
                idleTimeOut = -1;
                idleHandler.update();
            } else if ( idleTimeOut == 0) {
                //trigger new random mode
                idleHandler.setMode(IdleHandler.MODE_RANDOM);
                //io.getOut().println("[LEAP Telemetry] Added new RANDOM Mode Idle Handler.");
                LOGGER.config("[LEAP Telemetry] Added new RANDOM Mode Idle Handler.\n");
                IdleState.getInstance().setColor(Color.green);
            }
        }
        updateDMX();  // Send all current values out to DMX.

        /*        if (!frame.gestures().isEmpty()) {
         Iterator<Gesture> gestures = frame.gestures().iterator();
         messageArea.append("Gesture: ");
         int swipes = 0;
         while (gestures.hasNext()) {
         Gesture g = gestures.next();
         switch (g.type()) {
         case TYPE_CIRCLE:
         messageArea.append("Circle");
         break;
         case TYPE_KEY_TAP:
         messageArea.append("Key Tap");
         break;
         case TYPE_SCREEN_TAP:
         messageArea.append("Screen Tap");
         break;
         case TYPE_SWIPE:
         messageArea.append("Swipe");
         //bot_x += swipeSlider.getValue();
         //SwipeGesture sg = new SwipeGesture(g);
         //bot_x += sg.direction().getX() * 8.0f;
         break;
         case TYPE_INVALID:
         messageArea.append("INVALID");
         break;
         }
         if (gestures.hasNext()) {
         messageArea.append(" :: ");
         }
         messageScrollPane.getVerticalScrollBar().setValue(
         messageScrollPane.getVerticalScrollBar().getMaximum());
         }
         messageArea.append("\n");

         } else {
         // Decay back to normal
         //bot_x -= (handLx - DEF_XY) / 20.0f;
         }
         */
    }

    private Vector averageHandPosition(Hand h) {
        int count = 0;
        Vector average = new Vector();
        for (int i = 0; i < averageSlider.getValue(); i++) {
            Hand handFromFrame = controller.frame(i).hand(h.id());
            if (handFromFrame.isValid()) {
                average = average.plus(handFromFrame.palmPosition());
                count++;
            }
        }
        if (count > 0) {
            average = average.divide(count);
        }
        if (h.isLeft()) {
            average.setX(average.getX() + X_OFFSET);
        } else {
            average.setX(average.getX() - X_OFFSET);
        }
        return average;
    }

    private Vector averageFingerPosition(Hand h, int fid) {
        int count = 0;
        Vector average = new Vector();
        for (int i = 0; i < averageSlider.getValue(); i++) {
            //Hand handFromFrame = controller.frame(i). hand(h.id());
            Finger ff = controller.frame(i).finger(fid);
            if (ff.isValid()) {
                average = average.plus(ff.tipPosition());
                count++;
            }
        }
        if (count > 0) {
            average = average.divide(count);
        }
        if (h.isLeft()) {
            average.setX(average.getX() + X_OFFSET);
        } else {
            average.setX(average.getX() - X_OFFSET);
        }
        return average;
    }

    private int calcX(float v) {
        return (int) ((v + X_RANGE) * X_RATIO);
    }

    private int calcZ(float v) {
        return (int) ((v - Z_BOTTOM) * Z_RATIO);
    }

    private int calcY(float v) {
        return (int) ((v + Y_RANGE) * Y_RATIO);
    }

    private void doBots(Hand h, DeltaRobot[][] fingerBots) {
        int[] fingZ = new int[5];

        // Finger tracked bots
        for (int i = 0; i < h.fingers().count(); i++) {
            Finger f = h.fingers().get(i);
            Vector fingerPos = averageFingerPosition(h, f.id());
            for (DeltaRobot fingerBot : fingerBots[i]) {
                fingerBot.setTarget(                
                    (255-calcZ(h.palmPosition().getY()))+(int)( (h.palmPosition().getY()-fingerPos.getY())*3.0  ), // Robot Up-Down
                    calcY(fingerPos.getX()), // Robot Right-Left
                    calcX(fingerPos.getZ()), // Robot Forward-Back
                    3
                );
                
//                fingerBot.x = (255-calcZ(h.palmPosition().getY()))+(int)( (h.palmPosition().getY()-fingerPos.getY())*3.0  ); // Robot Up-Down
//                fingerBot.y = calcY(fingerPos.getX()); // Robot Right-Left
//                fingerBot.z = calcX(fingerPos.getZ()); // Robot Forward-Back
            }
            fingZ[i] = calcZ(fingerPos.getY());

        }

        if (h.isLeft()) {
            handPanelL.updateFingers(fingZ, HandPanel.DIR_LEFT);
        } else {
            handPanelR.updateFingers(fingZ, HandPanel.DIR_RIGHT);
        }
    }

    // Called by Timer at about 30FPS
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (controller.isConnected()) {
            if (!leapConnected) {
                leapConnected(controller);
            }
//            leapFrame(controller);
//            IdleState.getInstance().setColor(Color.darkGray);
//            repaint();
        } else {
            leapDisconnected(controller);
            //IdleState.getInstance().setColor(Color.green);
            //repaint();
//            if ( IdleState.getInstance().isSelected() ) {
//                idleHandler.update();
//                updateDMX();
//            }
        }
        
        leapFrame(controller);
        
    }

    private void decay(DeltaRobot[][] bots) {
        for (DeltaRobot[] fingerBotLCOL : bots) {
            for (DeltaRobot bot : fingerBotLCOL) {
                bot.decay();
            }
        }
//        for (DeltaRobot[] fingerBotRCOL : fingerBotR) {
//            for (DeltaRobot bot : fingerBotRCOL) {
//                bot.decay();
//            }
//        }
    }

    private void update() {
        for (DeltaRobot[] fingerBotLCOL : fingerBotL) {
            for (DeltaRobot bot : fingerBotLCOL) {
                bot.update();
            }
        }
        for (DeltaRobot[] fingerBotRCOL : fingerBotR) {
            for (DeltaRobot bot : fingerBotRCOL) {
                bot.update();
            }
        }        
    }
    
    private void updateDMX() {
        for (DeltaRobot[] fingerBotLCOL : fingerBotL) {
            for (DeltaRobot bot : fingerBotLCOL) {
                setBotDMX(bot.channel, (int)bot.x, (int)bot.y, (int)bot.z);
            }
        }
        for (DeltaRobot[] fingerBotRCOL : fingerBotR) {
            for (DeltaRobot bot : fingerBotRCOL) {
                setBotDMX(bot.channel, (int)bot.x, (int)bot.y, (int)bot.z);
            }
        }        
    }
    
    private void setBotDMX(int n, int x, int y, int z) {
        if (motionButton.isSelected()) {
            universe.write((n * 6) + 3 ,
                    //255 - clamp(x), 255 - clamp(y), 255 - clamp(z), // RGB
                    255 - clamp(x), 255 - clamp(y), 255 - clamp(z) // Position
            );
        }
    }

    private int clamp(int n) {
        if (n > 253) {
            return 253;
        }
        if (n < 3) {
            return 3;
        }
        return n;
    }

   private void openLoggingTab() {
        if (io != null) {
            return;
        }
        //LOGGER.setLevel(MySettings.getLogLevel());
        LOGGER.setLevel(Level.FINEST);
        io = IOProvider.getDefault().getIO("LightPlay Log", true);
        io.select();

        final LogOutputHandler handler = new LogOutputHandler(io);
        LOGGER.addHandler(handler);

        // Don't send log to stdout(parent).
        LOGGER.setUseParentHandlers(false);
    }

    private void closeLoggingTab() {
        if (io == null) {
            return;
        }

        io.getErr().flush();
        io.getOut().flush();

        LOGGER.setUseParentHandlers(true);

        io.closeInputOutput();
        io = null;
    }}
