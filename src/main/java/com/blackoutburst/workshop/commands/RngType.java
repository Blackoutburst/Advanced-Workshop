package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.core.GameOptions;
import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RngType implements CommandExecutor {
    private char randomType(String value) {
        value = value.toLowerCase();

        switch (value) {
            case "n":
            case "normal":
            case "nonrepeating": return 'N';
            case "b":
            case "bagged":
            case "bag": return 'B';

            default: return 'R';
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        WSPlayer wsplayer = WSPlayer.getFromPlayer((Player) sender);
        if (wsplayer == null) return true;

        Player player = (Player) sender;

        if (wsplayer.isInGame()) {
            wsplayer.getPlayer().sendMessage("§cYou cannot use this command in game!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§cYou must specify \"bagged\", \"nonRepeating\", or \"random\"");
            return true;
        }
        char type = randomType(args[0]);
        GameOptions gameoptions = wsplayer.getGameOptions();

        switch (type) {
            case 'B':
                if (args.length == 1) {
                    player.sendMessage("§cYou must specify a bag size");
                    break;
                }
                if (!args[1].matches("[0-9]+")) {
                    player.sendMessage("§cThe bag size must be a number");
                    break;
                }
                gameoptions.setRandomType('B');
                gameoptions.setBagSize(Integer.parseInt(args[1]));
                player.sendMessage("§aRNG Type successfully changed to §ebagged");
                break;
            case 'R':
                gameoptions.setRandomType('R');
                player.sendMessage("§aRNG Type successfully changed to §ecompletely random");
                break;
            default:
                gameoptions.setRandomType('N');
                player.sendMessage("§aRNG Type successfully changed to §enon-repeating");
                break;
        }
        return true;
    }
}
