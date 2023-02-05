package com.blackoutburst.workshop.core.events;

import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage {

    public static void execute(EntityDamageEvent event) {
        event.setCancelled(true);
    }
}
