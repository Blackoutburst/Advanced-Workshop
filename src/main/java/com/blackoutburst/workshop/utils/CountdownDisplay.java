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
        if (!wsplayer.isInGame() || seconds == 0) {
            this.cancel();
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (seconds == 0 ? 1 : 0), 0.8f);
            return;
        }
        char color;

        switch (seconds) {
            case 5:
            case 4:
                color = 'e'; // yellow
                break;
            case 3:
            case 2:
            case 1:
                color = 'c'; // red
                break;
            default:
                color = 'a'; // green
                break;
        }

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.8f);
        MiscUtils.sendTitle(player,"§" + color + seconds,"",0,20,0);
        seconds--;
    }
}
