package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.Main;
import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Furnace;
import org.bukkit.block.Smoker;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class FurnaceUtils {

    private static void fastCook(Furnace furnace, ItemStack stack, Material output) {
        furnace.getInventory().setSmelting(new ItemStack(Material.AIR));
        furnace.getInventory().setResult(new ItemStack(output, stack.getAmount()));
    }

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
                    switch (stack.getType()) {
                        case POTATO: fastCook(furnace, stack, Material.BAKED_POTATO); break;
                        case CHICKEN: fastCook(furnace, stack, Material.COOKED_CHICKEN); break;
                        case COD: fastCook(furnace, stack, Material.COOKED_COD); break;
                        case SALMON: fastCook(furnace, stack, Material.SALMON); break;
                        case PORKCHOP: fastCook(furnace, stack, Material.COOKED_PORKCHOP); break;
                        case BEEF: fastCook(furnace, stack, Material.COOKED_BEEF); break;
                        case CLAY_BALL: fastCook(furnace, stack, Material.BRICK); break;
                        case SAND: fastCook(furnace, stack, Material.GLASS); break;
                        case GOLD_ORE: fastCook(furnace, stack, Material.GOLD_INGOT); break;
                        case IRON_ORE: fastCook(furnace, stack, Material.IRON_INGOT); break;
                        case NETHERRACK: fastCook(furnace, stack, Material.NETHER_BRICK); break;
                        case COBBLESTONE: fastCook(furnace, stack, Material.STONE); break;
                        case CACTUS: fastCook(furnace, stack, Material.GREEN_DYE); break;
                        case OAK_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case BIRCH_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case SPRUCE_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case JUNGLE_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case ACACIA_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case DARK_OAK_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case COAL_ORE: fastCook(furnace, stack, Material.COAL); break;
                        case DIAMOND_ORE: fastCook(furnace, stack, Material.DIAMOND); break;
                        case EMERALD_ORE: fastCook(furnace, stack, Material.EMERALD); break;
                        case LAPIS_ORE: fastCook(furnace, stack, Material.LAPIS_LAZULI); break;
                        case NETHER_QUARTZ_ORE: fastCook(furnace, stack, Material.QUARTZ); break;
                        case REDSTONE_ORE: fastCook(furnace, stack, Material.REDSTONE); break;
                    }
                }
                count++;
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 1L, 0L);
    }

    public static void instantSmelt(BlastFurnace furnace) {
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count >= 200) {
                    this.cancel();
                }

                ItemStack stack = furnace.getInventory().getSmelting();

                if (stack != null) {
                    switch (stack.getType()) {
                        case POTATO: fastCook(furnace, stack, Material.BAKED_POTATO); break;
                        case CHICKEN: fastCook(furnace, stack, Material.COOKED_CHICKEN); break;
                        case COD: fastCook(furnace, stack, Material.COOKED_COD); break;
                        case SALMON: fastCook(furnace, stack, Material.SALMON); break;
                        case PORKCHOP: fastCook(furnace, stack, Material.COOKED_PORKCHOP); break;
                        case BEEF: fastCook(furnace, stack, Material.COOKED_BEEF); break;
                        case CLAY_BALL: fastCook(furnace, stack, Material.BRICK); break;
                        case SAND: fastCook(furnace, stack, Material.GLASS); break;
                        case GOLD_ORE: fastCook(furnace, stack, Material.GOLD_INGOT); break;
                        case IRON_ORE: fastCook(furnace, stack, Material.IRON_INGOT); break;
                        case NETHERRACK: fastCook(furnace, stack, Material.NETHER_BRICK); break;
                        case COBBLESTONE: fastCook(furnace, stack, Material.STONE); break;
                        case CACTUS: fastCook(furnace, stack, Material.GREEN_DYE); break;
                        case OAK_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case BIRCH_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case SPRUCE_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case JUNGLE_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case ACACIA_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case DARK_OAK_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case COAL_ORE: fastCook(furnace, stack, Material.COAL); break;
                        case DIAMOND_ORE: fastCook(furnace, stack, Material.DIAMOND); break;
                        case EMERALD_ORE: fastCook(furnace, stack, Material.EMERALD); break;
                        case LAPIS_ORE: fastCook(furnace, stack, Material.LAPIS_LAZULI); break;
                        case NETHER_QUARTZ_ORE: fastCook(furnace, stack, Material.QUARTZ); break;
                        case REDSTONE_ORE: fastCook(furnace, stack, Material.REDSTONE); break;
                    }
                }
                count++;
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 1L, 0L);
    }

    public static void instantSmelt(Smoker furnace) {
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count >= 200) {
                    this.cancel();
                }

                ItemStack stack = furnace.getInventory().getSmelting();

                if (stack != null) {
                    switch (stack.getType()) {
                        case POTATO: fastCook(furnace, stack, Material.BAKED_POTATO); break;
                        case CHICKEN: fastCook(furnace, stack, Material.COOKED_CHICKEN); break;
                        case COD: fastCook(furnace, stack, Material.COOKED_COD); break;
                        case SALMON: fastCook(furnace, stack, Material.SALMON); break;
                        case PORKCHOP: fastCook(furnace, stack, Material.COOKED_PORKCHOP); break;
                        case BEEF: fastCook(furnace, stack, Material.COOKED_BEEF); break;
                        case CLAY_BALL: fastCook(furnace, stack, Material.BRICK); break;
                        case SAND: fastCook(furnace, stack, Material.GLASS); break;
                        case GOLD_ORE: fastCook(furnace, stack, Material.GOLD_INGOT); break;
                        case IRON_ORE: fastCook(furnace, stack, Material.IRON_INGOT); break;
                        case NETHERRACK: fastCook(furnace, stack, Material.NETHER_BRICK); break;
                        case COBBLESTONE: fastCook(furnace, stack, Material.STONE); break;
                        case CACTUS: fastCook(furnace, stack, Material.GREEN_DYE); break;
                        case OAK_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case BIRCH_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case SPRUCE_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case JUNGLE_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case ACACIA_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case DARK_OAK_LOG: fastCook(furnace, stack, Material.CHARCOAL); break;
                        case COAL_ORE: fastCook(furnace, stack, Material.COAL); break;
                        case DIAMOND_ORE: fastCook(furnace, stack, Material.DIAMOND); break;
                        case EMERALD_ORE: fastCook(furnace, stack, Material.EMERALD); break;
                        case LAPIS_ORE: fastCook(furnace, stack, Material.LAPIS_LAZULI); break;
                        case NETHER_QUARTZ_ORE: fastCook(furnace, stack, Material.QUARTZ); break;
                        case REDSTONE_ORE: fastCook(furnace, stack, Material.REDSTONE); break;
                    }
                }
                count++;
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 1L, 0L);
    }

}
