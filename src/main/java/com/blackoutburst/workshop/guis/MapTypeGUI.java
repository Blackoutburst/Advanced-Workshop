package com.blackoutburst.workshop.guis;

import com.blackoutburst.workshop.commands.Play;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.game.GameOptions;
import com.blackoutburst.workshop.utils.minecraft.GUIUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MapTypeGUI {

	public static final String NAME = "Game Type";
	public static final GUIItem[] INV_SETUP;

	public static final GUIItem normal;

	public static final GUIItem timed;

	public static final GUIItem HSMode;

	public static final GUIItem all;

	public static final GUIItem infinite;

	public static void open(WSPlayer WSP, String mapName) {
		Player p = WSP.getPlayer();
		Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, NAME);
		WSP.setInventoryType(mapName);

		GUIUtils.setItems(inv, INV_SETUP);
		p.openInventory(inv);
	}

	public static boolean click(InventoryClickEvent event) {
		if (event.getClickedInventory() == null) return false;
		if (event.getClickedInventory().getType() == InventoryType.PLAYER) return false;
		Player p = (Player) event.getWhoClicked();
		WSPlayer WSP = WSPlayer.getFromPlayer(p);
		if (WSP == null) return false;
		GUIItem item = INV_SETUP[event.getSlot()];
		if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return false;

		GameOptions gameOptions = new GameOptions();

		switch (item.getName()) {
			case "§aNormal" -> {
				gameOptions.setUnlimitedCrafts(false);
				gameOptions.setDefaultCraftLimit(5);
				gameOptions.setCraftLimit(5);
				gameOptions.setRandomType('N');
				gameOptions.setHypixelSaysMode(false);
				gameOptions.setTimeLimited(false);
				Play.searchGame(WSP, WSP.getInventoryType(), gameOptions, false);
			}
			case "§aTimed" -> {
				gameOptions.setUnlimitedCrafts(true);
				gameOptions.setRandomType('N');
				gameOptions.setHypixelSaysMode(false);
				gameOptions.setTimeLimited(true);
				gameOptions.setDefaultTimeLimit(60.0f);
				gameOptions.setTimeLimit(60.0f);
				Play.searchGame(WSP, WSP.getInventoryType(), gameOptions, false);
			}
			case "§aHypixel Says Mode" -> {
				gameOptions.setUnlimitedCrafts(false);
				gameOptions.setDefaultCraftLimit(5);
				gameOptions.setCraftLimit(5);
				gameOptions.setRandomType('N');
				gameOptions.setHypixelSaysMode(true);
				gameOptions.setTimeLimited(false);
				Play.searchGame(WSP, WSP.getInventoryType(), gameOptions, false);
			}
			case "§aAll Crafts" -> {
				gameOptions.setUnlimitedCrafts(false);
				gameOptions.setRandomType('N');
				gameOptions.setHypixelSaysMode(false);
				gameOptions.setTimeLimited(false);
				Play.searchGame(WSP, WSP.getInventoryType(), gameOptions, true);
			}
			case "§aInfinite" -> {
				gameOptions.setUnlimitedCrafts(true);
				gameOptions.setRandomType('N');
				gameOptions.setHypixelSaysMode(false);
				gameOptions.setTimeLimited(false);
				Play.searchGame(WSP, WSP.getInventoryType(), gameOptions, false);
			}
		}
		return true;
	}


	static {
		ItemStack infBedrock = new ItemStack(Material.BEDROCK);
		infBedrock.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);

		normal   = new GUIItem(Material.CRAFTING_TABLE, "§aNormal");
		timed    = new GUIItem(Material.CLOCK,          "§aTimed");
		HSMode   = new GUIItem(Material.GOLD_BLOCK,     "§aHypixel Says Mode");
		all      = new GUIItem(Material.CHEST,          "§aAll Crafts");
		infinite = new GUIItem(infBedrock,              "§aInfinite");

		INV_SETUP = new GUIItem[]{
				normal, timed, HSMode, all, infinite
		};
	}

}
