package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.blocks.MaterialBlock;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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
        if (materialBlock.getTools()[0].isEmpty()) return true;

        for (String tool : materialBlock.getTools()) {
            if (player.getItemInHand().getType() == Material.AIR) {
                return false;
            }

            NBTContainer handItemNBT = NBTItem.convertItemtoNBT(player.getItemInHand());
            String handItem = handItemNBT.getString("id");
            if (handItem.equals(tool)) {
                return true;
            }
        }
        return false;
    }
}
