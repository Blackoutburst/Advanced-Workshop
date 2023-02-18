package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTimeLimit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        WSPlayer wsplayer = WSPlayer.getFromPlayer(player);
        if (wsplayer == null) return true;

        if (args.length == 0) {
            player.sendMessage("§cYou must specify a time!");
            return true;
        }
        if (!args[0].matches("[0-9]+([.][0-9]+)?")) {
            player.sendMessage("§cThe time must be a number!");
            return true;
        }
        wsplayer.getGameOptions().setTimeLimit(Float.parseFloat(args[0]));

        return true;
    }
}
