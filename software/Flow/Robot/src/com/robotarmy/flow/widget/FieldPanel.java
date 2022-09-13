/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.widget;

import com.robotarmy.dmx.DmxElement;
import com.robotarmy.flow.FlowDataObject;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.awt.UndoRedo;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;

//@MultiViewElement.Registration(displayName = "#LBL_Flow_VISUAL2",
////iconBase = "org/myorg/abcfiletype/Datasource.gif",
//mimeType = "application/x-flow+xml",
//persistenceType = TopComponent.PERSISTENCE_NEVER,
//preferredID = "FlowVisual2",
//position = 3000)

@NbBundle.Messages({
    "LBL_Flow_VISUAL2=Visual2"
})/**
 *
 * @author mark
 */
public class FieldPanel extends JPanel implements MouseListener, MultiViewElement {

    private static final Logger LOG = Logger.getLogger("FieldPanel");
    private final List<ChangeListener> listeners = new ArrayList<>();
    private DmxElement selectedElement = new DmxElement(0, 123);
    private JToolBar toolbar = new JToolBar();
    private FlowDataObject obj;
    private transient MultiViewElementCallback callback;
    
    public FieldPanel(Lookup lkp) {
        obj = lkp.lookup(FlowDataObject.class);
        assert obj != null;
        LOG.setLevel(Level.FINEST);
        setBackground(Color.DARK_GRAY);
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //LOG.log(Level.FINEST, "mousePressed(){0}\n", e.paramString());
        notifyListeners();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void addChangeListener(ChangeListener l) {
        listeners.add(l);
    }
    
    public void notifyListeners() {
        for ( ChangeListener l: listeners) {
            l.stateChanged(new ChangeEvent(this));
        }
    }

    public DmxElement getSelectedElement() {
        return selectedElement;
    }

    @Override
    public JComponent getVisualRepresentation() {
        return this;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        return toolbar;
    }

    @Override
    public Action[] getActions() {
        return new Action[0];
    }

    @Override
    public Lookup getLookup() {
        return obj.getLookup();
    }

    @Override
    public void componentOpened() {
    }

    @Override
    public void componentClosed() {
    }

    @Override
    public void componentShowing() {
    }

    @Override
    public void componentHidden() {
    }

    @Override
    public void componentActivated() {
    }

    @Override
    public void componentDeactivated() {
    }

    @Override
    public UndoRedo getUndoRedo() {
        return UndoRedo.NONE;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.callback = callback;
        Mode m = WindowManager.getDefault().findMode("editor");
        m.dockInto(callback.getTopComponent());
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }
}
