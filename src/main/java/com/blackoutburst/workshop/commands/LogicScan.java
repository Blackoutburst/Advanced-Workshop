package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.map.LogicUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LogicScan implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
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

            LogicUtils.scan(wsplayer, args[0]);

            player.sendMessage("§bScan completed");
        }
        return true;
    }
}
