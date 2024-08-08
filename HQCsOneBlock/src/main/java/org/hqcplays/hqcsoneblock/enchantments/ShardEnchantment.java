package org.hqcplays.hqcsoneblock.enchantments;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

public abstract class ShardEnchantment {
    public static ShardEnchantment voiding;
    public static ShardEnchantment vitality;
    public static ShardEnchantment vampirism;
    public static ShardEnchantment turbulence;
    public static ShardEnchantment asphyxiation;

    public static EnchantmentEventsListener listener = new EnchantmentEventsListener();

    private final NamespacedKey key;

    public ShardEnchantment(HQCsOneBlock plugin, String keyName) {
        this.key = new NamespacedKey(plugin, keyName);
        listener.enchantments.put(key, this);
    }

    public static void createEnchantments() {
        voiding = new VoidingEnchantment(HQCsOneBlock.getPlugin(HQCsOneBlock.class));
        vitality = new VitalityEnchantment(HQCsOneBlock.getPlugin(HQCsOneBlock.class));
        vampirism = new VampirismEnchantment(HQCsOneBlock.getPlugin(HQCsOneBlock.class));
        turbulence = new TurbulenceEnchantment(HQCsOneBlock.getPlugin(HQCsOneBlock.class));
        asphyxiation = new AsphyxiationEnchantment(HQCsOneBlock.getPlugin(HQCsOneBlock.class));
    }

    protected void applyHitEffect(EntityDamageByEntityEvent event, Player player) {
    }

    protected void applyArmor(Player player) {
    }

    public boolean hasEnchantment(ItemStack itemStack) {
        if (itemStack != null && itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            return meta.getPersistentDataContainer().has(key, PersistentDataType.BOOLEAN);
        }
        return false;
    }

    public abstract Component getLoreName();

    public NamespacedKey getItemMetaKey() {
        return key;
    }
}
