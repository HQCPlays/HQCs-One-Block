package org.hqcplays.hqcsoneblock;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.hqcplays.hqcsoneblock.commands.*;
import org.hqcplays.hqcsoneblock.customBlockBreaking.MiningListener;
import org.hqcplays.hqcsoneblock.customBlockBreaking.MiningManager;
import org.hqcplays.hqcsoneblock.customBlockBreaking.MiningSpeedManager;
import org.hqcplays.hqcsoneblock.enchantments.AsphyxiationEnchantment;
import org.hqcplays.hqcsoneblock.enchantments.ShardEnchantment;
import org.hqcplays.hqcsoneblock.fleaMarket.FleaListing;
import org.hqcplays.hqcsoneblock.fleaMarket.FleaListingUtils;
import org.hqcplays.hqcsoneblock.items.*;
import org.hqcplays.hqcsoneblock.progression.Progression;
import org.bukkit.enchantments.Enchantment;

import java.util.*;

public final class HQCsOneBlock extends JavaPlugin implements Listener {
    // Variables
    public static SaveDataManager dataManager;
    public static IslandManager islandManager;
    private final String scoreboardTitle = ChatColor.GOLD + "Block Coins";

    private ArrayList<FleaListing> listings = new ArrayList<>();
    private static Plugin plugin;
    private final List<String> authorizedUsers = Arrays.asList("HQC_Plays", "OfficialNagi"); // Replace with actual usernames

    // Command classes
    private BCShopCommand bcShopCommand;
    private CheatMenuCommand cheatMenuCommand;
    private LobbyCommand lobbyCommand;
    private IslandCommand islandCommand;
    private ProgressionCommand progressionCommand;
    private ProfilesCommand profilesCommand;
    private WarpsCommand warpsCommand;
    private FleaCommand fleaCommand;
    private ListCommand listCommand;
    private InboxCommand inboxCommand;
    private RecipesCommand recipesCommand;
    private EnchantCommand enchantCommand;
    private AnvilCommand anvilCommand;

    // Functions
    @Override
    public void onEnable() {
        // Register core plugin events
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new OneBlockController(), this);
        getServer().getPluginManager().registerEvents(new CustomPickaxes(), this);
        getServer().getPluginManager().registerEvents(new CustomAxes(), this);
        getServer().getPluginManager().registerEvents(new CustomShovels(), this);
        getServer().getPluginManager().registerEvents(new PickaxeController(), this);
        getServer().getPluginManager().registerEvents(new AxeController(), this);
        getServer().getPluginManager().registerEvents(new ShovelController(), this);
        getServer().getPluginManager().registerEvents(new AmethystShardItems(), this);
        getServer().getPluginManager().registerEvents(new RareOneBlockItems(), this);
        getServer().getPluginManager().registerEvents(new VanillaPlusItems(), this);
        getServer().getPluginManager().registerEvents(new MenuItemController(), this);
        getServer().getPluginManager().registerEvents(new Progression(), this);
        getServer().getPluginManager().registerEvents(new ProgressionCommand(), this);
        getServer().getPluginManager().registerEvents(new TechItems(), this);
        getServer().getPluginManager().registerEvents(new MobController(), this);
        getServer().getPluginManager().registerEvents(new MobSpawnerController(this), this);
        getServer().getPluginManager().registerEvents(new ResourceGeneratorController(), this);
        getServer().getPluginManager().registerEvents(new MiningSpeedManager(), this);
        getServer().getPluginManager().registerEvents(new DeathController(this), this);

        // Custom Block Breaking registers
        getServer().getPluginManager().registerEvents(new MiningManager(), this);
        getServer().getPluginManager().registerEvents(new MiningListener(new MiningManager()), this);

        // Register enchantments
        ShardEnchantment.createEnchantments();
        getServer().getPluginManager().registerEvents(ShardEnchantment.listener, this);

        OneBlockController.initializeBlockChances();

        dataManager = new SaveDataManager(getLogger());
        islandManager = new IslandManager();

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

        if (this.getCommand("profiles") != null) {
            profilesCommand = new ProfilesCommand();
            this.getCommand("profiles").setExecutor(profilesCommand);
            // Only register events if ProfilesnCommand implements Listener
            getServer().getPluginManager().registerEvents(profilesCommand, this);
        } else {
            getLogger().severe("Command 'profiles' is not defined!"); // Not defined in plugin.yml
        }

