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
    HashMap<String, Boolean> deathPlayers = new HashMap<>();
    boolean gulak = true;

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

        difficultyChanges.put("redstone", false);

        difficultyChanges.put("lava", false);


        deathPlayers.put("0PHY", false);
        deathPlayers.put("Hasvik", false);
        deathPlayers.put("VancouverMC", false);
        deathPlayers.put("iByCris", false);
        deathPlayers.put("dedreviil", false);
        deathPlayers.put("Natalaan", false);
        deathPlayers.put("Greencito", false);
        deathPlayers.put("Laugamer721", false);
        deathPlayers.put("Estailus", false);
        deathPlayers.put("DennisCyan", false);
        deathPlayers.put("JavierMtz", false);
        deathPlayers.put("MariioTQ", false);
        deathPlayers.put("MissAndieFTW", false);
        deathPlayers.put("iRoier", false);
        deathPlayers.put("AlcatrasSG", false);
        deathPlayers.put("OllieGamerz", false);
        deathPlayers.put("ElMariana", false);
        deathPlayers.put("TomyCatt", false);
        deathPlayers.put("NefariusAP", false);
        deathPlayers.put("ElYost_", false);
        deathPlayers.put("Samueme", false);
        deathPlayers.put("iTakerMetal", false);
        deathPlayers.put("domgamermc", false);
        deathPlayers.put("ElBudget", false);
        deathPlayers.put("rociodta", false);
        deathPlayers.put("aldo_geo", false);
        deathPlayers.put("Puji2", false);
        deathPlayers.put("Bizzza", false);
        deathPlayers.put("iChurro", false);
        deathPlayers.put("ElChurches", false);
        deathPlayers.put("LarryCabirria", false);
        deathPlayers.put("Katuuta", false);
        deathPlayers.put("MrFerruzca", false);
        deathPlayers.put("Rubynavx", false);
        deathPlayers.put("Mariemoone", false);
        deathPlayers.put("notneekolul", false);
        deathPlayers.put("Juanjuegajuegos", false);
        deathPlayers.put("Renooxx", false);
        deathPlayers.put("xNephtunie", false);
        deathPlayers.put("Charliitoss", false);
        deathPlayers.put("GirlOfNox", false);
        deathPlayers.put("Lakshart", false);
        deathPlayers.put("AgarioObsession", false);
        deathPlayers.put("Zilverk", false);
        deathPlayers.put("SoyBarcaGamer", false);


    }

    @Override
    public void run() {

        var new_time = (int) (Math.floor((System.currentTimeMillis() - startTime) / 1000.0));

        // set new gametime
        gameTime = new_time;

        Bukkit.getPluginManager().callEvent(new GameTickEvent(new_time, true));
    }
    
}
