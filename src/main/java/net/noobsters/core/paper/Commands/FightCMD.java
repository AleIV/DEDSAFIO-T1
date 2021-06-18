package net.noobsters.core.paper.Commands;

import java.util.Random;
import java.util.UUID;

import com.destroystokyo.paper.Title;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Flags;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.taskchain.TaskChain;
import fr.mrmicky.fastinv.ItemBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.noobsters.core.paper.PERMADED;

@RequiredArgsConstructor
@CommandPermission("dedsafio.cmd")
@CommandAlias("fight")
public class FightCMD extends BaseCommand {

    private @NonNull PERMADED instance;
    static public String KICK_MSG = ChatColor.AQUA + "GG\n";
    Random random = new Random();

    @Subcommand("pvp-on")
    @CommandAlias("pvp-on")
    @CommandCompletion("@players")
    public void pvp(CommandSender sender, @Flags("other") Player player, boolean bool) {
        var pvp = instance.getGame().getPvpOn();

        if (bool) {
            pvp.add(player.getUniqueId().toString());
            sender.sendMessage(ChatColor.GREEN + "PvP " + player + " set to " + bool);
        } else {
            pvp.remove(player.getUniqueId().toString());
            sender.sendMessage(ChatColor.RED + "PvP " + player + " set to " + bool);
        }

    }

    @Subcommand("pvp-all")
    @CommandAlias("pvp-all")
    public void pvpall(CommandSender sender) {
        var fight = instance.getGame().getFighters();
        var pvp = instance.getGame().getPvpOn();

        pvp.clear();
        Bukkit.getOnlinePlayers().forEach(player -> {
            var uuid = player.getUniqueId().toString();
            if (fight.contains(uuid)) {
                pvp.add(uuid);
            }
        });

        sender.sendMessage(ChatColor.BLUE + "" + fight.size() + " Fighters pvp enabled.");
    }

    @Subcommand("spec-fighters")
    @CommandAlias("spec-fighters")
    public void specfighters(CommandSender sender) {
        var fight = instance.getGame().getFighters();
        var pvp = instance.getGame().getPvpOn();

        pvp.clear();
        Bukkit.getOnlinePlayers().forEach(player -> {
            var uuid = player.getUniqueId().toString();
            if (fight.contains(uuid)) {
                player.setGameMode(GameMode.SPECTATOR);
            }
        });

        sender.sendMessage(ChatColor.BLUE + "" + fight.size() + " Fighters spec enabled.");
    }

    @Subcommand("surv-fighters")
    @CommandAlias("surv-fighters")
    public void survfighters(CommandSender sender) {
        var fight = instance.getGame().getFighters();
        var pvp = instance.getGame().getPvpOn();

        pvp.clear();
        Bukkit.getOnlinePlayers().forEach(player -> {
            var uuid = player.getUniqueId().toString();
            if (fight.contains(uuid)) {
                player.setGameMode(GameMode.SURVIVAL);
            }
        });

        sender.sendMessage(ChatColor.BLUE + "" + fight.size() + " Fighters survival enabled.");
    }

    @Subcommand("tpall-fighters")
    @CommandAlias("tpall-fighters")
    public void tp(Player sender) {
        var fight = instance.getGame().getFighters();

        Bukkit.getOnlinePlayers().forEach(player -> {
            var uuid = player.getUniqueId().toString();
            if (fight.contains(uuid)) {
                player.teleport(sender.getLocation());
            }
        });

        sender.sendMessage(ChatColor.BLUE + "" + fight.size() + " Fighters teleport.");
    }

    @Subcommand("fighter-add")
    @CommandAlias("fighter-add")
    @CommandCompletion("@players")
    public void fighteradd(CommandSender sender, @Flags("other") Player player, boolean bool) {
        var fight = instance.getGame().getFighters();

        var uuid = player.getUniqueId().toString();
        if (bool) {
            if(!fight.contains(uuid)){
                fight.add(player.getUniqueId().toString());
                sender.sendMessage(ChatColor.GREEN + "Fighter " + player + " set to " + bool);
            }else{
                sender.sendMessage(ChatColor.RED + "CANT ADD");
            }
        } else {
            fight.remove(player.getUniqueId().toString());
            sender.sendMessage(ChatColor.RED + "Fighter " + player + " set to " + bool);
        }

        sender.sendMessage(ChatColor.YELLOW + "" + fight.size() + " Fighters added.");

    }

