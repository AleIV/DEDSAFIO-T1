package net.noobsters.core.paper.Commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
@CommandPermission("dedsafio.cmd")
@CommandAlias("disguise-ded")
public class disguiseCMD extends BaseCommand {

    private @NonNull PERMADED instance;
    static public String KICK_MSG = ChatColor.AQUA + "GG\n";
    Random random = new Random();

    @Subcommand("redstone")
    @CommandAlias("redstone")
    public void disguise(Player sender) {

        var name = "&4Redstone Monstrosity";
        Bukkit.dispatchCommand(sender, "disguise ravager setcustomname \""+ name +"\" setcustomnamevisible false setSelfDisguiseVisible false");

        var melee = new ItemBuilder(Material.NETHERITE_SWORD).name(ChatColor.GOLD + "Melee").meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();

        var shootFireball = new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_INFINITE).meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).name(ChatColor.GOLD + "Shoot Fireball").build();

        var explosion = new ItemBuilder(Material.GUNPOWDER).name(ChatColor.GOLD + "Explosion").meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();

        var walk = new ItemBuilder(Material.NETHERITE_BOOTS).name(ChatColor.GREEN + "Walk").meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();

        var speed = new ItemBuilder(Material.DIAMOND_BOOTS).name(ChatColor.GREEN + "Speed").meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();

        var jump = new ItemBuilder(Material.IRON_BOOTS).name(ChatColor.GREEN + "Jump").meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();

        var roar = new ItemBuilder(Material.GLOWSTONE_DUST).name(ChatColor.GREEN + "Roar").meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();

        var laugh = new ItemBuilder(Material.SUGAR).name(ChatColor.GREEN + "Laugh").meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();

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

        var helmet = new ItemBuilder(Material.NETHERITE_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();
        var chestplate = new ItemBuilder(Material.NETHERITE_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();
        var leggings = new ItemBuilder(Material.NETHERITE_LEGGINGS).enchant(Enchantment.PROTECTION_PROJECTILE, 4).meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();
        var boots = new ItemBuilder(Material.NETHERITE_BOOTS).enchant(Enchantment.PROTECTION_PROJECTILE, 4).meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();

        var equip = sender.getEquipment();
        equip.setHelmet(helmet);
        equip.setChestplate(chestplate);
        equip.setLeggings(leggings);
        equip.setBoots(boots);

        sender.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000, 5, false, false));

        sender.setGameMode(GameMode.SURVIVAL);

        sender.sendMessage(ChatColor.GOLD + "Disquised as Redstone Monstrosity");
    }

    @Subcommand("bomber")
    @CommandAlias("bomber")
    public void disguisebomber(Player sender) {
    
        Bukkit.dispatchCommand(sender, "disguise phantom setCustomName \""+ "Bomber Phantom" +"\" setSize 30 setSelfDisguiseVisible false");

        var bomber = new ItemBuilder(Material.FLINT_AND_STEEL).name(ChatColor.GOLD + "Bomber").meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();


        var inv = sender.getInventory();
        inv.addItem(bomber);

        var helmet = new ItemBuilder(Material.NETHERITE_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();
        var chestplate = new ItemBuilder(Material.NETHERITE_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();
        var leggings = new ItemBuilder(Material.NETHERITE_LEGGINGS).enchant(Enchantment.PROTECTION_PROJECTILE, 4).meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();
        var boots = new ItemBuilder(Material.NETHERITE_BOOTS).enchant(Enchantment.PROTECTION_PROJECTILE, 4).meta(ItemMeta.class, meta -> meta.setCustomModelData(666)).build();

        var equip = sender.getEquipment();
        equip.setHelmet(helmet);
        equip.setChestplate(chestplate);
        equip.setLeggings(leggings);
        equip.setBoots(boots);

        sender.setGameMode(GameMode.SURVIVAL);
        
        sender.setAllowFlight(true);
        sender.setFlying(true);
        sender.sendMessage(ChatColor.GOLD + "Disquised as Phantom Bomber");
    }

    @Subcommand("disguise")
    public void disguiseCustom(Player sender, EntityType entity, String name, boolean baby) {

        Bukkit.dispatchCommand(sender, "disguise " + entity + " setBaby " + baby + " setcustomname \""+ name +"\" setcustomnamevisible false setSelfDisguiseVisible false");

        sender.setGameMode(GameMode.SURVIVAL);

        sender.sendMessage(ChatColor.GOLD + "Disquised as " + name);
    }


}