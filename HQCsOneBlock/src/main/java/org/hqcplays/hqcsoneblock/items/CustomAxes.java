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

public class CustomAxes implements Listener {
    // Vanilla Axes
    public static ItemStack woodAxe;
    public static ItemStack stoneAxe;
    public static ItemStack ironAxe;
    public static ItemStack goldenAxe;
    public static ItemStack diamondAxe;
    public static ItemStack netheriteAxe;

    // Custom Axes
    public static ItemStack lapisAxe;
    public static ItemStack redstoneAxe;
    public static ItemStack stardustAxe;

    public static void init() {
        woodAxe = createVanillaAxe(Material.WOODEN_AXE, ChatColor.WHITE + "Wooden Axe", 0.5f);
        lapisAxe = createLapisAxe(0.9f);
        stoneAxe = createVanillaAxe(Material.STONE_AXE, ChatColor.WHITE + "Stone Axe", 0.75f);
        ironAxe = createVanillaAxe(Material.IRON_AXE, ChatColor.WHITE + "Iron Axe", 1.0f);
        redstoneAxe = createRedstoneAxe(1.1f);
        goldenAxe = createVanillaAxe(Material.GOLDEN_AXE, ChatColor.WHITE + "Gold Axe", 1.25f);
        diamondAxe = createVanillaAxe(Material.DIAMOND_AXE, ChatColor.WHITE + "Diamond Axe", 1.5f);
        netheriteAxe = createVanillaAxe(Material.NETHERITE_AXE, ChatColor.WHITE + "Netherite Axe", 2.0f);
        stardustAxe = createStardustAxe(3.0f);
    }

    private static ItemStack createVanillaAxe(Material type, String name, float miningSpeedModifier) {
        ItemStack customAxe = new ItemStack(type);

        ItemMeta meta = customAxe.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList(miningSpeedModifier + " Mining Speed"));
        customAxe.setItemMeta(meta);

        return customAxe;
    }

    private static ItemStack createStardustAxe(float miningSpeedModifier) {
        ItemStack customAxe = new ItemStack(Material.GOLDEN_AXE, 1);

        ItemMeta meta = customAxe.getItemMeta();
        meta.setDisplayName("Stardust Axe");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList("+" + miningSpeedModifier + " Mining Speed"));

        customAxe.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "stardust_Axe"), customAxe);
        sr.shape(" AA",
                " SA",
                " S ");
        sr.setIngredient('A', stardust);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomTool(customAxe);

        return customAxe;
    }

    private static ItemStack createLapisAxe(float miningSpeedModifier) {
        ItemStack customAxe = new ItemStack(Material.STONE_AXE, 1);

        ItemMeta meta = customAxe.getItemMeta();
        meta.setDisplayName("Lapis Axe");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList("+" + miningSpeedModifier + " Mining Speed"));

        customAxe.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "lapis_Axe"), customAxe);
        sr.shape(" AA",
                " SA",
                " S ");
        sr.setIngredient('A', Material.LAPIS_LAZULI);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomTool(customAxe);

        return customAxe;
    }

    private static ItemStack createRedstoneAxe(float miningSpeedModifier) {
        ItemStack customAxe = new ItemStack(Material.IRON_AXE, 1);

        ItemMeta meta = customAxe.getItemMeta();
        meta.setDisplayName("Redstone Axe");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList("+" + miningSpeedModifier + " Mining Speed"));

        customAxe.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "redstone_Axe"), customAxe);
        sr.shape(" AA",
                " SA",
                " S ");
        sr.setIngredient('A', Material.REDSTONE);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomTool(customAxe);

        return customAxe;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Random rand = new Random(System.currentTimeMillis());

        // Lapis Axe effect
        if (rand.nextInt(5) == 0) {
            ItemStack itemInHand = event.getPlayer().getItemInHand();
            if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName() && itemInHand.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Lapis Axe")) {
                event.getPlayer().giveExp(10);
            }
        }

        // Redstone Axe effect
        if (rand.nextInt(10) == 0) {
            ItemStack itemInHand = event.getPlayer().getItemInHand();
            if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName() && itemInHand.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Redstone Axe")) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 40, 2));
            }
        }
    }
}
