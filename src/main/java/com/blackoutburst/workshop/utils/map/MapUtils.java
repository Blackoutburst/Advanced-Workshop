package com.blackoutburst.workshop.utils.map;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.*;
import com.blackoutburst.workshop.core.blocks.*;
import com.blackoutburst.workshop.nms.NMSEntityType;
import com.blackoutburst.workshop.utils.minecraft.BlockUtils;
import com.blackoutburst.workshop.utils.misc.EntityUtils;
import com.blackoutburst.workshop.utils.misc.MiscUtils;
import de.tr7zw.nbtapi.*;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.List;

public class MapUtils {

    public static void readSigns(WSPlayer wsPlayer, String type) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + type + ".logic"));
            PlayArea area = wsPlayer.getPlayArea();

            for (String line : lines) {
                if (line.startsWith("S")) {
                    String[] data = line.split(", ");

                    switch (data[1]) {
                        case "player" -> MiscUtils.teleportPlayerToArea(wsPlayer.getPlayer(), data, area);
                        case "chicken" -> EntityUtils.spawnEntity(NMSEntityType.CHICKEN, wsPlayer, data, area);
                        case "villager" -> EntityUtils.spawnEntity(NMSEntityType.VILLAGER, wsPlayer, data, area);
                        case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" ->
                                EntityUtils.spawnEntity(NMSEntityType.ITEM_FRAME, wsPlayer, data, area);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        DecoUtils.updateBlocks(wsplayer);

        List<DecoBlock> decoBlocks = wsplayer.getDecoBlocks();
        List<Material> inventories = Arrays.asList(
                Material.CHEST, Material.TRAPPED_CHEST, Material.FURNACE,
                Material.DISPENSER, Material.DROPPER, Material.BREWING_STAND, Material.HOPPER);

        for (DecoBlock i : decoBlocks) {
            Location location = i.getLocation();
            Block b = location.getBlock();

            b.setType(i.getType());

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
                        BlockUtils.getMaterialBlock(wsplayer, location).setIndex(index);
                        break;
                    }
                    if (match) { break; }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void loadMaterials(WSPlayer wsPlayer, String type) {
        wsPlayer.getMaterialBlocks().clear();
        PlayArea area = wsPlayer.getPlayArea();
        if (area == null) return;

        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + type + ".logic"));

            for (String line : lines) {

                if ((line.startsWith("R")) || (line.startsWith("P"))) {
                    String[] data = line.split(";",-1);

                    int relX = Integer.parseInt(data[1].split(",")[0]);
                    int relY = Integer.parseInt(data[1].split(",")[1]);
                    int relZ = Integer.parseInt(data[1].split(",")[2]);

                    String[] items = data[2].split(",");
                    String[] neededItems = {};
                    List<List<String>> allTools = new ArrayList<>();

                    if (!(data[3].equals(""))) {
                        neededItems = data[3].split(",");
                    }
                    if (!(data[4].equals(""))) {
                        for (int i = 4; i < data.length; i++) {
                            List<String> toolsList = Arrays.asList(data[i].split(","));

                            allTools.add(toolsList);
                        }
                    }

                    allTools = MiscUtils.transpose2dList(allTools);

                    String[][] allToolsArray = new String[allTools.size()][];

                    for (List<String> tools : allTools) {
                        String[] toolArray = new String[tools.size()];
                        for (String tool : tools) {
                            toolArray[tools.indexOf(tool)] = tool;
                        }
                        allToolsArray[allTools.indexOf(tools)] = toolArray;
                    }

                    List<Material> allItems = new ArrayList<>();
                    List<Byte> allItemData = new ArrayList<>();

                    for (String item : items) {
                        String id = item.split(" ")[0];
                        int itemData = Integer.parseInt(item.split(" ")[1]);

                        ReadWriteNBT nbtObject = NBT.createNBTObject();
                        nbtObject.setString("id",id);
                        nbtObject.setInteger("Damage",itemData);
                        nbtObject.setInteger("Count",1);
                        ItemStack convertedItem = NBTItem.convertNBTtoItem((NBTCompound) nbtObject);
                        allItems.add(convertedItem.getType());
                        allItemData.add(convertedItem.getData().getData());
                    }
                    if ((neededItems.length != 0) && !(neededItems[0].equals(""))) {
                        for (String item : neededItems) {
                            String id = item.split(" ")[0];
                            int itemData = Integer.parseInt(item.split(" ")[1]);

                            ReadWriteNBT nbtObject = NBT.createNBTObject();
                            nbtObject.setString("id", id);
                            nbtObject.setInteger("Damage", itemData);
                            nbtObject.setInteger("Count", 1);
                            ItemStack convertedItem = NBTItem.convertNBTtoItem((NBTCompound) nbtObject);
                            allItems.add(convertedItem.getType());
                            allItemData.add(convertedItem.getData().getData());
                        }
                    }
                    Material[] itemArray = allItems.toArray(new Material[]{});
                    Byte[] dataArray = allItemData.toArray(new Byte[]{});

                    Location offset = area.getAnchor();
                    Location relLoc = new Location(area.getAnchor().getWorld(), relX, relY, relZ);
                    Location location = relLoc.add(offset);

                    wsPlayer.getMaterialBlocks().add(new MaterialBlock(itemArray, dataArray, location, location.getWorld(), allToolsArray, 0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
