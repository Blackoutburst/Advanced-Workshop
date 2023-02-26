package com.blackoutburst.workshop.utils.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class FileReader {

    public static File getFileByMap(String mapName, char type) {
        return switch (type) {
            case 'D' -> new File("./plugins/Workshop/maps/" + mapName + "/deco.yml");
            case 'L' -> new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
            case 'C' -> new File("./plugins/Workshop/maps/" + mapName + "/craft.yml");
            default -> null;
        };
    }

    public static String[] getAllKeys(File f) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        return file.getKeys(false).toArray(new String[]{});
    }

    public static Location[] getDecoLocationKeys(File f, World world) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        List<String> xKeys = new ArrayList<>();
        ConfigurationSection normalBlocks = file.getConfigurationSection("Normal");
        normalBlocks.getKeys(false).forEach(x -> xKeys.add("Normal." + x));

        if (file.getKeys(false).contains("Needed")) {
            ConfigurationSection neededBlocks = file.getConfigurationSection("Needed");
            neededBlocks.getKeys(false).forEach(x -> xKeys.add("Needed." + x));
        }
        List<Location> locations = new ArrayList<>();

        for (String x : xKeys) {
            Set<String> yValues = file.getConfigurationSection(x).getKeys(false);
            for (String y : yValues) {
                Set<String> zValues = file.getConfigurationSection(x).getConfigurationSection(y).getKeys(false);
                for (String z : zValues) {
                    int xInt = Integer.parseInt(x.split("[.]")[1]);
                    int yInt = Integer.parseInt(y);
                    int zInt = Integer.parseInt(z);
                    locations.add(new Location(world, xInt, yInt, zInt));
                }
            }
        }

        Set<Location> locationSet = new HashSet<>(locations);
        return locationSet.toArray(new Location[]{});
    }

    public static Location[] getLogicLocationKeys(File f, World world, char type) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        List<String> xKeys = new ArrayList<>();
        String section = (type == 'S') ? "Signs." : "Resources.";

        ConfigurationSection blocks = file.getConfigurationSection(section);
        blocks.getKeys(false).forEach(x -> xKeys.add(section + x));

        List<Location> locations = new ArrayList<>();

        for (String x : xKeys) {
            Set<String> yValues = file.getConfigurationSection(x).getKeys(false);
            for (String y : yValues) {
                Set<String> zValues = file.getConfigurationSection(x).getConfigurationSection(y).getKeys(false);
                for (String z : zValues) {
                    int xInt = Integer.parseInt(x.split("[.]")[1]);
                    int yInt = Integer.parseInt(y);
                    int zInt = Integer.parseInt(z);
                    locations.add(new Location(world, xInt, yInt, zInt));
                }
            }
        }
        return locations.toArray(new Location[]{});
    }

    public static String[] getAllKeys(ConfigurationSection section) {
        return (section == null) ? new String[]{} : section.getKeys(false).toArray(new String[]{});
    }

    public static ConfigurationSection getConfigSection(File f, String path) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        if (!file.isConfigurationSection(path)) return null;
        return file.getConfigurationSection(path);
    }

    public static ConfigurationSection getConfigSection(File f, Location location, char type) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        if (type == 'L') {
            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
            String path = "Resources." + locationString;
            return file.getConfigurationSection(path);
        }
        if (type == 'R') {
            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
            String path = "Normal." + locationString;
            return file.getConfigurationSection(path);
        }
        if (type == 'P') {
            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
            String path = "Needed." + locationString;
            return file.getConfigurationSection(path);
        }
        if (type == 'S') {
            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
            String path = "Signs." + locationString;
            return file.getConfigurationSection(path);
        }
        return null;
    }

    public static ConfigurationSection getConfigSection(ConfigurationSection c, String path) {
        return c.getConfigurationSection(path);
    }
}
