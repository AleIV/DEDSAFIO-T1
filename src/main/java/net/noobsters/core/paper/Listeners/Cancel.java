package net.noobsters.core.paper.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import net.noobsters.core.paper.PERMADED;

public class Cancel implements Listener {

    PERMADED instance;

    Cancel(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onCreature(CreatureSpawnEvent e){
        var entity = e.getEntity();
        if(entity instanceof Sheep){
            e.setCancelled(true);

        }else if(entity instanceof Horse && entity.getWorld() == Bukkit.getWorld("world")){
            e.setCancelled(true);

        }else if(entity instanceof Bat){
            e.setCancelled(true);

        }
    }

}