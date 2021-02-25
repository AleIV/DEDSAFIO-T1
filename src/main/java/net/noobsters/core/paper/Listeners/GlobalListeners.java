package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class GlobalListeners implements Listener{

    PERMADED instance;
    Random random = new Random();
    static public String NO_RP_EN = ChatColor.AQUA + "You need the custom resourcepack to play!\n " +
         ChatColor.GREEN + "1. Download the resource pack. \n" +
         ChatColor.YELLOW + "https://www.dropbox.com/s/4qotet9czhvkhv1/PERMADED.zip?dl=1 \n" + 
         ChatColor.GREEN + "2. Paste the file into the resource folder in Minecraft. \n" + 
         ChatColor.GREEN + "3. Select the resource pack in the game and select the custom language: 'PERMADED(NIGHTMARE)' \n";

    static public String NO_RP_ES = ChatColor.AQUA + "Necesitas el texture pack para jugar!\n " +
         ChatColor.GREEN + "1. Descarga el texture pack. \n" +
         ChatColor.YELLOW + "https://www.dropbox.com/s/4qotet9czhvkhv1/PERMADED.zip?dl=1 \n" + 
         ChatColor.GREEN + "2. Pega el archivo en el resource pack folder en la carpeta de Minecraft. \n" + 
         ChatColor.GREEN + "3. Activa el texture pack en el juego y después selecciona el lenguaje custom: 'PERMADED(NIGHTMARE)' \n";

    GlobalListeners(PERMADED instance){
        this.instance = instance;
    }

    /*
    @EventHandler
    public void impact(ProjectileHitEvent e) {
        var entity = e.getHitEntity();
        var block = e.getHitBlock();
        var projectile = e.getEntity();
        var shooter = projectile.getShooter();
        if(projectile instanceof Arrow){
            var loc = block.getLocation();
            if(entity != null){
                loc.add(0, -10, 0).createExplosion(100, true);
                loc.getBlock().setType(Material.OBSIDIAN);
            }else if(block != null){
                loc.add(0, -10, 0).createExplosion(100, true);
                loc.getBlock().setType(Material.OBSIDIAN);

            }
        }
    }*/

    @EventHandler
    public void onResourcePackChange(PlayerLocaleChangeEvent e){
        var player = e.getPlayer();

        if(!e.getLocale().contains("NOOBSTERS")){
            player.kickPlayer(NO_RP_ES);
        }else if(!e.getLocale().contains("NOOBSTERS_2")){
            player.kickPlayer(NO_RP_ES + ChatColor.RED + "\n Hay otra actualizacion del texture pack, descarga la ultima!");
            
        }
    }

    @EventHandler
    public void mobsDamageModifier(EntityDamageByEntityEvent e){
        var damager = e.getDamager();
        var entity = e.getEntity();
        var damageAmplifier = instance.getGame().getDamageAmplifier();
        if(damager instanceof Projectile){
            var proj = (Projectile) damager;
            var shooter = proj.getShooter();
            if(shooter != null && !(shooter instanceof Player)){
                e.setDamage(e.getDamage()*damageAmplifier);
            }
        }

        if(damager instanceof WitherSkull && entity instanceof Player){
            var player = (Player) entity;
            
            player.damage(4*damageAmplifier);
        }

        if(!(damager instanceof Player) && damager.getCustomName() != null){
            e.setDamage(e.getDamage()*damageAmplifier);

        }

    }

    @EventHandler
    public void mobsResistanceModifier(EntityDamageByEntityEvent e){
        var entity = e.getEntity();
        if(entity.getCustomName() != null){
            var resistanceAmplifier = instance.getGame().getResistanceAmplifier();
            e.setDamage(e.getDamage()-resistanceAmplifier);
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