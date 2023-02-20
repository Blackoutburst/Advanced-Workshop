package com.blackoutburst.workshop.core.events.spigot;

import com.blackout.npcapi.core.PacketInteractListener;
import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.events.listeners.NMSListener;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.DBUtils;
import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join {

    public static void execute(PlayerJoinEvent event) {
        DBUtils.createPlayerData(event.getPlayer());
        Main.players.add(new WSPlayer(event.getPlayer()));
        PacketInteractListener.init(event.getPlayer(), new NMSListener());
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        DBUtils.saveData(event.getPlayer(), "name", event.getPlayer().getName(), String.class);
    }
}
