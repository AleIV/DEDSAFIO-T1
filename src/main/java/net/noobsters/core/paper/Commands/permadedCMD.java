package net.noobsters.core.paper.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Flags;
import co.aikar.commands.annotation.Subcommand;
import fr.mrmicky.fastinv.ItemBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

@RequiredArgsConstructor
@CommandPermission("dedsafio.cmd")
@CommandAlias("dedsafio|ded")
public class permadedCMD extends BaseCommand {

    private @NonNull PERMADED instance;

    @Subcommand("difficulty")
    public void difficultyChange(CommandSender sender, String change, boolean bool) {
        var difficulty = instance.getGame().getDifficultyChanges();
        if(!difficulty.containsKey(change)){
            sender.sendMessage(ChatColor.RED + "Difficulty change doesn't exist.");
        }
        instance.getGame().getDifficultyChanges().put(change, bool);
        sender.sendMessage(ChatColor.GREEN + "Difficulty change " + change + " set to " + bool);
    }

    @Subcommand("zombiePlayer")
    @CommandAlias("zombiePlayer")
    public void zombiePlayer(CommandSender sender, String player, boolean bool) {
        var players = instance.getGame().getDeathPlayers();
        if(!players.containsKey(player)){
            sender.sendMessage(ChatColor.RED + "Player change doesn't exist.");
        }
        players.put(player, bool);
        sender.sendMessage(ChatColor.GREEN + "Player zombie " + player + " set to " + bool);
    }

    @Subcommand("pvp-on")
    @CommandAlias("pvp-on")
    public void pvp(CommandSender sender, @Flags("other") Player player, boolean bool) {
        var pvp = instance.getGame().getPvpOn();
        
        if(bool){
            pvp.add(player.getUniqueId().toString());
            sender.sendMessage(ChatColor.GREEN + "PvP " + player + " set to " + bool);
        }else{
            pvp.remove(player.getUniqueId().toString());
            sender.sendMessage(ChatColor.RED + "PvP " + player + " set to " + bool);
        }

    }

    @Subcommand("gulag")
    @CommandAlias("gulag")
    public void gulag(CommandSender sender) {
        var game = instance.getGame();
        var bool = !game.isGulak();
        game.setGulak(!game.isGulak());
        sender.sendMessage(ChatColor.GREEN + "Gulag set to " + bool);
    }

    @Subcommand("difficulty-list")
    @CommandAlias("difficulty-list")
    public void difList(CommandSender sender) {
        var game = instance.getGame();
        sender.sendMessage(ChatColor.AQUA + "LIST: " + game.getDifficultyChanges().toString());
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

    @Subcommand("music")
    @CommandAlias("music")
    public void music(Player sender, String music){
        var loc = sender.getLocation();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:" + music + " master @a " + loc.getX()
                        + " " + loc.getY() + " " + loc.getZ() + " 0.3 1");
        sender.sendMessage(ChatColor.AQUA + "Music played");
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

    @Subcommand("redstone")
    @CommandAlias("redstone")
    public void disguise(Player sender) {

        var name = "&4Redstone Monstrosity";
        Bukkit.dispatchCommand(sender, "disguise ravager setcustomname \""+ name +"\" setcustomnamevisible false setSelfDisguiseVisible false");

        var melee = new ItemBuilder(Material.NETHERITE_SWORD).name(ChatColor.GOLD + "Melee").build();

        var shootFireball = new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_INFINITE).name(ChatColor.GOLD + "Shoot Fireball").build();

        var explosion = new ItemBuilder(Material.GUNPOWDER).name(ChatColor.GOLD + "Explosion").build();

        var walk = new ItemBuilder(Material.NETHERITE_BOOTS).name(ChatColor.GREEN + "Walk").build();

        var speed = new ItemBuilder(Material.DIAMOND_BOOTS).name(ChatColor.GREEN + "Speed").build();

        var jump = new ItemBuilder(Material.IRON_BOOTS).name(ChatColor.GREEN + "Jump").build();

        var roar = new ItemBuilder(Material.GLOWSTONE_DUST).name(ChatColor.GREEN + "Roar").build();

        var laugh = new ItemBuilder(Material.SUGAR).name(ChatColor.GREEN + "Laugh").build();

        var inv = sender.getInventory();
        inv.addItem(melee);
        inv.addItem(shootFireball);
        inv.addItem(explosion);

        inv.addItem(walk);
        inv.addItem(speed);
        inv.addItem(jump);
        inv.addItem(roar);
        inv.addItem(laugh);

        inv.addItem(new ItemStack(Material.ARROW));

        var helmet = new ItemBuilder(Material.NETHERITE_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build();
        var chestplate = new ItemBuilder(Material.NETHERITE_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build();
        var leggings = new ItemBuilder(Material.NETHERITE_LEGGINGS).enchant(Enchantment.PROTECTION_PROJECTILE, 4).build();
        var boots = new ItemBuilder(Material.NETHERITE_BOOTS).enchant(Enchantment.PROTECTION_PROJECTILE, 4).build();

        var equip = sender.getEquipment();
        equip.setHelmet(helmet);
        equip.setChestplate(chestplate);
        equip.setLeggings(leggings);
        equip.setBoots(boots);

        sender.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(60.0);
        sender.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(60.0);
        sender.setHealth(60);
        sender.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000, 5, false, false));
        sender.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000, 3, false, false));

        sender.setGameMode(GameMode.SURVIVAL);

        sender.sendMessage(ChatColor.GOLD + "Disquised as Redstone Monstrosity");
    }

    @Subcommand("disguise")
    public void disguiseCustom(Player sender, EntityType entity, String name) {

        Bukkit.dispatchCommand(sender, "disguise " + entity + " setcustomname \""+ name +"\" setcustomnamevisible false setSelfDisguiseVisible false");

        sender.setGameMode(GameMode.SURVIVAL);

        sender.sendMessage(ChatColor.GOLD + "Disquised as " + name);
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



}