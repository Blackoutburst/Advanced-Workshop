package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.nms.NMSEntity;
import com.blackoutburst.workshop.nms.NMSEntityType;
import com.blackoutburst.workshop.nms.NMSEnumDirection;
import com.blackoutburst.workshop.nms.NMSItemFrame;
import org.bukkit.block.BlockFace;

public class EntityUtils {

    public static void clearEntity(WSPlayer wsplayer) {
        int size = wsplayer.getEntities().size();
        for (int i = 0; i < size; i++) {
            NMSEntity entity = wsplayer.getEntities().get(i);
            entity.delete();
            size--;
            i--;
        }
    }

    public static void spawnEntity(NMSEntityType type, WSPlayer wsPlayer, String[] data, PlayArea area) {
        String tag = data[0];

        float x = Integer.parseInt(data[2]) + area.getAnchor().getBlockX() + 0.5f;
        int y = Integer.parseInt(data[3]) + area.getAnchor().getBlockY();
        float z = Integer.parseInt(data[4]) + area.getAnchor().getBlockZ() + 0.5f;
        int yaw = 0;

        BlockFace blockFace = BlockFace.valueOf(data[5]);

        switch (BlockFace.valueOf(data[5])) {
            case NORTH -> yaw = 180;
            case EAST -> yaw = -90;
            case WEST -> yaw = 90;
        }

        if (type.equals(NMSEntityType.ITEM_FRAME)) {
            NMSItemFrame itemFrame = new NMSItemFrame(wsPlayer.getPlayer().getWorld());
            NMSEnumDirection.Direction.valueOf(String.valueOf(blockFace));
            itemFrame.setDirection(NMSEnumDirection.Direction.NORTH);
            itemFrame.setPosition(x, y, z);
            itemFrame.setTag(tag);
            itemFrame.spawn();
        } else {
            NMSEntity entity = new NMSEntity(wsPlayer.getPlayer().getWorld(), type);
            entity.setLocation(x, y, z, yaw, 0);
            entity.setTag(tag);
            entity.spawn();
        }
    }
}
