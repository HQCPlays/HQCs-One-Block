package org.hqcplays.hqcsoneblock.numberSheets;

import org.bukkit.Material;

import java.util.ArrayList;

public class PricesSheet {
    // Returns the items you can buy in the item shop
    public static ArrayList<Material> getItemShopItems() {
        ArrayList<Material> items = new ArrayList<>();

        items.add(Material.OAK_SAPLING);
        items.add(Material.COBBLESTONE);
        items.add(Material.OBSIDIAN);

        return items;
    }

    // This function returns the price for the item shop items
    public static int getItemShopPrices(Material itemType) {
        return switch (itemType) {
            case OAK_SAPLING -> 100;
            case COBBLESTONE -> 5;
            case OBSIDIAN -> 250;
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
        blocks.add(Material.LAPIS_ORE);
        blocks.add(Material.REDSTONE_ORE);
        blocks.add(Material.GLOWSTONE);

        return blocks;
    }

    // Returns the price for the block unlock shop prices
    public static int getBlockUnlockPrices(Material itemType) {
        return switch (itemType) {
            case GRAVEL -> 50;
            case COAL_ORE -> 100;
            case LAPIS_ORE -> 150;
            case IRON_ORE -> 250;
            case REDSTONE_ORE -> 300;
            case DIAMOND_ORE -> 500;
            case GOLD_ORE -> 350;
            case ANCIENT_DEBRIS -> 2000;
            case GLOWSTONE -> 3500;
            default -> 100000;
        };
    }
}
