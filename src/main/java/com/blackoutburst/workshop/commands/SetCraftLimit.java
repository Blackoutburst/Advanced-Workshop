package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.guis.CraftSelectorGUI;
import com.blackoutburst.workshop.utils.GameUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCraftLimit implements CommandExecutor {

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
                wsplayer.getPlayer().sendMessage("§cYou must enter a valid craft amount!");
                return true;
            }

            int craftLimit = 0;

            try {
                craftLimit = Integer.parseInt(args[0]);

                if (craftLimit < 1) throw new Exception();
            } catch (Exception ignored) {
                wsplayer.getPlayer().sendMessage("§cYou must enter a valid craft amount!");
                return true;
            }

            wsplayer.getGameOptions().setCraftLimit(craftLimit);
            wsplayer.getPlayer().sendMessage("§aYou've set the craft limit to §f: §e" + (wsplayer.getGameOptions().getCraftLimit()));
        }
        return true;
    }
}
