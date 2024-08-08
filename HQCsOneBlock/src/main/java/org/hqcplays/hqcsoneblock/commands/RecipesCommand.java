package org.hqcplays.hqcsoneblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.items.AccessoryItems;
import org.hqcplays.hqcsoneblock.items.AmethystShardItems;
import org.hqcplays.hqcsoneblock.items.CustomPickaxes;
import org.hqcplays.hqcsoneblock.items.RareOneBlockItems;
import org.hqcplays.hqcsoneblock.items.VanillaPlusItems;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipesCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Checks if sender is a player, if so open the flea market, otherwise throw error message
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openRecipesGUI(player);
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

        // Create and place custom items
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
            weaponRecipesGUI.setItem(i, weaponList.get(i-18));
        }

        player.openInventory(weaponRecipesGUI);
    }

    public void openCraftingGridGUI(Player player, ItemStack item) {
        Inventory craftingGridGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "CRAFTING GRID");


        Map<Integer, ItemStack> recipeMaterials = getCraftingRecipe(item);

         // 3x3 crafting grid starting at index 30 in the 54-slot inventory
         int startIndex = 10;
         int[] gridIndices = {
                 startIndex, startIndex + 1, startIndex + 2,
                 startIndex + 9, startIndex + 10, startIndex + 11,
                 startIndex + 18, startIndex + 19, startIndex + 20
         };
 
         for (int i = 0; i < 9; i++) {
             ItemStack material = recipeMaterials.get(i);
             if (material != null) {
                 craftingGridGUI.setItem(gridIndices[i], material);
             }
         }

         // Create back button
        ItemStack backButton = new ItemStack(Material.FIREWORK_ROCKET);
        backButton.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta backButtonMeta = backButton.getItemMeta();
        backButtonMeta.setDisplayName(ChatColor.GREEN + "Back");
        backButton.setItemMeta(backButtonMeta);


        craftingGridGUI.setItem(startIndex+15, item);
        craftingGridGUI.setItem(startIndex+13, new ItemStack(Material.CRAFTER));
        craftingGridGUI.setItem(45, backButton);


        for (int i = 0; i < craftingGridGUI.getSize(); i++) {
            if (!contains(gridIndices, i) && craftingGridGUI.getItem(i) == null) {
                craftingGridGUI.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            }
        }

        player.openInventory(craftingGridGUI);
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
                        ItemStack item = new ItemStack(CustomPickaxes.lapisPickaxe);
                        //openCraftingGridGUI(player, new ItemStack(CustomPickaxes.lapisPickaxe));
                        //openWeaponRecipesGUI(player);
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

        // Create and place line separating recipes and menu controls
        for (int i = 9; i < 18; i++){
            ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            gui.setItem(i, pane);
        }
    }

    public Map<Integer, ItemStack> getCraftingRecipe(ItemStack item) {
        for (Recipe recipe : Bukkit.getRecipesFor(item)) {
            if (recipe instanceof ShapedRecipe) {
                ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
                ItemStack result = shapedRecipe.getResult();

                // Check if the result item has the same custom metadata
                if (hasSameCustomMetadata(result, item)) {
                    return getShapedRecipeMaterials(shapedRecipe);
                }
            } else if (recipe instanceof ShapelessRecipe) {
                ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
                ItemStack result = shapelessRecipe.getResult();

                // Check if the result item has the same custom metadata
                if (hasSameCustomMetadata(result, item)) {
                    return getShapelessRecipeMaterials(shapelessRecipe);
                }
            }
        }
        return null;
    }

    private boolean hasSameCustomMetadata(ItemStack item1, ItemStack item2) {
        // Compare the custom metadata
        if (item1.hasItemMeta() && item2.hasItemMeta()) {
            String customName1 = PlainTextComponentSerializer.plainText().serialize(item1.displayName());
            String customName2 = PlainTextComponentSerializer.plainText().serialize(item1.displayName());
            return customName1 != null && customName1.equals(customName2);
        }
        return false;
    }

    private Map<Integer, ItemStack> getShapedRecipeMaterials(ShapedRecipe recipe) {
        Map<Integer, ItemStack> materials = new HashMap<>();
        String[] shape = recipe.getShape();
        Map<Character, ItemStack> ingredientMap = recipe.getIngredientMap();

        // Determine the smallest bounding box of the shape
        int minRow = 3, maxRow = 0, minCol = 3, maxCol = 0;
        for (int row = 0; row < shape.length; row++) {
            String line = shape[row];
            for (int col = 0; col < line.length(); col++) {
                if (line.charAt(col) != ' ') {
                    if (row < minRow) minRow = row;
                    if (row > maxRow) maxRow = row;
                    if (col < minCol) minCol = col;
                    if (col > maxCol) maxCol = col;
                }
            }
        }

        // Calculate offset to center the shape
        int rowOffset = (3 - (maxRow - minRow + 1)) / 2 - minRow;
        int colOffset = (3 - (maxCol - minCol + 1)) / 2 - minCol;

        // Initialize all slots to AIR
        for (int i = 0; i < 9; i++) {
            materials.put(i, new ItemStack(Material.AIR));
        }

        // Map materials to correct slots in the 3x3 grid with offset
        for (int row = 0; row < shape.length; row++) {
            String line = shape[row];
            for (int col = 0; col < line.length(); col++) {
                char key = line.charAt(col);
                ItemStack ingredient = ingredientMap.get(key);

                // Calculate the index in the 3x3 grid with offset
                int index = (row + rowOffset) * 3 + (col + colOffset);
                if (ingredient != null) {
                    materials.put(index, ingredient);
                }
            }
        }

        return materials;
    }

    private Map<Integer, ItemStack> getShapelessRecipeMaterials(ShapelessRecipe recipe) {
        Map<Integer, ItemStack> materials = new HashMap<>();
        List<ItemStack> ingredientList = recipe.getIngredientList();

        // Initialize all slots to AIR
        for (int i = 0; i < 9; i++) {
            materials.put(i, new ItemStack(Material.AIR));
        }

        // Map materials to slots starting from 0
        for (int i = 0; i < ingredientList.size(); i++) {
            ItemStack ingredient = ingredientList.get(i);
            if (ingredient != null) {
                materials.put(i, ingredient);
            }
        }

        return materials;
    }

    private boolean contains(int[] array, int key) {
        for (int value : array) {
            if (value == key) {
                return true;
            }
        }
        return false;
    }
}
