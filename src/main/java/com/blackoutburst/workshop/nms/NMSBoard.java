package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

public class NMSBoard {

    private final NMSScoreboard scoreboard;

    public NMSBoard(Player player, String title) {
        scoreboard = new NMSScoreboard();
        scoreboard.registerObjective(player.getName(), "dummy");
        scoreboard.setDisplaySlot(NMSScoreboard.DisplaySlot.SIDEBAR);
        scoreboard.setDisplayName(title);

        NMSScoreboardObjective.send(player, scoreboard, NMSScoreboardObjective.ObjectiveOption.CREATE);
        NMSScoreboardDisplayObjective.send(player, scoreboard);
    }

    public void setTitle(Player player, String title) {
        scoreboard.setDisplayName(title);
        NMSScoreboardObjective.send(player, scoreboard, NMSScoreboardObjective.ObjectiveOption.EDIT);
    }

    public void set(Player player, int line, String text) {
        removeLine(player, line);

        NMSScoreboardScore score = new NMSScoreboardScore(scoreboard, text, line);
        NMSScoreboardScorePacket.send(player, score);
        scoreboard.getLines().add(score);
    }

    public void removeLine(Player player, int line) {
        for (int i = 0; i < scoreboard.getLines().size(); i++) {
            NMSScoreboardScore s = scoreboard.getLines().get(i);

            if (s.getScore() == line) {
                NMSScoreboardScorePacket.remove(player, s);
                scoreboard.getLines().remove(s);
                break;
            }
        }
    }

    public void clear(Player player) {
        for (NMSScoreboardScore s : scoreboard.getLines())
            NMSScoreboardScorePacket.remove(player, s);

        scoreboard.getLines().clear();
    }
}
