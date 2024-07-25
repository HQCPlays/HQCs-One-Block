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
import org.hqcplays.hqcsoneblock.PlayerSaveData;
import org.hqcplays.hqcsoneblock.items.AmethystShardItems;
import org.hqcplays.hqcsoneblock.numberSheets.PricesSheet;
import org.jetbrains.annotations.NotNull;

import org.hqcplays.hqcsoneblock.FleaListing;
import org.hqcplays.hqcsoneblock.FleaListingUtils;
import org.hqcplays.hqcsoneblock.FleaMarket;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

import static org.hqcplays.hqcsoneblock.HQCsOneBlock.updateScoreboard;

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
        FleaListingUtils fleaListingUltils = new FleaListingUtils(HQCsOneBlock.getPlugin());

        PlayerSaveData playerData = HQCsOneBlock.playerData.get(player.getUniqueId());

        for (FleaListing listing : FleaMarket.getFleaListings()){
            ItemStack fleaListingItem = fleaListingUltils.createListingItem(listing);
            fleaGUI.addItem(fleaListingItem);
        }

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

        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "FLEA MARKET")) {
            event.setCancelled(true);
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return; // Do nothing if player doesn't click anything

            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                event.setCancelled(true); // prevents players from taking item

                if (clickedItem != null) {
                    FleaListingUtils listingUtils = new FleaListingUtils(HQCsOneBlock.getPlugin());
                    UUID listingID = listingUtils.getListingIdFromItem(clickedItem); //obtain the listing ID from the clicked item

                    if (listingID != null) {
                        FleaListing fleaListing = findListingById(listingID);
                        if (fleaListing != null) {
                            handleFleaPurchase(player, fleaListing);
                        }
                    }
                }
            }
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

    // returns the actual listing via the listingID
    private FleaListing findListingById(UUID listingId){
        for (FleaListing listing : FleaMarket.getFleaListings()) {
            if (listing.getId().equals(listingId)) {
                return listing;
            }
        }
        return null;
    }

    private void handleFleaPurchase(Player player, FleaListing listing){
        PlayerSaveData playerData = HQCsOneBlock.playerData.get(player.getUniqueId());
        double price = listing.getPrice();
        System.out.println("Player balance = " + playerData.balance);
        System.out.println("Listing price = " + price);
        if (playerData.balance >= price) {
            playerData.balance -= price;
            player.getInventory().addItem(listing.getItem());
            FleaMarket.removeListing(listing);
            player.closeInventory();
            openFleaGUI(player);
            updateScoreboard(player);
            player.sendMessage("Purchase Complete!");
        } else {
            player.sendMessage("Not enough funds!");
        }

    }
}
