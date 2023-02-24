package com.blackoutburst.workshop.utils.files;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CraftFileUtils {

    public static void saveFileCraft(String mapName, ItemStack item, ItemStack[] craftMats, ItemStack[] rawMats ) {
        try {
            File mapFolder = new File("./plugins/Workshop/maps/" + mapName);
            mapFolder.mkdir();

            File craftFile = new File("./plugins/Workshop/maps/" + mapName + "/craft.yml");
            YamlConfiguration craft = YamlConfiguration.loadConfiguration(craftFile);

            String item_name = item.getType().toString();

            for (int i = 0 ; i < craftMats.length; i++) {
                ItemStack craftMat =  craftMats[i];
                craft.set(item_name + ".craftMats." + i, craftMat);
            }
            for (int i = 0 ; i < rawMats.length; i++) {
                ItemStack rawMat =  rawMats[i];
                craft.set(item_name + ".rawMats." + i, rawMat);
            }
            craft.save(craftFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String mapName) {
        File mapFolder = new File("./plugins/Workshop/maps/" + mapName);

        if (!mapFolder.mkdir()) {
            File craftFile = new File("./plugins/Workshop/maps/" + mapName + "/craft.yml");
            craftFile.delete();
        }
    }

    public static ItemStack[] readCraftFile(String mapName, String craftResult, char type) {

        File craftFile = new File("./plugins/Workshop/maps/" + mapName + "/craft.yml");
        YamlConfiguration craft = YamlConfiguration.loadConfiguration(craftFile);
        String typeString = (type == 'R') ? "rawMats" : "craftMats";

        ConfigurationSection mats = craft.getConfigurationSection(craftResult + "." + typeString);
        List<String> keys = craft.getKeys(false).stream().toList();
        ItemStack[] result = new ItemStack[keys.size()];

        for (int i = 0; i < keys.size(); i++) {
            result[i] = mats.getItemStack(keys.get(i));
        }
        return result;
    }
}
