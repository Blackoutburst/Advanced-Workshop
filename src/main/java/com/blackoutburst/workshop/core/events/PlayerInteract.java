package com.blackoutburst.workshop.core.events;

import com.blackoutburst.workshop.core.ScanWand;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract {

    public static void execute(PlayerInteractEvent event) {
        ScanWand.rightClick(event);
    }
}
