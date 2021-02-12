package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.entity.Piglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import net.noobsters.core.paper.PERMADED;

public class Animals implements Listener{

    PERMADED instance;
    Random random = new Random();

    Animals(PERMADED instance){
        this.instance = instance;
    }
    
    @EventHandler
    public void piglinZombification(CreatureSpawnEvent e) {
        if (e.getEntity() instanceof Piglin) {
            
        }

    }
}