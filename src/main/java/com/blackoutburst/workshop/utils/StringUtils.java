package com.blackoutburst.workshop.utils;

import com.blackoutburst.workshop.core.WSPlayer;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils {

    public static String getCurrentRound(WSPlayer wsplayer) {
        if (wsplayer.getGameOptions().isUnlimitedCrafts())
            return "∞";

        return wsplayer.getCurrentCraftIndex() + "/" + wsplayer.getGameOptions().getCraftLimit();
    }

    public static String formatItemName(String name) {
        String input = name.replace("_", " ").toLowerCase();

        return Arrays.stream(input.split(" "))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
