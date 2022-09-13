/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.dmx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.NbPreferences;

/**
 *
 * @author mark
 */
@Messages({
    "LBL_DmxConnectPanel_Connect=Connect",
    "LBL_DmxConnectPanel_Disconnect=Disconnect"
})

public class DmxConnectPanel extends javax.swing.JPanel {
    private static final Logger LOG = Logger.getLogger("DMX");
    private static final long serialVersionUID = 18492642321L;
    private DmxManager dmx = null;
    //private final static String PROPNAME_DEFAULT_PORT = "defaultPort";
    private String defaultPort = "none";
    
    private int universe;
    private boolean parkable = true;

    /**
     * Creates new form NewJPanel
     */
    public DmxConnectPanel(int universe) {
        this.universe = universe;
        initComponents();
        
        // Rescan for DMX devices.
        updatePortSelector();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        portSelectorBox = new javax.swing.JComboBox<>();
        connectButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        portSelectorBox.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        portSelectorBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Port Name"})
        );
        portSelectorBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portSelectorBoxActionPerformed(evt);
            }
        });

        connectButton.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(connectButton, org.openide.util.NbBundle.getMessage(DmxConnectPanel.class, "DmxConnectPanel.connectButton.text")); // NOI18N
        connectButton.setEnabled(false);
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(DmxConnectPanel.class, "DmxConnectPanel.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(portSelectorBox, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(portSelectorBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(connectButton)
                .addComponent(jLabel1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void portSelectorBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portSelectorBoxActionPerformed
        if (portSelectorBox.getItemCount() == 0) {
            return;
        }
        // There are items.
        int selectedIndex = portSelectorBox.getSelectedIndex();
        if (selectedIndex == 0) {
            // No port selected
            connectButton.setEnabled(false);
            dmx = null;
        } else if (selectedIndex == portSelectorBox.getItemCount() - 1) {
            //Rescan selected
            connectButton.setEnabled(false);
            updatePortSelector();
        } else if (portSelectorBox.getItemCount() > 2) {
            connectButton.setEnabled(true);
            defaultPort = (String) portSelectorBox.getSelectedItem();
            String lastPort;
            switch (universe) {
                case 0:
                    lastPort = Bundle.PREF_LastPortUsed0();
                    break;
                case 1:
                    lastPort = Bundle.PREF_LastPortUsed1();
                    break;
                default:
                    lastPort = "none";
                    break;
            }
            NbPreferences.forModule(SerialPortSettingsPanel.class).put(lastPort, defaultPort);
            String[] s = defaultPort.split(" ");
            dmx = DmxManager.getInstance(s[0]);
            dmx.setUniverse(universe);
        }
    }//GEN-LAST:event_portSelectorBoxActionPerformed

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        ImageIcon icon = createImageIcon("PortScan.png", "Port scan icon");
        if (dmx == null) {
            connectButton.setText(Bundle.LBL_DmxConnectPanel_Connect());
            connectButton.setEnabled(false);
            return;
        }

        if (!dmx.hasConnection()) {
            // User pressed button when it was 'Connect'
            String device = (String) portSelectorBox.getSelectedItem();
            device = device.substring(0, device.indexOf(' '));
            if (!device.startsWith("FT")) {
                LOG.log(Level.WARNING, "Connecting to suspicious device [{0}] on Universe {1}", new Object[]{device,universe});
            }

            try {
                dmx.connect();
                connectButton.setEnabled(true);
                connectButton.setText(Bundle.LBL_DmxConnectPanel_Disconnect());
                NotificationDisplayer.getDefault().notify("DMX Connect", icon, "Connected to " + device + " on Universe:" + universe + ".", null);
            } catch (DmxConnectException ex) {
                Exceptions.printStackTrace(ex);
                NotificationDisplayer.getDefault().notify("DMX Connect", icon, "Failed connecting to " + device + " on Universe " + universe + ".", null);
            }
        } else {
            // User pressed button when it was 'Disconnect'
            try {
                if ( isParkable() ) {
                    park();
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Exceptions.printStackTrace(ex);
                }
                dmx.disconnect();
                
                connectButton.setText(Bundle.LBL_DmxConnectPanel_Connect());
                NotificationDisplayer.getDefault().notify("DMX Disconnect", icon, dmx.getSerial() + " Disconnected OK", null);
            } catch (DmxDisconnectException ex) {
                NotificationDisplayer.getDefault().notify("DMX Disconnect", icon, dmx.getSerial() + "Failed during disconnect.", null);
            }
        }
    }//GEN-LAST:event_connectButtonActionPerformed

    private void updatePortSelector() {
        SwingWorker<String[], Void> worker = new SwingWorker<String[], Void>() {
            @Override
            @SuppressWarnings("unchecked")
            public String[] doInBackground() {
                connectButton.setEnabled(false);
                portSelectorBox.removeAllItems();
                portSelectorBox.addItem("Scanning");
                portSelectorBox.setEnabled(false);
                return DmxManager.getSerials();
            }

            @Override
            @SuppressWarnings("unchecked")
            public void done() {
                connectButton.setEnabled(false);
                ImageIcon icon = createImageIcon("PortScan.png", "Port scan icon");
                portSelectorBox.removeAllItems();
                
                String lastPort;
                switch (universe) {
                    case 0:
                        lastPort = Bundle.PREF_LastPortUsed0();
                        break;
                    case 1:
                        lastPort = Bundle.PREF_LastPortUsed1();
                        break;
                    default:
                        lastPort = "none";
                        break;
                }
                defaultPort = NbPreferences.forModule(SerialPortSettingsPanel.class).get(lastPort, "none");
                
                int selected = 0;

                try {
                    String[] list = get();
                    NotificationDisplayer.getDefault().notify("Port Scan", icon, "Found " + list.length + " devices.", null);
                    portSelectorBox.addItem("No Port Selected");
                    LOG.warning(Arrays.toString(list));
                    for (String item : list) {
                        LOG.log(Level.WARNING, "Device: {0}\n", item);
                        String[] split = item.split(":");
                        if (split.length == 3) {
                            String s = split[1] + " (" + split[2] + ")";
                            portSelectorBox.addItem(s);
                            if (defaultPort.equals(s)) {
                                selected = portSelectorBox.getItemCount() - 1;
                                dmx = DmxManager.getInstance(defaultPort);
                                dmx.setUniverse(0);

                            }
                        } else {
                            NotificationDisplayer.getDefault().notify(
                                    "Port Scan", icon,
                                    "Could not get device serial number.\nPerhaps D2XX drivers are disabled?", null);
                            break;
                        }
                    }
                                       
                    portSelectorBox.addItem("Re-scan");
                    portSelectorBox.setEnabled(true);
                    
                    portSelectorBox.setSelectedIndex(selected);
                    // If AutoConnect then connect.
                    
                    // TODO.   Measuer the time it takes for both scans to complete and use that as a base for delay.
                    boolean autoConnect = NbPreferences.forModule(SerialPortSettingsPanel.class).getBoolean(Bundle.PREF_AutoConnect(), true);
                    if (autoConnect) {
                        Timer timer = new Timer((int) ((Math.random()*2000)+10000), new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                connectButtonActionPerformed(new ActionEvent(this, 0, "AutoConnect"));
                            }
                        });
                        timer.setRepeats(false);
                        timer.start(); 
                    }
                    
                } catch (InterruptedException | ExecutionException ex) {
                    portSelectorBox.addItem("Scan Error!");
                    //selected = 0;
                    Exceptions.printStackTrace(ex);
                }
            }
        };

        worker.execute();
    }
    
    protected ImageIcon createImageIcon(String path,
            String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connectButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JComboBox<String> portSelectorBox;
    // End of variables declaration//GEN-END:variables

    private void park() {
        for ( int i=5; i< (512-6); i+=6) {
            dmx.write(i,   30);
            dmx.write(i+1, 30);
            dmx.write(i+2, 0);
            dmx.write(i+3, 127);  
            dmx.write(i+4, 127);
            dmx.write(i+5, 240);
        }
    }

    /**
     * @return the parkable
     */
    public boolean isParkable() {
        return parkable;
    }

    /**
     * @param parkable the parkable to set
     */
    public void setParkable(boolean parkable) {
        this.parkable = parkable;
    }
}