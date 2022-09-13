/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.node;

import com.robotarmy.flow.Position3D;
import com.robotarmy.flow.Position3DPropertyEditor;
import com.robotarmy.flow.actions.NewDeltaAction;
import com.robotarmy.flow.actions.NewPaletteAction;
import com.robotarmy.flow.actions.RenameAction;
import com.robotarmy.flow.object.AFlowObject;
import com.robotarmy.flow.object.DeltaObject;
import com.robotarmy.flow.object.PaletteObject;
import com.robotarmy.flow.object.RootObject;
import java.awt.Color;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.openide.ErrorManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author mark
 */
public class FlowNode extends AbstractNode implements PropertyChangeListener, LookupListener {

    protected static final Logger LOG = Logger.getLogger("FlowNode");

    private final InstanceContent lookupContents;

    public FlowNode(AFlowObject o, InstanceContent ic) {
        super(Children.LEAF, new AbstractLookup(ic));
        LOG.setLevel(Level.FINEST);
        LOG.log(Level.FINEST, "Create FlowNode: {0}", o.getName());
        this.lookupContents = ic;

        
        lookupContents.add(o);

        // Cause the widget palette to show up when a node is selected.
        //Node root = new AbstractNode(new WidgetGroupNodeContainer());
        //PaletteActions a = new WidgetPaletteActions();
        //lookupContents.add(projectNode);
//        // If I am inside a page or am a visual element then add a viewCookie
//        if (o.isDisplayable()) {
//            lookupContents.add(new ViewCookieImpl());
//        }
//        // This is necessary to reorder the tree nodes,
//        // AKA drag-and-drop nodes within the tree
//        Index indexSupport = new IndexSupport();
//        lookupContents.add(indexSupport);
        setName(o.getName());
        //setDisplayName(o.getHtmlName());

        setIconBaseWithExtension(getIconPathFor(o.getType() + "-node"));

        // Listen for changes to properties of the actual object in ship.
        o.addPropertyChangeListener(WeakListeners.propertyChange(this, o));

        // Listen to general ship changes, like document loaded, mashed, etc.
        //ship.addListener(this);
        if (o.hasChildren()) {
            initChildren();
        }

    }

