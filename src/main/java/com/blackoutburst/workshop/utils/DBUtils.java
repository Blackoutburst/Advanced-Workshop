package com.blackoutburst.workshop.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class DBUtils {

    public static void saveData(Player player, String field, Object value, Class<?> type) {
        try {
            File f = new File("./plugins/Workshop/playerData/"+player.getUniqueId().toString().replace("-", "")+".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(f);

            config.set(field, type.cast(value));
            config.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getData(Player player, String field, Class<T> type) {
        try {
            File f = new File("./plugins/Workshop/playerData/"+player.getUniqueId().toString().replace("-", "")+".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(f);

            return type.cast(config.get(field));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createPlayerData(Player player) {
        try {
            File f = new File("./plugins/Workshop/playerData/"+player.getUniqueId().toString().replace("-", "")+".yml");
            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
