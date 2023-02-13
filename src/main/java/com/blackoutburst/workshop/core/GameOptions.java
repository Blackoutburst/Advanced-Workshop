package com.blackoutburst.workshop.core;

public class GameOptions {

    protected int craftLimit;
    protected boolean unlimitedCrafts;

    public GameOptions(WSPlayer player) {
        this.craftLimit = 5;
        this.unlimitedCrafts = false;

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

    public void setCraftLimit(int craftLimit) {
        this.craftLimit = craftLimit;
    }

    public void setUnlimitedCrafts(boolean unlimitedCrafts) {
        this.unlimitedCrafts = unlimitedCrafts;
    }
}
