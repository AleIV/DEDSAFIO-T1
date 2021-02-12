package net.noobsters.core.paper.Commands;

import org.bukkit.command.CommandSender;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

@RequiredArgsConstructor
@CommandPermission("permaded.cmd")
@CommandAlias("permaded")
public class permadedCMD extends BaseCommand {

    private @NonNull PERMADED instance;

    @Subcommand("difficulty")
    public void difficultyChange(CommandSender sender, int change) {
        instance.getGame().setDifficultyChange(change);
        sender.sendMessage(ChatColor.GREEN + "Difficulty change set to " + change);
    }

    @Subcommand("resistance")
    public void resistanceChange(CommandSender sender, int change) {
        instance.getGame().setResistanceAmplifier(change);
        sender.sendMessage(ChatColor.GREEN + "Resistance amplifier set to " + change);
    }

    @Subcommand("damage")
    public void damageChange(CommandSender sender, int change) {
        instance.getGame().setDamageAmplifier(change);
        sender.sendMessage(ChatColor.GREEN + "Damage amplifier set to " + change);
    }

}