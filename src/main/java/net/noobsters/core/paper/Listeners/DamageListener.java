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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.noobsters.core.paper.PERMADED;

public class DamageListener implements Listener {

    PERMADED instance;
    Random random = new Random();
    
    DamageListener(PERMADED instance) {
        this.instance = instance;
    }
    
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        var entity = e.getEntity();
        var cause = e.getCause();
        var damage = instance.getGame().getDamageAmplifier();
        var difficulty = instance.getGame().getDifficultyChanges();
        var loc = entity.getLocation();
        
        if(entity instanceof Horse && entity.getCustomName() != null){

            loc.getNearbyPlayers(10).stream().forEach(p ->{
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1);
            });
            
        }

        if(entity instanceof IronGolem && entity.getCustomName() != null && entity.getCustomName().toString().contains("Mutant")){
            if(random.nextBoolean()){
                loc.getNearbyPlayers(10).stream().forEach(p ->{
                    p.playSound(p.getLocation(), "zombie_damage1", 0.5f, 1);
                });

            }else{
                loc.getNearbyPlayers(10).stream().forEach(p ->{
                    p.playSound(p.getLocation(), "zombie_damage2", 0.5f, 1);
                });
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

            if(!(damager instanceof Player) && damager.getCustomName() != null){
                var damage = instance.getGame().getDamageAmplifier();
                e.setDamage(e.getDamage()*damage);
            }

            if(damager instanceof IronGolem && entity.getCustomName().toString().contains("Mutant")){

                var loc = entity.getLocation();

                loc.getNearbyPlayers(20).stream().forEach(p ->{
                    p.playSound(p.getLocation(), "zombie_attack", 1, 1);
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

    
    @EventHandler
    public void mobsDamageModifier(EntityDamageByEntityEvent e){
        var damager = e.getDamager();

        if(damager instanceof Player) return;

        if(damager instanceof Projectile){
            var proj = (Projectile) damager;
            var shooter = proj.getShooter();

            if(proj.getCustomName() != null && proj.getCustomName().contains("lead")){
                e.setDamage(e.getDamage()+12);
                if(random.nextBoolean() && shooter instanceof Pillager){
                    var pillager = (Pillager) shooter;
                    var loc = pillager.getLocation();

                    loc.getNearbyPlayers(20).stream().forEach(p ->{
                        p.playSound(p.getLocation(), "boomheadshot", 1, 1);
                    });

                }
            }else if(proj.getCustomName() != null && proj.getCustomName().contains("golden")){
                e.setDamage(e.getDamage()+6);
            }

        }

    }
}
