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

public class CustomShovels implements Listener {
    // Vanilla Shovels
    public static ItemStack woodShovel;
    public static ItemStack stoneShovel;
    public static ItemStack ironShovel;
    public static ItemStack goldenShovel;
    public static ItemStack diamondShovel;
    public static ItemStack netheriteShovel;

    // Custom Shovels
    public static ItemStack lapisShovel;
    public static ItemStack redstoneShovel;
    public static ItemStack stardustShovel;

    public static void init() {
        woodShovel = createVanillaShovel(Material.WOODEN_SHOVEL, ChatColor.WHITE + "Wooden Shovel", 0.5f);
        lapisShovel = createLapisShovel(0.9f);
        stoneShovel = createVanillaShovel(Material.STONE_SHOVEL, ChatColor.WHITE + "Stone Shovel", 0.75f);
        ironShovel = createVanillaShovel(Material.IRON_SHOVEL, ChatColor.WHITE + "Iron Shovel", 1.0f);
        redstoneShovel = createRedstoneShovel(1.1f);
        goldenShovel = createVanillaShovel(Material.GOLDEN_SHOVEL, ChatColor.WHITE + "Gold Shovel", 1.25f);
        diamondShovel = createVanillaShovel(Material.DIAMOND_SHOVEL, ChatColor.WHITE + "Diamond Shovel", 1.5f);
        netheriteShovel = createVanillaShovel(Material.NETHERITE_SHOVEL, ChatColor.WHITE + "Netherite Shovel", 2.0f);
        stardustShovel = createStardustShovel(3.0f);
    }

    private static ItemStack createVanillaShovel(Material type, String name, float miningSpeedModifier) {
        ItemStack customShovel = new ItemStack(type);

        ItemMeta meta = customShovel.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList(miningSpeedModifier + " Mining Speed"));
        customShovel.setItemMeta(meta);

        return customShovel;
    }

    private static ItemStack createStardustShovel(float miningSpeedModifier) {
        ItemStack customShovel = new ItemStack(Material.GOLDEN_SHOVEL, 1);

        ItemMeta meta = customShovel.getItemMeta();
        meta.setDisplayName("Stardust Shovel");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList("+" + miningSpeedModifier + " Mining Speed"));

        customShovel.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "stardust_Shovel"), customShovel);
        sr.shape(" A ",
                " S ",
                " S ");
        sr.setIngredient('A', stardust);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomTool(customShovel);

        return customShovel;
    }

    private static ItemStack createLapisShovel(float miningSpeedModifier) {
        ItemStack customShovel = new ItemStack(Material.STONE_SHOVEL, 1);

        ItemMeta meta = customShovel.getItemMeta();
        meta.setDisplayName("Lapis Shovel");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList("+" + miningSpeedModifier + " Mining Speed"));

        customShovel.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "lapis_Shovel"), customShovel);
        sr.shape(" A ",
                " S ",
                " S ");
        sr.setIngredient('A', Material.LAPIS_LAZULI);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomTool(customShovel);

        return customShovel;
    }

    private static ItemStack createRedstoneShovel(float miningSpeedModifier) {
        ItemStack customShovel = new ItemStack(Material.IRON_SHOVEL, 1);

        ItemMeta meta = customShovel.getItemMeta();
        meta.setDisplayName("Redstone Shovel");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Collections.singletonList("+" + miningSpeedModifier + " Mining Speed"));

        customShovel.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "redstone_Shovel"), customShovel);
        sr.shape(" A ",
                " S ",
                " S ");
        sr.setIngredient('A', Material.REDSTONE);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomTool(customShovel);

        return customShovel;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Random rand = new Random(System.currentTimeMillis());

        // Lapis Shovel effect
        if (rand.nextInt(5) == 0) {
            ItemStack itemInHand = event.getPlayer().getItemInHand();
            if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName() && itemInHand.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Lapis Shovel")) {
                event.getPlayer().giveExp(10);
            }
        }

        // Redstone Shovel effect
        if (rand.nextInt(10) == 0) {
            ItemStack itemInHand = event.getPlayer().getItemInHand();
            if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName() && itemInHand.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Redstone Shovel")) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 40, 2));
            }
        }
    }
}
