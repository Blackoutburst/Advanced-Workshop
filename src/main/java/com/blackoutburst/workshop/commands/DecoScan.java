package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.MapUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DecoScan implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            WSPlayer wsplayer = WSPlayer.getFromPlayer((Player) sender);
            if (wsplayer == null) return true;
            if (wsplayer.getScanWand1() == null || wsplayer.getScanWand2() == null) {
                wsplayer.getPlayer().sendMessage("§cYou must select an area using the Scan wand first");
                return true;
            }

            if (args.length != 1) {
                wsplayer.getPlayer().sendMessage("§cYou must enter a valid map name");
                return true;
            }

            wsplayer.getPlayer().sendMessage("§bScanning...");

            MapUtils.decoScan(wsplayer, args[0]);

            wsplayer.getPlayer().sendMessage("§bScan completed");
        }
        return true;
    }
}
