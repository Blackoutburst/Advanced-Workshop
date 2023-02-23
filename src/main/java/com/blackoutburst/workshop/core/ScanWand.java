package com.blackoutburst.workshop.core;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ScanWand {

    private static boolean isWand(PlayerInventory inv) {
        ItemStack item = inv.getItemInHand();

        return (item.getType() == Material.BLAZE_ROD && item.getItemMeta().getDisplayName().equals("§6Scan wand"));
    }

    public static void leftClick(BlockBreakEvent event) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer(event.getPlayer());
        if (wsplayer == null) return;

        Player p = wsplayer.getPlayer();
        Block b = event.getBlock();

        if (isWand(p.getInventory())) {
            event.setCancelled(true);
            p.sendMessage("§dFirst pos: (" + b.getX() + ", " + b.getY() + " ," + b.getZ() + ")");
            wsplayer.setScanWand1(b.getLocation());
        }
    }

    public static void rightClick(PlayerInteractEvent event) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer(event.getPlayer());
        if (wsplayer == null) return;

        Player p = wsplayer.getPlayer();
        Block b = event.getClickedBlock();

        if (isWand(event.getPlayer().getInventory()) && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            p.sendMessage("§dSecond pos: (" + b.getX() + ", " + b.getY() + " ," + b.getZ() + ")");
            wsplayer.setScanWand2(b.getLocation());
        }
    }
}
