package com.blackoutburst.workshop.core.blocks;

import org.bukkit.block.data.BlockData;

public class RandomBlock {
    protected BlockData blockData;

    protected boolean priority;

    public RandomBlock(BlockData blockData, boolean priority) {
        this.blockData = blockData;
        this.priority = priority;
    }

    public BlockData getBlockData() { return blockData; }

    public void setBlockData(BlockData blockData) { this.blockData = blockData; }

    public boolean isPriority() { return priority; }

    public void setPriority(boolean priority) { this.priority = priority; }
}
