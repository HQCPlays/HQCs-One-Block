package org.hqcplays.hqcsoneblock.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.PlayerSaveData;
import org.hqcplays.hqcsoneblock.enchantments.EnchantmentAlgorithm;
import org.hqcplays.hqcsoneblock.enchantments.EnchantmentFilter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class EnchantCommand implements CommandExecutor, Listener {

    EnchantmentFilter enchantmentFilter = new EnchantmentFilter();
    EnchantmentAlgorithm enchantmentAlgorithm = new EnchantmentAlgorithm();
    final int enchantability = 15;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openEnchantMenu(player);
            return true;
        }
        return false;
    }

    private void openEnchantMenu(Player player) {
        Inventory enchantmentGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "ENCHANTMENT TABLE");

        // Placeholder item for decorative slots (gray background)
        ItemStack placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = placeholder.getItemMeta();
        meta.setDisplayName(" ");
        placeholder.setItemMeta(meta);

        for (int i = 0; i < 54; i++){
            enchantmentGUI.setItem(i, placeholder);
        }

        // Placeholder item for decorative slots (purple boarder)
        ItemStack placeholder1 = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        ItemMeta meta1 = placeholder.getItemMeta();
        meta1.setDisplayName(" ");
        placeholder1.setItemMeta(meta1);

        for (int i = 0; i < 9; i++){
            enchantmentGUI.setItem(i, placeholder1);
        }
        for (int i = 0; i < 46; i += 9){
            enchantmentGUI.setItem(i, placeholder1);
        }
        for (int i = 8; i < 54; i += 9){
            enchantmentGUI.setItem(i, placeholder1);
        }
        for (int i = 45; i < 54; i++){
            enchantmentGUI.setItem(i, placeholder1);
        }

        // Define the Enchanting information
        ItemStack enchantingInformation = new ItemStack(Material.ENCHANTING_TABLE);
        ItemMeta enchantingInformationMeta = enchantingInformation.getItemMeta();
        enchantingInformationMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Enchanting Information");
        List<Component> enchantingInformationLore = new ArrayList<>();
        enchantingInformationLore.add(Component.text("- Start enchanting by placing an item in the middle slot at the top of the inventory!").color(NamedTextColor.WHITE));
        enchantingInformationLore.add(Component.text("- To enchant the item, simply press on the book with then enchantment you desire!").color(NamedTextColor.WHITE));
        enchantingInformationMeta.lore(enchantingInformationLore);
        enchantingInformation.setItemMeta(enchantingInformationMeta);
        enchantmentGUI.setItem(22, enchantingInformation); 
        enchantmentGUI.setItem(49, enchantingInformation);

        // Placeholder slots for enchanted books
        enchantmentGUI.setItem(20, new ItemStack(Material.GLASS_PANE));
        enchantmentGUI.setItem(22, new ItemStack(Material.GLASS_PANE));
        enchantmentGUI.setItem(24, new ItemStack(Material.GLASS_PANE));

        // slots for input item and result
        enchantmentGUI.setItem(4, null);
        enchantmentGUI.setItem(40, null);

        player.openInventory(enchantmentGUI);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        // Obtain event information
        Player player = (Player) event.getWhoClicked();
        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
        UUID playerUUID = player.getUniqueId();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return; // Do nothing if player doesn't click anything
        ItemMeta clickedItemMeta = clickedItem.getItemMeta();
        String inventoryTitle = event.getView().getTitle();

        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "ENCHANTMENT TABLE")) {
            event.setCancelled(true); // prevents players from taking item

            EnchantmentAlgorithm enchantmentAlgorithm = new EnchantmentAlgorithm();

            int baseEnchantmentLevel = 30; // Example base enchantment level

            // Enchant the item
            Map<Enchantment, Integer> selectedEnchantments = enchantmentAlgorithm.enchantItem(baseEnchantmentLevel, enchantability, new ItemStack(Material.DIAMOND_PICKAXE));

            // Print the selected enchantments
            System.out.println("Selected Enchantments:");
            for (Map.Entry<Enchantment, Integer> entry : selectedEnchantments.entrySet()) {
                System.out.println(entry.getKey().getName() + " Level " + entry.getValue());
            }
        }
    }
}
