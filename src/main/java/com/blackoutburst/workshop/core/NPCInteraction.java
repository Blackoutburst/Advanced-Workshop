package com.blackoutburst.workshop.core;

import com.blackout.npcapi.core.APlayer;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.core.NPCPacket;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class NPCInteraction implements NPCPacket {

    @Override
    public void onLeftClick(Player player, int id) {
        APlayer ap = APlayer.get(player);
        for (NPC npc : ap.npcs) {
            if (id != npc.getEntityId()) { continue; }
            if (!npc.getName().equals("chicken")) { continue; }
            if (WSPlayer.getFromPlayer(player).isWaiting()) { return; }

            player.getInventory().addItem(new ItemStack(Material.EGG));
        }
    }

    @Override
    public void onRightClick(Player player, int id) {
        APlayer ap = APlayer.get(player);
        for (NPC npc : ap.npcs) {
            if (id != npc.getEntityId()) { continue; }
            if (!npc.getName().equals("villager")) { continue; }

            WSPlayer wsPlayer = WSPlayer.getFromPlayer(player);
            if (wsPlayer == null) return;
            if (wsPlayer.waiting) return;

            if (player.getInventory().containsAtLeast(wsPlayer.getCurrentCraft().getItemRequired(), 1)) {
                wsPlayer.setNextRound(true);
                player.sendMessage("§ePerfect! Just what I needed.");
                continue;
            }
            player.sendMessage("§cThat's not quite right. I need " + wsPlayer.getCurrentCraft().getName());
        }
    }
}
