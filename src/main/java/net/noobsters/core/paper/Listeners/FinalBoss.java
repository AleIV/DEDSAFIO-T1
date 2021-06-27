package net.noobsters.core.paper.Listeners;

import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.GameTickEvent;
import net.noobsters.core.paper.PERMADED;

public class FinalBoss implements Listener {

    Location blood1 = new Location(Bukkit.getWorld("FINALFIGHT"), -1060, 160, 67);
    Location blood2 = new Location(Bukkit.getWorld("FINALFIGHT"), -943, 21, 141);

    PERMADED instance;
    Random random = new Random();

    FinalBoss(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onTick(GameTickEvent e) {
        var difficulty = instance.getGame().getDifficultyChanges();
        Bukkit.getScheduler().runTask(instance, () -> {
            if (difficulty.get("finalboss")) {

                playersRefresh();

            }

        });

    }

    public boolean isInArea(Location point, Location pos1, Location pos2) {

        var cX = pos1.getX() < pos2.getX();
        var cZ = pos1.getZ() < pos2.getZ();

        var minX = cX ? pos1.getX() : pos2.getX();
        var maxX = cX ? pos2.getX() : pos1.getX();

        var minZ = cZ ? pos1.getZ() : pos2.getZ();
        var maxZ = cZ ? pos2.getZ() : pos1.getZ();

        if (point.getX() < minX || point.getZ() < minZ)
            return false;
        if (point.getX() > maxX || point.getZ() > maxZ)
            return false;

        return true;
    }

    public void playersRefresh() {

        Bukkit.getOnlinePlayers().stream().forEach(player -> {

            var slowfalling = player.getNearbyEntities(16, 16, 16).stream()
                    .filter(slowStand -> slowStand instanceof ArmorStand && slowStand.getCustomName() != null
                            && slowStand.getCustomName().contains("slow"))
                    .map(e -> (ArmorStand) e).collect(Collectors.toList());

            if (!slowfalling.isEmpty()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 3, 1));

            }

            if (player.getWorld() == Bukkit.getWorld("FINALFIGHT")) {

                var drugs = player.getNearbyEntities(16, 16, 16).stream()
                    .filter(drug -> drug instanceof ArmorStand && drug.getCustomName() != null
                            && drug.getCustomName().contains("drugs"))
                    .map(e -> (ArmorStand) e).collect(Collectors.toList());

                if (!drugs.isEmpty()) {
                    
                    Bukkit.getOnlinePlayers().stream().forEach(all -> {
                        if (all.getGameMode() == GameMode.SURVIVAL || !all.hasPermission("mod.perm")) {
                            player.hidePlayer(instance, all);
                        }
                    });
    
                }else{

                    Bukkit.getOnlinePlayers().stream().forEach(all -> {
                        if (all.getGameMode() == GameMode.SURVIVAL) {
                            player.showPlayer(instance, all);
                        }
                    });
    
                }

                if(player.getLocation().getY() < 163 && isInArea(player.getLocation(), blood1, blood2)){
                    var name = player.getName();
                    var character = 92;
                    var charac = Character.toString((char) character);

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            "title " + name + " actionbar {\"text\":\"" + charac + "uE4A8" + "\"}");
                }

            }

        });

    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void checkForMute(AsyncPlayerChatEvent e) {
        var difficulty = instance.getGame().getDifficultyChanges();
        
        if (difficulty.get("globalmute") && !e.getPlayer().hasPermission("mod.perm")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.GRAY + "Shhhhhhhhhhh!");
        }
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        var entity = e.getEntity();

        if (entity.getWorld() == Bukkit.getWorld("FINALFIGHT")) {
            if(entity instanceof MagmaCube && e.getSpawnReason().toString().contains("NATURAL")){
                e.setCancelled(true);              

            }
        }
    }

    @EventHandler
    public void damageent(EntityDamageByEntityEvent e){
        var entity = e.getEntity();
        var damager = e.getDamager();
        if(entity.getWorld() == Bukkit.getWorld("FINALFIGHT")){
            if(entity instanceof ItemFrame){
                if(damager instanceof Player){
                    var player = (Player) damager;
                    if(!player.hasPermission("mod.perm")){
                        e.setCancelled(true);
                    }
                }else{
                    e.setCancelled(true);
                }
                
            }
        }
    }

}