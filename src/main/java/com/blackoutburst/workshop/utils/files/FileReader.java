package com.blackoutburst.workshop.utils.files;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

    public static Location[] getLocationKeys(File f, World world) {
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
        return locations.toArray(new Location[]{});
    }

    public static String[] getAllKeys(ConfigurationSection section) {
        return section.getKeys(false).toArray(new String[]{});
    }

    public static ConfigurationSection getConfigSection(File f, String path) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        if (!file.isConfigurationSection(path)) return null;
        return file.getConfigurationSection(path);
    }
}
