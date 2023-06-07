package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;
import com.jeff_media.armorequipevent.ArmorEquipEvent;
import org.bukkit.entity.Player;

public class ArmorEquip {

	public static void execute(ArmorEquipEvent event) {
		Player p = event.getPlayer();
		WSPlayer Wsp = WSPlayer.getFromPlayer(p);
		event.setCancelled(Wsp != null && Wsp.isInGame());
	}
}
