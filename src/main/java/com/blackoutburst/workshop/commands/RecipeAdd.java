package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.guis.CraftSelectorGUI;
import com.blackoutburst.workshop.utils.minecraft.CraftUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RecipeAdd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            WSPlayer wsplayer = WSPlayer.getFromPlayer(player);
            if (wsplayer == null) return true;

            if (args.length != 1) {
                wsplayer.getPlayer().sendMessage("§cYou must enter a valid map name");
                return true;
            }

            CraftUtils.loadCraft(wsplayer, args[0]);
            wsplayer.setInventoryType(args[0]);
            CraftSelectorGUI.open(wsplayer, 0);
        }
        return true;
    }
}
