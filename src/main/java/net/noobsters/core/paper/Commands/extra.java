package net.noobsters.core.paper.Commands;

import java.util.Random;

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
@CommandPermission("dedsafio.cmd")
@CommandAlias("boss")
public class extra extends BaseCommand {

    private @NonNull PERMADED instance;
    static public String KICK_MSG = ChatColor.AQUA + "GG\n";
    Random random = new Random();

    @Subcommand("enable")
    public void bossbarenable(CommandSender sender, String bossbar, boolean bool) {
        var game = instance.getGame();
        var bossbars = game.getBossbars();
        if(bossbars.containsKey(bossbar)){
            var boss = bossbars.get(bossbar);
            boss.setVisible(bool);
            sender.sendMessage(ChatColor.GREEN + "bossbar set to " + bool);

        }else{
            sender.sendMessage(ChatColor.RED + "NO HAY");
        }
        
    }

    @Subcommand("setHealth")
    public void setHealth(CommandSender sender, String bossbar, double value) {
        var game = instance.getGame();
        var bossbars = game.getBossbars();

        if(bossbars.containsKey(bossbar)){
            bossbars.get(bossbar).setProgress(value);
            sender.sendMessage(ChatColor.GREEN + "bossbar health set to " + value);

        }else{
            sender.sendMessage(ChatColor.RED + "NO HAY");
        }

    }


}
