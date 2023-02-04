package com.blackoutburst.workshop.utils;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class GameUtils {

    public static void instantSmelt(Furnace furnace) {
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                if (count >= 200) {
                    this.cancel();
                }
                ItemStack stack = furnace.getInventory().getSmelting();
                if (stack != null) {
                    if (stack.getType().equals(Material.GOLD_ORE)) {
                        furnace.getInventory().setSmelting(new ItemStack(Material.AIR));
                        furnace.getInventory().setResult(new ItemStack(Material.GOLD_INGOT, stack.getAmount()));
                    }
                    if (stack.getType().equals(Material.POTATO_ITEM)) {
                        furnace.getInventory().setSmelting(new ItemStack(Material.AIR));
                        furnace.getInventory().setResult(new ItemStack(Material.BAKED_POTATO, stack.getAmount()));
                    }
                }
                count++;
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 1L, 0L);
    }

    public static void startRound(WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();
        Random rng = new Random();

        wsplayer.setCurrentCraft(Main.crafts.get(rng.nextInt(Main.crafts.size())));
        wsplayer.getPlayer().sendMessage("You must craft: " + wsplayer.getCurrentCraft().getName());
        player.getInventory().clear();
        wsplayer.getBrokenBlocks().clear();
    }

}
