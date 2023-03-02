package com.blackoutburst.workshop.utils.files;

import com.blackoutburst.workshop.core.Craft;
import com.blackoutburst.workshop.utils.misc.MiscUtils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        List<Object> keys = new ArrayList<>();
        Object[] tmp = mats.getKeys(false).stream().toArray();
        keys.addAll(List.of(tmp));
        ItemStack[] result = new ItemStack[keys.size()];

        for (int i = 0; i < keys.size(); i++) {
            result[i] = mats.getItemStack((String)keys.get(i));
        }
        return result;
    }

    public static Craft[] readCraftFile(String mapName) {
        File craftFile = new File("./plugins/Workshop/maps/" + mapName + "/craft.yml");
        YamlConfiguration craftConfig = YamlConfiguration.loadConfiguration(craftFile);
        String[] craftKeys = craftConfig.getKeys(false).toArray(new String[0]);
        Craft[] crafts = new Craft[craftKeys.length];
        for (int i = 0; i < craftKeys.length; i++) {
            String craftKey = craftKeys[i];
            ConfigurationSection rawMatSection = craftConfig.getConfigurationSection(craftKey + ".rawMats");
            ConfigurationSection craftMatSection = craftConfig.getConfigurationSection(craftKey + ".craftMats");
            if (rawMatSection == null || craftMatSection == null) continue;

            Material resultMaterial = Material.getMaterial(craftKey);
            if (resultMaterial == null) continue;

            ItemStack craftResult = new ItemStack(resultMaterial);
            String resultName = MiscUtils.getItemName(resultMaterial);

            Set<String> rawMatKeys = rawMatSection.getKeys(false);
            String[] craftMatKeys = craftMatSection.getKeys(false).toArray(new String[0]);

            ItemStack[] craftMats = new ItemStack[craftMatKeys.length];
            List<ItemStack> rawMats = new ArrayList<>();
            for (int j = 0; j < craftMatKeys.length; j++) {
                String craftMatKey = craftMatKeys[j];
                craftMats[j] = craftMatSection.getItemStack(craftMatKey);
            }
            for (String rawMatKey : rawMatKeys) {
                rawMats.add(rawMatSection.getItemStack(rawMatKey));
            }
            Craft craft = new Craft(resultName, craftResult, craftMats, rawMats);
            crafts[i] = craft;
        }
        return crafts;
    }

    public static void deleteCraft(String mapName, String craftResult) {
        try {
            File craftFile = new File("./plugins/Workshop/maps/" + mapName + "/craft.yml");
            YamlConfiguration craft = YamlConfiguration.loadConfiguration(craftFile);

            craft.set(craftResult, null);
            craft.save(craftFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
