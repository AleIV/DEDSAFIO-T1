package net.noobsters.core.paper.Listeners;

import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mrmicky.fastinv.ItemBuilder;
import me.libraryaddict.disguise.DisguiseAPI;
import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class Disguise implements Listener {

    PERMADED instance;
    Random random = new Random();

    Disguise(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void deathDisguise(PlayerDeathEvent e) {
        var player = e.getEntity();
        var killer = player.getKiller();
        var game = instance.getGame();
        var loc = player.getLocation();
        if (DisguiseAPI.isDisguised(player)) {
            var disguise = DisguiseAPI.getDisguise(player);
            e.setDeathMessage("");
            e.getDrops().forEach(drop -> drop.setType(Material.AIR));

            player.setGameMode(GameMode.SPECTATOR);

            if (game.getIntelligence().containsKey(player.getName())) {
                game.getIntelligence().put(player.getName(), true);
            }

            if (disguise.getType().getEntityType() == EntityType.RAVAGER
                    || disguise.getType().getEntityType() == EntityType.IRON_GOLEM
                    || disguise.getType().getEntityType() == EntityType.PHANTOM) {
                loc.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);

            } else {
                loc.getWorld().spawnParticle(Particle.CLOUD, loc, 50, 1);
            }

            var disguises = game.getDisguises();
            if (disguises.containsKey(player.getName())) {
                var mob = disguises.get(player.getName());
                switch (mob) {
                    case "warden": {
                        // HANDLE WARDEN DEATH
                        disguises.remove(player.getName());

                        loc.getNearbyPlayers(100).stream().forEach(p ->{
                            p.playSound(p.getLocation(), "warden_death", 1, 0.3f);
                            p.playSound(p.getLocation(), "warden_death", 1, 0.5f);
                        });

                    }
                        break;

                    case "redstone": {
                        // HANDLE REDSTONE DEATH
                        disguises.remove(player.getName());
                        loc.getNearbyPlayers(100).stream().forEach(p ->{
                            p.playSound(p.getLocation(), Sound.ENTITY_RAVAGER_DEATH, 1, 0.3f);
                            p.playSound(p.getLocation(), Sound.ENTITY_RAVAGER_DEATH, 1, 0.5f);
                        });

                    }
                        break;

                    case "clown": {
                        // HANDLE CLOWN DEATH
                        disguises.remove(player.getName());
                    }
                        break;

                    default:
                        break;
                }
            }

        } else if (killer != null && killer instanceof Player && DisguiseAPI.isDisguised(killer)) {

            var disguises = game.getDisguises();
            if (disguises.containsKey(player.getName())) {
                var mob = disguises.get(player.getName());
                switch (mob) {
                    case "warden": {
                        e.setDeathMessage(player.getName() + " was silenced to death by the " + ChatColor.DARK_AQUA
                                + "Warden Monstrosity");

                    }
                        break;

                    case "redstone": {
                        e.setDeathMessage(player.getName() + " was reduced to dust by the " + ChatColor.DARK_RED
                                + "Redstone Monstrosity");

                        loc.getNearbyPlayers(100).stream().forEach(p ->{
                            p.playSound(p.getLocation(), "laugh", 1, 1);
                        });
                    }
                        break;

                    case "clown": {
                        // HANDLE CLOWN KILL

                    }
                        break;

                    default:
                        break;
                }
            } else {

                e.setDeathMessage(player.getName() + " was tricked by artificial intelligence");
            }

        }
    }

    @EventHandler
    public void onSpawnIntelligence(CreatureSpawnEvent e) {
        var entity = e.getEntity();
        var loc = entity.getLocation();
        var game = instance.getGame();
        var intelligence = loc.getNearbyEntities(32, 100, 32).stream()
                .filter(radius -> radius instanceof ArmorStand && radius.getCustomName() != null
                        && radius.getCustomName().contains("Raid"))
                .map(ent -> (ArmorStand) ent).collect(Collectors.toList());

        var entry = game.getIntelligence().entrySet().stream().filter(val -> val.getValue()).findAny();

        if (!intelligence.isEmpty() && entity instanceof Zombie && entry.isPresent()) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (player.getName() == entry.get().getKey()) {
                    // ALL DISGUISE INTELLIGENCE

                    var zombiePlayers = game.getDeathPlayers().keySet().stream().collect(Collectors.toList());
                    var name = "&c" + zombiePlayers.get(random.nextInt(zombiePlayers.size()));

                    Bukkit.dispatchCommand(player, "disguise zombie setcustomname \"" + name
                            + "\" setcustomnamevisible false setSelfDisguiseVisible false");

                    var inv = player.getInventory();
                    inv.addItem(new ItemBuilder(Material.NETHERITE_PICKAXE).enchant(Enchantment.DIG_SPEED, 5).build());

                }
            });
        }
    }

    @EventHandler
    public void onDrop(ItemSpawnEvent e) {
        var item = e.getEntity().getItemStack();
        if (item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 666) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        var player = e.getPlayer();
        if (DisguiseAPI.isDisguised(player)) {
            e.setQuitMessage("");
        }
    }

    @EventHandler
    public void velocity(PlayerVelocityEvent e) {
        var player = e.getPlayer();
        if (DisguiseAPI.isDisguised(player)) {
            var disguises = instance.getGame().getDisguises();
            if (disguises.containsKey(player.getName())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void powers(PlayerInteractEvent e) {
        var player = e.getPlayer();
        var item = e.getItem();
        if (item != null && DisguiseAPI.isDisguised(player)) {
            var string = item.getItemMeta().getDisplayName().toString();
            var loc = player.getLocation();
            if (string.contains("Explosion")) {
                loc.createExplosion(10, false, false);

                loc.getNearbyPlayers(100).stream().forEach(p -> {
                    p.playSound(p.getLocation(), "smash", 3, 1);
                });

            } else if (string.contains("Walk")) {
                if (player.hasPotionEffect(PotionEffectType.SPEED)) {
                    player.removePotionEffect(PotionEffectType.SPEED);
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 3, false, false));
                // sound walk

                loc.getNearbyPlayers(100).stream().forEach(p -> {
                    p.playSound(p.getLocation(), "steps", 3, 1);
                });

            } else if (string.contains("Speed")) {
                if (player.hasPotionEffect(PotionEffectType.SLOW)) {
                    player.removePotionEffect(PotionEffectType.SLOW);
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 0, false, false));
                // sound fast walk

                loc.getNearbyPlayers(100).stream().forEach(p -> {
                    p.playSound(p.getLocation(), "steps", 3, 1.5f);
                });

            } else if (string.contains("Jump")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 29, false, false));
                // sound jump

                loc.getNearbyPlayers(100).stream().forEach(p -> {
                    p.playSound(p.getLocation(), "smash", 3, 1);
                });

            } else if (string.contains("Roar")) {
                loc.getNearbyPlayers(40).stream().filter(p -> DisguiseAPI.isDisguised(p)).forEach(
                        p -> p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 4, false, false)));
                // sound roar

                loc.getNearbyPlayers(100).stream().forEach(p -> {
                    p.playSound(p.getLocation(), "roar", 3, 1);
                });

            } else if (string.contains("Laugh")) {
                // sound laugh

                loc.getNearbyPlayers(100).stream().forEach(p -> {
                    p.playSound(p.getLocation(), "laugh", 3, 1);
                });

            } else if (string.contains("Bomber")) {
                // bomber
                Bukkit.dispatchCommand(player, "summon tnt ~ ~ ~ {Fuse:80}");

            } else if (string.contains("kamikaze")) {
                // kamikaze
                player.getWorld().createExplosion(loc, 10);
            }
        }
    }

    @EventHandler
    public void onTarget(EntityTargetEvent e) {
        var target = e.getTarget();
        if (target != null && target instanceof Player) {
            var player = (Player) target;
            if (DisguiseAPI.isDisguised(player)) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        var entity = e.getEntity();
        var bow = e.getBow().getItemMeta();
        if (entity instanceof Player && DisguiseAPI.isDisguised(entity)) {

            if (bow.hasDisplayName() && bow.getDisplayName().toString().contains("Fireball")) {
                var fireball = (Fireball) e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation().add(0, 1, 0),
                        EntityType.FIREBALL);
                fireball.setYield(5);
                e.setProjectile(fireball);
            }
        }
    }

    public Integer chooseCoord(int radius) {
        var num = random.nextInt(radius);
        num = random.nextBoolean() ? ~(num) : num;
        return num;
    }

    @EventHandler
    public void damage(EntityDamageEvent e) {
        var entity = e.getEntity();
        if (entity instanceof Player) {
            var cause = e.getCause();

            var player = (Player) entity;
            if (DisguiseAPI.isDisguised(player)) {

                var loc = entity.getLocation();

                var disguises = instance.getGame().getDisguises();
                if (disguises.containsKey(player.getName())) {

                    if (cause == DamageCause.FALL || cause == DamageCause.LAVA || cause == DamageCause.FIRE
                            || cause == DamageCause.FIRE_TICK || cause == DamageCause.ENTITY_EXPLOSION
                            || cause == DamageCause.BLOCK_EXPLOSION || cause == DamageCause.HOT_FLOOR
                            || cause == DamageCause.WITHER) {
                        e.setCancelled(true);
                        return;
                    }

                    var mob = disguises.get(player.getName());
                    var damage = e.getDamage();
                    var bossbars = instance.getGame().getBossbars();
                    switch (mob) {

                        case "warden": {
                            double point = 0.00001;
                            var boss = bossbars.get("warden");
                            var health = boss.getProgress();

                            double finalHealth = health - (point * damage);
                            if (finalHealth <= 0) {
                                boss.setVisible(false);
                                player.damage(100);
                                return;
                            }

                            boss.setProgress(finalHealth);

                            loc.getNearbyPlayers(100).stream().forEach(p ->{
                                p.playSound(p.getLocation(), "warden_hurt", 1, 1.5f);
                            });
                        }
                            break;

                        case "redstone": {
                            double point = 0.00001;
                            var boss = bossbars.get("redstone");
                            var health = boss.getProgress();

                            double finalHealth = health - (point * damage);
                            if (finalHealth <= 0) {
                                boss.setVisible(false);
                                player.damage(100);
                                return;
                            }

                            boss.setProgress(finalHealth);

                            loc.getNearbyPlayers(100).stream().forEach(p ->{
                                p.playSound(p.getLocation(), Sound.ENTITY_RAVAGER_HURT, 1, 0.5f);
                            });

                        }
                            break;

                        case "clown": {
                            // HANDLE CLOWN DAMAGE

                        }
                            break;

                        default:
                            break;
                    }

                    e.setDamage(0);

                }
            }

        }

    }

}