package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.GameUtils;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Interaction {

    private static void clickFurnace(PlayerInteractEvent event) {
        Furnace furnace = (Furnace) event.getClickedBlock().getState();
        furnace.getInventory().setFuel(new ItemStack(Material.COAL, 64));

        GameUtils.instantSmelt(furnace);
    }

    public static void execute(PlayerInteractEvent event) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer(event.getPlayer());
        if (wsplayer == null || !wsplayer.isInGame()) return;
        if (event.getClickedBlock() == null) return;

        Material blockType = event.getClickedBlock().getType();
        if (blockType.equals(Material.FURNACE)) {
            clickFurnace(event);
        }
    }
}
