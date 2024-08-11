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
import org.hqcplays.hqcsoneblock.items.BlockCoin;
import org.hqcplays.hqcsoneblock.numberSheets.PricesSheet;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import org.hqcplays.hqcsoneblock.fleaMarket.FleaListing;
import org.hqcplays.hqcsoneblock.fleaMarket.FleaListingUtils;
import org.hqcplays.hqcsoneblock.fleaMarket.FleaMarket;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

import static org.hqcplays.hqcsoneblock.HQCsOneBlock.updateScoreboard;

public class FleaCommand implements CommandExecutor, Listener {


    Inventory fleaGUI;
    Inventory purchaseGUI;
    Inventory myListingsGUI;
    Inventory removeListingGUI;
    int pageNum;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        
        // Checks if sender is a player, if so open the flea market, otherwise throw error message
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openFleaGUI(player, 1);
            return true;
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }
    }


    public void openFleaGUI(Player player, int pageNumber) {
        pageNum = pageNumber;

        fleaGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "FLEA MARKET              " + ChatColor.RED + "PAGE: " + pageNum);
        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
        // playerData.balance = 10000;
        // updateScoreboard(player);
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

        // Create 'my listings' button
        ItemStack myListings = new ItemStack(Material.PAPER);
        myListings.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta myListingsMeta = myListings.getItemMeta();
        myListingsMeta.setDisplayName(ChatColor.GREEN + "My Listings");
        myListings.setItemMeta(myListingsMeta);

        // Create inbox button
        ItemStack inbox = new ItemStack(Material.WRITABLE_BOOK);
        inbox.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta inboxMeta = myListings.getItemMeta();
        inboxMeta.setDisplayName(ChatColor.GREEN + "Inbox");
        inbox.setItemMeta(inboxMeta);

        // Create and place line separating listings and menu controls
        for (int i = 36; i < 45; i++){
            ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            fleaGUI.setItem(i, pane);
        }

        // Place page controls
        if (pageNum > 1) {
            fleaGUI.setItem(48, previousPage); 
        }
        if (FleaMarket.getFleaListings().size() / (pageNum*36.0) > 1.0) {
            fleaGUI.setItem(50, nextPage);
        }

        // Place information item
        fleaGUI.setItem(49, listingInformation);
        // Place 'my listings' button
        fleaGUI.setItem(45, myListings);
        // Place 'my listings' button
        fleaGUI.setItem(46, inbox);

        player.openInventory(fleaGUI);
    }

    public void openPurchaseConfirmationGUI(Player player, ItemStack fleaItem) {
        purchaseGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "CONFIRM PURCHASE");

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
        purchaseGUI.setItem(13, fleaItem);
        purchaseGUI.setItem(29, cancelButton);
        purchaseGUI.setItem(33, confirmButton);

        player.openInventory(purchaseGUI);
    }

    public void openMyListingsGUI(Player player) {
        myListingsGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "MY LISTINGS");
        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
        ArrayList<FleaListing> playerFleaListings = playerData.fleaListings;

        // Create and place players listings
        for (FleaListing listing : playerFleaListings) {
            ItemStack fleaListingItem = FleaListingUtils.createListingItem(listing);
            myListingsGUI.addItem(fleaListingItem);
        }

        // Create and place line separating listings and menu controls
        for (int i = 36; i < 45; i++){
            ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            myListingsGUI.setItem(i, pane);
        }

        // Create Information Item
        ItemStack myListingInformation = new ItemStack(Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE);
        myListingInformation.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta myListingInformationMeta = myListingInformation.getItemMeta();
        myListingInformationMeta.setDisplayName(ChatColor.GOLD + "My listings information:");
        List<Component> myListingInformationlore = new ArrayList<>();
        myListingInformationlore.add(Component.text("- A list of all your current listings can be found above.").color(NamedTextColor.WHITE));
        myListingInformationlore.add(Component.text("- To remove a listing, click it and confirm the removal!").color(NamedTextColor.WHITE));
        myListingInformationMeta.lore(myListingInformationlore);
        myListingInformation.setItemMeta(myListingInformationMeta);

        // Create back button
        ItemStack backButton = new ItemStack(Material.EMERALD);
        backButton.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta backButtonMeta = backButton.getItemMeta();
        backButtonMeta.setDisplayName(ChatColor.GREEN + "Flea Market");
        backButton.setItemMeta(backButtonMeta);

        // Place information item and back button
        myListingsGUI.setItem(49, myListingInformation);
        myListingsGUI.setItem(45, backButton);

        player.openInventory(myListingsGUI);
    }

    public void openRemoveListingGUI(Player player, ItemStack fleaItem) {
        removeListingGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "REMOVE LISTING");

        // Create Cancel and Confirm items to act as buttons
        ItemStack cancelButton = new ItemStack(Material.RED_CONCRETE);
        ItemMeta cancelButtonMeta = cancelButton.getItemMeta();
        cancelButtonMeta.setDisplayName(ChatColor.GOLD + "CANCEL REMOVAL");
        cancelButton.setItemMeta(cancelButtonMeta);

        ItemStack confirmButton = new ItemStack(Material.LIME_CONCRETE);
        ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
        confirmButtonMeta.setDisplayName(ChatColor.GOLD + "CONFIRM REMOVAL");
        confirmButton.setItemMeta(confirmButtonMeta);


        // Place the items in the GUI
        removeListingGUI.setItem(13, fleaItem);
        removeListingGUI.setItem(29, cancelButton);
        removeListingGUI.setItem(33, confirmButton);

        player.openInventory(removeListingGUI);
    }


    // This function controls what happens when the player clicks in the shop
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        // Obtain event information
        Player player = (Player) event.getWhoClicked();
        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
        UUID playerUUID = player.getUniqueId();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return; // Do nothing if player doesn't click anything
        ItemMeta clickedItemMeta = clickedItem.getItemMeta();
        String inventoryTitle = event.getView().getTitle();

        // Main Flea Market GUI
        if (event.getView().getTitle().startsWith(ChatColor.DARK_GREEN + "FLEA MARKET")) {
            event.setCancelled(true); // prevents players from taking item
            // If player is trying to click on a purchaseable item (first 36 slots of the flea)
            if (event.getSlot() < 36) {
                if (event.getClickedInventory() == event.getView().getTopInventory()) {
                    if (clickedItem != null) {
                        openPurchaseConfirmationGUI(player, clickedItem);
                    }
                }
            // if player is clicking on the menu controls 
            } else if (event.getSlot() > 44){
                if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Previous page")) {
                    openFleaGUI(player, pageNum-1);
                }
                else if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Next page")) {
                    openFleaGUI(player, pageNum+1);
                }
                else if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("My Listings")) {
                    openMyListingsGUI(player);
                }
                else if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Inbox")) {
                    player.performCommand("inbox");
                }
            }
        }
        // Confirm Purchase GUI
        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "CONFIRM PURCHASE")) {
            event.setCancelled(true);
            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                event.setCancelled(true); // prevents players from taking item
                ItemStack fleaItem = event.getClickedInventory().getItem(13); 

                if (event.isLeftClick()) {
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
        // My listings GUI
        if (event.getView().getTitle().startsWith(ChatColor.DARK_GREEN + "MY LISTINGS")) {
            event.setCancelled(true);
            // If player is trying to click on a purchaseable item (first 36 slots of the flea)
            if (event.getSlot() < 36) {
                if (event.getClickedInventory() == event.getView().getTopInventory()) {
                    if (clickedItem != null) {
                        openRemoveListingGUI(player, clickedItem);
                    }
                }
            // if player is clicking on the menu controls 
            } else if (event.getSlot() > 44){
                if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Flea Market")) {
                    openFleaGUI(player, 1);
                }
            }
        }
        // Confirm Removal GUI
        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "REMOVE LISTING")) {
            event.setCancelled(true); // prevents players from taking item
            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                ItemStack fleaItem = event.getClickedInventory().getItem(13); 

                if (clickedItem.getType() == Material.RED_CONCRETE) { // If player cancels the removal
                    player.sendMessage(ChatColor.RED + "Listing removal canceled!");
                    player.openInventory(myListingsGUI);
                } else if (clickedItem.getType() == Material.LIME_CONCRETE) { // if player confirms the removal
                    FleaListing listing = FleaListingUtils.findListingByItem(fleaItem);
                    FleaMarket.removeListing(listing, player.getUniqueId());

                    // Give the player their item back
                    playerData.mail.add(listing.getItem());

                    // Display confirmation message
                    ItemStack item = listing.getItem();
                    ItemMeta itemMeta = item.getItemMeta();
                    if (itemMeta != null && itemMeta.hasDisplayName()) { // For custom items
                        player.sendMessage(ChatColor.GREEN + "Successfully removed listing: " + item.getAmount() + " " + PlainTextComponentSerializer.plainText().serialize(itemMeta.displayName()) + " for $" + listing.getPrice() + " from the Flea Market!");
                    } else { // for vanilla items
                        player.sendMessage(ChatColor.GREEN + "Successfully removed listing: " + item.getAmount() + " " + item.getType().toString() + " for $" + listing.getPrice() + " from the Flea Market!");
                        player.sendMessage(ChatColor.GOLD + "Reclaim your expired item in your inbox!");
                    }
                    openMyListingsGUI(player);
                } 
            }
        }
    }

    private void handleFleaPurchase(Player player, ItemStack fleaItem, FleaListing listing){
        System.out.println("Run");
        if (listing.getSeller() != player.getUniqueId()){
            if (FleaListingUtils.findPostedListingByItem(fleaItem) != null) {
                PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
                UUID seller = listing.getSeller();
                int price = listing.getPrice();
                if (playerData.balance >= price) {
                    playerData.balance -= price;
                    updateScoreboard(player);
                    //player.getInventory().addItem(listing.getItem());
                    playerData.mail.add(listing.getItem());
                    player.closeInventory();
                    openFleaGUI(player, 1);

                    // Give money to the seller
                    if (HQCsOneBlock.dataManager.getPlayerData(seller, listing.getSellerProfileNum()) != null){
                        PlayerSaveData sellerData = HQCsOneBlock.dataManager.getPlayerData(seller, listing.getSellerProfileNum());
                        FleaMarket.removeListing(listing, seller);
                        sellerData.mail.add(BlockCoin.createBlockCoin(price));
                    }

                    // Display confirmation message
                    ItemStack item = listing.getItem();
                    ItemMeta itemMeta = item.getItemMeta();
                    if (itemMeta != null && itemMeta.hasDisplayName()) { // For custom items
                        player.sendMessage(ChatColor.GREEN + "Successfully purchased " + item.getAmount() + " " + PlainTextComponentSerializer.plainText().serialize(itemMeta.displayName()) + " for $" + price);
                        player.sendMessage(ChatColor.GOLD + "Claim your purchase in your inbox!");
                        if (Bukkit.getPlayer(seller) != null) {
                            Bukkit.getPlayer(seller).sendMessage(ChatColor.GREEN + "Your offer: " + item.getAmount() + " " + PlainTextComponentSerializer.plainText().serialize(itemMeta.displayName()) + " for $" + price + " has been purchased!");
                            Bukkit.getPlayer(seller).sendMessage(ChatColor.GOLD + "Claim your BlockCoins in your inbox!");
                        }
                    } else { // for vanilla items
                        player.sendMessage(ChatColor.GREEN + "Successfully purchased " + item.getAmount() + " " + item.getType().toString() + " for $" + price);
                        player.sendMessage(ChatColor.GOLD + "Claim your purchase in your inbox!");
                        if (Bukkit.getPlayer(seller) != null) {
                            Bukkit.getPlayer(seller).sendMessage(ChatColor.GREEN + "Your offer: " + item.getAmount() + " " + item.getType().toString() + " for $" + price + " has been purchased!");
                            Bukkit.getPlayer(seller).sendMessage(ChatColor.GOLD + "Claim your BlockCoins in your inbox!");
                        }
                    }
                } else {
                        player.sendMessage(ChatColor.RED + "Not enough funds!");
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