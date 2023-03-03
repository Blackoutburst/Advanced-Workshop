package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.ClientVersion;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;

public class ItemFrameUtils {

    public static void updateCraft(WSPlayer wsplayer) {
        PlayArea area = wsplayer.getPlayArea();
        ItemStack outputItem = wsplayer.getCurrentCraft().getItemRequired();
        ClientVersion clientVersion = wsplayer.getClientVersion();
        boolean usePanes = clientVersion.compareTo(ClientVersion.V1_8_9) >= 0;

        for (Entity entity : area.getEntities()) {
            if (entity.getCustomName() == null) continue;

            if (!(entity instanceof ItemFrame itemFrame)) continue;

            if (itemFrame.getCustomName().equals("0")) {
                itemFrame.setItem(outputItem);
                continue;
            }

            if (itemFrame.getCustomName().matches("[1-9]+")) {
                int index = Integer.parseInt(itemFrame.getCustomName()) - 1;
                ItemStack item = wsplayer.getCurrentCraft().getCraftingTable()[index];

                if (item.getType() == Material.AIR && usePanes) {
                    itemFrame.setItem(new ItemStack(Material.BARRIER));
                }
                else {
                    itemFrame.setItem(item);
                }
            }
        }
    }
}
