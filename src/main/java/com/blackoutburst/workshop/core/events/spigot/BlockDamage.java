package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.blocks.MaterialBlock;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.minecraft.BlockUtils;
import com.blackoutburst.workshop.utils.misc.EffectsUtils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

public class BlockDamage {

    private static void breakBlock(Block block, WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();
        if (wsplayer.getPlayArea() == null) return;

        MaterialBlock materialBlock = BlockUtils.getMaterialBlock(wsplayer, block.getLocation());

        if (materialBlock == null) return;

        if (!(BlockUtils.canBreak(materialBlock,player))) {
            return;
        }

        player.getInventory().addItem(new ItemStack(materialBlock.getType(), 1));

        EffectsUtils.breakBlock(block);
        block.setType(Material.AIR, false);
        BlockUtils.supportIterator(wsplayer, block);
        block.setType(Material.STRUCTURE_VOID, true); // temp
        block.setType(Material.AIR, true);
    }

    public static void execute(BlockDamageEvent event) {
        if (event.getBlock().getType().equals(Material.AIR)) return;
        WSPlayer wsplayer = WSPlayer.getFromPlayer(event.getPlayer());

        if (wsplayer != null && wsplayer.isInGame()) {
            Block block = event.getBlock();

            breakBlock(block, wsplayer);
        }
    }
}
