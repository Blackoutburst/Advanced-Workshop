package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class NMSScoreboardObjective {

    public enum ObjectiveOption {
        CREATE,
        DELETE,
        EDIT
    }

    public static void send(Player player, NMSScoreboard scoreboard, ObjectiveOption option) {
        try {
            Class<?> packetClass = NMS.getClass("PacketPlayOutScoreboardObjective");
            Class<?> objectiveClass = NMS.getClass("ScoreboardObjective");

            Constructor<?> packetConstructor = packetClass.getConstructor(objectiveClass, int.class);

            Object packet = packetConstructor.newInstance(scoreboard.objective, option.ordinal());

            NMS.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
