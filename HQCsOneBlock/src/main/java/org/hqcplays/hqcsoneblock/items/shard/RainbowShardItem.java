package org.hqcplays.hqcsoneblock.items.shard;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.Random;

public class RainbowShardItem extends AmethystShardItem {
    public RainbowShardItem() {
        super("Rainbow Shard", NamedTextColor.GOLD, "Right-click to activate a random shard effect (including black)!", false);
    }

    // Random effect from all the other shards
    @Override
    public boolean doEffect(Player player) {
        Random rand = new Random(System.currentTimeMillis());
        // Try random effects until one returns true
        while (!allEffects.get(rand.nextInt(allEffects.size())).doEffect(player));
        return true;
    }
}
