/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.widget;

import com.robotarmy.flow.Position3D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author mark
 */
public class DeltaRobot extends JPanel implements MouseMotionListener, MouseWheelListener, MouseListener {

    private static final Logger LOG = Logger.getLogger("DeltaRobot");

    int cr = 0;
    int cg = 0;
    int cb = 0;

    private static final int NATIVE_W = 255;
    private static final int NATIVE_H = 255;
    private static final int NATIVE_Z = 255;
    private static final int MARGIN = 5;
    private static final int W = MARGIN + NATIVE_W + MARGIN;
    private static final int H = MARGIN + NATIVE_H + MARGIN;
    private static final int H_MID = H / 2;
    private static final int W_MID = W / 2;
    private static final int Z_MID = NATIVE_Z / 2;

    private static final Color YELLOW = new Color(215, 255, 0);

    private static final Color BLACK = Color.DARK_GRAY;

    // Hexagon points
    private final int[] pX = {
        MARGIN,
        MARGIN + (int) (0.25 * NATIVE_W),
        MARGIN + (int) (0.75 * NATIVE_W),
        NATIVE_W + MARGIN,
        MARGIN + (int) (0.75 * NATIVE_W),
        MARGIN + (int) (0.25 * NATIVE_W)
    };
    private final int[] pY = {
        MARGIN + (int) (0.5 * NATIVE_H),
        MARGIN + (int) (0.06 * NATIVE_H),
        MARGIN + (int) (0.06 * NATIVE_H),
        MARGIN + (int) (0.5 * NATIVE_H),
        MARGIN + (int) (0.94 * NATIVE_H),
        MARGIN + (int) (0.94 * NATIVE_H)
    };

    private static final int H_TEXT = 20;

    private int posX;
    private int posY;
    private int posZ;

    BufferedImage img = null;
    boolean pickColor = false;
    private int cX;
    private int cY;
    private int h;
    private double theta;
    private ArrayList<ChangeListener> listeners = new ArrayList<>();
    private Position3D position = new Position3D();

    public DeltaRobot() {
        LOG.setLevel(Level.FINEST);
        setPreferredSize(new Dimension(W, H + H_TEXT));
        setMinimumSize(new Dimension(W, H + H_TEXT));
        setOpaque(false);
        //setBounds(0, 0, W, H+ H_TEXT);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("colorpicker.png");
            img = ImageIO.read(resourceAsStream);
        } catch (IOException e) {
            LOG.severe("Could not load color picker image!\n");
        }
        reset();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.

        Graphics2D g2 = (Graphics2D) g;
        // Remember draw color.
        Color color = g2.getColor();

        if (pickColor) {
            g2.drawImage(img, 0, 0, W, H, null);

            g2.setColor(new Color(cr, cg, cb));
            g2.fillRect(0, 0, 20, 20);

            g2.drawLine(W / 2, H / 2, cX, cY);
            int a = (int) ((double) h * (Math.cos(Math.PI / 6)));
            int b = (int) ((double) h * (Math.sin(Math.PI / 6)));
            //a+=W_MID;
            b += H_MID;

            g2.drawLine(cX, cY, a, b);

        } else {
            g2.setColor(BLACK);
            // draw GeneralPath (polygon)
            GeneralPath polygon
                    = new GeneralPath(GeneralPath.WIND_EVEN_ODD, pX.length);
            polygon.moveTo(pX[0], pY[0]);

            for (int index = 1; index < pX.length; index++) {
                polygon.lineTo(pX[index], pY[index]);
            }

            // Base
            polygon.closePath();
            g2.draw(polygon);

            if (!isEnabled()) {
                return;
            }

            //Shadow
            g2.setColor(new Color(0, 0, 0, 80));
            int sSize = (int) (0.1 * NATIVE_W) + posZ / (int) (0.05 * NATIVE_W);
            g2.fill(new Ellipse2D.Double(posX - (sSize / 2), posY - (sSize / 2), sSize, sSize));

            // Arms
            g2.setColor(YELLOW);
            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(3.0f));
            int aX1 = ((pX[2] - pX[1]) / 2) + pX[1];
            int aY1 = pY[1];
            int armX = (int) (0.05 * NATIVE_W);
            g2.drawLine(aX1 - armX, aY1, posX - armX, posY - (int) (0.05 * NATIVE_H));
            g2.drawLine(aX1 + armX, aY1, posX + armX, posY - (int) (0.05 * NATIVE_H));

            g2.drawLine(
                    posX - (int) (0.07 * NATIVE_W), posY - (int) (0.02 * NATIVE_H),
                    W_MID - (int) (0.4 * NATIVE_W), H_MID + (int) (0.17 * NATIVE_H));
            g2.drawLine(
                    posX - (int) (0.02 * NATIVE_W), posY + (int) (0.07 * NATIVE_H),
                    W_MID - (int) (0.35 * NATIVE_W), H_MID + (int) (0.26 * NATIVE_H));

            g2.drawLine(
                    posX + (int) (0.07 * NATIVE_W), posY - (int) (0.02 * NATIVE_H),
                    W_MID + (int) (0.4 * NATIVE_W), H_MID + (int) (0.17 * NATIVE_H));
            g2.drawLine(
                    posX + (int) (0.02 * NATIVE_W), posY + (int) (0.07 * NATIVE_H),
                    W_MID + (int) (0.35 * NATIVE_W), H_MID + (int) (0.26 * NATIVE_H));

