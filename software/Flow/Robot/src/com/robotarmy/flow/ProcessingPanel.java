/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow;

import com.robotarmy.simpleopenni.SimpleOpenNI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author mark
 */
@SuppressWarnings("serial")
public class ProcessingPanel extends PApplet {

    private static final Logger LOG = Logger.getLogger(ProcessingPanel.class.getSimpleName());
    private List<KinectListener> listeners = new ArrayList<>();

    SimpleOpenNI openNI;
    private float zoomF = 0.5f;
    private float rotX = radians(180);  // by default rotate the hole scene 180deg around the x-axis, 
// the data from openni comes upside down
    private float rotY = radians(180);
    private int handVecListSize = 30;
    private Map<Integer, ArrayList<PVector>> handPathList = new HashMap<Integer, ArrayList<PVector>>();
    private int[] userClr = new int[]{
        color(255, 0, 0),
        color(0, 255, 0),
        color(0, 0, 255),
        color(255, 255, 0),
        color(255, 0, 255),
        color(0, 255, 255)
    };

    private int pxMax = 30000;
    private int pxMin = -30000;
    private int pyMax = 30000;
    private int pyMin = -30000;
    private int pzMax = 30000;
    private int pzMin = -30000;

//    Serial myPort;  // Create object from Serial class
    private int nPoints = 1;
    private int point = 0;
    private PVector[] pl = new PVector[nPoints];

    private int xP = 0;
    private int yP = 0;
    private int zP = 0;
//    private Universe universe;

    public ProcessingPanel() {
        LOG.setLevel(Level.FINEST);
        
        this.addKeyListener(this);
    }

    @Override
    public void setup() {
        if (frame != null) {
            frame.setResizable(true);
        }

        initKinect();

        stroke(255, 255, 255);
        smooth();
        perspective(radians(45), width / (float) height, 10.0f, 150000.0f);
        initPointAverages();

//        universe = Universe.getUniverse(0);
    }

    private void initKinect() {
        //context = new SimpleOpenNI(this);
        //context = new com.robotarmy.simpleopenni.SimpleOpenNI(this, SimpleOpenNI.RUN_MODE_MULTI_THREADED);
        openNI = new com.robotarmy.simpleopenni.SimpleOpenNI(this);

        if (openNI.isInit() == false) {
            LOG.severe("Can't init SimpleOpenNI, maybe the camera is not connected!");
            return;
        }

        //context.enableUser();
        // enable skeleton generation for all joints
        //context.enableUser(SimpleOpenNI.SKEL_RIGHT_HAND);
        openNI.enableUser(this);

        // enable depthMap generation 
        openNI.enableDepth();

        // mirror
        openNI.setMirror(true);

        // enable hands + gesture generation
        openNI.enableHand();
        //openNI.startGesture(SimpleOpenNI.GESTURE_WAVE);
        openNI.startGesture(SimpleOpenNI.GESTURE_HAND_RAISE);
    }

    @Override
    public synchronized void draw() {

        if ( openNI != null && openNI.isInit()) {
            // update the cam
            openNI.update();

            background(0, 0, 0);

            // set the scene pos
            translate(width / 2, height / 2, -500);
            rotateX(rotX);
            rotateY(rotY);
            scale(zoomF);

            // draw the 3d point depth map
            int[] depthMap = openNI.depthMap();
            int steps = 10;  // to speed up the drawing, draw every third point
            int index;
            PVector realWorldPoint;

            //translate(0, 0, -1000);  // set the rotation center of the scene 1000 infront of the camera

            // draw point cloud
            stroke(140,0,0);
            strokeWeight(2.0f);
            beginShape(POINTS);
            for (int y = 0; y < openNI.depthHeight(); y += steps) {
                for (int x = 0; x < openNI.depthWidth(); x += steps) {
                    index = x + y * openNI.depthWidth();
                    if (depthMap[index] > 0) {
                        // draw the projected point
                        realWorldPoint = openNI.depthMapRealWorld()[index];
                        vertex(realWorldPoint.x, realWorldPoint.y, realWorldPoint.z);
                    }
                }
            }
            endShape();

            // draw the tracked hands
            if (handPathList.size() > 0) {
                Iterator itr = handPathList.entrySet().iterator();
                if (itr.hasNext()) {
                    Map.Entry mapEntry = (Map.Entry) itr.next();
                    int handId = (Integer) mapEntry.getKey();
                    ArrayList<PVector> vecList = (ArrayList<PVector>) mapEntry.getValue();
                    PVector p;

                    // Draw Vapor Trail
                    pushStyle();
                    stroke(userClr[(handId - 1) % userClr.length]);
                    noFill();
                    Iterator itrVec = vecList.iterator();
                    beginShape();
                    while (itrVec.hasNext()) {
                        p = (PVector) itrVec.next();
                        vertex(p.x, p.y, p.z);
                    }
                    endShape();

                    // Draw Current Hand Position
                    stroke(userClr[(handId - 1) % userClr.length]);
                    strokeWeight(8.0f);

                    p = vecList.get(0);

                    point(p.x, p.y, p.z);
                    popStyle();

                    //LOG.log(Level.FINEST, "Point:  x:{0}  y:{1}  z:{2}", new Object[]{p.x, p.y, p.z});
                    //  p range is -300 to +300 for x and y
                    //  p range is 600 to 1200 for z
                    int x = (int) (p.x / 2) + 127;
                    if (x < 0) {
                        x = 0;
                    }
                    if (x > 255) {
                        x = 255;
                    }
                    // Y
                    int y = (int) (p.y / 2) + 127;
                    if (y < 0) {
                        y = 0;
                    }
                    if (y > 255) {
                        y = 255;
                    }
                    // Z
                    int z = (int) (p.z / 2) - 300;
                    if (z < 0) {
                        z = 0;
                    }
                    if (z > 255) {
                        z = 255;
                    }
                    //LOG.log(Level.FINEST, "  INT:  x:{0}  y:{1}  z:{2}", new Object[]{x, y, z});
//                    universe.write(0, x, y, z, x, y, z);
                    for ( KinectListener l: listeners) {
                        l.kinectPositionChanged(new Point3i(x, y, z));
                    }
                }
            } else {
//                    universe.write(0,   30, 30, 0, 127, 127, 127);
            }

            // draw the kinect cam
            openNI.drawCamFrustum();

            point++;
            if (point >= nPoints) {
                point = 0;
            }
        } else {
            background(0, 0, 0);
            stroke(255, 0, 0, 255);  // Red
            strokeWeight(4.0f);
            line(0, 0, this.getWidth(), this.getHeight());
            line(0, this.getHeight(), this.getWidth(), 0);
            //textSize(20.0f);
            //text("No Kinect Plugged In", 20, 20);
        }
    }

