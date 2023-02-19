package com.blackoutburst.workshop.core;

public class GameOptions {

    protected int craftLimit;

    protected int defaultCraftLimit;

    protected int bagSize;

    protected float timeLimit;

    protected float defaultTimeLimit;

    protected boolean unlimitedCrafts;

    protected boolean isTimeLimited;

    protected char randomType;

    public GameOptions(WSPlayer player) {
        this.defaultCraftLimit = 5;
        this.unlimitedCrafts = false;
        this.randomType = 'N';
        this.bagSize = 5;
        this.isTimeLimited = false;
        this.defaultTimeLimit = 60.0f;
        load(player);
    }

    // TODO
    public void save(WSPlayer player) {}

    // TODO
    private void load(WSPlayer player) {}

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
}
