package com.blackoutburst.workshop.nms;

public enum NMSPackets {
    USE_ENTITY("PacketPlayInUseEntity");

    private final String className;

    NMSPackets(String className) {
        this.className = className;
    }

    public static NMSPackets getFromClassName(String className) {
        for (NMSPackets packet : NMSPackets.values()) {
            if (packet.className.equals(className))
                return packet;
        }
        return null;
    }
}
