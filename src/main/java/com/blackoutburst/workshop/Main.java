package com.blackoutburst.workshop;

import com.blackout.npcapi.utils.SkinLoader;
import com.blackoutburst.workshop.commands.*;
import com.blackoutburst.workshop.core.EventListener;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.GameUtils;
import com.blackoutburst.workshop.utils.MapUtils;
import com.blackoutburst.workshop.utils.StringUtils;
import com.blackoutburst.workshop.utils.Webhook;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static Location spawn;

    public static List<PlayArea> playAreas = new ArrayList<>();

    public static List<WSPlayer> players = new ArrayList<>();

    public static String WEBHOOK = readEnv(0);

    private static String readEnv(int index) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(".env"));
            return lines.get(index).split("=")[1];
        } catch (Exception e) {
            System.err.println("Invalid .env file");
            System.exit(0);
        }
        return null;
    }

    @Override
    public void onEnable() {
        new File("./plugins/Workshop").mkdirs();
        new File("./plugins/Workshop/playerData").mkdirs();
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getCommand("play").setExecutor(new Play());
        getCommand("l").setExecutor(new L());
        getCommand("scanwand").setExecutor(new ScanWand());
        getCommand("logicscan").setExecutor(new LogicScan());
        getCommand("decoscan").setExecutor(new DecoScan());
        getCommand("pastemap").setExecutor(new PasteMap());
        getCommand("setspawn").setExecutor(new SetSpawn());
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("recipeadd").setExecutor(new RecipeAdd());
        getCommand("setcraftlimit").setExecutor(new SetCraftLimit());
        getCommand("setcraftamount").setExecutor(new SetCraftLimit());
        getCommand("setunlimitedcraft").setExecutor(new SetUnlimitedCraft());
        getCommand("rngtype").setExecutor(new RngType());
        getCommand("reloadmaps").setExecutor(new ReloadMaps());
        MapUtils.loadPlayAreas();
        MapUtils.loadSpawn();
        SkinLoader.loadSkinFromUUID(0, "92deafa9430742d9b00388601598d6c0");


        new BukkitRunnable() {
            @Override
            public void run() {
                int size = Main.players.size();
                for (int i = 0; i < size; i++) {
                    WSPlayer wsPlayer = Main.players.get(i);
                    if (wsPlayer == null) continue;
                    if (!wsPlayer.isInGame()) continue;


                    String gameTime = (wsPlayer.getTimers().getMapBegin() == null)
                    ?
                        "0.00s"
                    :
                        StringUtils.ROUND.format(((float) Duration.between(wsPlayer.getTimers().getMapBegin(), Instant.now()).toMillis() / 1000.0f)) + "s";


                    if (wsPlayer.isWaiting()) continue;

                    String roundTime = "0.00s";

                    if (wsPlayer.isNextRound()) {
                        if (wsPlayer.getTimers().getRoundBegin() != null) {
                            roundTime = StringUtils.ROUND.format(((float) Duration.between(wsPlayer.getTimers().getRoundBegin(), wsPlayer.getTimers().getRoundEnd()).toMillis() / 1000.0f)) + "s";
                        }

                        wsPlayer.getBoard().set(wsPlayer.getPlayer(), 12, "Game Time: §b0.00s");
                        wsPlayer.getBoard().set(wsPlayer.getPlayer(), 9, "Craft Time: §b0.00s");

                        wsPlayer.setNextRound(false);

                        int round_delay = 1;

                        boolean finished = GameUtils.prepareNextRound(wsPlayer);

                        if (finished) continue;

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                GameUtils.startRound(wsPlayer);
                            }
                        }.runTaskLater(Main.getPlugin(Main.class), round_delay * 20);
                    } else {
                        roundTime = StringUtils.ROUND.format(((float) Duration.between(wsPlayer.getTimers().getRoundBegin(), Instant.now()).toMillis() / 1000.0f)) + "s";
                    }

                    wsPlayer.getBoard().set(wsPlayer.getPlayer(), 12, "Game Time: §b" + gameTime);
                    wsPlayer.getBoard().set(wsPlayer.getPlayer(), 9, "Craft Time: §b" + roundTime);
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 1L, 0L);
    }
}