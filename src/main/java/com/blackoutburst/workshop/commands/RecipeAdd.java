package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.guis.CraftGUI;
import com.blackoutburst.workshop.utils.GameUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RecipeAdd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            WSPlayer wsplayer = WSPlayer.getFromPlayer((Player) sender);
            if (wsplayer == null) return true;

            if (args.length != 1) {
                wsplayer.getPlayer().sendMessage("§cYou must enter a valid map name");
                return true;
            }

            GameUtils.loadCraft(wsplayer, args[0]);
            wsplayer.setInventoryType(args[0]);
            CraftGUI.open(wsplayer);
        }
        return true;
    }
}
