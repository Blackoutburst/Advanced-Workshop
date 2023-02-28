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

    private static void blaze(Player player) {
        player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD));
    }

    private static void chicken(Player player) {
        player.getInventory().addItem(new ItemStack(Material.EGG));
    }

    private static void hoglin(Player player) {
        player.getInventory().addItem(new ItemStack(Material.LEATHER));
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

        if (duration < currentDuration && !wsPlayer.hasStored())
            DBUtils.saveData(player, wsPlayer.getPlayArea().getType() + "." + "crafts" + "." + wsPlayer.getCurrentCraft().getName(), duration, Float.class);

        String message = (currentDuration != Double.MAX_VALUE && (duration < currentDuration) && !wsPlayer.hasStored() ? "§d§lPB! (" + StringUtils.ROUND.format(duration - currentDuration) + "s" + ")" : "");

        if (wsPlayer.getGameOptions().isShowNonPBs() && duration >= currentDuration) {
            message = ("§c§l(+" + StringUtils.ROUND.format(duration - currentDuration) + "s)");
        }

        player.sendMessage("§ePerfect! Just what I needed. §b(" + roundTime + ") " + message);

        wsPlayer.setHasStored(false);
    }

    private static void witherSkeleton(Player player) {
        player.getInventory().addItem(new ItemStack(Material.BONE));
        player.getInventory().addItem(new ItemStack(Material.COAL));
    }

    public static void execute(NMSEntityUseEvent event) {
        if (event.getHand().equals(NMSEntityUseEvent.Hand.OFF_HAND)) return;
        if (WSPlayer.getFromPlayer(event.getPlayer()).isWaiting()) return;

        Player player = event.getPlayer();
        NMSEntity entity = event.getEntity();

        switch (entity.getType()) {
            case BLAZE: blaze(player); break;
            case CHICKEN: chicken(player); break;
            case HOGLIN: hoglin(player); break;
            case WITHER_SKELETON: witherSkeleton(player); break;
        }

        if (entity.getType().equals(NMSEntityType.VILLAGER) && event.getAction().equals(NMSEntityUseEvent.Action.RIGHT_CLICK)) {
            villager(player);
        }
    }
}
