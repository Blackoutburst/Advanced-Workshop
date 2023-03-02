package com.blackoutburst.workshop.commands;

import com.blackoutburst.workshop.Main;
import com.blackoutburst.workshop.utils.map.MapUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class DeleteMap implements CommandExecutor {

    public static void deleteLine(String fileName, int lineIndex) {
        Path inputFile = Path.of(fileName);
        Path tempFile = Path.of(fileName + ".temp");
        int lineNumber = 0;

        try (BufferedReader reader = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (lineNumber != lineIndex) {
                    writer.write(line);
                    writer.newLine();
                }
                lineNumber++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            Files.move(tempFile, inputFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cYou must specifie the map ID");
        }
        if (!args[0].matches("([0-9]+([.][0-9]+)?)?")) {
            sender.sendMessage("§cInvalid map ID");
            return true;
        }

        int index = Integer.parseInt(args[0]);

        if (index < 0 || index > Main.playAreas.size() -1) {
            sender.sendMessage("§cID is out of range");
            return true;
        }

        deleteLine("./plugins/Workshop/areas", index);
        Main.playAreas.clear();
        MapUtils.loadPlayAreas();
        sender.sendMessage("§aMap removed successfully!");
        return true;
    }

}
