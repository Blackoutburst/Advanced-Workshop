package com.blackoutburst.workshop.core.events;

import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClick {

    public static void execute(InventoryClickEvent event) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer((Player) event.getWhoClicked());

        if (wsplayer != null && wsplayer.isInGame() && event.getInventory().getType().equals(InventoryType.CRAFTING) &&
                event.getSlotType().equals(InventoryType.SlotType.CRAFTING)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage("§cYou must use the crafting table!");
        }
    }
}
