package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.game.GameOptions;
import com.blackoutburst.workshop.core.game.GameRestarter;
import com.blackoutburst.workshop.core.game.GameStarter;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.game.RoundLogic;
import com.blackoutburst.workshop.utils.map.DecoBlockLoader;
import com.blackoutburst.workshop.utils.files.DBUtils;
import com.blackoutburst.workshop.utils.map.MapUtils;
import com.blackoutburst.workshop.utils.minecraft.CraftUtils;
import com.blackoutburst.workshop.utils.misc.CountdownDisplay;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.Instant;

public class Play implements CommandExecutor {

    private void setCraftAmount(WSPlayer wsplayer, String value) {
        if (!value.matches("[0-9]+")) {
            wsplayer.getPlayer().sendMessage("§cInvalid craft amount provided, using your current settings.");
            return;
        }

        int limit = Integer.parseInt(value);
        if (limit <= 0) {
            wsplayer.getGameOptions().setUnlimitedCrafts(true);
            return;
        }

        wsplayer.getGameOptions().setUnlimitedCrafts(false);
        wsplayer.getGameOptions().setCraftLimit(limit);
    }

    private void setTimeLimit(WSPlayer wsplayer, String value) {
        GameOptions gameoptions = wsplayer.getGameOptions();

        if (!value.matches("([0-9]+([.][0-9]+)?)?")) {
            wsplayer.getPlayer().sendMessage("§cInvalid time limit provided, using your current settings.");
            gameoptions.setTimeLimited(true);
            gameoptions.setUnlimitedCrafts(true);
            return;
        }

        if (value.equals("")) {
            gameoptions.setTimeLimited(true);
            gameoptions.setTimeLimit(gameoptions.getDefaultTimeLimit());
            gameoptions.setUnlimitedCrafts(true);
            return;
        }

        float limit = Float.parseFloat(value);
        gameoptions.setTimeLimited(true);
        gameoptions.setTimeLimit(limit);
        gameoptions.setUnlimitedCrafts(true);

    }

    private void initPlayer(WSPlayer wsplayer, PlayArea area) {
        wsplayer.getPlayer().sendMessage("§eThe game is about to start!");
        wsplayer.setPlayArea(area);
        wsplayer.setCurrentCraftIndex(0);
        wsplayer.setWaiting(true);
        wsplayer.setInGame(true);
        wsplayer.setNextRound(false);
        wsplayer.getPlayer().setCanPickupItems(false);
        wsplayer.getPlayer().setGameMode(GameMode.SURVIVAL);
        wsplayer.getBoard().set(14, "Map: §e" + area.getType());

        CraftUtils.loadCraft(wsplayer, area.getType());
        MapUtils.loadMaterials(wsplayer, area.getType());
        MapUtils.readSigns(wsplayer, area.getType());
    }

    private void setGameLimits(WSPlayer wsplayer, GameOptions gameoptions, String[] args) {
        if (args.length < 2) {
            gameoptions.setCraftLimit(gameoptions.getDefaultCraftLimit());
            return;
        }

        switch (args[1]) {
            case "time": {
                if (args.length == 2) setTimeLimit(wsplayer, "");
                if (args.length > 2) setTimeLimit(wsplayer, args[2]);
            } break;
            case "all": gameoptions.setCraftLimit(wsplayer.getCrafts().size()); break;
            default: setCraftAmount(wsplayer, args[1]);
        }
    }

    private void setBagSize(WSPlayer wsplayer, GameOptions gameoptions) {
        switch (gameoptions.getRandomType()) {
            case 'N': gameoptions.setBagSize(wsplayer.getCrafts().size()); break;
            case 'R': gameoptions.setBagSize(10); break;
        }
    }

    private void startGame(WSPlayer wsplayer, GameOptions gameoptions, PlayArea area) {
        int start_delay = gameoptions.getCountDownTime();

        BukkitRunnable displayCountdown = new CountdownDisplay(start_delay, wsplayer);

        displayCountdown.runTaskTimer(Main.getPlugin(Main.class),0,20);

        RoundLogic.prepareNextRound(wsplayer);

        wsplayer.getPlayArea().setLoading(true);
        DecoBlockLoader decoBlockLoader = new DecoBlockLoader(wsplayer);
        decoBlockLoader.runTaskAsynchronously(Main.getPlugin(Main.class));

        Integer gameCount = DBUtils.getData(wsplayer.getPlayer(), "gameCount", Integer.class);
        Integer mapGameCount = DBUtils.getData(wsplayer.getPlayer(), area.getType() + ".gameCount", Integer.class);

        GameStarter starter = new GameStarter(wsplayer, area, gameCount, mapGameCount);
        GameRestarter restarter = new GameRestarter(wsplayer, starter);
        wsplayer.getPlayArea().setHasStarted(false);
        wsplayer.setGamestarter(starter);
        wsplayer.setGameRestarter(restarter);

        starter.runTaskLater(Main.getPlugin(Main.class), start_delay * 20L);
        restarter.runTaskTimer(Main.getPlugin(Main.class), start_delay * 20L + 1, 1);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            WSPlayer wsplayer = WSPlayer.getFromPlayer(player);
            if (wsplayer == null || wsplayer.isInGame()) return true;

            GameOptions gameoptions = wsplayer.getGameOptions();

            for (PlayArea area : Main.playAreas) {
                if (area.isBusy()) continue;
                if (args.length > 0 && !args[0].equals(area.getType())) continue;
                area.setBusy(true);

                initPlayer(wsplayer, area);
                setGameLimits(wsplayer, gameoptions, args);
                setBagSize(wsplayer, gameoptions);

                CraftUtils.updateCraftList(wsplayer);

                startGame(wsplayer, gameoptions, area);
                return true;
            }

            player.sendMessage("No game available");
        }

        return true;
    }
}
