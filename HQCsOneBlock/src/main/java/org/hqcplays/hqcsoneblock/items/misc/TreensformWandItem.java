package org.hqcplays.hqcsoneblock.items.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.items.AmethystShardItems;
import org.hqcplays.hqcsoneblock.items.UsableCustomItem;

import java.util.Random;

public class TreensformWandItem extends UsableCustomItem {
    public TreensformWandItem() {
        super(Material.STICK, Component.text("Tree-nsform Wand", NamedTextColor.GREEN), Component.text("Turns saplings into other types of saplings!"));
        ShapedRecipe sr1 = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "treensform_wand"), this.item);
        sr1.shape(" RG",
                " TR",
                "S  ");
        sr1.setIngredient('S', Material.STICK);
        sr1.setIngredient('T', Material.OAK_SAPLING);
        sr1.setIngredient('G', AmethystShardItems.greenShard.item);
        sr1.setIngredient('R', AmethystShardItems.rainbowShard.item);
        addCraftingRecipe(sr1);
    }

    @Override
    public void onUse(PlayerInteractEvent event) {
        if (event.getAction().isRightClick()) {
            if (event.getClickedBlock().getType() == Material.OAK_SAPLING || event.getClickedBlock().getType() == Material.ACACIA_SAPLING || event.getClickedBlock().getType() == Material.DARK_OAK_SAPLING || event.getClickedBlock().getType() == Material.CHERRY_SAPLING || event.getClickedBlock().getType() == Material.JUNGLE_SAPLING || event.getClickedBlock().getType() == Material.BIRCH_SAPLING || event.getClickedBlock().getType() == Material.SPRUCE_SAPLING) {
                Random rand = new Random(System.currentTimeMillis());
                int randomInt = rand.nextInt(8);

                switch (randomInt) {
                    case 0:
                        event.getClickedBlock().setType(Material.ACACIA_SAPLING);
                        break;
                    case 1:
                        event.getClickedBlock().setType(Material.DARK_OAK_SAPLING);
                        break;
                    case 2:
                        event.getClickedBlock().setType(Material.OAK_SAPLING);
                        break;
                    case 3:
                        event.getClickedBlock().setType(Material.CHERRY_SAPLING);
                        break;
                    case 4:
                        event.getClickedBlock().setType(Material.JUNGLE_SAPLING);
                        break;
                    case 5:
                        event.getClickedBlock().setType(Material.SPRUCE_SAPLING);
                        break;
                    case 6:
                        event.getClickedBlock().setType(Material.BIRCH_SAPLING);
                        break;
                }
            }
        }
    }
}
