package com.blackoutburst.workshop.nms;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class NMSEntity {

    protected static final List<NMSEntity> GLOBAL_ENTITIES = new ArrayList<>();

    private static final Class<?> ENTITY_CLASS = NMS.getClass("Entity");

    protected Location location;

    protected final UUID uuid;

    protected String tag;

    protected Object entity;
    protected int networkID;
    protected NMSEntityType type;

    public int getNetworkID() {
        try {
            return networkID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getID() {
        try {
            Method getId = ENTITY_CLASS.getMethod("getId");
            return (int) getId.invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Object getDataWatcher() {
        try {
            Method getDataWatcher = ENTITY_CLASS.getMethod("getDataWatcher");
            return getDataWatcher.invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public NMSEntity(World world, NMSEntityType type, Object... parameters) {
        this.type = type;
        this.uuid = UUID.randomUUID();

        try {
            Class<?> entityClass = NMS.getClass(type.className);
            Class<?> entityTypesClass = NMS.getClass("EntityTypes");

            Constructor<?> entityConstructor = entityClass.getConstructor(type.constructorArgs);
            Field entityTypeField = entityTypesClass.getField(String.valueOf(type));

            Object entityType = entityTypeField.get(null);

            List<Object> tmp = new ArrayList<>();
            tmp.add(entityType);
            tmp.add(NMSWorld.getWorld(world));
            tmp.addAll(Arrays.asList(parameters));

            Object[] args = tmp.toArray();

            this.entity = entityConstructor.newInstance(args);
            this.networkID = type.networkID;
            this.location = new Location(world,0,0,0,0,0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void load(WSPlayer wsPlayer) {
        Player player = wsPlayer.getPlayer();

        for (NMSEntity e : GLOBAL_ENTITIES) {
            wsPlayer.getEntities().add(e);
            NMSPacketPlayOutSpawnEntityLiving.send(player, e);
            NMSPacketPlayOutEntityHeadRotation.send(player, e, e.location.getYaw());
        }
    }

    public NMSEntityType getType() {
        return type;
    }

    public void spawn() {
        for (WSPlayer wsPlayer : Main.players) {
            Player player = wsPlayer.getPlayer();

            NMSPacketPlayOutSpawnEntityLiving.send(player, this);
            NMSPacketPlayOutEntityHeadRotation.send(player, this, this.location.getYaw());
            wsPlayer.getEntities().add(this);
            GLOBAL_ENTITIES.add(this);
        }
    }

    public void delete() {
        for (WSPlayer wsPlayer : Main.players) {
            Player player = wsPlayer.getPlayer();

            for (NMSEntity e : wsPlayer.getEntities()) {
                if (e.uuid == this.uuid) {
                    NMSPacketPlayOutEntityDestroy.send(player, this.getID());
                    wsPlayer.getEntities().remove(this);
                }
            }
        }
        GLOBAL_ENTITIES.remove(this);
    }

    public void setLocation(double x, double y, double z, float yaw, float pitch) {
        try {
            Method method = ENTITY_CLASS.getMethod("setLocation", double.class, double.class, double.class, float.class, float.class);

            method.invoke(this.entity, x, y, z, yaw, pitch);
            location.setX(x);
            location.setY(y);
            location.setZ(z);
            location.setPitch(pitch);
            location.setYaw(yaw);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setYawPitch(float yaw, float pitch) {
        try {
            Method method = ENTITY_CLASS.getMethod("setYawPitch", float.class, float.class);

            method.invoke(this.entity, yaw, pitch);
            location.setPitch(pitch);
            location.setYaw(yaw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPosition(double x, double y, double z) {
        try {
            Method method = ENTITY_CLASS.getMethod("setPosition", double.class, double.class, double.class);

            method.invoke(this.entity, x, y, z);
            location.setX(x);
            location.setY(y);
            location.setZ(z);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}