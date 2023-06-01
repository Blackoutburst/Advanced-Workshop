package com.blackoutburst.workshop.guis;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.game.MapMetadata;
import com.blackoutburst.workshop.utils.files.MapFileUtils;
import com.blackoutburst.workshop.utils.files.MetaFileUtils;
import com.blackoutburst.workshop.utils.minecraft.CraftUtils;
import com.blackoutburst.workshop.utils.minecraft.GUIUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class MapMetaGUI {
	public static final String NAME = "Map Info";

	private static final GUIItem[] INV_SETUP;

	private static final GUIItem helmet;
	private static final GUIItem chestplate;
	private static final GUIItem leggings;
	private static final GUIItem boots;
	private static final GUIItem offhand;
	private static final GUIItem icon;



	public static void open(WSPlayer WSP, String mapName) {
		int depth = WSP.getGUIDepth();
		Player p = WSP.getPlayer();
		Inventory inv = Bukkit.createInventory(null, 54, NAME);
		GUIUtils.setItems(inv, INV_SETUP);

		MapMetadata items = load(mapName);

		for (int i = 0; i < INV_SETUP.length; i++) {
			GUIItem item = INV_SETUP[i];
			if (item == helmet) inv.setItem(i, items.getArmorContents()[0]);
			if (item == chestplate) inv.setItem(i, items.getArmorContents()[1]);
			if (item == leggings) inv.setItem(i, items.getArmorContents()[2]);
			if (item == boots) inv.setItem(i, items.getArmorContents()[3]);
			if (item == offhand) inv.setItem(i, items.getOffHand());
			if (item == icon) inv.setItem(i, items.getIcon());
		}

		p.openInventory(inv);

		WSP.setGUIDepth(depth+1);
	}


	public static boolean click(InventoryClickEvent event) {
		if (event.getClickedInventory() == null) return false;
		if (event.getClickedInventory().getType() == InventoryType.PLAYER) return false;
		Player p = (Player) event.getWhoClicked();
		WSPlayer WSP = WSPlayer.getFromPlayer(p);
		if (WSP == null) return false;

		GUIItem item = INV_SETUP[event.getSlot()];
		if (item.getName() == null) return false;

		if (item.getName().equals("§cExit")) {
			close(WSP);
		}

		if (item.getName().equals("§aSave")) {
			save(WSP, event.getClickedInventory());
		}

		if (item.getName().equals("§aCrafts")) {
			CraftUtils.loadCraft(WSP, WSP.getInventoryType());
			CraftSelectorGUI.open(WSP, 0);
		}

		if (item.getName().equals("§aInventory setup")) {
			InventorySetupGUI.open(WSP, WSP.getInventoryType());
		}

		return true;
	}

	private static void save(WSPlayer WSP, Inventory inv) {
		if (WSP == null) return;
		String mapName = WSP.getInventoryType();
		if (mapName == null) return;

		ItemStack[] armor = new ItemStack[4];
		ItemStack offHandItem = offhand.getItem();
		ItemStack iconItem = icon.getItem();

		List<GUIItem> ArmorList = List.of(new GUIItem[]{helmet,chestplate,leggings,boots});

		for (int i = 0; i < INV_SETUP.length; i++) {
			GUIItem item = INV_SETUP[i];
			if (ArmorList.contains(item)) {
				armor[ArmorList.indexOf(item)] = inv.getItem(i);
			}
			if (item == offhand) {
				offHandItem = inv.getItem(i);
			}
			if (item == icon) {
				iconItem = inv.getItem(i);
			}
		}

		MapMetadata data = new MapMetadata(armor,offHandItem,iconItem);
		File f = MapFileUtils.getMapFile(mapName, 'M');

		MetaFileUtils.save(f, data);
		close(WSP);
	}

	private static void close(WSPlayer WSP) {
		Player p = WSP.getPlayer();
		if (WSP.getGUIDepth() == 1) {
			p.closeInventory();
			return;
		}
		WSP.decrementGUIDepth(); WSP.decrementGUIDepth();
		MapSelector.open(WSP, 0);
	}


	private static MapMetadata load(String mapName) {
		File f = MapFileUtils.getMapFile(mapName, 'M');
		return MetaFileUtils.load(f);
	}

	static {
		helmet     = new GUIItem(Material.AIR,null);
		chestplate = new GUIItem(Material.AIR,null);
		leggings   = new GUIItem(Material.AIR,null);
		boots      = new GUIItem(Material.AIR,null);
		offhand    = new GUIItem(Material.AIR,null);
		icon       = new GUIItem(Material.AIR,null);

		GUIItem pane    = new GUIItem(Material.GRAY_STAINED_GLASS_PANE, " ");
		GUIItem black   = new GUIItem(Material.BLACK_STAINED_GLASS_PANE," ");
		GUIItem barrier = new GUIItem(Material.BARRIER,                 "§cExit");
		GUIItem green   = new GUIItem(Material.GREEN_TERRACOTTA,        "§aSave");
		GUIItem armor   = new GUIItem(Material.ARMOR_STAND,             "§eArmor");
		GUIItem shield  = new GUIItem(Material.SHIELD,                  "§eOffhand");
		GUIItem map     = new GUIItem(Material.MAP,                     "§eMap Icon");
		GUIItem chest   = new GUIItem(Material.CHEST,                   "§aInventory setup");
		GUIItem craft   = new GUIItem(Material.CRAFTING_TABLE,          "§aCrafts");

		INV_SETUP = new GUIItem[]{
				black, armor,      black, black, shield,  black, black, map,   black,
				pane,  helmet,     pane,  pane,  offhand, pane,  pane,  icon,  pane,
				pane,  chestplate, pane,  pane,  pane,    pane,  pane,  pane,  pane,
				pane,  leggings,   pane,  pane,  chest,   pane,  pane,  craft, pane,
				pane,  boots,      pane,  pane,  pane,    pane,  pane,  pane,  pane,
				pane,  pane,       pane,  green, green,   green, pane,  pane,  barrier
		};
	}
}
