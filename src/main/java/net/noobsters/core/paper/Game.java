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
    int damageAmplifier = 1;
    int mobResistance = 0;
    HashMap<String, Boolean> difficultyChanges = new HashMap<>();
    HashMap<String, Boolean> deathPlayers = new HashMap<>();
    List<String> pvpOn = new ArrayList<>();
    List<String> trap = new ArrayList<>();
    boolean gulak = false;
    boolean closed = true;

    long gameTime = 0;
    long startTime = 0;
    
    public Game(PERMADED instance) {
        this.instance = instance;
        this.startTime = System.currentTimeMillis();

        difficultyChanges.put("zombies", true);
        difficultyChanges.put("spiders", false);
        difficultyChanges.put("skeletons", false);
        difficultyChanges.put("pigs", false);
        difficultyChanges.put("raiders", false);
        difficultyChanges.put("mages", false);
        difficultyChanges.put("demons", false);
        difficultyChanges.put("creepers", false);
        difficultyChanges.put("raids", false);

        difficultyChanges.put("redstone", false);
        difficultyChanges.put("meteor", false);

        difficultyChanges.put("blood", false);

        difficultyChanges.put("lava", false);

        deathPlayers.put("0PHY", false);
        deathPlayers.put("Hasvik", true);
        deathPlayers.put("VancouverMC", true);
        deathPlayers.put("iByCris", true);
        deathPlayers.put("dedreviil", false);
        deathPlayers.put("Natalaan", false);
        deathPlayers.put("Greencito", false);
        deathPlayers.put("Laugamer721", true);
        deathPlayers.put("Estailus", false);
        deathPlayers.put("DennisCyan", false);
        deathPlayers.put("JavierMtz", true);
        deathPlayers.put("MariioTQ", false);
        deathPlayers.put("MissAndieFTW", false);
        deathPlayers.put("iRoier", false);
        deathPlayers.put("OllieGamerz", false);
        deathPlayers.put("ElMariana", false);
        deathPlayers.put("TomyCatt", true);
        deathPlayers.put("NefariusAP", false);
        deathPlayers.put("ElYost_", false);
        deathPlayers.put("Samueme", false);
        deathPlayers.put("iTakerMetal", false);
        deathPlayers.put("domgamermc", true);
        deathPlayers.put("ElBudget", false);
        deathPlayers.put("rociodtaa", true);
        deathPlayers.put("aldo_geo", false);
        deathPlayers.put("Puji2", false);
        deathPlayers.put("Bizzza", false);
        deathPlayers.put("iChurro", true);
        deathPlayers.put("ElChurches", false);
        deathPlayers.put("LarryCabirria", false);
        deathPlayers.put("Katuuta", false);
        deathPlayers.put("MrFerruzca", true);
        deathPlayers.put("Rubynavx", false);
        deathPlayers.put("Mariemoone", false);
        deathPlayers.put("notneekolul", false);
        deathPlayers.put("Renooxx", false);
        deathPlayers.put("xNephtunie", true);
        deathPlayers.put("Charliitoss", true);
        deathPlayers.put("GirlOfNox", false);
        deathPlayers.put("Lakshart", false);
        deathPlayers.put("AgarioObsession", true);
        deathPlayers.put("Zilverk", false);
        deathPlayers.put("SoyBarcaGamer", false);
        deathPlayers.put("Duxorethey", false);
        deathPlayers.put("Leyville", true);
        deathPlayers.put("D3stri", false);
        deathPlayers.put("AkimCraft24", false);
        deathPlayers.put("Nonigamer", true);
        deathPlayers.put("MYM_TUM_TUM", false);
        deathPlayers.put("Dannespino", false);
        deathPlayers.put("iDelt4", false);
        deathPlayers.put("ElKomanche", false);
        deathPlayers.put("TheRealCry", false);
        deathPlayers.put("AriGameplays", true);
        deathPlayers.put("ElJuaniquilador", false);

    }

    @Override
    public void run() {

        var new_time = (int) (Math.floor((System.currentTimeMillis() - startTime) / 1000.0));

        // set new gametime
        gameTime = new_time;

        Bukkit.getPluginManager().callEvent(new GameTickEvent(new_time, true));
    }
    
}
