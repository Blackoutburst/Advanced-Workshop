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
            if (entity.getOwner().getPlayer().getUniqueId().equals(wsplayer.getPlayer().getUniqueId())) {
                entity.delete();
                size--;
                i--;
            }
        }
    }

    public static void spawnEntity(NMSEntityType type, WSPlayer wsPlayer, Location location, BlockFace direction, String EntityName, PlayArea area) {
        double x = location.getBlockX() + area.getAnchor().getBlockX() + 0.5;
        double y = location.getBlockY() + area.getAnchor().getBlockY();
        double z = location.getBlockZ() + area.getAnchor().getBlockZ() + 0.5;
        float yaw = 0;

        switch (direction) {
            case NORTH: yaw = 180; break;
            case EAST: yaw = -90; break;
            case WEST: yaw = 90; break;
        }

        if (type.equals(NMSEntityType.ITEM_FRAME)) {
            NMSItemFrame itemFrame = new NMSItemFrame(wsPlayer.getPlayer().getWorld());
            itemFrame.setOwner(wsPlayer);
            itemFrame.setDirection(NMSEnumDirection.Direction.valueOf(String.valueOf(direction)));
            itemFrame.setPosition(x, y, z);
            itemFrame.setTag(EntityName);
            itemFrame.spawn();
        } else {
            NMSEntity entity = new NMSEntity(wsPlayer.getPlayer().getWorld(), type);
            entity.setOwner(wsPlayer);
            entity.setLocation(x, y, z, yaw, 0);
            entity.setTag(EntityName);
            entity.spawn();
        }
    }
}
