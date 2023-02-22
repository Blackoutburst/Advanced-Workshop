package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.blocks.MaterialBlock;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.EffectsUtils;
import com.blackoutburst.workshop.utils.GameUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

public class BlockDamage {

    private static void breakBlock(Block block, WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();
        if (wsplayer.getPlayArea() == null) return;

        MaterialBlock materialBlock = GameUtils.getMaterialBlock(wsplayer, block.getLocation());

        if (materialBlock == null) return;

        if (!(GameUtils.canBreak(materialBlock,player))) {
            return;
        }

        player.getInventory().addItem(new ItemStack(materialBlock.getType(), 1, materialBlock.getData()));

        // GameUtils.supportIterator(block.getLocation(), wsplayer, '0');

        EffectsUtils.breakBlock(block);
        block.setType(Material.AIR);
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
