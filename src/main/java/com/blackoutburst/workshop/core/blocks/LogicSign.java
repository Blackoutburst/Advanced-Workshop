package com.blackoutburst.workshop.core.blocks;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class LogicSign {

    protected String text;

    protected BlockFace direction;

    protected Location location;

    public LogicSign(String text, BlockFace direction, Location location) {
        this.text = text;
        this.direction = direction;
        this.location = location;
    }

    public BlockFace getDirection() { return direction; }

    public String getText() { return text; }

    public Location getLocation() { return location; }

    public void setDirection(BlockFace direction) { this.direction = direction; }

    public void setText(String text) { this.text = text; }

    public void setLocation(Location location) { this.location = location; }
}
