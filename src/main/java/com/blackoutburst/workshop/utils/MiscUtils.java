package com.blackoutburst.workshop.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MiscUtils {
    public static <E> List<List<E>> transpose2dList(List<List<E>> startList) {
        List<List<E>> transposedList = new ArrayList<>();

        int max_size = 0;

        for (List<E> subList : startList) {
            if (subList.size() > max_size) { max_size =  subList.size(); }
        }

        for (int i = 0; i < max_size; i++) {
            List<E> tempList = new ArrayList<>();

            for (List<E> subList : startList) {
                if (subList.size() > i) {
                    tempList.add(subList.get(i));
                }
            }
            transposedList.add(tempList);
        }
        return transposedList;
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        PlayerConnection connection = craftPlayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + title + "'}");
        IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + subtitle + "'}");
        PacketPlayOutTitle timePacket = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON);
        connection.sendPacket(timePacket);
        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);
    }
}
