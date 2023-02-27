package com.blackoutburst.workshop.utils.map;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.blocks.DecoBlock;
import com.blackoutburst.workshop.core.blocks.MaterialBlock;
import com.blackoutburst.workshop.core.blocks.NeededBlock;
import com.blackoutburst.workshop.core.blocks.RandomBlock;
import com.blackoutburst.workshop.utils.files.MapFileUtils;
import com.blackoutburst.workshop.utils.minecraft.BlockUtils;
import com.blackoutburst.workshop.utils.files.DecoFileUtils;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTList;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class DecoUtils {

    public static void scan(WSPlayer wsplayer, String name) {
        DecoFileUtils.deleteFile(name);

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

        List<Location> locations = new ArrayList<>();
        List<List<Boolean>> allNeeded = new ArrayList<>();
        List<List<String>> allBlockData = new ArrayList<>();


        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block b = world.getBlockAt(x, y, z);

                    int relX = x - minX;
                    int relY = y - minY;
                    int relZ = z - minZ;
                    Location relativeLoc = new Location(world, relX, relY,relZ);

                    if (b.getType().equals(Material.AIR)) continue;
                    locations.add(relativeLoc);
                    RandomBlock[] blocks = null;
                    if (b.getType().equals(Material.DISPENSER) || b.getType().equals(Material.DROPPER)) {
                        InventoryHolder dropper = (InventoryHolder) b.getState();
                        if (dropper.getInventory().getContents()[0] != null) {
                            blocks = readDropper(dropper.getInventory().getContents()[0]);
                        }
                    }
                    if (blocks != null) {
                        List<String> blockData = new ArrayList<>();
                        List<Boolean> needed = new ArrayList<>();
                        for (RandomBlock block : blocks) {
                            if (b.getType() == Material.DROPPER && block.isPriority()) continue;
                            blockData.add(block.getBlockData().getAsString());
                            needed.add(block.isPriority());
                        }
                        allBlockData.add(blockData);
                        allNeeded.add(needed);
                        continue;
                    }
                    allBlockData.add(new ArrayList<>(Collections.singleton(b.getBlockData().getAsString())));
                    allNeeded.add(new ArrayList<>(Collections.singleton(false)));
                }
            }
        }
        DecoFileUtils.saveFile(name,locations, allBlockData, allNeeded);
    }

    private static RandomBlock[] readDropper(ItemStack item) {
        if (item.getType() == Material.CHEST || item.getType() == Material.SHULKER_BOX) {
            NBTContainer chest_nbt = NBTItem.convertItemtoNBT(item);

            if (chest_nbt.getKeys().contains("tag")) {
                NBTList<ReadWriteNBT> chestItems = MapUtils.readChestItemNBT(item);
                RandomBlock[] randomBlocks = new RandomBlock[chestItems.size()];
                for (int i = 0; i < chestItems.size(); i++) {
                    boolean needed = false;
                    ReadWriteNBT itemNBT = chestItems.get(i);
                    if (itemNBT.getInteger("Slot") == i + 1) {
                        needed = true;
                    }
                    BlockData block = Material.AIR.createBlockData();
                    ItemStack chestItem = NBTItem.convertNBTtoItem((NBTCompound) itemNBT);
                    if (chestItem.getType().isBlock()) {
                        block = Bukkit.createBlockData(itemNBT.getString("id"));
                    }
                    RandomBlock randomBlock = new RandomBlock(block, needed);
                    randomBlocks[i] = randomBlock;
                }
                return randomBlocks;
            }
        }
        BlockData block = Material.AIR.createBlockData();
        if (item.getType().isBlock()) {
            block = item.getType().createBlockData();
        }
        return new RandomBlock[]{new RandomBlock(block,false)};
    }

    public static void updateBlocks(WSPlayer wsplayer) {
        List<DecoBlock> decoBlocks = wsplayer.getDecoBlocks();
        for (DecoBlock decoBlock : decoBlocks) {
            if (decoBlock.getTypes().length == 1) continue;

            Location location = decoBlock.getLocation();
            NeededBlock neededBlock = getNeededBlock(wsplayer, location);
            MaterialBlock materialBlock = BlockUtils.getMaterialBlock(wsplayer, location);
            if (neededBlock != null) {
                int index = neededBlock.getIndex();
                decoBlock.setIndex(index);
                if (materialBlock != null) {
                    materialBlock.setIndex(index);
                }
                continue;
            }
            if (wsplayer.isInGame()) {
                Random rng = new Random();
                int randomBlockIndex = rng.nextInt(decoBlock.getNormalBlockNum());

                decoBlock.setIndex(randomBlockIndex);
                if (materialBlock != null) {
                    materialBlock.setIndex(randomBlockIndex);
                }
                continue;
            }
            decoBlock.setTypes(new BlockData[]{Material.AIR.createBlockData()});
        }
    }

    public static DecoBlock getBlock(WSPlayer wsPlayer, Location location) {
        for (DecoBlock block : wsPlayer.getDecoBlocks()) {
            if (location.getWorld().getName().equals(block.getWorld().getName()) &&
                    location.getBlockX() == block.getLocation().getBlockX() &&
                    location.getBlockY() == block.getLocation().getBlockY() &&
                    location.getBlockZ() == block.getLocation().getBlockZ()) {
                return block;
            }
        }
        return null;
    }

    private static NeededBlock getNeededBlock(WSPlayer wsPlayer, Location location) {
        for (NeededBlock block : wsPlayer.getNeededBlocks()) {
            if (location.getWorld().getName().equals(block.getWorld().getName()) &&
                    location.getBlockX() == block.getLocation().getBlockX() &&
                    location.getBlockY() == block.getLocation().getBlockY() &&
                    location.getBlockZ() == block.getLocation().getBlockZ()) {
                return block;
            }
        }
        return null;
    }
}
