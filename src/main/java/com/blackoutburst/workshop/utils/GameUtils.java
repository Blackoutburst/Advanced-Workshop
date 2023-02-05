package com.blackoutburst.workshop.utils;

import com.blackoutburst.workshop.Craft;
import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.MaterialBlock;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class GameUtils {

    public static void instantSmelt(Furnace furnace) {
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                if (count >= 200) {
                    this.cancel();
                }
                ItemStack stack = furnace.getInventory().getSmelting();
                if (stack != null) {
                    if (stack.getType().equals(Material.GOLD_ORE)) {
                        furnace.getInventory().setSmelting(new ItemStack(Material.AIR));
                        furnace.getInventory().setResult(new ItemStack(Material.GOLD_INGOT, stack.getAmount()));
                    }
                    if (stack.getType().equals(Material.POTATO_ITEM)) {
                        furnace.getInventory().setSmelting(new ItemStack(Material.AIR));
                        furnace.getInventory().setResult(new ItemStack(Material.BAKED_POTATO, stack.getAmount()));
                    }
                }
                count++;
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 1L, 0L);
    }

    public static boolean isWand(PlayerInventory inv) {
        ItemStack item = inv.getItemInHand();

        return(item.getType() == Material.BLAZE_ROD &&
                item.getItemMeta().getDisplayName().equals("§6Scan wand"));
    }
    public static void startRound(WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();
        Random rng = new Random();

        wsplayer.setCurrentCraft(wsplayer.getCrafts().get(rng.nextInt(wsplayer.getCrafts().size())));
        wsplayer.getPlayer().sendMessage("You must craft: " + wsplayer.getCurrentCraft().getName());
        player.getInventory().clear();
    }

    private static ItemStack getItem(String data) {
        if (data.contains(":")) {
            String[] subData = data.split(":");
            return new ItemStack(Integer.parseInt(subData[0]), 1, Short.parseShort(subData[1]));
        }
        return new ItemStack(Integer.parseInt(data));
    }

    public static MaterialBlock getMaterialBlock(WSPlayer wsPlayer, Location location) {
        for (MaterialBlock block : wsPlayer.getMaterialBlocks()) {
            if (location.getWorld().getName().equals(block.getWorld().getName()) &&
                location.getBlockX() == block.getLocation().getBlockX() &&
                location.getBlockY() == block.getLocation().getBlockY() &&
                location.getBlockZ() == block.getLocation().getBlockZ()) {
                return block;
            }
        }
        return null;
    }

    public static void loadMaterials(WSPlayer wsPlayer, String type) {
        wsPlayer.getMaterialBlocks().clear();
        PlayArea area = wsPlayer.getPlayArea();
        if (area == null) return;

        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + type + ".logic"));

            for (String line : lines) {
                if (line.startsWith("D")) {
                    String[] data = line.split(", ");
                    String[] itemData = data[1].split(":");
                    Material itemType = Material.getMaterial(Integer.parseInt(itemData[0]));
                    byte itemDataType = Byte.parseByte(itemData[1]);
                    int x = Integer.parseInt(data[2]) + area.getAnchor().getBlockX();
                    int y = Integer.parseInt(data[3]) + area.getAnchor().getBlockY();
                    int z = Integer.parseInt(data[4]) + area.getAnchor().getBlockZ();

                    Location location = new Location(wsPlayer.getPlayer().getWorld(), x, y, z);
                    wsPlayer.getMaterialBlocks().add(new MaterialBlock(itemType, itemDataType, location, location.getWorld()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadCraft(WSPlayer wsPlayer, String type) {
        wsPlayer.getCrafts().clear();

        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + type + ".craft"));

            for (String line : lines) {
                String[] data = line.split(", ");
                String name = data[0];
                ItemStack requiredItem = getItem(data[1]);

                ItemStack[] craftingTable = new ItemStack[] {
                        getItem(data[2]), getItem(data[3]), getItem(data[4]),
                        getItem(data[5]), getItem(data[6]), getItem(data[7]),
                        getItem(data[8]), getItem(data[9]), getItem(data[10])
                };

                wsPlayer.getCrafts().add(new Craft(name, requiredItem, craftingTable));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
