package com.blackoutburst.workshop.core.events;

import com.blackoutburst.workshop.core.BrokenBlock;
import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

public class BlockDamage {

    /**
     *
     * Must change
     * @param block
     * @param wsplayer
     */
    private static void breakBlock(Block block, WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();

        switch (block.getType()) {
            case GOLD_ORE: player.getInventory().addItem(new ItemStack(Material.GOLD_ORE)); break;
            case LEAVES: player.getInventory().addItem(new ItemStack(Material.APPLE)); break;
            case SUGAR_CANE_BLOCK: {
                for (int y = block.getY(); y < block.getY() + 3; y++) {
                    Block sugarCane = block.getWorld().getBlockAt(block.getX(), y, block.getZ());
                    if (sugarCane.getType().equals(Material.SUGAR_CANE_BLOCK)) {
                        wsplayer.getBrokenBlocks().add(new BrokenBlock(sugarCane.getType(), sugarCane.getData(), sugarCane.getLocation(), sugarCane.getWorld()));
                        player.getInventory().addItem(new ItemStack(Material.SUGAR_CANE));
                    }
                }
            } break;
            case COCOA: player.getInventory().addItem(new ItemStack(Material.INK_SACK,1, (short) 3)); break;
            case PUMPKIN: player.getInventory().addItem(new ItemStack(Material.PUMPKIN)); break;
            case POTATO: player.getInventory().addItem(new ItemStack(Material.POTATO_ITEM)); break;
            case CARROT: player.getInventory().addItem(new ItemStack(Material.CARROT_ITEM)); break;
            case CROPS: player.getInventory().addItem(new ItemStack(Material.WHEAT)); break;
            case MELON_BLOCK: player.getInventory().addItem(new ItemStack(Material.MELON)); break;
            case SKULL: player.getInventory().addItem(new ItemStack(Material.EGG)); break;
            default: return;
        }
        wsplayer.getBrokenBlocks().add(new BrokenBlock(block.getType(), block.getData(), block.getLocation(), block.getWorld()));
        block.breakNaturally();
    }

    public static void execute(BlockDamageEvent event) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer(event.getPlayer());

        if (wsplayer != null && wsplayer.isInGame()) {
            Block block = event.getBlock();

            breakBlock(block, wsplayer);
        }
    }
}
