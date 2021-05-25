package net.noobsters.core.paper.Listeners;

import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.noobsters.core.paper.GameTickEvent;
import net.noobsters.core.paper.PERMADED;

public class Extra implements Listener {

    PERMADED instance;
    Random random = new Random();

    Extra(PERMADED instance) {
        this.instance = instance;
    }


    @EventHandler
    public void onTick(GameTickEvent e){
        var difficulty = instance.getGame().getDifficultyChanges();
        Bukkit.getScheduler().runTask(instance, () -> {
            if(difficulty.get("meteor")){
                meteorRefresh();
            }

        });

    }

    public float getDistance(Location val1, Location val2) {

        double x1 = val1.getX();
        double z1 = val1.getZ();

        double x2 = val2.getX();
        double z2 = val2.getZ();

        return (float) Math.abs(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2)));
    }

    public void meteorRefresh() {
        
        Bukkit.getOnlinePlayers().parallelStream().filter(player-> player.getWorld() == Bukkit.getWorld("world")).forEach(player -> {

            var meteors = player.getNearbyEntities(32, 100, 32).stream().filter(meteor -> 
                meteor instanceof ArmorStand && meteor.getCustomName() != null && meteor.getCustomName().contains("Meteor")).collect(Collectors.toList());

            meteors.forEach(meteor ->{
                player.damage(2);
                player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 1));
            });

        });
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        var damager = e.getDamager();
        
        if(damager instanceof Player){
            var player = (Player) damager;
            var item = player.getEquipment().getItemInMainHand();
            var meta = item;
            var entity = e.getEntity();
            if(item != null && item.getItemMeta().hasCustomModelData() && !entity.isDead()){
                var living = (LivingEntity) entity;
                if(meta.getItemMeta().getCustomModelData() == 90){
                    living.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*5, 0));
                }
            }
        }
    }

    

}