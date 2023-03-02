package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetUnlimitedCraft implements CommandExecutor {

    private boolean isUnlimited(String value) {
        return switch (value) {
            case "yes", "1", "true" -> true;
            default -> false;
        };
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            WSPlayer wsplayer = WSPlayer.getFromPlayer(player);
            if (wsplayer == null) return true;

            if (wsplayer.isInGame()) {
                wsplayer.getPlayer().sendMessage("§cYou cannot use this command in game!");
                return true;
            }

            if (args.length != 1) {
                wsplayer.getPlayer().sendMessage("§cYou must specify \"yes\" or \"no\"");
                return true;
            }

            wsplayer.getGameOptions().setUnlimitedCrafts(isUnlimited(args[0]));

            wsplayer.getPlayer().sendMessage("§aYou " + (wsplayer.getGameOptions().isUnlimitedCrafts() ? "§eenabled" : "§edisabled" + " §aunlimited crafts"));
        }
        return true;
    }
}
