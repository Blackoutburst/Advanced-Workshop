package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;

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
        if (wsplayer == null) return;

        if (!wsplayer.isInGame()) return;
        List<Material> banned_items = Arrays.asList(
                Material.ARMOR_STAND, Material.OAK_BOAT, Material.BIRCH_BOAT, Material.SPRUCE_BOAT,
                Material.JUNGLE_BOAT, Material.ACACIA_BOAT, Material.DARK_OAK_BOAT, Material.MINECART,
                Material.COMMAND_BLOCK_MINECART, Material.TNT_MINECART, Material.HOPPER_MINECART,
                Material.FURNACE_MINECART, Material.CHEST_MINECART
        );

        if (!banned_items.contains(player.getInventory().getItemInMainHand().getType())) return;

        event.setUseItemInHand(Event.Result.DENY);
    }
}
