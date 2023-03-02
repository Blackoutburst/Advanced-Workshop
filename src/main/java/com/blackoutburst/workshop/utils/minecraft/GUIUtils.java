package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.Craft;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.guis.CraftSelectorGUI;
import com.blackoutburst.workshop.utils.files.CraftFileUtils;
import com.blackoutburst.workshop.utils.misc.MiscUtils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIUtils {
    //
    // TODO
    // format broken, redo

    public static void deleteCraft(Inventory inv, WSPlayer p) {
        if (p.getInventoryType() == null) return;

        boolean removed = false;

        ItemStack reqItem = inv.getItem(23);
        if (reqItem == null) {
            CraftUtils.loadCraft(p, p.getInventoryType());
            CraftSelectorGUI.open(p, 0);
            return;
        }

        for (int i = 0; i < p.getCrafts().size(); i++) {
            Craft c = p.getCrafts().get(i);

            if (c.getName().equals(MiscUtils.getItemName(reqItem.getType()))) {
                p.getCrafts().remove(i);
                removed = true;
                CraftFileUtils.deleteCraft(p.getInventoryType(), reqItem.getType().toString());
                break;
            }
        }

        if (!removed) {
            p.getPlayer().sendMessage("§cThis craft doesn't exist");
            CraftUtils.loadCraft(p, p.getInventoryType());
            CraftSelectorGUI.open(p, 0);
            return;
        }

        p.getPlayer().sendMessage("§aCraft deleted successfully");

        CraftUtils.loadCraft(p, p.getInventoryType());
        CraftSelectorGUI.open(p, 0);
    }

    public static void saveCraft(Inventory inv, WSPlayer p) {
        if (p.getInventoryType() == null) return;
        String type = p.getInventoryType();

        int[] order = new int[] { 23, 10, 11, 12, 19, 20, 21, 28, 29, 30, 7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53 };
        //                     result <-------------materials---------->  <------------------raw materials----------->

        try {
            ItemStack reqItem = inv.getItem(order[0]);
            if (reqItem == null) throw new Exception();
            ItemStack[] cTable = new ItemStack[9];
            ItemStack[] mats = new ItemStack[10];

            for (int i = 0; i < 9; i++) {
                if (inv.getItem(order[i+1]) == null ) {
                    cTable[i] = new ItemStack(Material.AIR);
                    continue;
                }
                cTable[i] = inv.getItem(order[i + 1]);
            }

            for (int i  = 0; i < 10; i++) {
                if (inv.getItem(order[i+10]) == null ) {
                    mats[i] = new ItemStack(Material.AIR);
                    continue;
                }
                mats[i] = inv.getItem(order[i+10]);
            }

            CraftFileUtils.saveFileCraft(type, reqItem, cTable, mats);

            p.getPlayer().sendMessage("§aCraft added successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

        CraftUtils.loadCraft(p, p.getInventoryType());
        CraftSelectorGUI.open(p, 0);
    }
}
