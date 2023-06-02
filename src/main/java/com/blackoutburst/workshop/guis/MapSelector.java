package com.blackoutburst.workshop.guis;

import com.blackoutburst.workshop.commands.Play;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.files.MapFileUtils;
import com.blackoutburst.workshop.utils.files.MetaFileUtils;
import com.blackoutburst.workshop.utils.minecraft.GUIUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MapSelector {

	public static final String NAME = "Map Selector";

	private static final GUIItem[] INV_SETUP;

	private static final GUIItem air;

	private static final GUIItem up;

	private static final GUIItem down;

	public static void open(WSPlayer WSP, int line) {
		WSP.setMapSelectorLine(line);
		int depth = WSP.getGUIDepth();
		Player p = WSP.getPlayer();
		Inventory inv = Bukkit.createInventory(null, 54, NAME);

		File[] mapFiles = MapFileUtils.getAllMaps();
		List<GUIItem> mapIcons = new ArrayList<>();

		for (File mapFile : mapFiles) {
			ItemStack mapItem = MetaFileUtils.getIcon(mapFile);
			mapIcons.add(new GUIItem(mapItem, mapFile.getName()));
		}

		GUIItem[] finalInv = new GUIItem[54];
		Iterator<GUIItem> iconIterator = mapIcons.iterator();

		for (int i = 0; i < line*9; i++) {
			iconIterator.next();
		}

		for (int i = 0; i < INV_SETUP.length; i++) {
			GUIItem item = INV_SETUP[i];
			if (item.getName() != null) {
				finalInv[i] = INV_SETUP[i];
				continue;
			}
			if (iconIterator.hasNext()) finalInv[i] = iconIterator.next();
			else finalInv[i] = air;
		}

		if (!iconIterator.hasNext()) {
			int downIndex = Arrays.stream(INV_SETUP).toList().indexOf(down);
			finalInv[downIndex] = air;
		}
		if (line == 0) {
			int upIndex = Arrays.stream(INV_SETUP).toList().indexOf(up);
			finalInv[upIndex] = air;
		}

		GUIUtils.setItems(inv, finalInv);
		p.openInventory(inv);

		WSP.setGUIDepth(depth+1);
	}

	public static void open(WSPlayer WSP) {
		open(WSP, WSP.getMapSelectorLine());
	}

	public static boolean click(InventoryClickEvent event) {
		if (event.getClickedInventory() == null) return false;
		if (event.getClickedInventory().getType() == InventoryType.PLAYER) return false;
		Player p = (Player) event.getWhoClicked();
		WSPlayer WSP = WSPlayer.getFromPlayer(p);
		if (WSP == null) return false;
		GUIItem item = INV_SETUP[event.getSlot()];

		if (item.getName() != null) {
			if (item.getName().equals("§cExit")) close(WSP);
			if (item == up && event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				WSP.decrementGUIDepth();
				WSP.setMapSelectorLine(WSP.getMapSelectorLine() - 1);
				open(WSP);
			}
			if (item == down && event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				WSP.decrementGUIDepth();
				WSP.setMapSelectorLine(WSP.getMapSelectorLine() + 1);
				open(WSP);
			}
		}
		if (item != air) return true;
		if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return true;

		ItemStack clickedItem = event.getCurrentItem();
		ItemMeta itemMeta = clickedItem.getItemMeta();
		if (itemMeta == null) return true;
		String mapName = itemMeta.getDisplayName();

		if (!WSP.isEditing()) {
			MapTypeGUI.open(WSP, mapName);
			return true;
		}

		WSP.setInventoryType(mapName);
		MapMetaGUI.open(WSP, mapName);

		return true;
	}


	public static void close(WSPlayer WSP){
		if (WSP.getGUIDepth() == 1) {
			WSP.getPlayer().closeInventory();
			//return;
		}
		// if there is every a higher up GUI, open it here
	}



	static {
		air  = new GUIItem(Material.AIR,   null);
		up   = new GUIItem(Material.ARROW, "§aScroll up");
		down = new GUIItem(Material.ARROW, "§aScroll down");


		GUIItem pane    = new GUIItem(Material.GRAY_STAINED_GLASS_PANE, " ");
		GUIItem barrier = new GUIItem(Material.BARRIER,                 "§cExit");

		INV_SETUP = new GUIItem[]{
				air, air, air, air, air, air, air, pane, up,
				air, air, air, air, air, air, air, pane, barrier,
				air, air, air, air, air, air, air, pane, barrier,
				air, air, air, air, air, air, air, pane, barrier,
				air, air, air, air, air, air, air, pane, barrier,
				air, air, air, air, air, air, air, pane, down
		};
	}

}
