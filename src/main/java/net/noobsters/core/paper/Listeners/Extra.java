package net.noobsters.core.paper.Listeners;

import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.noobsters.core.paper.GameTickEvent;
import net.noobsters.core.paper.PERMADED;

public class Extra implements Listener {

    PERMADED instance;
    Random random = new Random();

    Extra(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onDrop(ItemSpawnEvent e){
        var item = e.getEntity().getItemStack();
        var game = instance.getGame();
        if(game.isGulak() && item.getType() == Material.TNT){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        ItemStack item = e.getCurrentItem();
        var game = instance.getGame();

        if (item == null){
            return;
        }

        if (item.equals(game.getTnt())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTick(GameTickEvent e) {
        var difficulty = instance.getGame().getDifficultyChanges();
        Bukkit.getScheduler().runTask(instance, () -> {
            if (difficulty.get("meteor")) {
                playersRefresh();
            }

        });

    }

    public float getDistance(Location val1, Location val2) {

        double x1 = val1.getX();
        double z1 = val1.getZ();

        double x2 = val2.getX();
        double z2 = val2.getZ();

        return (float) Math.abs(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2)));
    }

    public void playersRefresh() {
        var difficulty = instance.getGame().getDifficultyChanges();

        Bukkit.getOnlinePlayers().stream().forEach(player -> {

            if (player.getWorld() == Bukkit.getWorld("world")) {
                var meteors = player.getNearbyEntities(32, 100, 32).stream()
                        .filter(meteor -> meteor instanceof ArmorStand && meteor.getCustomName() != null
                                && meteor.getCustomName().contains("Meteor"))
                        .map(e -> (ArmorStand) e).collect(Collectors.toList());
                if (!meteors.isEmpty()) {

                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 1));
                }
            }

            if (difficulty.get("environment") && player.getWorld() == Bukkit.getWorld("world_nether")) {

                var equip = player.getEquipment();
                var helmet = equip.getHelmet();
                var chest = equip.getChestplate();
                var legs = equip.getLeggings();
                var boots = equip.getBoots();

                if (helmet == null || chest == null || chest.getType().toString().contains("ELYTRA") || legs == null
                        || boots == null) {

                    player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                    player.setFireTicks(20 * 30);
                }
            }

        });

    }

}