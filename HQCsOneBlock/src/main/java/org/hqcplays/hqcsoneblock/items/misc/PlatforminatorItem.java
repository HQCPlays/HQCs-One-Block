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
import org.hqcplays.hqcsoneblock.items.TechItems;
import org.hqcplays.hqcsoneblock.items.UsableCustomItem;

public class PlatforminatorItem extends UsableCustomItem {
    public PlatforminatorItem() {
        super(Material.STICK, Component.text("Cobblestone Platforminator", NamedTextColor.AQUA), Component.text("Right-click to place a 3x3 cobblestone square!", NamedTextColor.YELLOW));
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "cobblestone_platforminator"), this.item);
        sr.shape("CCC",
                "CTC",
                "SCC");
        sr.setIngredient('S', Material.STICK);
        sr.setIngredient('C', Material.COBBLESTONE);
        sr.setIngredient('T', TechItems.toolCore.item);
        addCraftingRecipe(sr);
    }

    @Override
    public void onUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock != null && !player.getWorld().getName().equals("world")) {
                // TODO: Implement
            }
        }
    }
}
