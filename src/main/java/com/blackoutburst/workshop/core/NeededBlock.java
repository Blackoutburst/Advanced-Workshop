package com.blackoutburst.workshop.core;

import org.bukkit.Location;

public class NeededBlock {

    protected Location location;

    protected int index;

    public NeededBlock(Location location, int index) {
        this.location = location;
        this.index = index;
    }

    public Location getLocation() {return location;}

    public int getIndex() {return index;}

    public void setIndex(int i) {
        this.index = i;
    }
}

