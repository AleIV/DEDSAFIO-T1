package net.noobsters.core.paper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.mrmicky.fastinv.ItemBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.md_5.bungee.api.ChatColor;

@Data
@EqualsAndHashCode(callSuper=false)
public class Game extends BukkitRunnable{
    PERMADED instance;
    int spawnPatrolDelay = 600;
    int damageAmplifier = 3;
    int mobResistance = 60;

    HashMap<String, BossBar> bossbars = new HashMap<>();

    HashMap<String, Boolean> difficultyChanges = new HashMap<>();
    HashMap<String, Boolean> deathPlayers = new HashMap<>();
    HashMap<String, String> disguises = new HashMap<>();

    HashMap<String, Boolean> intelligence = new HashMap<>();

    List<String> fighters = new ArrayList<>();
    List<String> pvpOn = new ArrayList<>();
    List<String> reviveList = new ArrayList<>();

    boolean gulak = false;
    boolean closed = true;

    long gameTime = 0;
    long startTime = 0;

    ItemStack tnt = new ItemBuilder(Material.TNT).name(ChatColor.DARK_RED + "TNT").build();
    
    public Game(PERMADED instance) {
        this.instance = instance;
        this.startTime = System.currentTimeMillis();

        var raid = Bukkit.createBossBar(new NamespacedKey(instance, "boss-raid"), ChatColor.GRAY + "DEATH RAID", BarColor.BLUE, BarStyle.SOLID);
        var warden = Bukkit.createBossBar(new NamespacedKey(instance, "boss-warden"), ChatColor.DARK_AQUA + "WARDEN MONSTROSITY", BarColor.GREEN, BarStyle.SOLID);
        var redstone = Bukkit.createBossBar(new NamespacedKey(instance, "boss-redstone"), ChatColor.DARK_RED + "REDSTONE MONSTROSITY", BarColor.YELLOW, BarStyle.SOLID);

        raid.setVisible(false);
        warden.setVisible(false);
        redstone.setVisible(false);
        raid.setProgress(1);
        warden.setProgress(1);
        redstone.setProgress(1);

        bossbars.put("raid", raid);
        bossbars.put("warden", warden);
        bossbars.put("redstone", redstone);

        difficultyChanges.put("race", false);
        difficultyChanges.put("tnt", false);
        difficultyChanges.put("sumo", false);

        difficultyChanges.put("blazecap", true);
        difficultyChanges.put("pigcap", true);
        difficultyChanges.put("creepercap", true);
        difficultyChanges.put("demoncap", true);
        difficultyChanges.put("pigcapenable", false);
        difficultyChanges.put("villager", false);

        difficultyChanges.put("blind", false);
        difficultyChanges.put("watermonster", true);

        difficultyChanges.put("totems50", true);
        difficultyChanges.put("totems", true);

        difficultyChanges.put("zombies", true);
        difficultyChanges.put("spiders", true);
        difficultyChanges.put("skeletons", true);
        difficultyChanges.put("dragon", true);
        difficultyChanges.put("pigs", true);
        difficultyChanges.put("raiders", true); 
        difficultyChanges.put("mages", true);
        difficultyChanges.put("naturally", true);
        difficultyChanges.put("demons", true);
        difficultyChanges.put("creepers", true);
        difficultyChanges.put("raids", false);

        difficultyChanges.put("redstone", true);
        difficultyChanges.put("meteor", true);

        difficultyChanges.put("lava", true);
        difficultyChanges.put("environment", true);

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
