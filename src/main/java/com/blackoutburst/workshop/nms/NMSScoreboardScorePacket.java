package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class NMSScoreboardScorePacket {

    public static void send(Player player, NMSScoreboardScore score) {
        try {
            final Class<?> packetClass = NMS.getClass("PacketPlayOutScoreboardScore");
            final Class<?> scoreClass = NMS.getClass("ScoreboardScore");

            final Constructor<?> packetConstructor = packetClass.getConstructor(scoreClass);

            final Object packet = packetConstructor.newInstance(score.scoreboardScore);

            NMS.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void remove(Player player, NMSScoreboardScore score) {
        try {
            final Class<?> packetClass = NMS.getClass("PacketPlayOutScoreboardScore");

            final Constructor<?> packetConstructor = packetClass.getConstructor(String.class);

            final Object packet = packetConstructor.newInstance(score.name);

            NMS.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
