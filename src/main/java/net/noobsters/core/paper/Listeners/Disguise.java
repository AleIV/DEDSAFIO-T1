package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.libraryaddict.disguise.DisguiseAPI;
import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class Disguise implements Listener {

    PERMADED instance;
    Random random = new Random();

    Disguise(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void deathDisguise(PlayerDeathEvent e) {
        var player = e.getEntity();
        var killer = player.getKiller();
        if (DisguiseAPI.isDisguised(player)) {
            var disguise = DisguiseAPI.getDisguise(player);
            e.setDeathMessage("");
            e.getDrops().forEach(drop -> drop.setType(Material.AIR));
            e.getDrops().add(new ItemStack(Material.WOODEN_PICKAXE));

            if (killer != null && killer instanceof Player && disguise.getDisguiseName().contains("Redstone")) {
                e.setDeathMessage(ChatColor.DARK_RED + "Redstone Monstrosity " + ChatColor.WHITE + "was destroyed by "
                        + player.getName().toString());
            }
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
            player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0);
            player.setGameMode(GameMode.SPECTATOR);
            
            var loc = player.getLocation();

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "particle minecraft:campfire_signal_smoke " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " .1 .1 .1 .1 50 force");

        } else if (killer != null && killer instanceof Player && DisguiseAPI.isDisguised(killer)) {
            e.setDeathMessage(player.getName().toString() + " was tricked by artificial intelligence");

            var item = killer.getEquipment().getItemInMainHand().getItemMeta();
            if (killer.hasPermission("mod.perm") && item.hasDisplayName() && item.getDisplayName().contains("Melee")) {
                e.setDeathMessage(player.getName().toString() + " has been reduced to dust by the " + ChatColor.DARK_RED
                        + "Redstone Monstrosity");
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        var item = e.getItemDrop().getItemStack();
        var string = item.getItemMeta().getDisplayName().toString();
        if (DisguiseAPI.isDisguised(e.getPlayer()) && string != null && string.contains("Melee")
                || string.contains("Fireball") || string.contains("Explosion")
                || string.contains("Walk") || string.contains("Speed") || string.contains("Jump")
                || string.contains("Roar") || string.contains("Laugh")) {
            e.setCancelled(true);

        }
        
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        var player = e.getPlayer();
        if (DisguiseAPI.isDisguised(player)) {
            e.setQuitMessage("");
        }
    }

    @EventHandler
    public void velocity(PlayerVelocityEvent e) {
        var player = e.getPlayer();
        if (DisguiseAPI.isDisguised(player)) {
            var disguise = DisguiseAPI.getDisguise(player);
            if (disguise.getDisguiseName().toString().contains("Redstone")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void powers(PlayerInteractEvent e){
        var player = e.getPlayer();
        var item = e.getItem();
        if(item != null && DisguiseAPI.isDisguised(player)){
            var string = item.getItemMeta().getDisplayName().toString();
            var loc = player.getLocation();
            if(string.contains("Explosion")){
                loc.createExplosion(10, false, false);

                loc.getNearbyPlayers(100).stream().forEach(p ->{
                    p.playSound(p.getLocation(), "smash", 3, 1);
                });

            }else if(string.contains("Walk")){
                if(player.hasPotionEffect(PotionEffectType.SPEED)){
                    player.removePotionEffect(PotionEffectType.SPEED);
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 3, false, false));
                //sound walk

                loc.getNearbyPlayers(100).stream().forEach(p ->{
                    p.playSound(p.getLocation(), "steps", 3, 1);
                });

            }else if(string.contains("Speed")){
                if(player.hasPotionEffect(PotionEffectType.SLOW)){
                    player.removePotionEffect(PotionEffectType.SLOW);
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 0, false, false));
                //sound fast walk

                loc.getNearbyPlayers(100).stream().forEach(p ->{
                    p.playSound(p.getLocation(), "steps", 3, 1.5f);
                });

            }else if(string.contains("Jump")){
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 29, false, false));
                //sound jump

                loc.getNearbyPlayers(100).stream().forEach(p ->{
                    p.playSound(p.getLocation(), "smash", 3, 1);
                });

            }else if(string.contains("Roar")){
                loc.getNearbyPlayers(40).stream().filter(p -> DisguiseAPI.isDisguised(p))
                    .forEach(p -> p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*3, 4, false, false)));
                //sound roar

                loc.getNearbyPlayers(100).stream().forEach(p ->{
                    p.playSound(p.getLocation(), "roar", 3, 1);
                });

            }else if(string.contains("Laugh")){
                //sound laugh

                loc.getNearbyPlayers(100).stream().forEach(p ->{
                    p.playSound(p.getLocation(), "laugh", 3, 1);
                });

            }
        }
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        var entity = e.getEntity();
        var bow = e.getBow().getItemMeta();
        if (entity instanceof Player && DisguiseAPI.isDisguised(entity)) {

            if (bow.hasDisplayName() && bow.getDisplayName().toString().contains("Fireball")) {
                var fireball = (Fireball) e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation().add(0, 1, 0),
                        EntityType.FIREBALL);
                fireball.setYield(5);
                e.setProjectile(fireball);
            }
        }
    }

    public Integer chooseCoord(int radius) {
        var num = random.nextInt(radius);
        num = random.nextBoolean() ? ~(num) : num;
        return num;
    }

    @EventHandler
    public void damage(EntityDamageEvent e) {
        var entity = e.getEntity();
        if (entity instanceof Player && DisguiseAPI.isDisguised(entity)) {
            var disguise = DisguiseAPI.getDisguise(entity);
            var cause = e.getCause();
            if (disguise.getDisguiseName().contains("Redstone") && cause == DamageCause.FALL
                    || cause == DamageCause.LAVA || cause == DamageCause.FIRE || cause == DamageCause.FIRE_TICK
                    || cause == DamageCause.ENTITY_EXPLOSION || cause == DamageCause.BLOCK_EXPLOSION
                    || cause == DamageCause.HOT_FLOOR || cause == DamageCause.WITHER) {
                e.setCancelled(true);

            }else if(cause == DamageCause.FALL){
                e.setCancelled(true);
            }
        }

    }

}