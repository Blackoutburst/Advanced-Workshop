package com.blackoutburst.workshop.core.game;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.files.DBUtils;
import com.blackoutburst.workshop.utils.map.MapUtils;
import com.blackoutburst.workshop.utils.minecraft.ArmorUtils;
import com.blackoutburst.workshop.utils.minecraft.CraftUtils;
import com.blackoutburst.workshop.utils.minecraft.ItemFrameUtils;
import com.blackoutburst.workshop.utils.minecraft.ScoreboardUtils;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

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
        if (player.getOpenInventory().getType() == InventoryType.WORKBENCH) {
            player.getOpenInventory().getTopInventory().clear();
        }
        player.getInventory().clear();
        if (player.getItemOnCursor().getAmount() != 0) wsplayer.setHasStored(true);
        //Instant a = Instant.now();
        player.sendMessage("§eYou need to craft a §r" + wsplayer.getCurrentCraft().getName());

        ArmorUtils.setArmor(player);
        ScoreboardUtils.startRound(wsplayer);
        ItemFrameUtils.updateCraft(wsplayer);

        //Bukkit.broadcastMessage("Duration"+Duration.between(Instant.now(),a));

        MapUtils.restoreArea(wsplayer, false);

        //Bukkit.broadcastMessage("Duration"+Duration.between(Instant.now(),a));

        wsplayer.setWaiting(false);
        wsplayer.getTimers().setRoundBegin(Instant.now());

        updateRoundData(wsplayer);
    }

    public static boolean prepareNextRound(WSPlayer wsplayer) {
        wsplayer.setWaiting(true);

        GameOptions gameoptions = wsplayer.getGameOptions();

        if (!gameoptions.isUnlimitedCrafts() && wsplayer.getCurrentCraftIndex() >= gameoptions.getCraftLimit()) {
            EndGameLogic.endGame(wsplayer, true);
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
