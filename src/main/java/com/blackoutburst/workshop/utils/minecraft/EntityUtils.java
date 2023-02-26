package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.nms.NMSEntity;
import com.blackoutburst.workshop.nms.NMSEntityType;
import com.blackoutburst.workshop.nms.NMSEnumDirection;
import com.blackoutburst.workshop.nms.NMSItemFrame;
import org.bukkit.Location;
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

    public static void spawnEntity(NMSEntityType type, WSPlayer wsPlayer, Location location, BlockFace direction,
                                   String EntityName, PlayArea area) {
        location.add(area.getAnchor()).add(0.5, 0, 0.5);
        float yaw = 0;
        switch (direction) {
            case NORTH: yaw = 180; break;
            case EAST: yaw = -90; break;
            case WEST: yaw = 90; break;
            default: yaw = 0; break;
        };
        location.setYaw(yaw);
        location.setPitch(0);

        if (type.equals(NMSEntityType.ITEM_FRAME)) {
            NMSItemFrame itemFrame = new NMSItemFrame(wsPlayer.getPlayer().getWorld());
            NMSEnumDirection.Direction.valueOf(String.valueOf(direction));
            itemFrame.setDirection(NMSEnumDirection.Direction.NORTH);
            itemFrame.setPosition(location);
            itemFrame.setTag(EntityName);
            itemFrame.spawn();
        } else {
            NMSEntity entity = new NMSEntity(wsPlayer.getPlayer().getWorld(), type);
            entity.setLocation(location);
            entity.setTag(EntityName);
            entity.spawn();
        }
    }
}
