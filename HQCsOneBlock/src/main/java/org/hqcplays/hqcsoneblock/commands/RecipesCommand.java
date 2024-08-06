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

public class RecipesCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Checks if sender is a player, if so open the flea market, otherwise throw error message
        if (sender instanceof Player) {
            Player player = (Player) sender;
            //openRecipesGUI(player);
            return true;
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }
    }

    public void openRecipesGUI(Player player) {
        Inventory recipesGUI = Bukkit.createInventory(null, 18, ChatColor.DARK_GREEN + "RECIPES MAIN MENU");

        // Place menu controls
        placeRecipeTabs(recipesGUI);

        player.openInventory(recipesGUI);
    }

    public void openWeaponRecipesGUI(Player player) {
        Inventory weaponRecipesGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "RECIPES MAIN MENU");

        // Place menu controls
        placeRecipeTabs(weaponRecipesGUI);

        ArrayList<ItemStack> weaponList = new ArrayList<>();
        ItemStack coalSword = VanillaPlusItems.coalSword;
        ItemStack redShardSword = AmethystShardItems.redShardSword;
        ItemStack blueShardSword = AmethystShardItems.blueShardSword;
        ItemStack blackShardSword = AmethystShardItems.blackShardSword;
        ItemStack whiteShardSword = AmethystShardItems.whiteShardSword;
        weaponList.add(coalSword);
        weaponList.add(redShardSword);
        weaponList.add(blueShardSword);
        weaponList.add(blackShardSword);
        weaponList.add(whiteShardSword);

        for (int i = 18; i < weaponList.size()+18; i++){
            weaponRecipesGUI.setItem(i, coalSword);
        }

        player.openInventory(weaponRecipesGUI);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "RECIPES MAIN MENU")) {
            event.setCancelled(true);

            // Check if the click is in the top inventory (custom inventory) only
            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                ItemStack clickedItem = event.getCurrentItem();
                Player player = (Player) event.getWhoClicked();
                if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

                ItemMeta clickedItemMeta = clickedItem.getItemMeta();
                if (clickedItemMeta != null) {
                    if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Weapons")) {
                        openWeaponRecipesGUI(player);
                    }
                    else if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Armors")) {
                        player.performCommand("island");
                    }
                    else if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Accessories")) {
                        player.performCommand("island");
                    }
                    else if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Tools")) {
                        player.performCommand("island");
                    }
                    else if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Wands")) {
                        player.performCommand("island");
                    }
                }
            }
        }
    }

    private ItemStack createRecipeCatagory(Material displayItem, String displayName) {

        // Create a new Warp button
        ItemStack newButton = new ItemStack(displayItem);
        newButton.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta newButtonMeta = newButton.getItemMeta();
        newButtonMeta.setDisplayName(ChatColor.GREEN + displayName);
        newButton.setItemMeta(newButtonMeta);

        return newButton;

    }

    private void placeRecipeTabs(Inventory gui){

        // Create new warp buttons
        ItemStack weaponsButton = createRecipeCatagory(Material.DIAMOND_SWORD, "Weapons");
        ItemStack armorsButton = createRecipeCatagory(Material.DIAMOND_CHESTPLATE, "Armors");
        ItemStack accessoryButton = createRecipeCatagory(Material.SHIELD, "Accessories");          
        ItemStack toolsButton = createRecipeCatagory(Material.DIAMOND_PICKAXE, "Tools");
        ItemStack wandsButton = createRecipeCatagory(Material.STICK, "Wands");

        // Place warp buttons
        gui.setItem(2, weaponsButton);
        gui.setItem(3, armorsButton);
        gui.setItem(4, accessoryButton);
        gui.setItem(5, toolsButton);
        gui.setItem(6, wandsButton);

        // Create and place line separating listings and menu controls
        for (int i = 9; i < 18; i++){
            ItemStack pane = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            gui.setItem(i, pane);
        }
    }
}
