package net.noobsters.core.paper.Commands;

import org.bukkit.entity.Player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.noobsters.core.paper.PERMADED;

@RequiredArgsConstructor
@CommandPermission("permaded.cmd")
@CommandAlias("permaded")
public class permadedCMD extends BaseCommand {

    private @NonNull PERMADED instance;

    @Default
    public void fase(Player sender, String fase) {
        instance.getGame().setFase(fase);
        sender.sendMessage("Fase changed to " + fase);
    }

}