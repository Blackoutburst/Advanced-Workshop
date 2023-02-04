package com.blackoutburst.workshop.nms;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class NMSItemFrame {

    private static Object toNMSItemStack(ItemStack stack) {
        try {
            final Class<?> itemStackClass = NMS.getClass("ItemStack");
            final Class<?> itemClass = NMS.getClass("Item");

            final Constructor<?> stackConstructor = itemStackClass.getConstructor(itemClass, int.class, int.class);

            final Method method = itemClass.getMethod("getById", int.class);

            return stackConstructor.newInstance(method.invoke(null, stack.getType().getId()), stack.getAmount(), (int) stack.getData().getData());
        } catch (Exception ignored) {}
        try {
            final Class<?> itemStackClass = NMS.getClass("ItemStack");
            final Class<?> blockClass = NMS.getClass("Block");

            final Constructor<?> stackConstructor = itemStackClass.getConstructor(blockClass, int.class, int.class);

            final Method method = blockClass.getMethod("getById", int.class);

            return stackConstructor.newInstance(method.invoke(null, stack.getType().getId()), stack.getAmount(), (int) stack.getData().getData());
        } catch (Exception ignored) {}
        return null;
    }

    public static void setItem(Player player, NMSEntities entity, ItemStack stack) {
        try {
            final Class<?> entityClass = entity.entity.getClass();
            final Class<?> itemStackClass = NMS.getClass("ItemStack");

            final Method method = entityClass.getMethod("setItem", itemStackClass);

            method.invoke(entity.entity, toNMSItemStack(stack));
            NMSEntityMetadata.send(player, entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
