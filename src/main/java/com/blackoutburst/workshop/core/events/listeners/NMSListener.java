package com.blackoutburst.workshop.core.events.listeners;

import com.blackoutburst.workshop.core.events.nms.EntityUse;
import com.blackoutburst.workshop.nms.NMSEntityUseEvent;
import com.blackoutburst.workshop.nms.NMSPacket;

public class NMSListener implements NMSPacket {

    @Override
    public void onEntityUse(NMSEntityUseEvent event) {
        EntityUse.execute(event);
    }

}
