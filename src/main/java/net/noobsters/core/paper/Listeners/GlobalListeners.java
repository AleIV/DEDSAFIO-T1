package net.noobsters.core.paper.Listeners;

import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mrmicky.fastinv.ItemBuilder;
import net.noobsters.core.paper.PERMADED;

public class GlobalListeners implements Listener{

    PERMADED instance;
    Random random = new Random();

    GlobalListeners(PERMADED instance){
        this.instance = instance;
    }
    
    @EventHandler
    public void piglinZombification(CreatureSpawnEvent e) {
        if (e.getEntity() instanceof Piglin) {
            Piglin piglin = (Piglin) e.getEntity();
            piglin.setImmuneToZombification(true);
            piglin.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            piglin.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 3));
            var item = new ItemStack(Material.CROSSBOW);
            var meta = (CrossbowMeta) item.getItemMeta();

            var rocket = new ItemBuilder(Material.FIREWORK_ROCKET).amount(64).build();
            var rocketMeta = (FireworkMeta) rocket.getItemMeta();
            var fireworkEffect = FireworkEffect.builder().withColor(Color.AQUA).withFade(Color.GREEN).build();
            rocketMeta.addEffect(fireworkEffect);
            rocket.setItemMeta(rocketMeta);

            var proyectiles = List.of(rocket,rocket,rocket);
            meta.setChargedProjectiles(proyectiles);
            item.setItemMeta(meta);
            piglin.getEquipment().setItemInOffHand(rocket);
            piglin.getEquipment().setItemInMainHand(item);

        } else if (e.getEntity() instanceof Bat) {
            Bat bat = (Bat) e.getEntity();
            var entity = bat.getWorld().spawnEntity(bat.getLocation(), EntityType.BLAZE);
            bat.addPassenger(entity);

        } else if (e.getEntity() instanceof Pig) {
            Pig pig = (Pig) e.getEntity();
            e.setCancelled(true);
            var ghast = (Ghast) pig.getWorld().spawnEntity(pig.getLocation().add(0, 20, 0), EntityType.GHAST);
            
        }
        else if (e.getEntity() instanceof Sheep) {
            Sheep pig = (Sheep) e.getEntity();
            e.setCancelled(true);
            var phantom = (Phantom) pig.getWorld().spawnEntity(pig.getLocation().add(0, 20, 0), EntityType.PHANTOM);
            
        }
        else if (e.getEntity() instanceof Pillager) {
            Pillager pillager = (Pillager) e.getEntity();
            pillager.getEquipment().setItemInMainHand(new ItemBuilder(Material.CROSSBOW).enchant(Enchantment.PIERCING)
                .enchant(Enchantment.QUICK_CHARGE, 3).enchant(Enchantment.MULTISHOT).build());
            
        }

        else if (e.getEntity() instanceof Chicken) {
            Chicken chicken = (Chicken) e.getEntity();
            var entity = (org.bukkit.entity.Skeleton) chicken.getWorld().spawnEntity(chicken.getLocation(), EntityType.SKELETON);
            entity.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            chicken.addPassenger(entity);
            
        }
        else if (e.getEntity() instanceof Skeleton) {
            Skeleton skeleton = (Skeleton) e.getEntity();
            skeleton.getEquipment().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
            skeleton.getEquipment().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
            skeleton.getEquipment().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
            skeleton.getEquipment().setBoots(new ItemBuilder(Material.IRON_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
            skeleton.getEquipment().setItemInMainHand(new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 3).build());
            
        }

    }

    
    @EventHandler
    public void impact(ProjectileHitEvent e){
        if(e.getEntity() instanceof Arrow){
            var hitblock = e.getHitBlock();
            var entity = e.getHitEntity();
            if(hitblock != null){
                hitblock.getLocation().createExplosion(5.0f, true);
            } else if(entity != null){
                entity.getLocation().createExplosion(5.0f, true);
            }
        }
    }


}