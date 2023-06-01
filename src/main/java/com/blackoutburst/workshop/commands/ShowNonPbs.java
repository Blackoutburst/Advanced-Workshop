package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.misc.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShowNonPbs implements CommandExecutor {
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

            wsplayer.getGameOptions().setShowNonPBs(StringUtils.isYes(args[0]));

            wsplayer.getPlayer().sendMessage("§aYou " + (wsplayer.getGameOptions().isShowNonPBs() ? "§eenabled" : "§edisabled") + " §ashowing non-pb times");
        }
        return true;
    }
}