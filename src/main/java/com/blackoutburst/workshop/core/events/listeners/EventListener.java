package com.blackoutburst.workshop.core.events.listeners;

import com.blackoutburst.workshop.core.events.spigot.*;
import com.jeff_media.armorequipevent.ArmorEquipEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Join.execute(event);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Quit.execute(event);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        FoodLevelChange.execute(event);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
       BlockBreak.execute(event);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        BlockPlace.execute(event);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        ItemDrop.execute(event);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        EntityDamage.execute(event);
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        EntityInteract.execute(event);
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        BlockDamage.execute(event);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryClick.execute(event);
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {
        Interaction.execute(event);
        PlayerInteract.execute(event);
        PlaceEntity.execute(event);
        OpenHopper.execute(event);
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        PlayerInteractEntity.execute(event);
    }

    @EventHandler
    public void onEntityInteract(EntityDamageByEntityEvent event) {
        EntityDamageByEntity.execute(event);
    }

    @EventHandler
    public void onPlayerEggThrowEvent(PlayerEggThrowEvent event) { EggThrow.execute(event); }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        PlayerMove.execute(event);
    }

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        InventoryOpen.execute(event);
    }

    @EventHandler
    public void onTakeBookEvent(PlayerTakeLecternBookEvent event) {
        TakeBook.execute(event);
    }

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent event) { ArmorEquip.execute(event); }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) { InventoryClose.execute(event); }


}
