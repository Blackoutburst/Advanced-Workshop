package com.blackoutburst.workshop.utils;

import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class DBUtils {

    public static void createPlayerData(Player player) {
        try {
            File f = new File("./plugins/Quake/player data/"+player.getUniqueId().toString().replace("-", "")+".yml");

            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
