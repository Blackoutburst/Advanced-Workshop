package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.Main;

import org.bukkit.Material;
import org.bukkit.block.Furnace;
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
                        case POTATO -> fastCook(furnace, stack, Material.BAKED_POTATO);
                        case CHICKEN -> fastCook(furnace, stack, Material.COOKED_CHICKEN);
                        case COD -> fastCook(furnace, stack, Material.COOKED_COD);
                        case SALMON -> fastCook(furnace, stack, Material.SALMON);
                        case PORKCHOP -> fastCook(furnace, stack, Material.COOKED_PORKCHOP);
                        case BEEF -> fastCook(furnace, stack, Material.COOKED_BEEF);
                        case CLAY_BALL -> fastCook(furnace, stack, Material.BRICK);
                        case SAND -> fastCook(furnace, stack, Material.GLASS);
                        case GOLD_ORE, NETHER_GOLD_ORE -> fastCook(furnace, stack, Material.GOLD_INGOT);
                        case ANCIENT_DEBRIS -> fastCook(furnace, stack, Material.NETHERITE_SCRAP);
                        case IRON_ORE -> fastCook(furnace, stack, Material.IRON_INGOT);
                        case NETHERRACK -> fastCook(furnace, stack, Material.NETHER_BRICK);
                        case COBBLESTONE -> fastCook(furnace, stack, Material.STONE);
                        case CACTUS -> fastCook(furnace, stack, Material.GREEN_DYE);
                        case OAK_LOG, BIRCH_LOG, SPRUCE_LOG, JUNGLE_LOG, ACACIA_LOG, DARK_OAK_LOG -> fastCook(furnace, stack, Material.CHARCOAL);
                        case COAL_ORE -> fastCook(furnace, stack, Material.COAL);
                        case DIAMOND_ORE -> fastCook(furnace, stack, Material.DIAMOND);
                        case EMERALD_ORE -> fastCook(furnace, stack, Material.EMERALD);
                        case LAPIS_ORE -> fastCook(furnace, stack, Material.LAPIS_LAZULI);
                        case NETHER_QUARTZ_ORE -> fastCook(furnace, stack, Material.QUARTZ);
                        case REDSTONE_ORE -> fastCook(furnace, stack, Material.REDSTONE);
                        default -> fastCook(furnace, stack, stack.getType());
                    }
                }
                count++;
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 1L, 0L);
    }

}
