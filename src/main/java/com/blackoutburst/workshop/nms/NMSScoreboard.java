package com.blackoutburst.workshop.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class NMSScoreboard {

    public enum DisplaySlot {
        PLAYER_LIST,
        SIDEBAR,
        BELOW_NAME
    }

    public enum EnumScoreboardHealthDisplay {
        INTEGER,
        HEARTS
    }

    protected List<NMSScoreboardScore> lines;

    protected DisplaySlot slot;

    protected Object scoreboard;
    protected Object objective;

    private Class<?> scoreboardClass;
    private Class<?> objectiveClass;

    public List<NMSScoreboardScore> getLines() {
        return lines;
    }

    public NMSScoreboard() {
        try {
            scoreboardClass = NMS.getClass("Scoreboard");
            objectiveClass = NMS.getClass("ScoreboardObjective");

            Constructor<?> scoreboardConstructor = scoreboardClass.getConstructor();

            scoreboard = scoreboardConstructor.newInstance();
            lines = new ArrayList<>();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void registerObjective(String name, EnumScoreboardHealthDisplay healthDisplay) {
        try {
            Class<?> iScoreboardCriteriaClass = NMS.getClass("IScoreboardCriteria");
            Class<?> scoreboardHealthDisplayEnum = NMS.getClass("IScoreboardCriteria$EnumScoreboardHealthDisplay");
            Class<?> chatBaseComponentClass = NMS.getClass("IChatBaseComponent");
            Class<?> chatSerializer = NMS.getClass("IChatBaseComponent").getDeclaredClasses()[0];

            Field classField = iScoreboardCriteriaClass.getField("DUMMY");
            Object criteria = classField.get(null);

            Object displayName = chatSerializer.getMethod("a", String.class).invoke(null, "{\"text\": \"" + name + "\"}");

            Method method = scoreboardClass.getMethod("registerObjective", String.class, iScoreboardCriteriaClass, chatBaseComponentClass, scoreboardHealthDisplayEnum);

            this.objective = method.invoke(scoreboard, name, criteria, displayName, scoreboardHealthDisplayEnum.getEnumConstants()[healthDisplay.ordinal()]);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setDisplaySlot(DisplaySlot slot) {
        try {
            Method method = scoreboardClass.getMethod("setDisplaySlot", int.class, this.objectiveClass);

            method.invoke(scoreboard, slot.ordinal(), this.objective);
            this.slot = slot;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setDisplayName(String name) {
        try {
            Class<?> chatBaseComponentClass = NMS.getClass("IChatBaseComponent");
            Class<?> chatSerializer = NMS.getClass("IChatBaseComponent").getDeclaredClasses()[0];
            
            Method method = objectiveClass.getMethod("setDisplayName", chatBaseComponentClass);

            Object displayName = chatSerializer.getMethod("a", String.class).invoke(null, "{\"text\": \"" + name + "\"}");

            method.invoke(objective, displayName);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
