package com.blackoutburst.workshop.core;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.material.MaterialData;

public class DecoBlock {

    protected Material[] types;

    protected Byte[] data;

    protected Location location;

    protected World world;

    protected int index;

    public DecoBlock(Material[] types, Byte[] data, Location location, World world, int index) {
        this.types = types;
        this.data = data;
        this.location = location;
        this.world = world;
        this.index = index;
    }

    public Material getType() {
        return types[index];
    }

    public Material[] getTypes() {
        return types;
    }

    public Byte getData() {return data[index];}

    public Location getLocation() {return location;}

    public World getWorld() {return world;}

    public int getIndex() {return index;}

    public void setIndex(int i) {
        this.index = i;
    }
}
