package com.blackoutburst.workshop.nms;

public class NMSEnumDirection {

    public enum Direction {
        DOWN(1, 0, 90),
        UP(0, 0, -90),
        NORTH(2, 180, 0),
        SOUTH(3, 0, 0),
        WEST(4, 90, 0),
        EAST(5, -90, 0);

        private final int logicalDirection;
        private final int yaw;
        private final int pitch;

        Direction(int logicalDirection, int yaw, int pitch) {
            this.logicalDirection = logicalDirection;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public int getLogicalDirection() {
            return logicalDirection;
        }

        public int getYaw() {
            return yaw;
        }

        public int getPitch() {
            return pitch;
        }
    }

    protected Object direction;

    public NMSEnumDirection(Direction direction) {
        try {
            Class<?> enumClass = NMS.getClass("EnumDirection");

            this.direction = enumClass.getEnumConstants()[direction.ordinal()];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getDirection() {
        return direction;
    }
}
