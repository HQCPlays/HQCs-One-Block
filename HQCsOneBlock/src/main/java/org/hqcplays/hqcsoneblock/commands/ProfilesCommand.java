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
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

public class ProfilesCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openProgressionMenu(player);
            return true;
        }
        return false;
    }

    private void openProgressionMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 9, ChatColor.GREEN + "Profiles");
        int selectedProfile = HQCsOneBlock.dataManager.getSelectedProfile(player);

        ItemStack profile1 = new ItemStack(selectedProfile == 0 ? Material.BLUE_WOOL : Material.WHITE_WOOL);
        ItemMeta profile1Meta = profile1.getItemMeta();
        profile1Meta.setDisplayName(ChatColor.AQUA + "Profile 1");
        profile1.setItemMeta(profile1Meta);

        ItemStack profile2 = new ItemStack(selectedProfile == 1 ? Material.BLUE_WOOL : Material.WHITE_WOOL);
        ItemMeta profile2Meta = profile2.getItemMeta();
        profile2Meta.setDisplayName(ChatColor.AQUA + "Profile 2");
        profile2.setItemMeta(profile2Meta);

        ItemStack profile3 = new ItemStack(selectedProfile == 2 ? Material.BLUE_WOOL : Material.WHITE_WOOL);
        ItemMeta profile3Meta = profile3.getItemMeta();
        profile3Meta.setDisplayName(ChatColor.AQUA + "Profile 3");
        profile3.setItemMeta(profile3Meta);

        menu.setItem(2, profile1);
        menu.setItem(4, profile2);
        menu.setItem(6, profile3);

        player.openInventory(menu);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item != null && event.getView().getTitle().equals(ChatColor.GREEN + "Profiles")) {

            int chosenProfile = -1;
            if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Profile 1"))
                chosenProfile = 0;
            else if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Profile 2"))
                chosenProfile = 1;
            else if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Profile 3"))
                chosenProfile = 2;

            HQCsOneBlock.dataManager.setSelectedProfile(player, chosenProfile);
            HQCsOneBlock.dataManager.getPlayerData(player).miningSpeed = 1;
            event.getClickedInventory().close();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getView().getTitle().equals(ChatColor.GREEN + "Profiles")) {
            event.setCancelled(true);
        }
    }
}
