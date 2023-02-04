package com.blackoutburst.workshop.nms;

public class NMSEnumDirection {

    public enum Direction {
        DOWN,
        UP,
        NORTH,
        SOUTH,
        WEST,
        EAST
    }

    public Object direction;

    public NMSEnumDirection(Direction direction) {
        try {
            final Class enumClass = NMS.getClass("EnumDirection");

            this.direction = Enum.valueOf(enumClass, direction.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
