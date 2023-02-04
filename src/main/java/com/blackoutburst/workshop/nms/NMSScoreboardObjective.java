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
            final Class<?> packetClass = NMS.getClass("PacketPlayOutScoreboardObjective");
            final Class<?> objectiveClass = NMS.getClass("ScoreboardObjective");

            final Constructor<?> packetConstructor = packetClass.getConstructor(objectiveClass, int.class);

            final Object packet = packetConstructor.newInstance(scoreboard.objective, option.ordinal());

            NMS.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
