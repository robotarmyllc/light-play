/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.actions;

import com.robotarmy.flow.node.FlowNode;
import com.robotarmy.flow.object.DeltaObject;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import org.openide.util.Utilities;

/**
 *
 * @author mark
 */
public class NewDeltaAction extends AbstractAction {
    protected static final Logger LOG = Logger.getLogger("NewDeltaAction");
    private static final long serialVersionUID = 1L;

    public NewDeltaAction() {
        LOG.setLevel(Level.FINEST);
        putValue(NAME, "New Delta");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FlowNode node = Utilities.actionsGlobalContext().lookup(FlowNode.class);
        DeltaObject o = new DeltaObject(node.getObject());
        LOG.log(Level.FINEST, "Action Performed: {0}", e.getSource().toString());
    }
}
