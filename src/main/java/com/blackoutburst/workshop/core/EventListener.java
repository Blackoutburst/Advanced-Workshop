package com.blackoutburst.workshop.core;

import com.blackoutburst.workshop.core.events.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        PlayerInteract.execute(event);
    }
}
