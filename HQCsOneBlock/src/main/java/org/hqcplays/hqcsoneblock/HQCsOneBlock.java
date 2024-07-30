package org.hqcplays.hqcsoneblock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.hqcplays.hqcsoneblock.commands.BCShopCommand;
import org.hqcplays.hqcsoneblock.commands.CheatMenuCommand;
import org.hqcplays.hqcsoneblock.commands.FleaCommand;
import org.hqcplays.hqcsoneblock.commands.ListCommand;
import org.hqcplays.hqcsoneblock.commands.IslandCommand;
import org.hqcplays.hqcsoneblock.commands.LobbyCommand;
import org.hqcplays.hqcsoneblock.enchantments.ShardEnchantment;
import org.hqcplays.hqcsoneblock.items.AmethystShardItems;
import org.hqcplays.hqcsoneblock.items.CustomPickaxes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class HQCsOneBlock extends JavaPlugin implements Listener {
    // Variables
    public static Map<UUID, PlayerSaveData> playerData = new HashMap<>();
    private final String scoreboardTitle = ChatColor.GOLD + "Block Coins";
    private File saveDataFile;
    private final List<String> authorizedUsers = Arrays.asList("HQC_Plays", "Entitylght"); // Replace with actual usernames
    private ArrayList<FleaListing> listings = new ArrayList<>();
    private static Plugin plugin;

    // Command classes
    private BCShopCommand bcShopCommand;
    private CheatMenuCommand cheatMenuCommand;
    private LobbyCommand lobbyCommand;
    private IslandCommand islandCommand;
    private FleaCommand fleaCommand;
    private ListCommand listCommand;

    // Functions
    @Override
    public void onEnable() {
        // Register core plugin events
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new OneBlockController(), this);
        getServer().getPluginManager().registerEvents(new PickaxeController(), this);
        getServer().getPluginManager().registerEvents(new AmethystShardItems(), this);
        getServer().getPluginManager().registerEvents(new CustomPickaxes(), this);

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
            // Only register events if fleaCommand implements Listener
            getServer().getPluginManager().registerEvents(fleaCommand, this);
        } else {
            getLogger().severe("Command 'flea' is not defined!"); // Not defined in plugin.yml
        }

        if (this.getCommand("list") != null) {
            listCommand = new ListCommand();
            this.getCommand("list").setExecutor(listCommand);
            // Only register events if listCommand implements Listener
            getServer().getPluginManager().registerEvents(listCommand, this);
        } else {
            getLogger().severe("Command 'list' is not defined!"); // Not defined in plugin.yml
        }

        // Initialize items or other components
        AmethystShardItems.init();


        // Initialize Flea Market

        // Schedule a repeating task to check for expired flea market items
        getServer().getScheduler().runTaskTimer(this, () -> FleaListingUtils.checkExpiredListings(), 0L, 20L * 60); // Check every minute

        ItemStack testItem = new ItemStack(Material.GRASS_BLOCK);
        testItem.setAmount(64);

        ItemStack testItem1 = new ItemStack(Material.DIAMOND);
        testItem1.setAmount(64);

        // FleaMarket.clearFleaMarket(); // temporary empty flea market on init
        // FleaMarket.addListing(new FleaListing(testItem, 100, UUID.randomUUID()));
        // FleaMarket.addListing(new FleaListing(testItem1, 10000000, UUID.randomUUID()));
        // FleaMarket.addListing(new FleaListing(AmethystShardItems.blackShard, 200, UUID.randomUUID()));
        // FleaMarket.addListing(new FleaListing(AmethystShardItems.redShard, 300, UUID.randomUUID()));

        // for (int i = 5; i < 37; i++) {
        //     FleaMarket.addListing(new FleaListing(testItem1, 10000000, UUID.randomUUID()));
        // }
        // for (int i = 37; i < 73; i++) {
        //     FleaMarket.addListing(new FleaListing(testItem, 10000000, UUID.randomUUID()));
        // }
        // for (int i = 73; i < 80; i++) {
        //     FleaMarket.addListing(new FleaListing(AmethystShardItems.blackShard, 200, UUID.randomUUID()));
        // }
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

        setupScoreboard(player);
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
        if (!authorizedUsers.contains(playerName)) {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            event.setCancelled(true);
            event.getPlayer().sendMessage("You are not allowed to change your game mode!");
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
        int balance = playerData.get(player.getUniqueId()).balance;
        Objective objective = scoreboard.getObjective("blockCoins");
        Score score = objective.getScore(ChatColor.GREEN + "Coins: ");
        score.setScore(balance);
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
