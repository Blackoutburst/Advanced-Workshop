package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class NMSPacketPlayOutEntityDestroy {

    public static void send(Player player, int id) {
        try {
            final Class<?> packetClass = NMS.getClass("PacketPlayOutEntityDestroy");

            final Constructor<?> packetConstructor = packetClass.getConstructor();

            final Object packet = packetConstructor.newInstance();

            NMS.setField(packet, "a", new int[]{id});
            NMS.sendPacket(player, packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
