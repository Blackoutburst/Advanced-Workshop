package com.blackoutburst.workshop;

import com.blackoutburst.workshop.commands.*;
import com.blackoutburst.workshop.core.EventListener;
import com.blackoutburst.workshop.core.PlayArea;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.GameUtils;
import com.blackoutburst.workshop.utils.MapUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {

    public static List<PlayArea> playAreas = new ArrayList<>();

    public static List<WSPlayer> players = new ArrayList<>();

    @Override
    public void onEnable() {
        new File("./plugins/Workshop").mkdirs();
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getCommand("play").setExecutor(new Play());
        getCommand("l").setExecutor(new L());
        getCommand("scanwand").setExecutor(new ScanWand());
        getCommand("logicscan").setExecutor(new LogicScan());
        getCommand("decoscan").setExecutor(new DecoScan());
        getCommand("pastemap").setExecutor(new PasteMap());
        MapUtils.loadPlayAreas();
    }

}