package net.noobsters.core.paper.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.taskchain.TaskChain;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

@RequiredArgsConstructor
@CommandAlias("tpworld")
@CommandPermission("tpworld.cmd")
public class worldCMD extends BaseCommand {

    private @NonNull PERMADED instance;
    Random random = new Random();

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
        Bukkit.broadcastMessage(ChatColor.GOLD + sender.getName() + " respawned!");
        sender.setGameMode(GameMode.SURVIVAL);
    }

    @Subcommand("jsoncreate")
    @CommandAlias("jsoncreate")
    public void json(CommandSender sender, String letter, int number, boolean right) {

        var character = 92;
        var charac = Character.toString((char)character);
        
        for (int i = 0; i < number; i++) {

            var id = "" + (i <= 9 ? "0" + i : i);
            var code = right ? (id + letter) : (letter + id);

            sender.sendMessage("{");
            sender.sendMessage("   \"type\": " + "\"bitmap\",");
            sender.sendMessage("   \"file\": " + "\"minecraft:custom/" + code + ".png\",");
            sender.sendMessage("   \"ascent\": " + "9,");
            sender.sendMessage("   \"height\": " + "256,");
            sender.sendMessage("   \"chars\": " + "[\"" + charac + "uE" + code + "\"]");
            sender.sendMessage("},");
        }

    }

    @Subcommand("say")
    @CommandAlias("say")
    public void say(CommandSender sender, String text) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b[&6DEDSAFIO&b] &f" + text));
    }

    @Subcommand("animation-test")
    @CommandAlias("animation-test")
    public void animationcmd(Player player, String text, String sound, String letter, int number, boolean bool) {

        animation(text, sound, letter, number, bool);
    }

    @Subcommand("animation")
    @CommandAlias("animation")
    public void animationcmd2(Player player, String type) {
        List<String> letters = new ArrayList<>();

        letters.add("A"); //ROJO
        letters.add("B"); //NARANJA
        letters.add("C"); //AMARILLO
        letters.add("D"); //VERDE
        letters.add("E"); //CELESTE
        letters.add("F"); //AZUL
        letters.add("0"); //MORADO
        letters.add("1"); //ROSA

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a times 0 1 60");

        var ruleta = letters.get(random.nextInt(letters.size()));

        switch (type) {
            case "ZOMBIES":{
                animation("", "ruedita", ruleta, 97, false);

                Bukkit.getScheduler().runTaskLater(instance, task ->{
                    animation("", "mistery", "2", 73, false);

                }, 97);

            }break;

            case "MAGES":{
                animation("", "ruedita", ruleta, 97, false);

                Bukkit.getScheduler().runTaskLater(instance, task ->{
                    animation("", "mistery", "7", 73, false);
                    
                }, 97);

            }break;

            case "SPIDERS":{
                animation("", "ruedita", ruleta, 97, false);

                Bukkit.getScheduler().runTaskLater(instance, task ->{
                    animation("", "mistery", "B", 73, true);
                    
                }, 97);

            }break;

            case "SKELETONS":{
                animation("", "ruedita", ruleta, 97, false);

                Bukkit.getScheduler().runTaskLater(instance, task ->{
                    animation("", "mistery", "5", 73, false);
                    
                }, 97);

            }break;

            case "PIGS":{
                animation("", "ruedita", ruleta, 97, false);

                Bukkit.getScheduler().runTaskLater(instance, task ->{
                    animation("", "mistery", "A", 73, true);
                    
                }, 97);

            }break;

            case "RAIDERS":{
                animation("", "ruedita", ruleta, 97, false);

                Bukkit.getScheduler().runTaskLater(instance, task ->{
                    animation("", "mistery", "C", 73, true);
                    
                }, 97);

            }break;

            case "DEMONS":{
                animation("", "ruedita", ruleta, 97, false);

                Bukkit.getScheduler().runTaskLater(instance, task ->{
                    animation("", "mistery", "8", 73, false);
                    
                }, 97);

            }break;

            case "CREEPERS":{
                animation("", "ruedita", ruleta, 97, false);

                Bukkit.getScheduler().runTaskLater(instance, task ->{
                    animation("", "mistery", "9", 73, false);
                    
                }, 97);

            }break;

            case "REDSTONE":{
                animation("", "ruedita", ruleta, 97, false);

                Bukkit.getScheduler().runTaskLater(instance, task ->{
                    animation("", "mistery", "3", 73, false);
                    
                }, 97);

            }break;

            case "MISTERY":{
                animation("", "ruedita", ruleta, 97, false);

                Bukkit.getScheduler().runTaskLater(instance, task ->{
                    animation("", "mistery", "F", 62, true);
                    
                }, 97);

            }break;

            case "MUDDY":{
                animation("", "ruedita", ruleta, 97, false);

                Bukkit.getScheduler().runTaskLater(instance, task ->{
                    animation("", "mistery", "A", 73, true);
                    
                }, 97);

            }break;

            case "MOOBLOOM":{
                animation("", "ruedita", ruleta, 97, false);

                Bukkit.getScheduler().runTaskLater(instance, task ->{
                    animation("", "mistery", "6", 73, false);
                    
                }, 97);

            }break;
        
            default:
                break;
        }

    }

    public void animation(String text, String sound, String letter, int number, boolean right){

        var chain = PERMADED.newChain();

        var count = 0;
        var character = 92;
        var charac = Character.toString((char)character);
        
        Bukkit.getOnlinePlayers().forEach(p->{
            var loc = p.getLocation();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:" + sound + " master " + p.getName() + " " + loc.getX()
                        + " " + loc.getY() + " " + loc.getZ() + " 1 1");
        });
        
        while (count < number) {

            final var current = count;

            var id = "" + (current <= 9 ? "0" + current : current);
            var code = right ? (charac + "uE" + id + letter) : (charac + "uE" + letter + id);

            chain.delay(1).sync(() -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"" + code + "\"}");

            });
            count++;
        }

        chain.sync(TaskChain::abort).execute();
    }
    


}