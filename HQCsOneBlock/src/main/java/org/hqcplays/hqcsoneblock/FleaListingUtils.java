package org.hqcplays.hqcsoneblock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class FleaListingUtils {
    private static final NamespacedKey idKey = new NamespacedKey(HQCsOneBlock.getPlugin(), "listing_id");
    private static final NamespacedKey priceKey = new NamespacedKey(HQCsOneBlock.getPlugin(), "listing_price");
    private static final NamespacedKey sellerKey = new NamespacedKey(HQCsOneBlock.getPlugin(), "listing_seller");

    // Create a item to be displayed representing a flea market listing
    public static ItemStack createListingItem(FleaListing listing) {
        ItemStack item = listing.getItem().clone();
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, listing.getId().toString());
            meta.getPersistentDataContainer().set(priceKey, PersistentDataType.INTEGER, listing.getPrice());
            meta.getPersistentDataContainer().set(sellerKey, PersistentDataType.STRING, listing.getSeller().toString());

            // Make the details of the listing visible in the lore of the item
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Price: " + listing.getPrice()).color(NamedTextColor.GOLD));
            if (Bukkit.getPlayer(listing.getSeller()) != null) { // If the player UUID matches a real player
                lore.add(Component.text("Seller: " + Bukkit.getPlayer(listing.getSeller()).getName()).color(NamedTextColor.AQUA));
            } else { // If UUID of player selling item is unknown
                lore.add(Component.text("Seller: Unknown").color(NamedTextColor.AQUA)); 
            }
            if (meta.lore() != null) { // If item already has lore (custom items)
                List<Component> originalLore = meta.lore();
                originalLore.addAll(lore);
                meta.lore(originalLore);
            } else { // If item has no lore (vanilla items)
                meta.lore(lore);
            }
            item.setItemMeta(meta);
        }

        return item;
    }


    // returns the actual listing via the listingID
    public static FleaListing findPostedListingByItem(ItemStack item){
        UUID listingId = getListingIdFromItem(item);
        if (listingId != null) {
            for (FleaListing listing : FleaMarket.getFleaListings()) {
                if (listing.getId().equals(listingId)) {
                    return listing;
                }
            }
        }
        return null;
    }

    public static FleaListing findPendingListingByItem(ItemStack item){
        UUID listingId = getListingIdFromItem(item);
        if (listingId != null) {
            for (FleaListing listing : FleaMarket.getPendingFleaListings()) {
                if (listing.getId().equals(listingId)) {
                    return listing;
                }
            }
        }
        return null;
    }

    // returns the actual listing via the listingID
    public static FleaListing findListingByItem(ItemStack item){
        UUID listingId = getListingIdFromItem(item);
        if (listingId != null) {
            for (FleaListing listing : FleaMarket.getFleaListings()) {
                if (listing.getId().equals(listingId)) {
                    return listing;
                }
            }
        }
        return null;
    }

    // Obtain the id of the listing from the item
    private static UUID getListingIdFromItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String idString = meta.getPersistentDataContainer().get(idKey, PersistentDataType.STRING);
            return UUID.fromString(idString);
        }
        return null;
    }

    //Obtain FleaListing from the item
    public static FleaListing getListingFromItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            ItemStack originalItem = getOriginalItemFromItem(item);
            Integer price = meta.getPersistentDataContainer().get(priceKey, PersistentDataType.INTEGER);
            UUID seller = UUID.fromString(meta.getPersistentDataContainer().get(sellerKey, PersistentDataType.STRING));
            FleaListing fleaListing = new FleaListing(originalItem, price, seller);
            return fleaListing;
        }
        return null;
    }

    public static ItemStack getOriginalItemFromItem(ItemStack item){
        // Obtain the original item without its flea related attributes
        ItemStack originalItem = item.clone();
        ItemMeta originalItemMeta = originalItem.getItemMeta();
        originalItemMeta.getPersistentDataContainer().remove(idKey);
        originalItemMeta.getPersistentDataContainer().remove(priceKey);
        originalItemMeta.getPersistentDataContainer().remove(sellerKey);

        originalItem.setItemMeta(originalItemMeta);

        return originalItem;
    }
}