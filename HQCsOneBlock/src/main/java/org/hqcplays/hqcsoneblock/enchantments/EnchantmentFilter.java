package org.hqcplays.hqcsoneblock.enchantments;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

import java.util.*;

public class EnchantmentFilter {

    // Enum to categorize item types
    public enum ItemCategory {
        SWORD,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        BOW,
        TOOL,
        FISHING_ROD,
        TRIDENT,
        CROSSBOW,
        BOOK,
        ANY // For enchantments that apply to all items
    }

    // Mapping from item materials to their categories
    private static final Map<Material, ItemCategory> ITEM_CATEGORY_MAP = new HashMap<>();

    static {
        // Swords
        ITEM_CATEGORY_MAP.put(Material.WOODEN_SWORD, ItemCategory.SWORD);
        ITEM_CATEGORY_MAP.put(Material.STONE_SWORD, ItemCategory.SWORD);
        ITEM_CATEGORY_MAP.put(Material.IRON_SWORD, ItemCategory.SWORD);
        ITEM_CATEGORY_MAP.put(Material.GOLDEN_SWORD, ItemCategory.SWORD);
        ITEM_CATEGORY_MAP.put(Material.DIAMOND_SWORD, ItemCategory.SWORD);
        ITEM_CATEGORY_MAP.put(Material.NETHERITE_SWORD, ItemCategory.SWORD);

        // Helmets
        ITEM_CATEGORY_MAP.put(Material.LEATHER_HELMET, ItemCategory.HELMET);
        ITEM_CATEGORY_MAP.put(Material.CHAINMAIL_HELMET, ItemCategory.HELMET);
        ITEM_CATEGORY_MAP.put(Material.IRON_HELMET, ItemCategory.HELMET);
        ITEM_CATEGORY_MAP.put(Material.GOLDEN_HELMET, ItemCategory.HELMET);
        ITEM_CATEGORY_MAP.put(Material.DIAMOND_HELMET, ItemCategory.HELMET);
        ITEM_CATEGORY_MAP.put(Material.NETHERITE_HELMET, ItemCategory.HELMET);
        ITEM_CATEGORY_MAP.put(Material.TURTLE_HELMET, ItemCategory.HELMET);

        // Chestplates
        ITEM_CATEGORY_MAP.put(Material.LEATHER_CHESTPLATE, ItemCategory.CHESTPLATE);
        ITEM_CATEGORY_MAP.put(Material.CHAINMAIL_CHESTPLATE, ItemCategory.CHESTPLATE);
        ITEM_CATEGORY_MAP.put(Material.IRON_CHESTPLATE, ItemCategory.CHESTPLATE);
        ITEM_CATEGORY_MAP.put(Material.GOLDEN_CHESTPLATE, ItemCategory.CHESTPLATE);
        ITEM_CATEGORY_MAP.put(Material.DIAMOND_CHESTPLATE, ItemCategory.CHESTPLATE);
        ITEM_CATEGORY_MAP.put(Material.NETHERITE_CHESTPLATE, ItemCategory.CHESTPLATE);

        // Leggings
        ITEM_CATEGORY_MAP.put(Material.LEATHER_LEGGINGS, ItemCategory.LEGGINGS);
        ITEM_CATEGORY_MAP.put(Material.CHAINMAIL_LEGGINGS, ItemCategory.LEGGINGS);
        ITEM_CATEGORY_MAP.put(Material.IRON_LEGGINGS, ItemCategory.LEGGINGS);
        ITEM_CATEGORY_MAP.put(Material.GOLDEN_LEGGINGS, ItemCategory.LEGGINGS);
        ITEM_CATEGORY_MAP.put(Material.DIAMOND_LEGGINGS, ItemCategory.LEGGINGS);
        ITEM_CATEGORY_MAP.put(Material.NETHERITE_LEGGINGS, ItemCategory.LEGGINGS);

        // Boots
        ITEM_CATEGORY_MAP.put(Material.LEATHER_BOOTS, ItemCategory.BOOTS);
        ITEM_CATEGORY_MAP.put(Material.CHAINMAIL_BOOTS, ItemCategory.BOOTS);
        ITEM_CATEGORY_MAP.put(Material.IRON_BOOTS, ItemCategory.BOOTS);
        ITEM_CATEGORY_MAP.put(Material.GOLDEN_BOOTS, ItemCategory.BOOTS);
        ITEM_CATEGORY_MAP.put(Material.DIAMOND_BOOTS, ItemCategory.BOOTS);
        ITEM_CATEGORY_MAP.put(Material.NETHERITE_BOOTS, ItemCategory.BOOTS);

        // Bow
        ITEM_CATEGORY_MAP.put(Material.BOW, ItemCategory.BOW);

        // Tools (Pickaxe, Shovel, Axe, Hoe, Shears)
        ITEM_CATEGORY_MAP.put(Material.WOODEN_PICKAXE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.STONE_PICKAXE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.IRON_PICKAXE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.GOLDEN_PICKAXE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.DIAMOND_PICKAXE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.NETHERITE_PICKAXE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.WOODEN_SHOVEL, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.STONE_SHOVEL, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.IRON_SHOVEL, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.GOLDEN_SHOVEL, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.DIAMOND_SHOVEL, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.NETHERITE_SHOVEL, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.WOODEN_AXE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.STONE_AXE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.IRON_AXE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.GOLDEN_AXE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.DIAMOND_AXE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.NETHERITE_AXE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.WOODEN_HOE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.STONE_HOE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.IRON_HOE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.GOLDEN_HOE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.DIAMOND_HOE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.NETHERITE_HOE, ItemCategory.TOOL);
        ITEM_CATEGORY_MAP.put(Material.SHEARS, ItemCategory.TOOL);

        // Fishing Rod
        ITEM_CATEGORY_MAP.put(Material.FISHING_ROD, ItemCategory.FISHING_ROD);

        // Trident
        ITEM_CATEGORY_MAP.put(Material.TRIDENT, ItemCategory.TRIDENT);

        // Crossbow
        ITEM_CATEGORY_MAP.put(Material.CROSSBOW, ItemCategory.CROSSBOW);

        // Book
        ITEM_CATEGORY_MAP.put(Material.BOOK, ItemCategory.BOOK);

    }

