package org.hqcplays.hqcsoneblock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.hqcplays.hqcsoneblock.items.ResourceGeneratorItems;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResourceGeneratorController implements Listener {
    public static HashMap<UUID, HashMap<String, Location>> generatorList = new HashMap<>();

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Block block = event.getBlock();

            ItemStack item = event.getItemInHand();

            if (item != null && isGenerator(item)) {
                Location location = block.getLocation();

                // Initialize the generator list for the player if it doesn't exist
                if (!generatorList.containsKey(uuid)) {
                    generatorList.put(uuid, new HashMap<>());
                }

                // Add the generator to the player's list
                HashMap<String, Location> playerGenerators = generatorList.get(uuid);
                playerGenerators.put(getGeneratorKey(item, location), location);
            }
    }

    public ResourceGeneratorController() {
        // Start the repeating task
        new BukkitRunnable() {
            @Override
            public void run() {
                // Iterate through all entries in generatorList
                for (Map.Entry<UUID, HashMap<String, Location>> entry : generatorList.entrySet()) {
                    UUID playerUUID = entry.getKey();
                    HashMap<String, Location> playerGenerators = entry.getValue();

                    Player player = Bukkit.getPlayer(playerUUID);

                    if (player != null) {
                        // Iterate through each generator for this player
                        for (Map.Entry<String, Location> genEntry : playerGenerators.entrySet()) {
                            Location location = genEntry.getValue();
                            Block block = location.getBlock();

                            if (block.getState() instanceof Chest) {
                                Chest chest = (Chest) block.getState();

                                if (genEntry.getKey().contains("Iron Generator")) {
                                    chest.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                                } else if (genEntry.getKey().contains("Coal Generator")) {
                                    chest.getInventory().addItem(new ItemStack(Material.COAL));
                                } else if (genEntry.getKey().contains("Gold Generator")) {
                                    chest.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                                } else if (genEntry.getKey().contains("Lapis Generator")) {
                                    chest.getInventory().addItem(new ItemStack(Material.LAPIS_LAZULI));
                                } else if (genEntry.getKey().contains("Redstone Generator")) {
                                    chest.getInventory().addItem(new ItemStack(Material.REDSTONE));
                                } else if (genEntry.getKey().contains("Diamond Generator")) {
                                    chest.getInventory().addItem(new ItemStack(Material.DIAMOND));
                                } else if (genEntry.getKey().contains("Ancient Debris Generator")) {
                                    chest.getInventory().addItem(new ItemStack(Material.ANCIENT_DEBRIS));
                                } else if (genEntry.getKey().contains("Cobblestone Generator")) {
                                    chest.getInventory().addItem(new ItemStack(Material.COBBLESTONE));
                                } else {
                                    chest.getInventory().addItem(new ItemStack(Material.DIRT));
                                }
                            } else {
                                // Remove the generator if the block is not a chest
                                playerGenerators.remove(genEntry.getKey());
                            }
                        }
                    } else {
                        // Remove the player's generators if they are offline
                        generatorList.remove(playerUUID);
                    }
                }
            }
        }.runTaskTimer(HQCsOneBlock.getPlugin(HQCsOneBlock.class), 0L, 400L);
    }

    private boolean isGenerator(ItemStack item) {
        if (item == null || item.getType() != Material.CHEST) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }

        String displayName = meta.getDisplayName();
        return displayName.equals(ChatColor.DARK_GREEN + "Iron Generator") ||
                displayName.equals(ChatColor.DARK_GREEN + "Coal Generator") ||
                displayName.equals(ChatColor.DARK_GREEN + "Gold Generator") ||
                displayName.equals(ChatColor.DARK_GREEN + "Lapis Generator") ||
                displayName.equals(ChatColor.DARK_GREEN + "Redstone Generator") ||
                displayName.equals(ChatColor.DARK_GREEN + "Diamond Generator") ||
                displayName.equals(ChatColor.DARK_GREEN + "Cobblestone Generator") ||
                displayName.equals(ChatColor.DARK_GREEN + "Ancient Debris Generator");
    }

    private String getGeneratorKey(ItemStack item, Location location) {
        return item.getItemMeta().getDisplayName() + "_" + location.toString();
    }
}
