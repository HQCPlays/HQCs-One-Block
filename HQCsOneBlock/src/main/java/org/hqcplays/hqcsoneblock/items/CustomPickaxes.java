package org.hqcplays.hqcsoneblock.items;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.hqcplays.hqcsoneblock.items.RareOneBlockItems.stardust;

public class CustomPickaxes implements Listener {
    private static final NamespacedKey modifierKey = new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "pickaxe_modifier");

    // Vanilla Pickaxes
    public static ItemStack woodPickaxe;
    public static ItemStack stonePickaxe;
    public static ItemStack ironPickaxe;
    public static ItemStack goldenPickaxe;
    public static ItemStack diamondPickaxe;
    public static ItemStack netheritePickaxe;

    // Custom Pickaxes
    public static ItemStack lapisPickaxe;
    public static ItemStack redstonePickaxe;
    public static ItemStack stardustPickaxe;

    public static void init() {
        woodPickaxe = createVanillaPickaxe(Material.WOODEN_PICKAXE, ChatColor.WHITE + "Wooden Pickaxe", 0.5f);
        stonePickaxe = createVanillaPickaxe(Material.STONE_PICKAXE, ChatColor.WHITE + "Stone Pickaxe", 0.75f);
        lapisPickaxe = createCustomPickaxe(Material.STONE_PICKAXE, ChatColor.WHITE + "Lapis Pickaxe", 0.90f, ItemStack.of(Material.LAPIS_LAZULI), "lapis_pickaxe");
        ironPickaxe = createVanillaPickaxe(Material.IRON_PICKAXE, ChatColor.WHITE + "Iron Pickaxe", 1.0f);
        redstonePickaxe = createCustomPickaxe(Material.IRON_PICKAXE, ChatColor.WHITE + "Redstone Pickaxe", 1.10f, ItemStack.of(Material.REDSTONE), "redstone_pickaxe");
        goldenPickaxe = createVanillaPickaxe(Material.GOLDEN_PICKAXE, ChatColor.WHITE + "Gold Pickaxe", 1.25f);
        diamondPickaxe = createVanillaPickaxe(Material.DIAMOND_PICKAXE, ChatColor.WHITE + "Diamond Pickaxe", 1.5f);
        netheritePickaxe = createVanillaPickaxe(Material.NETHERITE_PICKAXE, ChatColor.WHITE + "Netherite Pickaxe", 2.0f);
        stardustPickaxe = createCustomPickaxe(Material.GOLDEN_PICKAXE, ChatColor.WHITE + "Stardust Pickaxe", 3.0f, RareOneBlockItems.stardust, "stardust_pickaxe");
    }

    private static ItemStack createVanillaPickaxe(Material type, String name, float miningSpeedModifier) {
        ItemStack customPickaxe = new ItemStack(type);

        float modifierValue = getBaseMiningSpeed(type) * miningSpeedModifier - 1.0f;
        AttributeModifier modifier = new AttributeModifier(modifierKey, modifierValue, AttributeModifier.Operation.ADD_SCALAR);

        ItemMeta meta = customPickaxe.getItemMeta();
        meta.setDisplayName(name);
        meta.addAttributeModifier(Attribute.PLAYER_BLOCK_BREAK_SPEED, modifier);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList(miningSpeedModifier + " Mining Speed"));
        customPickaxe.setItemMeta(meta);

        return customPickaxe;
    }

    private static ItemStack createCustomPickaxe(Material type, String name, float miningSpeedModifier, ItemStack pickaxeMaterial, String craftingKey) {
        ItemStack customPickaxe = new ItemStack(type);

        float modifierValue = getBaseMiningSpeed(type) * miningSpeedModifier - 1.0f;
        AttributeModifier modifier = new AttributeModifier(modifierKey, modifierValue, AttributeModifier.Operation.ADD_SCALAR);

        ItemMeta meta = customPickaxe.getItemMeta();
        meta.setDisplayName(name);
        meta.addAttributeModifier(Attribute.PLAYER_BLOCK_BREAK_SPEED, modifier);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        if (name.contains("Lapis Pickaxe")) {
            meta.setLore(Collections.singletonList("+" + miningSpeedModifier + " Mining Speed. Has a chance to drop extra experience!"));
        } else if (name.contains("Redstone Pickaxe")) {
            meta.setLore(Collections.singletonList("+" + miningSpeedModifier + " Mining Speed. Has a chance to give you Haste upon mining a block!"));
        } else {
            meta.setLore(Collections.singletonList("+" + miningSpeedModifier + " Mining Speed"));
        }

        customPickaxe.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customPickaxe);
        sr.shape("AAA",
                " S ",
                " S ");
        sr.setIngredient('A', pickaxeMaterial);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);

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

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Random rand = new Random(System.currentTimeMillis());

        // Lapis Pickaxe effect
        if (rand.nextInt(5) == 0) {
            ItemStack itemInHand = event.getPlayer().getItemInHand();
            if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName() && itemInHand.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Lapis Pickaxe")) {
                event.getPlayer().giveExp(10);
            }
        }

        // Redstone Pickaxe effect
        if (rand.nextInt(10) == 0) {
            ItemStack itemInHand = event.getPlayer().getItemInHand();
            if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName() && itemInHand.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Redstone Pickaxe")) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 40, 2));
            }
        }
    }
}
