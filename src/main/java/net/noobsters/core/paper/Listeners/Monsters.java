package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class Monsters implements Listener {

    PERMADED instance;
    Random random = new Random();

    Monsters(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void monsterSpawns(CreatureSpawnEvent e) {
        var game = instance.getGame();
        var difficulty = game.getDifficultyChange();
        if ((e.getEntity() instanceof Spider || e.getEntity() instanceof CaveSpider) && difficulty >= 2) {
            var spider = (Spider) e.getEntity();
            spider.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 1000, 1));
            spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 1000, 1));
            spider.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 1000, 0));
            switch (random.nextInt(5)) {
                case 1: spider.setCustomName(ChatColor.RED + "Green Spider");
                    
                    break;
                case 2: spider.setCustomName(ChatColor.RED + "Stone Spider");
                    
                    break;
                case 3: spider.setCustomName(ChatColor.RED + "Dust Spider");
                    
                    break;
                case 4: spider.setCustomName(ChatColor.RED + "Glaciar Spider");
                    
                    break;

                default: spider.setCustomName(ChatColor.RED + "Ancient Spider");
                    break;
            }
            
        } else if (e.getEntity() instanceof Creeper && difficulty >= 7) {
            var creeper = (Creeper) e.getEntity();

            switch (random.nextInt(5)) {
                case 1: {
                    creeper.setCustomName(ChatColor.RED + "Tesla Creeper");
                    creeper.setPowered(true);
                }
                    break;
                case 2: {
                    creeper.setCustomName(ChatColor.RED + "Creeper Ninja");
                    creeper.setPowered(true);
                    creeper.setInvisible(true);
                    creeper.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 200, 0));
                    creeper.setSilent(true);
                    creeper.setHealth(0.1);
                }
                    break;
                case 3: {
                    creeper.setCustomName(ChatColor.RED + "Creeperscrimer");
                    creeper.setExplosionRadius(2);
                    creeper.setMaxFuseTicks(5);
                    creeper.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 120, 2));
                }
                    break;
                case 4: {
                    creeper.setCustomName(ChatColor.RED + "Atomic Creeper");
                    creeper.setPowered(true);
                    creeper.setExplosionRadius(30);
                    creeper.setHealth(0.1);
                    creeper.setGlowing(true);
                }
                    break;
                // rubber chicken
                default: {
                    creeper.setCustomName(ChatColor.AQUA + "Pollito de color");
                    creeper.setSilent(true);
                    creeper.setPowered(true);
                    creeper.setInvisible(true);
                    creeper.setExplosionRadius(2);
                    creeper.setMaxFuseTicks(5);
                    creeper.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 120, 2));

                    var pollo = new ItemStack(Material.RABBIT_FOOT);
                    var polloMeta = pollo.getItemMeta();
                    polloMeta.setCustomModelData(137);
                    polloMeta.setDisplayName(ChatColor.YELLOW + "Rubber Chicken");
                    pollo.setItemMeta(polloMeta);

                    var miniZombie = (Zombie) creeper.getWorld().spawnEntity(creeper.getLocation(), EntityType.ZOMBIE);
                    miniZombie.setCustomName(ChatColor.RED + "Rubber Chicken");
                    miniZombie.setSilent(true);
                    miniZombie.setInvisible(true);
                    miniZombie.setBaby();
                    miniZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0);

                    var equipment = miniZombie.getEquipment();
                    equipment.setHelmet(pollo);
                    equipment.setChestplate(new ItemStack(Material.AIR));
                    equipment.setLeggings(new ItemStack(Material.AIR));
                    equipment.setBoots(new ItemStack(Material.AIR));
                    equipment.setItemInMainHand(new ItemStack(Material.AIR));
                    equipment.setItemInOffHand(new ItemStack(Material.AIR));

                    creeper.addPassenger(miniZombie);
                }
                    break;
            }

        }

    }

    @EventHandler
    public void entityDamage(EntityDeathEvent e){
        var entity = e.getEntity();
        if(entity instanceof Creeper && (entity.toString().contains("Pollito") || entity.getName().toString().contains("Rubber"))){
            var loc = entity.getLocation();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:pollo_4 ambient @a "
                    + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " 1 1");
        }
    }

    //chicken sounds on hit
}