        if (this.getCommand("warps") != null) {
            warpsCommand = new WarpsCommand();
            this.getCommand("warps").setExecutor(warpsCommand);
            // Only register events if Warps Command implements Listener
            getServer().getPluginManager().registerEvents(warpsCommand, this);
        } else {
            getLogger().severe("Command 'warps' is not defined!"); // Not defined in plugin.yml
        }
        if (this.getCommand("recipes") != null) {
            recipesCommand = new RecipesCommand();
            this.getCommand("recipes").setExecutor(recipesCommand);
            // Only register events if Warps Command implements Listener
            getServer().getPluginManager().registerEvents(recipesCommand, this);
        } else {
            getLogger().severe("Command 'recipes' is not defined!"); // Not defined in plugin.yml
        }
        if (this.getCommand("enchant") != null) {
            enchantCommand = new EnchantCommand();
            this.getCommand("enchant").setExecutor(enchantCommand);
            // Only register events if Enchant Command implements Listener
            getServer().getPluginManager().registerEvents(enchantCommand, this);
        } else {
            getLogger().severe("Command 'enchant' is not defined!"); // Not defined in plugin.yml
        }
        if (this.getCommand("anvil") != null) {
            anvilCommand = new AnvilCommand();
            this.getCommand("anvil").setExecutor(anvilCommand);
            // Only register events if Anvil Command implements Listener
            getServer().getPluginManager().registerEvents(anvilCommand, this);
        } else {
            getLogger().severe("Command 'enchant' is not defined!"); // Not defined in plugin.yml
        }

        // Initialize flea market
        // Schedule a repeating task to check for expired flea market items
        getServer().getScheduler().runTaskTimer(this, () -> FleaListingUtils.checkExpiredListings(), 0L, 20L * 60); // Check every minute

        // Mob name tag updater
        new BukkitRunnable() {
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    for (Entity entity : world.getEntities()) {
                        if (entity instanceof LivingEntity) {
                            MobController.updateHealthNametag((LivingEntity) entity);
                        }
                    }
                }
            }
        }.runTaskTimer(HQCsOneBlock.getPlugin(HQCsOneBlock.class), 0L, 20L); // Update every second (20 ticks)

        // Initialize items or other components
        AmethystShardItems.init();
        RareOneBlockItems.init();
        TechItems.init();
        VanillaPlusItems.init();
        CustomPickaxes.init();
        CustomAxes.init();
        CustomShovels.init();
        Progression.init();
        ResourceGeneratorItems.init();
        MobSpawnerController.init();

        dataManager.loadSaveData();

        plugin = this;

        // Initialize Flea Market

        // Schedule a repeating task to check for expired flea market items
        getServer().getScheduler().runTaskTimer(this, () -> FleaListingUtils.checkExpiredListings(), 0L, 20L * 60); // Check every minute


        getLogger().info("HQC's OneBlock Plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        dataManager.writeSaveData();
        getLogger().info("HQC's OneBlock Plugin has been disabled.");
    }

    @EventHandler
    public void onWorldSave(WorldSaveEvent event) {
        // Only save once per auto-save
        if (event.getWorld().getName().equals("world")) {
            dataManager.writeSaveData();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = event.getPlayer().getUniqueId();

        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(new Location(Bukkit.getWorld("world"), 0, 78, 0));

        dataManager.setupPlayer(player);

        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
        playerData.miningSpeed = 1;

        // Set the player's mining speed to 0 (this is the 1.21 replacement for -1 Mining Fatigue) for the custom block breaking to work
        Objects.requireNonNull(player.getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED)).setBaseValue(0);

        setupScoreboard(player);
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

        if (event.getItemInHand().getItemMeta().getDisplayName().contains("Tool Core") || event.getItemInHand().getItemMeta().getDisplayName().contains("Automation Core")) {
            event.setCancelled(true);
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
        if (!authorizedUsers.contains(playerName)) {
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
        Inventory menu = Bukkit.createInventory(null, 45, ChatColor.DARK_GREEN + "Main Menu");

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

        ItemStack profilesItem = new ItemStack(Material.WHITE_WOOL);
        ItemMeta profilesMeta = profilesItem.getItemMeta();
        profilesMeta.setDisplayName(ChatColor.GOLD + "Profiles");
        profilesItem.setItemMeta(profilesMeta);

        ItemStack warpsItem = new ItemStack(Material.END_CRYSTAL);
        ItemMeta warpsMeta = warpsItem.getItemMeta();
        warpsMeta.setDisplayName(ChatColor.GOLD + "Warps");
        warpsItem.setItemMeta(warpsMeta);

        // Place items in menu
        menu.setItem(20, progressionItem);
        menu.setItem(22, fleaMarketItem);
        menu.setItem(24, shopItem);
        menu.setItem(40, profilesItem);
        menu.setItem(44, warpsItem);

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
            } else if (clickedItem.getType() == Material.WHITE_WOOL) {
                player.performCommand("profiles");
            } else if (clickedItem.getType() == Material.END_CRYSTAL) {
                player.performCommand("warps");
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
        double balance = dataManager.getPlayerData(player).balance;
        Objective objective = scoreboard.getObjective("blockCoins");
        Score score = objective.getScore(ChatColor.GREEN + "Coins: ");
        score.setScore((int) balance);
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
