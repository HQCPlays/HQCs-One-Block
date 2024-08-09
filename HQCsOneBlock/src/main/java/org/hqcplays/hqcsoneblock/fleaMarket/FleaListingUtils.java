package org.hqcplays.hqcsoneblock.fleaMarket;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.PlayerSaveData;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class FleaListingUtils {
    private static final NamespacedKey idKey = new NamespacedKey(HQCsOneBlock.getPlugin(), "listing_id");
    private static final NamespacedKey priceKey = new NamespacedKey(HQCsOneBlock.getPlugin(), "listing_price");
    private static final NamespacedKey sellerKey = new NamespacedKey(HQCsOneBlock.getPlugin(), "listing_seller");
    private static final NamespacedKey listingTimeKey = new NamespacedKey(HQCsOneBlock.getPlugin(), "listing_time");
    private static final NamespacedKey expirationTimeKey = new NamespacedKey(HQCsOneBlock.getPlugin(), "expiration_time");

    // Create a item to be displayed representing a flea market listing
    public static ItemStack createListingItem(FleaListing listing) {
        ItemStack item = listing.getItem().clone();
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, listing.getId().toString());
            meta.getPersistentDataContainer().set(priceKey, PersistentDataType.INTEGER, listing.getPrice());
            meta.getPersistentDataContainer().set(sellerKey, PersistentDataType.STRING, listing.getSeller().toString());
            meta.getPersistentDataContainer().set(listingTimeKey, PersistentDataType.LONG, listing.getListingTime());
            meta.getPersistentDataContainer().set(expirationTimeKey, PersistentDataType.LONG, listing.getExpirationTime());

            // Make the details of the listing visible in the lore of the item
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Price: $" + listing.getPrice()).color(NamedTextColor.GOLD));
            lore.add(Component.text("Seller: " + HQCsOneBlock.dataManager.getPlayerData(listing.getSeller(), listing.getSellerProfileNum()).name).color(NamedTextColor.AQUA));

            // Listing and expiration date logic
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); // Define the desired format

            Instant listingInstant = Instant.ofEpochSecond(listing.getListingTime()); // Convert epoch seconds to Instant
            LocalDateTime listingDateTime = LocalDateTime.ofInstant(listingInstant, ZoneId.systemDefault()); // Convert Instant to LocalDateTime in system default time zone
            String formattedListingDateTime = listingDateTime.format(formatter); // Format the LocalDateTime to the desired string
            lore.add(Component.text("Listing Date: " + formattedListingDateTime).color(NamedTextColor.DARK_PURPLE));

            Instant expirationInstant = Instant.ofEpochSecond(listing.getExpirationTime()); // Convert epoch seconds to Instant
            LocalDateTime expirationDateTime = LocalDateTime.ofInstant(expirationInstant, ZoneId.systemDefault()); // Convert Instant to LocalDateTime in system default time zone
            String formattedExpirationDateTime = expirationDateTime.format(formatter); // Format the LocalDateTime to the desired string
            lore.add(Component.text("Expiration Date: " + formattedExpirationDateTime).color(NamedTextColor.DARK_RED));

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

    public static void checkExpiredListings(){
        Iterator<FleaListing> iterator = FleaMarket.getFleaListings().iterator();
        while (iterator.hasNext()) {
            FleaListing fleaListing = iterator.next();
            if (isExpired(fleaListing)) {
                iterator.remove();
                Player seller = Bukkit.getPlayer(fleaListing.getSeller());
                    if (seller != null){
                        ItemStack item = fleaListing.getItem();
                        ItemMeta itemMeta = item.getItemMeta();
                        //seller.getInventory().addItem(fleaListing.getItem()); // Item will be lost if players inventory is full, needs an inbox system
                        PlayerSaveData sellerData = HQCsOneBlock.dataManager.getPlayerData(seller.getUniqueId(), fleaListing.getSellerProfileNum());
                        sellerData.mail.add(fleaListing.getItem());
                        seller.sendMessage(ChatColor.RED + "Your offer: " + item.getAmount() + " " + PlainTextComponentSerializer.plainText().serialize(itemMeta.displayName()) + " for $" + fleaListing.getPrice() + " has expired!");
                        seller.sendMessage(ChatColor.GOLD + "Reclaim your expired item in your inbox!");
                    }
            }
        }
    }


    private static boolean isExpired(FleaListing listing) {
        long expirationTime = listing.getExpirationTime();
        long currentTime = Instant.now().getEpochSecond();
        return currentTime >= expirationTime;
    }
}