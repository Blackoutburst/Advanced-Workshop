package com.blackoutburst.workshop.core.events;

import com.blackout.npcapi.core.PacketInteractListener;
import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.NPCInteraction;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.nms.*;
import com.blackoutburst.workshop.utils.DBUtils;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join {

    public static void execute(PlayerJoinEvent event) {
        DBUtils.createPlayerData(event.getPlayer());
        Main.players.add(new WSPlayer(event.getPlayer()));
        PacketInteractListener.init(event.getPlayer(), new NPCInteraction());
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        DBUtils.saveData(event.getPlayer(), "name", event.getPlayer().getName(), String.class);
    }
}
