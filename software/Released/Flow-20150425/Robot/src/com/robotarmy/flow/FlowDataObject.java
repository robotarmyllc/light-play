/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow;

import com.robotarmy.flow.project.Project;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiFileLoader;
import org.openide.loaders.XMLDataObject;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@Messages({
    "LBL_Flow_LOADER=Files of Flow"
})

@MIMEResolver.Registration(
        displayName = "#LBL_Flow_LOADER",
        resource = "ProjectResolver.xml",
        showInFileChooser = {"xml"},
        position = 0)

// @MIMEResolver.NamespaceRegistration(
// displayName = "#LBL_Flow_LOADER",
// mimeType = "application/x-flow+xml",
// elementNS = {"flow"},
// position = 1000
// )

@DataObject.Registration(
        mimeType = "application/x-flow+xml",
        iconBase = "com/robotarmy/flow/icon/Savable.png",
        displayName = "#LBL_Flow_LOADER",
        position = 300
)

@ActionReferences({
    @ActionReference(
            path = "Loaders/application/x-flow+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200
    ),
    @ActionReference(
            path = "Loaders/application/x-flow+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300
    ),
    @ActionReference(
            path = "Loaders/application/x-flow+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400,
            separatorAfter = 500
    ),
    @ActionReference(
            path = "Loaders/application/x-flow+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 600
    ),
    @ActionReference(
            path = "Loaders/application/x-flow+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 700,
            separatorAfter = 800
    ),
    @ActionReference(
            path = "Loaders/application/x-flow+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            position = 900,
            separatorAfter = 1000
    ),
    @ActionReference(
            path = "Loaders/application/x-flow+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1100,
            separatorAfter = 1200
    ),
    @ActionReference(
            path = "Loaders/application/x-flow+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1300
    ),
    @ActionReference(
            path = "Loaders/application/x-flow+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1400
    )
})
//@org.openide.util.lookup.ServiceProvider(service=FlowDataObject.class)
public class FlowDataObject extends XMLDataObject {
    private static final Logger LOG = Logger.getLogger("FlowDataObject");
    private static final long serialVersionUID = 8309487531L;
    private final Project project;

    public FlowDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        LOG.setLevel(Level.FINEST);
        LOG.finest("FlowDataObject created.");
        registerEditor("application/x-flow+xml", true);
        project = new Project(this);
        
        
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @MultiViewElement.Registration(
            displayName = "#LBL_Flow_EDITOR",
            iconBase = "com/robotarmy/flow/icon/Savable.png",
            mimeType = "application/x-flow+xml",
            persistenceType = TopComponent.PERSISTENCE_NEVER,
            preferredID = "Flow",
            position = 1000
    )
    
    @Messages("LBL_Flow_EDITOR=Source")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
        return new MultiViewEditorElement(lkp);
    }

    public Project getProject() {
        return project;
    }
        
//    @Override
//    protected Node createNodeDelegate() {
//        //return DataNode.EMPTY;
//        return new DataNode(
//                this,
//                Children.create(new FlowChildFactory(this), true),
//                getLookup());
//    }

}
