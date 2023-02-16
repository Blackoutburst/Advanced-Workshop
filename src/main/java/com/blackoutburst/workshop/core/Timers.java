package com.blackoutburst.workshop.core;

import java.time.Instant;

public class Timers {

    protected Instant mapBegin;
    protected Instant mapEnd;
    protected Instant roundBegin;
    protected Instant roundEnd;

    public Instant getMapBegin() {
        return mapBegin;
    }

    public Instant getMapEnd() {
        return mapEnd;
    }

    public Instant getRoundBegin() {
        return roundBegin;
    }

    public Instant getRoundEnd() {
        return roundEnd;
    }

    public void setMapBegin(Instant mapBegin) {
        this.mapBegin = mapBegin;
    }

    public void setMapEnd(Instant mapEnd) {
        this.mapEnd = mapEnd;
    }

    public void setRoundBegin(Instant roundBegin) {
        this.roundBegin = roundBegin;
    }

    public void setRoundEnd(Instant roundEnd) {
        this.roundEnd = roundEnd;
    }
}
