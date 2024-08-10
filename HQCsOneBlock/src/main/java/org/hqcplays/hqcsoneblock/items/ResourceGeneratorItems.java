package org.hqcplays.hqcsoneblock.items;

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
    public static ItemStack ironGenerator;
    public static ItemStack coalGenerator;
    public static ItemStack goldGenerator;
    public static ItemStack redstoneGenerator;
    public static ItemStack lapisGenerator;
    public static ItemStack diamondGenerator;
    public static ItemStack ancientDebrisGenerator;
    public static ItemStack cobblestoneGenerator;

    public static void init() {
        ironGenerator = createGenerator("Iron", "iron_generator", Material.IRON_INGOT);
        coalGenerator = createGenerator("Coal", "coal_generator", Material.COAL);
        goldGenerator = createGenerator("Gold", "gold_generator", Material.GOLD_INGOT);
        redstoneGenerator = createGenerator("Redstone", "redstone_generator", Material.REDSTONE);
        lapisGenerator = createGenerator("Lapis", "lapis_generator", Material.LAPIS_LAZULI);
        diamondGenerator = createGenerator("Diamond", "diamond_generator", Material.DIAMOND);
        ancientDebrisGenerator = createGenerator("Ancient Debris", "ancient_debris_generator", Material.ANCIENT_DEBRIS);
        cobblestoneGenerator = createGenerator("Cobblestone", "cobblestone_generator", Material.COBBLESTONE);
    }

    public static ItemStack createGenerator(String materialName, String craftingKey, Material material) {
        ItemStack customItem = new ItemStack(Material.CHEST, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GREEN + materialName + " Generator");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + "Place this item to start generating " + materialName + " automatically"));
        customItem.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem);
        sr.shape("ICI",
                "CAC",
                "ICI");
        sr.setIngredient('C', Material.CHEST);
        sr.setIngredient('I', material);
        sr.setIngredient('A', automationCore);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomAutomaton(customItem);

        return customItem;
    }
}
