package org.hqcplays.hqcsoneblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.*;

public class IslandCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String playerWorldName = "island_" + player.getUniqueId();

            World playerWorld;
            File worldFolder = new File(Bukkit.getWorldContainer(), playerWorldName);
            if (!worldFolder.exists()) {
                // World doesn't exist, clone it
                cloneWorld("world_island", playerWorldName);
                playerWorld = Bukkit.createWorld(new WorldCreator(playerWorldName));
            } else {
                // World exists and is loaded
                playerWorld = Bukkit.getWorld(playerWorldName);
            }

            // Teleport the player to their island
            player.teleport(new Location(playerWorld, 0, 2, 0));
            player.sendMessage("Teleported to your island!");

            return true;
        }

        sender.sendMessage("This command can only be used by players.");
        return false;
    }

    private void cloneWorld(String originalWorldName, String newWorldName) {
        File originalWorldFolder = new File(Bukkit.getWorldContainer(), originalWorldName);
        File newWorldFolder = new File(Bukkit.getWorldContainer(), newWorldName);

        System.out.println("Cloning world from " + originalWorldFolder.getAbsolutePath() + " to " + newWorldFolder.getAbsolutePath());

        try {
            copyFolder(originalWorldFolder, newWorldFolder);
            System.out.println("World cloned successfully.");
        } catch (IOException e) {
            System.err.println("Error cloning world:");
            e.printStackTrace();
        }
    }


    private void copyFolder(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdir();
            }

            String[] children = source.list();
            for (String child : children) {
                copyFolder(new File(source, child), new File(destination, child));
            }
        } else {
            try (InputStream in = new FileInputStream(source); OutputStream out = new FileOutputStream(destination)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }
        }
    }
}
