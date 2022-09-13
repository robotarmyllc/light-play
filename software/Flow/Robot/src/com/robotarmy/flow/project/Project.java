/*
 *  Flow -- Project
 *
 *  Copyright 2015 Robot Army LLC
 */
package com.robotarmy.flow.project;

import com.robotarmy.flow.FlowDataObject;
import com.robotarmy.flow.GlobalActionContextProxy;
import com.robotarmy.flow.node.FlowNode;
import com.robotarmy.flow.object.AFlowObject;
import com.robotarmy.flow.object.RootObject;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.filesystems.FileAttributeEvent;
import org.openide.filesystems.FileChangeListener;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileRenameEvent;
import org.openide.nodes.Node;
import org.openide.util.ContextGlobalProvider;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author mark
 */
public class Project implements PropertyChangeListener, FileChangeListener {

    private static final Logger LOG = Logger.getLogger("Project");

//    private File file = null;
    private String name = "Un-named";
    private final RootObject root = new RootObject();
    private boolean open = false;
    private final List<ProjectListener> listeners = new ArrayList<>();
    private FlowNode rootNode = null;
    private FlowDataObject dob = null;

    public Project(FlowDataObject dob) {

        this.dob = dob;
        GlobalActionContextProxy impl = (GlobalActionContextProxy) Lookup.getDefault().lookup(ContextGlobalProvider.class);
        
        impl.add(dob);

        //dob.setProject(this);
        load();
//        LOG.setLevel(Level.FINEST);
//        if (file != null) {
//            this.file = file;
//            this.name = file.getName();
//            getDataObject();
//            dob.setProject(this);
//            load();
//        }

        open = true;
        //root.addPropertyChangeListener(this);
    }

    public Project() {
        this(null);
    }

    public String getName() {
        return name;
    }

    public void close() {
        // Clear/free resources.
        GlobalActionContextProxy impl = (GlobalActionContextProxy) Lookup.getDefault().lookup(ContextGlobalProvider.class);        
        impl.remove(dob);
        
        this.dob = null;

        // Close any open files.
        open = false;
    }

    private void load() {
        try {
//            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
//            Document doc = docBuilder.parse(file);
            Document doc = dob.getDocument();

            // normalize text representation 
            //doc.getDocumentElement().normalize();
            LOG.log(Level.CONFIG, "Root element of the doc is {0}\n", doc.getDocumentElement().getNodeName());

            //dob.getPrimaryFile().addFileChangeListener(this);
            FileParser.parsePalettes(doc.getDocumentElement(), this.getRoot());
            FileParser.parseDeltas(doc.getDocumentElement(), this.getRoot());

            // Load the file
        } catch (SAXException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
//        } catch (ParserConfigurationException ex) {
//            Exceptions.printStackTrace(ex);
        }
    }

    public boolean doSave() {
        // Save to file

        // Clear edits
        root.clearEdits();

        notifyListeners(new ProjectChangeEvent(ProjectChangeEvent.EVENT_TYPE.FILE_SAVED));
        return true;
    }

    /**
     * @return the root
     */
    public AFlowObject getRoot() {
        return root;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isEdited() {
        return root.hasEdits();
    }

    public void addListener(ProjectListener l) {
        listeners.add(l);
    }

    public void removeListener(ProjectListener l) {
        listeners.remove(l);
    }

    private void notifyListeners(ProjectChangeEvent e) {
        for (ProjectListener l : listeners) {
            l.projectChanged(e);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        notifyListeners(new ProjectChangeEvent(ProjectChangeEvent.EVENT_TYPE.DATA_CHANGED));
    }

    public Node getRootNode() {
        if (rootNode == null) {
            rootNode = new FlowNode(getRoot(), new InstanceContent());
        }
        return rootNode;
    }

    @Override
    public void fileFolderCreated(FileEvent fe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fileDataCreated(FileEvent fe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fileChanged(FileEvent fe) {
        LOG.log(Level.CONFIG, "Project:  File has changed. ===> {0}", fe.toString());
    }

    @Override
    public void fileDeleted(FileEvent fe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fileRenamed(FileRenameEvent fe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fileAttributeChanged(FileAttributeEvent fe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
