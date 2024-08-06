package org.hqcplays.hqcsoneblock;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PlayerSaveData implements Serializable {
    public String name;
    public int balance;
    public Map<Material, Double> blockChances;
    public Set<Material> unlockedBlocks;
    public ArrayList<FleaListing> fleaListings;
    public int listingLimit = 5;
    public ArrayList<ItemStack> mail;

    public PlayerSaveData(UUID playerUUID) {
        // Set up some default values for new players
        this.name = Bukkit.getPlayer(playerUUID).getName();
        this.balance = 0;
        this.blockChances = OneBlockController.blockChances;
        this.unlockedBlocks = new HashSet<>();
        this.fleaListings = new ArrayList<>();
        this.mail = new ArrayList<>();
    }
}
