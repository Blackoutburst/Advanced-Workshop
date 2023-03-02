package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;

import com.blackoutburst.workshop.utils.files.DBUtils;
import com.blackoutburst.workshop.utils.misc.EffectsUtils;
import com.blackoutburst.workshop.utils.misc.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.time.Instant;

public class EntityUtils {

    public static void blaze(Player player) {
        player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD));
    }

    public static void chicken(Player player) {
        player.getInventory().addItem(new ItemStack(Material.EGG));
    }

    public static void hoglin(Player player) {
        player.getInventory().addItem(new ItemStack(Material.LEATHER));
    }

    public static void villager(WSPlayer wsPlayer, Player player) {
        if (!player.getInventory().containsAtLeast(wsPlayer.getCurrentCraft().getItemRequired(), 1)) {
            player.sendMessage("§cThat's not quite right. I need " + wsPlayer.getCurrentCraft().getName());
            return;
        }

        wsPlayer.setNextRound(true);
        EffectsUtils.playLevelUPSound(player);
        wsPlayer.getTimers().setRoundEnd(Instant.now());

        Float duration = Duration.between(wsPlayer.getTimers().getRoundBegin(), wsPlayer.getTimers().getRoundEnd()).toMillis() / 1000.0f;
        String roundTime = StringUtils.ROUND.format(duration) + "s";

        Double currentDuration = DBUtils.getData(player, wsPlayer.getPlayArea().getType() + "." + "crafts" + "." + wsPlayer.getCurrentCraft().getName(), Double.class);
        if (currentDuration == null)
            currentDuration = Double.MAX_VALUE;

        if (duration < currentDuration && !wsPlayer.hasStored())
            DBUtils.saveData(player, wsPlayer.getPlayArea().getType() + "." + "crafts" + "." + wsPlayer.getCurrentCraft().getName(), duration, Float.class);

        String message = (currentDuration != Double.MAX_VALUE && (duration < currentDuration) && !wsPlayer.hasStored() ? "§d§lPB! (" + StringUtils.ROUND.format(duration - currentDuration) + "s" + ")" : "");

        if (wsPlayer.getGameOptions().isShowNonPBs() && duration >= currentDuration) {
            message = ("§c§l(+" + StringUtils.ROUND.format(duration - currentDuration) + "s)");
        }

        player.sendMessage("§ePerfect! Just what I needed. §b(" + roundTime + ") " + message);

        wsPlayer.setHasStored(false);
    }

    public static void witherSkeleton(Player player) {
        player.getInventory().addItem(new ItemStack(Material.BONE));
        player.getInventory().addItem(new ItemStack(Material.COAL));
    }

    public static void clearEntity(WSPlayer wsplayer) {
        PlayArea area = wsplayer.getPlayArea();
        area.getEntities().forEach(Entity::remove);
    }

    public static void spawnEntity(EntityType type, Location location, BlockFace direction, PlayArea area, String tag) {
        World world = location.getWorld();
        if (world == null) return;

        double x = location.getBlockX() + area.getAnchor().getBlockX() + 0.5;
        double y = location.getBlockY() + area.getAnchor().getBlockY();
        double z = location.getBlockZ() + area.getAnchor().getBlockZ() + 0.5;
        float yaw = switch (direction) {
            case NORTH -> 180;
            case EAST -> -90;
            case WEST -> 90;
            default -> 0;
        };

        Entity entity = world.spawnEntity(new Location(world, x, y, z, yaw, 0), type);
        entity.setCustomNameVisible(false);
        entity.setCustomName(tag);
        entity.setPersistent(true);
        entity.setSilent(true);
        entity.setInvulnerable(true);
        entity.setGravity(true);

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setCollidable(false);
            livingEntity.setAI(false);
        }

        if (entity instanceof ItemFrame itemFrame) {
            itemFrame.setFacingDirection(direction);
            itemFrame.setFixed(true);
        }

        area.getEntities().add(entity);
    }
}
