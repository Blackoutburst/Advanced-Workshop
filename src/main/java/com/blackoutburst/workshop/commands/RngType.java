package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.core.game.GameOptions;
import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RngType implements CommandExecutor {
    private char randomType(String value) {
        value = value.toLowerCase();

        return switch (value) {
            case "n", "normal", "nonrepeating" -> 'N';
            case "b", "bagged", "bag" -> 'B';
            default -> 'R';
        };
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) { return true; }
        WSPlayer wsplayer = WSPlayer.getFromPlayer(player);
        if (wsplayer == null) return true;

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
            case 'B' -> {
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
            }
            case 'R' -> {
                gameoptions.setRandomType('R');
                player.sendMessage("§aRNG Type successfully changed to §ecompletely random");
            }
            default -> {
                gameoptions.setRandomType('N');
                player.sendMessage("§aRNG Type successfully changed to §enon-repeating");
            }
        }
        return true;
    }
}
