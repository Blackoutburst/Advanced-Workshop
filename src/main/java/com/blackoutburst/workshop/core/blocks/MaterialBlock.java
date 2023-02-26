package com.blackoutburst.workshop.core.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class MaterialBlock {

    protected Material[] types;

    protected Location location;

    protected World world;

    protected ItemStack[][] tools;

    protected int index;

    public MaterialBlock(Material[] types, Location location, World world, ItemStack[][] tools, int index) {
        this.types = types;
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

    public ItemStack[][] getAllTools() {
        return tools;
    }

    public Location getLocation() {return location;}

    public World getWorld() {return world;}

    public ItemStack[] getTools() {
        if (tools.length == 0) {
            return new ItemStack[]{};
        }
        return tools[index];}

    public int getIndex() {return index;}

    public void setIndex(int i) {
        this.index = i;
    }

    public void setTypes(Material[] types) { this.types = types; }
}
