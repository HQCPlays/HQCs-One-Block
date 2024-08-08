package org.hqcplays.hqcsoneblock.items.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.items.UsableCustomItem;

public class CoalSwordItem extends UsableCustomItem {
    public CoalSwordItem() {
        super(Material.STONE_SWORD, Component.text("Coal Sword", NamedTextColor.DARK_GRAY), Component.text("Right-click to place a torch!", NamedTextColor.YELLOW));
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "coal_sword"), this.item);
        sr.shape(" C ",
                " C ",
                " S ");
        sr.setIngredient('S', Material.STICK);
        sr.setIngredient('C', Material.COAL);
        addCraftingRecipe(sr);
    }

    @Override
    public void onUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock != null && !player.getWorld().getName().equals("world")) {
                Block blockToPlaceTorch = clickedBlock.getRelative(event.getBlockFace());
                if (blockToPlaceTorch.getType() == Material.AIR) {
                    blockToPlaceTorch.setType(Material.TORCH);
                }
            }
        }
    }
}
