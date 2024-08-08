package org.hqcplays.hqcsoneblock.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.hqcplays.hqcsoneblock.enchantments.ShardEnchantment;

import java.util.List;
import java.util.Objects;

public class CustomItem {
    public final ItemStack item;

    public CustomItem(Material material, Component name, Component desc) {
        this.item = ItemStack.of(material);
        ItemMeta meta = this.item.getItemMeta();
        meta.displayName(name);
        if (desc != null)
            meta.lore(List.of(desc));
        this.item.setItemMeta(meta);
    }

    public CustomItem(Material material, String name, TextColor color) {
        this(material, Component.text(name, color), null);
    }

    public void addShardEnchantment(ShardEnchantment enchantment) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta.hasLore())
            Objects.requireNonNull(meta.lore()).add(enchantment.getLoreName());
        else
            meta.lore(List.of(enchantment.getLoreName()));
        meta.getPersistentDataContainer().set(enchantment.getItemMetaKey(), PersistentDataType.BOOLEAN, true);
        this.item.setItemMeta(meta);
    }

    public void addCraftingRecipe(CraftingRecipe recipe) {
        Bukkit.getServer().addRecipe(recipe);
    }
}
