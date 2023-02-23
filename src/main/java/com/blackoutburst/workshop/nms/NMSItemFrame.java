package com.blackoutburst.workshop.nms;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class NMSItemFrame extends NMSEntity {

    protected NMSEnumDirection.Direction direction;
    protected ItemStack itemStack;

    public NMSItemFrame(World world, Object... parameters) {
        super(world, NMSEntityType.ITEM_FRAME, parameters);
    }

    public void setDirection(NMSEnumDirection.Direction direction) {
        try {
            setYawPitch(direction.getYaw(), direction.getPitch());

            this.direction = direction;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object toNMSItemStack(ItemStack stack) {
        try {
            Class<?> itemStackClass = NMS.getClass("ItemStack");
            Class<?> blocksClass = NMS.getClass("Blocks");
            Class<?> iMaterialClass = NMS.getClass("IMaterial");

            Constructor<?> stackConstructor = itemStackClass.getConstructor(iMaterialClass);
            Object block = blocksClass.getField(stack.getType().toString()).get(null);

            return stackConstructor.newInstance(block);
        } catch (Exception ignored) {}
        try {
            Class<?> itemStackClass = NMS.getClass("ItemStack");
            Class<?> itemsClass = NMS.getClass("Items");
            Class<?> iMaterialClass = NMS.getClass("IMaterial");

            Constructor<?> stackConstructor = itemStackClass.getConstructor(iMaterialClass);
            Object item = itemsClass.getField(stack.getType().toString()).get(null);

            return stackConstructor.newInstance(item);
        } catch (Exception ignored) {}
        return null;
    }

    public void setItem(Player player, ItemStack stack) {
        try {
            Class<?> entityClass = this.entity.getClass();
            Class<?> itemStackClass = NMS.getClass("ItemStack");

            Method method = entityClass.getMethod("setItem", itemStackClass);

            method.invoke(this.entity, toNMSItemStack(stack));
            NMSPacketPlayOutEntityMetadata.send(player, this);

            itemStack = stack;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawn(Player player) {
        NMSPacketPlayOutSpawnEntity.send(player, this, this.direction.getLogicalDirection());
        NMSPacketPlayOutEntityMetadata.send(player, this);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public NMSEnumDirection.Direction getDirection() {
        return direction;
    }
}
