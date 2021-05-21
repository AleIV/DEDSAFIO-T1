package net.noobsters.core.paper.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

@RequiredArgsConstructor
@CommandAlias("tpworld")
public class worldCMD extends BaseCommand {

    private @NonNull PERMADED instance;

    @Default
    @CommandPermission("tpworld.cmd")
    @CommandCompletion("@worlds")
    public void tpWorld(Player sender, World world) {
        sender.teleport(world.getSpawnLocation());
        sender.sendMessage("Teleported to world " + world);
    }

    @Subcommand("respawn")
    @CommandAlias("respawn")
    public void respawn(Player sender) {
        sender.teleport(new Location(Bukkit.getWorld("world"), 0, 92, 0));
        Bukkit.broadcastMessage(ChatColor.ALL_CODES + sender.getName() + " respawned!");
        sender.setGameMode(GameMode.SURVIVAL);
    }

}