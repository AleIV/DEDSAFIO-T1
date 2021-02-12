package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vex;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.noobsters.core.paper.PERMADED;

public class GlobalListeners implements Listener{

    PERMADED instance;
    Random random = new Random();

    GlobalListeners(PERMADED instance){
        this.instance = instance;
    }

    @EventHandler
    public void passengerDeath(EntityDeathEvent e){
        var entity = e.getEntity();
        if(entity instanceof Vex){
            var vex = (Vex) entity;
            vex.getPassengers().forEach(passengers ->{
                if(passengers instanceof ArmorStand){
                    var armorStand = (ArmorStand) passengers;
                    armorStand.damage(100);
                }
            });
        }
    }

    @EventHandler
    public void riderDamage(EntityDamageByEntityEvent e){
        var entity = e.getEntity();
        if(entity instanceof ArmorStand && entity.getCustomName() != null){
            entity = (ArmorStand) e.getEntity();
            var findVex = entity.getLocation().getNearbyEntities(1, 2, 1).stream().filter(ent -> (ent instanceof Vex)).findAny();
            if(findVex.isPresent()){
                var vex = (Vex) findVex.get();
                var damage = e.getDamage();
                e.setCancelled(true);
                vex.damage(damage);
            }

        }
    }

    @EventHandler
    public void mobsDamageModifier(EntityDamageByEntityEvent e){
        var damager = e.getDamager();
        if(!(damager instanceof Player) && damager.getCustomName() != null){
            var damageAmplifier = instance.getGame().getDamageAmplifier();
            e.setDamage(e.getDamage()*damageAmplifier);
        }
    }

    @EventHandler
    public void onJoinHide(PlayerJoinEvent e) {
        var player = e.getPlayer();
        // If gamemode is Spectator, then hide him from all other non spectators
        if (e.getPlayer().getGameMode() == GameMode.SPECTATOR) {
            Bukkit.getOnlinePlayers().stream().filter(all -> all.getGameMode() != GameMode.SPECTATOR)
                    .forEach(all -> all.hidePlayer(instance, player));
        } else {
            // If gamemode isn't Spectator, then hide all spectators for him.
            Bukkit.getOnlinePlayers().stream().filter(it -> it.getGameMode() == GameMode.SPECTATOR)
                    .forEach(all -> player.hidePlayer(instance, all.getPlayer()));
        }
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e) {
        var player = e.getPlayer();
        // If gamemode to change is spectator
        if (e.getNewGameMode() == GameMode.SPECTATOR) {

            Bukkit.getOnlinePlayers().stream().forEach(all -> {
                // If players are not specs, hide them the player
                if (all.getGameMode() != GameMode.SPECTATOR) {
                    all.hidePlayer(instance, player);
                } else {
                    // If players are specs, then show them to the player
                    player.showPlayer(instance, all);
                }
            });
        } else {
            Bukkit.getOnlinePlayers().stream().forEach(all -> {
                // When switching to other gamemodes, show them if not visible to player
                if (!all.canSee(player)) {
                    all.showPlayer(instance, player);
                }
                // If one of the players is a spec, hide them from the player
                if (all.getGameMode() == GameMode.SPECTATOR) {
                    player.hidePlayer(instance, all);
                }
            });
        }
    }


}