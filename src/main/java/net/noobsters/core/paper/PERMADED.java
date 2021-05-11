package net.noobsters.core.paper;

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
        
    //commands
    commandManager.registerCommand(new worldCMD(this));
    commandManager.registerCommand(new permadedCMD(this));

    }

    @Override
    public void onDisable() {

  }
    
}