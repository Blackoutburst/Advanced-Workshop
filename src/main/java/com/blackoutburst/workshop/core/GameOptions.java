package com.blackoutburst.workshop.core;

public class GameOptions {

    protected int craftLimit;

    protected int bagSize;

    protected float timeLimit;

    protected boolean unlimitedCrafts;

    protected boolean isTimeLimited;

    protected char randomType;

    public GameOptions(WSPlayer player) {
        this.craftLimit = 5;
        this.unlimitedCrafts = false;
        this.randomType = 'N';
        this.bagSize = 5;
        this.isTimeLimited = false;
        this.timeLimit = 60.0f;
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
}
