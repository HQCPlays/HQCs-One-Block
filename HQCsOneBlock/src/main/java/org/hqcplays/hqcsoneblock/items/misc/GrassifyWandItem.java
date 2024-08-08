package org.hqcplays.hqcsoneblock.items.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.items.AmethystShardItems;
import org.hqcplays.hqcsoneblock.items.UsableCustomItem;

public class GrassifyWandItem extends UsableCustomItem {
    public GrassifyWandItem() {
        super(Material.STICK, Component.text("Grassify Wand", NamedTextColor.GREEN), Component.text("Turns dirt into grass!"));
        ShapedRecipe sr1 = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "grassify_wand"), this.item);
        sr1.shape("  G",
                " D ",
                "S  ");
        sr1.setIngredient('S', Material.STICK);
        sr1.setIngredient('D', Material.DIRT);
        sr1.setIngredient('G', AmethystShardItems.greenShard.item);
        addCraftingRecipe(sr1);
    }

    @Override
    public void onUse(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.DIRT) {
            event.getClickedBlock().setType(Material.GRASS_BLOCK);
        }
    }
}
