package com.blackoutburst.workshop.utils.files;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.time.Instant;
import java.util.*;

public class MapFileUtils {

    public static File getMapFile(String mapName, char type) {
        return switch (type) {
            case 'D' -> new File("./plugins/Workshop/maps/" + mapName + "/deco.yml");
            case 'L' -> new File("./plugins/Workshop/maps/" + mapName + "/logic.yml");
            case 'C' -> new File("./plugins/Workshop/maps/" + mapName + "/craft.yml");
            case 'M' -> new File("./plugins/Workshop/maps/" + mapName + "/meta.yml");
            default -> null;
        };
    }

    public static Location[] getDecoNormalKeys(File f, World world) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        List<String> xKeys = new ArrayList<>();
        ConfigurationSection blocks = file.getConfigurationSection("Normal");
        blocks.getKeys(true);
        blocks.getKeys(false).forEach(x -> xKeys.add("Normal." + x));
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

    public static Location[] getDecoNeededKeys(File f, World world) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        List<String> xKeys = new ArrayList<>();

        if (!file.getKeys(false).contains("Needed")) { return new Location[]{}; }

        ConfigurationSection blocks = file.getConfigurationSection("Needed");
        blocks.getKeys(false).forEach(x -> xKeys.add("Needed." + x));

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

    public static Location[] getDecoNormalKeys(YamlConfiguration file, World world) {

        List<String> xKeys = new ArrayList<>();
        ConfigurationSection blocks = file.getConfigurationSection("Normal");
        blocks.getKeys(true);
        blocks.getKeys(false).forEach(x -> xKeys.add("Normal." + x));
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

    public static Location[] getDecoNeededKeys(YamlConfiguration file, World world) {
        List<String> xKeys = new ArrayList<>();

        if (!file.getKeys(false).contains("Needed")) { return new Location[]{}; }

        ConfigurationSection blocks = file.getConfigurationSection("Needed");
        blocks.getKeys(false).forEach(x -> xKeys.add("Needed." + x));

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


    public static Location[] getLogicLocationKeys(File f, World world, char type) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        List<String> xKeys = new ArrayList<>();
        String section;
        String resourceType = "";
        switch (type) {
            case 'S' -> section = "Signs.";
            case 'P' -> {
                section = "Resources.";
                resourceType = "Priority";
            }
            case 'B' -> {
                section = "Resources.";
                resourceType = "Both";
            }
            default -> {
                section = "Resources.";
                resourceType = "Regular";
            }
        }


        ConfigurationSection blocks = file.getConfigurationSection(section);
        blocks.getKeys(false).forEach(x -> xKeys.add(section + x));

        List<Location> locations = new ArrayList<>();

        for (String x : xKeys) {
            ConfigurationSection xSection = file.getConfigurationSection(x);
            Set<String> yValues = xSection.getKeys(false);
            for (String y : yValues) {
                ConfigurationSection ySection = xSection.getConfigurationSection(y);
                Set<String> zValues = ySection.getKeys(false);
                for (String z : zValues) {
                    ConfigurationSection zSection = ySection.getConfigurationSection(z);
                    if (zSection.contains(resourceType) || resourceType.equals("Both")) {
                        int xInt = Integer.parseInt(x.split("[.]")[1]);
                        int yInt = Integer.parseInt(y);
                        int zInt = Integer.parseInt(z);
                        locations.add(new Location(world, xInt, yInt, zInt));
                    }
                }
            }
        }
        return locations.toArray(new Location[]{});
    }

    public static String[] getAllKeys(ConfigurationSection section) {
        return (section == null) ? new String[]{} : section.getKeys(false).toArray(new String[]{});
    }
    public static String[] getAllKeys(File f) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        return file.getKeys(false).toArray(new String[]{});
    }

    public static ConfigurationSection getConfigSection(File f, String path) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        if (!file.isConfigurationSection(path)) return null;
        return file.getConfigurationSection(path);
    }

    public static ConfigurationSection getConfigSection(File f, Location location, char type) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);

        switch (type) {
            case 'L' -> {
                String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
                String path = "Resources." + locationString;
                return file.getConfigurationSection(path);
            }
            case 'R' -> {
                String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
                String path = "Normal." + locationString;
                return file.getConfigurationSection(path);
            }
            case 'P' -> {
                String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
                String path = "Needed." + locationString;
                return file.getConfigurationSection(path);
            }
            case 'S' -> {
                String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
                String path = "Signs." + locationString;
                return file.getConfigurationSection(path);
            }
            default -> {
                return null;
            }
        }
    }

    public static ConfigurationSection getConfigSection(ConfigurationSection c, String path) {
        return c.getConfigurationSection(path);
    }

    public static File[] getAllMaps() {
        File f = new File("./plugins/Workshop/maps/");
        return f.listFiles();
    }

}
