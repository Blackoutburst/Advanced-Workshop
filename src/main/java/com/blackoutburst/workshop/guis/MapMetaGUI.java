package com.blackoutburst.workshop.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MapMetaGUI {
	public static final String NAME = "Map Info";

	private static final GUIItem[] INV_SETUP;

	private static final GUIItem helmet;
	private static final GUIItem chestplate;
	private static final GUIItem leggings;
	private static final GUIItem boots;
	private static final GUIItem offhand;
	private static final GUIItem icon;



	public static void open(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54, NAME);
		setItems(inv, INV_SETUP);

		//TODO load meta, set those items

		p.openInventory(inv);
	}


	private static void setItem(Inventory inv, GUIItem item, int slot) {
		ItemStack itemStack = item.getItem();
		String name = item.getName();
		if (name != null) {
			ItemMeta meta = itemStack.getItemMeta();
			if (meta != null) {
				meta.setDisplayName(name);
				itemStack.setItemMeta(meta);
			}
		}
		inv.setItem(slot, itemStack);
	}

	private static void setItems(Inventory inv, GUIItem[] items) {
		for (int i = 0; i < items.length; i++) {
			GUIItem item = items[i];
			ItemStack itemStack = item.getItem();
			String name = item.getName();
			if (name != null) {
				ItemMeta meta = itemStack.getItemMeta();
				if (meta != null) {
					meta.setDisplayName(name);
					itemStack.setItemMeta(meta);
				}
			}
			inv.setItem(i, itemStack);
		}
	}


	public static boolean click(InventoryClickEvent event) {
		if (event.getClickedInventory() == null) return false;
		if (event.getClickedInventory().getType() == InventoryType.PLAYER) return false;

		GUIItem item = INV_SETUP[event.getSlot()];
		if (item.getName().equals("§cExit")) {
			event.getWhoClicked().closeInventory();
		}

		//TODO save if item is called §aSave



		return item.getName() != null;
	}

	private static void save() {
		// TODO make
	}

	private static void load() {
		//TODO make
	}


	static {
		helmet     = new GUIItem(Material.AIR,null);
		chestplate = new GUIItem(Material.AIR,null);
		leggings   = new GUIItem(Material.AIR,null);
		boots      = new GUIItem(Material.AIR,null);
		offhand    = new GUIItem(Material.AIR,null);
		icon       = new GUIItem(Material.AIR,null);

		GUIItem pane    = new GUIItem(Material.GRAY_STAINED_GLASS_PANE,     " ");
		GUIItem black   = new GUIItem(Material.BLACK_STAINED_GLASS_PANE,    " ");
		GUIItem barrier = new GUIItem(Material.BARRIER,                     "§cExit");
		GUIItem green   = new GUIItem(Material.GREEN_TERRACOTTA,            "§aSave");
		GUIItem armor   = new GUIItem(Material.ARMOR_STAND,                 "§eArmor");
		GUIItem shield  = new GUIItem(Material.SHIELD,                      "§eOffhand");
		GUIItem map     = new GUIItem(Material.MAP,                         "§eMap Icon");
		GUIItem chest   = new GUIItem(Material.CHEST,                       "§aInventory setup");
		GUIItem craft   = new GUIItem(Material.CRAFTING_TABLE,              "§aCrafts");

		INV_SETUP = new GUIItem[]{
				black,  armor,      black,  black,  shield,     black,  black,  map,    black,
				pane,   helmet,     pane,   pane,   offhand,    pane,   pane,   icon,   pane,
				pane,   chestplate, pane,   pane,   pane,       pane,   pane,   pane,   pane,
				pane,   leggings,   pane,   pane,   chest,      pane,   pane,   craft,  pane,
				pane,   boots,      pane,   pane,   pane,       pane,   pane,   pane,   pane,
				pane,   pane,       pane,   green,  green,      green,  pane,   pane,   barrier
		};
	}
}
