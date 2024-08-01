package org.hqcplays.hqcsoneblock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.hqcplays.hqcsoneblock.items.CustomPickaxes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SaveDataManager {
    public static final int NUM_PROFILES = 3;

    private static class PlayerProfileSet implements Serializable {
        public int selectedProfile;
        public PlayerSaveData[] saveData;

        public PlayerProfileSet() {
            selectedProfile = 0;
            saveData = new PlayerSaveData[NUM_PROFILES];
            saveData[selectedProfile] = new PlayerSaveData();
        }
    }

    private Map<UUID, PlayerProfileSet> playerData = new HashMap<>();
    private final File file;
    private final Logger logger;

    public SaveDataManager(Logger logger) {
        this.file = new File("HQCsOneBlock.dat");
        this.logger = logger;
    }

    public void setupPlayer(Player player) {
        // Create new player data if none exists yet
        playerData.putIfAbsent(player.getUniqueId(), new PlayerProfileSet());
    }

    public PlayerSaveData getPlayerData(Player player) {
        PlayerProfileSet profileSet = playerData.get(player.getUniqueId());
        return profileSet.saveData[profileSet.selectedProfile];
    }

    public List<PlayerSaveData> getAllPlayerData() {
        List<PlayerSaveData> result = new ArrayList<>();
        playerData.forEach((uuid, playerProfileSet) -> {
            for (int i = 0; i < NUM_PROFILES; i++) {
                if (playerProfileSet.saveData[i] != null) {
                    result.add(playerProfileSet.saveData[i]);
                }
            }
        });
        return result;
    }

    public int getSelectedProfile(Player player) {
        return playerData.get(player.getUniqueId()).selectedProfile;
    }

    public void setSelectedProfile(Player player, int id) {
        PlayerProfileSet profileSet = playerData.get(player.getUniqueId());
        if (id < 0 || id >= NUM_PROFILES)
            return;
        if (profileSet.selectedProfile == id)
            return;

        // Get the old world name before switching the profile id
        String originalWorldName = HQCsOneBlock.islandManager.getIslandWorldName(player);

        // Save inventory contents
        profileSet.saveData[profileSet.selectedProfile].savedInventory = player.getInventory().getContents();

        profileSet.selectedProfile = id;
        if (profileSet.saveData[id] == null)
            profileSet.saveData[id] = new PlayerSaveData();

        HQCsOneBlock.updateScoreboard(player);

        // Load new inventory contents
        if (profileSet.saveData[profileSet.selectedProfile].savedInventory != null) {
            player.getInventory().setContents(profileSet.saveData[profileSet.selectedProfile].savedInventory);
        } else {
            // No inventory is saved for this profile, set up a new one
            ItemStack newPickaxe = new ItemStack(CustomPickaxes.woodPickaxe);
            player.getInventory().clear();
            player.getInventory().addItem(newPickaxe);
            player.getInventory().setItem(8, MenuItemController.MENU_ITEM);
        }

        // Switch worlds if the player is in their island world
        if (player.getWorld().getName().equals(originalWorldName)) {
            // Kick out any other players on the island to the lobby
            player.getWorld().getPlayers().stream().filter(p -> p != player).forEach(otherPlayer -> {
                otherPlayer.teleport(new Location(Bukkit.getWorld("world"), 0, 78, 0));
                otherPlayer.sendMessage(ChatColor.RED + player.getName() + " has switched to a different profile");
            });
            player.teleport(new Location(HQCsOneBlock.islandManager.getOrCreateIslandWorld(player), 0, 2, 0));
        }

        player.sendMessage(ChatColor.AQUA + "Switched to profile " + (id + 1));
    }

    public void loadSaveData() {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
            BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(gzipInputStream);
            playerData = (Map<UUID, PlayerProfileSet>) objectInputStream.readObject();
            objectInputStream.close();
            logger.info("Successfully loaded existing save data");
        } catch (FileNotFoundException e) {
            // No save file has been created yet, just use the new empty player data
        } catch (IOException e) {
            logger.severe("Unable to load player data: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.severe("Invalid or corrupt save data: " + e.getMessage());
        }
    }

    public void writeSaveData() {
        logger.info("Saving player data...");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);
            BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(gzipOutputStream);
            objectOutputStream.writeObject(playerData);
            objectOutputStream.close();
        } catch (IOException e) {
            logger.warning("Unable to save player data: " + e.getMessage());
        }
    }
}