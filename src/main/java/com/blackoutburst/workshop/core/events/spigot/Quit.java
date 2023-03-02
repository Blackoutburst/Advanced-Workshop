package com.blackoutburst.workshop.core.events.spigot;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.map.MapUtils;

import com.blackoutburst.workshop.utils.minecraft.EntityUtils;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit {

    public static void execute(PlayerQuitEvent event) {
        WSPlayer wsPlayer = WSPlayer.getFromPlayer(event.getPlayer());
        if (wsPlayer == null) return;

        PlayArea area = wsPlayer.getPlayArea();
        if (area != null) {
            EntityUtils.clearEntity(wsPlayer);
            area.setBusy(false);
            MapUtils.restoreArea(wsPlayer, true);
        }
        wsPlayer.getGameOptions().save(wsPlayer);
        Main.players.remove(WSPlayer.getFromPlayer(event.getPlayer()));
    }
}