    /**
     *
     * @param type string name of the icon. Full path and '.png' will be added
     * automatically.
     * @return
     */
    @Messages({
        "# {0} - Icon tag/base name",
        "ICON_PATH_BASE=com/robotarmy/flow/icon/{0}.png"
    })
    public static String getIconPathFor(String type) {
        String icon = null;
        try {
            icon = Bundle.ICON_PATH_BASE(type);
        } catch (NoClassDefFoundError e) {
            LOG.log(Level.WARNING, "No icon for [{0}] found.\n", icon);
        }
        if (icon != null && FlowNode.class.getResource("/" + icon) != null) {
            return icon;
        } else {
            return Bundle.ICON_PATH_BASE("xml-node");
        }
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public final void setName(String s) {
        super.setName(s); //To change body of generated methods, choose Tools | Templates.
        setDisplayName(getObject().getHtmlName());
        // Short Description appears in the box below the property sets.
        // It can take HTML for making it look that way we want.
        setShortDescription(createShortDescription());
    }

    protected final String createShortDescription() {
        return "<html>" + getObject().getHTMLDescription() + "</html>";
    }

    /**
     * @return the object
     */
    public AFlowObject getObject() {
        return getLookup().lookup(AFlowObject.class);
    }

//    public Node getNode() {
//        return this;
//    }
    private void initChildren() {
        setChildren(new FlowObjectChildren(getLookup().lookup(AFlowObject.class)));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LOG.log(Level.FINEST, "{0}: Property Changed.\n", getClass().getSimpleName());

        //Iterator<AFlowObject> objects = evt.getObjects();
        Object source = evt.getSource();
        if (source instanceof AFlowObject) {
            AFlowObject o = (AFlowObject) source;
            switch (evt.getPropertyName()) {
                case AFlowObject.EVT_CHILD_ADD:
                    if (this.isLeaf()) {
                        if (getObject().hasChildren()) {
                            initChildren();
                        }
                    } else {
                        if (getObject().hasChildren()) {
                            FlowObjectChildren children = (FlowObjectChildren) this.getChildren();
                            // Cause children to regenerate it's key list.
                            children.addNotify();
                        } else {
                            // We no longer have children. Change to leaf.
                            setChildren(Children.LEAF);
                        }
                    }   firePropertyChange("Child Added", null, null);
                    break;
                case AFlowObject.EVT_NAME:
                    setName((String) evt.getNewValue());
                    firePropertyChange("Name", evt.getOldValue(), evt.getNewValue());
                    break;
            }
        }

    }

    @Override
    public void resultChanged(LookupEvent ev) {
        LOG.log(Level.INFO, "{0}: Lookup resultChanged().\n", getClass().getSimpleName());
    }

    @Override
    public Action[] getActions(boolean popup) {
        if (getObject() instanceof RootObject) {
            return new Action[]{new NewPaletteAction()};
        }
        if (getObject() instanceof DeltaObject) {
            return new Action[]{new RenameAction()};
        }
        if (getObject() instanceof PaletteObject) {
            return new Action[]{new RenameAction(), new NewDeltaAction()};
        }

        return new Action[]{};
    }

    @Override
    protected Sheet createSheet() {

        Sheet sheet = Sheet.createDefault();
        // Regular Editable Properties
        sheet.put(initEditablePropertySheetSet());
        
        // DMX Set
        if (getObject() instanceof DeltaObject) {
            sheet.put(initDmxPropertySheetSet());
        }
        
        // Location Set
        if (getObject() instanceof DeltaObject ||
            getObject() instanceof PaletteObject) {
            sheet.put(initLocationPropertySheetSet());
        }
        
        // Address Set
        if (getObject() instanceof DeltaObject) {
            sheet.put(initDeltaPropertySheetSet());
        }

        return sheet;
    }
    
    private Sheet.Set initEditablePropertySheetSet() {
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setName("Base Properties");
        set.setDisplayName("Base Properties");

        try {

            // Type -- Read-only
            Property<String> typeProp = new PropertySupport.Reflection<>(
                    getObject(), String.class, "getType", null);
            typeProp.setName("Type");
            set.put(typeProp);

            // Name -- R/W
            Property<String> nameProp = new PropertySupport.Reflection<>(
                    getObject(), String.class, "getName", "setName");
            nameProp.setName("Name");
            set.put(nameProp);

        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }

        return set;
    }

    private Sheet.Set initDmxPropertySheetSet() {
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setName("DMX Properties");
        set.setDisplayName("DMX");

        try {
            // Address -- R/W
            Property<Integer> uProp = new PropertySupport.Reflection<>(
                    ((DeltaObject)getObject()).getAddress(), int.class, "getUniverse", "setUniverse");
            uProp.setName("Universe");
            set.put(uProp);
            // Address -- R/W
            Property<Integer> aProp = new PropertySupport.Reflection<>(
                    ((DeltaObject)getObject()).getAddress(), int.class, "getAddress", "setAddress");
            aProp.setName("Address");
            set.put(aProp);

        } catch (NoSuchMethodException ex) {
            //ErrorManager.getDefault();
            Exceptions.printStackTrace(ex);
        }

        return set;
    }
    
    private Sheet.Set initLocationPropertySheetSet() {
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setName("Location Properties");
        set.setDisplayName("Location");

        try {
            // X -- R/W
            Property<Integer> xProp = new PropertySupport.Reflection<>(
                    getObject(), int.class, "getX", "setX");
            xProp.setName("X");
            set.put(xProp);

            // Y -- R/W
            Property<Integer> yProp = new PropertySupport.Reflection<>(
                    getObject(), int.class, "getY", "setY");
            yProp.setName("Y");
            set.put(yProp);

        } catch (NoSuchMethodException ex) {
            //ErrorManager.getDefault();
            Exceptions.printStackTrace(ex);
        }

        return set;
    }
    
    private Sheet.Set initDeltaPropertySheetSet() {
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setName("Delta Properties");
        set.setDisplayName("XYZ and Color");

        try {
            // X -- R/W
            PropertySupport.Reflection<Position3D> xProp = new PropertySupport.Reflection<>(
                    getObject(), Position3D.class, "Position");
            xProp.setPropertyEditorClass(Position3DPropertyEditor.class);
            xProp.setName("Position");
            set.put(xProp);

            // Y -- R/W
            Property<Color> yProp = new PropertySupport.Reflection<>(
                    getObject(), Color.class, "getColor", null);
            yProp.setName("Color");
            set.put(yProp);

        } catch (NoSuchMethodException ex) {
            //ErrorManager.getDefault();
            Exceptions.printStackTrace(ex);
        }

        return set;
    }
}