    @Subcommand("tnt-head")
    @CommandAlias("tnt-head")
    @CommandCompletion("@players")
    public void fighteradd(CommandSender sender, @Flags("other") Player player) {
        var game = instance.getGame();
        player.getEquipment().setHelmet(game.getTnt());
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20*1000, 0));
        sender.sendMessage(ChatColor.YELLOW + "TNT head " + player.getName());

    }

    @Subcommand("tnt-explode")
    @CommandAlias("tnt-explode")
    public void explode(CommandSender sender) {
        var chain = PERMADED.newChain();

        chain.delay(20).sync(() -> {
            Bukkit.getOnlinePlayers().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1, 1);
                p.sendTitle(Title.builder().title("")
                        .subtitle(new ComponentBuilder("10").bold(true).color(ChatColor.GREEN).create()).build());
            });

        });

        chain.delay(20).sync(() -> {
            Bukkit.getOnlinePlayers().forEach(p -> {
                p.sendTitle(Title.builder().title("")
                        .subtitle(new ComponentBuilder("").bold(true).color(ChatColor.GREEN).create()).build());
            });

        });

        chain.delay(80).sync(() -> {
            Bukkit.getOnlinePlayers().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1, 1);
                p.sendTitle(Title.builder().title("")
                        .subtitle(new ComponentBuilder("5").bold(true).color(ChatColor.GREEN).create()).build());
            });

        });

        chain.delay(20).sync(() -> {
            Bukkit.getOnlinePlayers().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1, 1);
                p.sendTitle(Title.builder().title("")
                        .subtitle(new ComponentBuilder("4").bold(true).color(ChatColor.GREEN).create()).build());
            });

        });

        chain.delay(20).sync(() -> {
            Bukkit.getOnlinePlayers().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1, 1);
                p.sendTitle(Title.builder().title("")
                        .subtitle(new ComponentBuilder("3").bold(true).color(ChatColor.GREEN).create()).build());
            });

        });

        chain.delay(20).sync(() -> {
            Bukkit.getOnlinePlayers().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1, 1);
                p.sendTitle(Title.builder().title("")
                        .subtitle(new ComponentBuilder("2").bold(true).color(ChatColor.GREEN).create()).build());
            });

        });

        chain.delay(20).sync(() -> {
            Bukkit.getOnlinePlayers().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1, 1);
                p.sendTitle(Title.builder().title("")
                        .subtitle(new ComponentBuilder("1").bold(true).color(ChatColor.GREEN).create()).build());
            });

        });

        chain.delay(20).sync(() -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                var helmet = player.getEquipment().getHelmet();
                player.sendTitle(Title.builder().title("").subtitle(new ComponentBuilder("").create()).build());
                if (helmet != null && helmet.getItemMeta().getDisplayName() != null && helmet.getItemMeta().getDisplayName().contains("TNT")) {
                    player.setHealth(1);
                    player.getLocation().createExplosion(1, false, false);
                    player.getWorld().strikeLightning(player.getLocation());
                }
            });

        });

        chain.sync(TaskChain::abort).execute();

        sender.sendMessage(ChatColor.YELLOW + "TNT explode!");

    }

    @Subcommand("kit")
    @CommandAlias("kit")
    @CommandCompletion("@players")
    public void kitAdd(CommandSender sender, @Flags("other") Player player) {
        addKit(player);
    }

    @Subcommand("fight-list")
    @CommandAlias("fight-list")
    public void list(CommandSender sender) {
        var fight = instance.getGame().getFighters();
        sender.sendMessage(ChatColor.YELLOW + "" + fight.size() + " Fighters list.");
    }

    public void addKit(Player player) {
        var equip = player.getEquipment();
        var inv = player.getInventory();
        inv.clear();

        var head = new ItemBuilder(Material.PLAYER_HEAD)
                    .meta(SkullMeta.class, meta -> meta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId())))
                    .build();
        var sumo = new ItemBuilder(Material.JIGSAW).name(ChatColor.RED + "Sumo").build();
        equip.setItemInOffHand(sumo);
        equip.setHelmet(head);

        /*
        inv.addItem(new ItemStack(Material.DIAMOND_SWORD));
        inv.addItem(new ItemStack(Material.BOW));
        inv.addItem(new ItemStack(Material.IRON_AXE));
        inv.addItem(new ItemStack(Material.GOLDEN_APPLE));
        inv.addItem(new ItemStack(Material.ARROW, 8));
        inv.addItem(new ItemStack(Material.BAKED_POTATO, 8));

        equip.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        equip.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        equip.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        equip.setBoots(new ItemStack(Material.DIAMOND_BOOTS));*/
    }

    @Subcommand("fight-random")
    @CommandAlias("fight-random")
    @CommandCompletion("@players")
    public void randomfight(CommandSender sender) {
        var fighters = instance.getGame().getFighters();
        var pvp = instance.getGame().getPvpOn();

        if (fighters.size() >= 2) {
            var player1 = fighters.get(0);
            var player2 = fighters.get(1);

            fighters.remove(player1);
            fighters.remove(player2);

            var chain = PERMADED.newChain();

            var p1 = Bukkit.getPlayer(UUID.fromString(player1));
            var p2 = Bukkit.getPlayer(UUID.fromString(player2));

            p1.setGameMode(GameMode.SURVIVAL);
            p2.setGameMode(GameMode.SURVIVAL);

            addKit(p1);
            addKit(p2);

            p1.teleport(new Location(Bukkit.getWorld("world"), -510, 200, -263, 90, 0));
            p2.teleport(new Location(Bukkit.getWorld("world"), -554, 200, -263, -90, 0));

            Bukkit.getOnlinePlayers().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_CONDUIT_ACTIVATE, 1, 1);
                p.sendTitle(
                        Title.builder().title("").subtitle(new ComponentBuilder(p1.getName() + " VS " + p2.getName())
                                .bold(true).color(ChatColor.DARK_RED).create()).build());
            });

            
            chain.delay(80).sync(() -> {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1, 1);
                    p.sendTitle(Title.builder().title("")
                            .subtitle(new ComponentBuilder("3").bold(true).color(ChatColor.GREEN).create()).build());
                });

            });

            chain.delay(20).sync(() -> {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1, 1);
                    p.sendTitle(Title.builder().title("")
                            .subtitle(new ComponentBuilder("2").bold(true).color(ChatColor.GREEN).create()).build());
                });

            });

            chain.delay(20).sync(() -> {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1, 1);
                    p.sendTitle(Title.builder().title("")
                            .subtitle(new ComponentBuilder("1").bold(true).color(ChatColor.GREEN).create()).build());
                });

            });



            chain.delay(20).sync(() -> {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.ENTITY_RAVAGER_ROAR, 1, 1);
                    p.sendTitle(Title.builder().title("")
                            .subtitle(new ComponentBuilder("GO!").bold(true).color(ChatColor.AQUA).create()).build());
                    pvp.add(player1);
                    pvp.add(player2);
                });

            });

            chain.delay(20).sync(() -> {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.sendTitle(Title.builder().title("")
                            .subtitle(new ComponentBuilder("").bold(true).color(ChatColor.AQUA).create()).build());
                });

            });

            chain.sync(TaskChain::abort).execute();

        } else {
            sender.sendMessage(ChatColor.RED + "Not enough fighters.");
        }

    }

    @Subcommand("add-fighters")
    @CommandAlias("add-fighters")
    public void addFighter(CommandSender sender) {
        var fight = instance.getGame().getFighters();
        fight.clear();
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (!player.hasPermission("mod.perm") && player.getGameMode() == GameMode.SPECTATOR) {
                fight.add(player.getUniqueId().toString());
            }
        });

        sender.sendMessage(ChatColor.YELLOW + "" + fight.size() + " Fighters added.");

    }

    @Subcommand("gulag")
    @CommandAlias("gulag")
    public void gulag(CommandSender sender) {
        var game = instance.getGame();
        var bool = !game.isGulak();

        if (!bool) {
            var chain = PERMADED.newChain();

            Bukkit.getOnlinePlayers().forEach(p -> {
                chain.delay(1).sync(() -> {
                    if (p.getGameMode() == GameMode.SPECTATOR && !p.hasPermission("mod.perm")) {
                        p.kickPlayer(KICK_MSG);
                    }

                });
            });

            chain.sync(TaskChain::abort).execute();

        }

        game.setGulak(!game.isGulak());
        sender.sendMessage(ChatColor.GREEN + "Gulag set to " + bool);
    }

}