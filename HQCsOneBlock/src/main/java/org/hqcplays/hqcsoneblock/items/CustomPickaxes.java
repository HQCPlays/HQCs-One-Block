package org.hqcplays.hqcsoneblock.items;

import org.bukkit.*;
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
import java.util.Random;

import static org.hqcplays.hqcsoneblock.items.RareOneBlockItems.stardust;

public class CustomPickaxes implements Listener {
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
        lapisPickaxe = createLapisPickaxe(0.9f);
        stonePickaxe = createVanillaPickaxe(Material.STONE_PICKAXE, ChatColor.WHITE + "Stone Pickaxe", 0.75f);
        ironPickaxe = createVanillaPickaxe(Material.IRON_PICKAXE, ChatColor.WHITE + "Iron Pickaxe", 1.0f);
        redstonePickaxe = createRedstonePickaxe(1.1f);
        goldenPickaxe = createVanillaPickaxe(Material.GOLDEN_PICKAXE, ChatColor.WHITE + "Gold Pickaxe", 1.25f);
        diamondPickaxe = createVanillaPickaxe(Material.DIAMOND_PICKAXE, ChatColor.WHITE + "Diamond Pickaxe", 1.5f);
        netheritePickaxe = createVanillaPickaxe(Material.NETHERITE_PICKAXE, ChatColor.WHITE + "Netherite Pickaxe", 2.0f);
        stardustPickaxe = createStardustPickaxe(3.0f);
    }

    private static ItemStack createVanillaPickaxe(Material type, String name, float miningSpeedModifier) {
        ItemStack customPickaxe = new ItemStack(type);

        ItemMeta meta = customPickaxe.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList(miningSpeedModifier + " Mining Speed"));
        customPickaxe.setItemMeta(meta);

        return customPickaxe;
    }

    private static ItemStack createStardustPickaxe(float miningSpeedModifier) {
        ItemStack customPickaxe = new ItemStack(Material.GOLDEN_PICKAXE, 1);

        ItemMeta meta = customPickaxe.getItemMeta();
        meta.setDisplayName("Stardust Pickaxe");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList("+" + miningSpeedModifier + " Mining Speed"));

        customPickaxe.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "stardust_pickaxe"), customPickaxe);
        sr.shape("AAA",
                " S ",
                " S ");
        sr.setIngredient('A', stardust);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);

        return customPickaxe;
    }

    private static ItemStack createLapisPickaxe(float miningSpeedModifier) {
        ItemStack customPickaxe = new ItemStack(Material.STONE_PICKAXE, 1);

        ItemMeta meta = customPickaxe.getItemMeta();
        meta.setDisplayName("Lapis Pickaxe");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList("+" + miningSpeedModifier + " Mining Speed"));

        customPickaxe.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "lapis_pickaxe"), customPickaxe);
        sr.shape("AAA",
                " S ",
                " S ");
        sr.setIngredient('A', Material.LAPIS_LAZULI);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);

        return customPickaxe;
    }

    private static ItemStack createRedstonePickaxe(float miningSpeedModifier) {
        ItemStack customPickaxe = new ItemStack(Material.IRON_PICKAXE, 1);

        ItemMeta meta = customPickaxe.getItemMeta();
        meta.setDisplayName("Redstone Pickaxe");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList("+" + miningSpeedModifier + " Mining Speed"));

        customPickaxe.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "redstone_pickaxe"), customPickaxe);
        sr.shape("AAA",
                " S ",
                " S ");
        sr.setIngredient('A', Material.REDSTONE);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);

        return customPickaxe;
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
