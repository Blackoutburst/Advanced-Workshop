package com.blackoutburst.workshop.utils;

import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownDisplay extends BukkitRunnable {
    private int seconds;
    private final WSPlayer wsplayer;

    private final Player player;

    public CountdownDisplay(int seconds, WSPlayer wsplayer) {
        this.seconds = seconds;
        this.wsplayer = wsplayer;
        this.player = wsplayer.getPlayer();
    }
    @Override
    public void run() {
        if (!wsplayer.isInGame()) return;
        if (seconds == 1) this.cancel();

        MiscUtils.sendTitle(player,"§c" + seconds,"",0,20,0);
        player.playSound(player.getLocation(), Sound.NOTE_STICKS,1,0.8f);
        seconds--;
    }
}
