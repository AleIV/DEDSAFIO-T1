package net.noobsters.core.paper.Listeners;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftIronGolem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
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

import me.libraryaddict.disguise.DisguiseAPI;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityIronGolem;
import net.minecraft.server.v1_16_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_16_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalSelector;
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
        var difficulty = game.getDifficultyChanges();
        var entity = e.getEntity();
        if (entity instanceof Husk && difficulty.get("zombies")) {
            var chef = (Husk) entity;

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

        }else if(entity instanceof Zombie && difficulty.get("zombies")){

            if(entity instanceof Drowned || entity.getType() == EntityType.ZOMBIFIED_PIGLIN || entity instanceof ZombieVillager)
                return;
            
            var zombie = (Zombie) entity;
            var zombiePlayers = game.getDeathPlayers().entrySet().stream().filter(entry -> entry.getValue()).collect(Collectors.toList());
            
            if(!zombiePlayers.isEmpty()){
                var choose = zombiePlayers.get(random.nextInt(zombiePlayers.size()));
                zombie.setCustomName(ChatColor.RED + "Zombie " + choose.getKey());
            }

        }else if(entity instanceof IronGolem && difficulty.get("zombies")){
            var golem = (IronGolem) entity;

            if(golem.getLocation().getY() < 55){
                var loc = golem.getLocation();
                golem.setCustomName(ChatColor.DARK_AQUA + "Warden");
                
                loc.getNearbyPlayers(20).stream().forEach(p ->{
                    p.playSound(p.getLocation(), "warden_idle", 1, 1);
                });
                
            }else{
                golem.setCustomName(ChatColor.RED + "Mutant Zombie");
            }

            golem.setSilent(true);
            golem.setRemoveWhenFarAway(true);

            CraftIronGolem craft = ((CraftIronGolem) golem);
            EntityIronGolem nms = craft.getHandle();

            try {
                Class<? extends EntityInsentient> cl = EntityInsentient.class; //nms.getClass()
                Field gf = cl.getDeclaredField("goalSelector");
                gf.setAccessible(true);

                PathfinderGoalSelector goal = (PathfinderGoalSelector) gf.get(nms);
                goal.a(0, new PathfinderGoalMeleeAttack(nms, 1.0D, true));

                Field tf = cl.getDeclaredField("targetSelector");
                tf.setAccessible(true);

                PathfinderGoalSelector target = (PathfinderGoalSelector) tf.get(nms);
                target.a(0, new PathfinderGoalNearestAttackableTarget<>(nms, EntityHuman.class, 10, true, false, null));
                
            } catch (Exception x) {
                x.printStackTrace();
            }

        } else if (entity instanceof Skeleton && difficulty.get("skeletons")) {
            var skeleton = (Skeleton) entity;

            if(skeleton.getType() == EntityType.WITHER_SKELETON){

                skeleton.setCustomName(ChatColor.RED + "The Death");

                var weapon = new ItemStack(Material.NETHERITE_SWORD);
                var meta = weapon.getItemMeta();
                meta.setCustomModelData(90);
                meta.setDisplayName(ChatColor.BLUE + "Rhaast");
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
                    meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
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
                    meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
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
        if (e.getEntity() instanceof WitherSkull) {
            var entity = e.getHitEntity();
            if (entity != null && entity instanceof Player && !DisguiseAPI.isDisguised(entity)) {
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
            
            loc.getNearbyPlayers(20).stream().forEach(player ->{
                player.playSound(player.getLocation(), "sans_talking", 1, 1);
            });

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
        var difficulty = instance.getGame().getDifficultyChanges();

        if (damager instanceof Husk && difficulty.get("zombies")) {

            var loc = damager.getLocation();
            loc.getNearbyPlayers(20).stream().forEach(player ->{
                player.playSound(player.getLocation(), "burp", 1, 1);
            });

        } else if (entity instanceof Skeleton && entity.getCustomName() != null
                && entity.getCustomName().contains("Sans") && (damager instanceof Player || damager instanceof Arrow)) {

            var loc = damager.getLocation();
            loc.getNearbyPlayers(20).stream().forEach(player ->{
                player.playSound(player.getLocation(), "sans_song", 1, 1);
            });

        }

    }

}