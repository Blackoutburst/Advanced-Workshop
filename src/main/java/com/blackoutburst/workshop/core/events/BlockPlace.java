package com.blackoutburst.workshop.core.events;

import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace {

    public static void execute(BlockPlaceEvent event) {
        WSPlayer player = WSPlayer.getFromPlayer(event.getPlayer());
        if (player != null)
            event.setCancelled(player.isInGame());
    }
}
