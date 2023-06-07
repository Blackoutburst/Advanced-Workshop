package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.files.DBUtils;
import com.blackoutburst.workshop.utils.minecraft.CraftUtils;
import com.blackoutburst.workshop.utils.misc.EffectsUtils;
import com.blackoutburst.workshop.utils.misc.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;

import java.time.Duration;
import java.time.Instant;

public class CraftItem {

	public static void execute(CraftItemEvent event) {
		WSPlayer WSP = WSPlayer.getFromPlayer((Player) event.getWhoClicked());
		if (WSP == null) return;
		CraftUtils.checkCraft(event.getRecipe().getResult(), WSP);
	}
}
