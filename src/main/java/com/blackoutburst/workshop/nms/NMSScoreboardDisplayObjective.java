package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class NMSScoreboardDisplayObjective {

    public static void send(Player player, NMSScoreboard scoreboard) {
        try {
            Class<?> packetClass = NMS.getClass("PacketPlayOutScoreboardDisplayObjective");
            Class<?> objectiveClass = NMS.getClass("ScoreboardObjective");

            Constructor<?> packetConstructor = packetClass.getConstructor(int.class, objectiveClass);

            Object packet = packetConstructor.newInstance(scoreboard.slot.ordinal(), scoreboard.objective);

            NMS.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
