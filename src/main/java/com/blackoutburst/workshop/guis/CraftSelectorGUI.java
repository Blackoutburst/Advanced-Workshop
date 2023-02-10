package com.blackoutburst.workshop.guis;

import com.blackoutburst.workshop.Craft;
import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.GUIUtils;
import com.blackoutburst.workshop.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftSelectorGUI {

    public static final String NAME = "Craft Selector";

    private static Craft getCorrectCraft(WSPlayer p, ItemStack reqItem) {
        for (int i = 0; i < p.getCrafts().size(); i++) {
            Craft c = p.getCrafts().get(i);

            if (c.getName().equals(StringUtils.formatItemName(reqItem.getType().name()))) {
                return c;
            }
        }
        return null;
    }

    public static void open(WSPlayer p) {
        Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 54, NAME);

        for (int i = 0; i < 36; i++) {
            if (i >= p.getCrafts().size()) break;
            Craft c = p.getCrafts().get(i);

            ItemStack item = c.getItemRequired();

            setItem(inv, item.getType(), item.getData().getData(), null, i);
        }

        for (int i = 36; i < 45; i++) {
            setItem(inv, Material.STAINED_GLASS_PANE, 7, "§r", i);
        }

        setItem(inv, Material.STAINED_CLAY, 13, "§aAdd recipe", 48);
        setItem(inv, Material.STAINED_CLAY, 13, "§aAdd recipe", 49);
        setItem(inv, Material.STAINED_CLAY, 13, "§aAdd recipe", 50);

        p.getPlayer().openInventory(inv);
    }

    private static void setItem(Inventory inv, Material mat, int data, String name, int slot) {
        ItemStack item = new ItemStack(mat, 1, (short) data);
        if (name != null) {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        inv.setItem(slot, item);
    }

    public static boolean click(Inventory inv, int slot, Player p) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer(p);
        if (wsplayer == null) return true;

        if (inv == null || !inv.getName().equals(NAME)) return false;

        if (slot >= 0 && slot < 36 && inv.getItem(slot) != null) {
            CraftGUI.open(wsplayer, getCorrectCraft(wsplayer, inv.getItem(slot)));
        }

        if (slot == 48 || slot == 49 || slot == 50) {
            CraftGUI.open(wsplayer, null);
            return true;
        }

        return true;
    }
}
