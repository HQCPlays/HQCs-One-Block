package org.hqcplays.hqcsoneblock;

import org.bukkit.Material;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerSaveData implements Serializable {
    public int balance;
    public Map<Material, Double> blockChances;
    public Set<Material> unlockedBlocks;

    public PlayerSaveData() {
        // Set up some default values for new players
        this.balance = 0;
        this.blockChances = OneBlockController.blockChances;
        this.unlockedBlocks = new HashSet<>();
    }
}
