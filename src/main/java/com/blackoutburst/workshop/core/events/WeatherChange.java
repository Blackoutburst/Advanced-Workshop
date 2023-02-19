package com.blackoutburst.workshop.core.events;

import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChange {

    public static void execute(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
