package net.noobsters.core.paper.Listeners;

import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vex;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
    public void onJoin(PlayerJoinEvent e) {
        var player = e.getPlayer();
        var boss = instance.getGame().getBossbars();
        boss.values().forEach(bossbar -> {
            bossbar.addPlayer(player);
        });
    }

    @EventHandler
    public void breakcancelstand(BlockBreakEvent e) {
        var block = e.getBlock();
        var stands = block.getLocation().getNearbyEntities(16, 16, 16).stream()
                .filter(stand -> stand instanceof ArmorStand && stand.getCustomName() != null
                        && stand.getCustomName().contains("Break"))
                .map(ent -> (ArmorStand) ent).collect(Collectors.toList());
        if (!stands.isEmpty()) {

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void placecancel(BlockPlaceEvent e) {
        var block = e.getBlock();
        var stands = block.getLocation().getNearbyEntities(16, 16, 16).stream()
                .filter(stand -> stand instanceof ArmorStand && stand.getCustomName() != null
                        && stand.getCustomName().contains("Break"))
                .map(ent -> (ArmorStand) ent).collect(Collectors.toList());
        if (!stands.isEmpty()) {

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(ItemSpawnEvent e) {
        var item = e.getEntity().getItemStack();
        var game = instance.getGame();
        if (game.isGulak() && item.getType() == Material.TNT) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        var game = instance.getGame();

        if (item == null) {
            return;
        }

        if (item.equals(game.getTnt())) {
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

    public void playersRefresh() {

        var game = instance.getGame();
        var difficulty = game.getDifficultyChanges();

        Bukkit.getOnlinePlayers().stream().forEach(player -> {

            if (player.getWorld() == Bukkit.getWorld("world")) {
                var meteors = player.getNearbyEntities(32, 100, 32).stream()
                        .filter(meteor -> meteor instanceof ArmorStand && meteor.getCustomName() != null
                                && meteor.getCustomName().contains("Meteor"))
                        .map(e -> (ArmorStand) e).collect(Collectors.toList());
                if (!meteors.isEmpty()) {

                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 1));
                }

                var blinds = player.getNearbyEntities(16, 16, 16).stream()
                        .filter(blindStand -> blindStand instanceof ArmorStand && blindStand.getCustomName() != null
                                && blindStand.getCustomName().contains("blind"))
                        .map(e -> (ArmorStand) e).collect(Collectors.toList());

                if (!blinds.isEmpty() && !player.hasPermission("mod.perm")) {

                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 0));
                }

                var blindlarge = player.getNearbyEntities(100, 100, 100).stream()
                        .filter(blindStand -> blindStand instanceof ArmorStand && blindStand.getCustomName() != null
                                && blindStand.getCustomName().contains("blindlarge"))
                        .map(e -> (ArmorStand) e).collect(Collectors.toList());

                if (!blindlarge.isEmpty() && !player.hasPermission("mod.perm")) {

                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 0));
                }
            }

            var equip = player.getEquipment();

            var helmet = equip.getHelmet();
            var chest = equip.getChestplate();
            var legs = equip.getLeggings();
            var boots = equip.getBoots();

            if (difficulty.get("environment") && player.getWorld() == Bukkit.getWorld("world_nether")) {

                if (helmet == null || chest == null || chest.getType().toString().contains("ELYTRA") || legs == null
                        || boots == null) {

                    player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                    player.setFireTicks(20 * 30);
                }
            }

            if (helmet != null && helmet.getItemMeta().hasCustomModelData() && helmet.getItemMeta().getCustomModelData() == 126
                && chest != null && chest.getItemMeta().hasCustomModelData() && chest.getItemMeta().getCustomModelData() == 126
                && legs != null && legs.getItemMeta().hasCustomModelData() && legs.getItemMeta().getCustomModelData() == 126
                && boots != null && boots.getItemMeta().hasCustomModelData() && boots.getItemMeta().getCustomModelData() == 126) {

                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 1));
                    
            }

            if (difficulty.get("blind") && !player.hasPermission("mod.perm")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 0));
            }

            // CUSTOM TOTEMS

            var totem = getTotemOnUse(player);

            if (difficulty.get("totems") && totem != null && isSpecialTotem(totem)) {
                var data = totem.getItemMeta().getCustomModelData();

                switch (data) {
                    case 2: {
                        // CHICKEN TOTEM
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 5, 1));
                    }
                        break;

                    case 6: {
                        // RAVAGER TOTEM
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 2));
                    }
                        break;

                    case 37: {
                        // BLAZE TOTEM
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 5, 0));
                    }
                        break;

                    case 39: {
                        // AMONG US TOTEM
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 5, 0));
                    }
                        break;

                    case 9: {
                        // GATOTEM
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 0));
                    }
                        break;

                    case 4: {
                        // SATURATION TOTEM VILLAGER
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 20 * 5, 0));
                    }
                        break;

                    case 32: {
                        // puffer fish TOTEM
                        player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 20 * 5, 0));
                    }
                        break;

                    case 22: {
                        // SLIME TOTEM
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 5, 2));
                    }
                        break;

                    case 23: {
                        // DRAGON TOTEM
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 5, 2));
                    }
                        break;

                    case 27: {
                        // WITCH TOTEM
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 0));
                    }
                        break;

                    case 16: {
                        // PHANTOM TOTEM
                        // player.setStatistic(Statistic.TIME_SINCE_REST, 0);
                    }
                        break;

                    case 31: {
                        // GOLEM TOTEM

                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 0));

                    }
                        break;

                    default:
                        break;
                }
            }

        });

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent e) {
        var entity = e.getEntity();
        var damager = e.getDamager();
        if (entity instanceof Player) {
            var player = (Player) entity;

            var totem = getTotemOnUse(player);

            if (totem != null && isSpecialTotem(totem)) {
                var data = totem.getItemMeta().getCustomModelData();

                switch (data) {
                    case 8: {
                        // GUARDIAN TOTEM
                        if (damager instanceof Creature) {
                            var creature = (Creature) damager;
                            creature.damage(20);
                        }

                    }
                        break;

                    case 28: {
                        // GUARDIAN TOTEM
                        if (damager instanceof Vex) {
                            e.setDamage(e.getDamage() / 2);
                        }

                    }
                        break;

                    default:
                        break;
                }
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityDamage(EntityDamageEvent e) {
        var entity = e.getEntity();
        if (entity instanceof Player) {
            var player = (Player) entity;

            var totem = getTotemOnUse(player);
            var cause = e.getCause();

            if (totem != null && isSpecialTotem(totem)) {
                var data = totem.getItemMeta().getCustomModelData();

                switch (data) {
                    case 19: {
                        // GHAST TOTEM
                        if (cause == DamageCause.ENTITY_EXPLOSION || cause == DamageCause.BLOCK_EXPLOSION) {
                            e.setDamage(e.getDamage() / 2);
                        }

                    }
                        break;

                    case 33: {
                        // WITHER TOTEM
                        if (cause == DamageCause.WITHER) {
                            e.setDamage(e.getDamage() / 2);
                        }
                    }
                        break;

                    default:
                        break;
                }
            }
        }
    }

    @EventHandler
    public void onResu(EntityResurrectEvent e) {
        var entity = e.getEntity();
        var difficulty = instance.getGame().getDifficultyChanges();

        if (entity instanceof Player) {
            var player = (Player) entity;
            var loc = player.getLocation();

            if (!e.isCancelled()) {
                var totemOnUse = getTotemOnUse(player);
                if (difficulty.get("totems50") && !isSpecialTotem(totemOnUse) && random.nextBoolean()) {
                    e.setCancelled(true);

                } else if (isSpecialTotem(totemOnUse)) {
                    var data = totemOnUse.getItemMeta().getCustomModelData();
                    switch (data) {

                        case 25: {
                            // RESURRECTION TOTEM
                            player.teleport(new Location(Bukkit.getWorld("world"), -531.5, 200.5, -262.5));

                            Bukkit.getScheduler().runTaskLater(instance, task -> {
                                loc.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);
                                loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                            }, 20);

                        }
                            break;

                        case 18: {
                            // CREEPER TOTEM
                            var entitys = loc.getNearbyLivingEntities(7, ent -> ent instanceof Creature);
                            loc.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);
                            loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                            entitys.forEach(ent -> {
                                ent.damage(70);
                            });

                        }
                            break;

                        default:
                            break;
                    }
                }
            }
        }
    }

    public ItemStack getTotemOnUse(Player player) {
        var equip = player.getEquipment();
        var totemoff = equip.getItemInOffHand();
        var totemhand = equip.getItemInMainHand();

        if (isTotem(totemhand)) {
            return totemhand;
        } else if (isTotem(totemoff)) {
            return totemoff;
        }

        return null;
    }

    public boolean isNotTotem(ItemStack stack) {
        return stack == null || stack.getType() != Material.TOTEM_OF_UNDYING;
    }

    public boolean isTotem(ItemStack stack) {
        return stack != null && stack.getType() == Material.TOTEM_OF_UNDYING;
    }

    public boolean isSpecialTotem(ItemStack stack) {
        return isTotem(stack) && stack.getItemMeta().hasCustomModelData();
    }

}