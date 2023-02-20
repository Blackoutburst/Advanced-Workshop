package com.blackoutburst.workshop.core.events.nms;

import com.blackoutburst.workshop.core.NMSEntityInteractEvent;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.nms.NMSEntities;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NMSEntityInteract {

    private static void chicken(Player player) {
        if (WSPlayer.getFromPlayer(player).isWaiting()) return;

        player.getInventory().addItem(new ItemStack(Material.EGG));
    }

    public static synchronized void execute(NMSEntityInteractEvent event) {
        Player player = event.getPlayer();
        NMSEntities entity = event.getEntity();

        if (entity.getType().equals(NMSEntities.EntityType.CHICKEN)) {
            chicken(player);
        }
    }
}
