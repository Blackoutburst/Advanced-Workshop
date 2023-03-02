package com.blackoutburst.workshop.core;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class PlayArea {

    protected boolean isBusy;

    protected List<Entity> entities;

    protected boolean isLoading;

    protected boolean hasStarted;

    protected String type;
    protected Location anchor;

    public PlayArea(String type, Location anchor) {
        this.type = type;
        this.anchor = anchor;
        this.entities = new ArrayList<>();
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

    public List<Entity> getEntities() {
        return entities;
    }
}
