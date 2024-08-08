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

import static org.hqcplays.hqcsoneblock.items.TechItems.automationCore;

public class ResourceGeneratorItems {
    public static CustomItem ironGenerator;
    public static CustomItem coalGenerator;
    public static CustomItem goldGenerator;
    public static CustomItem redstoneGenerator;
    public static CustomItem lapisGenerator;
    public static CustomItem diamondGenerator;
    public static CustomItem ancientDebrisGenerator;
    public static CustomItem cobblestoneGenerator;

    public static class GeneratorItem extends CustomItem {
        public GeneratorItem(String materialName, String craftingKey, Material material) {
            super(Material.CHEST,
                    Component.text(materialName + " Generator", NamedTextColor.DARK_GREEN),
                    Component.text("Place this item to start generating " + materialName + " automatically", NamedTextColor.GRAY));

            ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), this.item);
            sr.shape("ICI",
                    "CAC",
                    "ICI");
            sr.setIngredient('C', Material.CHEST);
            sr.setIngredient('I', material);
            sr.setIngredient('A', automationCore.item);
            addCraftingRecipe(sr);
        }
    }

    public static void init() {
        ironGenerator = new GeneratorItem("Iron", "iron_generator", Material.IRON_INGOT);
        coalGenerator = new GeneratorItem("Coal", "coal_generator", Material.COAL);
        goldGenerator = new GeneratorItem("Gold", "gold_generator", Material.GOLD_INGOT);
        redstoneGenerator = new GeneratorItem("Redstone", "redstone_generator", Material.REDSTONE);
        lapisGenerator = new GeneratorItem("Lapis", "lapis_generator", Material.LAPIS_LAZULI);
        diamondGenerator = new GeneratorItem("Diamond", "diamond_generator", Material.DIAMOND);
        ancientDebrisGenerator = new GeneratorItem("Ancient Debris", "ancient_debris_generator", Material.ANCIENT_DEBRIS);
        cobblestoneGenerator = new GeneratorItem("Cobblestone", "cobblestone_generator", Material.COBBLESTONE);
    }
}
