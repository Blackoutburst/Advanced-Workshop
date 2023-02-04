package com.blackoutburst.workshop;

import com.blackoutburst.workshop.commands.L;
import com.blackoutburst.workshop.commands.Play;
import com.blackoutburst.workshop.core.EventListener;
import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.utils.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static List<WSPlayer> players = new ArrayList<>();

    public static List<Craft> crafts = new ArrayList<>();

    @Override
    public void onEnable() {
        new File("./plugins/Workshop").mkdirs();
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getCommand("play").setExecutor(new Play());
        getCommand("l").setExecutor(new L());
        FileUtils.loadCraft();
    }

}