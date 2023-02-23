package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.game.EndGameLogic;
import com.blackoutburst.workshop.nms.NMSEntity;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;

public class L implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            WSPlayer wsplayer = WSPlayer.getFromPlayer(player);

            if (wsplayer == null || !wsplayer.isInGame()) {
                sender.sendMessage("§aTeleporting...");
                (player).teleport(Main.spawn);
                return true;
            }

            wsplayer.getTimers().setMapEnd(Instant.now());

            EndGameLogic.endGame(wsplayer);
        }
        return true;
    }
}
