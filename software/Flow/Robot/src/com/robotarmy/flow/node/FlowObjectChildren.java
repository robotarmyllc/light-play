package com.robotarmy.flow.node;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.robotarmy.flow.object.AFlowObject;
import com.robotarmy.flow.object.FlowObjectList;
import java.util.logging.Logger;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author mark
 */
public class FlowObjectChildren  extends Children.Keys<AFlowObject> {
    private static final Logger LOG = Logger.getLogger("FlowChildren");

    private final AFlowObject obj;

    public FlowObjectChildren( AFlowObject obj ) {
        super(false);
        this.obj = obj;
    }

    @Override
    protected Node[] createNodes(AFlowObject o) {
        InstanceContent ic = new InstanceContent();
        
        return new Node[]{new FlowNode(o, ic)};
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addNotify() {
        FlowObjectList children = obj.getChildren();

        // Only add non-system nodes to view tree.
        FlowObjectList ol = new FlowObjectList();
        for ( AFlowObject c : children) {
//            if ( !c.isSystemNode() ) {
                ol.add(c);
//            }
        }

        setKeys( ol );
    }

//    @Override
//    public void fieldChanged(FlowChangeEvent evt) {
//        switch( evt.getEventType()) {
//            case FlowChangeEvent.EVT_DOC_CHANGED:
//                addNotify();
//                break;
//        }
//    }
}
