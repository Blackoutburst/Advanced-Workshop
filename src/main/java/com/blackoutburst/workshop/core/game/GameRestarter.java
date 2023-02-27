package com.blackoutburst.workshop.core.game;

import com.blackoutburst.workshop.core.WSPlayer;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRestarter extends BukkitRunnable {

    protected WSPlayer wsplayer;

    protected GameStarter gameStarter;
    public GameRestarter(WSPlayer wsplayer, GameStarter gameStarter) {
        this.wsplayer = wsplayer;
        this.gameStarter = gameStarter;
    }

    @Override
    public void run() {
        if (wsplayer.getPlayArea().hasStarted()) {
            this.cancel();
            return;
        }
        if (wsplayer.getPlayArea().isLoading()) {
            wsplayer.getPlayer().sendTitle("§cArea Loading, please wait...", "", 0, 2 ,0);
            return;
        }
        gameStarter.run();
    }
}
