package com.blackoutburst.workshop.utils.misc;

import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.game.EndGameLogic;
import com.blackoutburst.workshop.core.game.GameOptions;
import com.blackoutburst.workshop.core.WSPlayer;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MiscUtils {

    public static void teleportPlayerToArea(Player player, Location location, BlockFace direction, PlayArea area) {
        int yaw = 0;
        switch (direction) {
            case NORTH: yaw = 180; break;
            case EAST: yaw = -90; break;
            case WEST: yaw = 90; break;
            default: yaw = 0; break;
        }
        location.add(area.getAnchor()).add(0.5,0,0.5);
        location.setYaw(yaw);
        location.setPitch(0);
        player.teleport(location);
    }

    public static <E> List<List<E>> transpose2dList(List<List<E>> startList) {
        List<List<E>> transposedList = new ArrayList<>();

        int max_size = 0;

        for (List<E> subList : startList) {
            if (subList.size() > max_size) { max_size =  subList.size(); }
        }

        for (int i = 0; i < max_size; i++) {
            List<E> tempList = new ArrayList<>();

            for (List<E> subList : startList) {
                if (subList.size() > i) {
                    tempList.add(subList.get(i));
                }
            }
            transposedList.add(tempList);
        }
        return transposedList;
    }

    public static boolean checkTimeLimit(WSPlayer wsplayer) {
        if (wsplayer.getTimers().getMapBegin() == null) return false;

        GameOptions gameoptions = wsplayer.getGameOptions();

        if ((Duration.between(wsplayer.getTimers().getMapBegin(), Instant.now()).toMillis() / 1000.0f) > gameoptions.getTimeLimit()) {
            int timeLimit = (int) gameoptions.getTimeLimit();
            String timeType;

            switch (timeLimit) {
                case 60: timeType = ".1mCrafts"; break;
                case 90: timeType = ".90sCrafts"; break;
                case 120: timeType = ".2mCrafts"; break;
                case 300: timeType = ".5mCrafts"; break;
                default: {
                    EndGameLogic.endGame(wsplayer);
                    return true;
                }
            }

            EndGameLogic.endGame(wsplayer, timeType);
            return true;
        }
        return false;
    }

    public static String getItemName(Material item) {
        NBTCompound itemNBT = NBTItem.convertItemtoNBT(new ItemStack(item));
        String name = itemNBT.getString("id");

        if (name.length() < 10) return "";
        String filteredName = name.substring(10).replace("_", " ");
        filteredName = StringUtils.capitalize(filteredName);

        return filteredName;
    }
}
