package org.hqcplays.hqcsoneblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.items.AmethystShardItems;
import org.hqcplays.hqcsoneblock.items.CustomPickaxes;
import org.hqcplays.hqcsoneblock.items.RareOneBlockItems;
import org.hqcplays.hqcsoneblock.items.VanillaPlusItems;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WarpsCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Checks if sender is a player, if so open the flea market, otherwise throw error message
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openWarpsGUI(player);
            return true;
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }
    }

    public void openWarpsGUI(Player player) {
        Inventory warpsGUI = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "WARP MENU");

        // Create new warp buttons
        ItemStack lobbyButton = createWarpButton(Material.BEACON, "Lobby");          // Lobby
        ItemStack islandButton = createWarpButton(Material.GRASS_BLOCK, "Island");   // Island

        // Place warp buttons
        warpsGUI.setItem(12, lobbyButton);
        warpsGUI.setItem(14, islandButton);

        // Create and place line separating listings and menu controls
        for (int i = 0; i < 9; i++){
            ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            warpsGUI.setItem(i, pane);
        }
        for (int i = 18; i < 27; i++){
            ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            warpsGUI.setItem(i, pane);
        }

        player.openInventory(warpsGUI);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "WARP MENU")) {
            event.setCancelled(true);

            // Check if the click is in the top inventory (custom inventory) only
            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                ItemStack clickedItem = event.getCurrentItem();
                Player player = (Player) event.getWhoClicked();
                if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

                ItemMeta clickedItemMeta = clickedItem.getItemMeta();
                if (clickedItemMeta != null) {
                    if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Lobby")) {
                        player.performCommand("lobby");
                    }
                    else if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Island")) {
                        player.performCommand("island");
                    }
                }
            }
        }
    }

    private ItemStack createWarpButton(Material displayBlock, String displayName) {

        // Create a new Warp button
        ItemStack newButton = new ItemStack(displayBlock);
        newButton.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta newButtonMeta = newButton.getItemMeta();
        newButtonMeta.setDisplayName(ChatColor.GREEN + displayName);
        newButton.setItemMeta(newButtonMeta);

        return newButton;

    }
}
