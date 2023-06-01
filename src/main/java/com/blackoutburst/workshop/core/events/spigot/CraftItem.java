package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.files.DBUtils;
import com.blackoutburst.workshop.utils.misc.EffectsUtils;
import com.blackoutburst.workshop.utils.misc.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;

import java.time.Duration;
import java.time.Instant;

public class CraftItem {

	public static void execute(CraftItemEvent event) {
		Player player = (Player) event.getWhoClicked();
		WSPlayer WSP = WSPlayer.getFromPlayer(player);
		if (WSP == null) return;
		if (WSP.isWaiting()) return;
		if (!WSP.getGameOptions().isHypixelSaysMode()) return;

		Material required = WSP.getCurrentCraft().getItemRequired().getType();
		if (event.getRecipe().getResult().getType() != required) {
			player.sendMessage("§cThat's not quite right. I need " + WSP.getCurrentCraft().getName());
			return;
		}

		WSP.setWaiting(true);
		WSP.setNextRound(true);
		EffectsUtils.playLevelUPSound(player);
		WSP.getTimers().setRoundEnd(Instant.now());

		Float duration = Duration.between(WSP.getTimers().getRoundBegin(), WSP.getTimers().getRoundEnd()).toMillis() / 1000.0f;
		String roundTime = StringUtils.ROUND.format(duration) + "s";

		player.sendMessage("§ePerfect! Just what I needed. §b(" + roundTime + ") ");

		WSP.setHasStored(false);
	}
}
