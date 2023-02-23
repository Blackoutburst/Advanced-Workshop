package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class NMSPacketPlayOutEntityHeadRotation {

    public static void send(Player player, NMSEntity entity, float yaw) {
        try {
            Class<?> packetClass = NMS.getClass("PacketPlayOutEntityHeadRotation");
            Class<?> entityClass = NMS.getClass("Entity");

            Constructor<?> packetConstructor = packetClass.getConstructor(entityClass, byte.class);

            Object packet = packetConstructor.newInstance(entity.entity, (byte)((int)(yaw * 256.0F / 360.0F)));

            NMS.sendPacket(player, packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
