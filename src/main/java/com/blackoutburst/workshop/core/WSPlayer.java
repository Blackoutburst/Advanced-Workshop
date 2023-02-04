package com.blackoutburst.workshop.core;

import com.blackoutburst.workshop.Craft;
import com.blackoutburst.workshop.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WSPlayer {

    protected Player player;

    protected boolean inGame = false;
    protected List<BrokenBlock> brokenBlocks = new ArrayList<>();
    protected Craft currentCraft = null;

    public WSPlayer(Player player) {
        this.player = player;
    }

    public static WSPlayer getFromPlayer(Player p) {
        for (WSPlayer qp : Main.players) {
            if (qp.player.getUniqueId().equals(p.getUniqueId())) {
                return (qp);
            }
        }
        return (null);
    }

    public List<BrokenBlock> getBrokenBlocks() {
        return brokenBlocks;
    }

    public Craft getCurrentCraft() {
        return currentCraft;
    }

    public boolean isInGame() {
        return inGame;
    }

    public Player getPlayer() {
        return player;
    }

    public void setCurrentCraft(Craft currentCraft) {
        this.currentCraft = currentCraft;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}
