package com.blackoutburst.workshop;

import org.bukkit.inventory.ItemStack;

public class Craft {
    protected String name;
    protected ItemStack itemRequired;
    protected ItemStack[] craftingTable;

    public Craft(String name, ItemStack itemRequired, ItemStack[] craftingTable) {
        this.name = name;
        this.itemRequired = itemRequired;
        this.craftingTable = craftingTable;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemRequired() {
        return itemRequired;
    }

    public ItemStack[] getCraftingTable() {
        return craftingTable;
    }

}
