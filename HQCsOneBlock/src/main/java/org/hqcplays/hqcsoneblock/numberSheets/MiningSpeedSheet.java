package org.hqcplays.hqcsoneblock.numberSheets;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.PlayerSaveData;

import static org.hqcplays.hqcsoneblock.items.CustomPickaxes.*;

public class MiningSpeedSheet {
    // Pickaxe mining speed
    public static int getPickaxeMiningSpeed(ItemStack item) {
        if (item == null) return 1;

        if (item.isSimilar(woodPickaxe)) {
            return 20;
        }

        if (item.isSimilar(stonePickaxe)) {
            return 40;
        }

        if (item.isSimilar(lapisPickaxe)) {
            return 50;
        }

        if (item.isSimilar(ironPickaxe)) {
            return 60;
        }

        if (item.isSimilar(redstonePickaxe)) {
            return 70;
        }

        if (item.isSimilar(goldenPickaxe)) {
            return 80;
        }

        if (item.isSimilar(diamondPickaxe)) {
            return 100;
        }

        if (item.isSimilar(netheritePickaxe)) {
            return 125;
        }

        if (item.isSimilar(stardustPickaxe)) {
            return 150;
        }

        return 1;
    }

    // Block mining speed
    public static int getBlockMiningSpeed(Material block) {
        // Returns the amount of ticks to break the block (default is 20) (20 ticks / second)
        return switch (block) {
            case Material.COBBLESTONE -> 30;
            case Material.TALL_GRASS -> 0;
            case Material.SHORT_GRASS -> 0;
            default -> 10;
        };
    }

    // What hardness level a pickaxe can mine up to
    public static double getPickaxeHardness(ItemStack item) {
        if (item == null) return 0;

        if (item.isSimilar(woodPickaxe)) {
            return 1;
        }

        if (item.isSimilar(stonePickaxe)) {
            return 2;
        }

        if (item.isSimilar(lapisPickaxe)) {
            return 2;
        }

        if (item.isSimilar(ironPickaxe)) {
            return 3;
        }

        if (item.isSimilar(redstonePickaxe)) {
            return 3;
        }

        if (item.isSimilar(goldenPickaxe)) {
            return 4;
        }

        if (item.isSimilar(diamondPickaxe)) {
            return 5;
        }

        if (item.isSimilar(netheritePickaxe)) {
            return 6;
        }

        if (item.isSimilar(stardustPickaxe)) {
            return 7;
        }

        return 0.0;
    }

    // How hard a block is. All tools can only mine blocks with a hardness equal to or below their values
    public static double getBlockHardness(Material block) {
        return switch (block) {
            case Material.DIRT -> 0.0;
            case Material.OAK_WOOD -> 0.0;
            case Material.COBBLESTONE -> 1.0;
            case Material.AMETHYST_BLOCK -> 1.0;
            case Material.IRON_BLOCK -> 3.0;
            case Material.DIAMOND_BLOCK -> 3.0;
            default -> 0.0;
        };
    }

    public static boolean canMineBlock(ItemStack item, Material blockType) {
        double pickaxeHardness = getPickaxeHardness(item);
        double blockHardnessValue = getBlockHardness(blockType);
        return pickaxeHardness >= blockHardnessValue;
    }

    public static int ticksToMine(Player player, Material blockType) {
        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
        int miningSpeed = playerData.miningSpeed;
        int blockMiningSpeed = getBlockMiningSpeed(blockType);

        return Math.max(Math.round((float) (30 * blockMiningSpeed) / miningSpeed), 1);
    }
}
