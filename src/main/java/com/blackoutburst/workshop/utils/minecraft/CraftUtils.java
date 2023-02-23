package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.Craft;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.game.GameOptions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CraftUtils {

    public static void updateCraft(WSPlayer wsplayer) {
        List<Craft> crafts = wsplayer.getCraftList();
        int craftIndex = wsplayer.getCurrentCraftIndex() + 1;

        wsplayer.setCurrentCraft(crafts.get((craftIndex - 1) % wsplayer.getGameOptions().getBagSize()));
        wsplayer.setCurrentCraftIndex(craftIndex);
    }

    private static ItemStack getItem(String data) {
        return new ItemStack(Material.valueOf(data));
    }

    public static void loadCraft(WSPlayer wsPlayer, String type) {
        wsPlayer.getCrafts().clear();

        String name = null;
        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + type + ".craft"));

            // TODO
            // THIS IS BROKEN
            // update .craft format

            for (String line : lines) {
                String[] data = line.split(", ");
                name = data[0];
                ItemStack requiredItem = getItem(data[1]);

                ItemStack[] craftingTable = new ItemStack[]{
                        getItem(data[2]), getItem(data[3]), getItem(data[4]),
                        getItem(data[5]), getItem(data[6]), getItem(data[7]),
                        getItem(data[8]), getItem(data[9]), getItem(data[10])
                };

                List<ItemStack> materials = new ArrayList<>();
                for (int i = 11; i < data.length; i++) {
                    String[] subData = data[i].split(":");
                    Material itemType = Material.getMaterial(subData[0]);
                    int itemAmount = Integer.parseInt(subData[2]);

                    materials.add(new ItemStack(itemType, itemAmount));
                }

                wsPlayer.getCrafts().add(new Craft(name, requiredItem, craftingTable, materials));
            }
        } catch (Exception e) {
            Bukkit.broadcastMessage("Error loading craft: " + name);
            e.printStackTrace();
        }
    }

    public static void updateCraftList(WSPlayer wsplayer) {
        Random rng = new Random();
        GameOptions gameoptions = wsplayer.getGameOptions();

        List<Craft> finalCraftList =  new ArrayList<>();
        List<Craft> validCrafts = wsplayer.getCrafts();

        char type = gameoptions.getRandomType();
        int craftAmount = validCrafts.size();
        int bagSize = gameoptions.getBagSize();

        switch (type) {
            case 'N', 'B' -> {
                List<Craft> bags = new ArrayList<>();

                if (wsplayer.getCraftList() == null) {
                    List<Craft> bag1 = generateBag(wsplayer);
                    List<Craft> bag2 = generateBag(wsplayer);

                    bags.addAll(bag1);
                    bags.addAll(bag2);
                    finalCraftList = bags;
                    break;
                }

                List<Craft> tempCraftList = wsplayer.getCraftList().subList(bagSize, bagSize * 2);
                List<Craft> bag = generateBag(wsplayer);

                finalCraftList.addAll(tempCraftList);
                finalCraftList.addAll(bag);
            }
            case 'R' -> {
                List<Craft> last5 = wsplayer.getCraftList().subList(5, 10);

                finalCraftList.addAll(last5);

                for (int i = 0; i < 5; i++) {
                    int n = rng.nextInt(craftAmount);

                    finalCraftList.add(validCrafts.get(n));
                }
            }
        }
        wsplayer.setCraftList(finalCraftList);
    }

    public static List<Craft> generateBag(WSPlayer wsplayer) {
        List<Craft> validCrafts = wsplayer.getCrafts();
        List<Craft> bag = new ArrayList<>();

        int craftAmount = validCrafts.size();
        int bagSize = wsplayer.getGameOptions().getBagSize();
        float craftCopies = (float) bagSize / craftAmount;
        int roundedCraftCopies = (int) Math.ceil(craftCopies);
        int extra = (roundedCraftCopies*craftAmount) - bagSize;

        for (int i = 0; i < roundedCraftCopies; i++) {
            bag.addAll(validCrafts);
        }

        Collections.shuffle(bag);
        return bag.subList(0, bag.size() - extra);
    }
}
