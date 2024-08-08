package org.hqcplays.hqcsoneblock.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.enchantments.ShardEnchantment;
import org.hqcplays.hqcsoneblock.items.misc.CropformWandItem;
import org.hqcplays.hqcsoneblock.items.misc.GrassifyWandItem;
import org.hqcplays.hqcsoneblock.items.misc.TreensformWandItem;
import org.hqcplays.hqcsoneblock.items.shard.BlackShardItem;
import org.hqcplays.hqcsoneblock.items.shard.BlueShardItem;
import org.hqcplays.hqcsoneblock.items.shard.EffectShardItem;
import org.hqcplays.hqcsoneblock.items.shard.GoldShardItem;
import org.hqcplays.hqcsoneblock.items.shard.GreenShardItem;
import org.hqcplays.hqcsoneblock.items.shard.PurpleShardItem;
import org.hqcplays.hqcsoneblock.items.shard.RainbowShardItem;
import org.hqcplays.hqcsoneblock.items.shard.RedShardItem;
import org.hqcplays.hqcsoneblock.items.shard.WhiteShardItem;

import java.util.Collections;

public class AmethystShardItems {
    // Shards
    public static CustomItem goldShard;
    public static CustomItem redShard;
    public static CustomItem blueShard;
    public static CustomItem greenShard;
    public static CustomItem purpleShard;
    public static CustomItem blackShard;
    public static CustomItem whiteShard;
    public static CustomItem effectShard;
    public static CustomItem rainbowShard;

    // Shard Swords
    public static CustomItem blackShardSword;
    public static CustomItem whiteShardSword;
    public static CustomItem redShardSword;
    public static CustomItem blueShardSword;

    // Shard Armor
    public static CustomItem redShardHelmet;
    public static CustomItem redShardChestplate;
    public static CustomItem redShardLeggings;
    public static CustomItem redShardBoots;

    public static CustomItem purpleShardHelmet;
    public static CustomItem purpleShardChestplate;
    public static CustomItem purpleShardLeggings;
    public static CustomItem purpleShardBoots;

    // Shard Wands
    public static CustomItem grassifyWand;
    public static CustomItem treensformWand;
    public static CustomItem cropformWand;

    public static void init() {
        // Shards
        goldShard = new GoldShardItem();
        redShard = new RedShardItem();
        blueShard = new BlueShardItem();
        greenShard = new GreenShardItem();
        purpleShard = new PurpleShardItem();
        blackShard = new BlackShardItem();
        whiteShard = new WhiteShardItem();
        effectShard = new EffectShardItem();
        rainbowShard = new RainbowShardItem();

        // Swords
        blackShardSword = createShardSword(Material.NETHERITE_SWORD, NamedTextColor.DARK_GRAY, "Black Shard Sword", "black_shard_sword", blackShard.item, ShardEnchantment.voiding);
        whiteShardSword = createShardSword(Material.IRON_SWORD, NamedTextColor.WHITE, "White Shard Sword", "white_shard_sword", whiteShard.item, ShardEnchantment.turbulence);
        redShardSword = createShardSword(Material.WOODEN_SWORD, NamedTextColor.RED, "Red Shard Sword", "red_shard_sword", redShard.item, ShardEnchantment.vampirism);
        blueShardSword = createShardSword(Material.DIAMOND_SWORD, NamedTextColor.DARK_BLUE, "Blue Shard Sword", "blue_shard_sword", blueShard.item, ShardEnchantment.asphyxiation);

        // Armors
        redShardHelmet = createShardHelmet(Material.DIAMOND_HELMET, NamedTextColor.RED, "Red Shard Helmet", "red_shard_helmet", redShard.item, ShardEnchantment.vitality);
        redShardChestplate = createShardChestplate(Material.DIAMOND_CHESTPLATE, NamedTextColor.RED, "Red Shard Chestplate", "red_shard_chestplate", redShard.item, ShardEnchantment.vitality);
        redShardLeggings = createShardLeggings(Material.DIAMOND_LEGGINGS, NamedTextColor.RED, "Red Shard Leggings", "red_shard_leggings", redShard.item, ShardEnchantment.vitality);
        redShardBoots = createShardBoots(Material.DIAMOND_BOOTS, NamedTextColor.RED, "Red Shard Boots", "red_shard_boots", redShard.item, ShardEnchantment.vitality);

        // Wands
        grassifyWand = new GrassifyWandItem();
        treensformWand = new TreensformWandItem();
        cropformWand = new CropformWandItem();
    }

    public static CustomItem createShardSword(Material material, TextColor color, String name, String craftingKey, ItemStack craftingShard, ShardEnchantment enchantment) {
        CustomItem customItem = new CustomItem(material, name, color);
        customItem.addShardEnchantment(enchantment);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem.item);
        sr.shape(" A ",
                " A ",
                " S ");
        sr.setIngredient('A', craftingShard);
        sr.setIngredient('S', Material.STICK);
        customItem.addCraftingRecipe(sr);

        return customItem;
    }

    public static CustomItem createShardHelmet(Material material, TextColor color, String name, String craftingKey, ItemStack craftingShard, ShardEnchantment enchantment) {
        CustomItem customItem = new CustomItem(material, name, color);
        customItem.addShardEnchantment(enchantment);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem.item);
        sr.shape("AAA",
                "A A",
                "   ");
        sr.setIngredient('A', craftingShard);
        customItem.addCraftingRecipe(sr);

        return customItem;
    }

    public static CustomItem createShardChestplate(Material material, TextColor color, String name, String craftingKey, ItemStack craftingShard, ShardEnchantment enchantment) {
        CustomItem customItem = new CustomItem(material, name, color);
        customItem.addShardEnchantment(enchantment);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem.item);
        sr.shape("A A",
                "AAA",
                "AAA");
        sr.setIngredient('A', craftingShard);
        customItem.addCraftingRecipe(sr);

        return customItem;
    }

    public static CustomItem createShardLeggings(Material material, TextColor color, String name, String craftingKey, ItemStack craftingShard, ShardEnchantment enchantment) {
        CustomItem customItem = new CustomItem(material, name, color);
        customItem.addShardEnchantment(enchantment);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem.item);
        sr.shape("AAA",
                "A A",
                "A A");
        sr.setIngredient('A', craftingShard);
        customItem.addCraftingRecipe(sr);

        return customItem;
    }

    public static CustomItem createShardBoots(Material material, TextColor color, String name, String craftingKey, ItemStack craftingShard, ShardEnchantment enchantment) {
        CustomItem customItem = new CustomItem(material, name, color);
        customItem.addShardEnchantment(enchantment);

        // Crafting recipe
        ShapedRecipe sr1 = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem.item);
        sr1.shape("   ",
                "A A",
                "A A");
        sr1.setIngredient('A', craftingShard);
        customItem.addCraftingRecipe(sr1);

        return customItem;
    }
}
