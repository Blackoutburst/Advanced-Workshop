package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.core.BrokenBlock;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.MapUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class L implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            WSPlayer wsplayer = WSPlayer.getFromPlayer((Player) sender);
            if (wsplayer == null) return true;

            wsplayer.setInGame(false);

            wsplayer.setCurrentCraft(null);

            MapUtils.restoreArea(wsplayer);

            wsplayer.getBrokenBlocks().clear();
            wsplayer.getPlayer().sendMessage("Game stopped");

        }
        return true;
    }
}
