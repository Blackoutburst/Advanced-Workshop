package com.blackoutburst.workshop.core;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.game.GameOptions;
import com.blackoutburst.workshop.core.game.RoundLogic;
import com.blackoutburst.workshop.utils.minecraft.ScoreboardUtils;
import com.blackoutburst.workshop.utils.misc.MiscUtils;
import com.blackoutburst.workshop.utils.misc.StringUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.Instant;

public class Updater {

    private static final int ROUND_DELAY = 1;

    private static void nextRound(WSPlayer wsPlayer) {
        new BukkitRunnable() {
            @Override
            public void run() {
                RoundLogic.startRound(wsPlayer);
            }
        }.runTaskLater(Main.getPlugin(Main.class), ROUND_DELAY * 20L);
    }

    private static void updateTimes(WSPlayer wsPlayer) {
        GameOptions gameoptions = wsPlayer.getGameOptions();
        Timers timer = wsPlayer.getTimers();
        String gameDuration = StringUtils.ROUND.format(((float) Duration.between(timer.getMapBegin(), Instant.now()).toMillis() / 1000.0f)) + "s";
        String gameTime = (timer.getMapBegin() == null) ? "0.00s" : gameDuration;

        if (wsPlayer.isNextRound()) {
            ScoreboardUtils.waiting(wsPlayer);
            return;
        }

        String roundTime = "0.00s";

        if (timer.getRoundBegin() != null) {
            String completeTime = StringUtils.ROUND.format(((float) Duration.between(wsPlayer.getTimers().getRoundBegin(), wsPlayer.getTimers().getRoundEnd()).toMillis() / 1000.0f)) + "s";;
            String currentTime = StringUtils.ROUND.format(((float) Duration.between(wsPlayer.getTimers().getRoundBegin(), Instant.now()).toMillis() / 1000.0f)) + "s";

            roundTime = wsPlayer.isNextRound() ? completeTime : currentTime;
        }

        String remainingTime = "§eN/A";
        if (gameoptions.isTimeLimited()) {
            float time = Duration.between(wsPlayer.getTimers().getMapBegin(), Instant.now()).toMillis() / 1000.0f;
            remainingTime = "§b" + StringUtils.ROUND.format(gameoptions.getTimeLimit() - time);
        }

        ScoreboardUtils.game(wsPlayer, gameTime, roundTime, remainingTime);
    }

    public static void update() {
        int size = Main.players.size();

        for (int i = 0; i < size; i++) {
            WSPlayer wsPlayer = Main.players.get(i);

            if (wsPlayer == null) continue;
            if (!wsPlayer.isInGame()) continue;

            GameOptions gameoptions = wsPlayer.getGameOptions();

            if (gameoptions.isTimeLimited() && MiscUtils.checkTimeLimit(wsPlayer)) continue;
            if (wsPlayer.isWaiting()) continue;

            updateTimes(wsPlayer);

            if (wsPlayer.isNextRound()) {
                wsPlayer.setNextRound(false);

                boolean finished = RoundLogic.prepareNextRound(wsPlayer);
                if (finished) continue;

                nextRound(wsPlayer);
            }
        }
    }

}