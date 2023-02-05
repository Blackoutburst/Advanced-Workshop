package com.blackoutburst.workshop.core;

import com.blackout.npcapi.core.APlayer;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.core.NPCPacket;
import com.blackoutburst.workshop.utils.GameUtils;
import com.blackoutburst.workshop.utils.MapUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NPCInteraction implements NPCPacket {

    @Override
    public void onLeftClick(Player player, int id) {
        APlayer ap = APlayer.get(player);
        for (NPC npc : ap.npcs) {
            if (id == npc.getEntityId()) {
                if (npc.getName().equals("chicken")) {
                    player.getInventory().addItem(new ItemStack(Material.EGG));
                }
            }
        }
    }

    @Override
    public void onRightClick(Player player, int id) {
        APlayer ap = APlayer.get(player);
        for (NPC npc : ap.npcs) {
            if (id == npc.getEntityId()) {
                if (npc.getName().equals("villager")) {
                    WSPlayer wsPlayer = WSPlayer.getFromPlayer(player);
                    if (wsPlayer == null) return;
                    if (player.getInventory().containsAtLeast(wsPlayer.getCurrentCraft().getItemRequired(), 1)) {
                        wsPlayer.setNextRound(true);
                    }
                }
            }
        }
    }
}
