package com.blackoutburst.workshop.utils.map;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.*;
import com.blackoutburst.workshop.core.blocks.*;
import com.blackoutburst.workshop.nms.NMSEntityType;
import com.blackoutburst.workshop.utils.files.DecoFileUtils;
import com.blackoutburst.workshop.utils.files.MapFileUtils;
import com.blackoutburst.workshop.utils.files.LogicFileUtils;
import com.blackoutburst.workshop.utils.minecraft.BlockUtils;
import com.blackoutburst.workshop.utils.minecraft.EntityUtils;
import com.blackoutburst.workshop.utils.misc.MiscUtils;
import de.tr7zw.nbtapi.*;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.List;

public class MapUtils {

    public static void readSigns(WSPlayer wsPlayer, String type) {
        try {
            PlayArea area = wsPlayer.getPlayArea();
            World world = wsPlayer.getPlayer().getWorld();
            File mapFile = MapFileUtils.getMapFile(type, 'L');
            YamlConfiguration mapConfig = YamlConfiguration.loadConfiguration(mapFile);

            Location[] signLocations = MapFileUtils.getLogicLocationKeys(mapFile, world, 'S');
            LogicSign[] signs = new LogicSign[signLocations.length];
            for (int i = 0; i < signLocations.length; i++) {
                Location signLocation = signLocations[i];
                signs[i] = LogicFileUtils.readSigns(mapConfig, signLocation);
            }

            for (LogicSign sign : signs) {
                BlockFace direction = sign.getDirection();
                Location location = sign.getLocation();

                switch (sign.getText()) {
                    case "player": MiscUtils.teleportPlayerToArea(wsPlayer.getPlayer(), location, direction, area); break;
                    case "chicken": EntityUtils.spawnEntity(NMSEntityType.CHICKEN, wsPlayer, location, direction, sign.getText(), area); break;
                    case "villager": EntityUtils.spawnEntity(NMSEntityType.VILLAGER, wsPlayer, location, direction, sign.getText(), area); break;
                    case "0":
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9":
                                EntityUtils.spawnEntity(NMSEntityType.ITEM_FRAME, wsPlayer, location, direction, sign.getText(), area); break;
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

            b.setBlockData(i.getType());

            if (!clear_inventories) continue;

            if (inventories.contains(i.getType().getMaterial())) {
                InventoryHolder container = (InventoryHolder) b.getState();
                container.getInventory().clear();
            }
        }
    }

    public static void pasteMap(WSPlayer wsplayer, String name) {
        try {
            Player player = wsplayer.getPlayer();
            World world = player.getWorld();
            Location anchor = player.getLocation();

            File decoFile = MapFileUtils.getMapFile(name, 'D');
            Location[] keys = MapFileUtils.getDecoNormalKeys(decoFile, world);

            BlockData[][] allBlocks = DecoFileUtils.readFile(name, keys, false);

            for (int i = 0; i < allBlocks.length; i++) {
                BlockData[] blocks = allBlocks[i];
                Location location = keys[i];
                location.add(anchor);

                if (blocks.length > 1) {
                    location.getBlock().setType(Material.AIR);
                    continue;
                }
                location.getBlock().setBlockData(blocks[0]);
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
            File logicFile = MapFileUtils.getMapFile(mapName, 'L');
            List<Location> keyList = Arrays.asList(MapFileUtils.getLogicLocationKeys(logicFile, world, 'P'));
            Collections.shuffle(keyList);
            Location[] keys = keyList.toArray(new Location[0]);
            ItemStack[][] allNeeded = LogicFileUtils.readFileItem(mapName, keys, "Priority");

            for (int i = 0; i < keys.length; i++) {
                Location key = keys[i];
                ItemStack[] needed = allNeeded[i];
                if (needed == null) continue;
                Location location = key.add(anchor);

                boolean match = false;
                for (int j = 0; j < needed.length; j++) {
                    ItemStack item = needed[j];
                    for (ItemStack material : materialsCopy) {
                        if (material.getAmount() == 0 || material.getType() == Material.AIR) { continue; }
                        Material checkType = item.getType();
                        Material materialType = material.getType();
                        if (checkType != materialType) { continue; }

                        match = true;
                        material.setAmount(material.getAmount() - 1);
                        wsplayer.getNeededBlocks().add(new NeededBlock(location, j, world));
                        BlockUtils.getMaterialBlock(wsplayer, location).setIndex(j);
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
        Location anchor = area.getAnchor();
        World world = wsPlayer.getPlayer().getWorld();

        try {
            File logicFile = MapFileUtils.getMapFile(type, 'L');
            Location[] keys = MapFileUtils.getLogicLocationKeys(logicFile, world, 'B');
            ItemStack[][] allItems = LogicFileUtils.readFileItem(type, keys);
            ItemStack[][][] allTools = LogicFileUtils.readFileTools(type, keys);

            for (int i = 0; i < allItems.length; i++) {
                ItemStack[] items = allItems[i];
                Location key = keys[i];
                ItemStack[][] tools = allTools[i];

                if (items == null) items = new ItemStack[]{};

                Material[] itemMaterials = new Material[items.length];

                for (int j = 0; j < items.length; j++) {
                    ItemStack item = items[j];
                    itemMaterials[j] = item.getType();
                }

                Location location = key.add(anchor);
                wsPlayer.getMaterialBlocks().add(new MaterialBlock(itemMaterials, location, world, tools, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
