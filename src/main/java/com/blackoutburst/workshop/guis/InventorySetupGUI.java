package com.blackoutburst.workshop.guis;

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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class InventorySetupGUI {
	public static final String NAME = "Inventory Setup";

	private static final GUIItem[] INV_SETUP;

	private static final GUIItem air;

	public static void open(WSPlayer WSP, String mapName) {
		int depth = WSP.getGUIDepth();
		Player p = WSP.getPlayer();
		Inventory inv = Bukkit.createInventory(null, 54, NAME);

		File f = MapFileUtils.getMapFile(mapName, 'M');
		Iterator<ItemStack> invContents = Arrays.stream(MetaFileUtils.loadInventory(f)).iterator();

		GUIItem[] finalInv = new GUIItem[54];
		for (int i = 0; i < INV_SETUP.length; i++) {
			if (INV_SETUP[i] != air) finalInv[i] = INV_SETUP[i];
			else if (invContents.hasNext()) finalInv[i] = new GUIItem(invContents.next());
		}

		GUIUtils.setItems(inv, finalInv);
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
			close(WSP);
		}

		return true;
	}


	private static void save(WSPlayer WSP, Inventory inv) {
		List<ItemStack> invContents = new ArrayList<>();

		for (int i = 0; i < INV_SETUP.length; i++) {
			GUIItem item = INV_SETUP[i];
			if (item.getName() == null) {
				invContents.add(inv.getItem(i));
			}
		}

		File f = MapFileUtils.getMapFile(WSP.getInventoryType(), 'M');
		MetaFileUtils.saveInventory(f, invContents.toArray(new ItemStack[]{}));
	}

	private static void close(WSPlayer WSP) {
		Player p = WSP.getPlayer();
		if (WSP.getGUIDepth() == 1) {
			p.closeInventory();
			return;
		}
		WSP.decrementGUIDepth(); WSP.decrementGUIDepth();
		MapMetaGUI.open(WSP,WSP.getInventoryType());
	}


	static {
		air = new GUIItem(Material.AIR, null);

		GUIItem pane    = new GUIItem(Material.GRAY_STAINED_GLASS_PANE, " ");
		GUIItem barrier = new GUIItem(Material.BARRIER,                 "§cExit");
		GUIItem green   = new GUIItem(Material.GREEN_TERRACOTTA,        "§aSave");

		INV_SETUP = new GUIItem[]{
				barrier, pane, pane, green, green, green, pane, pane, pane,
				pane,    pane, pane, pane,  pane,  pane,  pane, pane, pane,
				air,     air,  air,  air,   air,   air,   air,  air,  air,
				air,     air,  air,  air,   air,   air,   air,  air,  air,
				air,     air,  air,  air,   air,   air,   air,  air,  air,
				air,     air,  air,  air,   air,   air,   air,  air,  air
		};

	}

}
