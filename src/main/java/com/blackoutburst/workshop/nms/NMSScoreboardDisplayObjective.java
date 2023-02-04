package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class NMSScoreboardDisplayObjective {

    public static void send(Player player, NMSScoreboard scoreboard) {
        try {
            final Class<?> packetClass = NMS.getClass("PacketPlayOutScoreboardDisplayObjective");
            final Class<?> objectiveClass = NMS.getClass("ScoreboardObjective");

            final Constructor<?> packetConstructor = packetClass.getConstructor(int.class, objectiveClass);

            final Object packet = packetConstructor.newInstance(scoreboard.slot.ordinal(), scoreboard.objective);

            NMS.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
