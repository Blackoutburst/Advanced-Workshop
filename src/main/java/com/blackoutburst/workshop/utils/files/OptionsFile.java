package com.blackoutburst.workshop.utils.files;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.game.GameOptions;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class OptionsFile {

    public static void save(UUID uuid, GameOptions gameOptions) {
        try {
            File file = new File("./plugins/Workshop/options/" + uuid.toString() + ".yml");
            YamlConfiguration options = YamlConfiguration.loadConfiguration(file);

            options.set("bagSize", gameOptions.getBagSize());
            options.set("craftLimit", gameOptions.getDefaultCraftLimit());
            options.set("countDownTime", gameOptions.getCountDownTime());
            options.set("rngType", String.valueOf(gameOptions.getRandomType()));
            options.set("timeLimit", (double) gameOptions.getDefaultTimeLimit());
            options.set("unlimitedCrafts", gameOptions.isUnlimitedCrafts());

            options.save(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GameOptions load(UUID uuid) {
        File file = new File("./plugins/Workshop/options/" + uuid.toString() + ".yml");
        YamlConfiguration options = YamlConfiguration.loadConfiguration(file);

        if(options.getKeys(false).size() == 0) return null;

        int bagSize = options.getInt("bagSize");
        int craftLimit = options.getInt("craftLimit");
        int countDownTime = options.getInt("countDownTime");
        char rngType = options.getString("rngType").charAt(0);
        float timeLimit = (float) options.getDouble("timeLimit");
        boolean unlimitedCrafts = options.getBoolean("unlimitedCrafts");

        return new GameOptions(craftLimit, unlimitedCrafts, rngType, bagSize, timeLimit, countDownTime);
    }
}
