package org.hqcplays.hqcsoneblock.items.shard;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WhiteShardItem extends AmethystShardItem {
    public WhiteShardItem() {
        super("White Shard", NamedTextColor.WHITE, "Right-click to enter the upper atmosphere!");
    }

    // Levitation 1 million
    @Override
    public boolean doEffect(Player player) {
        player.sendMessage(ChatColor.GREEN + "You are now entering the upper atmosphere!");
        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 100));
        return true;
    }
}
