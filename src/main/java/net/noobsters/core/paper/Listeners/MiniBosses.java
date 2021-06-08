package net.noobsters.core.paper.Listeners;

import java.util.Random;

import com.destroystokyo.paper.event.entity.EnderDragonFireballHitEvent;
import com.destroystokyo.paper.event.entity.EnderDragonFlameEvent;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.ShulkerBullet;
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
        var entity = e.getEntity();

        if (entity instanceof EnderDragon) {
            var dragon = (EnderDragon) entity;
            dragon.setCustomName(ChatColor.YELLOW + "Blood Ender Dragon");
            dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(400);
            dragon.setHealth(400);

        }else if(random.nextInt(5) == 1 && entity.getWorld() == Bukkit.getWorld("world_the_end") && entity instanceof Enderman){
            e.setCancelled(true);
            var loc = entity.getLocation();
            entity.getWorld().spawnEntity(loc, EntityType.ZOMBIE);

        }

    }

    @EventHandler
    public void powers(EntityDamageByEntityEvent e){
        var entity = e.getEntity();
        var proj = e.getDamager();
        if(proj instanceof ShulkerBullet && entity instanceof Player){
            var bullet = (ShulkerBullet) proj;
            var shulker = (Shulker) bullet.getShooter();
            var player = (Player) entity;

            if(shulker.getCustomName() == null){
                switch (random.nextInt(3)) {
                    case 1:{
                        shulker.setCustomName(ChatColor.GOLD + "Fire Shulker"); 
                        shulker.setColor(DyeColor.RED);
                    }break;
                        
                    case 2:{
                        shulker.setCustomName(ChatColor.BLACK + "Radioactive Shulker");
                        shulker.setColor(DyeColor.BLACK);
                    }break;
    
                    default:{
                        shulker.setCustomName(ChatColor.GREEN + "Poisonous Shulker"); 
                        shulker.setColor(DyeColor.GREEN);
                    }break;
                }
            }else if(shulker.getCustomName().contains("Fire")){
                player.setFireTicks(20*10);

            }else if(shulker.getCustomName().contains("Radioactive")){
                player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 10*20, 0));

            }else if(shulker.getCustomName().contains("Poisonous")){
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10*20, 0));

            }

            Bukkit.getScheduler().runTaskLater(instance, task->{
                player.getLocation().createExplosion(2, true, true);
            }, 5);
        }

    }

    @EventHandler
    public void bossDeath(EntityDeathEvent e) {

        var entity = e.getEntity();
        if (entity instanceof EnderDragon) {
            var bloodScale = new ItemBuilder(Material.RABBIT_FOOT).name(ChatColor.RED + "Blood Scale").meta(ItemMeta.class, meta -> meta.setCustomModelData(143)).amount(random.nextInt(10)+10).build();
            e.getDrops().add(bloodScale);
        }

    }

    @EventHandler
    public void scaleBlock(EntityDamageByEntityEvent e){
        
        var entity = e.getEntity();
        var damager = e.getDamager();
        if(damager instanceof Player && entity instanceof ArmorStand && entity.getCustomName() != null && entity.getCustomName().contains("Scale")){
            var item = new ItemBuilder(Material.RABBIT_FOOT).name(ChatColor.RED + "Blood Scale").meta(ItemMeta.class, meta -> meta.setCustomModelData(143)).build();

            entity.getWorld().dropItemNaturally(entity.getLocation(), item);
        }
    }

    @EventHandler
    public void onFireBall(EnderDragonFireballHitEvent e) {
        var cloud = e.getAreaEffectCloud();

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