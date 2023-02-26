package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetUnlimitedCraft implements CommandExecutor {

    private boolean isUnlimited(String value) {
        switch (value) {
            case "yes": case "1": case "true": return true;
            default: return false;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            WSPlayer wsplayer = WSPlayer.getFromPlayer((Player) sender);
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
