package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.misc.StringUtils;

public class ScoreboardUtils {

    public static void waiting(WSPlayer wsplayer) {
        wsplayer.getBoard().set(12, "Game Time: §b0.00s");
        wsplayer.getBoard().set(9, "Craft Time: §b0.00s");
    }

    public static void game(WSPlayer wsplayer, String gameTime, String roundTime, String remainingTime) {
        wsplayer.getBoard().set(12, "Game Time: §b" + gameTime);
        wsplayer.getBoard().set(9, "Craft Time: §b" + roundTime);
        wsplayer.getBoard().set(6, "Remaining Time: " + remainingTime);
    }

    public static void startRound(WSPlayer wsplayer) {
        wsplayer.getBoard().set(10, "Craft: §e" + wsplayer.getCurrentCraft().getName());
        wsplayer.getBoard().set(7, "Round: §e" + StringUtils.getCurrentRound(wsplayer));
    }

    public static void endGame(WSPlayer wsplayer) {
        wsplayer.getBoard().set(14, "Map: §enone");
        wsplayer.getBoard().set(10, "Craft: §enone");
        wsplayer.getBoard().set(7, "Round: §enone");
        wsplayer.getBoard().set(6, "Remaining Time: §eN/A");
    }

}
