package net.noobsters.core.paper.Listeners;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.destroystokyo.paper.Title;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
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
    public void playerDied(PlayerDeathEvent e) {
        var player = e.getEntity();
        var game = instance.getGame();
        var name = e.getEntity().getName();
        

        if(player.getGameMode() == GameMode.SPECTATOR) e.setDeathMessage("");

        if(game.getDeathPlayers().containsKey(name)){
            game.getDeathPlayers().put(name, true);
        }
        
        player.setHealth(20);
        player.setGameMode(GameMode.SPECTATOR);
        player.sendTitle(Title.builder().title("")
                .subtitle(new ComponentBuilder("YOU ARE DEAD").bold(true).color(ChatColor.DARK_RED).create()).build());
        
        if(!game.isGulak()){
            Bukkit.getScheduler().runTaskLater(instance, () -> {
                if (!player.hasPermission("mod.perm"))
                    player.kickPlayer(ChatColor.RED + "Good luck in the Gulag!");
            }, 20 * 30);
        }

        if(!player.hasPermission("mod.perm") && !game.isGulak()){
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
        if (player.getGameMode() == GameMode.SPECTATOR && !game.isGulak() && !player.hasPermission("mod.perm")) {
            Bukkit.getScheduler().runTaskLater(instance, () -> {
                player.kickPlayer(deathMsg);
            }, 5);
        }

        if(player.getGameMode() == GameMode.SPECTATOR){
            e.setJoinMessage("");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        var player = e.getPlayer();
        if(player.getGameMode() == GameMode.SPECTATOR){
            e.setQuitMessage("");
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPVP(EntityDamageByEntityEvent e) {
        
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
                if(p1.getUniqueId().toString() == p2.getUniqueId().toString()) return;

                if(!p1.hasPermission("mod.perm") && p2.getGameMode() == GameMode.SURVIVAL && !p2.hasPermission("mod.perm")){
                    e.setCancelled(true);
                }
            }
        }
    }

}