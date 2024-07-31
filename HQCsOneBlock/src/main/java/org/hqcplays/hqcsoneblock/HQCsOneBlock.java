package org.hqcplays.hqcsoneblock;

import io.papermc.paper.advancement.AdvancementDisplay;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.hqcplays.hqcsoneblock.commands.*;
import org.hqcplays.hqcsoneblock.enchantments.ShardEnchantment;
import org.hqcplays.hqcsoneblock.fleaMarket.FleaListing;
import org.hqcplays.hqcsoneblock.fleaMarket.FleaListingUtils;
import org.hqcplays.hqcsoneblock.items.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class HQCsOneBlock extends JavaPlugin implements Listener {
    // Variables
    public static Map<UUID, PlayerSaveData> playerData = new HashMap<>();
    private final String scoreboardTitle = ChatColor.GOLD + "Block Coins";
    private File saveDataFile;

    private ArrayList<FleaListing> listings = new ArrayList<>();
    private static Plugin plugin;

    // Command classes
    private BCShopCommand bcShopCommand;
    private CheatMenuCommand cheatMenuCommand;
    private LobbyCommand lobbyCommand;
    private IslandCommand islandCommand;
    private FleaCommand fleaCommand;
    private ListCommand listCommand;
    private InboxCommand inboxCommand;
    private ProgressionCommand progressionCommand;

    // Functions
    @Override
    public void onEnable() {
        // Register core plugin events
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new OneBlockController(), this);
        getServer().getPluginManager().registerEvents(new PickaxeController(), this);
        getServer().getPluginManager().registerEvents(new AmethystShardItems(), this);
        getServer().getPluginManager().registerEvents(new RareOneBlockItems(), this);
        getServer().getPluginManager().registerEvents(new CustomPickaxes(), this);
        getServer().getPluginManager().registerEvents(new VanillaPlusItems(), this);
        getServer().getPluginManager().registerEvents(new MenuItemController(), this);
        getServer().getPluginManager().registerEvents(new ProgressionCommand(), this);
        getServer().getPluginManager().registerEvents(new TechItems(), this);

        // Register enchantments
        ShardEnchantment.createEnchantments();
        getServer().getPluginManager().registerEvents(ShardEnchantment.listener, this);

        OneBlockController.initializeBlockChances();

        // Register command executors
        if (this.getCommand("bcshop") != null) {
            bcShopCommand = new BCShopCommand();
            this.getCommand("bcshop").setExecutor(bcShopCommand);
            // Only register events if BCShopCommand implements Listener
            getServer().getPluginManager().registerEvents(bcShopCommand, this);
        } else {
            getLogger().severe("Command 'bcshop' is not defined!"); // Not defined in plugin.yml
        }

        if (this.getCommand("cheat") != null) {
            cheatMenuCommand = new CheatMenuCommand();
            this.getCommand("cheat").setExecutor(cheatMenuCommand);
            // Only register events if CheatMenuCommand implements Listener
            getServer().getPluginManager().registerEvents(cheatMenuCommand, this);
        } else {
            getLogger().severe("Command 'cheat' is not defined!"); // Not defined in plugin.yml
        }

        if (this.getCommand("lobby") != null) {
            lobbyCommand = new LobbyCommand();
            this.getCommand("lobby").setExecutor(lobbyCommand);
            // Only register events if LobbyCommand implements Listener
            getServer().getPluginManager().registerEvents(lobbyCommand, this);
        } else {
            getLogger().severe("Command 'lobby' is not defined!"); // Not defined in plugin.yml
        }

        if (this.getCommand("island") != null) {
            islandCommand = new IslandCommand();
            this.getCommand("island").setExecutor(islandCommand);
            // Only register events if IslandCommand implements Listener
            getServer().getPluginManager().registerEvents(islandCommand, this);
        } else {
            getLogger().severe("Command 'island' is not defined!"); // Not defined in plugin.yml
        }

        if (this.getCommand("flea") != null) {
            fleaCommand = new FleaCommand();
            this.getCommand("flea").setExecutor(fleaCommand);
            getServer().getPluginManager().registerEvents(fleaCommand, this);
        } else {
            getLogger().severe("Command 'flea' is not defined!"); // Not defined in plugin.yml
        }

        if (this.getCommand("list") != null) {
            listCommand = new ListCommand();
            this.getCommand("list").setExecutor(listCommand);
            getServer().getPluginManager().registerEvents(listCommand, this);
        } else {
            getLogger().severe("Command 'list' is not defined!"); // Not defined in plugin.yml
        }

        if (this.getCommand("inbox") != null) {
            inboxCommand = new InboxCommand();
            this.getCommand("inbox").setExecutor(inboxCommand);
            // Only register events if InboxCommand implements Listener
            getServer().getPluginManager().registerEvents(inboxCommand, this);
        } else {
            getLogger().severe("Command 'inbox' is not defined!"); // Not defined in plugin.yml
        }

        if (this.getCommand("progression") != null) {
            progressionCommand = new ProgressionCommand();
            this.getCommand("progression").setExecutor(progressionCommand);
            // Only register events if ProgressionCommand implements Listener
            getServer().getPluginManager().registerEvents(progressionCommand, this);
        } else {
            getLogger().severe("Command 'progression' is not defined!"); // Not defined in plugin.yml
        }

        // Initialize flea market
        // Schedule a repeating task to check for expired flea market items
        getServer().getScheduler().runTaskTimer(this, () -> FleaListingUtils.checkExpiredListings(), 0L, 20L * 60); // Check every minute

        // Initialize items or other components
        AmethystShardItems.init();
        RareOneBlockItems.init();
        CustomPickaxes.init();
        TechItems.init();
        VanillaPlusItems.init();

        plugin = this;

        getLogger().info("HQC's OneBlock Plugin has been enabled.");

        saveDataFile = new File("HQCsOneBlock.dat");
        loadSaveData();
    }

    @Override
    public void onDisable() {
        writeSaveData();
        getLogger().info("HQC's OneBlock Plugin has been disabled.");
    }

    public void loadSaveData() {
        try {
            FileInputStream fileInputStream = new FileInputStream(saveDataFile);
            GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
            BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(gzipInputStream);
            playerData = (Map<UUID, PlayerSaveData>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            // No save file has been created yet, just use the new empty player data
        } catch (IOException e) {
            getLogger().severe("Unable to load player data: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            getLogger().severe("Invalid or corrupt save data: " + e.getMessage());
        }
        getLogger().info("Sucessfully loaded existing save data");
    }

    public void writeSaveData() {
        getLogger().info("Saving player data...");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(saveDataFile);
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);
            BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(gzipOutputStream);
            objectOutputStream.writeObject(playerData);
            objectOutputStream.close();
        } catch (IOException e) {
            getLogger().warning("Unable to save player data: " + e.getMessage());
        }
    }

    @EventHandler
    public void onWorldSave(WorldSaveEvent event) {
        // Only save once per auto-save
        if (event.getWorld().getName().equals("world")) {
            writeSaveData();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = event.getPlayer().getUniqueId();

        player.setGameMode(GameMode.SURVIVAL);

        // Create new player data if none exists yet
        playerData.putIfAbsent(playerUUID, new PlayerSaveData());

        player.teleport(new Location(Bukkit.getWorld("world"), 0, 78, 0));

        setupScoreboard(player);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.teleport(new Location(Bukkit.getWorld("world"), 0, 78, 0));
    }

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        if (event.getReason() == PortalCreateEvent.CreateReason.FIRE) {
            // Cancel the event if the portal is being created by fire, which is typical for Nether portals
            event.setCancelled(true);
        }
    }

    // Disable durability globally
    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        event.setCancelled(true);
    }

    // Prevent players from breaking blocks in the lobby
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        World lobbyWorld = Bukkit.getWorld("world");
        if (lobbyWorld != null && event.getBlock().getWorld().equals(lobbyWorld)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You cannot break blocks here!");
        }
    }

    // Prevent players from placing blocks in the lobby
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        World lobbyWorld = Bukkit.getWorld("world");
        if (lobbyWorld != null && event.getBlock().getWorld().equals(lobbyWorld)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You cannot place blocks here!");
        }
    }

    // Temporary fix: prevents players from dropping items in the lobby
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        World lobbyWorld = Bukkit.getWorld("world");
        if (lobbyWorld != null && event.getItemDrop().getWorld().equals(lobbyWorld)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You cannot drop items here!");
        }
    }

    // Ensures the player is always in survival
    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        String playerName = event.getPlayer().getName();
        if (!playerName.equals("HQC_Plays")) {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            event.setCancelled(true);
            event.getPlayer().sendMessage("You are not allowed to change your game mode!");
        }
    }

    // Clicking the menu item
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().isSimilar(MenuItemController.MENU_ITEM)) {
            event.setCancelled(true);
            openMainMenu(event.getPlayer());
        }
    }

    // Prevent hunger
    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    public void openMainMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Main Menu");

        // Create menu options
        ItemStack progressionItem = new ItemStack(Material.BOOK);
        ItemMeta progressionMeta = progressionItem.getItemMeta();
        progressionMeta.setDisplayName(ChatColor.GOLD + "Progression");
        progressionItem.setItemMeta(progressionMeta);

        ItemStack fleaMarketItem = new ItemStack(Material.EMERALD);
        ItemMeta fleaMarketMeta = fleaMarketItem.getItemMeta();
        fleaMarketMeta.setDisplayName(ChatColor.GOLD + "Flea Market");
        fleaMarketItem.setItemMeta(fleaMarketMeta);

        ItemStack shopItem = new ItemStack(Material.GOLD_INGOT);
        ItemMeta shopMeta = shopItem.getItemMeta();
        shopMeta.setDisplayName(ChatColor.GOLD + "Block Coin Shop");
        shopItem.setItemMeta(shopMeta);

        // Place items in menu
        menu.setItem(11, progressionItem);
        menu.setItem(13, fleaMarketItem);
        menu.setItem(15, shopItem);

        player.openInventory(menu);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "Main Menu")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem.getType() == Material.BOOK) {
                player.performCommand("progression");
            } else if (clickedItem.getType() == Material.EMERALD) {
                player.performCommand("flea");
            } else if (clickedItem.getType() == Material.GOLD_INGOT) {
                player.performCommand("bcshop");
            }
        }
    }

    private void setupScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("blockCoins", "dummy", scoreboardTitle);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        updateScoreboard(player, scoreboard);
        player.setScoreboard(scoreboard);
    }

    public static void updateScoreboard(Player player) {
        updateScoreboard(player, player.getScoreboard());
    }

    public static void updateScoreboard(Player player, Scoreboard scoreboard) {
        double balance = playerData.get(player.getUniqueId()).balance;
        Objective objective = scoreboard.getObjective("blockCoins");
        Score score = objective.getScore(ChatColor.GREEN + "Coins: ");
        score.setScore((int) balance);
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
