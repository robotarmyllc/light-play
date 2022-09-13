/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.dmx;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.Presenter;

/**
 *
 * @author mark
 */
@ActionID(
        category = "Edit",
        id = "com.robotarmy.dmx.DMXAction"
)
@ActionRegistration(
        //iconBase = "com/robotarmy/dmx/DMX-logo.png",
        displayName = "#CTL_DMXAction"
)
@ActionReference(path = "Toolbars/File", position = 0)
@Messages("CTL_DMXAction=DMX")
@SuppressWarnings("serial")
public final class DMXAction extends AbstractAction implements Presenter.Toolbar {

    @Override
    public void actionPerformed(ActionEvent e) {
        // delegated to getToolbarPresenter()
    }

    @Override
    public Component getToolbarPresenter() {
        return new DmxConnectPanel();
    }
}
