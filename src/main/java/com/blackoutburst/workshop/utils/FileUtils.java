package com.blackoutburst.workshop.utils;

import com.blackoutburst.workshop.Craft;
import com.blackoutburst.workshop.Main;
import org.bukkit.inventory.ItemStack;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    private static ItemStack getItem(String data) {
        if (data.contains(":")) {
            String[] subData = data.split(":");
            return new ItemStack(Integer.parseInt(subData[0]), 1, Short.parseShort(subData[1]));
        }
        return new ItemStack(Integer.parseInt(data));
    }

    public static void loadCraft() {
        Main.crafts.clear();

        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/farm.craft"));

            for (String line : lines) {
                String[] data = line.split(", ");
                String name = data[0];
                ItemStack requiredItem = getItem(data[1]);

                ItemStack[] craftingTable = new ItemStack[] {
                        getItem(data[2]), getItem(data[3]), getItem(data[4]),
                        getItem(data[5]), getItem(data[6]), getItem(data[7]),
                        getItem(data[8]), getItem(data[9]), getItem(data[10])
                };

                Main.crafts.add(new Craft(name, requiredItem, craftingTable));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
