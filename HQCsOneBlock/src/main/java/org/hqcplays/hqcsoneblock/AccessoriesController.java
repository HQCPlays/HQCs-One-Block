package org.hqcplays.hqcsoneblock;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

import static org.hqcplays.hqcsoneblock.items.AccessoryItems.redShardAmulet;

public class AccessoriesController implements Listener {
    private static final ItemStack bonusItem = new ItemStack(redShardAmulet);
    private static final UUID healthBonusUUID = UUID.randomUUID();

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        removeBonus(event.getPlayer());
    }

    @EventHandler
    public static void onPlayerLeave(PlayerQuitEvent event) {
        removeBonus(event.getPlayer());
    }

    private static void applyBonus(Player player) {
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attribute != null && attribute.getModifier(healthBonusUUID) == null) {
            attribute.addModifier(new AttributeModifier(healthBonusUUID, "bonus_health", 4.0, AttributeModifier.Operation.ADD_NUMBER));
        }
    }

    private static void removeBonus(Player player) {
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attribute != null && attribute.getModifier(healthBonusUUID) != null) {
            attribute.removeModifier(attribute.getModifier(healthBonusUUID));
        }
    }

    private static boolean hasBonusItem(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.isSimilar(bonusItem)) {
                return true;
            }
        }
        return false;
    }

    private static void applyOrRemoveBonus(Player player) {
        if (hasBonusItem(player)) {
            applyBonus(player);
        } else {
            removeBonus(player);
        }
    }

    public static class BonusChecker extends BukkitRunnable {
        @Override
        public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                applyOrRemoveBonus(player);
            }
        }
    }
}
