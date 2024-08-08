package org.hqcplays.hqcsoneblock.items.shard;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PurpleShardItem extends AmethystShardItem {
    public PurpleShardItem() {
        super("Purple Shard", NamedTextColor.DARK_PURPLE, "Right-click to obtain Haste 1 for 30 seconds!");
    }

    // Gives Haste
    @Override
    public boolean doEffect(Player player) {
        player.sendMessage(ChatColor.GREEN + "You now have Haste 1 for 30 seconds!");
        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 600, 0));
        return true;
    }
}
