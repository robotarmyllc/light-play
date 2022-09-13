/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.SwingUtilities;
import org.openide.actions.FileSystemAction;
import org.openide.actions.ToolsAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

@ActionID(
        category = "File",
        id = "com.robotarmy.flow.actions.OpenProjectAction"
)
@ActionRegistration(
        iconBase = "com/robotarmy/flow/icon/OpenProject.png",
        displayName = "#CTL_OpenProjectAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1301, separatorAfter = 1350),
    @ActionReference(path = "Toolbars/File", position = 301)
})
@Messages("CTL_OpenProjectAction=Open Project")
public final class OpenProjectAction implements ActionListener {

    private static final Logger LOG = Logger.getLogger("NewProjectAction");

//     https://blogs.oracle.com/geertjan/entry/open_file_action   
//     https://blogs.oracle.com/geertjan/entry/loosely_coupled_open_action 
    @Override
    public void actionPerformed(ActionEvent e) {
        LOG.setLevel(Level.FINEST);
        LOG.warning("Project Load Clicked.");
//        // Find the ExplorerTopComponent and tell it to make a new project.
//        TopComponent tc = WindowManager.getDefault().findTopComponent("ExplorerWindowTopComponent");
//        if( tc != null ) {
//            ExplorerWindowTopComponent explorer = (ExplorerWindowTopComponent) tc;
//            explorer.invokeOpenProject();
//            /*
//            */
//        }
//The default dir to use if no value is stored
       
        File home = new File(System.getProperty("user.home"));
        //Now build a file chooser and invoke the dialog in one line of code
        //"user-dir" is our unique key
        final File toAdd = new FileChooserBuilder("user-dir").setTitle("Open File").
                setDefaultWorkingDirectory(home).setApproveText("Open").showOpenDialog();
        //Result will be null if the user clicked cancel or closed the dialog w/o OK
        if (toAdd != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        open(toAdd);
                    } catch (DataObjectNotFoundException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            });
        }
    }
    
    void open( File f ) throws DataObjectNotFoundException {
        DataObject dataObject = DataObject.find(FileUtil.toFileObject(f));
        Node dataNode = dataObject.getNodeDelegate();        
        Action action = dataNode.getPreferredAction();
        if ((action != null)
                && !(action instanceof FileSystemAction)
                && !(action instanceof ToolsAction)) {
            if (LOG.isLoggable(Level.FINEST)) {
                LOG.log(Level.FINEST, " - using preferred action "            //NOI18N
                        + "(\"{0}\" - {1}) for opening the file",       //NOI18N
                        new Object[]{action.getValue(Action.NAME),
                            action.getClass().getName()});
            }

            if (action instanceof ContextAwareAction) {
                action = ((ContextAwareAction) action)
                  .createContextAwareInstance(dataNode.getLookup());
                if (LOG.isLoggable(Level.FINEST)) {
                    LOG.finest("    - it is a ContextAwareAction");     //NOI18N
                    LOG.log(Level.FINEST, "    - using a context-aware "      //NOI18N
                            + "instance instead (\"{0}\" - {1})",       //NOI18N
                            new Object[]{action.getValue(Action.NAME),
                                action.getClass().getName()});
                }
            }

            LOG.finest("   - will call action.actionPerformed(...)");   //NOI18N
            final Action a = action;
            final Node n = dataNode;
            WindowManager.getDefault().invokeWhenUIReady(new Runnable() {

                @Override
                public void run() {
                    LOG.finest("OpenProjectAction Node action performed.");
                    a.actionPerformed(new ActionEvent(n, 0, ""));
                }
            });   
        }
    }
}
