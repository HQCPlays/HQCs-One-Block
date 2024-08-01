package org.hqcplays.hqcsoneblock;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IslandManager {
    public String getIslandWorldName(Player player) {
        return "island_" + player.getUniqueId() + "_" + HQCsOneBlock.dataManager.getSelectedProfile(player);
    }

    public World getOrCreateIslandWorld(Player player) {
        String playerWorldName = getIslandWorldName(player);

        World playerWorld;
        File worldFolder = new File(Bukkit.getWorldContainer(), playerWorldName);
        if (!worldFolder.exists()) {
            // World doesn't exist, clone it
            cloneWorld("world_island", playerWorldName);
            playerWorld = Bukkit.createWorld(new WorldCreator(playerWorldName));
        } else {
            // World exists and is loaded
            playerWorld = Bukkit.getWorld(playerWorldName);
            if (playerWorld == null) {
                // Load the world if it isn't already loaded
                playerWorld = Bukkit.createWorld(new WorldCreator(playerWorldName));
            }
        }

        return playerWorld;
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
