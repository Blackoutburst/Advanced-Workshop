package com.blackoutburst.workshop.core.events.nms;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.nms.NMSEntity;
import com.blackoutburst.workshop.nms.NMSEntityType;
import com.blackoutburst.workshop.nms.NMSEntityUseEvent;
import com.blackoutburst.workshop.utils.files.DBUtils;
import com.blackoutburst.workshop.utils.misc.EffectsUtils;
import com.blackoutburst.workshop.utils.misc.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.time.Instant;

public class EntityUse {

    private static void chicken(Player player) {
        if (WSPlayer.getFromPlayer(player).isWaiting()) return;

        player.getInventory().addItem(new ItemStack(Material.EGG));
    }

    private static void villager(Player player) {
        WSPlayer wsPlayer = WSPlayer.getFromPlayer(player);

        if (wsPlayer.isWaiting()) return;

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

    public static void execute(NMSEntityUseEvent event) {
        if (event.getHand().equals(NMSEntityUseEvent.Hand.OFF_HAND)) return;

        Player player = event.getPlayer();
        NMSEntity entity = event.getEntity();

        if (entity.getType().equals(NMSEntityType.CHICKEN)) {
            chicken(player);
        }

        if (entity.getType().equals(NMSEntityType.VILLAGER) && event.getAction().equals(NMSEntityUseEvent.Action.RIGHT_CLICK)) {
            villager(player);
        }
    }
}
