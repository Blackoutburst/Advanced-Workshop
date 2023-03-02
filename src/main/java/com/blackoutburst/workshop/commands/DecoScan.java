package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.map.DecoUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DecoScan implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            WSPlayer wsplayer = WSPlayer.getFromPlayer(player);
            if (wsplayer == null) return true;
            
            if (wsplayer.getScanWand1() == null || wsplayer.getScanWand2() == null) {
                player.sendMessage("§cYou must select an area using the Scan wand first");
                return true;
            }

            if (args.length != 1) {
                player.sendMessage("§cYou must enter a valid map name");
                return true;
            }

            player.sendMessage("§bScanning...");

            DecoUtils.scan(wsplayer, args[0]);

            player.sendMessage("§bScan completed");
        }
        return true;
    }
}
