package org.hqcplays.hqcsoneblock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class MenuItemController implements Listener {
    public static final ItemStack MENU_ITEM = createMenuItem();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        ItemStack[] inventory = event.getPlayer().getInventory().getContents();
        inventory[8] = MENU_ITEM;
        event.getPlayer().getInventory().setContents(inventory);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        ItemStack[] inventory = event.getPlayer().getInventory().getContents();
        inventory[8] = MENU_ITEM;
        event.getPlayer().getInventory().setContents(inventory);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().isSimilar(MENU_ITEM)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.getPlayer().getInventory().remove(MENU_ITEM);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().isSimilar(MENU_ITEM)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "Main Menu")) {
            event.setCancelled(true);
        }
    }

    private static ItemStack createMenuItem() {
        ItemStack item = new ItemStack(Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Menu");
        item.setItemMeta(meta);
        return item;
    }
}
