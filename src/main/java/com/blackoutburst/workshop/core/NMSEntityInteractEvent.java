package com.blackoutburst.workshop.core;

import com.blackoutburst.workshop.nms.NMSEntities;
import org.bukkit.entity.Player;

public class NMSEntityInteractEvent {

    private final Player player;
    private final Action action;
    private final NMSEntities entity;

    public NMSEntityInteractEvent(Player player, Action action, NMSEntities entity) {
        this.player = player;
        this.action = action;
        this.entity = entity;
    }

    public Player getPlayer() {
        return player;
    }

    public Action getAction() {
        return action;
    }

    public NMSEntities getEntity() {
        return entity;
    }
}
