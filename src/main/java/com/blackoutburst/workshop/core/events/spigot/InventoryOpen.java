package com.blackoutburst.workshop.core.events.spigot;

import org.bukkit.event.inventory.InventoryOpenEvent;
public class InventoryOpen {

    public static void execute(InventoryOpenEvent event) {
        event.getPlayer().setItemOnCursor(null);
    }
}
