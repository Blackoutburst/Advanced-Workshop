package com.blackoutburst.workshop.core.events.spigot;

import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChange {

    public static void execute(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
