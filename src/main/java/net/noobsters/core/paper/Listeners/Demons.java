package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.entity.Piglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import net.noobsters.core.paper.PERMADED;

public class Demons implements Listener{

    PERMADED instance;
    Random random = new Random();

    Demons(PERMADED instance){
        this.instance = instance;
    }
    
    @EventHandler
    public void piglinZombification(CreatureSpawnEvent e) {
        if (e.getEntity() instanceof Piglin) {
            
        }

    }
}