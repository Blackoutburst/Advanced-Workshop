package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;

public record NMSEntityUseEvent(Player player, NMSEntity entity, NMSEntityUseEvent.Action action, NMSEntityUseEvent.Hand hand) {

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

}
