package com.blackoutburst.workshop.core.events;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join {

    public static void execute(PlayerJoinEvent event) {
        Main.players.add(new WSPlayer(event.getPlayer()));
    }
}
