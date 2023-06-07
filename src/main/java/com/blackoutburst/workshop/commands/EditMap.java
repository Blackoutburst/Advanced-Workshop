package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.guis.MapMetaGUI;
import com.blackoutburst.workshop.guis.MapSelector;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EditMap implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender instanceof Player player) {
			WSPlayer wsplayer = WSPlayer.getFromPlayer(player);
			if (wsplayer == null) return true;

			if (args.length != 1) {
				wsplayer.setEditing(true);
				MapSelector.open(wsplayer, 0);
				return true;
			}
			wsplayer.setInventoryType(args[0]);
			MapMetaGUI.open(wsplayer, args[0]);
		}
		return true;
	}
}
