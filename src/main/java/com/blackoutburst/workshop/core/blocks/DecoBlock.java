package com.blackoutburst.workshop.core.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

public class DecoBlock {

    protected BlockData[] blocks;

    protected Location location;

    protected World world;

    protected int index;

    public DecoBlock(BlockData[] blocks, Location location, World world, int index) {
        this.blocks = blocks;
        this.location = location;
        this.world = world;
        this.index = index;
    }

    public BlockData getType() { return blocks[index]; }

    public BlockData[] getTypes() { return blocks; }

    public Location getLocation() {return location;}

    public World getWorld() {return world;}

    public int getIndex() {return index;}

    public void setIndex(int i) {
        this.index = i;
    }

    public void setTypes(BlockData[] blocks) { this.blocks =  blocks; }
}
