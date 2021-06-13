package net.noobsters.core.paper.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.destroystokyo.paper.Title;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.taskchain.TaskChain;
import lombok.NonNull;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.noobsters.core.paper.GameTickEvent;
import net.noobsters.core.paper.PERMADED;

public class RaceListener implements Listener {

    PERMADED instance;
    Location pos1 = new Location(Bukkit.getWorld("RACE"), -110, 18, -29);
    Location pos2 = new Location(Bukkit.getWorld("RACE"), -116, 18, -3);
    boolean checkWin;
    int win = 3;
    HashMap<String, Integer> racers = new HashMap<>();
    HashMap<Integer, String> delay = new HashMap<>();
    List<String> winners = new ArrayList<>();

    RaceListener(PERMADED instance) {
        this.instance = instance;

        instance.getCommandManager().registerCommand(new raceCMD(instance));
    }

    public boolean isInArea(Location point) {

        var cX = pos1.getX() < pos2.getX();
        var cZ = pos1.getZ() < pos2.getZ();

        var minX = cX ? pos1.getX() : pos2.getX();
        var maxX = cX ? pos2.getX() : pos1.getX();

        var minZ = cZ ? pos1.getZ() : pos2.getZ();
        var maxZ = cZ ? pos2.getZ() : pos1.getZ();

        if (point.getX() < minX || point.getZ() < minZ)
            return false;
        if (point.getX() > maxX || point.getZ() > maxZ)
            return false;

        return true;
    }

