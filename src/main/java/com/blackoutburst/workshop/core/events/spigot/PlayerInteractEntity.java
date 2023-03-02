package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;

import com.blackoutburst.workshop.utils.minecraft.EntityUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractEntity {

    public static void execute(PlayerInteractEntityEvent event) {
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        WSPlayer wsPlayer = WSPlayer.getFromPlayer(event.getPlayer());
        if (wsPlayer == null || wsPlayer.isWaiting()) return;

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        switch (entity.getType()) {
            case BLAZE -> EntityUtils.blaze(player);
            case CHICKEN -> EntityUtils.chicken(player);
            case HOGLIN -> EntityUtils.hoglin(player);
            case WITHER_SKELETON -> EntityUtils.witherSkeleton(player);
            case VILLAGER -> EntityUtils.villager(wsPlayer, player);
        }

        if (wsPlayer.isInGame())
            event.setCancelled(true);
    }
}
