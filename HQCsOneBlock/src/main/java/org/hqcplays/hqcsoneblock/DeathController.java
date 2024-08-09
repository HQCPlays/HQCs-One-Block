package org.hqcplays.hqcsoneblock;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathController implements Listener {
    private final JavaPlugin plugin;

    public DeathController(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setCancelled(true); // Cancel the death event
        player.setHealth(player.getMaxHealth()); // Restore player's health

        // Delay the teleportation to ensure it happens after the event is processed
        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(new Location(Bukkit.getWorld("world"), 0, 78, 0));
            }
        }.runTaskLater(plugin, 1L); // Delay by 1 tick
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        // Delay the teleportation to ensure it happens after the player has respawned
        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(new Location(Bukkit.getWorld("world"), 0, 78, 0));
            }
        }.runTaskLater(plugin, 1L); // Delay by 1 tick
    }
}
