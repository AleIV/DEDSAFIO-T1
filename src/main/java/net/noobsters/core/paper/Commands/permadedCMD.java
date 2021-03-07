package net.noobsters.core.paper.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import fr.mrmicky.fastinv.ItemBuilder;
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
        instance.getGame().setMobResistance(change);
        sender.sendMessage(ChatColor.GREEN + "Resistance set to " + change);
    }

    @Subcommand("damage")
    public void damageChange(CommandSender sender, int change) {
        instance.getGame().setDamageAmplifier(change);
        sender.sendMessage(ChatColor.GREEN + "Damage amplifier set to " + change);
    }
    @Subcommand("spawn-patrol-delay")
    public void patrol(CommandSender sender, int change) {
        instance.getGame().setSpawnPatrolDelay(change);
        sender.sendMessage(ChatColor.GREEN + "Spawn patrol delay set to " + change);
    }

    @Subcommand("final-fantasy")
    public void music(Player sender) {
        var loc = sender.getLocation();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:final_fantasy master @a " + loc.getX()
                        + " " + loc.getY() + " " + loc.getZ() + " 6 1");
    }

    @Subcommand("redstone")
    public void disguise(Player sender) {
        /*
        MobDisguise mobDisguise = new MobDisguise(DisguiseType.RAVAGER);
        mobDisguise.setCustomDisguiseName(true);
        mobDisguise.setDisguiseName(ChatColor.DARK_RED + "Redstone Monstrosity");
        mobDisguise.setSelfDisguiseVisible(false);
        mobDisguise.setEntity(sender);
        mobDisguise.startDisguise();*/
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
        sender.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000, 14, false, false));
        sender.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000, 3, false, false));

        sender.setGameMode(GameMode.SURVIVAL);



        sender.sendMessage(ChatColor.GOLD + "Disquised as Redstone Monstrosity");
    }

}