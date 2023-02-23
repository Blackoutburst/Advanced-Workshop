package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.guis.CraftGUI;
import com.blackoutburst.workshop.guis.CraftSelectorGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClick {

    private static void preventArmorClick(InventoryClickEvent event, WSPlayer wsplayer) {
        if (wsplayer != null && wsplayer.isInGame() && event.getSlotType().equals(InventoryType.SlotType.ARMOR))
            event.setCancelled(true);
    }


    // TODO not working properly
    private static void preventCraftingTableUse(InventoryClickEvent event, WSPlayer wsplayer) {
        if (wsplayer != null && wsplayer.isInGame() && event.getInventory().getType().equals(InventoryType.CRAFTING) &&
                event.getSlotType().equals(InventoryType.SlotType.CRAFTING)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage("§cYou must use the crafting table!");
        }
    }

    public static void execute(InventoryClickEvent event) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer((Player) event.getWhoClicked());

        switch (event.getView().getTitle()) {
            case CraftGUI.NAME ->
                    event.setCancelled(CraftGUI.click(event.getClickedInventory(), event.getSlot(), (Player) event.getWhoClicked(), event.getView().getTitle()));
            case CraftSelectorGUI.NAME ->
                    event.setCancelled(CraftSelectorGUI.click(event.getClickedInventory(), event.getSlot(), (Player) event.getWhoClicked(), event.getView().getTitle()));
        }

        preventArmorClick(event, wsplayer);
        preventCraftingTableUse(event, wsplayer);
    }
}
