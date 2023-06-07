package com.blackoutburst.workshop.core.game;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.files.OptionsFile;

import java.util.UUID;

public class GameOptions {

    protected Boolean showNonPBs;

    protected Integer craftLimit;

    protected Integer defaultCraftLimit;

    protected Integer bagSize;

    protected Float timeLimit;

    protected Float defaultTimeLimit;

    protected Boolean unlimitedCrafts;

    protected Boolean isTimeLimited;

    protected Character randomType;

    protected Integer countDownTime;

    protected Boolean hypixelSaysMode;

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

    public GameOptions(Integer defaultCraftLimit, Boolean unlimitedCrafts, Character rngType, Integer bagSize,
                       Float defaultTimeLimit, Integer countDownTime, Boolean showNonPBs, Boolean hypixelSaysMode) {
        this.defaultCraftLimit = defaultCraftLimit;
        this.unlimitedCrafts   = unlimitedCrafts;
        this.randomType        = rngType;
        this.bagSize           = bagSize;
        this.defaultTimeLimit  = defaultTimeLimit;
        this.countDownTime     = countDownTime;
        this.showNonPBs        = showNonPBs;
        this.hypixelSaysMode   = hypixelSaysMode;
    }

    public GameOptions() {
        this.defaultCraftLimit = null;
        this.unlimitedCrafts   = null;
        this.randomType        = null;
        this.bagSize           = null;
        this.defaultTimeLimit  = null;
        this.countDownTime     = null;
        this.showNonPBs        = null;
        this.hypixelSaysMode   = null;
    }

    public void save(WSPlayer player) {
        UUID uuid = player.getPlayer().getUniqueId();
        OptionsFile.save(uuid, this);
    }

    private void load(WSPlayer player) {
        GameOptions gameOptions = OptionsFile.load(player.getPlayer().getUniqueId());
        if (gameOptions == null) return;
        load(gameOptions);
    }

    public void load (GameOptions gameOptions) {
        Integer defaultCraftLimit = gameOptions.getDefaultCraftLimit();
        Integer craftLimit        = gameOptions.getCraftLimit();
        Boolean unlimitedCrafts   = gameOptions.isUnlimitedCrafts();
        Character randomType      = gameOptions.getRandomType();
        Integer bagSize           = gameOptions.getBagSize();
        Float defaultTimeLimit    = gameOptions.getDefaultTimeLimit();
        Float timeLimit           = gameOptions.getTimeLimit();
        Integer countDownTime     = gameOptions.getCountDownTime();
        Boolean showNonPBs        = gameOptions.isShowNonPBs();
        Boolean hypixelSaysMode   = gameOptions.isHypixelSaysMode();
        Boolean isTimeLimited     = gameOptions.isTimeLimited();

        if (defaultCraftLimit != null) this.defaultCraftLimit = defaultCraftLimit;
        if (craftLimit != null)        this.craftLimit        = craftLimit;
        if (unlimitedCrafts != null)   this.unlimitedCrafts   = unlimitedCrafts;
        if (randomType != null)        this.randomType        = randomType;
        if (bagSize != null)           this.bagSize           = bagSize;
        if (defaultTimeLimit != null)  this.defaultTimeLimit  = defaultTimeLimit;
        if (timeLimit != null)         this.timeLimit         = timeLimit;
        if (countDownTime != null)     this.countDownTime     = countDownTime;
        if (showNonPBs != null)        this.showNonPBs        = showNonPBs;
        if (hypixelSaysMode != null)   this.hypixelSaysMode   = hypixelSaysMode;
        if (isTimeLimited != null)     this.isTimeLimited     = isTimeLimited;
    }

    public Integer getCraftLimit() {
        return craftLimit;
    }

    public Boolean isUnlimitedCrafts() {
        return unlimitedCrafts;
    }

    public Character getRandomType() { return randomType; }

    public void setCraftLimit(Integer craftLimit) {
        this.craftLimit = craftLimit;
    }

    public void setUnlimitedCrafts(Boolean unlimitedCrafts) {
        this.unlimitedCrafts = unlimitedCrafts;
    }

    public void setRandomType(Character randomType) { this.randomType = randomType; }

    public Integer getBagSize() { return bagSize; }

    public void setBagSize(Integer bagSize) { this.bagSize = bagSize; }

    public Boolean isTimeLimited() { return isTimeLimited; }

    public void setTimeLimited(Boolean isTimeLimited) { this.isTimeLimited = isTimeLimited; }

    public Float getTimeLimit() { return timeLimit; }

    public void setTimeLimit(Float timeLimit) { this.timeLimit = timeLimit; }

    public Integer getDefaultCraftLimit() { return defaultCraftLimit; }

    public void setDefaultCraftLimit(Integer defaultCraftLimit) { this.defaultCraftLimit = defaultCraftLimit; }

    public Float getDefaultTimeLimit() { return defaultTimeLimit; }

    public void setDefaultTimeLimit(Float defaultTimeLimit) { this.defaultTimeLimit = defaultTimeLimit; }

    public Integer getCountDownTime() { return countDownTime; }

    public void setCountDownTime(Integer countDownTime) { this.countDownTime = countDownTime; }

    public Boolean isShowNonPBs() { return showNonPBs; }

    public void setShowNonPBs(Boolean showNonPBs) { this.showNonPBs = showNonPBs; }

    public Boolean isHypixelSaysMode() { return hypixelSaysMode; }

    public void setHypixelSaysMode(Boolean hypixelSaysMode) { this.hypixelSaysMode = hypixelSaysMode; }
}
