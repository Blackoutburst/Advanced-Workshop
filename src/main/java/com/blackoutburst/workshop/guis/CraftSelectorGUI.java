package com.blackoutburst.workshop.guis;

import com.blackoutburst.workshop.core.Craft;
import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.game.MapMetadata;
import com.blackoutburst.workshop.utils.misc.MiscUtils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftSelectorGUI {

    public static final String NAME = "Craft Selector";

    private static int page = 0;

    private static Craft getCorrectCraft(WSPlayer p, ItemStack reqItem) {
        for (int i = 0; i < p.getCrafts().size(); i++) {
            Craft c = p.getCrafts().get(i);

            if (c.getName().equals(MiscUtils.getItemName(reqItem.getType()))) {
                return c;
            }
        }
        return null;
    }

    public static void open(WSPlayer player, int p) {
        int depth = player.getGUIDepth();
        page = p;

        Inventory inv = Main.getPlugin(Main.class).getServer().createInventory(null, 54, NAME);

        for (int i = 0; i < 36; i++) {
            if (i + (36 * page) >= player.getCrafts().size()) break;
            Craft c = player.getCrafts().get(i + (36 * page));

            ItemStack item = c.getItemRequired();

            setItem(inv, item.getType(), null, i);
        }

        for (int i = 36; i < 45; i++) {
            setItem(inv, Material.GRAY_STAINED_GLASS_PANE, "§r", i);
        }

        setItem(inv, Material.BARRIER, "§cExit", 46);
        setItem(inv, Material.BARRIER, "§cExit", 47);
        setItem(inv, Material.GREEN_TERRACOTTA, "§aAdd recipe", 48);
        setItem(inv, Material.GREEN_TERRACOTTA, "§aAdd recipe", 49);
        setItem(inv, Material.GREEN_TERRACOTTA, "§aAdd recipe", 50);
        setItem(inv, Material.BARRIER, "§cExit", 51);
        setItem(inv, Material.BARRIER, "§cExit", 52);

        if (page > 0)
            setItem(inv, Material.ARROW, "§ePrevious Page", 45);

        if (player.getCrafts().size() > 36 * (page + 1)) {
            setItem(inv, Material.ARROW, "§eNext Page", 53);
        }

        player.getPlayer().openInventory(inv);

        player.setGUIDepth(depth+1);
    }

    private static void setItem(Inventory inv, Material mat, String name, int slot) {
        ItemStack item = new ItemStack(mat, 1);
        if (name != null) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(name);
                item.setItemMeta(meta);
            }
        }
        inv.setItem(slot, item);
    }

    public static boolean click(Inventory inv, int slot, Player p, String title) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer(p);
        if (wsplayer == null) return true;

        if (inv == null || !title.equals(NAME)) return false;
        if (inv.getType() == InventoryType.PLAYER) return false;

        if (slot >= 0 && slot < 36 && inv.getItem(slot) != null) {
            CraftGUI.open(wsplayer, getCorrectCraft(wsplayer, inv.getItem(slot)));
        }

        if (slot == 53 && wsplayer.getCrafts().size() > 36 * (page + 1)) {
            page++;
            wsplayer.decrementGUIDepth();
            open(wsplayer, page);
            return true;
        }

        if (slot == 45 && page > 0) {
            page--;
            wsplayer.decrementGUIDepth();
            open(wsplayer, page);
            return true;
        }

        if (slot == 48 || slot == 49 || slot == 50) {
            CraftGUI.open(wsplayer, null);
            return true;
        }

        if ((slot == 46 || slot == 47 || slot == 51 || slot == 52)) {
            if (wsplayer.getGUIDepth() == 1) {
                p.closeInventory();
                return true;
            }

            wsplayer.decrementGUIDepth(); wsplayer.decrementGUIDepth();
            MapMetaGUI.open(wsplayer, wsplayer.getInventoryType());
        }

        return true;
    }
}
