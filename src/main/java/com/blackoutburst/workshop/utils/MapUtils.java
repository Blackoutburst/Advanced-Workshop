package com.blackoutburst.workshop.utils;

import com.blackoutburst.workshop.core.BrokenBlock;
import com.blackoutburst.workshop.core.WSPlayer;

public class MapUtils {

    public static void restoreArea(WSPlayer wsPlayer) {
        for (BrokenBlock b : wsPlayer.getBrokenBlocks()) {
            b.getWorld().getBlockAt(b.getLocation()).setType(b.getType());
            b.getWorld().getBlockAt(b.getLocation()).setData(b.getData());
        }
    }

}