    //@EventHandler
    public void raceCheck(GameTickEvent e) {
        var game = instance.getGame();
        var difficulty = game.getDifficultyChanges();
        final var second = e.getSecond();

        if (!difficulty.get("race"))
            return;

        Bukkit.getScheduler().runTask(instance, () -> {

            if (delay.containsKey(second)) {
                delay.remove(second);
            }

            Bukkit.getOnlinePlayers().stream().forEach(player -> {

                if (player.getWorld() == Bukkit.getWorld("RACE") && checkWin && isInArea(player.getLocation())) {

                    var name = player.getName();

                    if (!delay.containsValue(name)) {

                        if (!racers.containsKey(name)) {
                            player.playSound(player.getLocation(), Sound.BLOCK_CONDUIT_ACTIVATE, 1, 0.5f);
                            player.playSound(player.getLocation(), Sound.BLOCK_CONDUIT_ATTACK_TARGET, 1, 1);

                            racers.put(name, 1);
                            delay.put(second + 10, name);
                            Bukkit.getOnlinePlayers().forEach(p ->{
                                if(p.getWorld() == Bukkit.getWorld("RACE")){
                                    p.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.LIGHT_PURPLE + name
                                    + ChatColor.DARK_RED + "] " + ChatColor.WHITE + " VUELTA 1/3");
                                }
                            });

                        } else {
                            player.playSound(player.getLocation(), Sound.BLOCK_CONDUIT_ACTIVATE, 1, 0.5f);
                            player.playSound(player.getLocation(), Sound.BLOCK_CONDUIT_ATTACK_TARGET, 1, 1);


                            var racer = racers.get(name);
                            var current = racer + 1;

                            if (current == win) {
                                winners.add(name);

                                var message = "";
                                var size = winners.size();
                                if (size == 1) {
                                    message = ChatColor.GREEN + "1st";

                                } else if (size == 2) {
                                    message = ChatColor.DARK_GREEN + "2nd";

                                } else if (size == 3) {
                                    message = ChatColor.YELLOW + "3rd";

                                } else {
                                    message = ChatColor.RED + "" + size + "th";
                                }

                                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                                player.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(message).bold(true).create())
                                        .build());
                            }

                            racers.put(name, current);
                            delay.put(second + 10, name);

                            Bukkit.getOnlinePlayers().forEach(p ->{
                                if(p.getWorld() == Bukkit.getWorld("RACE")){
                                    p.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.LIGHT_PURPLE + name
                                    + ChatColor.DARK_RED + "] " + ChatColor.WHITE + " VUELTA " + current + "/3");
                                }
                            });
                        }
                    }
                }
            });
        });

    }
    
    @CommandAlias("race")
    @CommandPermission("mod.cmd")
    public class raceCMD extends BaseCommand {

        private @NonNull PERMADED instance;

        public raceCMD(PERMADED instance) {
            this.instance = instance;

        }

        @Subcommand("start")
        public void start(CommandSender sender) {
            var chain = PERMADED.newChain();

            chain.delay(20).sync(() -> {

                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                    p.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(ChatColor.DARK_RED + "30").bold(true).color(ChatColor.GREEN).create())
                                        .build());
                });

            });

            chain.delay(400).sync(() -> {

                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                    p.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(ChatColor.RED + "10").bold(true).color(ChatColor.GREEN).create())
                                        .build());
                });

            });

            chain.delay(20).sync(() -> {

                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                    p.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(ChatColor.RED + "9").bold(true).color(ChatColor.GREEN).create())
                                        .build());
                });

            });

            chain.delay(20).sync(() -> {

                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                    p.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(ChatColor.RED + "8").bold(true).color(ChatColor.GREEN).create())
                                        .build());
                });

            });

            chain.delay(20).sync(() -> {

                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                    p.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(ChatColor.RED + "7").bold(true).color(ChatColor.GREEN).create())
                                        .build());
                });

            });

            chain.delay(20).sync(() -> {

                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                    p.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(ChatColor.RED + "6").bold(true).color(ChatColor.GREEN).create())
                                        .build());
                });

            });

            chain.delay(20).sync(() -> {

                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                    p.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(ChatColor.YELLOW + "5").bold(true).color(ChatColor.GREEN).create())
                                        .build());
                });

            });

            chain.delay(20).sync(() -> {

                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                    p.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(ChatColor.YELLOW + "4").bold(true).color(ChatColor.GREEN).create())
                                        .build());
                });

            });

            chain.delay(20).sync(() -> {

                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                    p.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(ChatColor.DARK_GREEN + "3").bold(true).color(ChatColor.GREEN).create())
                                        .build());
                });

            });

            chain.delay(20).sync(() -> {

                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                    p.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(ChatColor.DARK_GREEN + "2").bold(true).color(ChatColor.GREEN).create())
                                        .build());
                });

            });

            chain.delay(20).sync(() -> {

                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                    p.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(ChatColor.DARK_GREEN + "1").bold(true).color(ChatColor.GREEN).create())
                                        .build());
                });

            });

            chain.delay(20).sync(() -> {

                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);

                    p.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(ChatColor.GREEN + "GO!").bold(true).color(ChatColor.GREEN).create())
                                        .build());
                });

            });

            chain.delay(40).sync(() -> {

                Bukkit.getOnlinePlayers().forEach(p -> {

                    p.sendTitle(Title.builder().title("").subtitle(
                                        new ComponentBuilder(ChatColor.DARK_GREEN + "").bold(true).color(ChatColor.GREEN).create())
                                        .build());
                });

            });

            chain.sync(TaskChain::abort).execute();
        }

        @Subcommand("pos1")
        public void pos1(Player sender) {
            var loc = sender.getLocation();
            pos1 = new Location(Bukkit.getWorld("RACE"), loc.getX(), loc.getY(), loc.getZ());
            sender.sendMessage(ChatColor.YELLOW + "POS1 SET");
        }

        @Subcommand("pos2")
        public void pos2(Player sender) {
            var loc = sender.getLocation();
            pos2 = new Location(Bukkit.getWorld("RACE"), loc.getX(), loc.getY(), loc.getZ());
            sender.sendMessage(ChatColor.YELLOW + "POS2 SET");
        }

        @Subcommand("winners-list")
        public void winnerschat(Player sender) {

            for (int i = 0; i < winners.size(); i++) {

                var message = "";
                var size = i+1;
                if (size == 1) {
                    message = "1st";

                } else if (size == 2) {
                    message = "2nd";

                } else if (size == 3) {
                    message = "3rd";

                } else {
                    message = size + "th";
                }

                Bukkit.broadcastMessage(ChatColor.GOLD + message + ": "+ ChatColor.GREEN + winners.get(i));
            }
        }

        @Subcommand("win-lap")
        public void winnerschat(Player sender, Integer lap) {

            win = lap;
            sender.sendMessage(ChatColor.YELLOW + "NEW WIN LAP " + lap);
        }

        @Subcommand("check-win")
        public void checkwin(Player sender) {

            checkWin = !checkWin;
            sender.sendMessage(ChatColor.YELLOW + "CHECK WIN " + checkWin);
        }

        @Subcommand("clear-race")
        public void clearwinners(CommandSender sender) {

            winners.clear();
            racers.clear();
            delay.clear();
            sender.sendMessage(ChatColor.YELLOW + "RACE CLEARED");
        }
    }

}