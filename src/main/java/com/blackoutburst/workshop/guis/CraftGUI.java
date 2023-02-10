package com.blackoutburst.workshop.guis;

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

    private static final int[] AIR = new int[] { 7, 8, 10, 11, 12, 16, 17, 19, 20, 21, 23, 25, 26, 28, 29, 30, 34, 35, 43, 44, 52, 53 };

    private static boolean isAir(int slot) {
        for (int s : AIR)
            if (s == slot) return true;

        return false;
    }
    public static void open(WSPlayer p) {
        Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 54, NAME);

        for (int i = 0; i < 54; i++) {
            if (isAir(i)) continue;
            setItem(inv, Material.STAINED_GLASS_PANE, 7, "§r", i);
        }

        setItem(inv, Material.STAINED_CLAY, 13, "§aSave", 46);
        setItem(inv, Material.STAINED_CLAY, 13, "§aSave", 47);

        setItem(inv, Material.STAINED_CLAY, 14, "§cDelete", 49);
        setItem(inv, Material.STAINED_CLAY, 14, "§cDelete", 50);

        p.getPlayer().openInventory(inv);
    }

    private static void setItem(Inventory inv, Material mat, int data, String name, int slot) {
        ItemStack item = new ItemStack(mat, 1, (short) data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        inv.setItem(slot, item);
    }

    public static boolean click(Inventory inv, int slot, Player p) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer(p);
        if (wsplayer == null) return true;

        if (inv == null || !inv.getName().equals(NAME)) return false;

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
