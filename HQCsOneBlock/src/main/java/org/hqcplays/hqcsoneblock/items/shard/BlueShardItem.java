package org.hqcplays.hqcsoneblock.items.shard;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BlueShardItem extends AmethystShardItem {
    public BlueShardItem() {
        super("Blue Shard", NamedTextColor.BLUE, "Right-click to spawn a 2x2 infinite water source directly below you!");
    }

    // Spawns an infinite water source
    @Override
    public boolean doEffect(Player player) {
        if (!player.getWorld().getName().contains("island_")) {
            return false;
        }

        player.sendMessage(ChatColor.GREEN + "You now have an infinite water source!");

        Location blueLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1, player.getLocation().getZ());
        blueLoc.getBlock().setType(Material.WATER);
        blueLoc = new Location(player.getWorld(), player.getLocation().getX() + 1, player.getLocation().getY() - 1, player.getLocation().getZ());
        blueLoc.getBlock().setType(Material.WATER);
        blueLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1, player.getLocation().getZ() + 1);
        blueLoc.getBlock().setType(Material.WATER);
        blueLoc = new Location(player.getWorld(), player.getLocation().getX() + 1, player.getLocation().getY() - 1, player.getLocation().getZ() + 1);
        blueLoc.getBlock().setType(Material.WATER);

        return true;
    }
}
