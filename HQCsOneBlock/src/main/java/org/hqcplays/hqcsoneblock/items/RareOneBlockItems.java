package org.hqcplays.hqcsoneblock.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class RareOneBlockItems implements Listener {
    // Materials
    public static ItemStack stardust;

    public static void init() {
        createStardust();
    }

    public static void createStardust() {
        ItemStack customItem = new ItemStack(Material.GLOWSTONE_DUST, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Stardust");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + "Crafting Material"));
        customItem.setItemMeta(meta);

        stardust = customItem;
    }
}