    void initPointAverages() {
        for (int i = 0; i < nPoints; i++) {
            pl[i] = new PVector();
        }
    }

    PVector getAveragedPoint() {
        PVector pa = new PVector();
        for (int i = 0; i < nPoints; i++) {
            pa.x += pl[i].x;
            pa.y += pl[i].y;
            pa.z += pl[i].z;
        }

        pa.x /= (float) nPoints;
        pa.y /= (float) nPoints;
        pa.z /= (float) nPoints;

        return pa;
    }

    PVector getCloudAvg(ArrayList<PVector> vecList) {
        PVector pa = new PVector();
        pa.x = 0;
        pa.y = 0;
        pa.z = 0;

        int i = 0;
        Iterator itrVec = vecList.iterator();
        while (itrVec.hasNext()) {
            PVector p = (PVector) itrVec.next();
            pa.x += p.x;
            pa.y += p.y;
            pa.z += p.z;

            i++;
        }

        if (i > 0) {
            pa.x /= i;
            pa.y /= i;
            pa.z /= i;
        }

        return pa;
    }


// -----------------------------------------------------------------
// hand events
    public void onNewHand(SimpleOpenNI curContext, int handId, PVector pos) {
        LOG.config("onNewHand - handId: " + handId + ", pos: " + pos);

        ArrayList<PVector> vecList = new ArrayList<PVector>();
        vecList.add(pos);

        handPathList.put(handId, vecList);
    }

    public void onTrackedHand(SimpleOpenNI curContext, int handId, PVector pos) {
        //println("onTrackedHand - handId: " + handId + ", pos: " + pos );

        ArrayList<PVector> vecList = handPathList.get(handId);
        if (vecList != null) {
            vecList.add(0, pos);
            if (vecList.size() >= handVecListSize) // remove the last point 
            {
                vecList.remove(vecList.size() - 1);
            }
        }
    }

    public void onLostHand(SimpleOpenNI curContext, int handId) {
        LOG.config("onLostHand - handId: " + handId);

        handPathList.remove(handId);
    }

    /**
     * gesture events
     */
    public void onCompletedGesture(SimpleOpenNI curContext, int gestureType, PVector pos) {
        //println("onCompletedGesture - gestureType: " + gestureType + ", pos: " + pos);

        openNI.startTrackingHand(pos);

        //int handId = openNI.startTrackingHand(pos);
        //println("hand tracked: " + handId);
    }

// -----------------------------------------------------------------
// Keyboard event
    @Override
    public void keyPressed() {
        switch (key) {
            case ' ':
                openNI.setMirror(!openNI.mirror());
                break;
        }

        switch (keyCode) {
            case LEFT:
                rotY += 0.1f;
                break;
            case RIGHT:
                rotY -= 0.1f;
                break;
            case UP:
                if (keyEvent.isShiftDown()) {
                    zoomF += 0.01f;
                } else {
                    rotX += 0.1f;
                }
                break;
            case DOWN:
                if (keyEvent.isShiftDown()) {
                    zoomF -= 0.01f;
                    if (zoomF < 0.01) {
                        zoomF = 0.01f;
                    }
                } else {
                    rotX -= 0.1f;
                }
                break;
        }
    }

//    @Override
//    public int sketchWidth() {
//        return displayWidth;
//    }
//
//    @Override
//    public int sketchHeight() {
//        return displayHeight;
//    }
    @Override
    public String sketchRenderer() {
        return OPENGL;
    }

    @Override
    public void stop() {
        openNI.dispose();
        super.stop(); //To change body of generated methods, choose Tools | Templates.
    }

    void addKinectListener(KinectListener aThis) {
        listeners.add(aThis);
    }

    @Override
    public void focusLost() {
        LOG.warning("We lost focus!");
        focused = true;
    }
    
    
    
}
