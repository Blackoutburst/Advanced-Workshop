package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.Craft;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.game.GameOptions;
import com.blackoutburst.workshop.utils.files.CraftFileUtils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

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
        Craft[] crafts = CraftFileUtils.readCraftFile(type);
        wsPlayer.getCrafts().addAll(List.of(crafts));
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
                if (wsplayer.getCraftList() == null) {
                    for (int i = 0; i < 10; i++) {
                        int n = rng.nextInt(craftAmount);
                        finalCraftList.add(validCrafts.get(n));
                    }
                    break;
                }
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
