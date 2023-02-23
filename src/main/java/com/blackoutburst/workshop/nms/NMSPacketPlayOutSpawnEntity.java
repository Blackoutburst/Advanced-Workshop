package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class NMSPacketPlayOutSpawnEntity {

    public static void send(Player player, NMSEntity entity, int data) {
        try {
            Class<?> packetClass = NMS.getClass("PacketPlayOutSpawnEntity");
            Class<?> entityClass = NMS.getClass("Entity");

            Constructor<?> packetConstructor = packetClass.getConstructor(entityClass, int.class);

            Object packet = packetConstructor.newInstance(entity.entity, data);

            NMS.sendPacket(player, packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void send(Player player, NMSEntity entity) {
        try {
            Class<?> packetClass = NMS.getClass("PacketPlayOutSpawnEntity");
            Class<?> entityClass = NMS.getClass("Entity");

            Constructor<?> packetConstructor = packetClass.getConstructor(entityClass);

            Object packet = packetConstructor.newInstance(entity.entity);

            NMS.sendPacket(player, packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
