package org.hqcplays.hqcsoneblock;

import java.util.UUID;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class FleaListingUtils {
    private final NamespacedKey idKey;
    private final NamespacedKey priceKey;
    private final NamespacedKey sellerKey;

    public FleaListingUtils(Plugin plugin) {
        idKey = new NamespacedKey(plugin, "listing_id");
        priceKey = new NamespacedKey(plugin, "listing_price");
        sellerKey = new NamespacedKey(plugin, "listing_seller");
    }

    // Create a item to be displayed representing a flea market listing
    public ItemStack createListingItem(FleaListing listing) {
        ItemStack item = listing.getItem();
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, listing.getId().toString());
            meta.getPersistentDataContainer().set(priceKey, PersistentDataType.DOUBLE, listing.getPrice());
            meta.getPersistentDataContainer().set(sellerKey, PersistentDataType.STRING, listing.getSeller().toString());
            item.setItemMeta(meta);
        }

        return item;
    }

    // Obtain the id of the listing from the item
    public UUID getListingIdFromItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String idString = meta.getPersistentDataContainer().get(idKey, PersistentDataType.STRING);
            return UUID.fromString(idString);
        }
        return null;
    }
}