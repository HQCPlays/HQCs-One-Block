package org.hqcplays.hqcsoneblock.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

public class TechItems {
    // Crafting materials
    public static CustomItem toolCore;
    public static CustomItem automationCore;

    public static void init() {
        Component materialDesc = Component.text("Material", NamedTextColor.GRAY);

        toolCore = new CustomItem(Material.REDSTONE_TORCH, Component.text("Tool Core", NamedTextColor.RED), materialDesc);
        automationCore = new CustomItem(Material.IRON_BLOCK, Component.text("Automation Core", NamedTextColor.RED), materialDesc);

        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "tool_core"), toolCore.item);
        sr.shape("FIF",
                "IRI",
                "FIF");
        sr.setIngredient('F', Material.FLINT);
        sr.setIngredient('I', Material.IRON_INGOT);
        sr.setIngredient('R', Material.REDSTONE_BLOCK);
        toolCore.addCraftingRecipe(sr);

        ShapedRecipe sr2 = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "automation_core"), automationCore.item);
        sr2.shape("AIA",
                "IRI",
                "AIA");
        sr2.setIngredient('A', Material.ANCIENT_DEBRIS);
        sr2.setIngredient('I', Material.IRON_INGOT);
        sr2.setIngredient('R', Material.REDSTONE_BLOCK);
        automationCore.addCraftingRecipe(sr2);
    }
}
