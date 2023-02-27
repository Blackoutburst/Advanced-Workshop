package com.blackoutburst.workshop.utils.files;

import com.blackoutburst.workshop.core.blocks.LogicSign;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            logic.set("Signs." + locationString + ".direction", direction.toString());
            logic.save(logicFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String mapName) {
        File mapFolder = new File("./plugins/Workshop/maps/" + mapName);

        if (!mapFolder.mkdir()) {
            File logicFile = new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
            logicFile.delete();
        }
    }

    public static ItemStack[][] readFileItem(String mapName, Location[] locations, String type) {
        File logicFile = new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
        YamlConfiguration logic = YamlConfiguration.loadConfiguration(logicFile);
        ItemStack[][] allItemStacks = new ItemStack[locations.length][];

        for (int i = 0; i < locations.length; i++) {
            Location location = locations[i];

            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

            ConfigurationSection items = (ConfigurationSection) logic.get("Resources." + locationString + "." + type);
            if (items == null) {
                allItemStacks[i] = new ItemStack[0];
                continue;
            }
            List<String> indexes = new ArrayList<>(items.getKeys(false));
            ItemStack[] itemStacks = new ItemStack[indexes.size()];
            for (int j = 0; j < indexes.size(); j++) {
                itemStacks[j] = (ItemStack) items.get(indexes.get(j));
            }
            allItemStacks[i] = itemStacks;
        }
        return allItemStacks;
    }

    public static ItemStack[][] readFileItem(String mapName, Location[] locations) {
        File logicFile = new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
        YamlConfiguration logic = YamlConfiguration.loadConfiguration(logicFile);

        ItemStack[][] allItems = new ItemStack[locations.length][];

        for (int i = 0; i < locations.length; i++) {
            Location location = locations[i];
            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

            ConfigurationSection items = (ConfigurationSection) logic.get("Resources." + locationString);
            ItemStack[] itemStacks = new ItemStack[0];

            if (items.contains("Regular")) {
                ConfigurationSection section = items.getConfigurationSection("Regular");
                Set<String> indexes = section.getKeys(false);
                for (String index : indexes) {
                    ItemStack item = section.getItemStack(index);
                    itemStacks = (ItemStack[]) ArrayUtils.add(itemStacks, item);
                }
            }
            if (items.contains("Priority")) {
                ConfigurationSection section = items.getConfigurationSection("Priority");
                Set<String> indexes = section.getKeys(false);
                for (String index : indexes) {
                    ItemStack item = section.getItemStack(index);
                    itemStacks = (ItemStack[]) ArrayUtils.add(itemStacks, item);
                }
            }
            allItems[i] = itemStacks;
        }
        return allItems;
    }

    public static ItemStack[][][] readFileTools(String mapName, Location[] locations) {
        ItemStack[][][] allTools = new ItemStack[locations.length][][];
        File logicFile = new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
        YamlConfiguration logic = YamlConfiguration.loadConfiguration(logicFile);

        for (int i = 0; i < locations.length; i++) {
            Location location = locations[i];
            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

            ConfigurationSection locTools = logic.getConfigurationSection("Resources." + locationString + ".Tools");
            if (locTools == null) {
                allTools[i] = new ItemStack[0][];
                continue;
            }
            Set<String> indexes = locTools.getKeys(false);
            ItemStack[][] locToolItems = new ItemStack[indexes.size()][];

            for (int j = 0; j < indexes.size(); j++) {
                String index = indexes.toArray(new String[0])[j];
                ConfigurationSection indexTools = locTools.getConfigurationSection(index);
                if (indexTools == null) {
                    locToolItems[j] = new ItemStack[0];
                    continue;
                }
                Set<String> toolNums = indexTools.getKeys(false);
                ItemStack[] indexToolItems = new ItemStack[toolNums.size()];

                for (int k = 0; k < toolNums.size(); k++) {
                    String toolNum = toolNums.toArray(new String[0])[k];
                    ItemStack tool = indexTools.getItemStack(toolNum);
                    indexToolItems[k] = tool;
                }
                locToolItems[j] = indexToolItems;
            }
            allTools[i] = locToolItems;
        }
        return allTools;
    }

    public static LogicSign readSigns(String mapName, Location location) {
        File logicFile = new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
        YamlConfiguration logic = YamlConfiguration.loadConfiguration(logicFile);
        String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

        String text = logic.getString("Signs." + locationString + ".text");
        BlockFace direction = BlockFace.valueOf(logic.getString("Signs." + locationString + ".direction"));

        return new LogicSign(text, direction, location);
    }

}
