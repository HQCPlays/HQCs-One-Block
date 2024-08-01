package org.hqcplays.hqcsoneblock.progression;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.PlayerSaveData;

import java.util.ArrayList;
import java.util.List;

public class Age {
    public int level;
    public String name;
    private final List<Goal> goals = new ArrayList<>();

    public Age(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public ItemStack createMenuItem() {
        ItemStack ageItem = new ItemStack(Material.PAPER);
        ItemMeta meta = ageItem.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + name);
        ageItem.setItemMeta(meta);
        return ageItem;
    }

    public void addGoal(Goal goal) {
        this.goals.add(goal);
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public Goal getGoalById(String goalId) {
        return this.goals.stream().filter(goal -> goal.id.equals(goalId)).findFirst().orElse(null);
    }

    public boolean hasUnlockedAllGoals(PlayerSaveData playerData) {
        return this.goals.stream().allMatch(goal -> playerData.unlockedGoals.contains(goal.id));
    }

    public void onEnter(Player player) {
        player.sendMessage(ChatColor.BLUE + "You have entered the " + this.name);
    }
}
