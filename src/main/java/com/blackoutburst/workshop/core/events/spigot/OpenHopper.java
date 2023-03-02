package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OpenHopper {
    public static void execute(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.HOPPER) return;
        WSPlayer wsPlayer = WSPlayer.getFromPlayer(event.getPlayer());
        if (wsPlayer == null) return;
        if (!wsPlayer.isInGame()) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        event.setCancelled(true);
    }
}
