package net.noobsters.core.paper.Listeners;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class Items implements Listener {

    PERMADED instance;
    Random random = new Random();

    Items(PERMADED instance) {
        this.instance = instance;
        SmithingRecipe helmet = new SmithingRecipe(new NamespacedKey(instance, "customHelmet"),
                new ItemStack(Material.AIR), new RecipeChoice.MaterialChoice(Material.NETHERITE_HELMET),
                new RecipeChoice.MaterialChoice(Material.RABBIT_FOOT));
        SmithingRecipe chestplate = new SmithingRecipe(new NamespacedKey(instance, "customChestplate"),
                new ItemStack(Material.AIR), new RecipeChoice.MaterialChoice(Material.NETHERITE_CHESTPLATE),
                new RecipeChoice.MaterialChoice(Material.RABBIT_FOOT));
        SmithingRecipe leggings = new SmithingRecipe(new NamespacedKey(instance, "customLeggings"),
                new ItemStack(Material.AIR), new RecipeChoice.MaterialChoice(Material.NETHERITE_LEGGINGS),
                new RecipeChoice.MaterialChoice(Material.RABBIT_FOOT));
        SmithingRecipe boots = new SmithingRecipe(new NamespacedKey(instance, "customBoots"),
                new ItemStack(Material.AIR), new RecipeChoice.MaterialChoice(Material.NETHERITE_BOOTS),
                new RecipeChoice.MaterialChoice(Material.RABBIT_FOOT));

        Bukkit.addRecipe(helmet);
        Bukkit.addRecipe(chestplate);
        Bukkit.addRecipe(leggings);
        Bukkit.addRecipe(boots);

    }

    /*
    @EventHandler
    public void redstoneAmulet(BlockBreakEvent e){
        var difficulty = instance.getGame().getDifficultyChanges();
        var block = e.getBlock();
        if(difficulty.get("redstone") && block.getType() == Material.REDSTONE_BLOCK){
            e.setDropItems(false);
            var item = new ItemBuilder(Material.RABBIT_FOOT).name(ChatColor.DARK_RED + "Redstone Amulet").meta(ItemMeta.class, meta -> meta.setCustomModelData(142)).build();
            block.getWorld().dropItemNaturally(block.getLocation(), item);
        }
    }*/
    
    @EventHandler
    public void onCustomArmor(PrepareSmithingEvent e) {
        var inv = e.getInventory();
        var armor = inv.getItem(0);
        var upgrade = inv.getItem(1);
        if (armor == null || upgrade == null)
            return;

        if (!armor.getItemMeta().hasCustomModelData() && upgrade.getItemMeta().hasCustomModelData() && upgrade.getItemMeta().getCustomModelData() == 143) {
            // blood armor
            if (armor.getType() == Material.NETHERITE_HELMET) {
                e.setResult(addBloodHelmetMeta(armor));
            } else if (armor.getType() == Material.NETHERITE_CHESTPLATE) {
                e.setResult(addBloodChestPlateMeta(armor));
            } else if (armor.getType() == Material.NETHERITE_LEGGINGS) {
                e.setResult(addBloodLeggingsMeta(armor));
            } else if (armor.getType() == Material.NETHERITE_BOOTS) {
                e.setResult(addBloodBootsMeta(armor));
            }

        } else if (armor.getItemMeta().hasCustomModelData() && armor.getItemMeta().getCustomModelData() == 123
                && upgrade.getItemMeta().hasCustomModelData() && upgrade.getItemMeta().getCustomModelData() == 142) {
            // reinforced redstone armor

            var redstone = armor.clone();
            var meta = redstone.getItemMeta();
            meta.setCustomModelData(126);

            if (armor.getType() == Material.NETHERITE_HELMET) {
                meta.setDisplayName(ChatColor.DARK_RED + "Reinforced Redstone Helmet");
            } else if (armor.getType() == Material.NETHERITE_CHESTPLATE) {
                meta.setDisplayName(ChatColor.DARK_RED + "Reinforced Redstone Chestplate");
            } else if (armor.getType() == Material.NETHERITE_LEGGINGS) {
                meta.setDisplayName(ChatColor.DARK_RED + "Reinforced Redstone Leggings");
            } else if (armor.getType() == Material.NETHERITE_BOOTS) {
                meta.setDisplayName(ChatColor.DARK_RED + "Reinforced Redstone Boots");
            }

            redstone.setItemMeta(meta);

            e.setResult(redstone);

        }

    }

    public ItemStack addBloodHelmetMeta(ItemStack armor) {
        var bloodArmor = armor.clone();
        var bloodMeta = bloodArmor.getItemMeta();
        bloodMeta.setDisplayName(ChatColor.RED + "Blood Helmet");
        bloodMeta.setCustomModelData(123);
        final AttributeModifier knockback = new AttributeModifier(UUID.randomUUID(), "GENERIC.KNOCKBACK_RESISTANCE",
                0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockback);
        final AttributeModifier armorRes = new AttributeModifier(UUID.randomUUID(), "GENERIC.ARMOR", 3.0,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorRes);
        final AttributeModifier toughness = new AttributeModifier(UUID.randomUUID(), "GENERIC.ARMOR_TOUGHNESS", 3.0,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughness);
        final AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "GENERIC.MAX_HEALTH", 2.0,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);
        bloodArmor.setItemMeta(bloodMeta);
        return bloodArmor;
    }

    public ItemStack addBloodChestPlateMeta(ItemStack armor) {
        var bloodArmor = armor.clone();
        var bloodMeta = bloodArmor.getItemMeta();
        bloodMeta.setDisplayName(ChatColor.RED + "Blood Chestplate");
        bloodMeta.setCustomModelData(123);
        final AttributeModifier knockback = new AttributeModifier(UUID.randomUUID(), "GENERIC.KNOCKBACK_RESISTANCE",
                0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockback);
        final AttributeModifier armorRes = new AttributeModifier(UUID.randomUUID(), "GENERIC.ARMOR", 8.0,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorRes);
        final AttributeModifier toughness = new AttributeModifier(UUID.randomUUID(), "GENERIC.ARMOR_TOUGHNESS", 3.0,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughness);
        final AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "GENERIC.MAX_HEALTH", 4.0,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);
        bloodArmor.setItemMeta(bloodMeta);
        return bloodArmor;
    }

    public ItemStack addBloodLeggingsMeta(ItemStack armor) {
        var bloodArmor = armor.clone();
        var bloodMeta = bloodArmor.getItemMeta();
        bloodMeta.setDisplayName(ChatColor.RED + "Blood Leggings");
        bloodMeta.setCustomModelData(123);
        final AttributeModifier knockback = new AttributeModifier(UUID.randomUUID(), "GENERIC.KNOCKBACK_RESISTANCE",
                0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockback);
        final AttributeModifier armorRes = new AttributeModifier(UUID.randomUUID(), "GENERIC.ARMOR", 6.0,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorRes);
        final AttributeModifier toughness = new AttributeModifier(UUID.randomUUID(), "GENERIC.ARMOR_TOUGHNESS", 3.0,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughness);
        final AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "GENERIC.MAX_HEALTH", 4.0,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);
        bloodArmor.setItemMeta(bloodMeta);
        return bloodArmor;
    }

    public ItemStack addBloodBootsMeta(ItemStack armor) {
        var bloodArmor = armor.clone();
        var bloodMeta = bloodArmor.getItemMeta();
        bloodMeta.setDisplayName(ChatColor.RED + "Blood Boots");
        bloodMeta.setCustomModelData(123);
        final AttributeModifier knockback = new AttributeModifier(UUID.randomUUID(), "GENERIC.KNOCKBACK_RESISTANCE",
                0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockback);
        final AttributeModifier armorRes = new AttributeModifier(UUID.randomUUID(), "GENERIC.ARMOR", 3.0,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorRes);
        final AttributeModifier toughness = new AttributeModifier(UUID.randomUUID(), "GENERIC.ARMOR_TOUGHNESS", 3.0,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughness);
        final AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "GENERIC.MAX_HEALTH", 2.0,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        bloodMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);
        bloodArmor.setItemMeta(bloodMeta);
        return bloodArmor;
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemConsume(PlayerItemConsumeEvent e) {
        var item = e.getItem().getType();
        if (item == Material.AIR || !e.getItem().hasItemMeta())
            return;
        ItemMeta itemMeta = e.getItem().getItemMeta();

        if (itemMeta.hasCustomModelData() && itemMeta.getCustomModelData() == 136) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 2, 100));
        }

        if (item == Material.MILK_BUCKET && itemMeta.hasCustomModelData() && itemMeta.getCustomModelData() == 1) {
            Bukkit.getScheduler().runTaskLater(instance, () -> {
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*15, 2));
            }, 20);
        }

    }

    @EventHandler
    public void impact(ProjectileHitEvent e) {
        var projectile = e.getEntity();
        var hitblock = e.getHitBlock();
        var entity = e.getHitEntity();

        if (projectile.getCustomName() != null
                && projectile.getCustomName().toString().contains("explosive")) {

            if (hitblock != null) {
                hitblock.getLocation().createExplosion(3, true, false);
            } else if (entity != null) {
                entity.getLocation().createExplosion(3, true, false);
            }

        }
    }

    @EventHandler
    public void onFireballSpawn(EntitySpawnEvent e){
        var entity = e.getEntity();
        var difficulty =  instance.getGame().getDifficultyChanges();
        if(difficulty.get("demons") && entity instanceof Fireball){
            var fireball = (Fireball) entity;
            fireball.setYield(10);
        }
    }

    @EventHandler
    public void blazeBalls(EntityDamageByEntityEvent e){
        var entity =  e.getEntity();
        var damager = e.getDamager();
        var difficulty =  instance.getGame().getDifficultyChanges();
        if(difficulty.get("demons") && entity instanceof Player && damager.getType() == EntityType.SMALL_FIREBALL){
            var player = (Player) entity;
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 1, 70));
            var loc = e.getEntity().getLocation();

            loc.createExplosion(7);

        }
    }

    @EventHandler
    public void blazeDamage(EntitySpawnEvent e){
        var entity = e.getEntity();
        var difficulty =  instance.getGame().getDifficultyChanges();
        if(difficulty.get("demons") && entity instanceof Fireball){
            var fireball = (Fireball) entity;
            fireball.setYield(10);
        }
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        var bow = e.getBow().getItemMeta();
        if (!bow.hasCustomModelData())
            return;

        if ((bow.getCustomModelData() == 105 || bow.getCustomModelData() == 112)) {
            e.getProjectile().setCustomName("explosive");

        } else if (bow.getCustomModelData() == 113) {
            var skull = e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation().add(0, 1, 0),
                    EntityType.WITHER_SKULL);
            e.setProjectile(skull);

        } else if (bow.getCustomModelData() == 116) {
            //magic staff
            e.getProjectile().setCustomName("blue orb");
            var loc = e.getEntity().getLocation();

            loc.getNearbyPlayers(20).stream().forEach(p ->{
                p.playSound(p.getLocation(), "magic_1", 0.3f, 1);
            });

        }else if (bow.getCustomModelData() == 108) {
            //rifle
            e.getProjectile().setCustomName("lead bullet");
            var loc = e.getEntity().getLocation();

            loc.getNearbyPlayers(20).stream().forEach(p ->{
                p.playSound(p.getLocation(), "rifle_shoot", 0.3f, 1);
            });

                    
        }else if (bow.getCustomModelData() == 107 || bow.getCustomModelData() == 109) {
            //pistol
            e.getProjectile().setCustomName("golden bullet");
            var loc = e.getEntity().getLocation();

            loc.getNearbyPlayers(20).stream().forEach(p ->{
                p.playSound(p.getLocation(), "gun_1", 0.3f, 1);
            });

        }
        

    }

}