package org.hqcplays.hqcsoneblock;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.items.CustomAxes;

import java.util.List;

public class AxeController implements Listener {
    // Give players a wooden Axe if they die and don't have a Axe
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        boolean hasAxe = false;

        for (ItemStack is : p.getInventory().getContents()) {
            if (is != null) {
                if (isAxe(is.getType())) {
                    hasAxe = true;
                }
            }
        }

        if (!hasAxe) {
            p.getInventory().addItem(CustomAxes.woodAxe.clone());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        boolean hasAxe = false;

        for (ItemStack is : p.getInventory().getContents()) {
            if (is != null) {
                if (isAxe(is.getType())) {
                    hasAxe = true;
                }
            }
        }

        if (!hasAxe) {
            p.getInventory().addItem(CustomAxes.woodAxe.clone());
        }
    }

    // Override normal Axe crafting
    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();

        if (result == null || !isVanillaAxe(result)) {
            return;
        }

        switch (result.getType()) {
            case WOODEN_AXE:
                inventory.setResult(CustomAxes.woodAxe);
                break;
            case STONE_AXE:
                inventory.setResult(CustomAxes.stoneAxe);
                break;
            case IRON_AXE:
                inventory.setResult(CustomAxes.ironAxe);
                break;
            case GOLDEN_AXE:
                inventory.setResult(CustomAxes.goldenAxe);
                break;
            case DIAMOND_AXE:
                inventory.setResult(CustomAxes.diamondAxe);
                break;
            case NETHERITE_AXE:
                inventory.setResult(CustomAxes.netheriteAxe);
                break;
            default:
                break;
        }
    }

    private boolean isVanillaAxe(ItemStack item) {
        if (!item.hasItemMeta()) {
            return true; // No meta means it's a default vanilla item
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return true; // No lore means it's a default vanilla item
        }

        // Check if the lore matches any custom Axe's lore
        List<String> lore = meta.getLore();
        return lore == null || lore.isEmpty() || !isCustomAxeLore(lore.get(0));
    }

    private boolean isCustomAxeLore(String lore) {
        return lore.contains("Mining Speed");
    }

    private boolean isAxe(Material material) {
        return material == Material.WOODEN_AXE ||
                material == Material.STONE_AXE ||
                material == Material.IRON_AXE ||
                material == Material.GOLDEN_AXE ||
                material == Material.DIAMOND_AXE ||
                material == Material.NETHERITE_AXE;
    }
}
