package org.hqcplays.hqcsoneblock.items.shard;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class GreenShardItem extends AmethystShardItem {
    public GreenShardItem() {
        super("Green Shard", NamedTextColor.GREEN, "Right-click to instantly grow all plants around you!");
    }

    // Makes all plants within 24 blocks of the player grow
    @Override
    public boolean doEffect(Player player) {
        if (!player.getWorld().getName().contains("island_")) {
            return false;
        }

        Location c = player.getLocation();
        int r = 24;
        boolean didUse = false;

        for (int x = c.getBlockX() - r; x <= c.getBlockX() + r; x++) {
            for (int y = c.getBlockY() - r; y <= c.getBlockY() + r; y++) {
                for (int z = c.getBlockZ() - r; z <= c.getBlockZ() + r; z++) {
                    Block block = new Location(c.getWorld(), x, y, z).getBlock();
                    // Keep bonemealing 30 times or until the block can't be bonemealed anymore.
                    for (int i = 0; i < 30; i++) {
                        if (block.applyBoneMeal(BlockFace.NORTH))
                            didUse = true;
                        else
                            break;
                    }
                }
            }
        }

        if (didUse) {
            player.sendMessage(ChatColor.GREEN + "Nearby plants are now fully grown!");
            return true;
        } else {
            //player.sendMessage(ChatColor.DARK_GREEN + "Use this shard near some growable plants");
            return false;
        }
    }
}
