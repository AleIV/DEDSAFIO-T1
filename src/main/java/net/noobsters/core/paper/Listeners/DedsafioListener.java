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
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import fr.mrmicky.fastinv.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class DedsafioListener implements Listener {

    PERMADED instance;
    Random random = new Random();
    String deathMsg = ChatColor.DARK_RED + "" + ChatColor.BOLD + "YOU ARE DEAD";
    private List<Material> possibleFence = Arrays.stream(Material.values())
            .filter(material -> material.name().contains("FENCE") && !material.name().contains("FENCE_GATE"))
            .collect(Collectors.toList());

    int amount1 = 1;
    int amount2 = 8;
    int amount3 = 64;
    int amount4 = 64;
    int amount5 = 64;

    Material item1 = Material.INK_SAC;
    Material item2 = Material.TNT;
    Material item3 = Material.END_CRYSTAL;
    Material item4 = Material.CHORUS_FRUIT;
    Material item5 = Material.END_CRYSTAL;

    DedsafioListener(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void interact(PlayerInteractAtEntityEvent e){
        var player = e.getPlayer();
        var entity = e.getRightClicked();

        if(entity instanceof ArmorStand && entity.getCustomName() != null && entity.getCustomName().contains("pilar")){
            var inv = player.getInventory();

            if(inv.contains(item1, amount1) && inv.contains(item2, amount2) && inv.contains(item3, amount3) /*&& inv.contains(item4, amount4) && inv.contains(item4, amount4)*/){
                var itemstack1 = new org.bukkit.inventory.ItemStack(item1, amount1);
                var itemstack2 = new org.bukkit.inventory.ItemStack(item2, amount2);
                var itemstack3 = new org.bukkit.inventory.ItemStack(item3, amount3);
                //var itemstack4 = new org.bukkit.inventory.ItemStack(item4, amount4);
                //var itemstack5 = new org.bukkit.inventory.ItemStack(item5, amount5);

                inv.removeItem(itemstack1);
                inv.removeItem(itemstack2);
                inv.removeItem(itemstack3);
                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "clear " + player.getName() + " minecraft:rabbit_foot 1");
                //inv.removeItem(itemstack4);
                //inv.removeItem(itemstack5);

                var item = new ItemBuilder(Material.WOODEN_SHOVEL).name(ChatColor.GOLD + "" + ChatColor.BOLD + "RESURRECCTION SPOON").flags(ItemFlag.HIDE_ATTRIBUTES).meta(ItemMeta.class, meta -> meta.setCustomModelData(114)).build();
                player.getInventory().addItem(item);

                var loc = entity.getLocation();
            
                loc.getNearbyPlayers(20).stream().forEach(p ->{
                    p.playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
                });

            }else{
                player.sendMessage(ChatColor.RED + "No tienes los materiales.");
            }

        }
    }

    @EventHandler
    public void onInteract(PlayerDropItemEvent e){
        var player = e.getPlayer();
        var item = e.getItemDrop().getItemStack();
            
        if(item.getItemMeta().hasCustomModelData() && 
            item.getItemMeta().getCustomModelData() == 114){

                instance.getGame().getReviveList().add(item.getItemMeta().getDisplayName().toLowerCase());
                
                player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1, 1);
        }
    }
    

    @EventHandler(priority = EventPriority.NORMAL)
    public void playerDied(PlayerDeathEvent e) {
        var player = e.getEntity();
        var game = instance.getGame();

        if (player.getGameMode() == GameMode.SPECTATOR || player.hasPermission("mod.perm"))
            e.setDeathMessage("");

        player.setHealth(20);
        player.setGameMode(GameMode.SPECTATOR);

        if (!game.isGulak()) {
            
            if(!player.hasPermission("mod.perm")){
                instance.animation(e.getDeathMessage(), "muerte", "D", 90, true);
            }

            Bukkit.getScheduler().runTaskLater(instance, () -> {
                if (!player.hasPermission("mod.perm"))
                    player.kickPlayer(ChatColor.RED + "Good luck in the Gulag!");
            }, 20 * 30);

        }else if(game.getPvpOn().contains(player.getUniqueId().toString())){
            instance.animation(e.getDeathMessage(), "fatality", "E", 55, true);
            
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
    public void onDeath(PlayerDeathEvent e) {
        var game = instance.getGame();
        var player = e.getEntity();
        var uuid = player.getUniqueId().toString();
        if(game.isGulak()){
           player.removePotionEffect(PotionEffectType.GLOWING);
           game.getPvpOn().clear(); 

           if(game.getFighters().contains(uuid)){
            game.getFighters().remove(uuid);
           }
        }

        
    }

}