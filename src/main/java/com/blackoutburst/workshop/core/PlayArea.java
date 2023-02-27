package com.blackoutburst.workshop.core;

import org.bukkit.Location;

public class PlayArea {

    protected boolean isBusy;

    protected boolean isLoading;

    protected boolean hasStarted;

    protected String type;
    protected Location anchor;

    public PlayArea(String type, Location anchor) {
        this.type = type;
        this.anchor = anchor;
    }

    public String getType() {
        return type;
    }

    public Location getAnchor() {
        return anchor;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public boolean isLoading() { return isLoading; }

    public void setLoading(boolean loading) { isLoading = loading; }

    public boolean hasStarted() { return hasStarted; }

    public void setHasStarted(boolean hasStarted) { this.hasStarted = hasStarted; }
}
