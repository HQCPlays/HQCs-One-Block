package org.hqcplays.hqcsoneblock;

import java.util.UUID;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

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
            item.setItemMeta(meta);
        }

        return item;
    }

    // Obtain the id of the listing from the item
    public static UUID getListingIdFromItem(ItemStack item) {
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