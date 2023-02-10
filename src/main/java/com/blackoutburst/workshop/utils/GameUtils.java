package com.blackoutburst.workshop.utils;

import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.NPCManager;
import com.blackout.npcapi.utils.SkinLoader;
import com.blackoutburst.workshop.Craft;
import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.MaterialBlock;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.nms.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameUtils {

    private static void fastCook(Furnace furnace, ItemStack stack, Material output, int data) {
        furnace.getInventory().setSmelting(new ItemStack(Material.AIR));
        furnace.getInventory().setResult(new ItemStack(output, stack.getAmount(), (short) data));
    }

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
                    switch (stack.getType()) {
                        case POTATO_ITEM:
                            fastCook(furnace, stack, Material.BAKED_POTATO, 0);
                            break;
                        case RAW_CHICKEN:
                            fastCook(furnace, stack, Material.COOKED_CHICKEN, 0);
                            break;
                        case RAW_FISH:
                            fastCook(furnace, stack, Material.COOKED_FISH, 0);
                            break;
                        case PORK:
                            fastCook(furnace, stack, Material.GRILLED_PORK, 0);
                            break;
                        case RAW_BEEF:
                            fastCook(furnace, stack, Material.COOKED_BEEF, 0);
                            break;
                        case CLAY_BALL:
                            fastCook(furnace, stack, Material.CLAY_BRICK, 0);
                            break;
                        case SAND:
                            fastCook(furnace, stack, Material.GLASS, 0);
                            break;
                        case GOLD_ORE:
                            fastCook(furnace, stack, Material.GOLD_INGOT, 0);
                            break;
                        case IRON_ORE:
                            fastCook(furnace, stack, Material.IRON_INGOT, 0);
                            break;
                        case NETHERRACK:
                            fastCook(furnace, stack, Material.NETHER_BRICK_ITEM, 0);
                            break;
                        case COBBLESTONE:
                            fastCook(furnace, stack, Material.STONE, 0);
                            break;
                        case CACTUS:
                            fastCook(furnace, stack, Material.INK_SACK, 2);
                            break;
                        case LOG:
                        case LOG_2:
                            fastCook(furnace, stack, Material.COAL, 1);
                            break;
                        case COAL_ORE:
                            fastCook(furnace, stack, Material.COAL, 0);
                            break;
                        case DIAMOND_ORE:
                            fastCook(furnace, stack, Material.DIAMOND, 0);
                            break;
                        case EMERALD_ORE:
                            fastCook(furnace, stack, Material.EMERALD, 0);
                            break;
                        case LAPIS_ORE:
                            fastCook(furnace, stack, Material.INK_SACK, 4);
                            break;
                        case QUARTZ_ORE:
                            fastCook(furnace, stack, Material.QUARTZ, 0);
                            break;
                        case REDSTONE_ORE:
                            fastCook(furnace, stack, Material.REDSTONE, 0);
                            break;
                    }
                }
                count++;
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 1L, 0L);
    }


    private static void teleportPlayerToArea(Player player, String[] data, PlayArea area) {
        float x = Integer.parseInt(data[2]) + area.getAnchor().getBlockX() + 0.5f;
        int y = Integer.parseInt(data[3]) + area.getAnchor().getBlockY();
        float z = Integer.parseInt(data[4]) + area.getAnchor().getBlockZ() + 0.5f;
        int yaw = 0;
        switch (BlockFace.valueOf(data[5])) {
            case NORTH:
                yaw = 180;
                break;
            case SOUTH:
                yaw = 0;
                break;
            case EAST:
                yaw = -90;
                break;
            case WEST:
                yaw = 90;
                break;
        }

        player.teleport(new Location(player.getWorld(), x, y, z, yaw, 0));
    }

    private static void spawnNPC(String name, int skinId, WSPlayer wsPlayer, String[] data, PlayArea area) {
        float x = Integer.parseInt(data[2]) + area.getAnchor().getBlockX() + 0.5f;
        int y = Integer.parseInt(data[3]) + area.getAnchor().getBlockY();
        float z = Integer.parseInt(data[4]) + area.getAnchor().getBlockZ() + 0.5f;
        int yaw = 0;
        switch (BlockFace.valueOf(data[5])) {
            case NORTH:
                yaw = 180;
                break;
            case SOUTH:
                yaw = 0;
                break;
            case EAST:
                yaw = -90;
                break;
            case WEST:
                yaw = 90;
                break;
        }

        NPC npc = new NPC(UUID.randomUUID(), name)
                .setLocation(new Location(wsPlayer.getPlayer().getWorld(), x, y, z, yaw, 0))
                .setSkin(SkinLoader.getSkinById(skinId))
                .setNameVisible(false)
                .setCapeVisible(false);

        NPCManager.spawnNPC(npc, wsPlayer.getPlayer());
        wsPlayer.getNpcs().add(npc);
    }

    private static void spawnItemFrame(WSPlayer wsPlayer, String[] data, PlayArea area, int id) {
        int x = Integer.parseInt(data[2]) + area.getAnchor().getBlockX();
        int y = Integer.parseInt(data[3]) + area.getAnchor().getBlockY();
        int z = Integer.parseInt(data[4]) + area.getAnchor().getBlockZ();
        NMSEnumDirection.Direction direction = NMSEnumDirection.Direction.NORTH;
        switch (BlockFace.valueOf(data[5])) {
            case NORTH:
                direction = NMSEnumDirection.Direction.NORTH;
                break;
            case SOUTH:
                direction = NMSEnumDirection.Direction.SOUTH;
                break;
            case EAST:
                direction = NMSEnumDirection.Direction.EAST;
                break;
            case WEST:
                direction = NMSEnumDirection.Direction.WEST;
                break;
        }

        NMSBlockPosition position = new NMSBlockPosition(x, y, z);
        NMSEnumDirection facingDirection = new NMSEnumDirection(direction);
        NMSEntities itemFrame = new NMSEntities(wsPlayer.getPlayer().getWorld(), NMSEntities.EntityType.ITEM_FRAME, position.position, facingDirection.direction);
        NMSSpawnEntity.send(wsPlayer.getPlayer(), itemFrame, 2);
        NMSEntityMetadata.send(wsPlayer.getPlayer(), itemFrame);
        wsPlayer.getItemFrames()[id] = itemFrame;
    }

    public static void spawnEntities(WSPlayer wsPlayer, String type) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + type + ".logic"));
            PlayArea area = wsPlayer.getPlayArea();

            for (String line : lines) {
                if (line.startsWith("S")) {
                    String[] data = line.split(", ");

                    if (data[1].equals("player")) {
                        teleportPlayerToArea(wsPlayer.getPlayer(), data, area);
                    }

                    if (data[1].equals("chicken")) {
                        spawnNPC("chicken", 0, wsPlayer, data, area);
                    }

                    if (data[1].equals("villager")) {
                        int skinID = SkinLoader.skins.size() + 1;
                        SkinLoader.loadSkinFromUUID(skinID, wsPlayer.getPlayer().getUniqueId().toString().replace("-", ""));
                        spawnNPC("villager", skinID, wsPlayer, data, area);
                    }

                    if (data[1].equals("1") || data[1].equals("2") || data[1].equals("3") ||
                            data[1].equals("4") || data[1].equals("5") || data[1].equals("6") ||
                            data[1].equals("7") || data[1].equals("8") || data[1].equals("9")) {
                        spawnItemFrame(wsPlayer, data, area, Integer.parseInt(data[1]) - 1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isWand(PlayerInventory inv) {
        ItemStack item = inv.getItemInHand();

        return (item.getType() == Material.BLAZE_ROD &&
                item.getItemMeta().getDisplayName().equals("§6Scan wand"));
    }

    public static void startRound(WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();
        Random rng = new Random();

        wsplayer.setCurrentCraft(wsplayer.getCrafts().get(rng.nextInt(wsplayer.getCrafts().size())));
        wsplayer.getPlayer().sendMessage("You must craft: " + wsplayer.getCurrentCraft().getName());
        player.getInventory().clear();
        for (int i = 0; i < 9; i++) {
            NMSEntities frame = wsplayer.getItemFrames()[i];
            ItemStack item = wsplayer.getCurrentCraft().getCraftingTable()[i];
            NMSItemFrame.setItem(player, frame, item);
        }
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

                    Bukkit.broadcastMessage(line);
                    Bukkit.broadcastMessage(String.valueOf(data));
                    Bukkit.broadcastMessage("-----------");

                    List<Integer> tools = new ArrayList<>();
                    for (int i = 5; i < data.length; i++) {
                        tools.add(Integer.parseInt(data[i]));
                    }
                    int x = Integer.parseInt(data[2]) + area.getAnchor().getBlockX();
                    int y = Integer.parseInt(data[3]) + area.getAnchor().getBlockY();
                    int z = Integer.parseInt(data[4]) + area.getAnchor().getBlockZ();

                    Location location = new Location(wsPlayer.getPlayer().getWorld(), x, y, z);
                    wsPlayer.getMaterialBlocks().add(new MaterialBlock(itemType, itemDataType, location, location.getWorld(), tools));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadCraft(WSPlayer wsPlayer, String type) {
        wsPlayer.getCrafts().clear();

        String name = null;
        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/" + type + ".craft"));

            for (String line : lines) {
                String[] data = line.split(", ");
                name = data[0];
                ItemStack requiredItem = getItem(data[1]);

                ItemStack[] craftingTable = new ItemStack[]{
                        getItem(data[2]), getItem(data[3]), getItem(data[4]),
                        getItem(data[5]), getItem(data[6]), getItem(data[7]),
                        getItem(data[8]), getItem(data[9]), getItem(data[10])
                };

                wsPlayer.getCrafts().add(new Craft(name, requiredItem, craftingTable));
            }
        } catch (Exception e) {
            Bukkit.broadcastMessage("Error loading craft: " + name);
            e.printStackTrace();
        }
    }

    private static boolean hasLostSupport(char direction, boolean solid, int id, byte data) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("./plugins/Workshop/supports.txt"));
            String pattern = direction + ",([0-9]+):?([0-9]+)?";

            Pattern regexPattern = Pattern.compile(pattern);

            if ((id == 132) && (!solid)) {
                return false;
            }

            for (String line : lines) {
                Matcher patternMatcher = regexPattern.matcher(line);
                if (!patternMatcher.matches()) {
                    continue;
                }
                int checkID = Integer.parseInt(patternMatcher.group(1));

                if (!(checkID == id)) {
                    continue;
                }
                if (patternMatcher.group(2) == null) {
                    return true;
                }
                byte checkData = (byte) Integer.parseInt(patternMatcher.group(2));

                if (data == checkData) {
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void supportIterator (Location location, WSPlayer wsplayer, char direction) {
        Player player = wsplayer.getPlayer();
        Block supporter = location.getWorld().getBlockAt(location);

        boolean solid = supporter.getType().isSolid();

        char[] directions = {'U','N','E','S','W','D'};
        char[] oppositeDirections = {'D','S','W','N','E','U'};
        int[][] relativeCoords = {
                {0,1,0},
                {0,0,-1},
                {1,0,0},
                {0,0,1},
                {-1,0,0},
                {0,-1,0}
        };

        for (int i = 0; i < directions.length; i++) {
            int xChange = relativeCoords[i][0];
            int yChange = relativeCoords[i][1];
            int zChange = relativeCoords[i][2];
            Location newLocation = location.clone();
            newLocation.add(xChange,yChange,zChange);

            Block testBlock = newLocation.getWorld().getBlockAt(newLocation);
            int id = testBlock.getTypeId();
            byte data = testBlock.getData();

            if (hasLostSupport(directions[i], solid, id, data) && direction != oppositeDirections[i]) {
                MaterialBlock materialBlock = GameUtils.getMaterialBlock(wsplayer, newLocation);

                if (materialBlock != null) {
                    ItemStack item = new ItemStack(materialBlock.getType(), 1, materialBlock.getData());
                    player.getInventory().addItem(item);
                }
                supportIterator (newLocation, wsplayer, directions[i]);
            }
        }
    }

    public static boolean canBreak(MaterialBlock materialBlock, Player player) {

        Bukkit.broadcastMessage(String.valueOf(materialBlock.getTools()));
        Bukkit.broadcastMessage(String.valueOf(player.getItemInHand().getTypeId()));

        if (materialBlock.getTools().size() == 0) {
            return true;
        }
        for (int tool : materialBlock.getTools()) {
            if (player.getItemInHand().getTypeId() == tool) {
                return true;
            }
        }
        return false;
    }
}
