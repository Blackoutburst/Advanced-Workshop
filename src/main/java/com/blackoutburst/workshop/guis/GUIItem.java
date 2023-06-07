package com.blackoutburst.workshop.guis;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GUIItem {

	private final ItemStack item;

	private final String name;


	public GUIItem(Material mat, String name) {
		this.item = new ItemStack(mat);
		this.name = name;
	}

	public GUIItem(ItemStack item) {
		this.item = item;
		this.name = null;
	}

	public GUIItem(ItemStack item, String name) {
		this.item = item;
		this.name = name;
	}

	public ItemStack getItem() {
		return item;
	}

	public String getName() {
		return name;
	}

}
