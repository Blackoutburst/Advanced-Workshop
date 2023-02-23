package com.blackoutburst.workshop.core.blocks;

import org.bukkit.inventory.ItemStack;

public class RandomItem {

    protected ItemStack item;

    protected boolean priority;

    public RandomItem(ItemStack item, boolean priority) {
        this.item = item;
        this.priority = priority;
    }

    public ItemStack GetItem() { return item; }

    public void setItem(ItemStack item) { this.item = item; }

    public boolean isPriority() { return priority; }

    public void setPriority(boolean priority) { this.priority = priority; }
}
