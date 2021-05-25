package net.noobsters.core.paper;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import net.noobsters.core.paper.Commands.permadedCMD;
import net.noobsters.core.paper.Commands.worldCMD;
import net.noobsters.core.paper.Listeners.ListenerManager;

public class PERMADED extends JavaPlugin {

  private @Getter PaperCommandManager commandManager;
  private @Getter ListenerManager listenerManager;
  private @Getter Game game;

  private static @Getter PERMADED instance;

  @Override
  public void onEnable() {

    instance = this;

    // managers
    commandManager = new PaperCommandManager(this);
    listenerManager = new ListenerManager(this);
    game = new Game(this);

    game.runTaskTimerAsynchronously(this, 0L, 20L);

    // commands
    commandManager.registerCommand(new worldCMD(this));
    commandManager.registerCommand(new permadedCMD(this));

    Bukkit.getScheduler().runTaskLater(this, task -> {
      WorldCreator arenaWorld = new WorldCreator("FINALFIGHT");
      arenaWorld.environment(Environment.NORMAL);
      arenaWorld.createWorld();

      WorldCreator dungeon = new WorldCreator("DUNGEON");
      dungeon.environment(Environment.NORMAL);
      dungeon.createWorld();

      WorldCreator extra = new WorldCreator("EXTRA");
      extra.environment(Environment.NORMAL);
      extra.createWorld();

      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives add health_tab health");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives setdisplay list health_tab");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives modify health_tab rendertype integer");
  
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives setdisplay list health_name");
      
    }, 20 * 10);

  }

  @Override
  public void onDisable() {

  }

}