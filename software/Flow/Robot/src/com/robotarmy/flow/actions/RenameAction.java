/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.actions;

import com.robotarmy.flow.node.FlowNode;
import com.robotarmy.flow.object.AFlowObject;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Utilities;

/**
 *
 * @author mark
 */
public class RenameAction extends AbstractAction {

    protected static final Logger LOG = Logger.getLogger("RenameAction");
    private static final long serialVersionUID = 1L;

    public RenameAction() {
        LOG.setLevel(Level.FINEST);
        putValue(NAME, "Rename...");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FlowNode node = Utilities.actionsGlobalContext().lookup(FlowNode.class);
        String label = "Name: ";
        AFlowObject o = node.getObject();
        String title = o.getHtmlName();

        NotifyDescriptor.InputLine input = new NotifyDescriptor.InputLine(label, title);
        input.setInputText(o.getName()); // specify a default name
        Object result = DialogDisplayer.getDefault().notify(input);
        if (result != NotifyDescriptor.OK_OPTION) {
            return;
        }

        o.setName(input.getInputText());
    }
}
