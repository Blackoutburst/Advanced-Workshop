package com.blackoutburst.workshop.core;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class MaterialBlock {

    protected Material[] types;

    protected Byte[] data;

    protected Location location;

    protected World world;

    protected String[][] tools;

    protected int index;

    public MaterialBlock(Material[] types, Byte[] data, Location location, World world, String[][] tools, int index) {
        this.types = types;
        this.data = data;
        this.location = location;
        this.world = world;
        this.tools = tools;
        this.index = index;
    }

    public Material getType() {
        return types[index];
    }

    public Material[] getTypes() {
        return types;
    }

    public String[][] getAllTools() {
        return tools;
    }

    public Byte getData() {return data[index];}

    public Location getLocation() {return location;}

    public World getWorld() {return world;}

    public String[] getTools() {
        if (tools.length == 0) {
            return new String[]{""};
        }
        return tools[index];}

    public int getIndex() {return index;}

    public void setIndex(int i) {
        this.index = i;
    }

    public void setTypes(Material[] types) { this.types = types; }
}
