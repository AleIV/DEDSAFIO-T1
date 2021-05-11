package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.noobsters.core.paper.PERMADED;

public class DedsafioListener implements Listener{

    PERMADED instance;
    Random random = new Random();

    DedsafioListener(PERMADED instance){
        this.instance = instance;
    }
    
    @EventHandler
    public void playerDied(PlayerDeathEvent e) {
        //death animation
    }

}