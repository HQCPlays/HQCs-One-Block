package org.hqcplays.hqcsoneblock.enchantments;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class EnchantmentAlgorithm {

    private Random random = new Random();

    // Randomly selects an integer between 0 (inclusive) and n (exclusive)
    private int randomInt(int n) {
        return random.nextInt(n);
    }

    // Randomly selects a floating-point number between 0.0 (inclusive) and 1.0 (exclusive)
    private float randomFloat() {
        return random.nextFloat();
    }

    // Rounds a floating-point number to the nearest integer
    private int round(float n) {
        return Math.round(n);
    }

    // Step 1: Apply modifiers to the base enchantment level
    public int applyModifiers(int baseEnchantmentLevel, int enchantability) {
        int randEnchantability = 1 + randomInt(enchantability / 4 + 1) + randomInt(enchantability / 4 + 1);
        int k = baseEnchantmentLevel + randEnchantability;

        float randBonusPercent = 1 + (randomFloat() + randomFloat() - 1) * 0.15f;
        int finalLevel = round(k * randBonusPercent);

        return Math.max(finalLevel, 5); // Ensure the final level is at least 1
    }

    // Step 2: Find possible enchantments and their power levels
    public List<Map.Entry<Enchantment, Integer>> findPossibleEnchantments(int modifiedLevel, ItemStack item) {
        EnchantmentFilter enchantmentFilter = new EnchantmentFilter();
        List<Enchantment> validEnchantments = enchantmentFilter.getValidEnchantments(item);
        List<Map.Entry<Enchantment, Integer>> possibleEnchants = new ArrayList<>();

        Map<Enchantment, EnchantmentData.EnchantmentDetails> enchantmentDetails = EnchantmentData.getEnchantmentDetails();

        for (Enchantment enchantment : validEnchantments) {
            EnchantmentData.EnchantmentDetails details = enchantmentDetails.get(enchantment);

            if (details != null && details.isObtainableFromEnchantingTable()) {
                int[] ranges = details.getRanges();

                for (int level = 0; level < ranges.length; level++) {
                    int minLevel = ranges[level];
                    int maxLevel = (level + 1 < ranges.length) ? ranges[level + 1] - 1 : Integer.MAX_VALUE;

                    if (modifiedLevel >= minLevel && modifiedLevel <= maxLevel) {
                        possibleEnchants.add(new AbstractMap.SimpleEntry<>(enchantment, level + 1));
                    }
                }
            }
        }

        // // Print the selected enchantments
        // System.out.println("Possible Enchantments:");
        // for (Map.Entry<Enchantment, Integer> entry : possibleEnchants) {
        //     System.out.println(entry.getKey().getName() + " Level " + entry.getValue());
        // }

        return possibleEnchants;
    }

    // Step 3: Select enchantments based on weighted randomness
    public Map<Enchantment, Integer> selectEnchantments(int modifiedLevel, List<Map.Entry<Enchantment, Integer>> possibleEnchantments) {
        Map<Enchantment, Integer> selectedEnchantments = new HashMap<>();
        Random random = new Random();

        while (!possibleEnchantments.isEmpty()) {
            int totalWeight = possibleEnchantments.stream()
                    .mapToInt(entry -> EnchantmentData.getEnchantmentDetails().get(entry.getKey()).getWeight())
                    .sum();

            int chosenWeight = random.nextInt(totalWeight);
            Map.Entry<Enchantment, Integer> selectedEnchantment = null;

            for (Map.Entry<Enchantment, Integer> entry : possibleEnchantments) {
                chosenWeight -= EnchantmentData.getEnchantmentDetails().get(entry.getKey()).getWeight();
                if (chosenWeight < 0) {
                    selectedEnchantment = entry;
                    break;
                }
            }

            if (selectedEnchantment != null) {
                selectedEnchantments.put(selectedEnchantment.getKey(), selectedEnchantment.getValue());
                possibleEnchantments.remove(selectedEnchantment);

                // Reduce the modified level and check if we should continue selecting
                modifiedLevel = modifiedLevel / 2;
                if (random.nextFloat() >= (modifiedLevel + 1) / 50.0f) {
                    break;
                }
            }
        }

        return selectedEnchantments;
    }

    public Map<Enchantment, Integer> enchantItem(int baseEnchantmentLevel, int enchantability, ItemStack item) {
        EnchantmentAlgorithm enchantmentAlgorithm = new EnchantmentAlgorithm();

        int modifiedLevel = enchantmentAlgorithm.applyModifiers(baseEnchantmentLevel, enchantability);
        List<Map.Entry<Enchantment, Integer>> possibleEnchantments = enchantmentAlgorithm.findPossibleEnchantments(modifiedLevel, item);
        Map<Enchantment, Integer> selectedEnchantments = enchantmentAlgorithm.selectEnchantments(modifiedLevel, possibleEnchantments);

        return selectedEnchantments;
    }

    public Map.Entry<Enchantment, Integer> obtainPossibleEnchant(int baseEnchantmentLevel, int enchantability, ItemStack item) {
        EnchantmentAlgorithm enchantmentAlgorithm = new EnchantmentAlgorithm();

        int modifiedLevel = enchantmentAlgorithm.applyModifiers(baseEnchantmentLevel, enchantability);
        List<Map.Entry<Enchantment, Integer>> possibleEnchantments = enchantmentAlgorithm.findPossibleEnchantments(modifiedLevel, item);
    
        int randomIndex = random.nextInt((possibleEnchantments.size()));

        return possibleEnchantments.get(randomIndex);
    }
}