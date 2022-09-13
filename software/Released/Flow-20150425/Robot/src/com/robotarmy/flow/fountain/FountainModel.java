/*
 * Fountain Model
 * 
 * Holds the data state for the fountain grid.
 */
package com.robotarmy.flow.fountain;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author mark
 */
public class FountainModel implements ActionListener {

    private final int w = 30;  // 30 squares wide.
    private final int h = 30;  // 30 squares long.

    private int decay = 30;  // Per-frame decay of values in a cell.

    private final List<List<FountainCell>> cells = new ArrayList<>(h);
    private final List<List<FountainCell>> diff = new ArrayList<>(h);
    private final Timer timer;
    private final List<ActionListener> listeners = new ArrayList<>();
    private final int WATERLINE_Z = 0;
    private final int WATERLINE_XY = 127;
    

    public FountainModel() {
        for (int y = 0; y < h; y++) {
            ArrayList<FountainCell> row = new ArrayList<>(w);
            ArrayList<FountainCell> dRow = new ArrayList<>(w);

            for (int x = 0; x < w; x++) {
                row.add(new FountainCell());
                dRow.add(new FountainCell());
            }
            cells.add(row);
            diff.add(dRow);
        }
        
        timer = new Timer(30, this);
        timer.setInitialDelay(5000);
        timer.start();
    }

    /**
     * @return the w
     */
    public int getW() {
        return w;
    }

    /**
     * @return the h
     */
    public int getH() {
        return h;
    }

    /**
     * @return the cells
     */
    public List<List<FountainCell>> getCells() {
        return Collections.unmodifiableList(cells);
    }

    Color getAsColor(int x, int y) {
        FountainCell cell = cells.get(x).get(y);
        return new Color(cell.getX(), cell.getY(), cell.getZ());
    }

    void setCell(int x, int y, Color c) {
        // Check the range
        if (x < 0 || x >= w || y < 0 || y >= h) {
            return;
        }
        FountainCell cell = cells.get(x).get(y);
        cell.set(c);
    }

    void setCell(int x, int y, int valX, int valY, int valZ ) {
        // Check the range
        if (x < 0 || x >= w || y < 0 || y >= h) {
            return;
        }
        FountainCell cell = cells.get(x).get(y);
        cell.set(valX, valY, valZ);
    }

    /**
     * Handle the animation loop.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        List<List<FountainCell>> prev = new ArrayList<>(cells);
        Collections.copy(prev, cells);

        for (int y = 0; y < h; y++) {
            List<FountainCell> row = cells.get(y);
            //List<FountainCell> pRow = cells.get(y);
            for (int x = 0; x < w; x++) {
                FountainCell cell = row.get(x);
                cell.setX(decay(WATERLINE_XY, cell.getX()));
                cell.setY(decay(WATERLINE_XY, cell.getY()));
                cell.setZ(decay(WATERLINE_Z,  cell.getZ()));
            }
        }

        bleed(prev);

        merge(diff);
        
        // Inject darkness into center
        if ( Math.random() < 0.02) {
            setCell(w/2, h/2, Color.BLUE);
        }
        if ( Math.random() < 0.02) {
            setCell(w/2, h/2-1, Color.BLUE);
        }
        if ( Math.random() < 0.02) {
            setCell(w/2-1, h/2, Color.BLUE);
        }
        if ( Math.random() < 0.02) {
            setCell(w/2-1, h/2-1, Color.BLUE);
        }

        for (ActionListener l : listeners) {
            l.actionPerformed(new ActionEvent(this, 0, null));
        }
    }

    int decay(int waterLine, int v) {
        int n = v;
        if (n <= (waterLine - getDecay())) {
            n += getDecay();
        } else if (n >= waterLine + getDecay()) {
            n -= getDecay();
        } else {
            n = waterLine;
        }
        
        return n;
    }

    /**
     * @return the decay
     */
    public int getDecay() {
        return decay/2 + (int) (decay * Math.random()/2.0);
    }

    /**
     * @param decay the decay to set
     */
    public void setDecay(int decay) {
        this.decay = decay;
    }

    public void addActionListener(ActionListener aThis) {
        listeners.add(aThis);
    }

    private void bleed(List<List<FountainCell>> prev) {
        int bx1, bx2, bx3, bx4;
        int by1, by2, by3, by4;

        clearDiff();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                FountainCell cell = prev.get(x).get(y);
                if (x < w / 2) {
                    if (y < h / 2) {
                        // Q1:  x-1, y-1, x-1:y-1
                        bx1 = x - 1;
                        by1 = y;
                        bx2 = x;
                        by2 = y - 1;
                        bx3 = x - 1;
                        by3 = y - 1;
                        bx4 = x + 1;
                        by4 = y - 1;
                    } else {
                        // Q3:  x-1, y+1, x-1:y+1
                        bx1 = x - 1;
                        by1 = y;
                        bx2 = x;
                        by2 = y + 1;
                        bx3 = x - 1;
                        by3 = y + 1;
                        bx4 = x + 1;
                        by4 = y + 1;
                    }
                } else {
                    if (y < h / 2) {
                        // Q2:  x+1, y-1, x+1:y-1
                        bx1 = x + 1;
                        by1 = y;
                        bx2 = x;
                        by2 = y - 1;
                        bx3 = x + 1;
                        by3 = y - 1;
                        bx4 = x - 1;
                        by4 = y - 1;
                    } else {
                        // Q4:  x+1, y+1, x+1:y+1
                        bx1 = x + 1;
                        by1 = y;
                        bx2 = x;
                        by2 = y + 1;
                        bx3 = x + 1;
                        by3 = y + 1;
                        bx4 = x - 1;
                        by4 = y + 1;
                    }
                }

                mush(cell, bx1, by1);
                mush(cell, bx2, by2);
                mush(cell, bx3, by3);
                mush(cell, bx4, by4);

//                bx4 = x;
//                by4 = y;
//                // Are we on the seam?
//                if (x == w / 2) {
//                    bx4--;
//                } else if (x == (w / 2) - 1) {
//                    bx4++;
//                }
//
//                if (y == h / 2) {
//                    by4--;
//                } else if (y == (h / 2) - 1) {
//                    by4++;
//                }
//
//                if (bx4 != x && by4 != y) {
//                    mush(cell, bx4, by4);
//                }
            }
        }
    }

    private void mush(FountainCell cell, int x, int y) {
        if (inRange(x, y)) {
            //FountainCell p1 = prev.get(bleedX).get(bleedY);
            FountainCell d1 = diff.get(x).get(y);
            d1.addX((int) ((cell.getX() - WATERLINE_XY) /* * (Math.random() - 0.5) * 0.5 */ ));
            d1.addY((int) ((cell.getY() - WATERLINE_XY) /* * (Math.random() - 0.5) * 0.5 */ ));
            d1.addZ((int) ((cell.getZ() - WATERLINE_Z) * (Math.random() - 0.5) * 0.5));

        }

    }

    private boolean inRange(int x, int y) {
        return (x >= 0 && x < w) && (y >= 0 && y < h);
    }

    private void clearDiff() {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                diff.get(x).get(y).clear();
            }
        }

    }

    private void merge(List<List<FountainCell>> diff) {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                FountainCell cell = cells.get(x).get(y);
                FountainCell dCell = diff.get(x).get(y);
                cell.setX(cell.getX() + dCell.getX());
                cell.setY(cell.getY() + dCell.getY());
                cell.setZ(cell.getZ() + dCell.getZ());
            }
        }

    }

    public FountainCell getCell(int x, int y) {
        return cells.get(x).get(y);
    }
}
