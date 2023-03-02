package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.minecraft.EntityUtils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity {

    public static void execute(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            WSPlayer wsPlayer = WSPlayer.getFromPlayer(player);
            if (wsPlayer == null || wsPlayer.isWaiting()) return;

            Entity entity = event.getEntity();

            switch (entity.getType()) {
                case BLAZE -> EntityUtils.blaze(player);
                case CHICKEN -> EntityUtils.chicken(player);
                case HOGLIN -> EntityUtils.hoglin(player);
                case WITHER_SKELETON -> EntityUtils.witherSkeleton(player);
            }

            if (wsPlayer.isInGame()) {
                event.setCancelled(true);
            }
        }
    }
}
