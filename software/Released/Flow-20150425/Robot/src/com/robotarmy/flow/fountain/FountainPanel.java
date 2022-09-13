/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.fountain;

import com.robotarmy.flow.KinectListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.vecmath.Point3i;

/**
 *
 * @author mark
 */
public class FountainPanel extends JPanel implements ActionListener, MouseMotionListener, KinectListener {

    private static final long serialVersionUID = 79376021266331L;
    private static final Logger LOG = Logger.getLogger("FountainPanel");

    private final FountainModel model = new FountainModel();
    private final int PRESSURE = 180;
    
    private int penWidth = 4;
    
    //  Previous position of mouse.
    private int lastX=0;
    private int lastY=0;
    
    private Point3i lastP = new Point3i();
    private List<FountainRobot> robots;

    public FountainPanel() {
        model.addActionListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int w = getWidth();
        int h = getHeight();

        int bWidth = w / getModel().getW();
        int bHeight = h / getModel().getH();

        // Draw grid of boxes
        for (int x = 0; x < getModel().getW(); x++) {
            for (int y = 0; y < getModel().getH(); y++) {
                g.setColor(getModel().getAsColor(x, y));
                g.fillRect(x * bWidth, y * bHeight, bWidth, bHeight);
                g.setColor(Color.black);
                g.drawRect(x * bWidth, y * bHeight, bWidth, bHeight);
            }
        }
        // Draw ovals where a robot exists
        if ( robots != null ) {
            for ( FountainRobot r: robots ) {
                g.drawOval(r.getX() * bWidth - 2, r.getY() * bHeight - 2, bWidth+4, bHeight+4);
            }
        }
        //g.drawOval(0, 0, getWidth(), getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int w = getWidth();
        int h = getHeight();

        processPoint(e.getX(), e.getY(), PRESSURE, w, h);
    }
    
    private void processPoint( int px, int py, int pz, int w, int h) {
        int bWidth = w / getModel().getW();
        int bHeight = h / getModel().getH();

        int x = px / bWidth;
        int y = py / bHeight;
        
        if( Math.abs(lastX) > 20) { 
            lastX = x; 
        }
        if( Math.abs(lastY) > 20) { 
            lastY = y; 
        }
        
        int dirX = lastX - x;
        int dirY = lastY - y;
        
        if ( x < 0 || y < 0 ) {
            return;
        }
        
        if ( x > getModel().getW()  || y > getModel().getH() ) {
            return;
        }
        
        for ( int i = x; i<x+penWidth; i++ ) {
            for ( int j=y; j<y+penWidth; j++ ) {
                int penX = i-(penWidth/2);
                int penY = j-(penWidth/2);
                if ( penX < 0 || penY < 0 || penX >= getModel().getW()  || penY >= getModel().getH() ) {
                    continue;
                }
                
                FountainCell cell = getModel().getCell(penX, penY);
                getModel().setCell(penX, penY, 
                        FountainCell.limit(cell.getX() + dirX ),
                        FountainCell.limit(cell.getY() + dirY ),
                        FountainCell.limit(cell.getZ() + pz )
                );
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void kinectPositionChanged(Point3i p) {
        // p x/y/z are in the range of 0-255.
        // Compress that into about 10x10 grid.
        // Divide by 25.
        //LOG.log(Level.WARNING, "kinect point changed: {0}:{1}:{2}", new Object[]{p.x, p.y, p.z});
//        int bWidth = 255 / getModel().getW();
//        int bHeight = 255 / getModel().getH();

//        int x = p.x / bWidth;
//        int y = p.y / bHeight;
        //getModel().setCell(p.x / bWidth, p.y / bHeight, new Color(p.z, p.z, p.z));
//        getModel().setCell( x, y,
//                lastP.x-p.x, lastP.y-p.y, p.z);

        
        processPoint(255-p.x, p.z, p.y+100, 255, 255);
        
        
    }

    /**
     * @return the model
     */
    public FountainModel getModel() {
        return model;
    }

    public void setRobots(List<FountainRobot> robots) {
        this.robots = robots;
    }

}
