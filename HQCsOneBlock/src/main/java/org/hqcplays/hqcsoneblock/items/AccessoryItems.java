package org.hqcplays.hqcsoneblock.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.eclipse.aether.util.graph.transformer.ChainedDependencyGraphTransformer;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.enchantments.ShardEnchantment;
import org.hqcplays.hqcsoneblock.items.AmethystShardItems.*;

import java.util.Collections;

import static org.hqcplays.hqcsoneblock.items.AmethystShardItems.redShard;

public class AccessoryItems implements Listener {
    // Amulets
    public static ItemStack redShardAmulet;

    public static void init() {
        createRedShardAmulet();
    }

    public static ItemStack createRedShardAmulet() {
        ItemStack customItem = new ItemStack(Material.REDSTONE_LAMP, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Red Shard Amulet");
        meta.setLore(Collections.singletonList("Gives you bonus health while in your inventory"));
        customItem.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "red_shard_amulet"), customItem);
        sr.shape("S S",
                "RRR",
                "   ");
        sr.setIngredient('S', Material.STRING);
        sr.setIngredient('R', redShard);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomAccessory(customItem);

        return customItem;
    }
}
