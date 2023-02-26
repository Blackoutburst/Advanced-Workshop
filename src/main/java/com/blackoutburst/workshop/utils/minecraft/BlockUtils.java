package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.blocks.MaterialBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlockUtils {

    public static MaterialBlock getMaterialBlock(WSPlayer wsPlayer, Location location) {
        for (MaterialBlock block : wsPlayer.getMaterialBlocks()) {
            if (location.getWorld().getName().equals(block.getWorld().getName()) &&
                    location.getBlockX() == block.getLocation().getBlockX() &&
                    location.getBlockY() == block.getLocation().getBlockY() &&
                    location.getBlockZ() == block.getLocation().getBlockZ()) {
                return block;
            }
        }
        return null;
    }

    public static boolean canBreak(MaterialBlock materialBlock, Player player) {
        if (WSPlayer.getFromPlayer(player).isWaiting()) return false;
        if (materialBlock.getTools().length == 0) return true;

        for (ItemStack tool : materialBlock.getTools()) {
            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                return false;
            }
            if (player.getInventory().getItemInMainHand().getType() == tool.getType()) {
                return true;
            }
        }
        return false;
    }
}
