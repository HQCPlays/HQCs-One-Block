package org.hqcplays.hqcsoneblock;



import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.inventory.ItemStack;

public class FleaMarket {
    
    private static ArrayList<FleaListing> listings = new ArrayList<>();


    public FleaMarket(ArrayList<FleaListing> listings){
        for (FleaListing listing : listings){
            FleaMarket.listings.add(listing);
        }
    }

    public static ArrayList<FleaListing> getFleaListings(){
        return listings;
    }

    public static void addListing(FleaListing fleaListing){
        listings.add(fleaListing);
    }

    public static void removeListing(FleaListing fleaListing){
        listings.remove(fleaListing);
    }

    public static void clearFleaMarket(){
        listings.clear();
    }

}
