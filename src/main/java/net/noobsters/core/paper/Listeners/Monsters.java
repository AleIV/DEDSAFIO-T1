package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
        if (difficulty >= 2 && (e.getEntity() instanceof Spider || e.getEntity() instanceof CaveSpider)) {
            var spider = (Spider) e.getEntity();
            spider.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 600, 1));
            spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 600, 1));
            spider.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 600, 0));
            switch (random.nextInt(5)) {
                case 1:
                    spider.setCustomName(ChatColor.RED + "Green Spider");

                    break;
                case 2:
                    spider.setCustomName(ChatColor.RED + "Stone Spider");

                    break;
                case 3:
                    spider.setCustomName(ChatColor.RED + "Dust Spider");

                    break;
                case 4:
                    spider.setCustomName(ChatColor.RED + "Glaciar Spider");

                    break;

                default:
                    spider.setCustomName(ChatColor.RED + "Ancient Spider");
                    break;
            }

        } else if (e.getEntity() instanceof Creeper && difficulty >= 8) {
            var creeper = (Creeper) e.getEntity();

            switch (random.nextInt(5)) {
                case 1: {
                    creeper.setCustomName(ChatColor.RED + "Tesla Creeper");
                    creeper.setPowered(true);
                }
                    break;
                case 2: {
                    creeper.setCustomName(ChatColor.RED + "Impostor Creeper");
                    creeper.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 300, 0));
                    creeper.setSilent(true);
                    creeper.setExplosionRadius(3);
                }
                    break;
                case 3: {
                    creeper.setCustomName(ChatColor.RED + "Creeperscrimer");
                    creeper.setExplosionRadius(3);
                    creeper.setMaxFuseTicks(5);
                    creeper.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 120, 2));
                }
                    break;
                case 4: {
                    creeper.setCustomName(ChatColor.RED + "Atomic Creeper");
                    creeper.setPowered(true);
                    creeper.setExplosionRadius(30);
                    creeper.setHealth(creeper.getHealth()/2);
                    creeper.setGlowing(true);
                }
                    break;
                // rubber chicken
                default: {
                    creeper.setCustomName(ChatColor.AQUA + "Rainbow Creeper");
                    creeper.setSilent(true);
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
                    equipment.setHelmetDropChance(100);
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
    public void entityDamage(EntityDeathEvent e) {
        var entity = e.getEntity();
        if (entity.getName().toString().contains("Rubber") && random.nextBoolean()) {
            var loc = entity.getLocation();
            switch (random.nextInt(4)) {
                case 1:
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:pollo ambient @a "
                            + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " 1 1");
                    break;
                case 2:
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:pollo_2 ambient @a "
                            + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " 1 1");
                    break;
                case 3:
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:pollo_3 ambient @a "
                            + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " 1 1");
                    break;

                default:
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:pollo_4 ambient @a "
                            + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " 1 1");
                    break;
            }
        }
    }
    
    @EventHandler
    public void rubberSoundItem(EntityDamageByEntityEvent e) {
        var entity =  e.getEntity();
        var damager = e.getDamager();
        if (damager instanceof Player) {
            var player = (Player) damager;
            var rubber = player.getEquipment().getItemInMainHand().getItemMeta();
            if (rubber != null && rubber.hasCustomModelData() && rubber.getCustomModelData() == 137) {
                var loc = damager.getLocation();
                switch (random.nextInt(4)) {
                    case 1:
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:pollo ambient @a "
                                + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " 1 1");
                        break;
                    case 2:
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:pollo_2 ambient @a "
                                + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " 1 1");
                        break;
                    case 3:
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:pollo_3 ambient @a "
                                + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " 1 1");
                        break;

                    default:
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:pollo_4 ambient @a "
                                + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " 1 1");
                        break;
                }
            }
        }else if(damager instanceof Spider && entity instanceof Player){
            var player = (Player) entity;
            var spider = (Spider) e.getDamager();
            if(spider.getCustomName() != null){
                var name = spider.getCustomName().toString();

                if(name.contains("Green")){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*30, 0));

                }else if(name.contains("Stone")){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20*20, 2));

                }else if(name.contains("Dust")){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*10, 0));

                }else if(name.contains("Glaciar")){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*10, 2));

                }else if(name.contains("Ancient")){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20*5, 200));
                }

                }
            
        }

    }

}