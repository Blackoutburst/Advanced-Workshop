package com.blackoutburst.workshop.utils;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.*;
import de.tr7zw.nbtapi.*;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtinjector.javassist.bytecode.analysis.ControlFlow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.InventoryWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.List;

public class MapUtils {

    public static void decoScan(WSPlayer wsplayer, String name) {
        try {
            PrintWriter writer = new PrintWriter("./plugins/Workshop/" + name + ".deco");
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
                        String relCoords = relX + "," + relY + "," + relZ;

                        if (b.getType().equals(Material.AIR)) continue;
                        ArrayList<ArrayList<Object>> blocks = null;
                        if (b.getType().equals(Material.DISPENSER)) {
                            Dispenser dispenser = (Dispenser) b.getState();
                            if (dispenser.getInventory().getContents()[0] != null) {
                                blocks = readDropper(dispenser.getInventory().getContents()[0]);
                            }
                        }
                        if (b.getType().equals(Material.DROPPER)) {
                            Dropper dropper = (Dropper) b.getState();
                            if (dropper.getInventory().getContents()[0] != null) {
                                blocks = readDropper(dropper.getInventory().getContents()[0]);
                            }
                        }
                        StringBuilder blocksString = new StringBuilder();
                        StringBuilder neededBlocksString = new StringBuilder();
                        if (!(blocks == null)) {

                            for (ArrayList<Object> i : blocks) {

                                String id = (String) i.get(0);
                                int data = (int) i.get(1);
                                ReadWriteNBT nbtObject = NBT.createNBTObject();
                                nbtObject.setString("id",id);
                                nbtObject.setInteger("Damage",data);
                                nbtObject.setInteger("Count",1);
                                ItemStack convertedItem = NBTItem.convertNBTtoItem((NBTCompound) nbtObject);


                                if (!(boolean) i.get(2)) {
                                    if (blocksString.length() == 0) {
                                        blocksString.append(convertedItem.getType()).append(" ").append(convertedItem.getData().getData());
                                        continue;
                                    }
                                    blocksString.append(",").append(convertedItem.getType()).append(" ").append(convertedItem.getData().getData());
                                    continue;
                                }
                                if (b.getType().equals(Material.DROPPER)) {
                                    continue;
                                }
                                if (neededBlocksString.length() == 0) {
                                    neededBlocksString.append(convertedItem.getType()).append(" ").append(convertedItem.getData().getData());
                                    continue;
                                }
                                neededBlocksString.append(",").append(convertedItem.getType()).append(" ").append(convertedItem.getData().getData());
                            }
                        }
                        else {
                            blocksString.append(b.getType()).append(" ").append(b.getData());
                        }

                        writer.write(relCoords + ";" + blocksString + ";" + neededBlocksString + "\n");
                    }
                }
            }

            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void logicScan(WSPlayer wsplayer, String name) {
        try {
            PrintWriter writer = new PrintWriter("./plugins/Workshop/" + name + ".logic");
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
                        String relCoords = relX + "," + relY + "," + relZ;

                        if (b.getType().equals(Material.DROPPER)) {
                            writer.write(resourceScan(b,relCoords,"R"));
                        }
                        if (b.getType().equals(Material.DISPENSER)) {
                            writer.write(resourceScan(b,relCoords,"P"));
                        }
                        if (b.getType().equals(Material.SIGN) || b.getType().equals(Material.SIGN_POST) || b.getType().equals(Material.WALL_SIGN)) {
                            Sign sign = (Sign) b.getState();
                            if (sign.getLines().length == 0) continue;
                            String text = sign.getLine(0);

                            org.bukkit.material.Sign sign2 = (org.bukkit.material.Sign) sign.getData();
                            BlockFace directionFacing = sign2.getFacing();

                            writer.write("S, " + text + ", " + relX + ", " + relY + ", " + relZ + ", " + directionFacing.toString() + "\n");
                        }
                    }
                }
            }

            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadSpawn() {
        try {
            String[] data = Files.readAllLines(Paths.get("./plugins/Workshop/spawn")).get(0).split(", ");
            Main.spawn = new Location(Bukkit.getWorld(data[0]), Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]), Float.parseFloat(data[4]), Float.parseFloat(data[5]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restoreArea(WSPlayer wsplayer, boolean clear_inventories) {
        wsplayer.getNeededBlocks().clear();
        wsplayer.getDecoBlocks().clear();
        if (wsplayer.getCurrentCraft() != null) {
            getNeededBlocks(wsplayer);
        }

        updateDecoBlocks(wsplayer);

        List<DecoBlock> decoBlocks = wsplayer.getDecoBlocks();
        List<Material> inventories = Arrays.asList(
                Material.CHEST, Material.TRAPPED_CHEST, Material.FURNACE, Material.BURNING_FURNACE,
                Material.DISPENSER, Material.DROPPER, Material.BREWING_STAND, Material.HOPPER);

        for (DecoBlock i : decoBlocks) {
            Location location = i.getLocation();
            Block b = location.getBlock();

            b.setType(i.getType());
            b.setData(i.getData());

            if (!clear_inventories) continue;

            if (inventories.contains(i.getType())) {
                InventoryHolder container = (InventoryHolder) b.getState();
                container.getInventory().clear();
            }
        }
    }

    public static void pasteMap(WSPlayer wsplayer, String name) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + name + ".deco"));
            Player player = wsplayer.getPlayer();
            World world = player.getWorld();
            Location anchor = player.getLocation();

            for (String line : lines) {
                String[] data = line.split(";");
                String[] blockTypes = data[1].split(",");
                String[] blockType;
                if (blockTypes.length > 1) {
                    blockType = new String[]{"AIR", "0"};
                }
                else {
                    blockType = blockTypes[0].split(" ");
                }
                Material type = Material.getMaterial(blockType[0]);
                byte dataType = Byte.parseByte(blockType[1]);
                int xOffset = Integer.parseInt(data[0].split(",")[0]);
                int yOffset = Integer.parseInt(data[0].split(",")[1]);
                int zOffset = Integer.parseInt(data[0].split(",")[2]);

                Block b = world.getBlockAt(anchor.getBlockX() + xOffset, anchor.getBlockY() + yOffset, anchor.getBlockZ() + zOffset);
                b.setType(type);
                b.setData(dataType);
            }

            String areaData = name + ", " + world.getName() + ", " + anchor.getBlockX() + ", " + anchor.getBlockY() + ", " + anchor.getBlockZ() + "\n";
            Files.write(Paths.get("./plugins/Workshop/areas"), areaData.getBytes(), StandardOpenOption.APPEND);
            Main.playAreas.add(new PlayArea(name, anchor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadPlayAreas() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/areas"));

            for (String line : lines) {
                String[] data = line.split(", ");
                String type = data[0];
                World world = Bukkit.getWorld(data[1]);
                int x = Integer.parseInt(data[2]);
                int y = Integer.parseInt(data[3]);
                int z = Integer.parseInt(data[4]);
                Location location = new Location(world, x, y, z);

                Main.playAreas.add(new PlayArea(type, location));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static NBTList<ReadWriteNBT> readChestItemNBT(ItemStack item) {
        NBTContainer chest_nbt = NBTItem.convertItemtoNBT(item);
        NBTCompound chest_tags = chest_nbt.getCompound("tag").getCompound("BlockEntityTag");
        return chest_tags.getCompoundList("Items");
    }

    public static ArrayList<ArrayList<Object>> readDropper(ItemStack item) {
        NBTCompound ItemNBT = NBTItem.convertItemtoNBT(item);
        if (item.getType() == Material.CHEST) {
            NBTContainer chest_nbt = NBTItem.convertItemtoNBT(item);

            if (chest_nbt.getKeys().contains("tag")) {
                NBTList<ReadWriteNBT> chestItems = readChestItemNBT(item);
                ArrayList<ArrayList<Object>> ChestItemArray = new ArrayList<>();
                for (int i = 0; i < chestItems.size(); i++) {
                    boolean needed = false;
                    ReadWriteNBT itemNBT = chestItems.get(i);
                    ArrayList<Object> itemsFormatted = new ArrayList<>();
                    if (itemNBT.getInteger("Slot") == i + 1) {
                        needed = true;
                    }
                    itemsFormatted.add(itemNBT.getString("id"));
                    itemsFormatted.add(itemNBT.getInteger("Damage"));
                    itemsFormatted.add(needed);
                    ChestItemArray.add(itemsFormatted);
                }
                return ChestItemArray;
            }
        }
        boolean needed = false;
        ArrayList<Object> itemsFormatted = new ArrayList<>();
        itemsFormatted.add(ItemNBT.getString("id"));
        itemsFormatted.add(ItemNBT.getInteger("Damage"));
        itemsFormatted.add(needed);
        ArrayList<ArrayList<Object>> itemArray = new ArrayList<>();

        itemArray.add(itemsFormatted);
        return itemArray;
    }

    public static String resourceScan(Block b, String relCoords, String type) {
        ItemStack[] items;
        if (type.equals("R")) {
            Dropper dropper = (Dropper) b.getState();
            items = dropper.getInventory().getContents();
        } else {
            Dispenser dispenser = (Dispenser) b.getState();
            items = dispenser.getInventory().getContents();
        }
        StringBuilder ItemString = new StringBuilder();
        StringBuilder NeededItemString = new StringBuilder();
        if (items[0] != null) {
            ArrayList<ArrayList<Object>> dropperItem = readDropper(items[0]);

            for (ArrayList<Object> i : dropperItem) {

                String id = (String) i.get(0);
                int data = (int) i.get(1);

                if (!(boolean) i.get(2)) {
                    if (ItemString.length() == 0) {
                        ItemString.append(id).append(" ").append(data);
                        continue;
                    }
                    ItemString.append(",").append(id).append(" ").append(data);
                    continue;
                }
                if (type.equals("R")) {
                    continue;
                }
                if (NeededItemString.length() == 0) {
                    NeededItemString.append(id).append(" ").append(data);
                    continue;
                }
                NeededItemString.append(",").append(id).append(" ").append(data);
            }
        } else {
            ItemString.append("minecraft:air 0");
        }

        StringBuilder allToolString = new StringBuilder();

        List<List<String>> allToolsList =  new ArrayList<>(new ArrayList<>());

        for (int i = 1; i < items.length; i++) {
            if (items[i] != null) {
                ArrayList<ArrayList<Object>> SlotTools = readDropper(items[i]);

                List<String> toolList = new ArrayList<>();

                for (ArrayList<Object> tool : SlotTools) {
                    toolList.add((String) tool.get(0));
                }
                allToolsList.add(toolList);
            }
        }
        for (List<String> toolList : allToolsList) {
            StringBuilder toolString = new StringBuilder();

            for (String tool : toolList) {
                toolString.append(toolList.indexOf(tool) == 0 ? "" : ",").append(tool);
            }
            allToolString.append(allToolsList.indexOf(toolList) == 0 ? "" : ";").append(toolString);
        }

        return type + ";" + relCoords + ";" + ItemString + ";" + NeededItemString + ";" + allToolString + "\n";
    }

    public static void getNeededBlocks(WSPlayer wsplayer) {
        List<ItemStack> materials = wsplayer.getCurrentCraft().getMaterials();
        String mapName = wsplayer.getPlayArea().getType();
        Location anchor = wsplayer.getPlayArea().getAnchor();
        World world = wsplayer.getPlayer().getWorld();
        List<ItemStack> materialsCopy = new ArrayList<>();
        for (ItemStack material : materials) {
            materialsCopy.add(material.clone());
        }
        
        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + mapName + ".logic"));
            Collections.shuffle(lines);

            for (String line : lines) {
                if (!(line.startsWith("P"))) { continue; }

                String[] data = line.split(";",-1);
                List<String> items = Arrays.asList((data[2] + "," + data[3]).split(","));

                int relX = Integer.parseInt(data[1].split(",")[0]);
                int relY = Integer.parseInt(data[1].split(",")[1]);
                int relZ = Integer.parseInt(data[1].split(",")[2]);

                Location relLoc = new Location(world,relX,relY,relZ);
                Location location = relLoc.add(anchor);

                boolean match = false;
                for (String item : items) {
                    String id = item.split(" ")[0];
                    int itemData = Integer.parseInt(item.split(" ")[1]);

                    ReadWriteNBT nbtObject = NBT.createNBTObject();
                    nbtObject.setString("id",id);
                    nbtObject.setInteger("Damage",itemData);
                    nbtObject.setInteger("Count",1);
                    ItemStack checkItem = NBTItem.convertNBTtoItem((NBTCompound) nbtObject);
                    for (ItemStack material : materialsCopy) {
                        if (material.getAmount() == 0 || material.getType() == Material.AIR) { continue; }

                        byte checkData = checkItem.getData().getData();
                        byte materialData = material.getData().getData();
                        Material checkType = checkItem.getType();
                        Material materialType = material.getType();

                        if (checkType != materialType || checkData != materialData) { continue; }

                        match = true;
                        int index = items.indexOf(item);

                        material.setAmount(material.getAmount() - 1);
                        wsplayer.getNeededBlocks().add(new NeededBlock(location, index, world));
                        GameUtils.getMaterialBlock(wsplayer, location).setIndex(index);
                        break;
                    }
                    if (match) { break; }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DecoBlock getDecoBlock(WSPlayer wsPlayer, Location location) {
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

    public static NeededBlock getNeededBlock(WSPlayer wsPlayer, Location location) {
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

    public static void updateDecoBlocks(WSPlayer wsplayer) {

        String mapName = wsplayer.getPlayArea().getType();
        Location anchor = wsplayer.getPlayArea().getAnchor();
        World world = wsplayer.getPlayer().getWorld();

        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + mapName + ".deco"));

            for (String line : lines) {
                String[] data = line.split(";", -1);
                String[] items = (data[1] + "," + data[2]).split(",");

                String[] rItems = data[1].split(",");

                List<Material> typeList = new ArrayList<>();
                List<Byte> dataList = new ArrayList<>();
                for (String item : items) {
                    Material itemMat = Material.getMaterial(item.split(" ")[0]);
                    Byte itemData = Byte.parseByte(item.split(" ")[1]);
                    typeList.add(itemMat);
                    dataList.add(itemData);
                }
                Material[] materials = typeList.toArray(new Material[0]);
                Byte[] matsData = dataList.toArray(new Byte[0]);

                int relX = Integer.parseInt(data[0].split(",")[0]);
                int relY = Integer.parseInt(data[0].split(",")[1]);
                int relZ = Integer.parseInt(data[0].split(",")[2]);

                Location relLoc = new Location(world,relX,relY,relZ);
                Location location = relLoc.add(anchor);

                wsplayer.getDecoBlocks().add(new DecoBlock(materials, matsData, location, world, 0));

                if (getNeededBlock(wsplayer, location) != null) {
                    int index = getNeededBlock(wsplayer, location).getIndex();
                    getDecoBlock(wsplayer, location).setIndex(index);
                    if (GameUtils.getMaterialBlock(wsplayer,location) != null) {
                        GameUtils.getMaterialBlock(wsplayer, location).setIndex(index);
                    }
                    continue;
                }
                if (materials.length > 1 && wsplayer.isInGame()) {
                    DecoBlock decoblock = getDecoBlock(wsplayer, location);
                    Random rng = new Random();
                    int randomBlockIndex = rng.nextInt(rItems.length);

                    decoblock.setIndex(randomBlockIndex);
                    MaterialBlock materialBlock = GameUtils.getMaterialBlock(wsplayer, location);
                    if (materialBlock != null) {
                        GameUtils.getMaterialBlock(wsplayer, location).setIndex(randomBlockIndex);
                    }
                }
                else if (materials.length > 1) {
                    DecoBlock materialBlock = getDecoBlock(wsplayer, location);
                    materialBlock.setTypes(new Material[]{Material.AIR});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
