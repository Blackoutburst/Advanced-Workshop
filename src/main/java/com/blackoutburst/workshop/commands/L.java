package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.GameUtils;
import com.blackoutburst.workshop.utils.MapFileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;

public class L implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            WSPlayer wsplayer = WSPlayer.getFromPlayer((Player) sender);

            if (wsplayer == null || !wsplayer.isInGame()) {
                sender.sendMessage("§aTeleporting...");
                ((Player) sender).teleport(Main.spawn);
                return true;
            }

            wsplayer.getTimers().setMapEnd(Instant.now());
            GameUtils.endGame(wsplayer);
        }
        return true;
    }
}
