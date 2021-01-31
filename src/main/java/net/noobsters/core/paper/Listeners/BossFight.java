package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import net.noobsters.core.paper.PERMADED;

public class BossFight implements Listener{

    PERMADED instance;
    Random random = new Random();

    BossFight(PERMADED instance){
        this.instance = instance;
    }
    
    @EventHandler
    public void piglinZombification(CreatureSpawnEvent e) {

    }

}