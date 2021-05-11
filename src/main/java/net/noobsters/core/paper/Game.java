package net.noobsters.core.paper;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Game extends BukkitRunnable{
    PERMADED instance;
    int spawnPatrolDelay = 600;
    int damageAmplifier = 4;
    int mobResistance = 60;
    int difficultyChange = 10;
    boolean combatlog = true;

    
    long gameTime = 0;
    long startTime = 0;
    
    

    public Game(PERMADED instance) {
        this.instance = instance;
        this.startTime = System.currentTimeMillis();

    }

    @Override
    public void run() {

        var new_time = (int) (Math.floor((System.currentTimeMillis() - startTime) / 1000.0));

        // set new gametime
        gameTime = new_time;

        Bukkit.getPluginManager().callEvent(new GameTickEvent(new_time, true));
    }
    
}
