package com.blackoutburst.workshop.core.blocks;

import org.bukkit.Location;
import org.bukkit.World;

public class NeededBlock {

    protected Location location;

    protected World world;

    protected int index;

    public NeededBlock(Location location, int index, World world) {
        this.location = location;
        this.index = index;
        this.world = world;
    }

    public Location getLocation() {return location;}

    public World getWorld() {return world;}

    public int getIndex() {return index;}

    public void setIndex(int i) {
        this.index = i;
    }
}

