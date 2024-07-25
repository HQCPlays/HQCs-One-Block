package org.hqcplays.hqcsoneblock.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.OneBlockController;
import org.hqcplays.hqcsoneblock.PickaxeController;
import org.jetbrains.annotations.NotNull;

import org.hqcplays.hqcsoneblock.FleaListing;

public class FleaCommand implements CommandExecutor, Listener {


    private List<FleaListing> listings;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        
        // Checks if sender is a player, if so open the flea market, otherwise throw error message
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openFleaGUI(player);
            return true;
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }
    }


    public void openFleaGUI(Player player) {
        Inventory fleaGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "FLEA MARKET");

        player.openInventory(fleaGUI);
    }


        // This function controls what happens when the player clicks in the shop
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        // Obtain event information
        Player player = (Player) event.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        ItemStack clickedItem = event.getCurrentItem();
        String inventoryTitle = event.getView().getTitle();

        // Do nothing if player doesn't click anything
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        if (event.getClickedInventory() == event.getView().getTopInventory()) {

        }

        // Main menu controls
        // if (inventoryTitle.equals(ChatColor.DARK_GREEN + "Main Shop")) {
        //     event.setCancelled(true);

        //     if (event.getClickedInventory() == event.getView().getTopInventory()) {
        //         if (clickedItem.getType() == Material.CHEST) {
        //             openItemShopGUI(player);
        //         } else if (clickedItem.getType() == Material.DIAMOND_PICKAXE) {
        //             openPickaxeUpgradeShopGUI(player);
        //         } else if (clickedItem.getType() == Material.GRASS_BLOCK) {
        //             openBlockUpgradeShopGUI(player);
        //         }
        //     }
        // }
    }
}
