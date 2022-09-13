/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.leap.idlemode;

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
        id = "com.robotarmy.leap.idlemode.IdleToggleAction"
)
@ActionRegistration(
        displayName = "#CTL_IdleToggleAction"
)
@ActionReference(path = "Toolbars/File", position = 10)
@Messages("CTL_IdleToggleAction=Idle Mode")

@SuppressWarnings("serial")
public final class IdleToggleAction extends AbstractAction  implements Presenter.Toolbar {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
    }

    @Override
    public Component getToolbarPresenter() {
        return new IdleEnablePanel();
    }
}
