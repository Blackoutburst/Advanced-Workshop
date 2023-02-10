package com.blackoutburst.workshop.core;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;

public class MaterialBlock {

    protected Material type;
    protected byte data;
    protected Location location;

    protected World world;

    protected List<Integer> tools;

    public MaterialBlock(Material type, byte data, Location location, World world, List<Integer> tools) {
        this.type = type;
        this.data = data;
        this.location = location;
        this.world = world;
        this.tools = tools;
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

    public World getWorld() {return world;}

    public List<Integer> getTools() {
        return tools;
    }
}
