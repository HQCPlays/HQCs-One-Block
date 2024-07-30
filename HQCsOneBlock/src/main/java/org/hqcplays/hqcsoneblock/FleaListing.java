package org.hqcplays.hqcsoneblock;

import java.time.Instant;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

public class FleaListing {
    
    // Properties of a listing
    private UUID id;
    private ItemStack item;
    private int price;
    private UUID seller;
    private long listingTime;
    private long expirationTime;

    // Main Constructor
    public FleaListing(ItemStack item, int price, UUID seller){
        this.id = UUID.randomUUID(); // Each Listing has a random (and statistically unique) UUID
        this.item = item;
        this.price = price;
        this.seller = seller;
        this.listingTime = Instant.now().getEpochSecond();
        this.expirationTime = listingTime+604800; // expiration time is 1 week (604800 seconds)
    }

    // Getters and Setters

    public UUID getId(){
        return id;
    }
    

    public ItemStack getItem(){
        return item;
    }
    
    public void setItemName(ItemStack item){
        this.item = item;
    }

    // Getters and Setters
    public int getPrice(){
        return price;
    }
    
    public void setPrice(int price){
        this.price = price;
    }

    public UUID getSeller(){
        return seller;
    }
    
    public void setSellerName(UUID seller){
        this.seller = seller;
    }

    public long getListingTime(){
        return listingTime;
    }
    
    public void setListingTime(long listingTime){
        this.listingTime = listingTime;
    }

    public long getExpirationTime(){
        return expirationTime;
    }
    
    public void setExpirationTime(long expirationTime){
        this.expirationTime = expirationTime;
    }
}
