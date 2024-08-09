package org.hqcplays.hqcsoneblock.progression;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.PlayerSaveData;

public class Progression implements Listener {
    public static final Age[] ages = new Age[3];

    private static final Age startingAge = new Age(0, "Starting Age");
    private static final Age expansionAge = new Age(1, "Expansion Age");
    private static final Age automationAge = new Age(2, "Automation Age");

    public static void init() {
        ages[0] = startingAge;
        ages[1] = expansionAge;
        ages[2] = automationAge;

        startingAge.addGoal(new Goal(startingAge, "create_island", "Create your Island", "Type /island to get started!"));
        startingAge.addGoal(new Goal(startingAge, "mine_oneblock", "Mine your OneBlock", "As you can see, it regenerates! It also gave you 1 Block Coin!"));
        startingAge.addGoal(new Goal(startingAge, "use_bcshop", "Go to the Block Coin Shop", "Type /bcshop or go to your menu to check out the BCShop!"));
        startingAge.addGoal(new Goal(startingAge, "upgrade_pickaxe", "Upgrade your pickaxe to stone", "Simply craft a new pickaxe out of stone!"));
        startingAge.addGoal(new Goal(startingAge, "buy_sapling", "Buy a Sapling", "Buy a sapling from the BCShop!"));

        expansionAge.addGoal(new Goal(expansionAge, "unlock_coal", "Unlock Coal Ore", "Unlock the coal ore in the BCShop! All unlocked blocks have a 1% chance of appearing!"));
        expansionAge.addGoal(new Goal(expansionAge, "unlock_iron", "Unlock Iron Ore", "Unlock the iron ore in the BCShop!"));
        expansionAge.addGoal(new Goal(expansionAge, "unlock_gold", "Unlock Gold Ore", "Unlock the gold ore in the BCShop!"));
        expansionAge.addGoal(new Goal(expansionAge, "unlock_diamond", "Unlock Diamond Ore", "Unlock the diamond ore in the BCShop!"));
        expansionAge.addGoal(new Goal(expansionAge, "unlock_netherite", "Unlock Ancient Debris", "Unlock the Ancient Debris from the BCShop!"));
    }

    public static void checkGoalUnlock(Player player, String goalId) {
        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
        Age age = ages[playerData.currentAge];
        if (!playerData.unlockedGoals.contains(goalId)) {
            Goal goal = age.getGoalById(goalId);
            if (goal == null)
                return;

            goal.onUnlocked(player);
            playerData.unlockedGoals.add(goalId);

            // Upgrade to the next age if needed
            if (age.hasUnlockedAllGoals(playerData) && ages.length >= playerData.currentAge + 1) {
                playerData.unlockedGoals.clear();
                playerData.currentAge++;
                ages[playerData.currentAge].onEnter(player);
            }
        }
    }

    @EventHandler
    public void onItemCrafted(CraftItemEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item != null && item.getType() == Material.STONE_PICKAXE) {
            checkGoalUnlock((Player) event.getWhoClicked(), "upgrade_pickaxe");
        }
    }
}
