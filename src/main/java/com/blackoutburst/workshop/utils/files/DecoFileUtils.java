package com.blackoutburst.workshop.utils.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DecoFileUtils {

    public static void saveFile(String mapName, Location location, String blockData, int index, boolean needed) {
        try {
            File mapFolder = new File("./plugins/Workshop/maps/" + mapName);
            mapFolder.mkdir();

            File decoFile = new File("./plugins/Workshop/maps/" + mapName + "/deco.yml");
            YamlConfiguration deco = YamlConfiguration.loadConfiguration(decoFile);
            String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
            String type = needed ? "Needed." : "Normal.";

            deco.set(type + locationString + "." + index, blockData);
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

    public static BlockData[] readFile(String mapName, Location location, boolean needed) {
        File decoFile = new File("./plugins/Workshop/maps/" + mapName + "/deco.yml");
        YamlConfiguration deco = YamlConfiguration.loadConfiguration(decoFile);
        String locationString = location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
        String type = needed ? "Needed." : "Normal.";

        ConfigurationSection blocks = (ConfigurationSection) deco.get(type + locationString);
        if (blocks == null) return new BlockData[]{};

        List<String> indexes = new ArrayList<>(blocks.getKeys(false));
        BlockData[] blockData = new BlockData[indexes.size()];

        for (int i = 0; i < indexes.size(); i++) {
            String blockDataString = (String) blocks.get(indexes.get(i));
            blockData[i] = Bukkit.createBlockData(blockDataString);
        }

        return blockData;
    }
}
