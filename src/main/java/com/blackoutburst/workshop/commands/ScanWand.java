package com.blackoutburst.workshop.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ScanWand  implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            ItemStack stack = new ItemStack(Material.BLAZE_ROD);
            ItemMeta meta = stack.getItemMeta();

            meta.setDisplayName("§6Scan wand");
            stack.setItemMeta(meta);

            ((Player)sender).getInventory().addItem(stack);
        }
        return true;
    }
}
