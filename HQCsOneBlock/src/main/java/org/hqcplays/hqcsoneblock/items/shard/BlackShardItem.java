package org.hqcplays.hqcsoneblock.items.shard;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlackShardItem extends AmethystShardItem {
    public BlackShardItem() {
        super("Black Shard", NamedTextColor.BLACK, "I wonder what this one does...");
    }

    // Just straight up kills the player, because screw you
    @Override
    public boolean doEffect(Player player) {
        player.sendMessage(ChatColor.GREEN + "You have died!");
        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 2));
        return true;
    }
}
