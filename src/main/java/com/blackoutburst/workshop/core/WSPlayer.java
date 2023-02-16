package com.blackoutburst.workshop.core;

import com.blackout.npcapi.core.NPC;
import com.blackoutburst.workshop.Craft;
import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.nms.NMSBoard;
import com.blackoutburst.workshop.nms.NMSEntities;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WSPlayer {

    protected boolean waiting;

    protected List<Craft> craftList;

    protected boolean nextRound;

    protected Player player;

    protected String inventoryType;

    protected NMSEntities[] itemFrames = new NMSEntities[10];

    protected List<NPC> npcs = new ArrayList<>();

    protected Location scanWand1;
    protected Location scanWand2;

    protected PlayArea playArea;

    protected List<Craft> crafts = new ArrayList<>();

    protected List<MaterialBlock> materialBlocks = new ArrayList<>();

    protected List<DecoBlock> decoBlocks = new ArrayList<>();

    protected List<NeededBlock> neededBlocks = new ArrayList<>();

    protected Timers timers = new Timers();

    protected boolean inGame = false;
    protected Craft currentCraft = null;

    protected int currentCraftIndex = 0;

    protected NMSBoard board;

    protected GameOptions gameOptions;

    public WSPlayer(Player player) {
        this.player = player;
        this.board = new NMSBoard(player, "§6Workshop");
        board.set(player,15, "§e§m--------------------");
        board.set(player, 14, "Map: §enone");
        board.set(player, 12, "Game Time: §b0.00s");
        board.set(player, 11, " ");
        board.set(player, 10, "Craft: §enone");
        board.set(player, 9, "Craft Time: §b0.00s");
        board.set(player, 8, "    ");
        board.set(player, 7, "Round: §enone");
        board.set(player,6, "§e§m-------------------- ");

        gameOptions = new GameOptions(this);
    }

    public static WSPlayer getFromPlayer(Player p) {
        int size = Main.players.size();

        for (int i = 0; i < size; i++) {
            WSPlayer qp = Main.players.get(i);
            if (qp == null) break;
            if (qp.player.getUniqueId().equals(p.getUniqueId())) {
                return (qp);
            }
        }
        return (null);
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

    public Location getScanWand1() {
        return scanWand1;
    }

    public Location getScanWand2() {
        return scanWand2;
    }

    public void setScanWand1(Location scanWand1) {
        this.scanWand1 = scanWand1;
    }

    public void setScanWand2(Location scanWand2) {
        this.scanWand2 = scanWand2;
    }

    public List<Craft> getCrafts() {
        return crafts;
    }

    public PlayArea getPlayArea() {
        return playArea;
    }

    public void setPlayArea(PlayArea playArea) {
        this.playArea = playArea;
    }

    public List<MaterialBlock> getMaterialBlocks() {
        return materialBlocks;
    }
    public List<DecoBlock> getDecoBlocks() {
        return decoBlocks;
    }
    public List<NeededBlock> getNeededBlocks() {
        return neededBlocks;
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    public boolean isNextRound() {
        return nextRound;
    }

    public void setNextRound(boolean nextRound) {
        this.nextRound = nextRound;
    }

    public NMSEntities[] getItemFrames() {
        return itemFrames;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public NMSBoard getBoard() {
        return board;
    }

    public GameOptions getGameOptions() {
        return gameOptions;
    }

    public int getCurrentCraftIndex() {
        return currentCraftIndex;
    }

    public void setCurrentCraftIndex(int currentCraftIndex) {
        this.currentCraftIndex = currentCraftIndex;
    }

    public boolean isWaiting() {return waiting;}

    public void setWaiting(boolean waiting) { this.waiting = waiting; }

    public List<Craft> getCraftList() { return craftList; }

    public void setCraftList(List<Craft> craftList) { this.craftList = craftList; }

    public Timers getTimers() {
        return timers;
    }
}
