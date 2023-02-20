package com.blackoutburst.workshop.core;

import com.blackout.npcapi.core.NPC;
import com.blackoutburst.workshop.nms.NMSEntities;
import org.bukkit.entity.Player;

public class NMSNPCInteractEvent {

    private final Player player;
    private final Action action;
    private final NPC npc;

    public NMSNPCInteractEvent(Player player, Action action, NPC npc) {
        this.player = player;
        this.action = action;
        this.npc = npc;
    }

    public Player getPlayer() {
        return player;
    }

    public Action getAction() {
        return action;
    }

    public NPC getNpc() {
        return npc;
    }
}
