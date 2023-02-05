package com.blackoutburst.workshop.core.events;

import com.blackout.npcapi.core.PacketInteractListener;
import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.NPCInteraction;
import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join {

    public static void execute(PlayerJoinEvent event) {
        Main.players.add(new WSPlayer(event.getPlayer()));
        PacketInteractListener.init(event.getPlayer(), new NPCInteraction());
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
    }
}
