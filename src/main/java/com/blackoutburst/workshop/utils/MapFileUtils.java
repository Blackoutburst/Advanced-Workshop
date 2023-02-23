package com.blackoutburst.workshop.utils;

import com.blackoutburst.workshop.nms.NMSEnumDirection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapFileUtils {
    public static void saveDecoFile(String mapName, Location location, String blockData, int index) {
        try {
            File mapFolder = new File("./plugins/Workshop/maps/" + mapName);
            mapFolder.mkdir();

            File decoFile = new File("./plugins/Workshop/maps/" + mapName + "/deco.yml");
            YamlConfiguration deco = YamlConfiguration.loadConfiguration(decoFile);
            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

            deco.set(locationString + "." + index, blockData);
            deco.save(decoFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDecoFile(String mapName) {
        File mapFolder = new File("./plugins/Workshop/maps/" + mapName);

        if (!mapFolder.mkdir()) {
            File decoFile = new File("./plugins/Workshop/maps/" + mapName + "/deco.yml");
            decoFile.delete();
        }
    }

    public static BlockData[] readDecoFile(String mapName, Location location) {
        File decoFile = new File("./plugins/Workshop/maps/" + mapName + "/deco.yml");
        YamlConfiguration deco = YamlConfiguration.loadConfiguration(decoFile);
        String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

        ConfigurationSection blocks = (ConfigurationSection) deco.get(locationString);
        List<String> indexes = new ArrayList<>(blocks.getKeys(false));
        BlockData[] blockData = new BlockData[indexes.size()];

        for (int i = 0; i < indexes.size(); i++) {
            String blockDataString = (String) blocks.get(indexes.get(i));
            blockData[i] = Bukkit.createBlockData(blockDataString);
        }

        return blockData;
    }

    public static void saveLogicFileItems(String mapName, Location location, ItemStack item, int index, String type) {
        try {
            File mapFolder = new File("./plugins/Workshop/maps/" + mapName);
            mapFolder.mkdir();

            File logicFile = new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
            YamlConfiguration logic = YamlConfiguration.loadConfiguration(logicFile);
            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

            logic.set("Resources." + locationString + "." + type + "." + index, item);
            logic.save(logicFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveLogicFileTools(String mapName, Location location, ItemStack item, int index, int toolNum) {
        try {
            File mapFolder = new File("./plugins/Workshop/maps/" + mapName);
            mapFolder.mkdir();

            File logicFile = new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
            YamlConfiguration logic = YamlConfiguration.loadConfiguration(logicFile);
            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

            logic.set("Resources." + locationString + ".Tools." + index + "." + toolNum, item);
            logic.save(logicFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveLogicFileSigns(String mapName, Location location, String text, BlockFace direction) {
        try {
            File mapFolder = new File("./plugins/Workshop/maps/" + mapName);
            mapFolder.mkdir();

            File logicFile = new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
            YamlConfiguration logic = YamlConfiguration.loadConfiguration(logicFile);
            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

            logic.set("Signs." + locationString + ".text", text);
            logic.set("Signs." + locationString + ".direction", direction);
            logic.save(logicFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteLogicFile(String mapName) {
        File mapFolder = new File("./plugins/Workshop/maps/" + mapName);

        if (!mapFolder.mkdir()) {
            File decoFile = new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
            decoFile.delete();
        }
    }

    public static ItemStack[] readLogicFileItem(String mapName, Location location, String type) {
        File logicFile = new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
        YamlConfiguration logic = YamlConfiguration.loadConfiguration(logicFile);
        String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

        ConfigurationSection items = (ConfigurationSection) logic.get("Resources." + locationString + "." + type);
        if (items == null) return new ItemStack[]{};

        List<String> indexes = new ArrayList<>(items.getKeys(false));
        ItemStack[] itemStacks = new ItemStack[indexes.size()];


        for (int i = 0; i < indexes.size(); i++) {
            itemStacks[i] = (ItemStack) items.get(indexes.get(i));
        }

        return itemStacks;
    }

    public static ItemStack[] readLogicFileTools(String mapName, Location location, int index) {
        File logicFile = new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
        YamlConfiguration logic = YamlConfiguration.loadConfiguration(logicFile);
        String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

        ConfigurationSection items = (ConfigurationSection) logic.get("Resources." + locationString + ".Tools." + index);
        if (items == null) return new ItemStack[]{};

        List<String> toolNums = new ArrayList<>(items.getKeys(false));
        ItemStack[] itemStacks = new ItemStack[toolNums.size()];

        for (int i = 0; i < toolNums.size(); i++) {
            itemStacks[i] = (ItemStack) items.get(toolNums.get(i));
        }

        return itemStacks;
    }
}
