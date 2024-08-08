package org.hqcplays.hqcsoneblock.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

import java.util.Collections;

public class AccessoryItems {
    // Amulets
    public static CustomItem redShardAmulet;

    public static void init() {
        redShardAmulet = createRedShardAmulet();
    }

    public static CustomItem createRedShardAmulet() {
        CustomItem customItem = new CustomItem(Material.REDSTONE_LAMP, Component.text("Red Shard Amulet", NamedTextColor.RED), Component.text("Gives you bonus health while in your inventory"));

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "red_shard_amulet"), customItem.item);
        sr.shape("S S",
                "RRR",
                "   ");
        sr.setIngredient('S', Material.STRING);
        sr.setIngredient('R', AmethystShardItems.redShard.item);
        customItem.addCraftingRecipe(sr);

        return customItem;
    }
}
