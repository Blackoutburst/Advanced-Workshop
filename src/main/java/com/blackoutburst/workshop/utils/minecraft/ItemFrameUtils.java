package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.nms.NMSEntity;
import com.blackoutburst.workshop.nms.NMSItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemFrameUtils {

    public static void updateCraft(WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();
        ItemStack outputItem = wsplayer.getCurrentCraft().getItemRequired();

        for (NMSEntity entity : wsplayer.getEntities()) {
            if (entity instanceof NMSItemFrame itemFrame) {
                if (itemFrame.getTag().equals("0")) {
                    itemFrame.setItem(player, outputItem);
                    continue;
                }

                if (itemFrame.getTag().matches("([0-9]+([.][0-9]+)?)?")) {
                    int index = Integer.parseInt(itemFrame.getTag()) - 1;
                    ItemStack item = wsplayer.getCurrentCraft().getCraftingTable()[index];

                    itemFrame.setItem(player, item);
                }
            }
        }
    }
}
