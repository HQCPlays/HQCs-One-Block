package org.hqcplays.hqcsoneblock.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class BlockCoin implements Listener {
    public static ItemStack blockCoin;


    public static ItemStack createBlockCoin(int value){
        ItemStack blockCoin = new ItemStack(Material.SUNFLOWER, 1);
        ItemMeta meta = blockCoin.getItemMeta();
        final NamespacedKey valueKey = new NamespacedKey(HQCsOneBlock.getPlugin(), "value");
        meta.getPersistentDataContainer().set(valueKey, PersistentDataType.INTEGER, value);


        meta.setDisplayName(ChatColor.GOLD + "Block Coin");
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Value: " + value).color(NamedTextColor.GOLD));
        meta.lore(lore);
        blockCoin.setItemMeta(meta);

        return blockCoin;
    }
}
