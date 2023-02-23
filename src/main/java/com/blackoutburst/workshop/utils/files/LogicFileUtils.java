package com.blackoutburst.workshop.utils.files;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogicFileUtils {

    public static void saveFileItems(String mapName, Location location, ItemStack item, int index, String type) {
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
    public static void saveFileTools(String mapName, Location location, ItemStack item, int index, int toolNum) {
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
    public static void saveFileSigns(String mapName, Location location, String text, BlockFace direction) {
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

    public static void deleteFile(String mapName) {
        File mapFolder = new File("./plugins/Workshop/maps/" + mapName);

        if (!mapFolder.mkdir()) {
            File decoFile = new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
            decoFile.delete();
        }
    }

    public static ItemStack[] readFileItem(String mapName, Location location, String type) {
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

    public static ItemStack[] readFileTools(String mapName, Location location, int index) {
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