            g2.setStroke(stroke);

            // End effector        
            int size = (int) (NATIVE_W * 0.2);
            g2.setStroke(new BasicStroke(2.0f));
            Ellipse2D.Double elipse = new Ellipse2D.Double(posX - (size / 2), posY - (size / 2), size, size);
            g2.setColor(new Color(cr, cg, cb));
            g2.fill(elipse);
            g2.setColor(BLACK);
            g2.draw(elipse);
            g2.setStroke(stroke);

        }

        g2.setColor(BLACK);

        // Reticule
        g2.drawLine(W_MID - MARGIN, H_MID, W_MID + MARGIN, H_MID);
        g2.drawLine(W_MID, H_MID - MARGIN, W_MID, H_MID + MARGIN);

        //Text
        Font font = g2.getFont();
        g2.setFont(font.deriveFont(font.getSize2D() - 3.0f));
        g2.drawString("X:", 0, H + H_TEXT);
        g2.drawString("Y:", (int) (W * 0.38), H + H_TEXT);
        g2.drawString("Z:", (int) (W * 0.76), H + H_TEXT);

        g2.setFont(font);
        g2.drawString(Integer.toString(posX), (int) (W * 0.09), H + H_TEXT);
        g2.drawString(Integer.toString(posY), (int) (W * 0.47), H + H_TEXT);
        g2.drawString(Integer.toString(posZ), (int) (W * 0.85), H + H_TEXT);

        g2.setColor(color);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //LOG.log(Level.FINEST, "Mouse Drag: {0}\n", e.toString());
        if (pickColor) {
            /*  Disabled for now
             //cr = e.getX() * 2;
             //cg = e.getY() * 2;
             int x = e.getX();
             if (x < 5) {
             x = 5;
             }
             if (x > 105) {
             x = 105;
             }
             int y = e.getY();
             if (y < 5) {
             y = 5;
             }
             if (y > 105) {
             y = 105;
             }
             cX = x;
             cY = y;

             // Hypotenuse
             h = hypotenuse(e.getX() - W_MID, e.getY() - H_MID);
             // Theta
             double tin = (double) (e.getY() - H_MID) / (double) h;
             theta = Math.asin(tin);
             double theta2 = Math.acos(tin);
             // B
             double theta30 = Math.PI / 6.0;
             double b = h * (theta - theta30);
             double b2 = h * (theta2 - theta30);
             LOG.log(Level.WARNING, "Hypo: {0} Theta-in: {1} Theta: {2}  T2: {3}  Th30: {4}  D1: {5}   D2:  {6}", new Object[]{h, tin, theta, theta2, theta30, b, b2});
             */
        } else {
            int x = e.getX();
            if (x < MARGIN) {
                x = MARGIN;
            }
            if (x > NATIVE_W + MARGIN) {
                x = NATIVE_W + MARGIN;
            }
            int y = e.getY();
            if (y < MARGIN) {
                y = MARGIN;
            }
            if (y > NATIVE_H + MARGIN) {
                y = NATIVE_H + MARGIN;
            }
            posX = x;
            posY = y;
        }
        repaint();
        for (ChangeListener l : listeners) {
            l.stateChanged(new ChangeEvent(this));
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //LOG.log(Level.FINEST, "Mouse Wheel: {0}\n", e.toString());
        //LOG.log(Level.FINE, "Scroll wheel modifiers.{0}\n", e.getModifiers());
        if (e.getModifiers() == MouseWheelEvent.BUTTON2_MASK) {
            posZ += e.getWheelRotation();
            if (posZ < 0) {
                posZ = 0;
            }
            if (posZ > NATIVE_Z) {
                posZ = NATIVE_Z;
            }
            repaint();
            for (ChangeListener l : listeners) {
                l.stateChanged(new ChangeEvent(this));
            }
            //LOG.log(Level.FINEST, "PosZ = {0}\n", posZ);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //pickColor = e.getButton() == MouseEvent.BUTTON3;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            pickColor = false;
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private int hypotenuse(int x, int y) {
        return (int) Math.sqrt(x * x + y * y);
    }

    public void addChangeListener(DeltaWidgetPanel l) {
        listeners.add(l);
    }

    private int getPosX() {
        return 255 * (posX - MARGIN) / NATIVE_W;
    }

    private void setPosX(int x) {
        posX = (NATIVE_W * x / 255) + MARGIN;
    }

    private int getPosY() {
        return 255 * (posY - MARGIN) / NATIVE_H;
    }

    private void setPosY(int x) {
        posY = (NATIVE_H * x / 255) + MARGIN;
    }

    private int getPosZ() {
        return 255 * posZ / NATIVE_Z;
    }
    
    private void setPosZ(int z) {
        posZ =  NATIVE_Z * z / 255;
    }

    public final void reset() {
        posX = W_MID;
        posY = H_MID;
        posZ = Z_MID;
        repaint();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        // Ignore mouse
        // set draw color light grey
    }

    void setPosition(Position3D d) {
        setPosX(d.getX());
        setPosY(d.getY());
        setPosZ(d.getZ());
        repaint();
    }

    Position3D getPosition() {
        // refresh the XYZ
        position.setX(getPosX());
        position.setY(getPosY());
        position.setZ(getPosZ());

        return position;
    }

}
