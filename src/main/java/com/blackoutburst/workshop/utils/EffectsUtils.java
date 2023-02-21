package com.blackoutburst.workshop.utils;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class EffectsUtils {

    public static void playLevelUPSound(Player player) {
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 3f, 1f);
    }

    public static void breakBlock(Block block) {
        World world = block.getWorld();
        Location location = block.getLocation();

        world.playEffect(location, Effect.STEP_SOUND, 10);
        world.playEffect(location, Effect.WITHER_BREAK_BLOCK, 10);
    }
}
