package org.hqcplays.hqcsoneblock.enchantments;

import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentData {

    // Enchantment ranges and weights
    private static final Map<Enchantment, EnchantmentDetails> ENCHANTMENT_DETAILS = new HashMap<>();

    static {
        // Initialize enchantments for Armor
        ENCHANTMENT_DETAILS.put(Enchantment.PROTECTION, new EnchantmentDetails(
            new int[]{1, 12, 23, 34, 46}, 10, true)); // Protection
        ENCHANTMENT_DETAILS.put(Enchantment.FEATHER_FALLING, new EnchantmentDetails(
            new int[]{5, 11, 17, 23, 30}, 5, true)); // Feather Falling
        ENCHANTMENT_DETAILS.put(Enchantment.FIRE_PROTECTION, new EnchantmentDetails(
            new int[]{10, 18, 26, 34, 43}, 5, true)); // Fire Protection
        ENCHANTMENT_DETAILS.put(Enchantment.PROJECTILE_PROTECTION, new EnchantmentDetails(
            new int[]{3, 9, 15, 21, 28}, 5, true)); // Projectile Protection
        ENCHANTMENT_DETAILS.put(Enchantment.BLAST_PROTECTION, new EnchantmentDetails(
            new int[]{5, 13, 21, 30, 38}, 2, true)); // Blast Protection
            ENCHANTMENT_DETAILS.put(Enchantment.RESPIRATION, new EnchantmentDetails(
            new int[]{10, 20, 30}, 2, true)); // RESPIRATION
        ENCHANTMENT_DETAILS.put(Enchantment.DEPTH_STRIDER, new EnchantmentDetails(
            new int[]{1}, 2, true)); // AQUA INFINITY
        ENCHANTMENT_DETAILS.put(Enchantment.DEPTH_STRIDER, new EnchantmentDetails(
            new int[]{10, 20, 30}, 2, true)); // Depth Strider
        ENCHANTMENT_DETAILS.put(Enchantment.FROST_WALKER, new EnchantmentDetails(
            new int[]{10, 20}, 2, false)); // Frost Walker
        ENCHANTMENT_DETAILS.put(Enchantment.THORNS, new EnchantmentDetails(
            new int[]{10, 30, 50}, 1, true)); // Thorns
        ENCHANTMENT_DETAILS.put(Enchantment.SWIFT_SNEAK, new EnchantmentDetails(
            new int[]{25, 50, 75}, 1, false)); // Swift Sneak
        ENCHANTMENT_DETAILS.put(Enchantment.BINDING_CURSE, new EnchantmentDetails(
            new int[]{25, 50}, 1, false)); // Curse of Binding
        ENCHANTMENT_DETAILS.put(Enchantment.SOUL_SPEED, new EnchantmentDetails(
            new int[]{10, 20, 30}, 1, false)); // Soul Speed

        // Initialize enchantments for Sword
        ENCHANTMENT_DETAILS.put(Enchantment.SHARPNESS, new EnchantmentDetails(
            new int[]{1, 12, 23, 34, 45}, 10, true)); // Sharpness
        ENCHANTMENT_DETAILS.put(Enchantment.SMITE, new EnchantmentDetails(
            new int[]{5, 13, 21, 29, 37}, 5, true)); // Smite
        ENCHANTMENT_DETAILS.put(Enchantment.BANE_OF_ARTHROPODS, new EnchantmentDetails(
            new int[]{5, 13, 21, 29, 37}, 5, true)); // Bane of Arthropods
        ENCHANTMENT_DETAILS.put(Enchantment.KNOCKBACK, new EnchantmentDetails(
            new int[]{5, 25}, 5, true)); // Knockback
        ENCHANTMENT_DETAILS.put(Enchantment.FIRE_ASPECT, new EnchantmentDetails(
            new int[]{10, 30}, 2, true)); // Fire Aspect
        ENCHANTMENT_DETAILS.put(Enchantment.LOOTING, new EnchantmentDetails(
            new int[]{15, 24, 33}, 2, true)); // Looting
        ENCHANTMENT_DETAILS.put(Enchantment.SWEEPING_EDGE, new EnchantmentDetails(
            new int[]{5, 14, 23}, 2, true)); // Sweeping Edge
        ENCHANTMENT_DETAILS.put(ShardEnchantment.asphyxiation, new EnchantmentDetails(
            new int[]{5, 14, 23}, 2, true)); // Asphyxiation
        ENCHANTMENT_DETAILS.put(ShardEnchantment.turbulence, new EnchantmentDetails(
            new int[]{5, 14, 23}, 2, true)); // Turbulence
        ENCHANTMENT_DETAILS.put(ShardEnchantment.vampirism, new EnchantmentDetails(
            new int[]{5, 14, 23}, 2, true)); // Vampirism
        ENCHANTMENT_DETAILS.put(ShardEnchantment.vitality, new EnchantmentDetails(
            new int[]{5, 14, 23}, 2, true)); // Vitality
        ENCHANTMENT_DETAILS.put(ShardEnchantment.voiding, new EnchantmentDetails(
            new int[]{5, 14, 23}, 2, true)); // Voiding

        // Initialize enchantments for Tools (Pickaxe, Shovel, Axe, Hoe, Shears)
        ENCHANTMENT_DETAILS.put(Enchantment.EFFICIENCY, new EnchantmentDetails(
            new int[]{1, 11, 21, 31, 41}, 10, true)); // Efficiency
        ENCHANTMENT_DETAILS.put(Enchantment.FORTUNE, new EnchantmentDetails(
            new int[]{15, 24, 33}, 2, true)); // Fortune
        ENCHANTMENT_DETAILS.put(Enchantment.SILK_TOUCH, new EnchantmentDetails(
            new int[]{15, 65}, 1, true)); // Silk Touch

        // Initialize enchantments for Bow
        ENCHANTMENT_DETAILS.put(Enchantment.POWER, new EnchantmentDetails(
            new int[]{1, 11, 21, 31, 41}, 10, true)); // Power
        ENCHANTMENT_DETAILS.put(Enchantment.FLAME, new EnchantmentDetails(
            new int[]{20, 50}, 2, true)); // Flame
        ENCHANTMENT_DETAILS.put(Enchantment.PUNCH, new EnchantmentDetails(
            new int[]{12, 32}, 2, true)); // Punch
        ENCHANTMENT_DETAILS.put(Enchantment.INFINITY, new EnchantmentDetails(
            new int[]{20, 50}, 1, true)); // Infinity

        // Initialize enchantments for Fishing Rod
        ENCHANTMENT_DETAILS.put(Enchantment.LUCK_OF_THE_SEA, new EnchantmentDetails(
            new int[]{15, 24, 33}, 2, true)); // Luck of the Sea
        ENCHANTMENT_DETAILS.put(Enchantment.LURE, new EnchantmentDetails(
            new int[]{15, 24, 33}, 2, true)); // Lure

        // Initialize enchantments for Trident
        ENCHANTMENT_DETAILS.put(Enchantment.LOYALTY, new EnchantmentDetails(
            new int[]{12, 19, 26}, 5, true)); // Loyalty
        ENCHANTMENT_DETAILS.put(Enchantment.IMPALING, new EnchantmentDetails(
            new int[]{1, 9, 17, 25, 33}, 2, true)); // Impaling
        ENCHANTMENT_DETAILS.put(Enchantment.RIPTIDE, new EnchantmentDetails(
            new int[]{17, 24, 31}, 2, true)); // Riptide
        ENCHANTMENT_DETAILS.put(Enchantment.CHANNELING, new EnchantmentDetails(
            new int[]{25, 50}, 1, true)); // Channeling

        // Initialize enchantments for Crossbow
        ENCHANTMENT_DETAILS.put(Enchantment.MULTISHOT, new EnchantmentDetails(
            new int[]{20, 50}, 2, true)); // Multishot
        ENCHANTMENT_DETAILS.put(Enchantment.PIERCING, new EnchantmentDetails(
            new int[]{1, 11, 21, 31}, 10, true)); // Piercing
        ENCHANTMENT_DETAILS.put(Enchantment.QUICK_CHARGE, new EnchantmentDetails(
            new int[]{12, 32, 52}, 5, true)); // Quick Charge

        // Initialize enchantments for All applicable
        ENCHANTMENT_DETAILS.put(Enchantment.UNBREAKING, new EnchantmentDetails(
            new int[]{5, 13, 21}, 5, true)); // Unbreaking
        ENCHANTMENT_DETAILS.put(Enchantment.MENDING, new EnchantmentDetails(
            new int[]{25, 75}, 2, false)); // Mending
        ENCHANTMENT_DETAILS.put(Enchantment.VANISHING_CURSE, new EnchantmentDetails(
            new int[]{25, 50}, 1, false)); // Curse of Vanishing
    }

    // Class to store the details of each enchantment
    public static class EnchantmentDetails {
        private final int[] ranges;
        private final int weight;
        private final boolean obtainableFromEnchantingTable;

        public EnchantmentDetails(int[] ranges, int weight, boolean obtainableFromEnchantingTable) {
            this.ranges = ranges;
            this.weight = weight;
            this.obtainableFromEnchantingTable = obtainableFromEnchantingTable;
        }

        public int[] getRanges() {
            return ranges;
        }

        public int getWeight() {
            return weight;
        }

        public boolean isObtainableFromEnchantingTable() {
            return obtainableFromEnchantingTable;
        }
    }

    public static Map<Enchantment, EnchantmentDetails> getEnchantmentDetails() {
        return ENCHANTMENT_DETAILS;
    }

    /*
     * Method for getting an enchantment via its name as a string.
     * Sadly there is no automatic way to convert a string into an enchantment, must be done manually
     */
    public static Enchantment getEnchantmentByName(String name) {
        switch (name) {
            case "PROTECTION": return Enchantment.PROTECTION;
            case "FIRE_PROTECTION": return Enchantment.FIRE_PROTECTION;
            case "FEATHER_FALLING": return Enchantment.FEATHER_FALLING;
            case "BLAST_PROTECTION": return Enchantment.BLAST_PROTECTION;
            case "PROJECTILE_PROTECTION": return Enchantment.PROJECTILE_PROTECTION;
            case "RESPIRATION": return Enchantment.RESPIRATION;
            case "AQUA_AFFINITY": return Enchantment.AQUA_AFFINITY;
            case "THORNS": return Enchantment.THORNS;
            case "DEPTH_STRIDER": return Enchantment.DEPTH_STRIDER;
            case "FROST_WALKER": return Enchantment.FROST_WALKER;
            case "BINDING_CURSE": return Enchantment.BINDING_CURSE;
            case "SHARPNESS": return Enchantment.SHARPNESS;
            case "SMITE": return Enchantment.SMITE;
            case "BANE_OF_ARTHROPODS": return Enchantment.BANE_OF_ARTHROPODS;
            case "KNOCKBACK": return Enchantment.KNOCKBACK;
            case "FIRE_ASPECT": return Enchantment.FIRE_ASPECT;
            case "LOOTING": return Enchantment.LOOTING;
            case "SWEEPING_EDGE": return Enchantment.SWEEPING_EDGE;
            case "ASPHYXIATION": return ShardEnchantment.asphyxiation;
            case "TURBULENCE": return ShardEnchantment.turbulence;
            case "VAMPIRISM": return ShardEnchantment.vampirism;
            case "VITALITY": return ShardEnchantment.vitality;
            case "VOIDING": return ShardEnchantment.voiding;
            case "EFFICIENCY": return Enchantment.EFFICIENCY;
            case "SILK_TOUCH": return Enchantment.SILK_TOUCH;
            case "FORTUNE": return Enchantment.FORTUNE;
            case "UNBREAKING": return Enchantment.UNBREAKING;
            case "POWER": return Enchantment.POWER;
            case "PUNCH": return Enchantment.PUNCH;
            case "FLAME": return Enchantment.FLAME;
            case "INFINITY": return Enchantment.INFINITY;
            case "LUCK_OF_THE_SEA": return Enchantment.LUCK_OF_THE_SEA;
            case "LURE": return Enchantment.LURE;
            case "LOYALTY": return Enchantment.LOYALTY;
            case "IMPALING": return Enchantment.IMPALING;
            case "RIPTIDE": return Enchantment.RIPTIDE;
            case "CHANNELING": return Enchantment.CHANNELING;
            case "MULTISHOT": return Enchantment.MULTISHOT;
            case "QUICK_CHARGE": return Enchantment.QUICK_CHARGE;
            case "PIERCING": return Enchantment.PIERCING;
            case "MENDING": return Enchantment.MENDING;
            case "VANISHING_CURSE": return Enchantment.VANISHING_CURSE;
            default: return null;
        }
    }
}
