package org.hqcplays.hqcsoneblock.items.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.items.AmethystShardItems;
import org.hqcplays.hqcsoneblock.items.UsableCustomItem;

import java.util.Random;

public class CropformWandItem extends UsableCustomItem {
    public CropformWandItem() {
        super(Material.STICK, Component.text("Cropform Wand", NamedTextColor.GREEN), Component.text("Turns crops into other crops!"));
        ShapedRecipe sr1 = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "cropform_wand"), this.item);
        sr1.shape(" RG",
                " TR",
                "S  ");
        sr1.setIngredient('S', Material.STICK);
        sr1.setIngredient('T', Material.WHEAT_SEEDS);
        sr1.setIngredient('G', AmethystShardItems.greenShard.item);
        sr1.setIngredient('R', AmethystShardItems.rainbowShard.item);
        addCraftingRecipe(sr1);
    }

    @Override
    public void onUse(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.WHEAT || event.getClickedBlock().getType() == Material.PUMPKIN_STEM || event.getClickedBlock().getType() == Material.MELON_STEM || event.getClickedBlock().getType() == Material.PUMPKIN_SEEDS || event.getClickedBlock().getType() == Material.MELON_SEEDS || event.getClickedBlock().getType() == Material.BEETROOT_SEEDS || event.getClickedBlock().getType() == Material.CARROTS || event.getClickedBlock().getType() == Material.POTATOES) {
                Random rand = new Random(System.currentTimeMillis());
                int randomInt = rand.nextInt(6);

                switch (randomInt) {
                    case 0:
                        event.getClickedBlock().setType(Material.WHEAT);
                        break;
                    case 1:
                        event.getClickedBlock().setType(Material.PUMPKIN_STEM);
                        break;
                    case 2:
                        event.getClickedBlock().setType(Material.MELON_STEM);
                        break;
                    case 3:
                        event.getClickedBlock().setType(Material.BEETROOT_SEEDS);
                        break;
                    case 4:
                        event.getClickedBlock().setType(Material.CARROTS);
                        break;
                    case 5:
                        event.getClickedBlock().setType(Material.POTATOES);
                        break;
                }
            }
        }
    }
}
