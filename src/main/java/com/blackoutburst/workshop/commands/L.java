package com.blackoutburst.workshop.commands;

import com.blackout.npcapi.core.NPC;
import com.blackout.npcapi.utils.NPCManager;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.MapUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class L implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            WSPlayer wsplayer = WSPlayer.getFromPlayer((Player) sender);
            if (wsplayer == null || !wsplayer.isInGame()) return true;

            wsplayer.setInGame(false);

            wsplayer.setCurrentCraft(null);

            MapUtils.restoreArea(wsplayer);

            PlayArea area = wsplayer.getPlayArea();
            if (area != null)
                area.setBusy(false);

            wsplayer.getPlayer().sendMessage("Game stopped");

            for (NPC npc : wsplayer.getNpcs()) {
                NPCManager.deleteNPC(wsplayer.getPlayer(), npc);
            }

        }
        return true;
    }
}
