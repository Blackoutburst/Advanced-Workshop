package com.blackoutburst.workshop.core.events;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.MapUtils;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit {

    public static void execute(PlayerQuitEvent event) {
        WSPlayer wsPlayer = WSPlayer.getFromPlayer(event.getPlayer());
        if (wsPlayer == null) return;

        MapUtils.restoreArea(wsPlayer);
        PlayArea area = wsPlayer.getPlayArea();
        if (area != null)
            area.setBusy(false);
        Main.players.remove(WSPlayer.getFromPlayer(event.getPlayer()));

    }
}
