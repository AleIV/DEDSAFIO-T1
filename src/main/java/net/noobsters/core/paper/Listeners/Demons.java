package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vex;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class Demons implements Listener{

    PERMADED instance;
    Random random = new Random();

    Demons(PERMADED instance){
        this.instance = instance;
    }
    
    @EventHandler
    public void demons(CreatureSpawnEvent e) {
        var entity =  e.getEntity();
        var difficulty = instance.getGame().getDifficultyChanges();

        if(entity instanceof Phantom && difficulty.get("dragon")){
            var phantom = (Phantom) entity;
            phantom.setCustomName(ChatColor.GRAY + "Phantom Thief");
            phantom.setSize(2);

            var loc = phantom.getLocation();
            var players = loc.getNearbyPlayers(64, player-> player.getGameMode() == GameMode.SURVIVAL).stream().findAny();
            if(players.isPresent()) phantom.setTarget(players.get());

        }else if(entity instanceof Vex && difficulty.get("mages")){
            var spirit = (Vex) entity;
            spirit.setCustomName(ChatColor.DARK_PURPLE + "Spirit");

        }else if(difficulty.get("demons") && entity instanceof Ghast){
            var ghast = (Ghast) entity;
            ghast.setCustomName(ChatColor.DARK_PURPLE + "Carminite Ghast Guard");
            var loc = ghast.getLocation();
            var players = loc.getNearbyPlayers(64, player-> player.getGameMode() == GameMode.SURVIVAL).stream().findAny();
            if(players.isPresent()) ghast.setTarget(players.get());

        }else if(difficulty.get("demons") && entity instanceof Blaze){
            var blaze = (Blaze) entity;
            blaze.setCustomName(ChatColor.DARK_PURPLE + "Pure Demon");
            blaze.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 600, 3));

        }else if(difficulty.get("demons") && entity instanceof Enderman){
            var enderman = (Enderman) entity;
            enderman.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 600, 1));
            switch (random.nextInt(4)) {
                case 1:{
                    enderman.setCustomName(ChatColor.DARK_PURPLE + "Corrupted Demon");
                }
                    break;
                case 2:{
                    enderman.setCustomName(ChatColor.DARK_PURPLE + "Old Demon");
                }
                    break;
                case 3:{
                    enderman.setCustomName(ChatColor.DARK_PURPLE + "Demon");
                }
                    break;
            
                default:{
                    enderman.setCustomName(ChatColor.DARK_PURPLE + "Ghost");
                }
                    break;
            }
            if(random.nextInt(30) == 1){
                var loc = enderman.getLocation();
                var players = loc.getNearbyPlayers(64, player-> player.getGameMode() == GameMode.SURVIVAL).stream().findAny();
                if(players.isPresent()) enderman.setTarget(players.get());
            }
        }


    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e){
        var entity = e.getEntity();
        var damager = e.getDamager();
        var difficulty = instance.getGame().getDifficultyChanges();

        if(difficulty.get("dragon") && entity instanceof Player && damager instanceof Phantom){
            var player = (Player) entity;
            var inv = player.getInventory().getContents();
            var pos = random.nextInt(inv.length);
            var item = inv[pos];

            if(item != null){
                var drop = item.clone();
                player.getInventory().setItem(pos, null);
                player.getWorld().dropItemNaturally(player.getLocation().add(0, 5, 0), drop);
            }
        }

    }

}