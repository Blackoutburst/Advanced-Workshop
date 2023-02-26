package com.blackoutburst.workshop.nms;

import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class NettyListener {

    private NettyListener(){}

    public static void init(Player player, NMSPacket nmsPacket) {
        try {
            Class<?> networkManagerClass = NMS.getClass("NetworkManager");
            Class<?> channelClass = Class.forName("io.netty.channel.Channel");

            Field channelField = null;
            Field[] networkManagerFields = networkManagerClass.getDeclaredFields();

            for (Field field : networkManagerFields) {
                if (field.getType().isAssignableFrom(channelClass)) {
                    channelField = field;
                    break;
                }
            }

            createListener(player, channelField, nmsPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createListener(Player player, Field channelField, NMSPacket nmsPacket) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            Object channel = channelField.get(playerConnection.getClass().getField("networkManager").get(playerConnection));
            if (channel == null) return;

            Object pipeline = channel.getClass().getMethod("pipeline").invoke(channel);
            Class<?> channelHandlerClass = Class.forName("io.netty.channel.ChannelHandler");

            Method addAfter = pipeline.getClass().getMethod("addAfter", String.class, String.class, channelHandlerClass);
            Method pipelineGet = pipeline.getClass().getMethod("get", String.class);

            Class<?> channelInboundHandlerClass = Class.forName("io.netty.channel.ChannelInboundHandler");

            Class<?> channelHandlerContextClass = Class.forName("io.netty.channel.ChannelHandlerContext");

            Object originalHandler = pipelineGet.invoke(pipeline, "decoder");

            Object customHandler = Proxy.newProxyInstance(
                    ClassLoader.getSystemClassLoader(),
                    new Class[] { channelInboundHandlerClass },
                    (proxy, method, args) -> {
                        if (method.getName().equals("channelRead")) {
                            Object ctx = args[0];
                            Object packet = args[1];

                            Method originalHandlerReadMethod = originalHandler.getClass().getMethod("channelRead", channelHandlerContextClass, Object.class);
                            originalHandlerReadMethod.invoke(originalHandler, ctx, packet);

                            if (packet.getClass().getSimpleName().equals("PacketPlayInUseEntity"))
                                packetSorter(packet, nmsPacket, player);
                        }
                        return null;
                    }
            );

            addAfter.invoke(pipeline, "decoder", "NETTY_LISTENER", customHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void packetSorter(Object packet, NMSPacket nmsPacket, Player player) throws Exception {
        WSPlayer wsPlayer = WSPlayer.getFromPlayer(player);
        if (wsPlayer == null) return;

        Class<?> packetClass = NMS.getClass(packet.getClass().getSimpleName());

        switch (NMSPackets.getFromClassName(packet.getClass().getSimpleName())) {
            case USE_ENTITY: {
                Field entityIdField = packetClass.getDeclaredField("a");
                Method getAction = packetClass.getMethod("b");
                Method getHand = packetClass.getMethod("c");
                entityIdField.setAccessible(true);

                NMSEntityUseEvent.Action action = NMSEntityUseEvent.Action.getFromNMSName(getAction.invoke(packet).toString());
                if (action == null) return;

                int handIndex = 0;

                try {
                    handIndex = ((Enum<?>)getHand.invoke(packet)).ordinal();
                } catch (Exception ignored) {}

                NMSEntityUseEvent.Hand hand = NMSEntityUseEvent.Hand.values()[handIndex];

                int entityId = entityIdField.getInt(packet);

                NMSEntity entity = null;

                for (NMSEntity e : wsPlayer.getEntities()) {
                    if (e.getID() == entityId) {
                        entity = e;
                        break;
                    }
                }

                NMSEntityUseEvent event = new NMSEntityUseEvent(player, entity, action, hand);
                nmsPacket.onEntityUse(event);
                break;
            }
        }
    }

    public static void remove(Player player) {
        try {
            Class<?> networkManagerClass = NMS.getClass("NetworkManager");
            Class<?> channelClass = Class.forName("io.netty.channel.Channel");
            Field channelField = null;
            Field[] networkManagerFields = networkManagerClass.getDeclaredFields();

            for (Field field : networkManagerFields) {
                if (field.getType().isAssignableFrom(channelClass)) {
                    channelField = field;
                    break;
                }
            }

            if (channelField == null)
                return;

            deleteListener(player, channelField);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteListener(Player player, Field channelField) throws Exception {
        Object handle = player.getClass().getMethod("getHandle").invoke(player);
        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
        Object channel = channelField.get(playerConnection.getClass().getField("networkManager").get(playerConnection));
        if (channel == null) return;

        Object pipeline = channel.getClass().getMethod("pipeline").invoke(channel);

        Method pipelineGet = pipeline.getClass().getMethod("get", String.class);
        Method pipelineRemove = pipeline.getClass().getMethod("remove", String.class);

        if (pipelineGet.invoke(pipeline, "NETTY_LISTENER") != null) {
            pipelineRemove.invoke(pipeline, "NETTY_LISTENER");
        }
    }
}
