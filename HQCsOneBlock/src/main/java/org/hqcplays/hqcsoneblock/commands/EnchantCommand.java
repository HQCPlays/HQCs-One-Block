package org.hqcplays.hqcsoneblock.commands;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
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
import org.hqcplays.hqcsoneblock.enchantments.EnchantmentFilter;
import org.hqcplays.hqcsoneblock.enchantments.ShardEnchantment;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class EnchantCommand implements CommandExecutor, Listener {

    EnchantmentFilter enchantmentFilter = new EnchantmentFilter();
    EnchantmentAlgorithm enchantmentAlgorithm = new EnchantmentAlgorithm();
    List<String> customEnchants = List.of("Asphyxiation", "Turbulence", "Vampirism", "Vitality", "Voiding");
    final int enchantability = 15;

    // Variables for finding bookshelves around the enchantment table
    private static final int RADIUS = 5;
    private static final int INNER_RADIUS = 3;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openEnchantMenu(player, 18);
            return true;
        }
        return false;
    }

    private void openEnchantMenu(Player player, int bookshelvesCount) {
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

        // Store the bookshelf count in the enchanting information for later
        NamespacedKey bookshelvesKey = new NamespacedKey(HQCsOneBlock.getPlugin(), "bookshelves_count");
        PersistentDataContainer container = enchantingInformationMeta.getPersistentDataContainer();
        container.set(bookshelvesKey, PersistentDataType.INTEGER, bookshelvesCount);
        enchantingInformation.setItemMeta(enchantingInformationMeta);
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
        Inventory enchantmentGUI = event.getView().getTopInventory();


        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "ENCHANTMENT TABLE")) {
            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                if (event.getClick() != ClickType.DOUBLE_CLICK){ // double clicks cause glitches
                    // If the player is trying to place an item in the enchanting slot
                    if (event.getSlot() == 4) {
                        ItemStack itemToEnchant = event.getCursor();
                        List<String> customEnchants = List.of("Asphyxiation", "Turbulence", "Vampirism", "Vitality", "Voiding");
                        // Make sure already enchanted items cannot be re-enchanted
                        if (itemToEnchant.getEnchantments().isEmpty() && !doesLoreContain(itemToEnchant, customEnchants)) {
                            if (itemToEnchant != null) {
                                if (itemToEnchant.getType() !=  Material.AIR) {
                                    if (event.getClick() != ClickType.SHIFT_LEFT && event.getClick() != ClickType.SHIFT_RIGHT) {
                                        // item was placed into the enchanting slot
                                        ItemStack enchantingInformation = event.getView().getTopInventory().getItem(49);
                                        ItemMeta enchantingInformationMeta = enchantingInformation.getItemMeta();
                                        NamespacedKey key = new NamespacedKey(HQCsOneBlock.getPlugin(), "bookshelves_count");
                                        PersistentDataContainer container = enchantingInformationMeta.getPersistentDataContainer();
                                        int bookshelvesCount = container.get(key, PersistentDataType.INTEGER); // Retrieve the integer value
                                        setPossibleEnchants(enchantmentGUI, itemToEnchant, bookshelvesCount);
                                    }
                                } else {
                                    if (event.getCurrentItem() != null) {
                                        // Item was removed from enchanting slot
                                        enchantmentGUI.setItem(20, new ItemStack(Material.GLASS_PANE));
                                        enchantmentGUI.setItem(22, new ItemStack(Material.GLASS_PANE));
                                        enchantmentGUI.setItem(24, new ItemStack(Material.GLASS_PANE));
                                    }
                                }
                            }
                        }
                    }
                    // If the player is clicking on one of the enchantment options
                    else if (event.getSlot() == 20 || event.getSlot() == 22 || event.getSlot() == 24) {
                        event.setCancelled(true); // prevents players from taking item
                        ItemStack clickedItem = event.getCurrentItem();
                        ItemMeta itemMeta = clickedItem.getItemMeta();
                        if (clickedItem.getType() == Material.ENCHANTED_BOOK) {
                            // check if the player has enough XP
                            if (clickedItem.getAmount() <= player.getLevel()) { 
                                if (itemMeta != null) {
                                    NamespacedKey enchantmentKey = new NamespacedKey(HQCsOneBlock.getPlugin(), "enchantment_data");
                                    // Extract the list of enchantments that are stored inside of the book
                                    if (itemMeta.getPersistentDataContainer().has(enchantmentKey, PersistentDataType.STRING)) {
                                        String serializedData = itemMeta.getPersistentDataContainer().get(enchantmentKey, PersistentDataType.STRING);
                                        Map<Enchantment, Integer> enchantments = deserializeEnchantmentMap(serializedData);
        
                                        ItemStack itemToEnchant = event.getView().getTopInventory().getItem(4);
                                        ItemMeta itemToEnchantMeta = itemToEnchant.getItemMeta();
                                        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                                            Enchantment enchantment = entry.getKey();
                                            if (enchantment instanceof ShardEnchantment){   // Custom enchants are stored in the lore of an item
                                                List<Component> lore = new ArrayList<>();
                                                if (itemToEnchantMeta.hasLore()) lore.addAll(itemToEnchantMeta.lore());
                                                lore.add(Component.text(enchantment.getName() + " " + intToRoman(entry.getValue())).color(NamedTextColor.GRAY));
                                                itemToEnchantMeta.lore(lore);
                                                PersistentDataContainer dataContainer = itemToEnchantMeta.getPersistentDataContainer();
                                                dataContainer.set(enchantment.getKey(), PersistentDataType.BOOLEAN, true);
                                            } else { // Use vanilla methods for vanilla enchants
                                                itemToEnchantMeta.addEnchant(entry.getKey(), entry.getValue(), true);
                                            }
                                        }

                                        // Take the players XP
                                        player.setLevel(player.getLevel() - clickedItem.getAmount());

                                        // Place new enchanted item in the finish slot
                                        if (itemToEnchant.getType() == Material.BOOK) {
                                            ItemStack enchantedItem = new ItemStack(Material.ENCHANTED_BOOK); // convert books to enchanted books
                                            enchantedItem.setItemMeta(itemToEnchantMeta);
                                            enchantmentGUI.setItem(4, null);
                                            enchantmentGUI.setItem(40, enchantedItem);
                                        } else { // regular items
                                            itemToEnchant.setItemMeta(itemToEnchantMeta);
                                            enchantmentGUI.setItem(4, null);
                                            enchantmentGUI.setItem(40, itemToEnchant);
                                        }
        
                                        // Reset the GUI
                                        enchantmentGUI.setItem(20, new ItemStack(Material.GLASS_PANE));
                                        enchantmentGUI.setItem(22, new ItemStack(Material.GLASS_PANE));
                                        enchantmentGUI.setItem(24, new ItemStack(Material.GLASS_PANE));
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Not enough experience!");
                            }
                        }
                    }
                    // If player is clicking on the finished slot
                    else if (event.getSlot() == 40) {
                        // Allow player to take the item
                    }
                    else {
                        event.setCancelled(true); // prevents players from taking a display item 
                    }
                }
            // If player is shift-clicking an item into the enchanting slot (same logic as above)
            } else if (event.getClickedInventory() == event.getView().getBottomInventory()) {
                if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                    ItemStack itemToEnchant = event.getCurrentItem();
                    if (itemToEnchant.getEnchantments().isEmpty() && !doesLoreContain(itemToEnchant, customEnchants)) {
                        if (itemToEnchant != null) {
                            ItemStack enchantingInformation = event.getView().getTopInventory().getItem(49);
                            ItemMeta enchantingInformationMeta = enchantingInformation.getItemMeta();
                            NamespacedKey key = new NamespacedKey(HQCsOneBlock.getPlugin(), "bookshelves_count");
                            PersistentDataContainer container = enchantingInformationMeta.getPersistentDataContainer();
                            int bookshelvesCount = container.get(key, PersistentDataType.INTEGER); // Retrieve the integer value
                            setPossibleEnchants(enchantmentGUI, itemToEnchant, bookshelvesCount);
                        }
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
        if (block != null && block.getType() == Material.ENCHANTING_TABLE) {
            if (action == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
                int bookshelves = countBookshelves(block);
                openEnchantMenu(event.getPlayer(), bookshelves);
            }
        }
    }
    
    // Method for counting bookshelves surrounding an enchantment table
    private int countBookshelves(Block table) {
        int count = 0;
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                // Skip the 3x3 area around the table
                if (Math.abs(x) <= INNER_RADIUS / 2 && Math.abs(z) <= INNER_RADIUS / 2) {
                    continue;
                }
                // Check the current layer and the layer above
                Block relative = table.getRelative(x, 0, z);
                Block aboveRelative = relative.getRelative(0, 1, 0);
                if (relative.getType() == Material.BOOKSHELF) {
                    count++;
                }
                if (aboveRelative.getType() == Material.BOOKSHELF) {
                    count++;
                }
            }
        }
        return count;
    }

    // Method for placing the enchantment options within the GUI
    private void setPossibleEnchants(Inventory gui, ItemStack itemToEnchant, int bookshelvesCount) {

        // Get the enchantment options
        Map<Enchantment, Integer> enchantOption1 = enchantmentAlgorithm.enchantItem(Math.min(1+bookshelvesCount, 10), enchantability, itemToEnchant);
        Map<Enchantment, Integer> enchantOption2 = enchantmentAlgorithm.enchantItem(Math.min(6+bookshelvesCount, 21), enchantability, itemToEnchant);
        Map<Enchantment, Integer> enchantOption3 = enchantmentAlgorithm.enchantItem(Math.min(12+bookshelvesCount, 30), enchantability, itemToEnchant);

        // Check if a valid item was placed for enchanting, empty enchantment options means player tried to enchant an invalid item
        if (!enchantOption1.isEmpty()) {
            NamespacedKey enchantmentKey = new NamespacedKey(HQCsOneBlock.getPlugin(), "enchantment_data");

            // Enchantment 1
            ItemStack enchantment1 = new ItemStack(Material.ENCHANTED_BOOK, Math.min(1+bookshelvesCount, 10));
            ItemMeta enchantment1Meta = enchantment1.getItemMeta();
            Map.Entry<Enchantment, Integer> randomEnchant1 = getRandomEntry(enchantOption1);
            enchantment1Meta.displayName(randomEnchant1.getKey().displayName(randomEnchant1.getValue()).color(NamedTextColor.GOLD));
            List<Component> enchantment1Lore = new ArrayList<>();
            enchantment1Lore.add(Component.text("Experience Required: " + Math.min(1+bookshelvesCount, 10)).color(NamedTextColor.LIGHT_PURPLE));
            enchantment1Meta.lore(enchantment1Lore);

            // Serialize and store the enchantment data
            enchantment1Meta.getPersistentDataContainer().set(enchantmentKey, PersistentDataType.STRING, serializeEnchantmentMap(enchantOption1));
            enchantment1.setItemMeta(enchantment1Meta);
            gui.setItem(20, enchantment1);  // Enchantment 1

            // Enchantment 2
            ItemStack enchantment2 = new ItemStack(Material.ENCHANTED_BOOK, Math.min(6+bookshelvesCount, 21));
            ItemMeta enchantment2Meta = enchantment2.getItemMeta();
            Map.Entry<Enchantment, Integer> randomEnchant2 = enchantOption2.entrySet().iterator().next();
            enchantment2Meta.displayName(randomEnchant2.getKey().displayName(randomEnchant2.getValue()).color(NamedTextColor.GOLD));
            List<Component> enchantment2Lore = new ArrayList<>();
            enchantment2Lore.add(Component.text("Experience Required: " + Math.min(6+bookshelvesCount, 21)).color(NamedTextColor.LIGHT_PURPLE));
            enchantment2Meta.lore(enchantment2Lore);

            // Serialize and store the enchantment data
            enchantment2Meta.getPersistentDataContainer().set(enchantmentKey, PersistentDataType.STRING, serializeEnchantmentMap(enchantOption2));
            enchantment2.setItemMeta(enchantment2Meta);
            gui.setItem(22, enchantment2);  // Enchantment 2

            // Enchantment 3
            ItemStack enchantment3 = new ItemStack(Material.ENCHANTED_BOOK, Math.min(12+bookshelvesCount, 30));
            ItemMeta enchantment3Meta = enchantment3.getItemMeta();
            Map.Entry<Enchantment, Integer> randomEnchant3 = enchantOption3.entrySet().iterator().next();
            enchantment3Meta.displayName(randomEnchant3.getKey().displayName(randomEnchant3.getValue()).color(NamedTextColor.GOLD));
            List<Component> enchantment3Lore = new ArrayList<>();
            enchantment3Lore.add(Component.text("Experience Required: " + Math.min(12+bookshelvesCount, 30)).color(NamedTextColor.LIGHT_PURPLE));
            enchantment3Meta.lore(enchantment3Lore);

            // Serialize and store the enchantment data
            enchantment3Meta.getPersistentDataContainer().set(enchantmentKey, PersistentDataType.STRING, serializeEnchantmentMap(enchantOption3));
            enchantment3.setItemMeta(enchantment3Meta);
            gui.setItem(24, enchantment3);  // Enchantment 3
        }
    }

    // Method for choosing a random entry from a map
    public static Map.Entry<Enchantment, Integer> getRandomEntry(Map<Enchantment, Integer> map) {
        if (map.isEmpty()) {
            return null; // return null if the map is empty
        }
        
        // Convert map entries to a list
        List<Map.Entry<Enchantment, Integer>> entryList = new ArrayList<>(map.entrySet());
        
        // Generate a random index
        Random random = new Random();
        int randomIndex = random.nextInt(entryList.size());
        
        // Return the entry at the random index
        return entryList.get(randomIndex);
    }

    // Method to serialize a map of chosen enchantments into a string
    private String serializeEnchantmentMap(Map<Enchantment, Integer> enchantmentMap) {
        StringBuilder serialized = new StringBuilder();
        for (Map.Entry<Enchantment, Integer> entry : enchantmentMap.entrySet()) {
            String enchantmentName = getEnchantmentName(entry.getKey());
            int level = entry.getValue();
            serialized.append(enchantmentName).append(":").append(level).append(";");
        }
        if (serialized.length() > 0) {
            serialized.setLength(serialized.length() - 1); // Remove trailing semicolon
        }
        return serialized.toString();
    }
    
    // method to capitalize the enchantment name, required for serialization
    private String getEnchantmentName(Enchantment enchantment) {
        String enchantmentString = enchantment.getKey().getKey();
        if (enchantmentString != null) {
            return enchantmentString.toUpperCase();
        } else {
            return null;
        }
        // switch (enchantment.getKey().getKey()) {
        //     case "protection": return "PROTECTION";
        //     case "fire_protection": return "FIRE_PROTECTION";
        //     case "feather_falling": return "FEATHER_FALLING";
        //     case "blast_protection": return "BLAST_PROTECTION";
        //     case "projectile_protection": return "PROJECTILE_PROTECTION";
        //     case "respiration": return "RESPIRATION";
        //     case "aqua_affinity": return "AQUA_AFFINITY";
        //     case "thorns": return "THORNS";
        //     case "depth_strider": return "DEPTH_STRIDER";
        //     case "frost_walker": return "FROST_WALKER";
        //     case "binding_curse": return "BINDING_CURSE";
        //     case "sharpness": return "SHARPNESS";
        //     case "smite": return "SMITE";
        //     case "bane_of_arthropods": return "BANE_OF_ARTHROPODS";
        //     case "knockback": return "KNOCKBACK";
        //     case "fire_aspect": return "FIRE_ASPECT";
        //     case "looting": return "LOOTING";
        //     case "sweeping": return "SWEEPING_EDGE";
        //     case "asphyxiation": return "ASPHYXIATION";
        //     case "turbulence": return "TURBULENCE";
        //     case "vampirism": return "VAMPIRISM";
        //     case "vitality": return "VITALITY";
        //     case "voiding": return "VOIDING";
        //     case "efficiency": return "EFFICIENCY";
        //     case "silk_touch": return "SILK_TOUCH";
        //     case "unbreaking": return "UNBREAKING";
        //     case "fortune": return "FORTUNE";
        //     case "power": return "POWER";
        //     case "punch": return "PUNCH";
        //     case "flame": return "FLAME";
        //     case "infinity": return "INFINITY";
        //     case "luck_of_the_sea": return "LUCK_OF_THE_SEA";
        //     case "lure": return "LURE";
        //     case "loyalty": return "LOYALTY";
        //     case "impaling": return "IMPALING";
        //     case "riptide": return "RIPTIDE";
        //     case "channeling": return "CHANNELING";
        //     case "multishot": return "MULTISHOT";
        //     case "quick_charge": return "QUICK_CHARGE";
        //     case "piercing": return "PIERCING";
        //     case "mending": return "MENDING";
        //     case "vanishing_curse": return "VANISHING_CURSE";
        //     default: return null;
        // }
    }

    // Deserialize the enchantment strings back into a map of actual enchantments
    private Map<Enchantment, Integer> deserializeEnchantmentMap(String serializedData) {
        Map<Enchantment, Integer> enchantmentMap = new HashMap<>();
        String[] pairs = serializedData.split(";");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            Enchantment enchantment = getEnchantmentByName(keyValue[0]);
            int level = Integer.parseInt(keyValue[1]);
            if (enchantment != null) {
                enchantmentMap.put(enchantment, level);
            }
        }

        return enchantmentMap;
    }
    
    /*
     * Method for getting an enchantment via its name as a string.
     * Sadly there is no automatic way to convert a string into an enchantment, must be done manually
     */
    private Enchantment getEnchantmentByName(String name) {
        switch (name) {
            case "PROTECTION": return Enchantment.PROTECTION;
            case "FIRE_PROTECTION": return Enchantment.FIRE_PROTECTION;
            case "FEATHER_FALLING": return Enchantment.FEATHER_FALLING;
            case "BLAST_PROTECTION": return Enchantment.BLAST_PROTECTION;
            case "PROJECTILE_PROTECTION": return Enchantment.PROJECTILE_PROTECTION;
            case "RESPIRATION": return Enchantment.RESPIRATION;
            case "AQUA_AFFINITY": return Enchantment.AQUA_AFFINITY;
            case "THORNS": return Enchantment.THORNS;
            case "DEPTH_STRIDER": return Enchantment.DEPTH_STRIDER;
            case "FROST_WALKER": return Enchantment.FROST_WALKER;
            case "BINDING_CURSE": return Enchantment.BINDING_CURSE;
            case "SHARPNESS": return Enchantment.SHARPNESS;
            case "SMITE": return Enchantment.SMITE;
            case "BANE_OF_ARTHROPODS": return Enchantment.BANE_OF_ARTHROPODS;
            case "KNOCKBACK": return Enchantment.KNOCKBACK;
            case "FIRE_ASPECT": return Enchantment.FIRE_ASPECT;
            case "LOOTING": return Enchantment.LOOTING;
            case "SWEEPING_EDGE": return Enchantment.SWEEPING_EDGE;
            case "ASPHYXIATION": return ShardEnchantment.asphyxiation;
            case "TURBULENCE": return ShardEnchantment.turbulence;
            case "VAMPIRISM": return ShardEnchantment.vampirism;
            case "VITALITY": return ShardEnchantment.vitality;
            case "VOIDING": return ShardEnchantment.voiding;
            case "EFFICIENCY": return Enchantment.EFFICIENCY;
            case "SILK_TOUCH": return Enchantment.SILK_TOUCH;
            case "UNBREAKING": return Enchantment.UNBREAKING;
            case "FORTUNE": return Enchantment.LOOTING;
            case "POWER": return Enchantment.POWER;
            case "PUNCH": return Enchantment.PUNCH;
            case "FLAME": return Enchantment.FLAME;
            case "INFINITY": return Enchantment.INFINITY;
            case "LUCK_OF_THE_SEA": return Enchantment.LUCK_OF_THE_SEA;
            case "LURE": return Enchantment.LURE;
            case "LOYALTY": return Enchantment.LOYALTY;
            case "IMPALING": return Enchantment.IMPALING;
            case "RIPTIDE": return Enchantment.RIPTIDE;
            case "CHANNELING": return Enchantment.CHANNELING;
            case "MULTISHOT": return Enchantment.MULTISHOT;
            case "QUICK_CHARGE": return Enchantment.QUICK_CHARGE;
            case "PIERCING": return Enchantment.PIERCING;
            case "MENDING": return Enchantment.MENDING;
            case "VANISHING_CURSE": return Enchantment.VANISHING_CURSE;
            default: return null;
        }
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

    // Method to check if at least one string in the list is present in the lore
    public boolean doesLoreContain(ItemStack itemStack, List<String> stringsToCheck) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return false;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null || !meta.hasLore()) {
            return false;
        }

        List<String> lore = meta.getLore();
        if (lore == null) {
            return false;
        }

        // Convert lore list to a single string for easier checking
        String loreText = String.join("\n", lore);

        // Check if any of the stringsToCheck is present in the lore text
        for (String stringToCheck : stringsToCheck) {
            if (loreText.contains(stringToCheck)) {
                return true;
            }
        }

        return false;
    }
}
