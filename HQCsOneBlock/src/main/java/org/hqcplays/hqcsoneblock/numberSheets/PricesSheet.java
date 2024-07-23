package org.hqcplays.hqcsoneblock.numberSheets;

import org.bukkit.Material;

import java.util.ArrayList;

public class PricesSheet {
    // This function takes the player's current pickaxe and returns the price to upgrade it to the next tier
    public static int getPickaxeUpgradePrices(Material currentPickaxeType) {
        return switch (currentPickaxeType) {
            case WOODEN_PICKAXE -> 100;
            case STONE_PICKAXE -> 200;
            case IRON_PICKAXE -> 400;
            case GOLDEN_PICKAXE -> 800;
            case DIAMOND_PICKAXE -> 1600;
            case NETHERITE_PICKAXE -> 99999;
            default -> 999999;
        };
    }

    // This function takes the player's current pickaxe and returns the next tier of pickaxe
    public static Material getPickaxeUpgrade(Material currentPickaxeType) {
        return switch (currentPickaxeType) {
            case WOODEN_PICKAXE -> Material.STONE_PICKAXE;
            case STONE_PICKAXE -> Material.IRON_PICKAXE;
            case IRON_PICKAXE -> Material.GOLDEN_PICKAXE;
            case GOLDEN_PICKAXE -> Material.DIAMOND_PICKAXE;
            case DIAMOND_PICKAXE -> Material.NETHERITE_PICKAXE;
            case NETHERITE_PICKAXE -> Material.NETHERITE_PICKAXE; // Replace with custom pickaxe tiers
            default -> Material.STONE_PICKAXE;
        };
    }

    // Returns the items you can buy in the item shop
    public static ArrayList<Material> getItemShopItems() {
        ArrayList<Material> items = new ArrayList<>();

        items.add(Material.OAK_SAPLING);
        items.add(Material.COBBLESTONE);

        return items;
    }

    // This function returns the price for the item shop items
    public static int getItemShopPrices(Material itemType) {
        return switch (itemType) {
            case OAK_SAPLING -> 25;
            case COBBLESTONE -> 5;
            default -> 100000;
        };
    }

    // Returns the blocks you can upgrade your OneBlock with
    public static ArrayList<Material> getBlockUnlockBlocks() {
        ArrayList<Material> blocks = new ArrayList<>();

        blocks.add(Material.GRAVEL);
        blocks.add(Material.COAL_ORE);
        blocks.add(Material.IRON_ORE);
        blocks.add(Material.DIAMOND_ORE);
        blocks.add(Material.GOLD_ORE);
        blocks.add(Material.ANCIENT_DEBRIS);

        return blocks;
    }

    // Returns the price for the block unlock shop prices
    public static int getBlockUnlockPrices(Material itemType) {
        return switch (itemType) {
            case GRAVEL -> 50;
            case COAL_ORE -> 100;
            case IRON_ORE -> 250;
            case DIAMOND_ORE -> 500;
            case GOLD_ORE -> 350;
            case ANCIENT_DEBRIS -> 2000;
            default -> 100000;
        };
    }
}
