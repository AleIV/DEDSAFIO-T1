package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.noobsters.core.paper.PERMADED;

public class DamageListener implements Listener {

    PERMADED instance;
    Random random = new Random();

    DamageListener(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void latraes(EntityDamageByEntityEvent e){
        var entity = e.getEntity();
        var damager = e.getDamager();
        var game = instance.getGame();

        if(game.isGulak() && entity instanceof Player && damager instanceof Player){
            var player1 = (Player) entity;
            var player2 = (Player) damager;
            var equip1 = player1.getEquipment();
            var equip2 = player2.getEquipment();

            var helmet2 = equip2.getHelmet();
    
            if (helmet2 != null && helmet2.getItemMeta().getDisplayName() != null && helmet2.getItemMeta().getDisplayName().contains("TNT")) {
                player2.removePotionEffect(PotionEffectType.GLOWING);
                equip2.setHelmet(null);
                equip1.setHelmet(game.getTnt());
                player1.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20*1000, 0));

            }

            if(game.getDifficultyChanges().get("tnt")){
                e.setDamage(0);

            }

            if(game.getDifficultyChanges().get("sumo")){
                e.setDamage(0);
                var loc = player1.getLocation();
                loc.getNearbyPlayers(40).stream().forEach(p ->{
                    p.playSound(p.getLocation(), "roblox_hurt", 0.5f, 1);
                });

            }

        }
    }
    
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        var entity = e.getEntity();
        var cause = e.getCause();
        var damage = instance.getGame().getDamageAmplifier();
        var difficulty = instance.getGame().getDifficultyChanges();
        var loc = entity.getLocation();

        if(difficulty.get("tnt") && cause == DamageCause.FALL){
            e.setCancelled(true);
        }
        
        if(entity instanceof Horse && entity.getCustomName() != null){

            loc.getNearbyPlayers(10).stream().forEach(p ->{
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.2f, 0.3f);
            });
            
        }

        if(entity instanceof IronGolem && entity.getCustomName() != null){

            if(entity.getCustomName().toString().contains("Mutant")){
                if(random.nextBoolean()){
                    loc.getNearbyPlayers(10).stream().forEach(p ->{
                        p.playSound(p.getLocation(), "zombie_damage1", 0.5f, 1);
                    });
    
                }else{
                    loc.getNearbyPlayers(10).stream().forEach(p ->{
                        p.playSound(p.getLocation(), "zombie_damage2", 0.5f, 1);
                    });
                }
            }else if(entity.getCustomName().toString().contains("Warden")){

                if(random.nextInt(10) == 1){
                    loc.getNearbyPlayers(10).stream().forEach(p ->{
                        p.playSound(p.getLocation(), "warden_hurt", 1, 1);
                    });
                }
        
            }
        }

        if(entity instanceof Player){
            var player = (Player) entity;
            if(player.getGameMode() == GameMode.SPECTATOR && cause == DamageCause.VOID){
                e.setCancelled(true);
            }
        }


        if(difficulty.get("lava") && cause == DamageCause.LAVA || cause == DamageCause.DRAGON_BREATH){
            e.setDamage(e.getDamage()*damage);
        }

        if(difficulty.get("environment") && (cause == DamageCause.FIRE || cause == DamageCause.FIRE_TICK 
            || cause == DamageCause.DROWNING || cause == DamageCause.HOT_FLOOR 
                || cause == DamageCause.STARVATION || cause == DamageCause.FALL || cause == DamageCause.CONTACT 
                    || cause == DamageCause.FLY_INTO_WALL || cause == DamageCause.SUFFOCATION)){
            e.setDamage(e.getDamage()*damage);
        }

        if(difficulty.get("meteor") && (cause == DamageCause.WITHER || cause == DamageCause.MAGIC)){
            e.setDamage(e.getDamage()*damage);
        }

        if(entity instanceof Horse && entity.getCustomName() != null && cause == DamageCause.FALL){
            e.setCancelled(true);
        }

        if(entity instanceof Player && entity.getVehicle() != null){
            var vehicle = entity.getVehicle();
            if(vehicle instanceof Horse && vehicle.getCustomName() != null && cause == DamageCause.FALL){
                e.setCancelled(true);
            }
        }


    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPVP(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof Player) {
            Player p1 = (Player) e.getEntity();
            Player p2 = null;
            if (e.getDamager() instanceof Player) {
                p2 = (Player) e.getDamager();
            } else if (e.getDamager() instanceof Projectile) {
                Projectile proj = (Projectile) e.getDamager();
                if (proj.getShooter() instanceof Player) {
                    p2 = (Player) proj.getShooter();
                }
            }
            if (p2 != null) {
                var pvp = instance.getGame().getPvpOn();

                if (pvp.contains(p1.getUniqueId().toString()) && pvp.contains(p2.getUniqueId().toString()))
                    return;

                if (p1.getUniqueId().toString() == p2.getUniqueId().toString())
                    return;

                if (!p1.hasPermission("mod.perm") && p2.getGameMode() == GameMode.SURVIVAL
                        && !p2.hasPermission("mod.perm")) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void mobsResistanceModifier(EntityDamageByEntityEvent e){
        var entity = e.getEntity();
        var damager = e.getDamager();

        if(entity instanceof Player){

            if(damager instanceof Projectile){
                var proj = (Projectile) damager;
                damager = (Entity) proj.getShooter();
            }

            if(!(damager instanceof Player)){
                var damage = instance.getGame().getDamageAmplifier();
                e.setDamage(e.getDamage()*damage);
            }

            if(entity.getCustomName() != null){

                if(damager instanceof IronGolem && entity instanceof Player){
                    var loc = entity.getLocation();

                    if(entity.getCustomName().toString().contains("Mutant")){

                        loc.getNearbyPlayers(20).stream().forEach(p ->{
                            p.playSound(p.getLocation(), "zombie_attack", 1, 1);
                        });
                    }else if(entity.getCustomName().toString().contains("Warden")){

                        loc.getNearbyPlayers(20).stream().forEach(p ->{
                            p.playSound(p.getLocation(), "warden_roar", 1, 1);
                        });
                    }
    
                }
            }

        }

    }

    @EventHandler
    public void ondeath(EntityDeathEvent e){
        var entity = e.getEntity();
        if(entity instanceof IronGolem && entity.getCustomName() != null){
            var golem = (IronGolem) entity;

            var loc = golem.getLocation();

                if(entity.getCustomName().toString().contains("Warden")){

                    loc.getNearbyPlayers(20).stream().forEach(p ->{
                        p.playSound(p.getLocation(), "warden_death", 1, 1);
                    });
                }
        }
    }

    @EventHandler
    public void resistance(EntityDamageEvent e){
        var entity = e.getEntity();

        if(!(entity instanceof Player)){
            var mobResistance = instance.getGame().getMobResistance();
            e.setDamage(e.getDamage()-((e.getDamage()/100)*mobResistance));
        }
    }

    
    @EventHandler(priority = EventPriority.LOW)
    public void mobsDamageModifier(EntityDamageByEntityEvent e){
        var damager = e.getDamager();

        if(damager instanceof Player) return;

        if(damager instanceof Projectile){
            var proj = (Projectile) damager;
            var shooter = proj.getShooter();

            if(proj.getCustomName() != null && proj.getCustomName().contains("lead")){
                e.setDamage(e.getDamage()+22);
                if(random.nextBoolean() && shooter instanceof Pillager){
                    var pillager = (Pillager) shooter;
                    var loc = pillager.getLocation();

                    loc.getNearbyPlayers(20).stream().forEach(p ->{
                        p.playSound(p.getLocation(), "boomheadshot", 0.3f, 1);
                    });

                }
            }else if(proj.getCustomName() != null && proj.getCustomName().contains("golden")){
                e.setDamage(e.getDamage()+14);
            }

        }

    }
}
