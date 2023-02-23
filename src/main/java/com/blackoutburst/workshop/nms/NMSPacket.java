package com.blackoutburst.workshop.nms;

public interface NMSPacket {

    default void onEntityUse(NMSEntityUseEvent event) {
    }

}
