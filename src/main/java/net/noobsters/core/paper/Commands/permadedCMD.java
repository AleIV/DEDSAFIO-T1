package net.noobsters.core.paper.Commands;

import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Flags;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.taskchain.TaskChain;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

@RequiredArgsConstructor
@CommandPermission("dedsafio.cmd")
@CommandAlias("dedsafio|ded")
public class permadedCMD extends BaseCommand {

    private @NonNull PERMADED instance;
    static public String KICK_MSG = ChatColor.AQUA + "GG\n";
    Random random = new Random();

    @Subcommand("closed")
    @CommandAlias("closed")
    public void closed(CommandSender sender, boolean bool) {
        var game = instance.getGame();

        if(bool){
            var chain = PERMADED.newChain();
        
            Bukkit.getOnlinePlayers().forEach(p->{
                chain.delay(1).sync(() -> {
                    p.kickPlayer(KICK_MSG);

                });
            });
        

            chain.sync(TaskChain::abort).execute();

        }
        game.setClosed(bool);
        sender.sendMessage(ChatColor.GREEN + "Closed set to " + bool);
    }

    @Subcommand("tp-here-all")
    public void here(Player sender) {

        var chain = PERMADED.newChain();
        
            Bukkit.getOnlinePlayers().forEach(p->{
                chain.delay(1).sync(() -> {
                    p.teleport(sender.getLocation());

                });
            });
        

            chain.sync(TaskChain::abort).execute();

        sender.sendMessage(ChatColor.GREEN + "TP ALL HERE");
    }

    @Subcommand("difficulty")
    public void difficultyChange(CommandSender sender, String change, boolean bool) {
        var difficulty = instance.getGame().getDifficultyChanges();
        if(!difficulty.containsKey(change)){
            sender.sendMessage(ChatColor.RED + "Difficulty change doesn't exist.");
        }
        instance.getGame().getDifficultyChanges().put(change, bool);
        sender.sendMessage(ChatColor.GREEN + "Difficulty change " + change + " set to " + bool);
    }

    @Subcommand("refresh")
    public void refresh(Player player) {
        var golems = player.getNearbyEntities(32, 100, 32).stream().filter(golem -> 
        golem instanceof IronGolem).map(e -> (IronGolem)e).collect(Collectors.toList());

        var loc = player.getLocation();
        var players = loc.getNearbyPlayers(64, p-> p.getGameMode() == GameMode.SURVIVAL).stream().collect(Collectors.toList());
        
        if(!players.isEmpty() && !golems.isEmpty()) {
            golems.forEach(golem ->{
                golem.setTarget(players.get(random.nextInt(players.size()-1)));
            });
        }
    }

    @Subcommand("revive")
    @CommandAlias("revive")
    @CommandCompletion("@players")
    public void revive(CommandSender sender, @Flags("other") Player player, boolean bool) {
        var revive = instance.getGame().getReviveList();
        
        if(bool){
            revive.add(player.getName());
            sender.sendMessage(ChatColor.GREEN + "Revive " + player + " set to " + bool);
        }else{
            revive.remove(player.getName());
            sender.sendMessage(ChatColor.RED + "Revive " + player + " set to " + bool);
        }

    }

    @Subcommand("difficulty-list")
    @CommandAlias("difficulty-list")
    public void difList(CommandSender sender) {
        var game = instance.getGame();
        sender.sendMessage(ChatColor.AQUA + "LIST: " + game.getDifficultyChanges().toString());
    }

    @Subcommand("fire-all")
    @CommandAlias("fire-all")
    public void fireall(CommandSender sender) {
        Bukkit.getOnlinePlayers().forEach(player ->{
            player.setFireTicks(30*20);
        });
    }

    @Subcommand("fix-light-fill")
    @CommandAlias("fix-light-fill")
    public void fixfill(Player sender) {
        Bukkit.dispatchCommand(sender, "fill ~15 ~5 ~15 ~-20 ~-10 ~-20 minecraft:sea_lantern replace air");
        Bukkit.getScheduler().runTaskLater(instance, task ->{
            Bukkit.dispatchCommand(sender, "fill ~15 ~5 ~15 ~-20 ~-10 ~-20 air replace minecraft:sea_lantern");
        }, 20);
    }

    @Subcommand("resistance")
    @CommandAlias("resistance")
    public void resistanceChange(CommandSender sender, int change) {
        instance.getGame().setMobResistance(change);
        sender.sendMessage(ChatColor.GREEN + "Resistance set to " + change);
    }

    @Subcommand("damage")
    @CommandAlias("damage")
    public void damageChange(CommandSender sender, int change) {
        instance.getGame().setDamageAmplifier(change);
        sender.sendMessage(ChatColor.GREEN + "Damage amplifier set to " + change);
    }
    
    @Subcommand("spawn-patrol-delay")
    @CommandAlias("spawn-patrol-delay")
    public void patrol(CommandSender sender, int change) {
        instance.getGame().setSpawnPatrolDelay(change);
        sender.sendMessage(ChatColor.GREEN + "Spawn patrol delay set to " + change);
    }

    @Subcommand("summon")
    public void summon(Player sender, EntityType entity, String summon) {
        var loc = sender.getLocation();
        var mob = sender.getWorld().spawnEntity(loc, entity);
        Bukkit.getScheduler().runTaskLater(instance, task ->{
            mob.setCustomName(summon);
        }, 2);
        
        sender.sendMessage(ChatColor.AQUA + "Summoned " + summon);
    }

    @Subcommand("car")
    public void disguiseCustom(Player sender, String name) {

        var horse = (Horse) sender.getWorld().spawnEntity(sender.getLocation(), EntityType.HORSE);

        horse.setCustomName(ChatColor.LIGHT_PURPLE + name);

        horse.clearLootTable();
        horse.setAdult();
        horse.setJumpStrength(1);
        horse.setDomestication(horse.getMaxDomestication());
        horse.setSilent(true);
        horse.setAI(false);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.setColor(Color.BROWN);
        horse.setStyle(Style.NONE);

        horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
        horse.setHealth(40);

        horse.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10000, 4));

        sender.sendMessage(ChatColor.BLUE + "New car " + name);
    }

    @Subcommand("scale")
    public void scaleDragon(Player sender) {

        var stand = (ArmorStand) sender.getWorld().spawnEntity(sender.getLocation(), EntityType.ARMOR_STAND);

        stand.setCustomName(ChatColor.RED + "Dragon Scale");

        sender.sendMessage(ChatColor.BLUE + "Blood Scale ");
    }

    @Subcommand("blind-stand")
    public void blind(Player sender) {

        var stand = (ArmorStand) sender.getWorld().spawnEntity(sender.getLocation(), EntityType.ARMOR_STAND);

        stand.setCustomName("blind");
        stand.setInvisible(true);

        sender.sendMessage(ChatColor.BLUE + "Blind stand ");
    }



}