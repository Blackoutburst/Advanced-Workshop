package com.blackoutburst.workshop;

import com.blackoutburst.workshop.commands.*;
import com.blackoutburst.workshop.core.Updater;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.events.listeners.EventListener;
import com.blackoutburst.workshop.utils.map.MapUtils;
import com.blackoutburst.workshop.utils.minecraft.ArmorUtils;
import com.jeff_media.armorequipevent.ArmorEquipEvent;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static final ViaAPI VIA_API = Via.getAPI();

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

    private void registerCommands() {
        getCommand("play").setExecutor(new Play());
        getCommand("l").setExecutor(new L());
        getCommand("spawn").setExecutor(new L());
        getCommand("scanwand").setExecutor(new ScanWand());
        getCommand("logicscan").setExecutor(new LogicScan());
        getCommand("decoscan").setExecutor(new DecoScan());
        getCommand("pastemap").setExecutor(new PasteMap());
        getCommand("setspawn").setExecutor(new SetSpawn());
        getCommand("recipeadd").setExecutor(new RecipeAdd());
        getCommand("setcraftlimit").setExecutor(new SetCraftLimit());
        getCommand("setcraftamount").setExecutor(new SetCraftLimit());
        getCommand("setunlimitedcraft").setExecutor(new SetUnlimitedCraft());
        getCommand("rngtype").setExecutor(new RngType());
        getCommand("reloadmaps").setExecutor(new ReloadMaps());
        getCommand("settimelimit").setExecutor(new SetTimeLimit());
        getCommand("setcountdown").setExecutor(new SetCountdown());
        getCommand("listmaps").setExecutor(new ListMaps());
        getCommand("deletemap").setExecutor(new DeleteMap());
        getCommand("shownonpbs").setExecutor(new ShowNonPbs());
    }

    @Override
    public void onEnable() {
        new File("./plugins/Workshop").mkdirs();
        new File("./plugins/Workshop/playerData").mkdirs();
        ArmorEquipEvent.registerListener(this);
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        registerCommands();
        MapUtils.loadPlayAreas();
        MapUtils.loadSpawn();

        new BukkitRunnable() {
            @Override
            public void run() {
                Updater.update();
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0L, 0L);
    }
}