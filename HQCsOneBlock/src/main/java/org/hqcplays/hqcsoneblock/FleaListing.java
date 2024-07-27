package org.hqcplays.hqcsoneblock;

import java.util.UUID;

import org.bukkit.inventory.ItemStack;

public class FleaListing {
    
    // Properties of a listing
    private UUID id;
    private ItemStack item;
    private int price;
    private UUID seller;

    // Main Constructor
    public FleaListing(ItemStack item, int price, UUID seller){
        this.id = UUID.randomUUID(); // Each Listing has a random (and statistically unique) UUID
        this.item = item;
        this.price = price;
        this.seller = seller;
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

    // Getters and Setters
    public UUID getSeller(){
        return seller;
    }
    
    public void setSellerName(UUID seller){
        this.seller = seller;
    }
}
