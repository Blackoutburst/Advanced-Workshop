package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.minecraft.FurnaceUtils;

import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Furnace;
import org.bukkit.block.Smoker;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Interaction {

    private static void clickFurnace(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;

        Furnace furnace = (Furnace) event.getClickedBlock().getState();
        furnace.getInventory().setFuel(new ItemStack(Material.COAL, 64));

        FurnaceUtils.instantSmelt(furnace);
    }
    private static void clickBlastFurnace(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;

        BlastFurnace furnace = (BlastFurnace) event.getClickedBlock().getState();
        furnace.getInventory().setFuel(new ItemStack(Material.COAL, 64));

        FurnaceUtils.instantSmelt(furnace);
    }
    private static void clickSmoker(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;

        Smoker furnace = (Smoker) event.getClickedBlock().getState();
        furnace.getInventory().setFuel(new ItemStack(Material.COAL, 64));

        FurnaceUtils.instantSmelt(furnace);
    }

    public static void execute(PlayerInteractEvent event) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer(event.getPlayer());
        if (wsplayer == null || !wsplayer.isInGame()) return;
        if (event.getClickedBlock() == null) return;

        Material blockType = event.getClickedBlock().getType();
        if (blockType.equals(Material.FURNACE)) {
            clickFurnace(event);
        }
        if (blockType.equals(Material.BLAST_FURNACE)) {
            clickBlastFurnace(event);
        }
        if (blockType.equals(Material.SMOKER)) {
            clickSmoker(event);
        }
    }
}
