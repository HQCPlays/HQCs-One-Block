package org.hqcplays.hqcsoneblock.fleaMarket;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.PlayerSaveData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FleaMarket {
    
    private static ArrayList<FleaListing> listings = new ArrayList<>();
    private static ArrayList<FleaListing> pendingListings = new ArrayList<>();


    public FleaMarket(ArrayList<FleaListing> listings){
        for (FleaListing listing : listings){
            FleaMarket.listings.add(listing);
        }
    }

    public static ArrayList<FleaListing> getFleaListings(){
        listings.clear();
        for (PlayerSaveData playerData : HQCsOneBlock.dataManager.getAllPlayerData()) {
            listings.addAll(playerData.fleaListings);
        }
        return listings;
    }

    public static void addListing(FleaListing fleaListing, UUID player){
        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player, fleaListing.getSellerProfileNum());
        playerData.fleaListings.add(fleaListing);
    }

    public static void removeListing(FleaListing fleaListing, UUID player){
        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player, fleaListing.getSellerProfileNum());
        playerData.fleaListings.remove(fleaListing);
    }

    public static ArrayList<FleaListing> getPendingFleaListings(){
        return pendingListings;
    }

    public static void addPendingListing(FleaListing fleaListing){
        pendingListings.add(fleaListing);
    }

    public static void removePendingListing(FleaListing fleaListing){
        pendingListings.remove(fleaListing);
    }

    public static void clearFleaMarket(){
        listings.clear();
    }

}
