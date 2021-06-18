package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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
        var entity = e.getEntity();
        var difficulty = game.getDifficultyChanges();
        if (difficulty.get("spiders") && entity instanceof Spider) {
            if (entity instanceof CaveSpider) {
                e.setCancelled(true);
                entity.getWorld().spawnEntity(entity.getLocation(), EntityType.SPIDER);
                return;
            }

            var spider = (Spider) entity;
            spider.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 600, 1));
            spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 600, 1));
            spider.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 600, 1));
            switch (random.nextInt(5)) {
                case 1:
                    spider.setCustomName(ChatColor.GREEN + "Green Spider");

                    break;
                case 2:
                    spider.setCustomName(ChatColor.GREEN + "Stone Spider");

                    break;
                case 3:
                    spider.setCustomName(ChatColor.GREEN + "Dust Spider");

                    break;
                case 4:
                    spider.setCustomName(ChatColor.GREEN + "Glaciar Spider");

                    break;

                default:
                    spider.setCustomName(ChatColor.GREEN + "Ancient Spider");
                    break;
            }

        } else if (difficulty.get("spiders") && entity instanceof CaveSpider) {
            e.setCancelled(true);
            entity.getWorld().spawnEntity(entity.getLocation(), EntityType.SPIDER);

        } else if (entity instanceof MagmaCube && difficulty.get("redstone")) {
            var magma = (MagmaCube) entity;
            magma.setCustomName(ChatColor.DARK_RED + "Redstone Cube");

        } else if (difficulty.get("creepers") && entity instanceof Creeper) {
            var creeper = (Creeper) entity;

            switch (random.nextInt(5)) {
                case 1: {
                    creeper.setCustomName(ChatColor.GREEN + "Tesla Creeper");
                    creeper.setPowered(true);
                }
                    break;
                case 2: {
                    creeper.setCustomName(ChatColor.GREEN + "Impostor Creeper");
                    creeper.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 300, 0));
                    creeper.setSilent(true);
                    creeper.setExplosionRadius(3);
                }
                    break;
                case 3: {
                    creeper.setCustomName(ChatColor.GREEN + "Creeperscrimer");
                    creeper.setExplosionRadius(3);
                    creeper.setMaxFuseTicks(5);
                    creeper.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 120, 2));
                }
                    break;
                case 4: {
                    creeper.setCustomName(ChatColor.GREEN + "Atomic Creeper");
                    creeper.setPowered(true);
                    creeper.setExplosionRadius(30);
                    creeper.setHealth(creeper.getHealth() / 2);
                    creeper.setGlowing(true);
                }
                    break;
                // rubber chicken
                default: {
                    creeper.setCustomName(ChatColor.GREEN + "Rainbow Creeper");
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
                    miniZombie.setCustomName(ChatColor.YELLOW + "Rubber Chicken");
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

    @EventHandler(priority = EventPriority.LOW)
    public void entityDamage(EntityDeathEvent e) {
        var entity = e.getEntity();
        if (entity.getName().toString().contains("Rubber") && random.nextBoolean()) {
            var loc = entity.getLocation();
            switch (random.nextInt(4)) {
                case 1:

                    loc.getNearbyPlayers(20).stream().forEach(p -> {
                        p.playSound(p.getLocation(), "pollo", 1, 1);
                    });

                    break;
                case 2:

                    loc.getNearbyPlayers(20).stream().forEach(p -> {
                        p.playSound(p.getLocation(), "pollo_2", 1, 1);
                    });

                    break;
                case 3:

                    loc.getNearbyPlayers(20).stream().forEach(p -> {
                        p.playSound(p.getLocation(), "pollo_3", 1, 1);
                    });

                    break;

                default:

                    loc.getNearbyPlayers(20).stream().forEach(p -> {
                        p.playSound(p.getLocation(), "pollo_4", 1, 1);
                    });

                    break;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void rubberSoundItem(EntityDamageByEntityEvent e) {
        var entity = e.getEntity();
        var damager = e.getDamager();
        if (damager instanceof Player) {
            var player = (Player) damager;
            var rubber = player.getEquipment().getItemInMainHand().getItemMeta();
            if (rubber != null && rubber.hasCustomModelData() && rubber.getCustomModelData() == 137) {
                var loc = damager.getLocation();
                switch (random.nextInt(4)) {
                    case 1:

                        loc.getNearbyPlayers(20).stream().forEach(p -> {
                            p.playSound(p.getLocation(), "pollo", 1, 1);
                        });

                        break;
                    case 2:

                        loc.getNearbyPlayers(20).stream().forEach(p -> {
                            p.playSound(p.getLocation(), "pollo_2", 1, 1);
                        });

                        break;
                    case 3:

                        loc.getNearbyPlayers(20).stream().forEach(p -> {
                            p.playSound(p.getLocation(), "pollo_3", 1, 1);
                        });

                        break;

                    default:

                        loc.getNearbyPlayers(20).stream().forEach(p -> {
                            p.playSound(p.getLocation(), "pollo_4", 1, 1);
                        });

                        break;
                }
            }
        } else if (damager instanceof Spider && entity instanceof Player) {
            var player = (Player) entity;
            var spider = (Spider) e.getDamager();
            if (spider.getCustomName() != null) {
                var name = spider.getCustomName().toString();

                var totem = getTotemOnUse(player);

                if (totem != null && isSpecialTotem(totem)) {
                    var data = totem.getItemMeta().getCustomModelData();
                    //SPIDER TOTEM
                    if(data == 3)
                        return;
                }

                if (name.contains("Green")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 30, 0));

                } else if (name.contains("Stone")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 20, 2));

                } else if (name.contains("Dust")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 0));

                } else if (name.contains("Glaciar")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 3));

                } else if (name.contains("Ancient")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * 5, 200));
                }

            }

        }

    }

    public ItemStack getTotemOnUse(Player player){
        var equip = player.getEquipment();
        var totemoff = equip.getItemInOffHand();
        var totemhand = equip.getItemInMainHand();

        if(isTotem(totemhand)){
            return totemhand;
        }else if(isTotem(totemoff)){
            return totemoff;
        }

        return null;
    }

    public boolean isTotem(ItemStack stack){
        return stack != null && stack.getType() == Material.TOTEM_OF_UNDYING;
    }

    public boolean isSpecialTotem(ItemStack stack){
        return isTotem(stack) && stack.getItemMeta().hasCustomModelData();
    }


}