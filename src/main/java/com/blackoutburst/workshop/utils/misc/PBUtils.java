package com.blackoutburst.workshop.utils.misc;

import com.blackoutburst.workshop.core.Timers;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.game.GameOptions;
import com.blackoutburst.workshop.utils.files.DBUtils;

import java.time.Duration;

public class PBUtils {

    public static void craftPB(WSPlayer wsplayer, String timeType) {
        GameOptions gameoptions = wsplayer.getGameOptions();

        int timeLimit = gameoptions.getTimeLimit().intValue();

        Integer craftPB = DBUtils.getData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + timeType, Integer.class);
        if (craftPB == null) craftPB = Integer.MIN_VALUE;

        int craftsCompleted = wsplayer.getCurrentCraftIndex() - 1;
        if (craftsCompleted > craftPB && gameoptions.getRandomType() == 'N')
            DBUtils.saveData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + timeType, craftsCompleted , Integer.class);

        if (craftPB != Integer.MIN_VALUE && craftsCompleted > craftPB && gameoptions.getRandomType() == 'N') {
            Webhook.send("**"+ wsplayer.getPlayer().getName() + "** got a new PB on the map **" + wsplayer.getPlayArea().getType() + "** for crafts in **" + timeLimit + "s**!\\nCrafts: **" + craftsCompleted + "**\\nImprovement: **+" + (craftsCompleted - craftPB) + "**");
            wsplayer.getPlayer().sendMessage("§d§lPB! (+" + (craftsCompleted - craftPB) + " craft" + ((craftsCompleted - craftPB) == 1 ? ")" : "s)"));
        }
    }

    public static boolean allCraftPB(WSPlayer wsplayer) {
        Timers timers = wsplayer.getTimers();

        Float duration = Duration.between(timers.getMapBegin(), timers.getMapEnd()).toMillis() / 1000.0f;

        Double currentDuration = DBUtils.getData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".timeAll", Double.class);
        if (currentDuration == null) currentDuration = Double.MAX_VALUE;

        if (duration < currentDuration)
            DBUtils.saveData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".timeAll", duration, Float.class);

        if (currentDuration != Double.MAX_VALUE && (duration < currentDuration)) {
            Webhook.send("**"+ wsplayer.getPlayer().getName() + "** got a new PB on the map **" + wsplayer.getPlayArea().getType() + "** for all crafts!\\nTime: **" + StringUtils.ROUND.format( duration) + "s**\\nImprovement: **" + StringUtils.ROUND.format(duration - currentDuration) + "s**");
            wsplayer.getPlayer().sendMessage("§d§lPB! (" + StringUtils.ROUND.format(duration - currentDuration) + "s" + ")");
            return true;
        }
        return false;
    }

    public static boolean hypixelSaysAllCraftPB(WSPlayer wsplayer) {
        Timers timers = wsplayer.getTimers();

        Float duration = Duration.between(timers.getMapBegin(), timers.getMapEnd()).toMillis() / 1000.0f;

        Double currentDuration = DBUtils.getData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".HStimeAll", Double.class);
        if (currentDuration == null) currentDuration = Double.MAX_VALUE;

        if (duration < currentDuration)
            DBUtils.saveData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".HStimeAll", duration, Float.class);

        if (currentDuration != Double.MAX_VALUE && (duration < currentDuration)) {
            Webhook.send("**"+ wsplayer.getPlayer().getName() + "** got a new PB on the map **" + wsplayer.getPlayArea().getType() + "** for hypixel says mode all crafts!\\nTime: **" + StringUtils.ROUND.format( duration) + "s**\\nImprovement: **" + StringUtils.ROUND.format(duration - currentDuration) + "s**");
            wsplayer.getPlayer().sendMessage("§d§lPB! (" + StringUtils.ROUND.format(duration - currentDuration) + "s" + ")");
            return true;
        }
        return false;
    }

    public static boolean regularPB(WSPlayer wsplayer) {
        Timers timers = wsplayer.getTimers();

        Float duration = Duration.between(timers.getMapBegin(), timers.getMapEnd()).toMillis() / 1000.0f;

        Double currentDuration = DBUtils.getData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".time", Double.class);
        if (currentDuration == null) currentDuration = Double.MAX_VALUE;

        if (duration < currentDuration)
            DBUtils.saveData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".time", duration, Float.class);

        if (currentDuration != Double.MAX_VALUE && (duration < currentDuration)) {
            Webhook.send("**"+ wsplayer.getPlayer().getName() + "** got a new PB on the map **" + wsplayer.getPlayArea().getType() + "**!\\nTime: **" +StringUtils.ROUND.format( duration) + "s**\\nImprovement: **" + StringUtils.ROUND.format(duration - currentDuration) + "s**");
            wsplayer.getPlayer().sendMessage("§d§lPB! (" + StringUtils.ROUND.format(duration - currentDuration) + "s" + ")");
            return true;
        }
        return false;
    }

    public static boolean craft10PB(WSPlayer wsplayer) {
        Timers timers = wsplayer.getTimers();

        Float duration = Duration.between(timers.getMapBegin(), timers.getMapEnd()).toMillis() / 1000.0f;

        Double currentDuration = DBUtils.getData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".time10", Double.class);
        if (currentDuration == null) currentDuration = Double.MAX_VALUE;

        if (duration < currentDuration)
            DBUtils.saveData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".time10", duration, Float.class);

        if (currentDuration != Double.MAX_VALUE && (duration < currentDuration)) {
            Webhook.send("**"+ wsplayer.getPlayer().getName() + "** got a new PB on the map **" + wsplayer.getPlayArea().getType() + "**for 10 crafts!\\nTime: **" +StringUtils.ROUND.format( duration) + "s**\\nImprovement: **" + StringUtils.ROUND.format(duration - currentDuration) + "s**");
            wsplayer.getPlayer().sendMessage("§d§lPB! (" + StringUtils.ROUND.format(duration - currentDuration) + "s" + ")");
            return true;
        }
        return false;
    }

    public static boolean craft15PB(WSPlayer wsplayer) {
        Timers timers = wsplayer.getTimers();

        Float duration = Duration.between(timers.getMapBegin(), timers.getMapEnd()).toMillis() / 1000.0f;

        Double currentDuration = DBUtils.getData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".time15", Double.class);
        if (currentDuration == null) currentDuration = Double.MAX_VALUE;

        if (duration < currentDuration)
            DBUtils.saveData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".time15", duration, Float.class);

        if (currentDuration != Double.MAX_VALUE && (duration < currentDuration)) {
            Webhook.send("**"+ wsplayer.getPlayer().getName() + "** got a new PB on the map **" + wsplayer.getPlayArea().getType() + "**for 15 crafts!\\nTime: **" +StringUtils.ROUND.format( duration) + "s**\\nImprovement: **" + StringUtils.ROUND.format(duration - currentDuration) + "s**");
            wsplayer.getPlayer().sendMessage("§d§lPB! (" + StringUtils.ROUND.format(duration - currentDuration) + "s" + ")");
            return true;
        }
        return false;
    }

    public static boolean hypixelSaysRegularPB(WSPlayer wsplayer) {
        Timers timers = wsplayer.getTimers();

        Float duration = Duration.between(timers.getMapBegin(), timers.getMapEnd()).toMillis() / 1000.0f;

        Double currentDuration = DBUtils.getData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".HStime", Double.class);
        if (currentDuration == null) currentDuration = Double.MAX_VALUE;

        if (duration < currentDuration)
            DBUtils.saveData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".HStime", duration, Float.class);

        if (currentDuration != Double.MAX_VALUE && (duration < currentDuration)) {
            Webhook.send("**"+ wsplayer.getPlayer().getName() + "** got a new PB on the map **" + wsplayer.getPlayArea().getType() + "** in hypixel says mode!\\nTime: **" +StringUtils.ROUND.format( duration) + "s**\\nImprovement: **" + StringUtils.ROUND.format(duration - currentDuration) + "s**");
            wsplayer.getPlayer().sendMessage("§d§lPB! (" + StringUtils.ROUND.format(duration - currentDuration) + "s" + ")");
            return true;
        }
        return false;
    }

    public static void nonPB(WSPlayer wsplayer, char type) {

        Timers timers = wsplayer.getTimers();
        Float duration = Duration.between(timers.getMapBegin(), timers.getMapEnd()).toMillis() / 1000.0f;
        String pbString = "";

        if (type == 'A' || type == 'R') {
            pbString = (type == 'R') ? ".time" : ".timeAll";
        }
        if (type == 'a' || type == 'r') {
            pbString = (type == 'r') ? ".HStime" : ".HStimeAll";
        }

        Double currentDuration = DBUtils.getData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + pbString, Double.class);

        if (currentDuration != null) {
            wsplayer.getPlayer().sendMessage("§c§l(+" + StringUtils.ROUND.format(duration - currentDuration) + "s)");
        }
    }

}
