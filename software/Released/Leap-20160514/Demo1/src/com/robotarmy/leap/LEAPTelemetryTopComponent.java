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
import com.robotarmy.leap.ui.HandPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
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
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "com.robotarmy.leap.LEAPTelemetryTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
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

    //private final LeapHandler handler = new LeapHandler();
    private final Controller controller = new Controller();
    private final Universe universe;
    private final Timer timer;

    private static final float X_RANGE = 200.0f;  // +/- range of leap raw values (-200 to +200)
    private static final float Y_RANGE = 130.0f;  // +/- range of leap raw values (-200 to +200)
    private static final float X_RATIO = 255.0f / (2 * X_RANGE);
    private static final float Y_RATIO = 255.0f / (2 * Y_RANGE);
    private static final float X_OFFSET = 80.0f;       // Offset the values for hands. They both can't take up the same space over the controller.
    
    
    private static final float Z_BOTTOM = 100.0f;  //  Lowest point of the hand.
    private static final float Z_TOP = 500.0f;     // Highest position of hand.
    private static final float Z_RANGE = Z_TOP - Z_BOTTOM;
    private static final float Z_RATIO = 255.0f / Z_RANGE;

    private final int[][] fingerBotL = {
        {15,16,40,41,39,38,47,46,49}, // Thumb
        {17,18,19,20,21,22,23,24,27,28},
        {1,2,3,6,7,25,26,29,30},
        {4,5,8,9,31,32,33,34,35},
        {10,11,12,13,14} // Pinky
    };
    private final int[][] fingerBotR = {
        {36,42,54,37,53,48,43,44,45}, // Thumb
        {50,51,52,55,56,59,60,61,62,63},
        {73,74,75,76,77,57,58,67,68},
        {71,72,81,82,64,65,66,69,70},
        {78,79,80,83,84} // Pinky
    };

//    private final int[] normalBotL = {46, 31};
//    private final int[] normalBotR = {43, 49, 45, 68};
    private final int[] normalBotL = {};
    private final int[] normalBotR = {};
    
    private boolean leapConnected = false;
    private final InputOutput io;
    
    private int leftNormalized = 0;
    private int rightNormalized = 0;

    private final Vector minVals = new Vector(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
    private final Vector maxVals = new Vector(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
    private float fingerAmplitutde = 10.0f;

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

        // Logging tab
        io = IOProvider.getDefault().getIO("Log", false);
        io.select();
        //io.getOut().println("Hello from standard out");
        //io.getErr().println("Hello from standard err");  //this text should appear in red

//        OutputWriter writer;
//        InputOutput io = IOProvider.getDefault().getIO("Hello Output", false);
//        writer = io.getOut();
//        writer.println("hello world");
        //upDownLabel.setText(String.valueOf(upDownSlider.getValue()));
        // DMX Universe
        universe = Universe.getUniverse(0);

        timer = new Timer(30, this);
        timer.setInitialDelay(3000);
        timer.start();

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
                .addContainerGap(54, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void motionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_motionButtonActionPerformed
        if (motionButton.isSelected()) {
            io.getOut().println("[LEAP Telemetry] User enabled DMX to motors.");
        } else {
            io.getOut().println("[LEAP Telemetry] User disabled DMX to motors.");
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
    }

    @Override
    public void componentClosed() {
        io.getOut().close();
        io.getErr().close();
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
            Iterator<Hand> hands = frame.hands().iterator();
            boolean leftDone = false;
            boolean rightDone = false;

            while (hands.hasNext()) {
                Hand h = hands.next();
                if (h.isLeft() && !leftDone) {
                    leftDone = true;  // There can be only one!
                    Vector avh = averageHandPosition(h);
                    doBots(h, avh, fingerBotL, normalBotL);
                    handPanelL.update(calcX(avh.getX()), calcY(avh.getZ()), calcZ(avh.getY()));
                }

                if (h.isRight() && !rightDone) {
                    rightDone = true;  // There can be only one!
                    Vector avh = averageHandPosition(h);
                    doBots(h, avh, fingerBotR, normalBotR);
                    handPanelR.update(calcX(avh.getX()), calcY(avh.getZ()), calcZ(avh.getY()));
                }
            }
        }

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

    private void doBots(Hand h, Vector average, int[][] fingerBots, int[] palmBots) {
/*   We don't do palm any more
        // Palm tracked bots
        for (int i = 0; i < palmBots.length; i++) {
            setBot(palmBots[i],
                    calcX(average.getZ()),   // Robot Forward-Back
                    calcY(average.getX()),   // Robot Right-Left
                    calcZ(average.getY())    // Robot Up-Down
            );
        }
*/

        int[] fingZ = new int[5];

        // Finger tracked bots
        for (int i = 0; i < h.fingers().count(); i++) {
            Finger f = h.fingers().get(i);
            Vector fingerPos = averageFingerPosition(h, f.id());
            for (int j = 0; j < fingerBots[i].length; j++) {
//                setBot(fingerBots[i][j],
//                        calcX(fingerPos.getZ()),  // Robot Forward-Back
//                        calcY(fingerPos.getX()),  // Robot Right-Left
//                        calcZ(fingerPos.getY())   // Robot Up-Down
//                );

//                setBot( fingerBots[i][j],
//                        255-calcZ(fingerPos.getY()),   // Robot Up-Down
//                        calcY(fingerPos.getX()),  // Robot Right-Left
//                        calcX(fingerPos.getZ())  // Robot Forward-Back
//                );
                setBot( fingerBots[i][j],
                        (255-calcZ(h.palmPosition().getY()))+(int)( (h.palmPosition().getY()-fingerPos.getY())*3.0  ),   // Robot Up-Down
                        calcY(fingerPos.getX()),  // Robot Right-Left
                        calcX(fingerPos.getZ())  // Robot Forward-Back
                );

            }
            fingZ[i] = calcZ(fingerPos.getY());

//            if (i == 0) {
//                io.getOut().println("X:" + average.getX() + "\tY:" + average.getY() + "\tZ:" + average.getZ());
//                messageScrollPane.getVerticalScrollBar().setValue(
//                        messageScrollPane.getVerticalScrollBar().getMaximum());
//            }
        }

        if (h.isLeft()) {
            handPanelL.updateFingers(fingZ, HandPanel.DIR_LEFT);
        } else {
            handPanelR.updateFingers(fingZ, HandPanel.DIR_RIGHT);
        }
    }

    private void setBot(int n, int x, int y, int z) {
        if (motionButton.isSelected()) {
            universe.write((n * 6)+3,
//                    255 - clamp(x), 255 - clamp(y), 255 - clamp(z), // Position
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

    // Called by Timer at about 30FPS
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (controller.isConnected()) {
            if (!leapConnected) {
                leapConnected = true;  // Don't need to track this anymore
                leapConnected(controller);
            }
            leapFrame(controller);
        } else {
            leapConnected = false;
            leapDisconnected(controller);
        }
    }

}
