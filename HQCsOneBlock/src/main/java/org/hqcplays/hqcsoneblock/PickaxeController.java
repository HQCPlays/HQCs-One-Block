package org.hqcplays.hqcsoneblock;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.items.CustomPickaxes;
import org.hqcplays.hqcsoneblock.numberSheets.PricesSheet;

import java.util.HashMap;
import java.util.UUID;

import static org.hqcplays.hqcsoneblock.HQCsOneBlock.updateScoreboard;

public class PickaxeController implements Listener {
    // Give players a wooden pickaxe if they die and don't have a pickaxe
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        boolean hasPA = false;

        for (ItemStack is : p.getInventory().getContents()) {
            if (is != null) {
                if (isPickaxe(is.getType())) {
                    hasPA = true;
                }
            }
        }

        if (!hasPA) {
            ItemStack newPickaxe = new ItemStack(CustomPickaxes.woodPickaxe);
            p.getInventory().addItem(newPickaxe);
        }
    }

    // Override normal pickaxe crafting
    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();

        if (result == null || !isPickaxe(result.getType())) {
            return;
        }

        switch (result.getType()) {
            case WOODEN_PICKAXE:
                inventory.setResult(CustomPickaxes.woodPickaxe);
                break;
            case STONE_PICKAXE:
                inventory.setResult(CustomPickaxes.stonePickaxe);
                break;
            case IRON_PICKAXE:
                inventory.setResult(CustomPickaxes.ironPickaxe);
                break;
            case GOLDEN_PICKAXE:
                inventory.setResult(CustomPickaxes.goldenPickaxe);
                break;
            case DIAMOND_PICKAXE:
                inventory.setResult(CustomPickaxes.diamondPickaxe);
                break;
            case NETHERITE_PICKAXE:
                inventory.setResult(CustomPickaxes.netheritePickaxe);
                break;
            default:
                break;
        }
    }

    private boolean isPickaxe(Material material) {
        return material == Material.WOODEN_PICKAXE ||
                material == Material.STONE_PICKAXE ||
                material == Material.IRON_PICKAXE ||
                material == Material.GOLDEN_PICKAXE ||
                material == Material.DIAMOND_PICKAXE ||
                material == Material.NETHERITE_PICKAXE;
    }
}