    // Method to get the valid enchantments for a given item
    public List<Enchantment> getValidEnchantments(ItemStack item) {
        Material material = item.getType();
        ItemCategory category = ITEM_CATEGORY_MAP.get(material);

        if (category == null) {
            return Collections.emptyList(); // No valid enchantments if the item type isn't mapped
        }

        List<Enchantment> validEnchantments = new ArrayList<>();
        Map<Enchantment, EnchantmentData.EnchantmentDetails> enchantmentDetails = EnchantmentData.getEnchantmentDetails();

        for (Map.Entry<Enchantment, EnchantmentData.EnchantmentDetails> entry : enchantmentDetails.entrySet()) {
            Enchantment enchantment = entry.getKey();

            // Check if the enchantment applies to the item category or is a universal enchantment
            if (isApplicableEnchantment(enchantment, category)) {
                validEnchantments.add(enchantment);
            }
        }

        return validEnchantments;
    }

    /**
     * Checks if a given enchantment is applicable to the specified item stack.
     *
     * @param enchantment The enchantment to check.
     * @param item The item stack to check against.
     * @return true if the enchantment can be applied to the item, false otherwise.
     */
    public static boolean isEnchantmentApplicable(Enchantment enchantment, ItemStack item) {
        Material material = item.getType();
        ItemCategory category = ITEM_CATEGORY_MAP.get(material);

        if (category == null) {
            return false; // Item type not mapped, so no valid enchantments
        }

        return isApplicableEnchantment(enchantment, category);
    }

