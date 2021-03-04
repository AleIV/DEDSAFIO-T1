package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.entity.EntityDismountEvent;

import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class UnDead implements Listener {

    PERMADED instance;
    Random random = new Random();

    UnDead(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void unDeadSpawns(CreatureSpawnEvent e) {
        var game = instance.getGame();
        if (e.getEntity() instanceof Husk && game.getDifficultyChange() >= 1) {
            var chef = (Husk) e.getEntity();

            chef.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 600, 0));

            var chefHat = new ItemStack(Material.CARVED_PUMPKIN);
            var chefHatMeta = chefHat.getItemMeta();
            chefHatMeta.setCustomModelData(71);
            chefHatMeta.setDisplayName(ChatColor.YELLOW + "Chef Hat");
            chefHatMeta.setUnbreakable(true);
            chefHat.setItemMeta(chefHatMeta);

            var chefOutfit = new ItemStack(Material.CHAINMAIL_LEGGINGS);
            var chefOutfitMeta = chefOutfit.getItemMeta();
            chefOutfitMeta.setCustomModelData(130);
            chefOutfitMeta.setDisplayName(ChatColor.YELLOW + "Chef Outfit");
            chefOutfitMeta.setUnbreakable(true);
            chefOutfit.setItemMeta(chefOutfitMeta);

            var taco = new ItemStack(Material.CHORUS_FRUIT);
            var tacoMeta = taco.getItemMeta();
            tacoMeta.setCustomModelData(136);
            tacoMeta.setDisplayName(ChatColor.DARK_GREEN + "Taco");
            taco.setItemMeta(tacoMeta);

            chef.setCustomName(ChatColor.RED + "Death Chef");
            var chefEquipment = chef.getEquipment();
            chefEquipment.setHelmet(chefHat);
            chefEquipment.setLeggings(chefOutfit);
            chefEquipment.setItemInMainHand(taco);
            chefEquipment.setItemInMainHandDropChance(0.1f);

        } else if (e.getEntity() instanceof Skeleton && game.getDifficultyChange() >= 4) {
            var skeleton = (Skeleton) e.getEntity();

            if(skeleton.getType() == EntityType.WITHER_SKELETON){

                skeleton.setCustomName(ChatColor.RED + "The Death");

                var weapon = new ItemStack(Material.NETHERITE_SWORD);
                var meta = weapon.getItemMeta();
                meta.setCustomModelData(90);
                meta.setDisplayName(ChatColor.BLUE + "Guadaña");
                weapon.setItemMeta(meta);
                var skeletonEquipment = skeleton.getEquipment();
                skeletonEquipment.setItemInMainHand(weapon);
                return;
            }else if(skeleton.getType() == EntityType.STRAY){
                e.setCancelled(true);
                skeleton.getWorld().spawnEntity(skeleton.getLocation(), EntityType.SKELETON);
            }

            switch (random.nextInt(4)) {
                case 1: {
                    skeleton.setCustomName(ChatColor.AQUA + "Sans");
                    var bow = new ItemStack(Material.BOW);
                    var meta = bow.getItemMeta();
                    meta.setCustomModelData(113);
                    meta.setDisplayName(ChatColor.BLUE + "Soul Bow");
                    bow.setItemMeta(meta);
                    var skeletonEquipment = skeleton.getEquipment();
                    skeletonEquipment.setItemInMainHand(bow);
                }
                    break;
                case 2: {
                    skeleton.setCustomName(ChatColor.RED + "Zozo");
                    var bow = new ItemStack(Material.BOW);
                    var meta = bow.getItemMeta();
                    meta.addEnchant(Enchantment.ARROW_DAMAGE, 3, true);
                    bow.setItemMeta(meta);
                    var skeletonEquipment = skeleton.getEquipment();
                    skeletonEquipment.setItemInMainHand(bow);
                }
                    break;
                case 3: {
                    skeleton.setCustomName(ChatColor.RED + "Archer");
                    var bow = new ItemStack(Material.BOW);
                    var meta = bow.getItemMeta();
                    meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
                    bow.setItemMeta(meta);
                    var skeletonEquipment = skeleton.getEquipment();
                    skeletonEquipment.setItemInMainHand(bow);
                }
                    break;

                default: {
                    skeleton.setCustomName(ChatColor.RED + "Captain Parrot");

                    var bow = new ItemStack(Material.BOW);
                    var meta = bow.getItemMeta();
                    meta.addEnchant(Enchantment.ARROW_DAMAGE, 3, true);
                    bow.setItemMeta(meta);

                    var pirateHat = new ItemStack(Material.CARVED_PUMPKIN);
                    var hatMeta = pirateHat.getItemMeta();
                    hatMeta.setCustomModelData(81);
                    hatMeta.setDisplayName(ChatColor.YELLOW + "Pirate Hat");
                    hatMeta.setUnbreakable(true);
                    pirateHat.setItemMeta(hatMeta);

                    var pirateChest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                    var chestMeta = pirateChest.getItemMeta();
                    chestMeta.setCustomModelData(131);
                    chestMeta.setDisplayName(ChatColor.YELLOW + "End Rob");
                    chestMeta.setUnbreakable(true);
                    pirateChest.setItemMeta(chestMeta);

                    var pirateLegs = new ItemStack(Material.DIAMOND_LEGGINGS);
                    var legsMeta = pirateLegs.getItemMeta();
                    legsMeta.setCustomModelData(128);
                    legsMeta.setDisplayName(ChatColor.DARK_GREEN + "Rusty Leggings");
                    pirateLegs.setItemMeta(legsMeta);

                    var pirateBoots = new ItemStack(Material.DIAMOND_BOOTS);
                    var bootsMeta = pirateBoots.getItemMeta();
                    bootsMeta.setCustomModelData(128);
                    bootsMeta.setDisplayName(ChatColor.DARK_GREEN + "Rusty Boots");
                    pirateBoots.setItemMeta(bootsMeta);

                    var skeletonEquipment = skeleton.getEquipment();

                    skeletonEquipment.setItemInOffHand(new ItemStack(Material.IRON_SWORD));
                    skeletonEquipment.setItemInMainHand(bow);
                    skeletonEquipment.setHelmet(pirateHat);
                    skeletonEquipment.setChestplate(pirateChest);
                    skeletonEquipment.setLeggings(pirateLegs);
                    skeletonEquipment.setBoots(pirateBoots);

                    if(random.nextBoolean() && random.nextBoolean()){
                        var parrot = skeleton.getWorld().spawnEntity(skeleton.getLocation(), EntityType.PARROT);
                        skeleton.addPassenger(parrot);
                    }
                }
                    break;
            }
        }

    }

    @EventHandler
    public void killPassengers1(EntityDismountEvent e){
        if(e.getEntity() instanceof Parrot){
            var parrot = (Parrot) e.getEntity();
            Bukkit.getScheduler().runTaskLater(instance, ()->{
                parrot.getLocation().createExplosion(1);
            }, 20*2);
        }
    }

    @EventHandler
    public void impact(ProjectileHitEvent e) {
        var difficulty = instance.getGame().getDifficultyChange();
        if (difficulty >= 4 && e.getEntity() instanceof WitherSkull) {
            var entity = e.getHitEntity();
            if (entity != null && entity instanceof Player) {
                var player = (Player) entity;
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 0));
            }
        }
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        var entity = e.getEntity();
        if (entity instanceof Skeleton && entity.getCustomName() != null
                && entity.getCustomName().contains("Sans")) {
            var loc = entity.getLocation();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:sans_talking ambient @a "
                    + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " 1 1");
        }
    }

    @EventHandler
    public void skeletonsDamage(EntityDamageEvent e) {
        var entity = e.getEntity();
        if (entity instanceof Skeleton && entity.getCustomName() != null
        && entity.getCustomName().contains("Sans") && (e.getCause() == DamageCause.WITHER || e.getCause() == DamageCause.ENTITY_EXPLOSION)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void sounds1(EntityDamageByEntityEvent e) {
        var damager = e.getDamager();
        var entity = e.getEntity();
        var game = instance.getGame();

        if (damager instanceof Husk) {
            var loc = damager.getLocation();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    "playsound minecraft:burp ambient @a " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " 1 1");

        } else if (game.getDifficultyChange() >= 4 && entity instanceof Skeleton && entity.getCustomName() != null
                && entity.getCustomName().contains("Sans") && (damager instanceof Player || damager instanceof Arrow)) {

            var loc = damager.getLocation();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:sans_song ambient @a " + loc.getX()
                    + " " + loc.getY() + " " + loc.getZ() + " 1 1");
        }

    }

}