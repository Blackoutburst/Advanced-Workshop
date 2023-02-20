package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class NMSEntityHeadRotation {

    /**
     * Edit an entity metadata
     *
     * @param player the player that will receive the packet
     * @param entity the entity updated
     */
    public static void send(Player player, NMSEntities entity, float yaw) {
        try {
            final Class<?> packetClass = NMS.getClass("PacketPlayOutEntityHeadRotation");
            final Class<?> entityClass = NMS.getClass("Entity");

            final Constructor<?> packetConstructor = packetClass.getConstructor(entityClass, byte.class);

            final Object packet = packetConstructor.newInstance(entity.entity, (byte)((int)(yaw * 256.0F / 360.0F)));

            NMS.sendPacket(player, packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
