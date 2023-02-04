package com.blackoutburst.workshop.core.events;

import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage {

    public static void execute(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            WSPlayer player = WSPlayer.getFromPlayer((Player) event.getEntity());
            if (player != null) {
                event.setCancelled(player.isInGame());
            }
        }
    }
}
