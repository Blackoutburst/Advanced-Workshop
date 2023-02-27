package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.PlayArea;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListMaps implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§e§m--------------------");
        for (PlayArea area : Main.playAreas) {
            sender.sendMessage(area.isBusy() ? "§c" : "§a" + area.getType()+ " §6("+area.getAnchor().getX()+", "+area.getAnchor().getY()+", "+area.getAnchor().getZ()+")");
        }
        sender.sendMessage("§e§m--------------------");

        return true;
    }
}
