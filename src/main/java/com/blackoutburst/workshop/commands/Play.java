package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.GameUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Play implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            WSPlayer wsplayer = WSPlayer.getFromPlayer((Player) sender);
            if (wsplayer == null || wsplayer.isInGame()) return true;

            wsplayer.getPlayer().sendMessage("Game running");
            wsplayer.setInGame(true);

            for (PlayArea area : Main.playAreas) {
                if (area.isBusy()) continue;
                area.setBusy(true);
                wsplayer.setPlayArea(area);
                GameUtils.loadCraft(wsplayer, area.getType());
                GameUtils.loadMaterials(wsplayer, area.getType());
                GameUtils.startRound(wsplayer);
                return true;
            }
            wsplayer.getPlayer().sendMessage("No game available");
        }

        return true;
    }
}
