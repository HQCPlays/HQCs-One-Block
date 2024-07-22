package org.hqcplays.hqcsoneblock;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.hqcplays.hqcsoneblock.commands.BCShopCommand;
import org.hqcplays.hqcsoneblock.commands.CheatMenuCommand;
import org.hqcplays.hqcsoneblock.commands.IslandCommand;
import org.hqcplays.hqcsoneblock.commands.LobbyCommand;
import org.hqcplays.hqcsoneblock.enchantments.TurbulenceEnchantment;
import org.hqcplays.hqcsoneblock.enchantments.VampirismEnchantment;
import org.hqcplays.hqcsoneblock.enchantments.VitalityEnchantment;
import org.hqcplays.hqcsoneblock.enchantments.VoidingEnchantment;
import org.hqcplays.hqcsoneblock.items.AmethystShardItems;

import java.util.*;

public final class HQCsOneBlock extends JavaPlugin implements Listener {
    // Variables
    public static final HashMap<UUID, Integer> playerBalances = new HashMap<>();
    private final String scoreboardTitle = ChatColor.GOLD + "Block Coins";

    // Command classes
    private BCShopCommand bcShopCommand;
    private CheatMenuCommand cheatMenuCommand;
    private LobbyCommand lobbyCommand;
    private IslandCommand islandCommand;

    // Functions
    @Override
    public void onEnable() {
        // Register core plugin events
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new OneBlockController(), this);
        getServer().getPluginManager().registerEvents(new PickaxeController(), this);
        getServer().getPluginManager().registerEvents(new AmethystShardItems(), this);

        // Register enchantments
        getServer().getPluginManager().registerEvents(new VoidingEnchantment(this), this);
        getServer().getPluginManager().registerEvents(new VitalityEnchantment(this), this);
        getServer().getPluginManager().registerEvents(new VampirismEnchantment(this), this);
        getServer().getPluginManager().registerEvents(new TurbulenceEnchantment(this), this);

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

        // Initialize items or other components
        AmethystShardItems.init();

        getLogger().info("HQC's OneBlock Plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("HQC's OneBlock Plugin has been disabled.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = event.getPlayer().getUniqueId();

        player.setGameMode(GameMode.SURVIVAL);

        playerBalances.putIfAbsent(playerUUID, 0);

        setupScoreboard(player);

        OneBlockController.initializeBlockChances(playerUUID);
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
        UUID playerUUID = player.getUniqueId();
        int balance = playerBalances.getOrDefault(playerUUID, 0);
        Objective objective = scoreboard.getObjective("blockCoins");
        Score score = objective.getScore(ChatColor.GREEN + "Coins: ");
        score.setScore(balance);
    }
}
