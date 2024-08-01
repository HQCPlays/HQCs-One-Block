package org.hqcplays.hqcsoneblock;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hqcplays.hqcsoneblock.fleaMarket.FleaListing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerSaveData implements Serializable {
    // Important Player stats
    public int balance;
    public Map<Material, Double> blockChances;
    public Set<Material> unlockedBlocks;

    // Progression
    public int currentAge;
    public Set<String> unlockedGoals; // List of unlocked goal IDs for the current age

    // Flea Market
    public ArrayList<FleaListing> fleaListings;
    public int listingLimit = 5;
    public ArrayList<ItemStack> mail;

    // Upgradeable Player stats
    public double miningBonus;
    public double damageBonus;

    public PlayerSaveData() {
        // Set up some default values for new players
        this.balance = 0;
        this.blockChances = OneBlockController.blockChances;
        this.unlockedBlocks = new HashSet<>();
        this.currentAge = 0;
        this.unlockedGoals = new HashSet<>();
        this.fleaListings = new ArrayList<>();
        this.mail = new ArrayList<>();
    }
}
