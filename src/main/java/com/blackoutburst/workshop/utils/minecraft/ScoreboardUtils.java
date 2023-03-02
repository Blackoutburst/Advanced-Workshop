package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.misc.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardUtils {

    private static void setStaticLine(Objective objective, int score, String line) {
        objective.getScore(line).setScore(score);
    }

    private static void setDynamicLine(Scoreboard scoreboard, Objective objective, int score, String staticText, String dynamicText) {
        if (scoreboard.getTeam(staticText) != null) {
            Team team = scoreboard.getTeam(staticText);
            team.setSuffix(dynamicText);

            objective.getScore(staticText).setScore(score);
        } else {
            Team team = scoreboard.registerNewTeam(staticText);
            team.addEntry(staticText);
            team.setSuffix(dynamicText);

            objective.getScore(staticText).setScore(score);
        }
    }

    public static void init(WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();
        Scoreboard scoreboard = wsplayer.getScoreBoard();
        Objective objective = scoreboard.registerNewObjective(player.getName(), Criteria.DUMMY, "§6Workshop");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        basic(wsplayer);
    }

    public static void basic(WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();
        Scoreboard scoreboard = wsplayer.getScoreBoard();
        Objective objective = scoreboard.getObjective(player.getName());
        if (objective == null) return;

        setStaticLine(objective, 15, "§e§m--------------------");
        setDynamicLine(scoreboard, objective, 14, "Map: ", "§enone");
        setDynamicLine(scoreboard, objective, 12, "Game Time: ", "§b0.00s");
        setStaticLine(objective, 11, " ");
        setDynamicLine(scoreboard, objective, 10, "Craft: ", "§enone");
        setDynamicLine(scoreboard, objective, 9, "Craft Time: ", "§b0.00s");
        setStaticLine(objective, 8, "  ");
        setDynamicLine(scoreboard, objective, 7, "Round: ", "§enone");
        setDynamicLine(scoreboard, objective, 6, "Time remaining: ", "§bN/A");
        setStaticLine(objective, 5, "§e§m-------------------- ");
    }

    public static void waiting(WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();
        Scoreboard scoreboard = wsplayer.getScoreBoard();
        Objective objective = scoreboard.getObjective(player.getName());
        if (objective == null) return;

        setDynamicLine(scoreboard, objective, 12, "Game Time: ", "§b0.00s");
        setDynamicLine(scoreboard, objective, 9, "Craft Time: ", "§b0.00s");
    }

    public static void game(WSPlayer wsplayer, String gameTime, String roundTime, String remainingTime) {
        Player player = wsplayer.getPlayer();
        Scoreboard scoreboard = wsplayer.getScoreBoard();
        Objective objective = scoreboard.getObjective(player.getName());
        if (objective == null) return;

        setDynamicLine(scoreboard, objective, 12, "Game Time: ", "§b" + gameTime);
        setDynamicLine(scoreboard, objective, 9, "Craft Time: ", "§b" + roundTime);
        setDynamicLine(scoreboard, objective, 6, "Time remaining: ", "§b" + remainingTime);
    }

    public static void startGame(WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();
        PlayArea area = wsplayer.getPlayArea();
        Scoreboard scoreboard = wsplayer.getScoreBoard();
        Objective objective = scoreboard.getObjective(player.getName());
        if (objective == null) return;

        setDynamicLine(scoreboard, objective, 14, "Map: ", "§e" + area.getType());
    }

    public static void startRound(WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();
        Scoreboard scoreboard = wsplayer.getScoreBoard();
        Objective objective = scoreboard.getObjective(player.getName());
        if (objective == null) return;


        setDynamicLine(scoreboard, objective, 10, "Craft: ", "§e" + wsplayer.getCurrentCraft().getName());
        setDynamicLine(scoreboard, objective, 7, "Round: ", "§e" + StringUtils.getCurrentRound(wsplayer));
    }

    public static void endGame(WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();
        Scoreboard scoreboard = wsplayer.getScoreBoard();
        Objective objective = scoreboard.getObjective(player.getName());
        if (objective == null) return;

        setDynamicLine(scoreboard, objective, 14, "Map: ", "§enone");
        setDynamicLine(scoreboard, objective, 10, "Craft: ", "§enone");
        setDynamicLine(scoreboard, objective, 7, "Round: ", "§enone");
        setDynamicLine(scoreboard, objective, 6, "Time remaining: ", "§bN/A");
    }

}
