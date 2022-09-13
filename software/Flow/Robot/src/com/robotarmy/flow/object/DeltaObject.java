/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robotarmy.flow.object;

import com.robotarmy.dmx.Address;
import com.robotarmy.flow.Position3D;
import java.awt.Color;

/**
 *
 * @author mark
 */
public class DeltaObject extends AFlowObject {

    private static final long serialVersionUID = 118563316887381077L;

    private static final String TYPE = "delta";

    //private int address=0;
    private int x = 0;
    private int y = 0;
    private final Address address = new Address();
    private final Position3D position = new Position3D();
    private final Color color = new Color(0, 0, 0);

    public DeltaObject(AFlowObject newParent) {
        super(newParent);

        setDescription(TYPE);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * @return the DMX address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @return the x position relative to the parent.
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y position relative to the parent.
     */
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the position
     */
    public Position3D getPosition() {
        return position;
    }

    public void setPosition(Position3D p) {
        position.setX(p.getX());
        position.setY(p.getY());
        position.setZ(p.getZ());
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

}
