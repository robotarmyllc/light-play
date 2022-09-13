/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.leap.lightgrid;

import com.robotarmy.leap.RobotCoords;
import com.robotarmy.leap.lightgrid.sprite.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.windows.IOProvider;

/**
 *
 * @author mark
 */
@SuppressWarnings("serial")
public class Grid extends javax.swing.JPanel implements ActionListener, ChangeListener {
    Logger log = Logger.getLogger("lightgrid.Grid");

    public static final int FWIDTH = 61;
    public static final int FHEIGHT = 41;
    
    private static final float X_RATIO = FWIDTH/(float)RobotCoords.WIDTH;
    private static final float Y_RATIO = FHEIGHT/(float)RobotCoords.HEIGHT;
    
    // Fall Colors  -  LightPlay 2016
//    private static final Color RANDOM_1 = new Color(255,0,0);
//    private static final Color RANDOM_2 = new Color(200,80,0);
//    private static final Color RANDOM_3 = new Color(150, 100, 0);
    
    // Pink Colors  -  LightPlay 2017
    private static final Color RANDOM_1 = new Color(90,0,255);
    private static final Color RANDOM_2 = new Color(200,0,200);
    private static final Color RANDOM_3 = new Color(128, 0, 230);

    private final Timer timer;

    //private final Color[][] grid = new Color[FWIDTH][FHEIGHT];
    private final ColorElement[][] grid = new ColorElement[FWIDTH][FHEIGHT];
    
    private final ArrayList<Sprite> spriteList = new ArrayList<>();
    
    //private final int fadeRate = 1;  // Amount per frame to fade alpha
    private int brushSize = 2;
    private int frameCount;

    /**
     * Creates new form LightGrid
     */
    public Grid() {
        for (int i = 0; i < FWIDTH; i++) {
            for (int j = 0; j < FHEIGHT; j++) {
                //grid[i][j] = Color.BLACK;
                grid[i][j] = new ColorElement();
            }
        }
        initComponents();
        

        timer = new Timer(30, this);
        //timer.setInitialDelay(3000);
        timer.start();
    }

    public void addSprite( Sprite sprite ) {
        spriteList.add(sprite);
    }
    
    public void removeSprite( Sprite sprite ) {
        spriteList.remove(sprite);
    }
    
    public void setXY(int x, int y, Color c) {
        if (x < 0 || y < 0 || x > FWIDTH - 1 || y > FHEIGHT - 1) {
            return;
        }
        
        grid[x][y].setTarget(c);
        grid[x][y].setRate(20);
    }

    public void brushXY(float size, int x, int y, Color c) {
        
        int bSize = (int)(size * brushSize);
        
        for ( int bx = x-bSize/2; bx < x+bSize/2; bx++ ) {
            for ( int by = y-(bSize*2); by < y+(bSize*2); by++ ) {
                setXY(bx, by, c);
            }
        }
    }

    public Color getColor(Point p) {
        //return grid[(int)(p.x*X_RATIO)][(int)(p.y*Y_RATIO)];
        return grid[(int)(p.x*X_RATIO)][(int)(p.y*Y_RATIO)].getColor();
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);

        int blockW = getWidth() / FWIDTH;
        int blockH = getHeight() / FHEIGHT;
        
        for (int i = 0; i < FWIDTH; i++) {
            for (int j = 0; j < FHEIGHT; j++) {
                grphcs.setColor(grid[i][j].getColor());
                grphcs.fillRect(i * blockW, j * blockH, blockW, blockH);
                grphcs.setColor(Color.DARK_GRAY);
                grphcs.drawRect(i * blockW, j * blockH, blockW, blockH);
            }
        }
    }

    // Called by Timer at about 30FPS
    @Override
    public void actionPerformed(ActionEvent ae) {
        for (int i = 0; i < FWIDTH; i++) {
            for (int j = 0; j < FHEIGHT; j++) {
                grid[i][j].processTarget();
//                Color c = grid[i][j];
//                if (c.getAlpha() > fadeRate) {
//                    grid[i][j] = clampedColor( c, fadeRate, 40);
//                } else {
//                    grid[i][j] = Color.BLACK;
//                }
                
//                // Sparkles!
//                if ( (frameCount&1) == 0 &&  Math.random() > 0.95 ) {
//                    grid[i][j] = Color.MAGENTA;
//                }
                // Sparkles!
                if ( (frameCount&1) == 0 && Math.random() > 0.999 ) {
                    Color bgc = randomBackgroundColor();
                    for(int x=i-3; x<i+3; x++) {
                        for ( int y=j-3; y<j+3; y++ ) {
                            setGrid(x, y, bgc);
                        }
                    }
                }
            }
        }

        frameCount++;
        if ( frameCount > 30 ) {
            frameCount = 0;
        }
        
        repaint();
    }

    private void setGrid( int x, int y, Color c) {
        try {
            grid[x][y].setTarget(c);
        } catch ( ArrayIndexOutOfBoundsException e) {
            // Off the grid.  Ignore it.
        }
    }
    
    private Color randomBackgroundColor() {
        switch ( (int)(Math.random()*3) ) {
            case 0:
                return RANDOM_1;
            case 1:
                return RANDOM_2;
            case 2:
            default:
                return RANDOM_3;
                
        }
    }
    
    private Color clampedColor(Color c, int rate, int min ) {
        int r = c.getRed() - rate;
        int g = c.getGreen() - rate;
        int b = c.getBlue() - rate;
        
        if ( r < min && g < min && b < min ) {
            //log.severe("Clamped.");
            return c;  // Color is already at least value
            
        } else {
            return new Color(clamp(r), clamp(g), clamp(b));        
        }
    }
    
    
    private int clamp(int n) {
        if (n > 255) {
            return 255;
        }
        if (n < 0) {
            return 0;
        }
        return n;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @return the brushSize
     */
    public int getBrushSize() {
        return brushSize;
    }

    /**
     * @param brushSize the brushSize to set. Must be greater than zero!
     */
    public void setBrushSize(int brushSize) {
        if ( brushSize > 0 ) {
            this.brushSize = brushSize;
        }
        
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        if ( ce.getSource() instanceof JSlider) {
            JSlider sl = (JSlider) ce.getSource();
            setBrushSize(sl.getValue());
            IOProvider.getDefault().getIO("Log", false).getOut().println("[LightGrid] Brush size changed: " + sl.getValue());
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
