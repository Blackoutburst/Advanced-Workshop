package com.blackoutburst.workshop.utils;

import com.blackoutburst.workshop.core.GameOptions;
import com.blackoutburst.workshop.core.WSPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MiscUtils {
    public static <E> List<List<E>> transpose2dList(List<List<E>> startList) {
        List<List<E>> transposedList = new ArrayList<>();

        int max_size = 0;

        for (List<E> subList : startList) {
            if (subList.size() > max_size) { max_size =  subList.size(); }
        }

        for (int i = 0; i < max_size; i++) {
            List<E> tempList = new ArrayList<>();

            for (List<E> subList : startList) {
                if (subList.size() > i) {
                    tempList.add(subList.get(i));
                }
            }
            transposedList.add(tempList);
        }
        return transposedList;
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        PlayerConnection connection = craftPlayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + title + "'}");
        IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + subtitle + "'}");
        PacketPlayOutTitle timePacket = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON);
        connection.sendPacket(timePacket);
        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);
    }

    public static boolean checkTimeLimit(WSPlayer wsplayer) {
        GameOptions gameoptions = wsplayer.getGameOptions();

        if (wsplayer.getTimers().getMapBegin() == null) return false;
        if ((Duration.between(wsplayer.getTimers().getMapBegin(), Instant.now()).toMillis() / 1000.0f)
                > gameoptions.getTimeLimit()) {
            int timeLimit = (int) gameoptions.getTimeLimit();
            String timeType;
            switch (timeLimit) {
                case 60:
                    timeType = ".1mCrafts";
                    break;
                case 90:
                    timeType = ".90sCrafts";
                    break;
                case 120:
                    timeType = ".2mCrafts";
                    break;
                default:
                    GameUtils.endGame(wsplayer);
                    return true;
            }

            Integer craftPB = DBUtils.getData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + timeType, Integer.class);
            if (craftPB == null) craftPB = Integer.MIN_VALUE;

            int craftsCompleted = wsplayer.getCurrentCraftIndex() - 1;
            if (craftsCompleted > craftPB && gameoptions.getRandomType() == 'N')
                DBUtils.saveData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + timeType, craftsCompleted , Integer.class);

            if (craftPB != Integer.MIN_VALUE && craftsCompleted > craftPB && gameoptions.getRandomType() == 'N') {
                Webhook.send("**"+ wsplayer.getPlayer().getName() + "** got a new PB on the map **" + wsplayer.getPlayArea().getType() + "** for crafts in **" + timeLimit + "s**!\\nCrafts: **" + craftsCompleted + "**\\nImprovement: **+" + (craftsCompleted - craftPB) + "**");
                wsplayer.getPlayer().sendMessage("§d§lPB! (+" + (craftsCompleted - craftPB) + " craft" + ((craftsCompleted - craftPB) == 1 ? ")" : "s)"));

            }
            GameUtils.endGame(wsplayer);
            return true;
        }
        return false;
    }
}
