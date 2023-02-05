package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SetSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Location location = ((Player) sender).getLocation();
            Main.spawn = location.clone();

            try {
                PrintWriter writer = new PrintWriter("./plugins/Workshop/spawn");
                writer.write(location.getWorld().getName() + ", " + location.getX() + ", " + location.getY() + ", " + location.getZ() + ", " + location.getYaw() + ", " + location.getPitch());
                writer.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
