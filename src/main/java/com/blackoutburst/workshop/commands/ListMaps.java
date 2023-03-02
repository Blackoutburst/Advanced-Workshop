package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.PlayArea;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ListMaps implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        sender.sendMessage("§e§m--------------------");

        int index = 0;
        for (PlayArea area : Main.playAreas) {
            sender.sendMessage(index + " | "+ (area.isBusy() ? "§c" : "§a") + area.getType()+ " §6("+area.getAnchor().getX()+", "+area.getAnchor().getY()+", "+area.getAnchor().getZ()+")");
            index++;
        }
        sender.sendMessage("§e§m--------------------");

        return true;
    }
}
