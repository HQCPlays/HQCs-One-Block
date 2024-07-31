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
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.PlayerSaveData;
import org.hqcplays.hqcsoneblock.fleaMarket.FleaListing;
import org.hqcplays.hqcsoneblock.fleaMarket.FleaListingUtils;
import org.hqcplays.hqcsoneblock.fleaMarket.FleaMarket;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

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
            if (args.length != 2) {
                player.sendMessage(ChatColor.RED + "Usage: /list <amount> <price>.");
                return true;
            }
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
                FleaMarket.addPendingListing(listing);
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
        cancelButtonMeta.setDisplayName(ChatColor.GOLD + "CANCEL LISTING");
        cancelButton.setItemMeta(cancelButtonMeta);

        ItemStack confirmButton = new ItemStack(Material.LIME_CONCRETE);
        ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
        confirmButtonMeta.setDisplayName(ChatColor.GOLD + "CONFIRM LISTING");
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
        PlayerSaveData playerData = HQCsOneBlock.playerData.get(playerUUID);
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
                    giveItemBack(player, fleaItem);
                    player.sendMessage(ChatColor.RED + "Listing canceled!");
                    player.closeInventory();
                    interactionFlag = 0; // POTENTIAL MASSIVE ERROR DOES NOT SCALE IN MULTIPLAYER
                } else if (clickedItem.getType() == Material.LIME_CONCRETE) { // if player confirms the listing
                    interactionFlag = 1;
                    FleaListing pendingListing = FleaListingUtils.findPendingListingByItem(fleaItem);
                    if (playerData.fleaListings.size() < playerData.listingLimit) {
                        if (pendingListing != null){
                            ItemStack originalItem = pendingListing.getItem();
                            ItemMeta originalItemMeta = originalItem.getItemMeta();
                            FleaMarket.addListing(pendingListing, player);
                            FleaMarket.removePendingListing(pendingListing);

                            // Display confirmation message
                            if (originalItemMeta != null && originalItemMeta.hasDisplayName()) { // For custom items
                                player.sendMessage(ChatColor.GREEN + "Successfully listed " + originalItem.getAmount() + " " + PlainTextComponentSerializer.plainText().serialize(originalItemMeta.displayName()) + " for $" + pendingListing.getPrice());
                            } else { // for vanilla items
                                player.sendMessage(ChatColor.GREEN + "Successfully listed " + originalItem.getAmount() + " " + originalItem.getType().toString() + " for $" + pendingListing.getPrice());
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Listing canceled! Item Not Found!");
                        }
                    } else { // if player has reached their max amount of concurrent listings
                        player.sendMessage(ChatColor.RED + "Listing canceled! Listing limit of " + playerData.listingLimit + " reached.");
                        giveItemBack(player, fleaItem);
                        FleaMarket.removePendingListing(pendingListing);
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
                giveItemBack(player, fleaItem);
                player.sendMessage(ChatColor.RED + "Listing canceled!");
            }
        }
    }

    private void giveItemBack(Player player, ItemStack fleaItem) {
        // Give the player their original item that they tried to list back
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.AIR){ // If the player tried to sell all of the original item stack
            FleaListing pendingListing = FleaListingUtils.findPendingListingByItem(fleaItem);
            if (pendingListing != null){
                ItemStack originalItem = pendingListing.getItem();
                FleaMarket.removePendingListing(pendingListing);
                player.getInventory().setItemInMainHand(originalItem);
            }
        } else { // If the player only sold part of the original item stack
            itemInMainHand.setAmount(itemInMainHand.getAmount() + fleaItem.getAmount());
        }
    }
}
