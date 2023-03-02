package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.Launchpad;
import com.blackoutburst.workshop.core.WSPlayer;

import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove {

    public static void execute(PlayerMoveEvent event) {
        final WSPlayer qp = WSPlayer.getFromPlayer(event.getPlayer());
        if (qp == null) return;

        Launchpad.walkOn(event, qp);
    }

}
