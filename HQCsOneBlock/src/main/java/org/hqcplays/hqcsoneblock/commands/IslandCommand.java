package org.hqcplays.hqcsoneblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.PlayerSaveData;
import org.hqcplays.hqcsoneblock.progression.Progression;

public class IslandCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                // Teleport the player to their island
                player.teleport(new Location(HQCsOneBlock.islandManager.getOrCreateIslandWorld(player), 0, 2, 0));
                player.sendMessage("Teleported to your island!");
                Progression.checkGoalUnlock(player, "create_island");
                return true;
            } else if (args.length == 2) {
                Player otherPlayer = Bukkit.getPlayerExact(args[1]);
                if (otherPlayer == null) {
                    player.sendMessage(ChatColor.RED + "Unknown player " + args[1]);
                    return false;
                }

                PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
                switch (args[0]) {
                    case "allow":
                        playerData.islandAllowedPlayers.add(otherPlayer.getUniqueId());
                        break;
                    case "disallow":
                        playerData.islandAllowedPlayers.remove(otherPlayer.getUniqueId());
                        // Move other player to lobby if they are on the owner's island
                        if (otherPlayer.getWorld().getName().equals(HQCsOneBlock.islandManager.getIslandWorldName(player))) {
                            otherPlayer.teleport(new Location(Bukkit.getWorld("world"), 0, 78, 0));
                            otherPlayer.sendMessage(ChatColor.RED + player.getName() + "has disallowed you from their island!");
                        }
                        break;
                    case "join":
                        PlayerSaveData otherPlayerData = HQCsOneBlock.dataManager.getPlayerData(otherPlayer);
                        if (otherPlayerData.islandAllowedPlayers.contains(player.getUniqueId())) {
                            player.teleport(new Location(HQCsOneBlock.islandManager.getOrCreateIslandWorld(otherPlayer), 0, 2, 0));
                        } else {
                            player.sendMessage(ChatColor.RED + "You do not have permission to join this player's island.");
                            return false;
                        }
                        break;
                    default:
                        player.sendMessage(ChatColor.RED + "Unknown subcommand");
                        return false;
                }
            }

            return true;
        }
        sender.sendMessage("This command can only be used by players.");
        return false;
    }
}
