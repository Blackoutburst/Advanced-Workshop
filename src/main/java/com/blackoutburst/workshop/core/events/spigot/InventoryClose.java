package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryClose {

	public static void execute(InventoryCloseEvent event) {
		Player p = (Player) event.getPlayer();
		WSPlayer WSP = WSPlayer.getFromPlayer(p);
		if (WSP == null) return;
		WSP.setGUIDepth(0);
	}

}
