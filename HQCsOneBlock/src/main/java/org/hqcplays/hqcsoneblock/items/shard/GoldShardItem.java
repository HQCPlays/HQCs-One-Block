package org.hqcplays.hqcsoneblock.items.shard;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.PlayerSaveData;

public class GoldShardItem extends AmethystShardItem {
    public GoldShardItem() {
        super("Gold Shard", NamedTextColor.GOLD, "Right-click to obtain +10 Block Coins!");
    }

    // Gives BC
    @Override
    public boolean doEffect(Player player) {
        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);

        // Trigger custom event
        player.sendMessage(ChatColor.GREEN + "You have gained +10 Block Coin from the Gold Shard!");

        playerData.balance += 10;
        HQCsOneBlock.updateScoreboard(player);

        return true;
    }
}
