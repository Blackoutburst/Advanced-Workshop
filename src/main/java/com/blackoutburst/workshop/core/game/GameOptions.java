package com.blackoutburst.workshop.core.game;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.files.OptionsFile;

import java.util.UUID;

public class GameOptions {

    protected boolean showNonPBs;

    protected int craftLimit;

    protected int defaultCraftLimit;

    protected int bagSize;

    protected float timeLimit;

    protected float defaultTimeLimit;

    protected boolean unlimitedCrafts;

    protected boolean isTimeLimited;

    protected char randomType;

    protected int countDownTime;

    protected boolean hypixelSaysMode;

    public GameOptions(WSPlayer player) {
        this.defaultCraftLimit = 5;
        this.unlimitedCrafts = false;
        this.randomType = 'N';
        this.bagSize = 5;
        this.isTimeLimited = false;
        this.defaultTimeLimit = 60.0f;
        this.countDownTime = 5;
        this.showNonPBs = false;
        this.hypixelSaysMode = false;
        load(player);
    }

    public GameOptions(int defaultCraftLimit, boolean unlimitedCrafts, char rngType, int bagSize,
                       float defaultTimeLimit, int countDownTime, boolean showNonPBs, boolean hypixelSaysMode) {
        this.defaultCraftLimit = defaultCraftLimit;
        this.unlimitedCrafts = unlimitedCrafts;
        this.randomType = rngType;
        this.bagSize = bagSize;
        this.defaultTimeLimit = defaultTimeLimit;
        this.countDownTime = countDownTime;
        this.showNonPBs = showNonPBs;
        this.hypixelSaysMode = hypixelSaysMode;
    }

    public void save(WSPlayer player) {
        UUID uuid = player.getPlayer().getUniqueId();
        OptionsFile.save(uuid, this);
    }

    private void load(WSPlayer player) {
        GameOptions gameOptions = OptionsFile.load(player.getPlayer().getUniqueId());
        if (gameOptions == null) return;

        this.defaultCraftLimit = gameOptions.getDefaultCraftLimit();
        this.unlimitedCrafts = gameOptions.isUnlimitedCrafts();
        this.randomType = gameOptions.getRandomType();
        this.bagSize = gameOptions.getBagSize();
        this.defaultTimeLimit = gameOptions.getDefaultTimeLimit();
        this.countDownTime = gameOptions.getCountDownTime();
        this.showNonPBs = gameOptions.isShowNonPBs();
        this.hypixelSaysMode = gameOptions.isHypixelSaysMode();
    }

    public int getCraftLimit() {
        return craftLimit;
    }

    public boolean isUnlimitedCrafts() {
        return unlimitedCrafts;
    }

    public char getRandomType() { return randomType; }

    public void setCraftLimit(int craftLimit) {
        this.craftLimit = craftLimit;
    }

    public void setUnlimitedCrafts(boolean unlimitedCrafts) {
        this.unlimitedCrafts = unlimitedCrafts;
    }

    public void setRandomType(char randomType) { this.randomType = randomType; }

    public int getBagSize() { return bagSize; }

    public void setBagSize(int bagSize) { this.bagSize = bagSize; }

    public boolean isTimeLimited() { return isTimeLimited; }

    public void setTimeLimited(boolean isTimeLimited) { this.isTimeLimited = isTimeLimited; }

    public float getTimeLimit() { return timeLimit; }

    public void setTimeLimit(float timeLimit) { this.timeLimit = timeLimit; }

    public int getDefaultCraftLimit() { return defaultCraftLimit; }

    public void setDefaultCraftLimit(int defaultCraftLimit) { this.defaultCraftLimit = defaultCraftLimit; }

    public float getDefaultTimeLimit() { return defaultTimeLimit; }

    public void setDefaultTimeLimit(float defaultTimeLimit) { this.defaultTimeLimit = defaultTimeLimit; }

    public int getCountDownTime() { return countDownTime; }

    public void setCountDownTime(int countDownTime) { this.countDownTime = countDownTime; }

    public boolean isShowNonPBs() { return showNonPBs; }

    public void setShowNonPBs(boolean showNonPBs) { this.showNonPBs = showNonPBs; }

    public boolean isHypixelSaysMode() { return hypixelSaysMode; }

    public void setHypixelSaysMode(boolean hypixelSaysMode) { this.hypixelSaysMode = hypixelSaysMode; }
}
