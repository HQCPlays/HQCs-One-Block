package org.hqcplays.hqcsoneblock;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

import static org.hqcplays.hqcsoneblock.HQCsOneBlock.playerBalances;
import static org.hqcplays.hqcsoneblock.HQCsOneBlock.updateScoreboard;

public class PickaxeController implements Listener {
    // Variables
    public static final HashMap<UUID, Material> playerPickaxes = new HashMap<>();

    // Functions
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        playerPickaxes.putIfAbsent(playerUUID, Material.WOODEN_PICKAXE);

        // Give the player a pickaxe if they don't already have a pickaxe
        if (!hasPickaxe(player)) {
            ItemStack pickaxe = new ItemStack(playerPickaxes.get(playerUUID));
            ItemMeta meta = pickaxe.getItemMeta();
            meta.setUnbreakable(true);
            meta.setDisplayName(ChatColor.AQUA + "Pickaxe");
            pickaxe.setItemMeta(meta);
            player.getInventory().addItem(pickaxe);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        playerPickaxes.putIfAbsent(playerUUID, Material.WOODEN_PICKAXE);

        // Give the player a pickaxe if they don't already have a pickaxe
        if (!hasPickaxe(player)) {
            ItemStack pickaxe = new ItemStack(playerPickaxes.get(playerUUID));
            ItemMeta meta = pickaxe.getItemMeta();
            meta.setUnbreakable(true);
            meta.setDisplayName(ChatColor.AQUA + "Pickaxe");
            pickaxe.setItemMeta(meta);
            player.getInventory().addItem(pickaxe);
        }
    }

    // Prevent the player from crafting pickaxes
    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        ItemStack result = event.getRecipe().getResult();
        if (result.getType().toString().endsWith("_PICKAXE")) {
            event.setCancelled(true);
            ((Player) event.getWhoClicked()).sendMessage(ChatColor.RED + "You cannot craft pickaxes. Please use the shop to upgrade your pickaxe.");
        }
    }

    private boolean hasPickaxe(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType().toString().endsWith("_PICKAXE")) {
                return true;
            }
        }

        return false;
    }

    public static void handlePickaxeUpgrade(Player player) {
        UUID playerUUID = player.getUniqueId();
        Material currentPickaxeType = playerPickaxes.get(playerUUID);
        Material newPickaxeType = PricesSheet.getPickaxeUpgrade(currentPickaxeType);
        int playerBalance = playerBalances.getOrDefault(playerUUID, 0);
        int price = PricesSheet.getPickaxeUpgradePrices(currentPickaxeType);

        if (playerBalance >= price) {
            playerBalances.put(playerUUID, playerBalance - price);
            replacePickaxe(player, newPickaxeType);
            updateScoreboard(player);
            player.sendMessage(ChatColor.GREEN + "You upgraded to a " + newPickaxeType.name() + " for " + price + " Block Coins.");
        } else {
            player.sendMessage(ChatColor.RED + "You don't have enough Block Coins.");
        }
    }

    private static void replacePickaxe(Player player, Material newPickaxeType) {
        UUID playerUUID = player.getUniqueId();

        playerPickaxes.put(playerUUID, newPickaxeType);
        ItemStack pickaxe = new ItemStack(playerPickaxes.get(playerUUID));
        ItemMeta meta = pickaxe.getItemMeta();
        meta.setUnbreakable(true);
        meta.setDisplayName(ChatColor.AQUA + "Pickaxe");
        pickaxe.setItemMeta(meta);
        player.getInventory().addItem(pickaxe);
    }
}
