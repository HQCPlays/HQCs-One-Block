package org.hqcplays.hqcsoneblock.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

import java.util.Collections;

public class TechItems implements Listener {
    // Crafting materials
    public static ItemStack toolCore;
    public static ItemStack automationCore;

    public static void init() {
        createToolCore();
        createAutomationCore();
    }

    public static void createToolCore() {
        ItemStack customItem = new ItemStack(Material.REDSTONE_TORCH, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Tool Core");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + "Material"));
        customItem.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "tool_core"), customItem);
        sr.shape("FIF",
                "IRI",
                "FIF");
        sr.setIngredient('F', Material.FLINT);
        sr.setIngredient('I', Material.IRON_INGOT);
        sr.setIngredient('R', Material.REDSTONE_BLOCK);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomTool(customItem);

        toolCore = customItem;
    }

    public static void createAutomationCore() {
        ItemStack customItem = new ItemStack(Material.IRON_BLOCK, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Automation Core");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + "Material"));
        customItem.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "automation_core"), customItem);
        sr.shape("AIA",
                "IRI",
                "AIA");
        sr.setIngredient('A', Material.ANCIENT_DEBRIS);
        sr.setIngredient('I', Material.IRON_INGOT);
        sr.setIngredient('R', Material.REDSTONE_BLOCK);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomAutomaton(customItem);

        automationCore = customItem;
    }
}
