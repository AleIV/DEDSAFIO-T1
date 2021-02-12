package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.noobsters.core.paper.PERMADED;

public class Monsters implements Listener{

    PERMADED instance;
    Random random = new Random();

    Monsters(PERMADED instance){
        this.instance = instance;
    }
    
    @EventHandler
    public void monsterSpawns(CreatureSpawnEvent e) {
        var game = instance.getGame();
        if (game.getDifficultyChange() >= 2 && e.getEntity() instanceof Spider) {
            var spider = (Spider) e.getEntity();
            spider.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20*10000, 1));
            spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*10000, 1));
            spider.setCustomName("Monster Spider");
        }

    }
}