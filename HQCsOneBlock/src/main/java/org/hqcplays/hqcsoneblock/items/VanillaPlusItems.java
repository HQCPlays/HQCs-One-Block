package org.hqcplays.hqcsoneblock.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

import java.util.Collections;
import java.util.UUID;

public class VanillaPlusItems implements Listener {
    // Swords
    public static ItemStack coalSword;

    public static void init() {
        createCoalSword();
    }

    public static void createCoalSword() {
        ItemStack customItem = new ItemStack(Material.STONE_SWORD, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "Coal Sword");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "Right-click to place a torch!"));
        customItem.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "coal_sword"), customItem);
        sr.shape(" C ",
                " C ",
                " S ");
        sr.setIngredient('S', Material.STICK);
        sr.setIngredient('C', Material.COAL);
        Bukkit.getServer().addRecipe(sr);

        coalSword = customItem;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        UUID playerUUID = player.getUniqueId();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            // Check items
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "Coal Sword")) {
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock != null && !player.getWorld().getName().equals("world")) {
                    Block blockAbove = clickedBlock.getRelative(org.bukkit.block.BlockFace.UP);
                    blockAbove.setType(Material.TORCH);
                }
            }
        }
    }
}
