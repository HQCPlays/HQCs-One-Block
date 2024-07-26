package org.hqcplays.hqcsoneblock.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

import java.util.Collections;

public class CustomPickaxes {
    private static final NamespacedKey modifierKey = new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "pickaxe_modifier");

    public static ItemStack woodPickaxe;
    public static ItemStack stonePickaxe;
    public static ItemStack ironPickaxe;
    public static ItemStack goldenPickaxe;
    public static ItemStack diamondPickaxe;
    public static ItemStack netheritePickaxe;

    public static void init() {
        woodPickaxe = createCustomPickaxe(Material.WOODEN_PICKAXE, ChatColor.AQUA + "Tier 1 Pickaxe", 0.5f);
        stonePickaxe = createCustomPickaxe(Material.STONE_PICKAXE, ChatColor.AQUA + "Tier 2 Pickaxe", 0.75f);
        ironPickaxe = createCustomPickaxe(Material.IRON_PICKAXE, ChatColor.AQUA + "Tier 3 Pickaxe", 1.0f);
        goldenPickaxe = createCustomPickaxe(Material.GOLDEN_PICKAXE, ChatColor.AQUA + "Tier 4 Pickaxe", 1.25f);
        diamondPickaxe = createCustomPickaxe(Material.DIAMOND_PICKAXE, ChatColor.AQUA + "Tier 5 Pickaxe", 1.5f);
        netheritePickaxe = createCustomPickaxe(Material.NETHERITE_PICKAXE, ChatColor.AQUA + "Tier 6 Pickaxe", 2.0f);
    }

    private static ItemStack createCustomPickaxe(Material type, String name, float miningSpeedModifier) {
        ItemStack customPickaxe = new ItemStack(type);

        float modifierValue = getBaseMiningSpeed(type) * miningSpeedModifier - 1.0f;
        AttributeModifier modifier = new AttributeModifier(modifierKey, modifierValue, AttributeModifier.Operation.ADD_SCALAR);

        ItemMeta meta = customPickaxe.getItemMeta();
        meta.setDisplayName(name);
        meta.addAttributeModifier(Attribute.PLAYER_BLOCK_BREAK_SPEED, modifier);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList("x" + miningSpeedModifier + " Mining Speed"));
        customPickaxe.setItemMeta(meta);

        return customPickaxe;
    }

    private static float getBaseMiningSpeed(Material material) {
        float multiplier = 1.0f; // Tweaks the base speed, 1.0 means pickaxes will mine cobblestone in one second
        switch (material) {
            case WOODEN_PICKAXE:
                return 1.5f * multiplier;
            case STONE_PICKAXE:
                return 0.75f * multiplier;
            case IRON_PICKAXE:
                return 0.5f * multiplier;
            case DIAMOND_PICKAXE:
                return 0.4f * multiplier;
            case NETHERITE_PICKAXE:
                return 0.35f * multiplier;
            case GOLDEN_PICKAXE:
                return 0.25f * multiplier;
            default:
                return multiplier;
        }
    }
}
