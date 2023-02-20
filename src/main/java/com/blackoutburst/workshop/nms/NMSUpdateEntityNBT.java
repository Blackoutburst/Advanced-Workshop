package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class NMSUpdateEntityNBT {

    /**
     * Edit an entity metadata
     *
     * @param player the player that will receive the packet
     * @param entity the entity updated
     */
    public static void send(Player player, NMSEntities entity) {
        try {
            final Class<?> packetClass = NMS.getClass("PacketPlayOutUpdateEntityNBT");
            final Class<?> nbtTagClass = NMS.getClass("NBTTagCompound");

            final Constructor<?> packetConstructor = packetClass.getConstructor(int.class, nbtTagClass);

            final Object packet = packetConstructor.newInstance(entity.getID(), entity.getNBTTag());

            NMS.sendPacket(player, packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
