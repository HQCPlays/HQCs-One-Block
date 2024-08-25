package org.hqcplays.hqcsoneblock.enchantments;

import java.util.Set;
import java.util.Collections;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.jetbrains.annotations.NotNull;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class AsphyxiationEnchantment extends ShardEnchantment {

    private final NamespacedKey key;

    public AsphyxiationEnchantment(HQCsOneBlock plugin) {
        super(plugin, "asphyxiation");
        this.key = new NamespacedKey(plugin, "asphyxiation");
    }

    @Override
    protected void applyHitEffect(EntityDamageByEntityEvent event, Player player) {
        if (event.getEntity() instanceof LivingEntity) {
            ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 5));
        }
    }

    @Override
    public String getLoreName() {
        return ChatColor.DARK_BLUE + "Asphyxiation I";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return this.key;
    }

    @Override
    public @NotNull String getTranslationKey() {
        return this.key.toString();
    }

    @Override
    public @NotNull String getName() {
        return "Asphyxiation";
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEAPON;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        // Define conflicts with other enchantments, if any
        return false; // No conflicts with other enchantments by default
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        // Specify which items can be enchanted
        return item.getType().name().contains("SWORD");
    }

    @Override
    public @NotNull Component displayName(int level) {
        String name = "Asphyxiation";
        Component nameComponent = Component.text(name, NamedTextColor.RED);

        if (level > 1) {
            nameComponent = nameComponent.append(Component.text(" " + level, NamedTextColor.GOLD));
        }

        return nameComponent;
    }

    @Override
    public boolean isTradeable() {
        return true; // Define whether the enchantment can be obtained through trading
    }

    @Override
    public boolean isDiscoverable() {
        return true; // Define whether the enchantment can be discovered in-game
    }

    @Override
    public int getMinModifiedCost(int level) {
        return 10 + (level * 5); // Define the minimum XP cost for the enchantment
    }

    @Override
    public int getMaxModifiedCost(int level) {
        return 20 + (level * 10); // Define the maximum XP cost for the enchantment
    }

    @Override
    public int getAnvilCost() {
        return 1; // Define the cost of combining this enchantment in an anvil
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.RARE; // Define the rarity of the enchantment
    }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) {
        return level * 1.5f; // Define the damage increase per level against an entity category
    }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityType entityType) {
        return level * 1.5f; // Define the damage increase per level against a specific entity type
    }

    @Override
    public @NotNull Set<EquipmentSlotGroup> getActiveSlotGroups() {
        return Collections.singleton(EquipmentSlotGroup.MAINHAND); // Active slot group for the enchantment
    }

    @Override
    public @NotNull String translationKey() {
        return this.getTranslationKey(); // Reuse the translation key implementation
    }
}
