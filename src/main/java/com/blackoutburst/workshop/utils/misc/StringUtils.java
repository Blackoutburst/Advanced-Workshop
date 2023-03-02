package com.blackoutburst.workshop.utils.misc;

import com.blackoutburst.workshop.core.WSPlayer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class StringUtils {

    public static final DecimalFormat ROUND = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));

    public static String getCurrentRound(WSPlayer wsplayer) {
        return wsplayer.getGameOptions().isUnlimitedCrafts() ? String.valueOf(wsplayer.getCurrentCraftIndex()) : wsplayer.getCurrentCraftIndex() + "/" + wsplayer.getGameOptions().getCraftLimit();
    }

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String formatItemName(String name) {
        String input = name.replace("_", " ").toLowerCase();

        return Arrays.stream(input.split(" "))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
