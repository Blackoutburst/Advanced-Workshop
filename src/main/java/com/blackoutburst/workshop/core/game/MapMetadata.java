package com.blackoutburst.workshop.core.game;

import org.bukkit.inventory.ItemStack;

public class MapMetadata {
	private final ItemStack[] inventoryContents;

	private final ItemStack[] armorContents;

	private final ItemStack offHand;

	private final ItemStack icon;

	public MapMetadata(ItemStack[] inventoryContents, ItemStack[] armorContents, ItemStack offHand, ItemStack icon) {
		this.inventoryContents = inventoryContents;
		this.armorContents = armorContents;
		this.offHand = offHand;
		this.icon = icon;
	}

	public MapMetadata(ItemStack[] armorContents, ItemStack offHand, ItemStack icon) {
		this.inventoryContents = null;
		this.armorContents = armorContents;
		this.offHand = offHand;
		this.icon = icon;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public ItemStack getOffHand() {
		return offHand;
	}

	public ItemStack[] getArmorContents() {
		return armorContents;
	}

	public ItemStack[] getInventoryContents() {
		return inventoryContents;
	}
}
