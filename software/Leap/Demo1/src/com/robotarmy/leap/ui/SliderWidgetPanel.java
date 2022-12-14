/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.leap.ui;

import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author mark
 */
@SuppressWarnings("serial")
public class SliderWidgetPanel extends javax.swing.JPanel implements ChangeListener {

    /**
     * Creates new form SliderWidgetPanel
     */
    public SliderWidgetPanel() {
        initComponents();
        slider.addChangeListener(this);
    }

    public void configure(String name, int min, int max, int value) {
        slider.setMinimum(min);
        slider.setMaximum(max);
        slider.setValue(value);
        setLabel(name);
        doLayout();
        //slider.addChangeListener(l);
    }

    public void setLabel( String text ) {
        ((TitledBorder)borderPanel.getBorder()).setTitle(text);
    }
    
    public JSlider getSlider() {
        return slider;
    }
    
    public int getValue() {
        return slider.getValue();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        namePanel = new javax.swing.JPanel();
        borderPanel = new com.robotarmy.leap.ui.BorderPanel();
        slider = new javax.swing.JSlider();
        valueLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(338, 57));

        namePanel.setPreferredSize(new java.awt.Dimension(100, 32));

        javax.swing.GroupLayout namePanelLayout = new javax.swing.GroupLayout(namePanel);
        namePanel.setLayout(namePanelLayout);
        namePanelLayout.setHorizontalGroup(
            namePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        namePanelLayout.setVerticalGroup(
            namePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        borderPanel.setPreferredSize(new java.awt.Dimension(338, 57));

        slider.setMaximum(10);
        slider.setSnapToTicks(true);
        slider.setValue(5);

        valueLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        valueLabel.setForeground(new java.awt.Color(102, 102, 102));
        org.openide.awt.Mnemonics.setLocalizedText(valueLabel, org.openide.util.NbBundle.getMessage(SliderWidgetPanel.class, "SliderWidgetPanel.valueLabel.text")); // NOI18N

        javax.swing.GroupLayout borderPanelLayout = new javax.swing.GroupLayout(borderPanel);
        borderPanel.setLayout(borderPanelLayout);
        borderPanelLayout.setHorizontalGroup(
            borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borderPanelLayout.createSequentialGroup()
                .addComponent(slider, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(valueLabel))
        );
        borderPanelLayout.setVerticalGroup(
            borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, borderPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, borderPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(valueLabel)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(borderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(namePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(namePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(borderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.robotarmy.leap.ui.BorderPanel borderPanel;
    private javax.swing.JPanel namePanel;
    private javax.swing.JSlider slider;
    private javax.swing.JLabel valueLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void stateChanged(ChangeEvent ce) {
        valueLabel.setText(String.valueOf(slider.getValue()));
    }
}
