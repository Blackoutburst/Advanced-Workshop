package com.blackoutburst.workshop.nms;

import org.bukkit.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NMSEntities {

    protected Object entity;
    protected int networkID;

    /**
     * Define all the entity types
     */
    public enum EntityType {
        ARMOR_STAND(0x4E,"EntityArmorStand", NMS.getClass("World")),
        ARROW(0x3C,"EntityArrow", NMS.getClass("World"), double.class, double.class, double.class),
        BAT(0x41,"EntityBat", NMS.getClass("World")),
        BLAZE(0x3D,"EntityBlaze", NMS.getClass("World")),
        BOAT(0x01,"EntityBoat", NMS.getClass("World")),
        CAVE_SPIDER(0x3B,"EntityCaveSpider", NMS.getClass("World")),
        CHICKEN(0x5D,"EntityChicken", NMS.getClass("World")),
        COW(0x5C,"EntityCow", NMS.getClass("World")),
        CREEPER(0x32,"EntityCreeper", NMS.getClass("World")),
        EGG(0x3E,"EntityEgg", NMS.getClass("World"), double.class, double.class, double.class),
        ENDER_CRYSTAL(0x33,"EntityEnderCrystal", NMS.getClass("World")),
        ENDER_DRAGON(0x3F,"EnityEnderDragon", NMS.getClass("World")),
        ENDERMAN(0x3A,"EntityEnderman", NMS.getClass("World")),
        ENDERMITE(0x43,"EntityEndermite", NMS.getClass("World")),
        ENDER_PEARL(0x41,"EntityEnderPearl", NMS.getClass("World")),
        EXPERIENCE_ORB(0x00,"EntityExperienceOrb", NMS.getClass("World"), double.class, double.class, double.class),
        FALLING_BLOCK(0x46,"EntityFallingBlock", NMS.getClass("World"), double.class, double.class, double.class, NMS.getClass("IBlockData")),
        FIREWORK(0x4C,"EntityFireworks", NMS.getClass("World"), double.class, double.class, double.class, NMS.getClass("ItemStack")),
        GHAST(0x38,"EntityGhast", NMS.getClass("World")),
        GIANT(0x35,"EntityGiantZombie", NMS.getClass("World")),
        GUARDIAN(0x44,"EntityGuardian", NMS.getClass("World")),
        HORSE(0x64,"EntityHorse", NMS.getClass("World")),
        IRON_GOLEM(0x63,"EntityIronGolem", NMS.getClass("World")),
        ITEM(0x02,"EntityItem", NMS.getClass("World"), double.class, double.class, double.class),
        ITEM_FRAME(0x47,"EntityItemFrame", NMS.getClass("World"), NMS.getClass("BlockPosition"), NMS.getClass("EnumDirection")),
        LARGE_FIREBALL(0x3F,"EntityLargeFireball", NMS.getClass("World")),
        LIGHTNING(0x00,"EntityLightning", NMS.getClass("World"), double.class, double.class, double.class),
        MAGMA_CUBE(0x3E,"EntityMagmaCube", NMS.getClass("World")),
        MINECART_CHEST(0x0A,"EntityMinecartChest", NMS.getClass("World")),
        MINECART_COMMAND_BLOCK(0x0A,"EntityMinecartCommandBlock", NMS.getClass("World")),
        MINECART_FURNACE(0x0A,"EntityMinecartFurnace", NMS.getClass("World")),
        MINECART_HOPPER(0x0A,"EntityMinecartHopper", NMS.getClass("World")),
        MINECART_MOB_SPAWNER(0x0A,"EntityMinecartMobSpawner", NMS.getClass("World")),
        MINECART_TNT(0x0A,"EntityMinecartTNT", NMS.getClass("World")),
        MUSHROOM_COW(0x60,"EntityMushroomCow", NMS.getClass("World")),
        OCELOT(0x62,"EntityOcelot", NMS.getClass("World")),
        PAINTING(0x00,"EntityPainting", NMS.getClass("World"), NMS.getClass("BlockPosition"), NMS.getClass("EnumDirection")),
        PIG(0x5A,"EntityPig", NMS.getClass("World")),
        PIG_ZOMBIE(0x39,"EntityPigZombie", NMS.getClass("World")),
        POTION(0x49,"EntityPotion", NMS.getClass("World"), double.class, double.class, double.class, NMS.getClass("ItemStack")),
        RABBIT(0x65,"EntityRabbit", NMS.getClass("World")),
        SHEEP(0x5B,"EntitySheep", NMS.getClass("World")),
        SILVERFISH(0x3C,"EntitySilverfish", NMS.getClass("World")),
        SKELETON(0x33,"EntitySkeleton", NMS.getClass("World")),
        SLIME(0x37,"EntitySlime", NMS.getClass("World")),
        SMALL_FIREBALL(0x40,"EntitySmallFireball", NMS.getClass("World")),
        SNOWBALL(0x3D,"EntitySnowball", NMS.getClass("World"), double.class, double.class, double.class),
        SNOWMAN(0x61,"EntitySnowman", NMS.getClass("World")),
        SPIDER(0x34,"EntitySpider", NMS.getClass("World")),
        SQUID(0x5E,"EntitySquid", NMS.getClass("World")),
        EXPERIENCE_BOTTLE(0x4B,"EntityExperienceBottle", NMS.getClass("World"), double.class, double.class, double.class),
        TNT(0x32,"EntityTNTPrimed", NMS.getClass("World")),
        VILLAGER(0x78,"EntityVillager", NMS.getClass("World")),
        WITCH(0x42,"EntityWitch", NMS.getClass("World")),
        WITHER(0x40,"EntityWither", NMS.getClass("World")),
        WITHER_SKULL(0x42,"EntityWitherSkull", NMS.getClass("World")),
        WOLF(0x5F,"EntityWolf", NMS.getClass("World")),
        ZOMBIE(0x36,"EntityZombie", NMS.getClass("World"));

        public final int networkID;
        public final String className;
        public final Class<?>[] constructorArgs;

        EntityType(int networkID, String className, Class<?>... constructorArgs) {
            this.networkID = networkID;
            this.className = className;
            this.constructorArgs = constructorArgs;
        }
    }

    /**
     * Allow you to get the entity network ID
     *
     * @return the entity network ID
     */
    public int getNetworkID() {
        try {
            return networkID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Allow you to get the entity ID
     *
     * @return the entity ID
     */
    public int getID() {
        try {
            final Method getId = entity.getClass().getMethod("getId");
            return (int) getId.invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Allow you to get the DataWatcher
     *
     * @return the entity DataWatcher
     */
    public Object getDataWatcher() {
        try {
            final Method getDataWatcher = entity.getClass().getMethod("getDataWatcher");
            return getDataWatcher.invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a new entity instance
     *
     * @param world the world used to spawn the entity
     * @param type the type of entity that will be spawned
     */
    public NMSEntities(World world, EntityType type, Object... parameters) {
        try {
            final Class<?> entityClass = NMS.getClass(type.className);

            final Constructor<?> entityConstructor = entityClass.getConstructor(type.constructorArgs);

            final List<Object> tmp = new ArrayList<>();
            tmp.add(NMSWorld.getWorld(world));
            tmp.addAll(Arrays.asList(parameters));

            final Object[] args = tmp.toArray();

            entity = entityConstructor.newInstance(args);
            networkID = type.networkID;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}