package com.blackoutburst.workshop.nms;

public enum NMSEntityType {
    BLAZE("EntityBlaze", NMS.getClass("EntityTypes"), NMS.getClass("World")),
    CHICKEN("EntityChicken", NMS.getClass("EntityTypes"), NMS.getClass("World")),
    HOGLIN("EntityHoglin", NMS.getClass("EntityTypes"), NMS.getClass("World")),
    ITEM_FRAME("EntityItemFrame", NMS.getClass("EntityTypes"), NMS.getClass("World")),
    VILLAGER("EntityVillager", NMS.getClass("EntityTypes"), NMS.getClass("World")),
    WITHER_SKELETON("EntitySkeletonWither", NMS.getClass("EntityTypes"), NMS.getClass("World"));


    public final String className;
    public final Class<?>[] constructorArgs;

    NMSEntityType(String className, Class<?>... constructorArgs) {
        this.className = className;
        this.constructorArgs = constructorArgs;
    }

    public String getClassName() {
        return className;
    }

    public Class<?>[] getConstructorArgs() {
        return constructorArgs;
    }
}