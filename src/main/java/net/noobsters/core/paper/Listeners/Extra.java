package net.noobsters.core.paper.Listeners;

import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
                playersRefresh();
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

    public void playersRefresh() {
        
        Bukkit.getOnlinePlayers().stream().forEach(player -> {

            var meteors = player.getNearbyEntities(32, 100, 32).stream().filter(meteor -> 
                meteor instanceof ArmorStand && meteor.getCustomName() != null && meteor.getCustomName().contains("Meteor")).collect(Collectors.toList());

            if(player.getWorld() == Bukkit.getWorld("world")){
                if(!meteors.isEmpty()){
                    player.damage(2);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 1));
                }
            }

            var equip = player.getEquipment();
            var helmet = equip.getHelmet();
            var chest = equip.getChestplate();
            var legs = equip.getLeggings();
            var boots = equip.getBoots();

            if(helmet != null && helmet.getType().toString().contains("GOLD")
                || chest != null && chest.getType().toString().contains("GOLD")
                    || legs != null && legs.getType().toString().contains("GOLD")
                        || boots != null && boots.getType().toString().contains("GOLD")){
                player.damage(2);
                player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 1));
            }
            

        });

    }

    

}