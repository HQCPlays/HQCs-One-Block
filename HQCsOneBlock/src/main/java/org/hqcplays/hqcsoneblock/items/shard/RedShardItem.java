package org.hqcplays.hqcsoneblock.items.shard;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RedShardItem extends AmethystShardItem {
    public RedShardItem() {
        super("Red Shard", NamedTextColor.RED, "Right-click to obtain Regeneration 2 for 10 seconds!");
    }

    // Gives Regen
    @Override
    public boolean doEffect(Player player) {
        player.sendMessage(ChatColor.GREEN + "You have Regeneration 2 for 10 seconds!");
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
        return true;
    }
}
