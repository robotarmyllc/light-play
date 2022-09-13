/*
 * DMX address information including universe.
 */
package com.robotarmy.dmx;

/**
 *
 * @author mark
 */
public class Address {
    private int universe;
    private int address;
    public final static int MAX_UNIVERSE = 5;
    public final static int MAX_ADDRESS = 512;
    
    /**
     *
     * @param universe
     * @param address
     * @throws InvalidUniverseException
     * @throws InvalidAddressException
     */
    public Address( int universe, int address ) throws InvalidUniverseException, InvalidAddressException {
        setUniverse(universe);
        setAddress(address);
    }

    /**
     *
     */
    public Address() {
        this.address = 0;
        this.address = -1;  // No universe set.
    }

    /**
     * @return the universe
     */
    public int getUniverse() {
        return universe;
    }

    /**
     * @param universe the universe to set
     * @throws com.robotarmy.dmx.InvalidUniverseException
     */
    public final void setUniverse(int universe) throws InvalidUniverseException {
        if ( universe < -1 || universe > MAX_UNIVERSE ) {
            throw new InvalidUniverseException();
        }
       
        this.universe = universe;
    }

    /**
     * @return the address
     */
    public int getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     * @throws com.robotarmy.dmx.InvalidAddressException
     */
    public final void setAddress(int address) throws InvalidAddressException {
        if ( address < -1 || address > 512 ) {
            throw new InvalidAddressException();
        }
        this.address = address;
    }
    
}
