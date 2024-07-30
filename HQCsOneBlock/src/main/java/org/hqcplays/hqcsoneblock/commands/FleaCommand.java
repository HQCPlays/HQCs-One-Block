package org.hqcplays.hqcsoneblock.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.OneBlockController;
import org.hqcplays.hqcsoneblock.PickaxeController;
import org.hqcplays.hqcsoneblock.PlayerSaveData;
import org.hqcplays.hqcsoneblock.items.AmethystShardItems;
import org.hqcplays.hqcsoneblock.numberSheets.PricesSheet;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import org.hqcplays.hqcsoneblock.FleaListing;
import org.hqcplays.hqcsoneblock.FleaListingUtils;
import org.hqcplays.hqcsoneblock.FleaMarket;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

import static org.hqcplays.hqcsoneblock.HQCsOneBlock.updateScoreboard;

public class FleaCommand implements CommandExecutor, Listener {


    Inventory fleaGUI;
    Inventory listGUI;
    int pageNum;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        
        // Checks if sender is a player, if so open the flea market, otherwise throw error message
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openFleaGUI(player, 2);
            return true;
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }
    }


    public void openFleaGUI(Player player, int pageNumber) {
        pageNum = pageNumber;
        fleaGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "FLEA MARKET              " + ChatColor.RED + "PAGE: " + pageNum);
        PlayerSaveData playerData = HQCsOneBlock.playerData.get(player.getUniqueId());

        // Add flea items to the screen. Each page can hold 36 items, thus the items we should add should consider which page we are on
        int startingIndex = (pageNum - 1) * 36;
        int endIndex = Math.min(startingIndex + 36, FleaMarket.getFleaListings().size());
        for (int i = startingIndex; i < endIndex; i++) {
            FleaListing listing = FleaMarket.getFleaListings().get(i);
            ItemStack fleaListingItem = FleaListingUtils.createListingItem(listing);
            fleaGUI.addItem(fleaListingItem);
        }


        // Create Information Item
        ItemStack listingInformation = new ItemStack(Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE);
        listingInformation.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta listingInformationMeta = listingInformation.getItemMeta();
        listingInformationMeta.setDisplayName(ChatColor.GOLD + "Listing Information:");
        List<Component> listingInformationlore = new ArrayList<>();
        listingInformationlore.add(Component.text("- Purchase an item on the Flea Market by clicking it and confirming your purchase!").color(NamedTextColor.WHITE));
        listingInformationlore.add(Component.text("- List items on the Flea Market via the /list command in chat!").color(NamedTextColor.WHITE));
        listingInformationMeta.lore(listingInformationlore);
        listingInformation.setItemMeta(listingInformationMeta);

        // Create next and previous page buttons
        ItemStack previousPage = new ItemStack(Material.FIREWORK_ROCKET);
        previousPage.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta previousPageMeta = previousPage.getItemMeta();
        previousPageMeta.setDisplayName(ChatColor.GREEN + "Previous page");
        previousPage.setItemMeta(previousPageMeta);

        
        ItemStack nextPage = new ItemStack(Material.FIREWORK_ROCKET);
        nextPage.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta nextPageMeta = nextPage.getItemMeta();
        nextPageMeta.setDisplayName(ChatColor.GREEN + "Next page");
        nextPage.setItemMeta(nextPageMeta);

        // Create and place line separating listings and menu controls
        for (int i = 36; i < 45; i++){
            ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            fleaGUI.setItem(i, pane);
        }

        // Place Information and menu control items
        fleaGUI.setItem(49, listingInformation);
        if (pageNum > 1) {
            fleaGUI.setItem(48, previousPage); 
        }
        if (FleaMarket.getFleaListings().size() / (pageNum*36.0) > 1.0) {
            fleaGUI.setItem(50, nextPage);
        }

        player.openInventory(fleaGUI);
    }

    public void openPurchaseConfirmationGUI(Player player, ItemStack fleaItem) {
        listGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "CONFIRM PURCHASE");

        // Create Cancel and Confirm items to act as buttons
        ItemStack cancelButton = new ItemStack(Material.RED_CONCRETE);
        ItemMeta cancelButtonMeta = cancelButton.getItemMeta();
        cancelButtonMeta.setDisplayName(ChatColor.GOLD + "CANCEL PURCHASE");
        cancelButton.setItemMeta(cancelButtonMeta);

        ItemStack confirmButton = new ItemStack(Material.LIME_CONCRETE);
        ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
        confirmButtonMeta.setDisplayName(ChatColor.GOLD + "CONFIRM PURCHASE");
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
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return; // Do nothing if player doesn't click anything
        ItemMeta clickedItemMeta = clickedItem.getItemMeta();
        String inventoryTitle = event.getView().getTitle();

        if (event.getView().getTitle().startsWith(ChatColor.DARK_GREEN + "FLEA MARKET")) {
            event.setCancelled(true);
            // If player is trying to click on a purchaseable item (first 36 slots of the flea)
            if (event.getSlot() < 37) {
                if (event.getClickedInventory() == event.getView().getTopInventory()) {
                    event.setCancelled(true); // prevents players from taking item

                    if (clickedItem != null) {
                        // FleaListing fleaListing = FleaListingUtils.findPostedListingByItem(clickedItem);
                        // if (fleaListing != null) {
                        //     handleFleaPurchase(player, fleaListing);
                        // }
                        openPurchaseConfirmationGUI(player, clickedItem);
                    }
                }
            // if player is clicking on the menu controls 
            } else if (event.getSlot() > 45){
                if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Previous page")) {
                    openFleaGUI(player, pageNum-1);
                }
                else if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Next page")) {
                    openFleaGUI(player, pageNum+1);
                }
            }
        }

        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "CONFIRM PURCHASE")) {
            event.setCancelled(true);
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return; // Do nothing if player doesn't click anything

            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                event.setCancelled(true); // prevents players from taking item
                ItemStack fleaItem = event.getClickedInventory().getItem(13); 

                if (clickedItem.getType() == Material.RED_CONCRETE) { // If player cancels the purchase
                    player.sendMessage(ChatColor.RED + "Purchased canceled!");
                    player.openInventory(fleaGUI);
                } else if (clickedItem.getType() == Material.LIME_CONCRETE) { // if player confirms the purchase
                    FleaListing listing = FleaListingUtils.findListingByItem(fleaItem);
                    handleFleaPurchase(player, fleaItem, listing);
                    player.closeInventory();
                } 
            }
        }
    }

    private void handleFleaPurchase(Player player, ItemStack fleaItem, FleaListing listing){
        if (listing.getSeller() != player.getUniqueId()){
            if (FleaListingUtils.findPostedListingByItem(fleaItem) != null) {
                PlayerSaveData playerData = HQCsOneBlock.playerData.get(player.getUniqueId());
                int price = listing.getPrice();
                if (!isInventoryFull(player)) {
                    if (playerData.balance >= price) {
                        playerData.balance -= price;
                        updateScoreboard(player);
                        player.getInventory().addItem(listing.getItem());
                        FleaMarket.removeListing(listing);
                        player.closeInventory();
                        openFleaGUI(player, 1);
    
                        // Give money to the seller
                        Player seller = Bukkit.getPlayer(listing.getSeller());
                        if (seller != null){
                            PlayerSaveData sellerData = HQCsOneBlock.playerData.get(seller.getUniqueId());
                            sellerData.balance += price;
                            updateScoreboard(player);
                        }
    
                        // Display confirmation message
                        ItemStack item = listing.getItem();
                        ItemMeta itemMeta = item.getItemMeta();
                        if (itemMeta != null && itemMeta.hasDisplayName()) { // For custom items
                            player.sendMessage(ChatColor.GREEN + "Successfully purchased " + item.getAmount() + " " + PlainTextComponentSerializer.plainText().serialize(itemMeta.displayName()) + " for $" + price);
                        } else { // for vanilla items
                            player.sendMessage(ChatColor.GREEN + "Successfully purchased " + item.getAmount() + " " + item.getType().toString() + " for $" + price);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Not enough funds!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "No space in inventory!");
                }
            } else {
                player.closeInventory();
                player.sendMessage(ChatColor.RED + "Listing has already been purchased or no longer exists!");
            }
        } else {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "You can not purchase your own listings!");
        }
        
        

    }

    public boolean isInventoryFull(Player player) {
        // Iterate through the player's inventory excluding armor slots and off hand (last 5 slots)
        for (int i = 0; i < player.getInventory().getSize()-5; i++) {
            ItemStack item = player.getInventory().getItem(i);
            // Check if any slot is empty (null)
            if (item == null) {
                return false; // Inventory is not full
            }
        }
        return true; // Inventory is full
    }
}