    // Helper method to determine if an enchantment is applicable to a category
    private static boolean isApplicableEnchantment(Enchantment enchantment, ItemCategory category) {
        switch (category) {
            case SWORD:
                return
                       enchantment.equals(Enchantment.BANE_OF_ARTHROPODS) ||
                       enchantment.equals(Enchantment.SMITE) ||
                       enchantment.equals(Enchantment.KNOCKBACK) ||
                       enchantment.equals(Enchantment.FIRE_ASPECT) ||
                       enchantment.equals(Enchantment.LOOTING) ||
                       enchantment.equals(Enchantment.SWEEPING_EDGE) ||
                       enchantment.equals(ShardEnchantment.asphyxiation) ||
                       enchantment.equals(ShardEnchantment.turbulence) ||
                       enchantment.equals(ShardEnchantment.vampirism) ||
                       enchantment.equals(ShardEnchantment.vitality) ||
                       enchantment.equals(ShardEnchantment.voiding) ||
                       isUniversalEnchantment(enchantment);
            case HELMET:
                return enchantment.equals(Enchantment.PROTECTION) ||
                       enchantment.equals(Enchantment.BLAST_PROTECTION) ||
                       enchantment.equals(Enchantment.FIRE_PROTECTION) ||
                       enchantment.equals(Enchantment.PROJECTILE_PROTECTION) ||
                       enchantment.equals(Enchantment.RESPIRATION) ||
                       enchantment.equals(Enchantment.AQUA_AFFINITY) ||
                       enchantment.equals(Enchantment.THORNS) ||
                       isUniversalEnchantment(enchantment);
            case CHESTPLATE:
            case LEGGINGS:
                return enchantment.equals(Enchantment.PROTECTION) ||
                       enchantment.equals(Enchantment.BLAST_PROTECTION) ||
                       enchantment.equals(Enchantment.FIRE_PROTECTION) ||
                       enchantment.equals(Enchantment.PROJECTILE_PROTECTION) ||
                       enchantment.equals(Enchantment.THORNS) ||
                       enchantment.equals(Enchantment.BINDING_CURSE) ||
                       isUniversalEnchantment(enchantment);
            case BOOTS:
                return enchantment.equals(Enchantment.PROTECTION) ||
                       enchantment.equals(Enchantment.BLAST_PROTECTION) ||
                       enchantment.equals(Enchantment.FEATHER_FALLING) ||
                       enchantment.equals(Enchantment.FIRE_PROTECTION) ||
                       enchantment.equals(Enchantment.PROJECTILE_PROTECTION) ||
                       enchantment.equals(Enchantment.THORNS) ||
                       enchantment.equals(Enchantment.DEPTH_STRIDER) ||
                       enchantment.equals(Enchantment.FROST_WALKER) ||
                       enchantment.equals(Enchantment.BINDING_CURSE) ||
                       enchantment.equals(Enchantment.SOUL_SPEED) ||
                       isUniversalEnchantment(enchantment);
            case BOW:
                return enchantment.equals(Enchantment.POWER) ||
                       enchantment.equals(Enchantment.FLAME) ||
                       enchantment.equals(Enchantment.PUNCH) ||
                       enchantment.equals(Enchantment.INFINITY) ||
                       isUniversalEnchantment(enchantment);
            case TOOL:
                return enchantment.equals(Enchantment.EFFICIENCY) ||
                       enchantment.equals(Enchantment.FORTUNE) ||
                       enchantment.equals(Enchantment.SILK_TOUCH) ||
                       isUniversalEnchantment(enchantment);
            case FISHING_ROD:
                return enchantment.equals(Enchantment.LUCK_OF_THE_SEA) ||
                       enchantment.equals(Enchantment.LURE) ||
                       isUniversalEnchantment(enchantment);
            case TRIDENT:
                return enchantment.equals(Enchantment.IMPALING) ||
                       enchantment.equals(Enchantment.RIPTIDE) ||
                       enchantment.equals(Enchantment.LOYALTY) ||
                       enchantment.equals(Enchantment.CHANNELING) ||
                       isUniversalEnchantment(enchantment);
            case CROSSBOW:
                return enchantment.equals(Enchantment.MULTISHOT) ||
                       enchantment.equals(Enchantment.PIERCING) ||
                       enchantment.equals(Enchantment.QUICK_CHARGE) ||
                       isUniversalEnchantment(enchantment);
            case BOOK:
                return enchantment.equals(Enchantment.BANE_OF_ARTHROPODS) ||
                       enchantment.equals(Enchantment.SMITE) ||
                       enchantment.equals(Enchantment.KNOCKBACK) ||
                       enchantment.equals(Enchantment.FIRE_ASPECT) ||
                       enchantment.equals(Enchantment.LOOTING) ||
                       enchantment.equals(Enchantment.SWEEPING_EDGE) ||
                       enchantment.equals(ShardEnchantment.asphyxiation) ||
                       enchantment.equals(ShardEnchantment.turbulence) ||
                       enchantment.equals(ShardEnchantment.vampirism) ||
                       enchantment.equals(ShardEnchantment.vitality) ||
                       enchantment.equals(ShardEnchantment.voiding) ||
                       enchantment.equals(Enchantment.PROTECTION) ||
                       enchantment.equals(Enchantment.BLAST_PROTECTION) ||
                       enchantment.equals(Enchantment.FIRE_PROTECTION) ||
                       enchantment.equals(Enchantment.PROJECTILE_PROTECTION) ||
                       enchantment.equals(Enchantment.RESPIRATION) ||
                       enchantment.equals(Enchantment.AQUA_AFFINITY) ||
                       enchantment.equals(Enchantment.THORNS) ||
                       enchantment.equals(Enchantment.BINDING_CURSE) ||
                       enchantment.equals(Enchantment.DEPTH_STRIDER) ||
                       enchantment.equals(Enchantment.FROST_WALKER) ||
                       enchantment.equals(Enchantment.BINDING_CURSE) ||
                       enchantment.equals(Enchantment.SOUL_SPEED) ||
                       enchantment.equals(Enchantment.POWER) ||
                       enchantment.equals(Enchantment.FLAME) ||
                       enchantment.equals(Enchantment.PUNCH) ||
                       enchantment.equals(Enchantment.INFINITY) ||
                       enchantment.equals(Enchantment.EFFICIENCY) ||
                       enchantment.equals(Enchantment.FORTUNE) ||
                       enchantment.equals(Enchantment.SILK_TOUCH) ||
                       enchantment.equals(Enchantment.LUCK_OF_THE_SEA) ||
                       enchantment.equals(Enchantment.LURE) ||
                       enchantment.equals(Enchantment.IMPALING) ||
                       enchantment.equals(Enchantment.RIPTIDE) ||
                       enchantment.equals(Enchantment.LOYALTY) ||
                       enchantment.equals(Enchantment.CHANNELING) ||
                       enchantment.equals(Enchantment.MULTISHOT) ||
                       enchantment.equals(Enchantment.PIERCING) ||
                       enchantment.equals(Enchantment.QUICK_CHARGE) ||
                       isUniversalEnchantment(enchantment);
            case ANY:
                return isUniversalEnchantment(enchantment);
            default:
                return false;
        }
    }

    // Helper method to check if an enchantment is universal (applies to any item)
    private static boolean isUniversalEnchantment(Enchantment enchantment) {
        return enchantment.equals(Enchantment.VANISHING_CURSE);
            //    enchantment.equals(Enchantment.UNBREAKING) ||
            //    enchantment.equals(Enchantment.MENDING);
    }
}
