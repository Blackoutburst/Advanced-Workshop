package com.blackoutburst.workshop.core.events;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.GameUtils;
import net.minecraft.server.v1_8_R3.ContainerDispenser;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.event.block.Action;
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

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem().getType() == Material.ARMOR_STAND) {
            event.setCancelled(true);
        }


        Material blockType = event.getClickedBlock().getType();
        if (blockType.equals(Material.FURNACE) || blockType.equals(Material.BURNING_FURNACE)) {
            clickFurnace(event);
        }
    }
}
