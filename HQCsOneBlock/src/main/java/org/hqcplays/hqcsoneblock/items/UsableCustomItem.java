package org.hqcplays.hqcsoneblock.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public abstract class UsableCustomItem extends CustomItem {
    private static final Map<String, UsableCustomItem> allUsableItems = new HashMap<>();
    private static final NamespacedKey useIdKey = new NamespacedKey("hqcs_one_block", "use_id");

    public static class PlayerUseListener implements Listener {
        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            ItemStack item = event.getItem();
            if (item != null && item.hasItemMeta()) {
                String itemId = item.getItemMeta().getPersistentDataContainer().get(useIdKey, PersistentDataType.STRING);
                UsableCustomItem usableItem = allUsableItems.get(itemId);
                if (usableItem != null) {
                    usableItem.onUse(event);
                }
            }
        }
    }

    public UsableCustomItem(Material material, Component name, Component desc) {
        super(material, name, desc);
        String useId = makeUseId(name);
        if (allUsableItems.containsKey(useId)) {
            System.err.println("Duplicate item ID: " + useId);
            return;
        }
        allUsableItems.put(useId, this);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(useIdKey, PersistentDataType.STRING, useId);
        item.setItemMeta(meta);
    }

    public String makeUseId(Component displayName) {
        return ((TextComponent)displayName).content().toLowerCase().replace(" ", "_");
    }

    public abstract void onUse(PlayerInteractEvent event);
}
