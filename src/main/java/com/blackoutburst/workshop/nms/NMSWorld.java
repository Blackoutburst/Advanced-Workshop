package com.blackoutburst.workshop.nms;

import org.bukkit.World;

public class NMSWorld {

    public static Object getWorld(World world) {
        try {
            return world.getClass().getMethod("getHandle").invoke(world);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
