package org.hqcplays.hqcsoneblock.commands;

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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.FleaListing;
import org.hqcplays.hqcsoneblock.FleaListingUtils;
import org.hqcplays.hqcsoneblock.FleaMarket;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.PlayerSaveData;
import org.jetbrains.annotations.NotNull;

public class ListCommand implements CommandExecutor, Listener {

    private int interactionFlag = 0; // tracks if players interact with the GUI in a meaningful way

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        
        // Checks if sender is a player, if so open the flea market, otherwise throw error message
        if (sender instanceof Player) {
            Player player = (Player) sender;

            /*
             * For /list we need two arguments and along currently held item:
             *      - an amount of the desired item (Integer)
             *      - a price (Integer)
             */

             try {
                // Check for valid integers
                int amount = Integer.parseInt(args[0]);
                int price = Integer.parseInt(args[1]);

                // Check for positive numbers
                if (amount < 0 || price < 0){
                    int errorThrow = Integer.parseInt("abc"); // force a NumberFormatException to be thrown
                }

                // Get the item in the player's main hand
                ItemStack itemInHand = player.getInventory().getItemInMainHand();

                // Check if the player is holding something
                if (itemInHand == null || itemInHand.getType() == Material.AIR) {
                    player.sendMessage(ChatColor.RED + "You must be holding an item to list.");
                    return true;
                }

                // Check if the player has enough of the item
                if (itemInHand.getAmount() < amount) {
                    player.sendMessage(ChatColor.RED + "You do not have enough of that item to list.");
                    return true;
                }

                // Convert the held item into a flea listing item
                ItemStack fleaItem = itemInHand.clone();
                fleaItem.setAmount(amount);
                FleaListing listing = new FleaListing(fleaItem, price, player.getUniqueId());
                ItemStack fleaListingItem = FleaListingUtils.createListingItem(listing);

                // subtract the amount of the item the player tried to list from their inventory
                itemInHand.setAmount(itemInHand.getAmount() - amount);
                openListGUI(player, fleaListingItem);
                return true;

            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Amount and price must be valid positive numbers.");
                return true;
            }
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }
    }


    public void openListGUI(Player player, ItemStack fleaItem) {
        Inventory listGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "CONFIRM LISTING");

        // Create Cancel and Confirm items to act as buttons
        ItemStack cancelButton = new ItemStack(Material.RED_CONCRETE);
        ItemMeta cancelButtonMeta = cancelButton.getItemMeta();
        cancelButtonMeta.setDisplayName(ChatColor.GOLD + "CANCEL");
        cancelButton.setItemMeta(cancelButtonMeta);

        ItemStack confirmButton = new ItemStack(Material.LIME_CONCRETE);
        ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
        confirmButtonMeta.setDisplayName(ChatColor.GOLD + "CONFIRM");
        confirmButton.setItemMeta(confirmButtonMeta);


        // Place the items in the GUI
        listGUI.setItem(13, fleaItem);
        listGUI.setItem(29, cancelButton);
        listGUI.setItem(33, confirmButton);

        player.openInventory(listGUI);
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

        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "CONFIRM LISTING")) {
            event.setCancelled(true);
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return; // Do nothing if player doesn't click anything

            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                event.setCancelled(true); // prevents players from taking item
                ItemStack fleaItem = event.getClickedInventory().getItem(13); 

                if (clickedItem.getType() == Material.RED_CONCRETE) { // If player cancels the listing
                    interactionFlag = 1;
                    // Give the player their original item that they tried to list back
                    ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
                    if (itemInMainHand.getType() == Material.AIR){ // If the player tried to sell all of the original item stack
                        ItemStack originalItem = FleaListingUtils.getOriginalItemFromItem(fleaItem);
                        player.getInventory().setItemInMainHand(originalItem);
                    } else { // If the player only sold part of the original item stack
                        itemInMainHand.setAmount(itemInMainHand.getAmount() + fleaItem.getAmount());
                    }
                    player.sendMessage(ChatColor.RED + "Listing canceled!");
                    player.closeInventory();
                    interactionFlag = 0; // POTENTIAL MASSIVE ERROR DOES NOT SCALE IN MULTIPLAYER
                } else if (clickedItem.getType() == Material.LIME_CONCRETE) { // if player confirms the listing
                    interactionFlag = 1;
                    FleaListing fleaListing = FleaListingUtils.getListingFromItem(fleaItem);
                    FleaMarket.addListing(fleaListing);
                    ItemStack fleaOriginalItem = fleaListing.getItem();
                    ItemMeta fleaOriginalItemMeta = fleaOriginalItem.getItemMeta();

                    // Display confirmation message
                    if (fleaOriginalItemMeta != null && fleaOriginalItemMeta.hasDisplayName()) { // For custom items
                        player.sendMessage(ChatColor.GREEN + "Successfully listed " + fleaOriginalItem.getAmount() + " " + fleaOriginalItemMeta.getDisplayName() + " for $" + fleaListing.getPrice());
                    } else { // for vanilla items
                        player.sendMessage(ChatColor.GREEN + "Successfully listed " + fleaOriginalItem.getAmount() + " " + fleaOriginalItem.getType().toString() + " for $" + fleaListing.getPrice());
                    }
                    
                    player.closeInventory();
                    interactionFlag = 0; // POTENTIAL MASSIVE ERROR DOES NOT SCALE IN MULTIPLAYER
                } 
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "CONFIRM LISTING")) {
            // If the interaction flag is 0, the player probably closed the inventory via the 'escape' key in which case we want to run this code
            if (interactionFlag == 0){
                Player player = (Player) event.getPlayer();
                ItemStack fleaItem = event.getInventory().getItem(13); 
                // Give the player their original item that they tried to list back
                ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
                if (itemInMainHand.getType() == Material.AIR){ // If the player tried to sell all of the original item stack
                    ItemStack originalItem = FleaListingUtils.getOriginalItemFromItem(fleaItem);
                    player.getInventory().setItemInMainHand(originalItem);
                } else { // If the player only sold part of the original item stack
                    itemInMainHand.setAmount(itemInMainHand.getAmount() + fleaItem.getAmount());
                }
                player.sendMessage(ChatColor.RED + "Listing canceled!"); 
            }
        }
    }
}
