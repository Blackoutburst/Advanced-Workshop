package com.blackoutburst.workshop;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Craft {

    protected String name;

    protected ItemStack itemRequired;
    protected ItemStack[] craftingTable;

    protected List<ItemStack> materials;

    public Craft(String name, ItemStack itemRequired, ItemStack[] craftingTable, List<ItemStack> materials) {
        this.name = name;
        this.itemRequired = itemRequired;
        this.craftingTable = craftingTable;
        this.materials = materials;
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

    public List<ItemStack> getMaterials() {
        return materials;
    }
}
