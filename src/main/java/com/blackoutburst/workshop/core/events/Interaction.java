package com.blackoutburst.workshop.core.events;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.commands.Play;
import com.blackoutburst.workshop.core.BrokenBlock;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.GameUtils;
import com.blackoutburst.workshop.utils.MapUtils;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Interaction {

    private static void clickVillager(WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();

        if (player.getInventory().containsAtLeast(wsplayer.getCurrentCraft().getItemRequired(), 1)) {
            GameUtils.startRound(wsplayer);
            MapUtils.restoreArea(wsplayer);
        }
    }

    private static void clickFurnace(PlayerInteractEvent event) {
        Furnace furnace = (Furnace) event.getClickedBlock().getState();
        furnace.getInventory().setFuel(new ItemStack(Material.COAL, 64));

        GameUtils.instantSmelt(furnace);
    }

    public static void execute(PlayerInteractEvent event) {
        WSPlayer wsplayer = WSPlayer.getFromPlayer(event.getPlayer());
        if (wsplayer == null || !wsplayer.isInGame()) return;
        if (event.getClickedBlock() == null) return;

        if (event.getClickedBlock().getType().equals(Material.EMERALD_BLOCK)) {
            clickVillager(wsplayer);
        }

        Material blockType = event.getClickedBlock().getType();
        if (blockType.equals(Material.FURNACE) || blockType.equals(Material.BURNING_FURNACE)) {
            clickFurnace(event);
        }
    }
}
