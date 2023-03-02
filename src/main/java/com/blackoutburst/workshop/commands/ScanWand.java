package com.blackoutburst.workshop.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ScanWand  implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            ItemStack stack = new ItemStack(Material.BLAZE_ROD);
            ItemMeta meta = stack.getItemMeta();
            if (meta != null)
                meta.setDisplayName("§6Scan wand");

            stack.setItemMeta(meta);

            player.getInventory().addItem(stack);
        }
        return true;
    }
}
