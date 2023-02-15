package com.blackoutburst.workshop.utils;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class EffectsUtils {

    public static void playLevelUPSound(Player player) {
        player.getWorld().playSound(player.getLocation(), Sound.LEVEL_UP, 3f, 1f);
    }

    public static void breakBlock(Block block) {
        World world = block.getWorld();
        Location location = block.getLocation();

        world.playEffect(location, Effect.STEP_SOUND, block.getTypeId(), 10);
        world.playEffect(location, Effect.TILE_BREAK, block.getTypeId(), 10);
    }
}
