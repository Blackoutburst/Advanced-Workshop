package com.blackoutburst.workshop.core.game;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.map.MapUtils;
import com.blackoutburst.workshop.utils.minecraft.ArmorUtils;
import com.blackoutburst.workshop.utils.minecraft.EntityUtils;
import com.blackoutburst.workshop.utils.minecraft.ScoreboardUtils;
import com.blackoutburst.workshop.utils.misc.PBUtils;
import com.blackoutburst.workshop.utils.misc.StringUtils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.time.Instant;

public class EndGameLogic {

    private static void clearPlayer(WSPlayer wsplayer) {
        Player player = wsplayer.getPlayer();

        ArmorUtils.removeArmor(player);
        EntityUtils.clearEntity(wsplayer);

        wsplayer.setPlayArea(null);
        wsplayer.setInGame(false);
        wsplayer.getGamestarter().cancel();
        wsplayer.getGameRestarter().cancel();
        wsplayer.setCurrentCraft(null);
        wsplayer.setCraftList(null);
        wsplayer.setWaiting(false);


        player.sendMessage("§eThe game ended! §b(" + getEndMessage(wsplayer) + ")");
        player.getInventory().clear();
        player.setItemOnCursor(new ItemStack(Material.AIR));
        player.setCanPickupItems(true);
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(Main.spawn);
        wsplayer.getTimers().setMapBegin(null);
        player.getInventory().clear();
    }

    private static String getEndMessage(WSPlayer wsplayer) {
        int craftNumber = wsplayer.getCurrentCraftIndex() - 1;

        if (wsplayer.getTimers().getMapBegin() != null && !wsplayer.getGameOptions().isTimeLimited()) {
            float duration = Duration.between(wsplayer.getTimers().getMapBegin(), wsplayer.getTimers().getMapEnd()).toMillis() / 1000.0f;
            return StringUtils.ROUND.format(duration) + "s";
        }

        wsplayer.getGameOptions().setTimeLimited(false);
        wsplayer.getGameOptions().setUnlimitedCrafts(false);

        return craftNumber + " craft" + (craftNumber == 1 ? "" : "s");
    }

    private static void checkTimes(WSPlayer wsplayer, boolean endedNaturally, String... timeTypes) {
        GameOptions gameoptions = wsplayer.getGameOptions();

        if (gameoptions.isTimeLimited() && endedNaturally && !gameoptions.hypixelSaysMode) {
            if (timeTypes.length > 0) {
                PBUtils.craftPB(wsplayer, timeTypes[0]);
            }
            return;
        }
        wsplayer.getTimers().setMapEnd(Instant.now());

        if (wsplayer.getCurrentCraftIndex() == 5 && gameoptions.getRandomType() == 'N'
                && gameoptions.hypixelSaysMode && endedNaturally) {
            if (PBUtils.hypixelSaysRegularPB(wsplayer)) return;
        }

        if (wsplayer.getCurrentCraftIndex() == 5 && gameoptions.getRandomType() == 'N'
                && !gameoptions.hypixelSaysMode && endedNaturally) {
            if (PBUtils.regularPB(wsplayer)) return;
        }

        if (wsplayer.getCurrentCraftIndex() == 10 && gameoptions.getRandomType() == 'N'
                && !gameoptions.hypixelSaysMode && endedNaturally) {
            if (PBUtils.craft10PB(wsplayer)) return;
        }

        if (wsplayer.getCurrentCraftIndex() == 15 && gameoptions.getRandomType() == 'N'
                && !gameoptions.hypixelSaysMode && endedNaturally) {
            if (PBUtils.craft15PB(wsplayer)) return;
        }

        if (wsplayer.getCurrentCraftIndex() == wsplayer.getCrafts().size()
                && gameoptions.getRandomType() == 'N' && endedNaturally && gameoptions.hypixelSaysMode) {
            if (PBUtils.hypixelSaysAllCraftPB(wsplayer)) return;
        }

        if (wsplayer.getCurrentCraftIndex() == wsplayer.getCrafts().size()
                && gameoptions.getRandomType() == 'N' && endedNaturally && !gameoptions.hypixelSaysMode) {
            if (PBUtils.allCraftPB(wsplayer)) return;
        }

        if (gameoptions.isShowNonPBs() && wsplayer.getCurrentCraftIndex() == wsplayer.getCrafts().size()
                && gameoptions.getRandomType() == 'N' && endedNaturally && !gameoptions.hypixelSaysMode) {
            PBUtils.nonPB(wsplayer, 'A');
        }
        if (gameoptions.isShowNonPBs() && wsplayer.getCurrentCraftIndex() == 5
                && gameoptions.getRandomType() == 'N' && endedNaturally && !gameoptions.hypixelSaysMode) {
            PBUtils.nonPB(wsplayer, 'R');
        }
        if (gameoptions.isShowNonPBs() && wsplayer.getCurrentCraftIndex() == wsplayer.getCrafts().size()
                && gameoptions.getRandomType() == 'N' && endedNaturally && gameoptions.hypixelSaysMode) {
            PBUtils.nonPB(wsplayer, 'a');
        }
        if (gameoptions.isShowNonPBs() && wsplayer.getCurrentCraftIndex() == 5
                && gameoptions.getRandomType() == 'N' && endedNaturally && gameoptions.hypixelSaysMode) {
            PBUtils.nonPB(wsplayer, 'r');
        }

    }

    public static void endGame(WSPlayer wsplayer, boolean endedNaturally, String... timeTypes) {
        checkTimes(wsplayer, endedNaturally, timeTypes);

        wsplayer.getGameOptions().load(wsplayer.getDefaultGameOptions());
        ScoreboardUtils.endGame(wsplayer);
        MapUtils.restoreArea(wsplayer, true);
        wsplayer.getDecoBlocks().clear();

        PlayArea area = wsplayer.getPlayArea();
        if (area != null)
            area.setBusy(false);

        clearPlayer(wsplayer);
    }
}
