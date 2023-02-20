package com.blackoutburst.workshop.core.events.nms;

import com.blackout.npcapi.core.NPC;
import com.blackoutburst.workshop.core.NMSNPCInteractEvent;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.DBUtils;
import com.blackoutburst.workshop.utils.EffectsUtils;
import com.blackoutburst.workshop.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.time.Instant;

public class NPCInteract {

    private static void chicken(Player player) {
        if (WSPlayer.getFromPlayer(player).isWaiting()) return;

        player.getInventory().addItem(new ItemStack(Material.EGG));
    }

    private static void villager(Player player) {
        WSPlayer wsPlayer = WSPlayer.getFromPlayer(player);

        if (!player.getInventory().containsAtLeast(wsPlayer.getCurrentCraft().getItemRequired(), 1)) {
            player.sendMessage("§cThat's not quite right. I need " + wsPlayer.getCurrentCraft().getName());
            return;
        }

        wsPlayer.setNextRound(true);
        EffectsUtils.playLevelUPSound(player);
        wsPlayer.getTimers().setRoundEnd(Instant.now());

        Float duration = Duration.between(wsPlayer.getTimers().getRoundBegin(), wsPlayer.getTimers().getRoundEnd()).toMillis() / 1000.0f;
        String roundTime = StringUtils.ROUND.format(duration) + "s";

        Double currentDuration = DBUtils.getData(player, wsPlayer.getPlayArea().getType() + "." + "crafts" + "." + wsPlayer.getCurrentCraft().getName(), Double.class);
        if (currentDuration == null)
            currentDuration = Double.MAX_VALUE;

        if (duration < currentDuration)
            DBUtils.saveData(player, wsPlayer.getPlayArea().getType() + "." + "crafts" + "." + wsPlayer.getCurrentCraft().getName(), duration, Float.class);

        String pb = (currentDuration != Double.MAX_VALUE && (duration < currentDuration) ? " §d§lPB! (" + StringUtils.ROUND.format(duration - currentDuration) + "s" + ")" : "");

        player.sendMessage("§ePerfect! Just what I needed. §b(" + roundTime + ")" + pb);
    }

    public static synchronized void execute(NMSNPCInteractEvent event) {
        Player player = event.getPlayer();
        NPC npc = event.getNpc();

        switch (npc.getName()) {
            case "chicken": chicken(player); break;
            case "villager": villager(player); break;
        }
    }
}
