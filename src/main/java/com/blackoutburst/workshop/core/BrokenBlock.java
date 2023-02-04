package com.blackoutburst.workshop.core;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class BrokenBlock {

    protected Material type;
    protected byte data;
    protected Location location;

    protected World world;

    public BrokenBlock(Material type, byte data, Location location, World world) {
        this.type = type;
        this.data = data;
        this.location = location;
        this.world = world;
    }

    public Material getType() {
        return type;
    }

    public byte getData() {
        return data;
    }

    public Location getLocation() {
        return location;
    }

    public World getWorld() {
        return world;
    }
}
