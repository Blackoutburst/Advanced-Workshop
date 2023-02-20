package com.blackoutburst.workshop.core.events.spigot;

import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage {

    public static void execute(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;
        event.setCancelled(true);
    }
}
