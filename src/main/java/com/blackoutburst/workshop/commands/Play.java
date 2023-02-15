package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.CountdownDisplay;
import com.blackoutburst.workshop.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Play implements CommandExecutor {

    private void setCraftAmount(WSPlayer wsplayer, String value) {
        try {
            int limit = Integer.parseInt(value);

            if (limit <= 0) {
                wsplayer.getGameOptions().setUnlimitedCrafts(true);
            } else {
                wsplayer.getGameOptions().setUnlimitedCrafts(false);
                wsplayer.getGameOptions().setCraftLimit(limit);
            }
        } catch (Exception ignored) {
            wsplayer.getPlayer().sendMessage("§cInvalid craft amount provided, using your current settings.");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            WSPlayer wsplayer = WSPlayer.getFromPlayer((Player) sender);
            if (wsplayer == null || wsplayer.isInGame()) return true;

            wsplayer.getPlayer().sendMessage("Game running");

            for (PlayArea area : Main.playAreas) {
                if (area.isBusy()) continue;
                if (args.length > 0 && !args[0].equals(area.getType())) continue;
                area.setBusy(true);
                wsplayer.setPlayArea(area);
                wsplayer.setCurrentCraftIndex(0);
                GameUtils.loadCraft(wsplayer, area.getType());
                GameUtils.loadMaterials(wsplayer, area.getType());
                GameUtils.spawnEntities(wsplayer, area.getType());
                wsplayer.setInGame(true);
                wsplayer.getPlayer().setGameMode(GameMode.SURVIVAL);
                wsplayer.getBoard().set(wsplayer.getPlayer(), 13, "Map: §e" + area.getType());
                if (args.length > 1)
                    setCraftAmount(wsplayer, args[1]);
                GameUtils.fillCraftList(wsplayer);

                int start_delay = 5;

                BukkitRunnable displayCountdown = new CountdownDisplay(start_delay, wsplayer.getPlayer());

                displayCountdown.runTaskTimer(Main.getPlugin(Main.class),0,20);

                GameUtils.prepareNextRound(wsplayer);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        GameUtils.startRound(wsplayer);
                    }
                }.runTaskLater(Main.getPlugin(Main.class), start_delay * 20);
                return true;
            }
            wsplayer.getPlayer().sendMessage("No game available");
        }

        return true;
    }
}
