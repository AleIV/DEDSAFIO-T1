package net.noobsters.core.paper;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Game extends BukkitRunnable{
    PERMADED instance;
    int spawnPatrolDelay = 600;
    int damageAmplifier = 1;
    int mobResistance = 0;
    HashMap<String, Boolean> difficultyChanges = new HashMap<>();
    boolean gulak = false;

    long gameTime = 0;
    long startTime = 0;
    
    

    public Game(PERMADED instance) {
        this.instance = instance;
        this.startTime = System.currentTimeMillis();

        difficultyChanges.put("zombies", false);
        difficultyChanges.put("spiders", false);
        difficultyChanges.put("skeletons", false);
        difficultyChanges.put("pigs", false);
        difficultyChanges.put("raiders", false);
        difficultyChanges.put("mages", false);
        difficultyChanges.put("demons", false);
        difficultyChanges.put("creepers", false);
        difficultyChanges.put("raids", false);

        difficultyChanges.put("lava", false);

    }

    @Override
    public void run() {

        var new_time = (int) (Math.floor((System.currentTimeMillis() - startTime) / 1000.0));

        // set new gametime
        gameTime = new_time;

        Bukkit.getPluginManager().callEvent(new GameTickEvent(new_time, true));
    }
    
}
