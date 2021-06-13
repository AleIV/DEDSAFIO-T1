package net.noobsters.core.paper;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.commands.PaperCommandManager;
import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import lombok.Getter;
import lombok.Setter;
import net.noobsters.core.paper.Commands.FightCMD;
import net.noobsters.core.paper.Commands.permadedCMD;
import net.noobsters.core.paper.Commands.worldCMD;
import net.noobsters.core.paper.Listeners.ListenerManager;

public class PERMADED extends JavaPlugin {

  private @Getter PaperCommandManager commandManager;
  private @Getter ListenerManager listenerManager;
  private @Getter Game game;
  //private @Getter CraftingManager craftingManager;
  private static @Getter @Setter TaskChainFactory taskChainFactory;

  private static @Getter PERMADED instance;

  @Override
  public void onEnable() {

    instance = this;

    // managers
    commandManager = new PaperCommandManager(this);
    listenerManager = new ListenerManager(this);
    game = new Game(this);
    //craftingManager = new CraftingManager(this);
    taskChainFactory = BukkitTaskChainFactory.create(this);

    game.runTaskTimerAsynchronously(this, 0L, 20L);

    // commands
    commandManager.registerCommand(new worldCMD(this));
    commandManager.registerCommand(new permadedCMD(this));
    commandManager.registerCommand(new FightCMD(this));

    Bukkit.getScheduler().runTaskLater(this, task -> {
      WorldCreator arenaWorld = new WorldCreator("FINALFIGHT");
      arenaWorld.environment(Environment.NORMAL);
      arenaWorld.createWorld();

    }, 20 * 10);

  }

  @Override
  public void onDisable() {

  }

  public void animation(String text, String sound, String letter, int number, boolean right){

    var chain = PERMADED.newChain();

    var count = 0;
    var character = 92;
    var charac = Character.toString((char)character);

    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a times 0 1 60");
    
    Bukkit.getOnlinePlayers().forEach(p->{
        p.playSound(p.getLocation(), sound, 1, 1);
    });
    
    while (count < number) {

        final var current = count;

        var id = "" + (current <= 9 ? "0" + current : current);
        var code = right ? (charac + "uE" + id + letter) : (charac + "uE" + letter + id);

        chain.delay(1).sync(() -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"" + code + "\"}");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a actionbar {\"text\":\"" + text + "\"}");

        });
        count++;
    }

    chain.sync(TaskChain::abort).execute();
}


  public static <T> TaskChain<T> newChain() {
    return taskChainFactory.newChain();
  }

  public static <T> TaskChain<T> newSharedChain(String name) {
    return taskChainFactory.newSharedChain(name);
  }

}