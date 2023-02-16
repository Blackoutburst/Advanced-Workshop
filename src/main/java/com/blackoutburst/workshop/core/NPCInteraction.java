package com.blackoutburst.workshop.core;

import com.blackout.npcapi.core.APlayer;
import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.core.NPCPacket;
import com.blackoutburst.workshop.utils.DBUtils;
import com.blackoutburst.workshop.utils.EffectsUtils;
import com.blackoutburst.workshop.utils.StringUtils;
import com.blackoutburst.workshop.utils.Webhook;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.time.Instant;

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
            if (wsPlayer.isWaiting()) return;

            if (player.getInventory().containsAtLeast(wsPlayer.getCurrentCraft().getItemRequired(), 1)) {
                wsPlayer.setNextRound(true);
                EffectsUtils.playLevelUPSound(player);
                wsPlayer.getTimers().setRoundEnd(Instant.now());

                Float duration = Duration.between(wsPlayer.getTimers().getRoundBegin(), wsPlayer.getTimers().getRoundEnd()).toMillis() / 1000.0f;
                String roundTime = StringUtils.ROUND.format(duration) + "s";

                Double currentDuration = DBUtils.getData(player, wsPlayer.getPlayArea().type + "." + "crafts" + "." + wsPlayer.getCurrentCraft().getName(), Double.class);
                if (currentDuration == null)
                    currentDuration = Double.MAX_VALUE;

                if (duration < currentDuration)
                    DBUtils.saveData(player, wsPlayer.getPlayArea().type + "." + "crafts" + "." + wsPlayer.getCurrentCraft().getName(), duration, Float.class);

                String pb = (currentDuration != Double.MAX_VALUE && (duration < currentDuration) ? " §d§lPB! (" + StringUtils.ROUND.format(duration - currentDuration) + "s" + ")" : "");

                player.sendMessage("§ePerfect! Just what I needed. §b(" + roundTime + ")" + pb);
                continue;
            }
            player.sendMessage("§cThat's not quite right. I need " + wsPlayer.getCurrentCraft().getName());
        }
    }
}
