package com.blackoutburst.workshop.utils.minecraft;

import com.blackoutburst.workshop.core.game.MapMetadata;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorUtils {

    public static void setArmor(Player player, MapMetadata m) {
        ItemStack[] armor = m.getArmorContents();
        player.getInventory().setHelmet(armor[0]);
        player.getInventory().setChestplate(armor[1]);
        player.getInventory().setLeggings(armor[2]);
        player.getInventory().setBoots(armor[3]);
    }

    public static void removeArmor(Player player) {
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));
    }
}
