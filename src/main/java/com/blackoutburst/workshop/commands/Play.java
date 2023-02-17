package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.Craft;
import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.GameOptions;
import com.blackoutburst.workshop.core.GameStarter;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.CountdownDisplay;
import com.blackoutburst.workshop.utils.DBUtils;
import com.blackoutburst.workshop.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Instant;

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

            for (PlayArea area : Main.playAreas) {
                if (area.isBusy()) continue;
                if (args.length > 0 && !args[0].equals(area.getType())) continue;
                area.setBusy(true);
                wsplayer.getPlayer().sendMessage("§eThe game is about to start!");
                wsplayer.setPlayArea(area);
                wsplayer.setCurrentCraftIndex(0);
                GameUtils.loadCraft(wsplayer, area.getType());
                GameUtils.loadMaterials(wsplayer, area.getType());
                GameUtils.spawnEntities(wsplayer, area.getType());
                wsplayer.setWaiting(true);
                wsplayer.setInGame(true);
                wsplayer.setNextRound(false);
                wsplayer.getPlayer().setGameMode(GameMode.SURVIVAL);

                wsplayer.getBoard().set(wsplayer.getPlayer(), 14, "Map: §e" + area.getType());
                if (args.length > 1)
                    setCraftAmount(wsplayer, args[1]);
                GameOptions gameoptions = wsplayer.getGameOptions();

                if (gameoptions.getRandomType() == 'N') {
                    gameoptions.setBagSize(wsplayer.getCrafts().size());
                }
                if (gameoptions.getRandomType() == 'R') {
                    gameoptions.setBagSize(10);
                }

                GameUtils.updateCraftList(wsplayer);

                int start_delay = 5;

                BukkitRunnable displayCountdown = new CountdownDisplay(start_delay, wsplayer);

                displayCountdown.runTaskTimer(Main.getPlugin(Main.class),0,20);

                GameUtils.prepareNextRound(wsplayer);

                Integer gameCount = DBUtils.getData(wsplayer.getPlayer(), "gameCount", Integer.class);
                Integer mapGameCount = DBUtils.getData(wsplayer.getPlayer(), area.getType() + ".gameCount", Integer.class);

                GameStarter startGame = new GameStarter(wsplayer, area, gameCount, mapGameCount);
                wsplayer.setGamestarter(startGame);

                startGame.runTaskLater(Main.getPlugin(Main.class), start_delay * 20);
                return true;
            }
            wsplayer.getPlayer().sendMessage("No game available");
        }

        return true;
    }
}
