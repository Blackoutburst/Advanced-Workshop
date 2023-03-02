package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.ClientVersion;
import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.events.listeners.NMSListener;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.nms.NMSEntity;
import com.blackoutburst.workshop.nms.NettyListener;
import com.blackoutburst.workshop.utils.files.DBUtils;

import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join {

    @SuppressWarnings("unchecked")
    public static void execute(PlayerJoinEvent event) {
        int protocolVersion = Main.VIA_API.getPlayerVersion(event.getPlayer());

        WSPlayer wsPlayer = new WSPlayer(event.getPlayer(), ClientVersion.getFromProtocolVersion(protocolVersion));
        DBUtils.createPlayerData(event.getPlayer());
        Main.players.add(wsPlayer);
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        DBUtils.saveData(event.getPlayer(), "name", event.getPlayer().getName(), String.class);

        NettyListener.init(event.getPlayer(), new NMSListener());
        NMSEntity.load(wsPlayer);
        event.getPlayer().teleport(Main.spawn);
    }
}
