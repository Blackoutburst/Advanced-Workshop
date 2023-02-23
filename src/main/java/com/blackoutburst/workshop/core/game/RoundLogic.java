package com.blackoutburst.workshop.core.game;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.files.DBUtils;
import com.blackoutburst.workshop.utils.map.MapUtils;
import com.blackoutburst.workshop.utils.minecraft.ArmorUtils;
import com.blackoutburst.workshop.utils.minecraft.CraftUtils;
import com.blackoutburst.workshop.utils.minecraft.ItemFrameUtils;
import com.blackoutburst.workshop.utils.minecraft.ScoreboardUtils;
import org.bukkit.entity.Player;

import java.time.Instant;

public class RoundLogic {

    private static void updateRoundData(WSPlayer wsplayer) {
        Integer roundCount = DBUtils.getData(wsplayer.getPlayer(), "roundCount", Integer.class);
        DBUtils.saveData(wsplayer.getPlayer(), "roundCount", roundCount != null ? roundCount + 1 : 1, Integer.class);

        Integer mapRoundCount = DBUtils.getData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".roundCount", Integer.class);
        DBUtils.saveData(wsplayer.getPlayer(), wsplayer.getPlayArea().getType() + ".roundCount", mapRoundCount != null ? mapRoundCount + 1 : 1, Integer.class);
    }

    public static void startRound(WSPlayer wsplayer) {
        if (!wsplayer.isInGame()) return;

        Player player = wsplayer.getPlayer();

        player.getInventory().clear();
        player.sendMessage("§eYou need to craft a §r" + wsplayer.getCurrentCraft().getName());

        ArmorUtils.setArmor(player);
        ScoreboardUtils.startRound(wsplayer);
        ItemFrameUtils.updateCraft(wsplayer);
        MapUtils.restoreArea(wsplayer, false);

        wsplayer.setWaiting(false);
        wsplayer.getTimers().setRoundBegin(Instant.now());

        updateRoundData(wsplayer);
    }

    public static boolean prepareNextRound(WSPlayer wsplayer) {
        wsplayer.setWaiting(true);

        GameOptions gameoptions = wsplayer.getGameOptions();

        if (!gameoptions.isUnlimitedCrafts() && wsplayer.getCurrentCraftIndex() >= gameoptions.getCraftLimit()) {
            EndGameLogic.endGame(wsplayer);
            return true;
        }

        int craftIndex = wsplayer.getCurrentCraftIndex();
        int bagSize = wsplayer.getGameOptions().getBagSize();

        if (craftIndex == bagSize) {
            CraftUtils.updateCraftList(wsplayer);
        }

        CraftUtils.updateCraft(wsplayer);
        return false;
    }

}
