package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class NMSPacketPlayOutScoreboardScore {

    private enum Action {
        CHANGE,
        REMOVE
    }

    public static void send(Player player, NMSScoreboardScore score) {
        try {
            Class<?> packetClass = NMS.getClass("PacketPlayOutScoreboardScore");
            Class<?> scoreboardServerActionEnum = NMS.getClass("ScoreboardServer$Action");

            Constructor<?> packetConstructor = packetClass.getConstructor(scoreboardServerActionEnum, String.class, String.class, int.class);

            Object packet = packetConstructor.newInstance(scoreboardServerActionEnum.getEnumConstants()[Action.CHANGE.ordinal()], player.getName(), score.name, score.score);

            NMS.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void remove(Player player, NMSScoreboardScore score) {
        try {
            Class<?> packetClass = NMS.getClass("PacketPlayOutScoreboardScore");
            Class<?> scoreboardServerActionEnum = NMS.getClass("ScoreboardServer$Action");

            Constructor<?> packetConstructor = packetClass.getConstructor(scoreboardServerActionEnum, String.class, String.class, int.class);

            Object packet = packetConstructor.newInstance(scoreboardServerActionEnum.getEnumConstants()[Action.REMOVE.ordinal()], player.getName(), score.name, score.score);

            NMS.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
