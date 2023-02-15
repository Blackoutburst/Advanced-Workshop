package com.blackoutburst.workshop.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownDisplay extends BukkitRunnable {
    private int seconds;
    private final Player player;
    public CountdownDisplay(int seconds, Player player) {
        this.seconds = seconds;
        this.player = player;
    }

    @Override
    public void run() {
        if (seconds == 1) { this.cancel(); }

        MiscUtils.sendTitle(player,"§c" + seconds,"",0,20,0);
        player.playSound(player.getLocation(), Sound.NOTE_STICKS,1,0.8f);
        seconds--;
    }
}
