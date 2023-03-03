package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.blocks.MaterialBlock;

import com.blackoutburst.workshop.utils.misc.EffectsUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
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

    public static boolean canBreak(MaterialBlock materialBlock, WSPlayer wsplayer) {
        if (wsplayer.isWaiting()) return false;
        Player player = wsplayer.getPlayer();
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

    public static void supportIterator(WSPlayer wsplayer, Block supporter) {
        Player player = wsplayer.getPlayer();

        BlockFace[] directions = {
                BlockFace.UP,
                BlockFace.NORTH,
                BlockFace.EAST,
                BlockFace.SOUTH,
                BlockFace.WEST,
                BlockFace.DOWN
        };

        for (BlockFace direction : directions) {
            Block checkBlock = supporter.getRelative(direction);
            Location location = checkBlock.getLocation();
            BlockData checkData = checkBlock.getBlockData();
            boolean isSupported = checkData.isSupported(checkBlock);
            if (isSupported) continue;

            checkBlock.setType(Material.AIR, false);
            EffectsUtils.breakBlock(checkBlock);
            MaterialBlock matBlock = getMaterialBlock(wsplayer, location);
            if (matBlock != null && canBreak(matBlock, wsplayer)) {
                ItemStack item = new ItemStack(matBlock.getType());
                player.getInventory().addItem(item);
            }
            supportIterator(wsplayer, checkBlock);
        }
    }
}
