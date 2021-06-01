package net.noobsters.core.paper.Listeners;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class DedsafioListener implements Listener {

    PERMADED instance;
    Random random = new Random();
    String deathMsg = ChatColor.DARK_RED + "" + ChatColor.BOLD + "YOU ARE DEAD";
    private @Getter List<Material> possibleFence = Arrays.stream(Material.values())
            .filter(material -> material.name().contains("FENCE") && !material.name().contains("FENCE_GATE"))
            .collect(Collectors.toList());

    DedsafioListener(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        var player = e.getPlayer();
        var entity = e.getRightClicked();
        if(entity instanceof ArmorStand && player.getEquipment().getItemInMainHand() != null){
            var item = player.getEquipment().getItemInMainHand();
            //var stand = (ArmorStand) entity;
            if(item.getItemMeta().hasCustomModelData() && 
                item.getItemMeta().getCustomModelData() == 114){
                    instance.getGame().getReviveList().add(item.getItemMeta().getDisplayName().toLowerCase());
                    player.getEquipment().setItemInMainHand(null);
                    
                    var loc = player.getLocation();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
                        "playsound minecraft:entity.blaze.death master @a " + loc.getX() +  " " + loc.getY() + " " + loc.getZ() +  " 1 1");
            }

        }
    }
    

    @EventHandler
    public void playerDied(PlayerDeathEvent e) {
        var player = e.getEntity();
        var game = instance.getGame();
        var name = e.getEntity().getName();
        

        if (player.getGameMode() == GameMode.SPECTATOR || player.hasPermission("mod.perm"))
            e.setDeathMessage("");

        if (game.getDeathPlayers().containsKey(name)) {
            game.getDeathPlayers().put(name, true);
        }

        player.setHealth(20);
        player.setGameMode(GameMode.SPECTATOR);

        if (!game.isGulak()) {
            
            if(!player.hasPermission("mod.perm")){
                instance.animation(ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + e.getDeathMessage(), "muerte", "D", 90, true);
            }

            Bukkit.getScheduler().runTaskLater(instance, () -> {
                if (!player.hasPermission("mod.perm"))
                    player.kickPlayer(ChatColor.RED + "Good luck in the Gulag!");
            }, 20 * 30);

        }else if(game.getPvpOn().contains(player.getUniqueId().toString())){

            if(!player.hasPermission("mod.perm")){
                instance.animation(ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + e.getDeathMessage(), "fatality", "E", 55, true);
            }
            
        }

        if (!player.hasPermission("mod.perm") && !game.isGulak()) {
            var loc = player.getLocation();
            loc.getBlock().setType(getRandomFence());

            Block headBlock = loc.getBlock().getRelative(BlockFace.UP);
            headBlock.setType(Material.PLAYER_HEAD);

            if (headBlock.getState() instanceof Skull) {
                Skull skull = (Skull) headBlock.getState();
                skull.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
                skull.update();
            }
        }

    }

    private Material getRandomFence() {
        return possibleFence.get(new Random().nextInt(possibleFence.size()));
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        var player = e.getPlayer();
        var game = instance.getGame();
        var revive = game.getReviveList();
        var check = revive.contains(player.getName().toLowerCase());

        if(player.getGameMode() == GameMode.SPECTATOR && !check && !game.isGulak() && !player.hasPermission("mod.perm")) {
            Bukkit.getScheduler().runTaskLater(instance, () -> {
                player.kickPlayer(deathMsg);

            }, 5);
        }

        if(check){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:overworld run setblock -535 162 -220 minecraft:redstone_block");

            Bukkit.getScheduler().runTaskLater(instance, task ->{
                revive.remove(player.getName().toLowerCase());
                player.setGameMode(GameMode.SURVIVAL);
                player.teleport(new Location(Bukkit.getWorld("world"), -525.5, 203.5, -174.5));
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[&4&lDEDSAFIO&6&l] &f" + player.getName() + " ha vuelto de la muerte."));

                Bukkit.getOnlinePlayers().forEach(p ->{
                    player.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 0.5f);
                });

            }, 20*4);
        }

        if (player.getGameMode() == GameMode.SPECTATOR) {
            e.setJoinMessage("");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        var player = e.getPlayer();
        if (player.getGameMode() == GameMode.SPECTATOR) {
            e.setQuitMessage("");
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPVP(EntityDamageByEntityEvent e) {
        var game = instance.getGame();

        if (e.getEntity() instanceof Player) {
            Player p1 = (Player) e.getEntity();
            Player p2 = null;
            if (e.getDamager() instanceof Player) {
                p2 = (Player) e.getDamager();
            } else if (e.getDamager() instanceof Projectile) {
                Projectile proj = (Projectile) e.getDamager();
                if (proj.getShooter() instanceof Player) {
                    p2 = (Player) proj.getShooter();
                }
            }
            if (p2 != null) {
                var pvp = instance.getGame().getPvpOn();

                if(game.getTrap().contains(p2.getUniqueId().toString())) e.setDamage(e.getDamage()+1);

                if (pvp.contains(p1.getUniqueId().toString()) && pvp.contains(p2.getUniqueId().toString()))
                    return;

                if (p1.getUniqueId().toString() == p2.getUniqueId().toString())
                    return;

                if (!p1.hasPermission("mod.perm") && p2.getGameMode() == GameMode.SURVIVAL
                        && !p2.hasPermission("mod.perm")) {
                    e.setCancelled(true);
                }
            }
        }
    }

}