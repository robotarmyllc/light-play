/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.actions;

import com.robotarmy.flow.node.FlowNode;
import com.robotarmy.flow.object.PaletteObject;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import org.openide.util.Utilities;

/**
 *
 * @author mark
 */
public class NewPaletteAction extends AbstractAction {
    protected static final Logger LOG = Logger.getLogger("NewPaletteAction");
    private static final long serialVersionUID = 1L;

    public NewPaletteAction() {
        LOG.setLevel(Level.FINEST);
        putValue(NAME, "New Palette");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FlowNode node = Utilities.actionsGlobalContext().lookup(FlowNode.class);
        PaletteObject o = new PaletteObject(node.getObject());
        //LOG.finest("Action Performed: " + e.getSource().toString());
        //Event obj = getLookup().lookup(Event.class);
        //JOptionPane.showMessageDialog(null, "Hello from " + obj);
    }
}
