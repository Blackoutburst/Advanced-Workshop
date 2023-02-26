package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

public class NMSEntityUseEvent {

    public enum Action {
        LEFT_CLICK("ATTACK"),
        RIGHT_CLICK("INTERACT");

        private final String nmsName;

        Action(String nmsName) {
            this.nmsName = nmsName;
        }

        public static Action getFromNMSName(String nmsName) {
            for (Action packet : Action.values()) {
                if (packet.nmsName.equals(nmsName))
                    return packet;
            }
            return null;
        }
    }

    public enum Hand {
        MAIN_HAND,
        OFF_HAND
    }

    protected Player player;
    protected NMSEntity entity;
    protected Action action;
    protected Hand hand;

    public NMSEntityUseEvent(Player player, NMSEntity entity, Action action, Hand hand) {
        this.player = player;
        this.entity = entity;
        this.action = action;
        this.hand = hand;
    }

    public Player getPlayer() {
        return player;
    }

    public NMSEntity getEntity() {
        return entity;
    }

    public Action getAction() {
        return action;
    }

    public Hand getHand() {
        return hand;
    }
}
