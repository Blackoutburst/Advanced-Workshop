package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.nms.NMSEntity;
import com.blackoutburst.workshop.nms.NMSItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemFrameUtils {

    public static void updateCraft(WSPlayer wsplayer) {
        ItemStack outputItem = wsplayer.getCurrentCraft().getItemRequired();

        for (NMSEntity entity : wsplayer.getEntities()) {
            if (entity instanceof NMSItemFrame) {
                NMSItemFrame itemFrame = (NMSItemFrame) entity;
                if (itemFrame.getTag().equals("0") && entity.getOwner().getPlayer().getUniqueId().equals(wsplayer.getPlayer().getUniqueId())) {
                    itemFrame.setItem(outputItem);
                    continue;
                }

                if (itemFrame.getTag().matches("([1-9]+([.][1-9]+)?)?") && entity.getOwner().getPlayer().getUniqueId().equals(wsplayer.getPlayer().getUniqueId())) {
                    int index = Integer.parseInt(itemFrame.getTag()) - 1;
                    ItemStack item = wsplayer.getCurrentCraft().getCraftingTable()[index];

                    itemFrame.setItem(item);
                }
            }
        }
    }
}
