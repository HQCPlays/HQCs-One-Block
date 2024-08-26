package org.hqcplays.hqcsoneblock.commands;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.PlayerSaveData;
import org.hqcplays.hqcsoneblock.enchantments.EnchantmentAlgorithm;
import org.hqcplays.hqcsoneblock.enchantments.EnchantmentData;
import org.hqcplays.hqcsoneblock.enchantments.EnchantmentFilter;
import org.hqcplays.hqcsoneblock.enchantments.ShardEnchantment;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class AnvilCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openAnvilMenu(player);
            return true;
        }
        return false;
    }

    private void openAnvilMenu(Player player) {
        Inventory anvilGUI = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "ANVIL");

        // Placeholder item for decorative slots (gray background)
        ItemStack placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = placeholder.getItemMeta();
        meta.setDisplayName(" ");
        placeholder.setItemMeta(meta);

        for (int i = 0; i < 27; i++){
            anvilGUI.setItem(i, placeholder);
        }


        // Define the Anvil information
        ItemStack anvilInformation = new ItemStack(Material.ANVIL);
        ItemMeta anvilInformationMeta = anvilInformation.getItemMeta();
        anvilInformationMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Anvil Information");
        List<Component> anvilInformationLore = new ArrayList<>();
        anvilInformationLore.add(Component.text("- To use the anvil, place two items you want to combine in the two empty slots on the left").color(NamedTextColor.WHITE));
        anvilInformationLore.add(Component.text("- Once you are ready to combine the items, simply take the item from the slot on the right!").color(NamedTextColor.WHITE));
        anvilInformationMeta.lore(anvilInformationLore);
        anvilInformation.setItemMeta(anvilInformationMeta);

        // Place the Anvil
        anvilGUI.setItem(14, anvilInformation);

        // Slots for input and output items
        anvilGUI.setItem(10, null);
        anvilGUI.setItem(12, null);
        anvilGUI.setItem(16, null);


        player.openInventory(anvilGUI);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws InterruptedException {

        if (event.getClickedInventory() == null) return;

        // Obtain event information
        Player player = (Player) event.getWhoClicked();
        Inventory anvilGUI = event.getView().getTopInventory();


        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "ANVIL")) {
            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                // If player is placing an item in the input slots
                if (event.getSlot() == 10 || event.getSlot() == 12) {
                    ItemStack input1 = null;
                    ItemStack input2 = null;
                    ItemStack placedItem = event.getCursor();
                    if (event.getSlot() == 10) {
                        input1 = placedItem;
                        input2 = anvilGUI.getItem(12);
                    }
                    else if (event.getSlot() == 12) {
                        input1 = anvilGUI.getItem(10);
                        input2 = placedItem;
                    }
                    // If both of the input slots are full
                    if (input1 != null && input1.getType() != Material.AIR && input2 != null && input2.getType() != Material.AIR) {
                        
                        ItemMeta input1Meta = input1.getItemMeta();
                        ItemMeta input2Meta = input2.getItemMeta();
                        if (input1Meta != null && input2Meta != null) {
                    
                            // Case 1: Both items have display names (custom items)
                            if (input1Meta.hasDisplayName() && input2Meta.hasDisplayName()) {
                                System.out.println("ACTIVATED");
                                if (input1Meta.getDisplayName().equals(input2Meta.getDisplayName())) {
                                    combineInputs(input1, input2, anvilGUI);
                                }
                            }
                            // Case 2: Neither item has a display name (vanilla items)
                            else if (!input1Meta.hasDisplayName() && !input2Meta.hasDisplayName()) {
                                if (input1.getType() == input2.getType()) {
                                    combineInputs(input1, input2, anvilGUI);
                                }
                            }
                        }
                    } else {
                        anvilGUI.setItem(16, null);
                    }
                }
            }  
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) { // dragging items is different than clicking an item... causes glitches so just tell player no
        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "ENCHANTMENT TABLE")) {

            Player player = (Player) event.getWhoClicked();
            player.sendMessage(ChatColor.RED + "You cannot drag items into the Enchantment Table!");
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // If the player forgets to take their items out of the GUI before closing it, give their items back
        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "ENCHANTMENT TABLE")) {
            ItemStack itemToEnchant = event.getView().getTopInventory().getItem(4);
            if (itemToEnchant != null) {
                Location location = event.getPlayer().getLocation();
                Item item = location.getWorld().dropItem(location, itemToEnchant);
                item.setItemStack(itemToEnchant);
            }
            ItemStack enchantedItem = event.getView().getTopInventory().getItem(40);
            if (enchantedItem != null) {
                Location location = event.getPlayer().getLocation();
                Item item = location.getWorld().dropItem(location, enchantedItem);
                item.setItemStack(enchantedItem);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Block block = event.getClickedBlock();

        // When a player clicks on an enchantment table, cancel the vanilla GUI, count the surrounding bookshelves, and open the custom GUI
        if (block != null && block.getType() == Material.ANVIL) {
            if (action == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
                openAnvilMenu(event.getPlayer());
            }
        }
    }
    private void combineInputs(ItemStack input1, ItemStack input2, Inventory anvilGUI) {
            ItemMeta input1Meta = input1.getItemMeta();
            ItemMeta input2Meta = input2.getItemMeta();

            // Create a cleared copy of the input items
            ItemStack result = new ItemStack(input1);
            ItemMeta resultMeta = result.getItemMeta();
            resultMeta.removeEnchantments();
            resultMeta.lore(null);

            // Apply combined Vanilla enchantments to the new item
            Map <Enchantment, Integer> combinedVanillaEnchants = combineEnchantMaps(input1Meta.getEnchants(), input2Meta.getEnchants());
            System.out.println("Combined Vanilla Enchants: ");
            for (Map.Entry<Enchantment, Integer> entry : combinedVanillaEnchants.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
            for (Map.Entry<Enchantment, Integer> entry : combinedVanillaEnchants.entrySet()) {
                resultMeta.addEnchant(entry.getKey(), entry.getValue(), true);
            }

            // Apply combined Custom enchantments to the new item
            Map <Enchantment, Integer> combinedCustomEnchants = combineEnchantMaps(extractCustomEnchants(input1Meta.getLore()), extractCustomEnchants(input2Meta.getLore()));
            System.out.println("Combined Custom Enchants: ");
            for (Map.Entry<Enchantment, Integer> entry : combinedCustomEnchants.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
            List<String> resultLore = new ArrayList<>();
            for (Map.Entry<Enchantment, Integer> entry : combinedCustomEnchants.entrySet()) {
                Enchantment enchantment = entry.getKey();
                resultLore.add(ChatColor.GRAY + enchantment.getName() + " " + intToRoman(entry.getValue()));
            }
            resultMeta.setLore(resultLore);

            result.setItemMeta(resultMeta);
            anvilGUI.setItem(16, result);
    }

    // Helper method to combine the enchants of two input items
    private static Map<Enchantment, Integer> combineEnchantMaps(Map<Enchantment, Integer> input1Enchants, Map<Enchantment, Integer> input2Enchants) {
        Map<Enchantment, Integer> combinedEnchants = new HashMap<>(input1Enchants);

        // Iterate through the enchantments of input2
        for (Map.Entry<Enchantment, Integer> entry : input2Enchants.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int input2Level = entry.getValue();

            if (combinedEnchants.containsKey(enchantment)) {
                int input1Level = combinedEnchants.get(enchantment);

                if (input2Level > input1Level) {
                    combinedEnchants.put(enchantment, input2Level);
                } else if (input2Level == input1Level) {
                    combinedEnchants.put(enchantment, input1Level + 1);
                }
            } else {
                combinedEnchants.put(enchantment, input2Level);
            }
        }

        return combinedEnchants;
    }

    // Extract custom enchantments from lore
    private static Map<Enchantment, Integer> extractCustomEnchants(List<String> lore) {
        Map<Enchantment, Integer> customEnchants = new HashMap<>();
        if (lore == null) return customEnchants;
        Pattern pattern = Pattern.compile("([a-zA-Z ]+) ([IVXLCDM]+)");

        for (String line : lore) {
            Matcher matcher = pattern.matcher(ChatColor.stripColor(line));
            if (matcher.find()) {
                String enchantName = matcher.group(1).trim();
                String romanLevel = matcher.group(2).trim();
                int level = romanToInt(romanLevel);

                System.out.println("Found enchantment: " + enchantName + " " + level);

                Enchantment enchantment = EnchantmentData.getEnchantmentByName(enchantName.toUpperCase());
                if (enchantment != null) {
                    System.out.println("Activated");
                    customEnchants.put(enchantment, level);
                }
            }
        }

        System.out.println("Custom Enchants: ");
        for (Map.Entry<Enchantment, Integer> entry : customEnchants.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        return customEnchants;
    }

    // Helper method to convert int's to roman numerals
    private String intToRoman(int num) {
        // Define Roman numeral symbols and their values
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        
        // Convert the integer to Roman numeral
        String roman = "";
        roman += thousands[num / 1000];
        roman += hundreds[(num % 1000) / 100];
        roman += tens[(num % 100) / 10];
        roman += units[num % 10];
        
        return roman;
    }

    // Convert Roman numeral to integer
    private static int romanToInt(String roman) {
        if (roman == null || roman.isEmpty()) return 0;
        roman = roman.toUpperCase();
        int total = 0;
        int prevValue = 0;
        for (char c : roman.toCharArray()) {
            int value = romanCharToInt(c);
            if (value > prevValue) {
                total += value - 2 * prevValue; // Subtract twice the value of the previous character
            } else {
                total += value;
            }
            prevValue = value;
        }
        return total;
    }

    private static int romanCharToInt(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }
}
