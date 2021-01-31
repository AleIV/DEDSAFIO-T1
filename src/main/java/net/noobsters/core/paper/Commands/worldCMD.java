package net.noobsters.core.paper.Commands;

import org.bukkit.World;
import org.bukkit.entity.Player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.noobsters.core.paper.PERMADED;

@RequiredArgsConstructor
@CommandPermission("tpworld.cmd")
@CommandAlias("tpworld")
public class worldCMD extends BaseCommand {

    private @NonNull PERMADED instance;

    @Default
    @CommandCompletion("@worlds")
    public void tpWorld(Player sender, World world) {
        sender.teleport(world.getSpawnLocation());
        sender.sendMessage("Teleported to world " + world);
    }

}