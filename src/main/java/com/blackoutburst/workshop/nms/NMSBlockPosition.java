package com.blackoutburst.workshop.nms;

import java.lang.reflect.Constructor;

public class NMSBlockPosition {

    public Object position;

    public NMSBlockPosition(int x, int y, int z) {
        try {
            Class<?> blockPositionClass = NMS.getClass("BlockPosition");

            Constructor<?> blockPositionConstructor = blockPositionClass.getConstructor(int.class, int.class, int.class);

            position = blockPositionConstructor.newInstance(x, y , z);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
