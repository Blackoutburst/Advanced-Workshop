package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

public class NMSBoard {

    private final NMSScoreboard scoreboard;

    private final Player player;

    public NMSBoard(Player player, String title) {
        this.player = player;

        scoreboard = new NMSScoreboard();
        scoreboard.registerObjective(player.getName(), NMSScoreboard.EnumScoreboardHealthDisplay.INTEGER);
        scoreboard.setDisplaySlot(NMSScoreboard.DisplaySlot.SIDEBAR);
        scoreboard.setDisplayName(title);

        NMSScoreboardObjective.send(player, scoreboard, NMSScoreboardObjective.ObjectiveOption.CREATE);
        NMSScoreboardDisplayObjective.send(player, scoreboard);
    }

    public void setTitle(Player player, String title) {
        scoreboard.setDisplayName(title);
        NMSScoreboardObjective.send(player, scoreboard, NMSScoreboardObjective.ObjectiveOption.EDIT);
    }

    public void set(int line, String text) {
        removeLine(line);

        NMSScoreboardScore score = new NMSScoreboardScore(scoreboard, text, line);
        NMSPacketPlayOutScoreboardScore.send(player, score);
        scoreboard.getLines().add(score);
    }

    public void removeLine(int line) {
        for (int i = 0; i < scoreboard.getLines().size(); i++) {
            NMSScoreboardScore s = scoreboard.getLines().get(i);

            if (s.getScore() == line) {
                NMSPacketPlayOutScoreboardScore.remove(player, s);
                scoreboard.getLines().remove(s);
                break;
            }
        }
    }

    public void clear() {
        for (NMSScoreboardScore s : scoreboard.getLines())
            NMSPacketPlayOutScoreboardScore.remove(player, s);

        scoreboard.getLines().clear();
    }
}
