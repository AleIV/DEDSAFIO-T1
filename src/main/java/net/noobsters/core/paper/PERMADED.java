package net.noobsters.core.paper;

import com.google.common.collect.ImmutableList;

import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import net.noobsters.core.paper.Commands.worldCMD;
import net.noobsters.core.paper.Listeners.ListenerManager;

//Valhalla where the dead warriors join the masses of those who have died in combat 

public class PERMADED extends JavaPlugin {
  // GUI tutorial: https://github.com/MrMicky-FR/FastInv
  // Scoreboard Tutorial: https://github.com/MrMicky-FR/FastBoard
  // Commands Tutorial: https://github.com/aikar/commands/wiki/Using-ACF

  private @Getter PaperCommandManager commandManager;
  private @Getter ListenerManager listenerManager;
  private @Getter Game game;

  private static @Getter PERMADED instance;

  @Override
  public void onEnable() {

    // worldcreator
    WorldCreator arenaWorld = new WorldCreator("PERMADED");
    arenaWorld.environment(Environment.THE_END);
    arenaWorld.createWorld();

    instance = this;

    // managers
    commandManager = new PaperCommandManager(this);
    listenerManager = new ListenerManager(this);
        
    //commands
    commandManager.registerCommand(new worldCMD(this));
    commandManager.getCommandCompletions().registerCompletion("stages", c -> {
      return ImmutableList.of("asd","asd","ads");
    });




        

    }

    @Override
    public void onDisable() {

  }
    
}