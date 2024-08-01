package org.hqcplays.hqcsoneblock.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.progression.Progression;

public class IslandCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            // Teleport the player to their island
            player.teleport(new Location(HQCsOneBlock.islandManager.getOrCreateIslandWorld(player), 0, 2, 0));
            player.sendMessage("Teleported to your island!");
            Progression.checkGoalUnlock(player, "create_island");
            return true;
        }
        sender.sendMessage("This command can only be used by players.");
        return false;
    }
}
