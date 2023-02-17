package com.blackoutburst.workshop.core.events;

import org.bukkit.event.player.PlayerEggThrowEvent;

public class EggThrow {
    public static void execute(PlayerEggThrowEvent event) {
        event.setHatching(false);
    }
}
