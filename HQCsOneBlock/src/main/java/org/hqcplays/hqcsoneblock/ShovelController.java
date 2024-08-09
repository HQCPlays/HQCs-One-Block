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
import org.hqcplays.hqcsoneblock.items.CustomShovels;

import java.util.List;

public class ShovelController implements Listener {
    // Give players a wooden Shovel if they die and don't have a Shovel
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        boolean hasPA = false;

        for (ItemStack is : p.getInventory().getContents()) {
            if (is != null) {
                if (isShovel(is.getType())) {
                    hasPA = true;
                }
            }
        }

        if (!hasPA) {
            p.getInventory().addItem(CustomShovels.woodShovel.clone());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        boolean hasPA = false;

        for (ItemStack is : p.getInventory().getContents()) {
            if (is != null) {
                if (isShovel(is.getType())) {
                    hasPA = true;
                }
            }
        }

        if (!hasPA) {
            p.getInventory().addItem(CustomShovels.woodShovel.clone());
        }
    }

    // Override normal Shovel crafting
    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();

        if (result == null || !isVanillaShovel(result)) {
            return;
        }

        switch (result.getType()) {
            case WOODEN_SHOVEL:
                inventory.setResult(CustomShovels.woodShovel);
                break;
            case STONE_SHOVEL:
                inventory.setResult(CustomShovels.stoneShovel);
                break;
            case IRON_SHOVEL:
                inventory.setResult(CustomShovels.ironShovel);
                break;
            case GOLDEN_SHOVEL:
                inventory.setResult(CustomShovels.goldenShovel);
                break;
            case DIAMOND_SHOVEL:
                inventory.setResult(CustomShovels.diamondShovel);
                break;
            case NETHERITE_SHOVEL:
                inventory.setResult(CustomShovels.netheriteShovel);
                break;
            default:
                break;
        }
    }

    private boolean isVanillaShovel(ItemStack item) {
        if (!item.hasItemMeta()) {
            return true; // No meta means it's a default vanilla item
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return true; // No lore means it's a default vanilla item
        }

        // Check if the lore matches any custom Shovel's lore
        List<String> lore = meta.getLore();
        return lore == null || lore.isEmpty() || !isCustomShovelLore(lore.get(0));
    }

    private boolean isCustomShovelLore(String lore) {
        return lore.contains("Mining Speed");
    }

    private boolean isShovel(Material material) {
        return material == Material.WOODEN_SHOVEL ||
                material == Material.STONE_SHOVEL ||
                material == Material.IRON_SHOVEL ||
                material == Material.GOLDEN_SHOVEL ||
                material == Material.DIAMOND_SHOVEL ||
                material == Material.NETHERITE_SHOVEL;
    }
}
