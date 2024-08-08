package org.hqcplays.hqcsoneblock.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class RareOneBlockItems {
    // Materials
    public static CustomItem stardust;

    public static void init() {
        stardust = new CustomItem(Material.GLOWSTONE_DUST, Component.text("Stardust", NamedTextColor.GOLD), Component.text("Crafting Material", NamedTextColor.GRAY));
    }
}
