package com.blackoutburst.workshop.nms;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class NMSWorld {

    /**
     * Get the world instance
     *
     * @param world the world used to create a NMS copies of it
     * @return the NMS world
     */
    public static Object getWorld(World world) {
        try {
            final Class<?> serverClass = NMS.getClass("MinecraftServer");
            final Object server = serverClass.getMethod("getServer").invoke(null);

            int id = 0;
            for (World w : Bukkit.getWorlds()) {
                if (w.getUID().equals(world.getUID())) break;
                id++;
            }

            return serverClass.getMethod("getWorldServer", int.class).invoke(server, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
