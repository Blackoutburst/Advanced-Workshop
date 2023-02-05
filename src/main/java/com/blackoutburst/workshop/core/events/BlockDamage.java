package com.blackoutburst.workshop.core.events;

import com.blackoutburst.workshop.core.MaterialBlock;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.GameUtils;
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
        if (wsplayer.getPlayArea() == null) return;

        MaterialBlock materialBlock = GameUtils.getMaterialBlock(wsplayer, block.getLocation());
        if (materialBlock == null) return;

        player.getInventory().addItem(new ItemStack(materialBlock.getType(), 1, materialBlock.getData()));

        for (int y = materialBlock.getLocation().getBlockY() + 1; y < materialBlock.getLocation().getBlockY() + 4; y++) {
            Block sugarCane = materialBlock.getWorld().getBlockAt(materialBlock.getLocation().getBlockX(), y, materialBlock.getLocation().getBlockZ());
            if (sugarCane.getType().equals(Material.SUGAR_CANE_BLOCK)) {
                player.getInventory().addItem(new ItemStack(materialBlock.getType(), 1, materialBlock.getData()));
            }
        }
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
