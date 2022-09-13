/*
 *  Flow -- New Project Action
 *
 *  Copyright 2015 Robot Army LLC
 */
package com.robotarmy.flow.actions;

import com.robotarmy.flow.explorer.ExplorerWindowTopComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

@ActionID(
        category = "File",
        id = "com.robotarmy.flow.actions.NewProjectAction"
)
@ActionRegistration(
        iconBase = "com/robotarmy/flow/icon/NewProject.png",
        displayName = "#CTL_NewProjectAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1300),
    @ActionReference(path = "Toolbars/File", position = 300)
})
@Messages("CTL_NewProjectAction=New Project")
public final class NewProjectAction implements ActionListener {
    private static final Logger LOG = Logger.getLogger("NewProjectAction");

    @Override
    public void actionPerformed(ActionEvent e) {
        LOG.warning("Project New Clicked.");
        
        // Find the ExplorerTopComponent and tell it to make a new project.
        TopComponent tc = WindowManager.getDefault().findTopComponent("ExplorerWindowTopComponent");
        if( tc != null ) {
            ExplorerWindowTopComponent explorer = (ExplorerWindowTopComponent) tc;
            explorer.invokeNewProject();
        }
    }
}
