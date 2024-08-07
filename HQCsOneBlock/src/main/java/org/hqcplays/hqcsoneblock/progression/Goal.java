package org.hqcplays.hqcsoneblock.progression;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.PlayerSaveData;

import java.util.Collections;

public class Goal {
    public String id;
    public String name;
    public String description;
    private final Age age;

    public Goal(Age age, String id, String name, String description) {
        this.age = age;
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ItemStack createMenuItem(PlayerSaveData playerData) {
        ItemStack goalItem = new ItemStack(Material.BOOK);
        ItemMeta meta = goalItem.getItemMeta();
        if (playerData.unlockedGoals.contains(this.id) || playerData.currentAge > this.age.level) {
            meta.setDisplayName(ChatColor.GRAY + this.name);
            meta.setLore(Collections.singletonList(ChatColor.DARK_GRAY + "Unlocked!"));
        } else {
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + this.name);
            meta.setLore(Collections.singletonList(ChatColor.YELLOW + this.description));
        }
        goalItem.setItemMeta(meta);
        return goalItem;
    }

    public void onUnlocked(Player player) {
        player.sendMessage(ChatColor.GREEN + "You have unlocked the goal '" + this.name + "'!");
    }
}
