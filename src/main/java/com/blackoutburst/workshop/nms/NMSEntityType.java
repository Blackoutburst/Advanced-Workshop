package com.blackoutburst.workshop.nms;

public enum NMSEntityType {
    CHICKEN(0x5D,"EntityChicken", NMS.getClass("EntityTypes"), NMS.getClass("World")),
    ITEM_FRAME(0x47,"EntityItemFrame", NMS.getClass("EntityTypes"), NMS.getClass("World")),
    VILLAGER(0x78,"EntityVillager", NMS.getClass("EntityTypes"), NMS.getClass("World"));


    public final int networkID;
    public final String className;
    public final Class<?>[] constructorArgs;

    NMSEntityType(int networkID, String className, Class<?>... constructorArgs) {
        this.networkID = networkID;
        this.className = className;
        this.constructorArgs = constructorArgs;
    }

    public int getNetworkID() {
        return networkID;
    }

    public String getClassName() {
        return className;
    }

    public Class<?>[] getConstructorArgs() {
        return constructorArgs;
    }
}