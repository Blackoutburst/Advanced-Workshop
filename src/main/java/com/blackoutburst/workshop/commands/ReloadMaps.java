package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.utils.MapUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadMaps implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        sender.sendMessage("§aReloading maps...");
        Main.playAreas.clear();
        MapUtils.loadPlayAreas();
        sender.sendMessage("§aMaps reloaded");
        return true;
    }
}
