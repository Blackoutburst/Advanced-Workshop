package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityInteractEvent;

public class EntityInteract {

    public static void execute(EntityInteractEvent event) {
        if (event.getEntity() instanceof Player) {
            WSPlayer player = WSPlayer.getFromPlayer((Player) event.getEntity());
            if (player != null) {
                if (event.getBlock().getType() == Material.SOIL)
                    event.setCancelled(player.isInGame());
            }
        }
    }
}
