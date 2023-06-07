package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;

public class TakeBook {
	public static void execute(PlayerTakeLecternBookEvent event) {
		WSPlayer wsPlayer = WSPlayer.getFromPlayer(event.getPlayer());
		event.setCancelled(wsPlayer != null && wsPlayer.isInGame());
	}
}
