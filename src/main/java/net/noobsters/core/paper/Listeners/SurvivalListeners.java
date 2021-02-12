package net.noobsters.core.paper.Listeners;

import java.util.HashMap;
import java.util.Random;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class SurvivalListeners implements Listener{

    PERMADED instance;
    Random random = new Random();
    private static String MESSAGE = ChatColor.translateAlternateColorCodes('&',
    "&bThis server is only for special users! \n &aUpgrade your rank at &6noobsters.buycraft.net");
    private HashMap<String, Long> combatlog = new HashMap<>();
    

    SurvivalListeners(PERMADED instance){
        this.instance = instance;

        Bukkit.getScheduler().runTaskTimerAsynchronously(instance, () -> {
            var iterator = combatlog.entrySet().iterator();
            while (iterator.hasNext()) {
                var entry = iterator.next();
                var player = Bukkit.getPlayer(entry.getKey());
                var differential = entry.getValue() - System.currentTimeMillis();
                if (differential <= 0) {
                    iterator.remove();
                    player.sendMessage(ChatColor.AQUA + "[CombatLog] " + ChatColor.GREEN + "You are no longer in combat. You can log out.");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1, 0.6f);
                }
            }
        }, 2L, 2L);

    }

    @EventHandler
    public void onCombat(EntityDamageByEntityEvent e){
        var damager = e.getDamager();

        if(damager instanceof Projectile){
            var projectile = (Projectile) damager;
            if(projectile.getShooter() instanceof Player){
                damager = (Player) projectile.getShooter();
            }
        }

        if(e.getEntity() instanceof Player && damager instanceof Player && e.getEntity() != e.getDamager()){
            var player = (Player) e.getEntity();
            var player2 = (Player) e.getDamager();

            if(!combatlog.containsKey(player.getUniqueId().toString())){
                var message = ChatColor.AQUA + "[CombatLog] " + ChatColor.RED + "YOU ARE IN COMBAT. do not disconnect.";
                player.sendMessage(message);
                player2.sendMessage(message);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1, 0.6f);
                player2.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1, 0.6f);
            }

            combatlog.put(player.getUniqueId().toString(), System.currentTimeMillis() + 30_000);
            combatlog.put(player2.getUniqueId().toString(), System.currentTimeMillis() + 30_000);

        }
    }

    @EventHandler
    public void onCombatLogOut(PlayerQuitEvent e){
        var player = e.getPlayer();
        if(combatlog.containsKey(player.getUniqueId().toString())){
            player.damage(200);
        }
    }

    
    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        var loc = e.getEntity().getLocation();
        var x = (int) loc.getX();
        var y = (int) loc.getY();
        var z = (int) loc.getZ();
        Bukkit.broadcastMessage(ChatColor.GREEN + e.getEntity().getName().toString() + " died at coordinates: "+ ChatColor.AQUA + "X=" + x + " Y=" + y + " Z=" + z);

        if(e.getEntity().getLastDamageCause().getCause() == DamageCause.CUSTOM){
            e.setDeathMessage(e.getEntity().getName().toString() + " died combat log out");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void slotLimit(PlayerLoginEvent e) {
        final var player = e.getPlayer();
        if (!player.hasPermission("survival.access"))
            e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_FULL, MESSAGE);

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void slotLimit(PlayerJoinEvent e) {
        final var player = e.getPlayer();
        if (!player.hasPermission("survival.access"))
            player.kickPlayer(MESSAGE);

    }

    @EventHandler
    public void onSpawn(PlayerJoinEvent e){
        var player = e.getPlayer();
        if(!player.hasPlayedBefore()){
            var loc = new Location(Bukkit.getWorld("world"), chooseCoord(), 200, chooseCoord());
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20*30, 0));
            player.teleport(loc);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        var player = e.getPlayer();
        if(player.getBedSpawnLocation() == null){
            var loc = new Location(Bukkit.getWorld("world"), chooseCoord(), 200, chooseCoord());
            e.setRespawnLocation(loc);
        }
    }

    @EventHandler
    public void spawn(PlayerPostRespawnEvent e){
        var player = e.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20*30, 0));
    }

    public Integer chooseCoord(){
        var num = random.nextInt(500);
        num = random.nextBoolean() ? ~(num) : num;
        return num;
    }


}