package com.blackoutburst.workshop.core.events;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.guis.CraftGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClick {

    public static void execute(InventoryClickEvent event) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer((Player) event.getWhoClicked());

        switch (event.getInventory().getName()) {
            case CraftGUI.NAME:
                event.setCancelled(CraftGUI.click(event.getClickedInventory(), event.getSlot(), (Player) event.getWhoClicked()));
            break;
        }

        if (wsplayer != null && wsplayer.isInGame() && event.getInventory().getType().equals(InventoryType.CRAFTING) &&
                event.getSlotType().equals(InventoryType.SlotType.CRAFTING)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage("§cYou must use the crafting table!");
        }
    }
}
