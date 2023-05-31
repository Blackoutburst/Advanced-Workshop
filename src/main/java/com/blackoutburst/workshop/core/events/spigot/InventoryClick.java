package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.guis.CraftGUI;
import com.blackoutburst.workshop.guis.CraftSelectorGUI;

import com.blackoutburst.workshop.guis.MapMetaGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class InventoryClick {


    // TODO not working properly
    private static void preventCraftingTableUse(InventoryClickEvent event, WSPlayer wsplayer) {
        if (wsplayer != null && wsplayer.isInGame() && event.getInventory().getType().equals(InventoryType.CRAFTING) &&
                event.getSlotType().equals(InventoryType.SlotType.CRAFTING)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage("§cYou must use the crafting table!");
        }
    }

    public static void execute(InventoryClickEvent event) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer((Player) event.getWhoClicked());

        switch (event.getView().getTitle()) {
            case CraftGUI.NAME ->
                    event.setCancelled(CraftGUI.click(event.getClickedInventory(),
                            event.getSlot(), (Player) event.getWhoClicked(), event.getView().getTitle()));
            case CraftSelectorGUI.NAME ->
                    event.setCancelled(CraftSelectorGUI.click(event.getClickedInventory(),
                            event.getSlot(), (Player) event.getWhoClicked(), event.getView().getTitle()));
            case MapMetaGUI.NAME -> event.setCancelled(MapMetaGUI.click(event));
        }

        oldHotkeyBehaviour(event, (Player) event.getWhoClicked());
        preventCraftingTableUse(event, wsplayer);
    }

    public static void oldHotkeyBehaviour(InventoryClickEvent event, Player player) {
        if (!event.getClick().isKeyboardClick()) return;

        int hotbarSlot = event.getHotbarButton();
        Inventory clickedInv = event.getClickedInventory();
        if (clickedInv == null) return;
        if (event.getSlot() == hotbarSlot && clickedInv.getType() == InventoryType.PLAYER) return;
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack hotbarItem = null;
        if (hotbarSlot != -1) {
            hotbarItem = player.getInventory().getItem(hotbarSlot);
        }
        if (hotbarItem == null || clickedItem == null ||
                hotbarItem.getType() != clickedItem.getType()) return;

        Inventory inv = event.getClickedInventory();
        int hotbarItemAmount = hotbarItem.getAmount();
        int clickedItemAmount = clickedItem.getAmount();
        if (hotbarItemAmount + clickedItemAmount > hotbarItem.getType().getMaxStackSize()) return;
        if (inv == null) return;
        if (event.getSlotType() == InventoryType.SlotType.RESULT) {
            List<ItemStack> contents =  Arrays.stream(inv.getContents()).toList();
            contents.forEach(x -> x.setAmount(x.getAmount() - 1));
            ItemStack[] results = contents.toArray(new ItemStack[0]);
            inv.setContents(results);
        }
        hotbarItem.setAmount(hotbarItemAmount + clickedItemAmount);
        clickedItem.setAmount(0);
    }

}
