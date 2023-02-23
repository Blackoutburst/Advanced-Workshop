package com.blackoutburst.workshop.utils.misc;

import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.game.EndGameLogic;
import com.blackoutburst.workshop.core.game.GameOptions;
import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MiscUtils {

    public static void teleportPlayerToArea(Player player, String[] data, PlayArea area) {
        float x = Integer.parseInt(data[2]) + area.getAnchor().getBlockX() + 0.5f;
        int y = Integer.parseInt(data[3]) + area.getAnchor().getBlockY();
        float z = Integer.parseInt(data[4]) + area.getAnchor().getBlockZ() + 0.5f;
        int yaw = switch (BlockFace.valueOf(data[5])) {
            case NORTH -> 180;
            case EAST -> -90;
            case WEST -> 90;
            default -> 0;
        };

        player.teleport(new Location(player.getWorld(), x, y, z, yaw, 0));
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
                case 60 -> timeType = ".1mCrafts";
                case 90 -> timeType = ".90sCrafts";
                case 120 -> timeType = ".2mCrafts";
                case 300 -> timeType = ".5mCrafts";
                default -> {
                    EndGameLogic.endGame(wsplayer);
                    return true;
                }
            }

            EndGameLogic.endGame(wsplayer, timeType);
            return true;
        }
        return false;
    }

}
