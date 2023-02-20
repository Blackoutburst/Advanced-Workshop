package com.blackoutburst.workshop.core.events.spigot;

import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChange {

    public static void execute(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
