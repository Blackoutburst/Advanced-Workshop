package com.blackoutburst.workshop.utils.files;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

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
}
