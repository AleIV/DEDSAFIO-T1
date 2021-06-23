package net.noobsters.core.paper.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.taskchain.TaskChain;
import lombok.NonNull;
import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

@CommandAlias("ruedita")
@CommandPermission("tpworld.cmd")
public class worldCMD extends BaseCommand {

    private @NonNull PERMADED instance;
    Random random = new Random();
    List<String> letters = new ArrayList<>();

    String item1 = "uE2A8";
    String item2 = "uE1A4";
    String item3 = "uE1A3";

    String item4 = "uE0A3";
    String item5 = "uE1A3";

    public worldCMD(PERMADED instance) {
        this.instance = instance;
        letters.add("A"); // ROJO
        letters.add("B"); // NARANJA
        letters.add("C"); // AMARILLO
        letters.add("D"); // VERDE
        letters.add("E"); // CELESTE
        letters.add("F"); // AZUL
        letters.add("0"); // MORADO
        letters.add("1"); // ROSA

    }

    @Subcommand("say")
    @CommandAlias("say")
    public void say(CommandSender sender, String text) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[&4&lDEDSAFIO&6&l] &f" + text));
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 0.5f);
        });

    }

    @Subcommand("kill-entity")
    @CommandAlias("kill-entity")
    public void killent(CommandSender sender) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=minecraft:parrot]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=minecraft:horse]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=minecraft:strider]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=minecraft:sheep]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=minecraft:chicken]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=minecraft:pig]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=minecraft:polar_bear]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=minecraft:cow]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=minecraft:llama]");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=minecraft:rabbit]");

    }

    @Subcommand("place-gulag")
    @CommandAlias("place-gulag")
    public void placegulag(CommandSender sender) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock -496 153 -246 minecraft:redstone_block");
        sender.sendMessage(ChatColor.AQUA + "GULAG PLACED");

    }

    @Subcommand("remove-gulag")
    @CommandAlias("remove-gulag")
    public void removegulag(CommandSender sender) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock -500 153 -246 minecraft:redstone_block");
        sender.sendMessage(ChatColor.AQUA + "GULAG REMOVED");

    }

    public void ruedita() {
        var ruleta = letters.get(random.nextInt(letters.size()));
        instance.animation("", "ruedita", ruleta, 97, false);
    }

    @Subcommand("animation")
    @CommandAlias("animation")
    public void animationcmd2(Player player, String type, String textChange) {

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a times 0 1 60");

        switch (type) {

            case "CRAFT": {
                ruedita();

                Bukkit.getScheduler().runTaskLater(instance, task -> {

                    var chain = PERMADED.newChain();

                    var character = 92;
                    var charac = Character.toString((char)character);

                    chain.delay(20).sync(() -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                "title @a title {\"text\":\"" + charac + item1 + "\"}");

                        Bukkit.getOnlinePlayers().forEach(p -> {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 0.5f);
                        });

                    });

                    chain.delay(20).sync(() -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                "title @a title {\"text\":\"" + charac + item2 + "\"}");
                        
                        Bukkit.getOnlinePlayers().forEach(p -> {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 0.5f);
                        });
                    });

                    chain.delay(20).sync(() -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                "title @a title {\"text\":\"" + charac + item3 + "\"}");
                        
                        Bukkit.getOnlinePlayers().forEach(p -> {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 0.5f);
                        });
                    });

                    /*chain.delay(20).sync(() -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                "title @a title {\"text\":\"" + charac + item4 + "\"}");
                        
                        Bukkit.getOnlinePlayers().forEach(p -> {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 0.5f);
                        });
                    });*/

                    chain.delay(20).sync(() -> {

                        /*Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                "title @a title {\"text\":\"" + charac + item5 + "\"}");*/
                        Bukkit.broadcastMessage("");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                "tellraw @a {\"text\":\"" + charac + item1 + " x1" + "\"}");
                        Bukkit.broadcastMessage("");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                "tellraw @a {\"text\":\"" + charac + item2 + " x8" + "\"}");
                        Bukkit.broadcastMessage("");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                "tellraw @a {\"text\":\"" + charac + item3 + " x64" + "\"}");
                        Bukkit.broadcastMessage("");

                        /*Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                "tellraw @a {\"text\":\"" + charac + item4 + " x64" + "\"}");
                        Bukkit.broadcastMessage("");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                "tellraw @a {\"text\":\"" + charac + item5 + " x64" + "\"}");*/
                                
                            
                        Bukkit.getOnlinePlayers().forEach(p -> {
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 0.5f);
                        });
                    });


                    chain.sync(TaskChain::abort).execute();

                }, 97);

            }
                break;

            case "ZOMBIES": {
                ruedita();

                Bukkit.getScheduler().runTaskLater(instance, task -> {
                    instance.animation("", "mistery", "2", 73, false);

                }, 97);

            }
                break;

            case "MAGES": {
                ruedita();

                Bukkit.getScheduler().runTaskLater(instance, task -> {
                    instance.animation("", "mistery", "7", 73, false);

                }, 97);

            }
                break;

            case "SPIDERS": {
                ruedita();

                Bukkit.getScheduler().runTaskLater(instance, task -> {
                    instance.animation("", "mistery", "B", 73, true);

                }, 97);

            }
                break;

            case "SKELETONS": {
                ruedita();

                Bukkit.getScheduler().runTaskLater(instance, task -> {
                    instance.animation("", "mistery", "5", 73, false);

                }, 97);

            }
                break;

            case "PIGS": {
                ruedita();

                Bukkit.getScheduler().runTaskLater(instance, task -> {
                    instance.animation("", "mistery", "4", 73, false);

                }, 97);

            }
                break;

            case "RAIDERS": {
                ruedita();

                Bukkit.getScheduler().runTaskLater(instance, task -> {
                    instance.animation("", "mistery", "C", 73, true);

                }, 97);

            }
                break;

            case "DEMONS": {
                ruedita();

                Bukkit.getScheduler().runTaskLater(instance, task -> {
                    instance.animation("", "mistery", "8", 73, false);

                }, 97);

            }
                break;

            case "CREEPERS": {
                ruedita();

                Bukkit.getScheduler().runTaskLater(instance, task -> {
                    instance.animation("", "mistery", "9", 73, false);

                }, 97);

            }
                break;

            case "REDSTONE": {
                ruedita();

                Bukkit.getScheduler().runTaskLater(instance, task -> {
                    instance.animation("", "mistery", "3", 73, false);

                }, 97);

            }
                break;

            case "MISTERY": {
                ruedita();

                Bukkit.getScheduler().runTaskLater(instance, task -> {
                    instance.animation("", "mistery", "F", 62, true);

                }, 97);

            }
                break;

            case "CHANGE": {
                ruedita();

            }break;

            case "MUDDY": {
                ruedita();

                Bukkit.getScheduler().runTaskLater(instance, task -> {
                    instance.animation("", "mistery", "A", 73, true);

                }, 97);

            }
                break;

            case "MOOBLOOM": {
                ruedita();

                Bukkit.getScheduler().runTaskLater(instance, task -> {
                    instance.animation("", "mistery", "6", 73, false);

                }, 97);

            }
                break;

            default:
                break;
        }

        Bukkit.getScheduler().runTaskLater(instance, task -> {
            Bukkit.broadcastMessage(
                    ChatColor.translateAlternateColorCodes('&', "&6&l[&4&lDEDSAFIO&6&l] &f" + textChange));

            Bukkit.getOnlinePlayers().forEach(p -> {
                player.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 0.5f);

            });

        }, 97);

    }

    @Subcommand("jsoncreate")
    @CommandAlias("jsoncreate")
    public void json(CommandSender sender, String letter, int number, boolean right) {

        var character = 92;
        var charac = Character.toString((char) character);

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

    @Subcommand("tpworld")
    @CommandAlias("tpworld")
    @CommandPermission("tpworld.cmd")
    @CommandCompletion("@worlds")
    public void tpWorld(Player sender, World world) {
        sender.teleport(world.getSpawnLocation());
        sender.sendMessage("Teleported to world " + world);
    }

}