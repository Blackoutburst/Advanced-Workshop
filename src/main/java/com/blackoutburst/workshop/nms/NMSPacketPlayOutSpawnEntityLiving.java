package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class NMSPacketPlayOutSpawnEntityLiving {

    public static void send(Player player, NMSEntity entity) {
        try {
            Class<?> packetClass = NMS.getClass("PacketPlayOutSpawnEntityLiving");
            Class<?> entityLivingClass = NMS.getClass("EntityLiving");

            Constructor<?> packetConstructor = packetClass.getConstructor(entityLivingClass);

            Object packet = packetConstructor.newInstance(entity.entity);

            NMS.sendPacket(player, packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
