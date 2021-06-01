package net.noobsters.core.paper.Listeners;

import java.util.Random;

import com.destroystokyo.paper.event.entity.EnderDragonFireballHitEvent;
import com.destroystokyo.paper.event.entity.EnderDragonFlameEvent;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vex;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mrmicky.fastinv.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class MiniBosses implements Listener {

    PERMADED instance;
    Random random = new Random();

    MiniBosses(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void bossSpawns(CreatureSpawnEvent e) {
        var difficulty = instance.getGame().getDifficultyChanges();
        if(!difficulty.get("blood")) return;

        if (e.getEntity() instanceof EnderDragon) {
            var dragon = (EnderDragon) e.getEntity();
            dragon.setCustomName(ChatColor.YELLOW + "Blood Ender Dragon");
            dragon.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000, 1, false, false));
        }

    }

    @EventHandler
    public void bossDeath(EntityDeathEvent e) {
        var difficulty = instance.getGame().getDifficultyChanges();
        if(!difficulty.get("blood")) return;

        var entity = e.getEntity();
        if (entity instanceof EnderDragon) {
            var bloodScale = new ItemBuilder(Material.RABBIT_FOOT).name(ChatColor.RED + "Blood Scale").meta(ItemMeta.class, meta -> meta.setCustomModelData(143)).amount(random.nextInt(16)).build();
            e.getDrops().add(bloodScale);
        }

    }

    @EventHandler
    public void scaleBlock(EntityDamageByEntityEvent e){
        
        var entity = e.getEntity();
        var damager = e.getDamager();
        if(damager instanceof Player && entity instanceof ArmorStand && entity.getCustomName() != null && entity.getCustomName().contains("Scale")){
            var item = new ItemBuilder(Material.RABBIT_FOOT).name(ChatColor.RED + "Blood Scale").meta(ItemMeta.class, meta -> meta.setCustomModelData(143)).amount(random.nextInt(3)).build();

            entity.getWorld().dropItemNaturally(entity.getLocation(), item);
        }
    }

    @EventHandler
    public void onFireBall(EnderDragonFireballHitEvent e) {
        var cloud = e.getAreaEffectCloud();

        var difficulty = instance.getGame().getDifficultyChanges();
        if(!difficulty.get("blood")) return;

        cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);

        if (e.getTargets() != null) {
            e.getTargets().forEach(target -> {
                if (target instanceof Player) {
                    target.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 0));
                }
            });
        }
    }

    @EventHandler
    public void onFireBall2(EnderDragonFlameEvent e) {
        var cloud = e.getAreaEffectCloud();

        var difficulty = instance.getGame().getDifficultyChanges();
        if(!difficulty.get("blood")) return;

        cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
        cloud.getWorld().spawnEntity(cloud.getLocation().add(chooseCoord(5), 2, chooseCoord(5)),
                EntityType.VEX);
    }

    @EventHandler
    public void spiritDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Vex && e.getCause() == DamageCause.MAGIC) {
            e.setCancelled(true);
        }
    }

    public Integer chooseCoord(int radius) {
        var num = random.nextInt(radius);
        num = random.nextBoolean() ? ~(num) : num;
        return num;
    }

}