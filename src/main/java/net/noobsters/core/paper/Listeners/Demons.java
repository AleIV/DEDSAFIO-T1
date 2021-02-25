package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.entity.Vex;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

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
        var difficulty = instance.getGame().getDifficultyChange();
        if (difficulty >= 3 && entity instanceof Vex) {
            var spirit = (Vex) entity;
            spirit.setCustomName(ChatColor.DARK_PURPLE + "Spirit");
        }

    }
}