package com.blackoutburst.workshop.utils;

import com.blackoutburst.workshop.Craft;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.guis.CraftSelectorGUI;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.PrintWriter;

public class GUIUtils {


    public static void deleteCraft(Inventory inv, WSPlayer p) {
        if (p.getInventoryType() == null) return;

        boolean removed = false;

        try {
            PrintWriter writer = new PrintWriter("./plugins/Workshop/" + p.getInventoryType() + ".craft");

            ItemStack reqItem = inv.getItem(23);
            if (reqItem == null) throw new Exception();

            for (int i = 0; i < p.getCrafts().size(); i++) {
                Craft c = p.getCrafts().get(i);

                if (c.getName().equals(StringUtils.formatItemName(reqItem.getType().name()))) {
                    p.getCrafts().remove(i);
                    removed = true;
                    break;
                }
            }

            if (!removed) {
                p.getPlayer().sendMessage("§cThis craft doesn't exist");
                GameUtils.loadCraft(p, p.getInventoryType());
                CraftSelectorGUI.open(p, 0);
                return;
            }

            for (Craft c : p.getCrafts()) {
                writer.write(c.getName() + ", ");
                writer.write(c.getItemRequired().getTypeId() + ":" + c.getItemRequired().getData().getData() + ", ");

                for (int i  = 0; i < 9; i++)
                    writer.write(c.getCraftingTable()[i].getTypeId() + ":" + c.getCraftingTable()[i].getData().getData() + (i == 8 ? "" : ", "));

                for (int i = 0; i < c.getMaterials().size(); i++) {
                    ItemStack material = c.getMaterials().get(i);

                    writer.write(material.getTypeId() + ":" + material.getData().getData() + ":" + material.getAmount() + (i == (c.getMaterials().size() - 1) ? "\n" : ", "));
                }
            }
            writer.close();
            p.getPlayer().sendMessage("§aCraft deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameUtils.loadCraft(p, p.getInventoryType());
        CraftSelectorGUI.open(p, 0);
    }

    public static void saveCraft(Inventory inv, WSPlayer p) {
        if (p.getInventoryType() == null) return;

        boolean updated = false;

        int[] order = new int[] { 23, 10, 11, 12, 19, 20, 21, 28, 29, 30, 7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53 };

        try {
            PrintWriter writer = new PrintWriter("./plugins/Workshop/" + p.getInventoryType() + ".craft");

            ItemStack reqItem = inv.getItem(order[0]);
            if (reqItem == null) throw new Exception();

            for (int i = 0; i < p.getCrafts().size(); i++) {
                Craft c = p.getCrafts().get(i);

                if (c.getName().equals(StringUtils.formatItemName(reqItem.getType().name()))) {
                    p.getCrafts().remove(i);
                    updated = true;
                    break;
                }
            }

            for (Craft c : p.getCrafts()) {
                writer.write(c.getName() + ", ");
                writer.write(c.getItemRequired().getTypeId() + ":" + c.getItemRequired().getData().getData() + ", ");

                for (int i  = 0; i < 9; i++)
                    writer.write(c.getCraftingTable()[i].getTypeId() + ":" + c.getCraftingTable()[i].getData().getData() + ", ");

                if (c.getMaterials().size() == 0) {
                    writer.write("0:0:0, 0:0:0, 0:0:0, 0:0:0, 0:0:0, 0:0:0, 0:0:0, 0:0:0, 0:0:0, 0:0:0, 0:0:0, 0:0:0\n");
                }
                for (int i = 0; i < c.getMaterials().size(); i++) {
                    ItemStack material = c.getMaterials().get(i);

                    writer.write(material.getTypeId() + ":" + material.getData().getData() + ":" + material.getAmount() + (i == (c.getMaterials().size() - 1) ? "\n" : ", "));
                }
            }

            writer.write(StringUtils.formatItemName(reqItem.getType().name()) + ", ");
            writer.write(reqItem.getTypeId() + ":" + reqItem.getData().getData() + ", ");

            for (int i  = 1; i < 10; i++) {
                ItemStack cTable = inv.getItem(order[i]);
                if (cTable == null) {
                    writer.write("0:0" + ", ");
                    continue;
                }
                writer.write(cTable.getTypeId() + ":" + cTable.getData().getData() + ", ");
            }

            for (int i  = 10; i < order.length; i++) {
                ItemStack mats = inv.getItem(order[i]);
                if (mats == null) {
                    writer.write("0:0:0" + (i == (order.length -1) ? "\n" : ", "));
                    continue;
                }
                writer.write(mats.getTypeId() + ":" + mats.getData().getData() + ":" + mats.getAmount() + (i == (order.length -1) ? "\n" : ", "));
            }

            writer.close();
            p.getPlayer().sendMessage(updated ? "§aCraft updated successfully" : "§aCraft added successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameUtils.loadCraft(p, p.getInventoryType());
        CraftSelectorGUI.open(p, 0);
    }
}
