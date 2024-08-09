package org.hqcplays.hqcsoneblock.customBlockBreaking;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ToolChecker {
    public static boolean isToolEffective(ItemStack tool, Material blockMaterial) {
        if (tool == null || tool.getType() == Material.AIR) return true;

        switch (tool.getType()) {
            case WOODEN_PICKAXE:
            case STONE_PICKAXE:
            case IRON_PICKAXE:
            case GOLDEN_PICKAXE:
            case DIAMOND_PICKAXE:
            case NETHERITE_PICKAXE:
                return isPickaxeEffective(blockMaterial);

            case WOODEN_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLDEN_AXE:
            case DIAMOND_AXE:
            case NETHERITE_AXE:
                return isAxeEffective(blockMaterial);

            case WOODEN_SHOVEL:
            case STONE_SHOVEL:
            case IRON_SHOVEL:
            case GOLDEN_SHOVEL:
            case DIAMOND_SHOVEL:
            case NETHERITE_SHOVEL:
                return isShovelEffective(blockMaterial);

            case SHEARS:
                return isShearEffective(blockMaterial);

            default:
                return true;
        }
    }

    private static boolean isPickaxeEffective(Material blockMaterial) {
        return blockMaterial.isBlock() && (
                blockMaterial == Material.STONE ||
                        blockMaterial == Material.COBBLESTONE ||
                        blockMaterial == Material.IRON_ORE ||
                        blockMaterial == Material.DIAMOND_ORE ||
                        blockMaterial == Material.GOLD_ORE ||
                        blockMaterial == Material.REDSTONE_ORE ||
                        blockMaterial == Material.EMERALD_ORE ||
                        blockMaterial == Material.NETHERITE_BLOCK ||
                        blockMaterial == Material.NETHER_GOLD_ORE ||
                        blockMaterial == Material.ANCIENT_DEBRIS ||
                        blockMaterial == Material.OBSIDIAN ||
                        blockMaterial == Material.BRICKS ||
                        blockMaterial == Material.DEEPSLATE ||
                        blockMaterial == Material.GLOWSTONE ||
                        blockMaterial == Material.AMETHYST_BLOCK ||
                        blockMaterial == Material.FURNACE

        );
    }

    private static boolean isAxeEffective(Material blockMaterial) {
        return blockMaterial.isBlock() && (
                blockMaterial == Material.OAK_LOG ||
                        blockMaterial == Material.SPRUCE_LOG ||
                        blockMaterial == Material.BIRCH_LOG ||
                        blockMaterial == Material.JUNGLE_LOG ||
                        blockMaterial == Material.CHERRY_LOG ||
                        blockMaterial == Material.ACACIA_LOG ||
                        blockMaterial == Material.DARK_OAK_LOG ||
                        blockMaterial == Material.STRIPPED_OAK_LOG ||
                        blockMaterial == Material.OAK_PLANKS ||
                        blockMaterial == Material.SPRUCE_PLANKS ||
                        blockMaterial == Material.DARK_OAK_PLANKS ||
                        blockMaterial == Material.BIRCH_PLANKS ||
                        blockMaterial == Material.ACACIA_PLANKS ||
                        blockMaterial == Material.CHERRY_PLANKS ||
                        blockMaterial == Material.JUNGLE_PLANKS ||
                        blockMaterial == Material.OAK_WOOD ||
                        blockMaterial == Material.CRAFTING_TABLE
                // Add more materials that should be chopped with an axe
        );
    }

    private static boolean isShovelEffective(Material blockMaterial) {
        return blockMaterial.isBlock() && (
                blockMaterial == Material.DIRT ||
                        blockMaterial == Material.GRASS_BLOCK ||
                        blockMaterial == Material.SAND ||
                        blockMaterial == Material.GRAVEL ||
                        blockMaterial == Material.CLAY ||
                        blockMaterial == Material.SOUL_SAND ||
                        blockMaterial == Material.FARMLAND
                // Add more materials that should be dug with a shovel
        );
    }

    public static boolean isShearEffective(Material blockMaterial) {
        return blockMaterial.isBlock() && (
                blockMaterial == Material.OAK_LEAVES ||
                        blockMaterial == Material.SPRUCE_LEAVES ||
                        blockMaterial == Material.ACACIA_LEAVES ||
                        blockMaterial == Material.BIRCH_LEAVES ||
                        blockMaterial == Material.CHERRY_LEAVES ||
                        blockMaterial == Material.JUNGLE_LEAVES ||
                        blockMaterial == Material.DARK_OAK_LEAVES ||
                        blockMaterial == Material.TALL_GRASS ||
                        blockMaterial == Material.SHORT_GRASS ||
                        blockMaterial == Material.POPPY ||
                        blockMaterial == Material.DANDELION ||
                        blockMaterial == Material.BLUE_ORCHID ||
                        blockMaterial == Material.ALLIUM ||
                        blockMaterial == Material.AZURE_BLUET ||
                        blockMaterial == Material.ORANGE_TULIP ||
                        blockMaterial == Material.PINK_TULIP ||
                        blockMaterial == Material.RED_TULIP ||
                        blockMaterial == Material.WHITE_TULIP ||
                        blockMaterial == Material.OXEYE_DAISY ||
                        blockMaterial == Material.CORNFLOWER ||
                        blockMaterial == Material.LILY_OF_THE_VALLEY ||
                        blockMaterial == Material.VINE ||
                        blockMaterial == Material.COBWEB
        );
    }
}
