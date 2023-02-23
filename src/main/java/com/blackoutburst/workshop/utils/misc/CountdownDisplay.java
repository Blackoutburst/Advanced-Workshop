package com.blackoutburst.workshop.utils.misc;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.nms.NMSTitle;
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

        char color = switch (seconds) {
            case 5, 4 -> 'e'; // yellow
            case 3, 2, 1 -> 'c'; // red
            default -> 'a'; // green
        };

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.8f);

        NMSTitle.send(player,"§" + color + seconds,"",0,20,0);
        seconds--;
    }
}