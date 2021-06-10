package net.noobsters.core.paper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Game extends BukkitRunnable{
    PERMADED instance;
    int spawnPatrolDelay = 600;
    int damageAmplifier = 3;
    int mobResistance = 40;
    HashMap<String, Boolean> difficultyChanges = new HashMap<>();
    HashMap<String, Boolean> deathPlayers = new HashMap<>();

    List<String> fighters = new ArrayList<>();
    List<String> pvpOn = new ArrayList<>();
    List<String> reviveList = new ArrayList<>();
    boolean gulak = false;
    boolean closed = true;


    long gameTime = 0;
    long startTime = 0;
    
    public Game(PERMADED instance) {
        this.instance = instance;
        this.startTime = System.currentTimeMillis();

        difficultyChanges.put("pigcap", true);
        difficultyChanges.put("pigcapenable", false);

        difficultyChanges.put("zombies", true);
        difficultyChanges.put("spiders", true);
        difficultyChanges.put("skeletons", true);
        difficultyChanges.put("dragon", true);
        difficultyChanges.put("pigs", true);
        difficultyChanges.put("raiders", false); 
        difficultyChanges.put("mages", true);
        difficultyChanges.put("demons", false);
        difficultyChanges.put("creepers", false);
        difficultyChanges.put("raids", false);

        difficultyChanges.put("redstone", false);
        difficultyChanges.put("meteor", true);

        difficultyChanges.put("lava", true);
        difficultyChanges.put("environment", false);

        deathPlayers.put("0PHY", true);
        deathPlayers.put("Hasvik", true);
        deathPlayers.put("VancouverMC", true);
        deathPlayers.put("iByCris", true);
        deathPlayers.put("dedreviil", true);
        deathPlayers.put("Natalaan", true);
        deathPlayers.put("Greencito", true);
        deathPlayers.put("Laugamer721", true);
        deathPlayers.put("Estailus", true);
        deathPlayers.put("DennisCyan", true);
        deathPlayers.put("JavierMtz", true);
        deathPlayers.put("MariioTQ", true);
        deathPlayers.put("MissAndieFTW", true);
        deathPlayers.put("iRoier", true);
        deathPlayers.put("OllieGamerz", true);
        deathPlayers.put("ElMariana", true);
        deathPlayers.put("TomyCatt", true);
        deathPlayers.put("NefariusAP", true);
        deathPlayers.put("ElYost_", true);
        deathPlayers.put("Samueme", true);
        deathPlayers.put("iTakerMetal", true);
        deathPlayers.put("domgamermc", true);
        deathPlayers.put("ElBudget", true);
        deathPlayers.put("rociodtaa", true);
        deathPlayers.put("aldo_geo", true);
        deathPlayers.put("Puji2", true);
        deathPlayers.put("Bizzza", true);
        deathPlayers.put("iChurro", true);
        deathPlayers.put("ElChurches", true);
        deathPlayers.put("LarryCabirria", true);
        deathPlayers.put("Katuuta", true);
        deathPlayers.put("MrFerruzca", true);
        deathPlayers.put("Rubynavx", true);
        deathPlayers.put("Mariemoone", true);
        deathPlayers.put("notneekolul", true);
        deathPlayers.put("Renooxx", true);
        deathPlayers.put("xNephtunie", true);
        deathPlayers.put("Charliitoss", true);
        deathPlayers.put("GirlOfNox", true);
        deathPlayers.put("Lakshart", true);
        deathPlayers.put("AgarioObsession", true);
        deathPlayers.put("Zilverk", true);
        deathPlayers.put("SoyBarcaGamer", true);
        deathPlayers.put("Duxorethey", true);
        deathPlayers.put("Leyville", true);
        deathPlayers.put("D3stri", true);
        deathPlayers.put("AkimCraft24", true);
        deathPlayers.put("Nonigamer", true);
        deathPlayers.put("MYM_TUM_TUM", true);
        deathPlayers.put("Dannespino", true);
        deathPlayers.put("iDelt4", true);
        deathPlayers.put("ElKomanche", true);
        deathPlayers.put("TheRealCry", true);
        deathPlayers.put("AriGameplays", true);
        deathPlayers.put("ElJuaniquilador", true);

    }

    @Override
    public void run() {

        var new_time = (int) (Math.floor((System.currentTimeMillis() - startTime) / 1000.0));

        // set new gametime
        gameTime = new_time;

        Bukkit.getPluginManager().callEvent(new GameTickEvent(new_time, true));
    }
    
}
