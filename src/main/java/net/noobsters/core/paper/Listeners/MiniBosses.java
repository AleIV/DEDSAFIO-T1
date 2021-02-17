package net.noobsters.core.paper.Listeners;

import java.util.Random;
import java.util.UUID;

import com.destroystokyo.paper.event.entity.EnderDragonFireballHitEvent;
import com.destroystokyo.paper.event.entity.EnderDragonFlameEvent;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vex;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class MiniBosses implements Listener {

    PERMADED instance;
    Random random = new Random();

    MiniBosses(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void bossSpawns(CreatureSpawnEvent e) {
        var game = instance.getGame();
        if (e.getEntity() instanceof EnderDragon && game.getDifficultyChange() >= 3) {
            var dragon = (EnderDragon) e.getEntity();
            dragon.setCustomName("Blood Ender Dragon");
        }

    }

    @EventHandler
    public void onFireBall(EnderDragonFireballHitEvent e) {
        var cloud = e.getAreaEffectCloud();

        if (instance.getGame().getDifficultyChange() < 3)
            return;

        var spirit = (Vex) cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        var spirit2 = (Vex) cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        var spirit3 = (Vex) cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        spirit.setCustomName("Spirit");
        spirit2.setCustomName("Spirit");
        spirit3.setCustomName("Spirit");

        if (e.getTargets() != null) {
            e.getTargets().forEach(target -> {
                if (target instanceof Player) {
                    target.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 0));
                }
            });
        }
    }

    @EventHandler
    public void onFireBall2(EnderDragonFlameEvent e) {
        var cloud = e.getAreaEffectCloud();

        if (instance.getGame().getDifficultyChange() < 3)
            return;

        var spirit = (Vex) cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        var spirit2 = (Vex) cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        var spirit3 = (Vex) cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        spirit.setCustomName("Spirit");
        spirit2.setCustomName("Spirit");
        spirit3.setCustomName("Spirit");
    }

    @EventHandler
    public void bossDeath(EntityDeathEvent e) {
        var entity = e.getEntity();
        if (entity instanceof EnderDragon && instance.getGame().getDifficultyChange() >= 3) {
            var bloodAmor = new ItemStack(Material.AIR);
            switch (random.nextInt(4)) {
                case 1: {
                    bloodAmor = new ItemStack(Material.NETHERITE_HELMET);
                    var bloodMeta = bloodAmor.getItemMeta();
                    bloodMeta.setDisplayName(ChatColor.RED + "Blood Helmet");
                    bloodMeta.setCustomModelData(123);
                    final AttributeModifier knockback = new AttributeModifier(UUID.randomUUID(),
                            "GENERIC.KNOCKBACK_RESISTANCE", 0.1, AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlot.HEAD);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockback);
                    final AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), "GENERIC.ARMOR", 3.0,
                            AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);
                    final AttributeModifier toughness = new AttributeModifier(UUID.randomUUID(),
                            "GENERIC.ARMOR_TOUGHNESS", 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughness);
                    final AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "GENERIC.MAX_HEALTH", 5.0,
                            AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);
                    bloodAmor.setItemMeta(bloodMeta);

                }
                    break;
                case 2: {
                    bloodAmor = new ItemStack(Material.NETHERITE_CHESTPLATE);
                    var bloodMeta = bloodAmor.getItemMeta();
                    bloodMeta.setDisplayName(ChatColor.RED + "Blood Chestplate");
                    bloodMeta.setCustomModelData(123);
                    final AttributeModifier knockback = new AttributeModifier(UUID.randomUUID(),
                            "GENERIC.KNOCKBACK_RESISTANCE", 0.1, AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlot.CHEST);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockback);
                    final AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), "GENERIC.ARMOR", 8.0,
                            AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);
                    final AttributeModifier toughness = new AttributeModifier(UUID.randomUUID(),
                            "GENERIC.ARMOR_TOUGHNESS", 2.0, AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlot.CHEST);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughness);
                    final AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "GENERIC.MAX_HEALTH", 5.0,
                            AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);
                    bloodAmor.setItemMeta(bloodMeta);
                }
                    break;
                case 3: {
                    bloodAmor = new ItemStack(Material.NETHERITE_LEGGINGS);
                    var bloodMeta = bloodAmor.getItemMeta();
                    bloodMeta.setDisplayName(ChatColor.RED + "Blood Leggings");
                    bloodMeta.setCustomModelData(123);
                    final AttributeModifier knockback = new AttributeModifier(UUID.randomUUID(),
                            "GENERIC.KNOCKBACK_RESISTANCE", 0.1, AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlot.LEGS);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockback);
                    final AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), "GENERIC.ARMOR", 6.0,
                            AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);
                    final AttributeModifier toughness = new AttributeModifier(UUID.randomUUID(),
                            "GENERIC.ARMOR_TOUGHNESS", 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughness);
                    final AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "GENERIC.MAX_HEALTH", 5.0,
                            AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);
                    bloodAmor.setItemMeta(bloodMeta);
                }
                    break;

                default: {
                    bloodAmor = new ItemStack(Material.NETHERITE_BOOTS);
                    var bloodMeta = bloodAmor.getItemMeta();
                    bloodMeta.setDisplayName(ChatColor.RED + "Blood Boots");
                    bloodMeta.setCustomModelData(123);
                    final AttributeModifier knockback = new AttributeModifier(UUID.randomUUID(),
                            "GENERIC.KNOCKBACK_RESISTANCE", 0.1, AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlot.FEET);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockback);
                    final AttributeModifier armor = new AttributeModifier(UUID.randomUUID(), "GENERIC.ARMOR", 3.0,
                            AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);
                    final AttributeModifier toughness = new AttributeModifier(UUID.randomUUID(),
                            "GENERIC.ARMOR_TOUGHNESS", 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughness);
                    final AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "GENERIC.MAX_HEALTH", 5.0,
                            AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
                    bloodMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);
                    bloodAmor.setItemMeta(bloodMeta);
                }
                    break;
            }
            e.getDrops().add(bloodAmor);
        }

    }

    @EventHandler
    public void spiritDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Vex && e.getCause() == DamageCause.MAGIC) {
            e.setCancelled(true);
        }
    }

    public Integer chooseCoord(int radius) {
        var num = random.nextInt(radius);
        num = random.nextBoolean() ? ~(num) : num;
        return num;
    }

}