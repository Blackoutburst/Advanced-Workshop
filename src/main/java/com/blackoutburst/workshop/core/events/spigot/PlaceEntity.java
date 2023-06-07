package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.List;

public class PlaceEntity {
    public static void execute(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Player player = event.getPlayer();
        WSPlayer wsplayer = WSPlayer.getFromPlayer(player);
        if (wsplayer == null) return;

        if (!wsplayer.isInGame()) return;
        List<Material> banned_items = Arrays.asList(
                Material.ARMOR_STAND, Material.OAK_BOAT, Material.BIRCH_BOAT, Material.SPRUCE_BOAT,
                Material.JUNGLE_BOAT, Material.ACACIA_BOAT, Material.DARK_OAK_BOAT, Material.MINECART,
                Material.COMMAND_BLOCK_MINECART, Material.TNT_MINECART, Material.HOPPER_MINECART,
                Material.FURNACE_MINECART, Material.CHEST_MINECART, Material.ACACIA_CHEST_BOAT,
                Material.SPRUCE_CHEST_BOAT, Material.OAK_CHEST_BOAT, Material.DARK_OAK_CHEST_BOAT,
                Material.JUNGLE_CHEST_BOAT, Material.JUNGLE_CHEST_BOAT, Material.MANGROVE_CHEST_BOAT,
                Material.BAMBOO_CHEST_RAFT, Material.MANGROVE_BOAT, Material.BAMBOO_RAFT
        );

        if (!banned_items.contains(event.getItem() == null ? null : event.getItem().getType())) return;

        event.setUseItemInHand(Event.Result.DENY);
    }
}
