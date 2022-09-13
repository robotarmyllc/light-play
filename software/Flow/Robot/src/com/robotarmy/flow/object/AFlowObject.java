/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.object;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mark
 */
public abstract class AFlowObject {

    protected static final Logger LOG = Logger.getLogger("FlowObject");
    public final static String UNNAMED = "(unnamed)";  // TODO: Move string to resource bundle.

    public final static String EVT_CHILD_ADD = "Child Add";
    public final static String EVT_NAME = "Name Changed";

    private static int serialCounter = 12000;
    private final int serial;

    private FlowObjectList children = new FlowObjectList();
//    private SHIPObject.SHIPOBJKEY key;
    private AFlowObject parent = null;
//    private final static String INDENT = "    ";  // Default indent when writing XML.
    private final FlowObjectList referenceListeners = new FlowObjectList();
    protected List<PropertyChangeListener> listeners = Collections.synchronizedList(new LinkedList<PropertyChangeListener>());
    private String name;
    //private String type;
    private String description;
    private boolean isEdited = false;

    public AFlowObject(AFlowObject newParent) {
        LOG.setLevel(Level.FINEST);
        serial = serialCounter;
        serialCounter++;

        setParent(newParent);
        if (parent != null) {
            //parent.getChildren().add(this);
            parent.addChild(this);
        }

        LOG.log(Level.FINEST, "Create Flow Object [{0}]", serial);
    }

    public final void setParent(AFlowObject newParent) {
        parent = newParent;
    }

    public abstract String getType();

    public FlowObjectList getChildren() {
        return children;
    }

    public void setChildren(FlowObjectList children) {
        this.children = children;
    }

    public void addChild(AFlowObject o) {
        if (children.contains(o)) {
            LOG.warning("AddChild: List already contains object!");
            return;
        }
        LOG.log(Level.CONFIG, "{0}: Add child object: {1}", new Object[]{this.getName(), o.getName()});
        children.add(o);
        setEdited(true);
        fire(new PropertyChangeEvent(this, EVT_CHILD_ADD, null, o));
        if (!this.equals(getRoot())) {
            getRoot().propertyChange(new PropertyChangeEvent(this, EVT_CHILD_ADD, null, o));
        }
    }

    public AFlowObject getParent() {
        return parent;
    }

    public RootObject getRoot() {
        return parent.getRoot();
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public FlowObjectList getSiblings() {
        FlowObjectList list;
        AFlowObject myParent = this.getParent();
        if (myParent == null) {
            list = new FlowObjectList();
            list.add(this);
        } else {
            list = myParent.getChildren();
        }
        return list;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        fire ( new PropertyChangeEvent(this, EVT_NAME, oldName, name));
    }

    public String getName() {
        return ((name == null) ? UNNAMED : name);
    }

    public String getHtmlName() {
        return ((name == null) ? UNNAMED : name) + ((name == null) ? " [" + this.getType() + "]" : "");
    }

    public void setDescription( String description ) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }

    /**
     * @return the referenceListeners
     */
    public FlowObjectList getReferenceListeners() {
        return referenceListeners;
    }

    public String getHTMLDescription() {
        StringBuilder line = new StringBuilder(description + "::&nbsp;&nbsp;<b>" + getName() + "</b>");

        if (!referenceListeners.isEmpty()) {
            line.append("<br>Listeners:");
            line.append("<ul>");
            for (AFlowObject o : getReferenceListeners()) {
                line.append("<li>").append(o.getName()).append("</li>");
            }
            line.append("</ul>");

        }

        return line.toString();
    }

    public final void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.add(pcl);
    }

    public final void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.remove(pcl);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        // Forward property changes from props.
        fire(evt);
        //notifyListenersDirty(evt);
    }

    private synchronized void fire(PropertyChangeEvent evt) {
        LOG.log(Level.CONFIG, "[AFlowObject {0}]: Property Changed in {1}\n", new Object[]{serial, getType()});
        PropertyChangeListener[] lis = listeners.toArray(new PropertyChangeListener[listeners.size()]);
        for (PropertyChangeListener l : lis) {
            l.propertyChange(evt);
        }
//        Iterator<PropertyChangeListener> i = listeners.iterator(); // Must be in synchronized block
//        while (i.hasNext()) {
//            i.next().propertyChange(evt);
//        }
    }

    protected void setEdited(boolean edited) {
        this.isEdited = edited;
    }

    public boolean hasEdits() {
        if (isEdited) {
            return true;
        }
        for (AFlowObject c : getChildren()) {
            if (c.hasEdits()) {
                return true;
            }
        }

        return false;
    }

    public void clearEdits() {
        setEdited(false);
        for (AFlowObject c : getChildren()) {
            c.clearEdits();
        }
    }
}
