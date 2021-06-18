package net.noobsters.core.paper.Listeners;

import java.lang.reflect.Field;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftBee;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Player;
import org.bukkit.entity.PufferFish;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mrmicky.fastinv.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.EntityBee;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_16_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalSelector;
import net.noobsters.core.paper.PERMADED;

public class Naturally implements Listener {

    PERMADED instance;
    Random random = new Random();

    Naturally(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onCreature(CreatureSpawnEvent e){
        var entity = e.getEntity();
        final var difficulty = instance.getGame().getDifficultyChanges();

        if(!difficulty.get("naturally")) return;

        if(entity instanceof ElderGuardian){
            var guardian = (ElderGuardian) entity;
            guardian.setCustomName(ChatColor.LIGHT_PURPLE + "Ancient Guardian");
            guardian.setGlowing(true);

        }else if(entity instanceof Guardian){
            var guardian = (Guardian) entity;
            guardian.setCustomName(ChatColor.GRAY + "Aquatic Mine");

        }else if(entity instanceof PufferFish){
            var fish = (PufferFish) entity;
            fish.setCustomName(ChatColor.RED + "Fuffer Pish");
            
        }else if(entity instanceof Silverfish){
            var fish = (Silverfish) entity;
            fish.setCustomName(ChatColor.RED + "Ant");
            fish.setRemoveWhenFarAway(true);
            

        }else if(entity instanceof Drowned){
            var drowned = (Drowned) entity;
            drowned.setCustomName(ChatColor.DARK_GREEN + "Monster Drowned");
            var equip = drowned.getEquipment();
            var trident = new ItemBuilder(Material.TRIDENT).build();
            equip.setItemInMainHand(trident);
            
        }else if(entity instanceof Bee){
            var bee = (Bee) entity;
            bee.setCustomName(ChatColor.BLACK + "Moth");
            bee.setRemoveWhenFarAway(true);

            bee.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(20);
            bee.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 600, 0));
            bee.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 600, 0));
            
            CraftBee craft = ((CraftBee) bee);
            EntityBee nms = craft.getHandle();

            try {
                Class<? extends EntityInsentient> cl = EntityInsentient.class; //nms.getClass()
                Field gf = cl.getDeclaredField("goalSelector");
                gf.setAccessible(true);

                PathfinderGoalSelector goal = (PathfinderGoalSelector) gf.get(nms);
                goal.a(0, new PathfinderGoalMeleeAttack(nms, 1.0D, true));

                Field tf = cl.getDeclaredField("targetSelector");
                tf.setAccessible(true);

                PathfinderGoalSelector target = (PathfinderGoalSelector) tf.get(nms);
                target.a(0, new PathfinderGoalNearestAttackableTarget<>(nms, EntityHuman.class, 10, true, false, null));
                
            } catch (Exception x) {
                x.printStackTrace();
            }

        }
    }

    @EventHandler
    public void onDamage2(EntityDamageByEntityEvent e){
        var entity = e.getEntity();
        var damager = e.getDamager();
        final var difficulty = instance.getGame().getDifficultyChanges();
        if(damager instanceof Guardian && !(damager instanceof ElderGuardian) && random.nextInt(3) == 1 && difficulty.get("watermonster")){
            
            entity.getWorld().spawnEntity(entity.getLocation(), EntityType.DROWNED);

        }else if(damager instanceof ElderGuardian && entity instanceof Player){

            var player = (Player) entity;
            player.removePotionEffect(PotionEffectType.WATER_BREATHING);
            player.setRemainingAir(20*4);
            
        }else if(entity instanceof Guardian && !(entity instanceof ElderGuardian)){
            entity.getLocation().createExplosion(6);

        }else if(entity instanceof Player && damager instanceof Bee){
            var player = (Player) entity;
            player.getActivePotionEffects().forEach(effect ->{
                player.removePotionEffect(effect.getType());
            });
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        var block = e.getBlock();
        if(block.getType() == Material.RED_CONCRETE_POWDER){
            var count = 0;
            var max = 20;
            while(count < max){
                block.getWorld().spawnEntity(block.getLocation(), EntityType.SILVERFISH);
                count++;
            }
        }else if(block.getType() == Material.BEE_NEST){
            var count = 0;
            var max = 20;
            while(count < max){
                block.getWorld().spawnEntity(block.getLocation(), EntityType.BEE);
                count++;
            }
        }
    }

}