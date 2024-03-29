package com.blackoutburst.workshop.utils.map;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.blocks.RandomItem;
import com.blackoutburst.workshop.utils.files.LogicFileUtils;

import com.blackoutburst.workshop.utils.files.MapFileUtils;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTList;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class LogicUtils {

    public static void scan(WSPlayer wsplayer, String name) {
        LogicFileUtils.deleteFile(name);
        File logicFile = MapFileUtils.getMapFile(name, 'L');

        int x1 = wsplayer.getScanWand1().getBlockX();
        int y1 = wsplayer.getScanWand1().getBlockY();
        int z1 = wsplayer.getScanWand1().getBlockZ();
        int x2 = wsplayer.getScanWand2().getBlockX();
        int y2 = wsplayer.getScanWand2().getBlockY();
        int z2 = wsplayer.getScanWand2().getBlockZ();

        World world = wsplayer.getScanWand1().getWorld();

        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int minZ = Math.min(z1, z2);

        int maxX = Math.max(x1, x2);
        int maxY = Math.max(y1, y2);
        int maxZ = Math.max(z1, z2);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block b = world.getBlockAt(x, y, z);

                    int relX = x - minX;
                    int relY = y - minY;
                    int relZ = z - minZ;
                    Location relLoc = new Location(world, relX, relY, relZ);

                    if (b.getType().equals(Material.DROPPER)) {
                        resourceScan(b,relLoc,"R", logicFile);
                    }
                    if (b.getType().equals(Material.DISPENSER)) {
                        resourceScan(b,relLoc,"P", logicFile);
                    }
                    if (b.getType().equals(Material.OAK_SIGN) || b.getType().equals(Material.OAK_WALL_SIGN)) {
                        Sign sign = (Sign) b.getState();
                        if (sign.getLines().length == 0) continue;
                        String text = sign.getLine(0);

                        BlockFace directionFacing;

                        if (sign.getBlock().getType() != Material.OAK_WALL_SIGN) {
                            org.bukkit.block.data.type.Sign signData = (org.bukkit.block.data.type.Sign) sign.getBlockData();
                            directionFacing = signData.getRotation();
                        }
                        else {
                            org.bukkit.block.data.type.WallSign signData = (org.bukkit.block.data.type.WallSign) sign.getBlockData();
                            directionFacing = signData.getFacing();
                        }

                        LogicFileUtils.saveFileSigns(logicFile, relLoc, text, directionFacing);
                    }
                }
            }
        }
    }

    private static void resourceScan(Block b, Location relLoc, String type, File logicFile) {
        ItemStack[] items;
        InventoryHolder container = (InventoryHolder) b.getState();
        items = container.getInventory().getContents();

        if (items[0] != null) {
            RandomItem[] dropperItems = readDropper(items[0]);

            for (int i = 0; i < dropperItems.length; i++) {
                RandomItem randomItem = dropperItems[i];
                ItemStack item = randomItem.GetItem();

                if (type.equals("R")) {
                    LogicFileUtils.saveFileItems(logicFile, relLoc, item, i, "Regular");
                }
                if (type.equals("P")) {
                    LogicFileUtils.saveFileItems(logicFile, relLoc, item, i, "Priority");
                }
            }
        } else {
            LogicFileUtils.saveFileItems(logicFile, relLoc, new ItemStack(Material.AIR), 0, "Regular");
        }

        for (int i = 1; i < items.length; i++) {
            if (items[i] != null) {
                RandomItem[] SlotTools = readDropper(items[i]);

                for (int j = 0; j < SlotTools.length; j++) {
                    ItemStack item = SlotTools[j].GetItem();

                    LogicFileUtils.saveFileTools(logicFile, relLoc, item, j, i);
                }
            }
        }
    }

    private static RandomItem[] readDropper(ItemStack item) {
        if (item.getType() == Material.CHEST || item.getType() == Material.SHULKER_BOX) {
            NBTContainer chest_nbt = NBTItem.convertItemtoNBT(item);

            if (chest_nbt.getKeys().contains("tag")) {
                NBTList<ReadWriteNBT> chestItems = MapUtils.readChestItemNBT(item);
                RandomItem[] randomItems = new RandomItem[chestItems.size()];
                for (int i = 0; i < chestItems.size(); i++) {
                    boolean needed = false;
                    ReadWriteNBT itemNBT = chestItems.get(i);
                    if (itemNBT.getInteger("Slot") == i + 1) {
                        needed = true;
                    }
                    ItemStack chestItem = NBTItem.convertNBTtoItem((NBTCompound) itemNBT);
                    randomItems[i] = new RandomItem(chestItem, needed);
                }
                return randomItems;
            }
        }
        return new RandomItem[]{ new RandomItem(item,false) };
    }
}
