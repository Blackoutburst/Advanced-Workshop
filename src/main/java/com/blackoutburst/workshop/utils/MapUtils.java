package com.blackoutburst.workshop.utils;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MapUtils {

    public static void decoScan(WSPlayer wsplayer, String name) {
        try {
            PrintWriter writer = new PrintWriter("./plugins/Workshop/" + name + ".deco");
            int x1 = wsplayer.getScanWand1().getBlockX();
            int y1 = wsplayer.getScanWand1().getBlockY();
            int z1 = wsplayer.getScanWand1().getBlockZ();
            int x2 = wsplayer.getScanWand2().getBlockX();
            int y2 = wsplayer.getScanWand2().getBlockY();
            int z2 = wsplayer.getScanWand2().getBlockZ();

            World world = wsplayer.getScanWand1().getWorld();

            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                    for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++) {
                        Block b = world.getBlockAt(x, y, z);
                        if (b.getType().equals(Material.AIR)) continue;

                        writer.write(b.getTypeId() + ":" + b.getData()+ ", " + (x - Math.min(x1, x2)) + ", " + (y - Math.min(y1, y2)) + ", " + (z - Math.min(z1, z2)) + "\n");
                    }
                }
            }

            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void logicScan(WSPlayer wsplayer, String name) {
        try {
            PrintWriter writer = new PrintWriter("./plugins/Workshop/" + name + ".logic");
            int x1 = wsplayer.getScanWand1().getBlockX();
            int y1 = wsplayer.getScanWand1().getBlockY();
            int z1 = wsplayer.getScanWand1().getBlockZ();
            int x2 = wsplayer.getScanWand2().getBlockX();
            int y2 = wsplayer.getScanWand2().getBlockY();
            int z2 = wsplayer.getScanWand2().getBlockZ();

            World world = wsplayer.getScanWand1().getWorld();

            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                    for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++) {
                        Block b = world.getBlockAt(x, y, z);
                        if (b.getType().equals(Material.DROPPER)) {
                            Dropper dropper = (Dropper) b.getState();
                            ItemStack[] items = dropper.getInventory().getContents();
                            if (items.length == 0) continue;

                            writer.write("D, " + items[0].getTypeId() + ":" + items[0].getData().getData() + ", "+ (x - Math.min(x1, x2)) + ", " + (y - Math.min(y1, y2)) + ", " + (z - Math.min(z1, z2)) + "\n");
                        }
                        if (b.getType().equals(Material.SIGN) || b.getType().equals(Material.SIGN_POST) || b.getType().equals(Material.WALL_SIGN)) {
                            Sign sign = (Sign) b.getState();
                            if (sign.getLines().length == 0) continue;
                            String text = sign.getLine(0);

                            org.bukkit.material.Sign sign2 = (org.bukkit.material.Sign) sign.getData();
                            BlockFace directionFacing = sign2.getFacing();

                            writer.write("S, " + text + ", "+ (x - Math.min(x1, x2)) + ", " + (y - Math.min(y1, y2)) + ", " + (z - Math.min(z1, z2))+ ", " + directionFacing.toString() + "\n");
                        }
                    }
                }
            }

            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void restoreArea(WSPlayer wsplayer) {
        if (wsplayer.getPlayArea() == null) return;

        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + wsplayer.getPlayArea().getType() + ".deco"));
            Player player = wsplayer.getPlayer();
            World world = player.getWorld();
            Location anchor = wsplayer.getPlayArea().getAnchor();

            for (String line : lines) {
                String[] data = line.split(", ");
                String[] blockType = data[0].split(":");
                Material type = Material.getMaterial(Integer.parseInt(blockType[0]));
                byte dataType = Byte.parseByte(blockType[1]);
                int xOffset = Integer.parseInt(data[1]);
                int yOffset = Integer.parseInt(data[2]);
                int zOffset = Integer.parseInt(data[3]);

                Block b = world.getBlockAt(anchor.getBlockX() + xOffset, anchor.getBlockY() + yOffset, anchor.getBlockZ() + zOffset);
                b.setType(type);
                b.setData(dataType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pasteMap(WSPlayer wsplayer, String name) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + name + ".deco"));
            Player player = wsplayer.getPlayer();
            World world = player.getWorld();
            Location anchor = player.getLocation();

            for (String line : lines) {
                String[] data = line.split(", ");
                String[] blockType = data[0].split(":");
                Material type = Material.getMaterial(Integer.parseInt(blockType[0]));
                byte dataType = Byte.parseByte(blockType[1]);
                int xOffset = Integer.parseInt(data[1]);
                int yOffset = Integer.parseInt(data[2]);
                int zOffset = Integer.parseInt(data[3]);

                Block b = world.getBlockAt(anchor.getBlockX() + xOffset, anchor.getBlockY() + yOffset, anchor.getBlockZ() + zOffset);
                b.setType(type);
                b.setData(dataType);
            }

            String areaData = name + ", " + world.getName() + ", " + anchor.getBlockX() + ", " + anchor.getBlockY() + ", " + anchor.getBlockZ() + "\n";
            Files.write(Paths.get("./plugins/Workshop/areas"), areaData.getBytes(), StandardOpenOption.APPEND);
            Main.playAreas.add(new PlayArea(name, anchor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadPlayAreas() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/areas"));

            for (String line : lines) {
                String[] data = line.split(", ");
                String type = data[0];
                World world = Bukkit.getWorld(data[1]);
                int x = Integer.parseInt(data[2]);
                int y = Integer.parseInt(data[3]);
                int z = Integer.parseInt(data[4]);
                Location location = new Location(world, x, y, z);

                Main.playAreas.add(new PlayArea(type, location));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
