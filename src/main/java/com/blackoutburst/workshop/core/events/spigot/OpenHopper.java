package com.blackoutburst.workshop.core.events.spigot;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class OpenHopper {
    public static void execute(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.HOPPER) return;

        event.setCancelled(true);
    }
}
