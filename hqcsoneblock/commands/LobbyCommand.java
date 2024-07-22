package org.hqcplays.hqcsoneblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class LobbyCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            World lobbyWorld = Bukkit.getWorld("world");

            if (lobbyWorld != null) {
                player.teleport(new Location(lobbyWorld, 0, 78, 0));
                player.sendMessage("Teleported to the lobby!");
            } else {
                player.sendMessage("Lobby world not found!");
            }

            return true;
        }

        sender.sendMessage("This command can only be used by players.");
        return false;
    }
}
