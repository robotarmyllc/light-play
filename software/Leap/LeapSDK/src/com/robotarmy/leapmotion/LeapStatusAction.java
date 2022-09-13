/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.leapmotion;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.Presenter;

@ActionID(
        category = "System",
        id = "com.robotarmy.leapmotion.LeapStatusAction"
)
@ActionRegistration(
        iconBase = "com/robotarmy/leapmotion/DMX-logo.png",
        displayName = "#CTL_LeapStatusAction"
)
@ActionReference(path = "Toolbars/File", position = -100)
@Messages("CTL_LeapStatusAction=Leap Status")
public final class LeapStatusAction  extends AbstractAction implements Presenter.Toolbar {

    @Override
    public void actionPerformed(ActionEvent e) {
        // delegated to getToolbarPresenter()
    }
    
    @Override
    public Component getToolbarPresenter() {
        return new LeapConnectionIndicatorPanel();
    }
}
