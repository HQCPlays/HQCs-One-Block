package org.hqcplays.hqcsoneblock.items.shard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.hqcplays.hqcsoneblock.items.UsableCustomItem;

import java.util.ArrayList;
import java.util.List;

public abstract class AmethystShardItem extends UsableCustomItem {
    protected static final List<AmethystShardItem> allEffects = new ArrayList<>();

    // Shard were Arinlol's idea
    public AmethystShardItem(String name, TextColor color, String desc, boolean addToEffectsList) {
        super(Material.AMETHYST_SHARD, Component.text(name, color), Component.text(desc, NamedTextColor.YELLOW));
        if (addToEffectsList) {
            allEffects.add(this);
        }
    }

    public AmethystShardItem(String name, TextColor color, String desc) {
        this(name, color, desc, true);
    }

    @Override
    public void onUse(PlayerInteractEvent event) {
        if (event.getAction().isRightClick() && doEffect(event.getPlayer())) {
            event.getItem().setAmount(item.getAmount() - 1);
        }
    }

    protected abstract boolean doEffect(Player player);
}
