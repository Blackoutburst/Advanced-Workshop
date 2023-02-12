package com.blackoutburst.workshop.utils;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.*;
import com.blackoutburst.workshop.utils.GameUtils;
import com.blackoutburst.workshop.Craft;
import de.tr7zw.nbtapi.*;
import de.tr7zw.nbtapi.data.NBTData;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import de.tr7zw.nbtapi.plugin.tests.blocks.BlockNBTTest;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerDispenser;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.minecraft.server.v1_8_R3.CommandTitle.a;

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
                            blocks = readDropper(dispenser.getInventory().getContents()[0]);
                        }
                        if (b.getType().equals(Material.DROPPER)) {
                            Dropper dropper = (Dropper) b.getState();
                            blocks = readDropper(dropper.getInventory().getContents()[0]);
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

    public static <JsonArray> void logicScan(WSPlayer wsplayer, String name) {
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

    public static void restoreArea(WSPlayer wsplayer) {
        setupNeededBlocks(wsplayer);

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

        StringBuilder tools = new StringBuilder();
        for (int i = 1; i < items.length; i++) {
            if (items[i] != null) {
                NBTCompound ToolNBT = NBTItem.convertItemtoNBT(items[i]);
                String ToolID = ToolNBT.getString("id");
                if (i == 1) {
                    tools.append(ToolID);
                    continue;
                }
                tools.append(",").append(ToolID);
            }
        }
        return type + ";" + relCoords + ";" + ItemString + ";" + NeededItemString + ";" + tools + "\n";
    }

    public static NeededBlock[] getNeededBlocks(WSPlayer wsplayer) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + wsplayer.getPlayArea().getType() + ".logic"));
            Player player = wsplayer.getPlayer();
            World world = player.getWorld();
            Location anchor = wsplayer.getPlayArea().getAnchor();

            Craft craft = wsplayer.getCurrentCraft();
            List<ItemStack> materials = new ArrayList<>();
            if (craft != null) {
                materials = craft.getMaterials();
            }
            List<NeededBlock> NeededBlockList = new ArrayList<>();
            List<Object[]> mats = new ArrayList<>();

            for (String line : lines) {
                if (line.startsWith("P")) {
                    String[] data = line.split(";");
                    List<String> itemList = new ArrayList<>();
                    Collections.addAll(itemList, data[2].split(","));
                    Collections.addAll(itemList, data[3].split(","));

                    String[] items = itemList.toArray(new String[0]);

                    for (int i = 0; i < items.length; i++) {
                        String item = items[i];
                        ReadWriteNBT nbtObject = NBT.createNBTObject();
                        nbtObject.setString("id", item.split(" ")[0]);
                        nbtObject.setInteger("Damage", Integer.parseInt(item.split(" ")[1]));
                        nbtObject.setInteger("Count", 1);
                        ItemStack mat = NBTItem.convertNBTtoItem((NBTCompound) nbtObject);
                        int x = Integer.parseInt(data[1].split(",")[0]) + anchor.getBlockX();
                        int y = Integer.parseInt(data[1].split(",")[1]) + anchor.getBlockY();
                        int z = Integer.parseInt(data[1].split(",")[2]) + anchor.getBlockZ();

                        Object[] matList = {mat, x, y, z, i};

                        mats.add(matList);
                    }

                    for (ItemStack item : materials) {
                        if (item.getAmount() > 0 && item.getType() != Material.AIR) {
                            int targetX;
                            int targetY;
                            int targetZ;
                            int targetIndex;
                            for (Object[] i : mats) {
                                ItemStack mat = (ItemStack) i[0];

                                if (mat.getType() == item.getType()) {
                                    item.setAmount(item.getAmount() - 1);

                                    targetX = (int) i[1];
                                    targetY = (int) i[2];
                                    targetZ = (int) i[3];
                                    targetIndex = (int) i[4];
                                    mats.clear();

                                    Location location = new Location(world, targetX, targetY, targetZ);

                                    NeededBlockList.add(new NeededBlock(location,targetIndex));

                                    break;
                                }

                            }
                        }
                    }
                }
            }
            for (NeededBlock test : NeededBlockList) {
                Bukkit.broadcastMessage(test.getLocation().toString());
                Bukkit.broadcastMessage(Arrays.toString(mats.get(test.getIndex())));
            }

            return NeededBlockList.toArray(new NeededBlock[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new NeededBlock[0];
    }
    public static void setupNeededBlocks(WSPlayer wsplayer) {
        if (wsplayer.getPlayArea() == null) return;

        World world = wsplayer.getPlayer().getWorld();
        Location anchor = wsplayer.getPlayArea().getAnchor();

        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + wsplayer.getPlayArea().getType() + ".deco"));

            NeededBlock[] neededBlocks = getNeededBlocks(wsplayer);

            List<Location> locations = new ArrayList<>();
            List<Integer> indexes = new ArrayList<>();

            for (NeededBlock neededBlock : neededBlocks) {
                locations.add(neededBlock.getLocation());
                indexes.add(neededBlock.getIndex());
            }

            for (String line : lines) {
                String[] data = line.split(";",-1);

                String[] RBlockTypes = data[1].split(",");
                String[] PBlockTypes = data[2].split(",");
                String[] blockType;

                List<String> blockTypeList = new ArrayList<>();

                Collections.addAll(blockTypeList, RBlockTypes);
                Collections.addAll(blockTypeList, PBlockTypes);

                int xOffset = Integer.parseInt(data[0].split(",")[0]);
                int yOffset = Integer.parseInt(data[0].split(",")[1]);
                int zOffset = Integer.parseInt(data[0].split(",")[2]);

                Location currentLoc = new Location(world,anchor.getBlockX() + xOffset,anchor.getBlockY() + yOffset,anchor.getBlockZ() + zOffset);
                Block b = world.getBlockAt(currentLoc);

                MaterialBlock material = GameUtils.getMaterialBlock(wsplayer, currentLoc);

                if ((blockTypeList.size() > 1) && (locations.contains(currentLoc))) {
                    int locIndex = locations.indexOf(currentLoc);
                    int index = indexes.get(locIndex);

                    material.setIndex(index);
                    blockType = blockTypeList.get(index).split(" ");
                    Material type = Material.getMaterial(blockType[0]);
                    byte dataType = Byte.parseByte(blockType[1]);

                    b.setType(type);
                    b.setData(dataType);
                }
                else {
                    blockType = blockTypeList.get(0).split(" ");
                    Material type = Material.getMaterial(blockType[0]);
                    byte dataType = Byte.parseByte(blockType[1]);
                    if (material != null) {
                        material.setIndex(0);
                    }
                    b.setType(type);
                    b.setData(dataType);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
