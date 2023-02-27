package com.blackoutburst.workshop.utils.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DecoFileUtils {

    public static void saveFile(String mapName, List<Location> locations, List<List<String>> allBlockData, List<List<Boolean>> needed) {
        try {
            File mapFolder = new File("./plugins/Workshop/maps/" + mapName);
            mapFolder.mkdir();

            File decoFile = new File("./plugins/Workshop/maps/" + mapName + "/deco.yml");
            YamlConfiguration deco = YamlConfiguration.loadConfiguration(decoFile);

            for (int i = 0; i < locations.size(); i++) {
                Location location = locations.get(i);
                List<String> locBlockData = allBlockData.get(i);
                String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

                for (int j = 0; j < locBlockData.size(); j++){
                    String type = needed.get(i).get(j) ? "Needed." : "Normal.";
                    String blockData = locBlockData.get(j);
                    deco.set(type + locationString + "." + j, blockData);
                }
            }
            deco.save(decoFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String mapName) {
        File mapFolder = new File("./plugins/Workshop/maps/" + mapName);

        if (!mapFolder.mkdir()) {
            File decoFile = new File("./plugins/Workshop/maps/" + mapName + "/deco.yml");
            decoFile.delete();
        }
    }

    public static BlockData[][] readFile(String mapName, Location[] locations, boolean needed) {
        File decoFile = new File("./plugins/Workshop/maps/" + mapName + "/deco.yml");
        YamlConfiguration deco = YamlConfiguration.loadConfiguration(decoFile);

        BlockData[][] blockData = new BlockData[locations.length][];
        String type = needed ? "Needed." : "Normal.";



        for (int i = 0; i < locations.length; i++) {
            Location location = locations[i];
            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
            ConfigurationSection blocks = (ConfigurationSection) deco.get(type + locationString);
            if (blocks == null) return new BlockData[][]{};

            List<String> indexes = new ArrayList<>(blocks.getKeys(false));
            BlockData[] locBlockData = new BlockData[indexes.size()];

            for (int j = 0; j < indexes.size(); j++) {
                String blockDataString = (String) blocks.get(indexes.get(j));
                locBlockData[j] = Bukkit.createBlockData(blockDataString);
            }
            blockData[i] = locBlockData;
        }

        return blockData;
    }

    public static BlockData[][] readFile(YamlConfiguration deco, Location[] locations, boolean needed) {
        BlockData[][] blockData = new BlockData[locations.length][];
        String type = needed ? "Needed." : "Normal.";



        for (int i = 0; i < locations.length; i++) {
            Location location = locations[i];
            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
            ConfigurationSection blocks = (ConfigurationSection) deco.get(type + locationString);
            if (blocks == null) return new BlockData[][]{};

            List<String> indexes = new ArrayList<>(blocks.getKeys(false));
            BlockData[] locBlockData = new BlockData[indexes.size()];

            for (int j = 0; j < indexes.size(); j++) {
                String blockDataString = (String) blocks.get(indexes.get(j));
                locBlockData[j] = Bukkit.createBlockData(blockDataString);
            }
            blockData[i] = locBlockData;
        }

        return blockData;
    }
}
