package com.blackoutburst.workshop.core.events;

import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.List;

public class PlaceEntity {
    public static void execute(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        WSPlayer wsplayer = WSPlayer.getFromPlayer(player);
        if (!wsplayer.isInGame()) return;
        List<Material> banned_items = Arrays.asList(
                Material.ARMOR_STAND, Material.BOAT, Material.MINECART, Material.COMMAND_MINECART,
                Material.EXPLOSIVE_MINECART, Material.HOPPER_MINECART, Material.POWERED_MINECART,
                Material.STORAGE_MINECART
        );

        if (!banned_items.contains(player.getItemInHand().getType())) return;

        event.setUseItemInHand(Event.Result.DENY);
    }
}
