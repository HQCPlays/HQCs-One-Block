package org.hqcplays.hqcsoneblock.enchantments;

import java.util.Set;
import java.util.Collections;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.jetbrains.annotations.NotNull;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class VoidingEnchantment extends ShardEnchantment {
    private final NamespacedKey key;

    public VoidingEnchantment(HQCsOneBlock plugin) {
        super(plugin, "voiding");
        this.key = new NamespacedKey(plugin, "voiding");
    }

    @Override
    protected void applyHitEffect(EntityDamageByEntityEvent event, Player player) {
        // Apply extra damage
        double originalDamage = event.getDamage();
        event.setDamage(originalDamage + 4.0); // Example: add 2 hearts of damage

        // Damage the player
        player.damage(2); // Damage the player for 1 heart
    }

    @Override
    public String getLoreName() {
        return ChatColor.DARK_GRAY + "Voiding I";
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
        return "Voiding";
    }

    @Override
    public int getMaxLevel() {
        return 3; // Example maximum level
    }

    @Override
    public int getStartLevel() {
        return 1; // Example starting level
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEAPON; // This enchantment applies to weapons
    }

    @Override
    public boolean isTreasure() {
        return false; // Change if this enchantment is considered treasure
    }

    @Override
    public boolean isCursed() {
        return false; // Change if this enchantment is cursed
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        // Define conflicts with other enchantments, if any
        return false; // No conflicts with other enchantments by default
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        // Specifies which items can be enchanted with Voiding
        return item.getType().name().contains("SWORD") || item.getType().name().contains("AXE"); // Allow enchantment on weapons
    }

    @Override
    public @NotNull Component displayName(int level) {
        String name = "Voiding";
        Component nameComponent = Component.text(name, NamedTextColor.DARK_GRAY);

        if (level > 1) {
            nameComponent = nameComponent.append(Component.text(" " + level, NamedTextColor.GRAY));
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
        return 15 + (level * 10); // Define the minimum XP cost for the enchantment
    }

    @Override
    public int getMaxModifiedCost(int level) {
        return 25 + (level * 20); // Define the maximum XP cost for the enchantment
    }

    @Override
    public int getAnvilCost() {
        return 3; // Define the cost of combining this enchantment in an anvil
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.RARE; // Define the rarity of the enchantment
    }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) {
        // Define damage increase per level against an entity category if applicable
        return 0;
    }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityType entityType) {
        // Define damage increase per level against a specific entity type if applicable
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlotGroup> getActiveSlotGroups() {
        return Collections.singleton(EquipmentSlotGroup.HAND); // Active slot group for the enchantment
    }

    @Override
    public @NotNull String translationKey() {
        return this.getTranslationKey(); // Reuse the translation key implementation
    }
}
