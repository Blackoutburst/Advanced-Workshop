package com.blackoutburst.workshop.guis;

import com.blackoutburst.workshop.Craft;
import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftGUI {

    public static final String NAME = "Add recipe";

    private static final int[] AIR = new int[] { 23, 10, 11, 12, 19, 20, 21, 28, 29, 30, 7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53 };

    private static boolean isAir(int slot) {
        for (int s : AIR)
            if (s == slot) return true;

        return false;
    }

    private static void loadCraft(Inventory inv, Craft craft) {
        ItemStack reqItem = craft.getItemRequired();

        setItem(inv, reqItem.getType(), 1, null, AIR[0]);

        for (int i  = 1; i < 10; i++) {
            ItemStack cTable = craft.getCraftingTable()[i - 1];
            if (cTable == null || cTable.getType().equals(Material.AIR)) continue;

            setItem(inv, cTable.getType(), 1, null, AIR[i]);
        }

        for (int i = 10; i < AIR.length - 1; i++) {
            if (i - 10 >= craft.getMaterials().size()) break;
            ItemStack material = craft.getMaterials().get(i - 10);
            if (material == null || material.getType().equals(Material.AIR)) continue;

            setItem(inv, material.getType(), material.getAmount(), null, AIR[i]);
        }
    }

    public static void open(WSPlayer p, Craft craft) {
        Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 54, NAME);

        for (int i = 0; i < 54; i++) {
            if (isAir(i)) continue;
            setItem(inv, Material.GRAY_STAINED_GLASS_PANE, 1, "§r", i);
        }

        setItem(inv, Material.GREEN_TERRACOTTA, 1, "§aSave", 46);
        setItem(inv, Material.GREEN_TERRACOTTA, 1, "§aSave", 47);

        setItem(inv, Material.RED_TERRACOTTA, 1, "§cDelete", 49);
        setItem(inv, Material.RED_TERRACOTTA, 1, "§cDelete", 50);

        if (craft != null) {
            loadCraft(inv, craft);
        }

        p.getPlayer().openInventory(inv);
    }

    private static void setItem(Inventory inv, Material mat, int amount, String name, int slot) {
        ItemStack item = new ItemStack(mat, amount);
        if (name != null) {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        inv.setItem(slot, item);
    }

    public static boolean click(Inventory inv, int slot, Player p, String title) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer(p);
        if (wsplayer == null) return true;

        if (inv == null || !title.equals(NAME)) return false;

        if (slot == 46 || slot == 47) {
            GUIUtils.saveCraft(inv, wsplayer);
            return true;
        }

        if (slot == 49 || slot == 50) {
            GUIUtils.deleteCraft(inv, wsplayer);
            return true;
        }

        return !isAir(slot);
    }
}
