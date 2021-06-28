package net.noobsters.core.paper.Listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
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

import co.aikar.taskchain.TaskChain;
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

    public void clownDeath(Location loc) {
        var clown = (Slime) loc.getWorld().spawnEntity(loc, EntityType.SLIME);
        clown.setCustomName(ChatColor.RED + "Dead Clown");
        clown.setSize(10);
        clown.setInvulnerable(true);
        clown.setSilent(true);
        clown.setPersistent(true);
        clown.setAI(false);
        
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
                    || disguise.getType().getEntityType() == EntityType.PHANTOM || disguise.getType().getEntityType() == EntityType.SLIME) {
                loc.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);

            } else {
                Bukkit.dispatchCommand(player, "particle minecraft:cloud ~ ~1 ~ .1 .1 .1 .03 10 force");
            }

            DisguiseAPI.undisguiseToAll(player);

            var disguises = game.getDisguises();
            if (disguises.containsKey(player.getName())) {
                var mob = disguises.get(player.getName());
                switch (mob) {
                    case "warden": {
                        // HANDLE WARDEN DEATH
                        disguises.remove(player.getName());

                        loc.getNearbyPlayers(100).stream().forEach(p -> {
                            p.playSound(p.getLocation(), "warden_death", 1, 0.3f);
                            p.playSound(p.getLocation(), "warden_death", 1, 0.5f);
                        });

                        createDeath(loc);

                    }
                        break;

                    case "redstone": {
                        // HANDLE REDSTONE DEATH
                        disguises.remove(player.getName());
                        loc.getNearbyPlayers(100).stream().forEach(p -> {
                            p.playSound(p.getLocation(), Sound.ENTITY_RAVAGER_DEATH, 1, 0.3f);
                            p.playSound(p.getLocation(), Sound.ENTITY_RAVAGER_DEATH, 1, 0.5f);
                        });

                        createDeath(loc);

                    }
                        break;

                    case "clown": {
                        // HANDLE CLOWN DEATH
                        disguises.remove(player.getName());
                        createDeath(loc);
                        clownDeath(loc);
                    }
                        break;

                    case "fake": {
                        // HANDLE DED CLOWN DEATH
                        disguises.remove(player.getName());
                        loc.getNearbyPlayers(100).stream().forEach(p -> {
                            p.playSound(p.getLocation(), "muerte", 1, 0.7f);
                            p.playSound(p.getLocation(), "muerte", 1, 0.8f);
                        });

                        createDeath(loc);
                    }
                        break;

                    default:
                        break;
                }
            }

        } else if (killer != null && killer instanceof Player && DisguiseAPI.isDisguised(killer)) {

            var killerPlayer = (Player) killer;
            var disguises = game.getDisguises();

            if (disguises.containsKey(killerPlayer.getName())) {
                var mob = disguises.get(killerPlayer.getName());
                switch (mob) {
                    case "warden": {
                        e.setDeathMessage(player.getName() + " was silenced to death by the " + ChatColor.DARK_AQUA
                                + "Warden Monstrosity");

                    }
                        break;

                    case "redstone": {
                        e.setDeathMessage(player.getName() + " was reduced to dust by the " + ChatColor.DARK_RED
                                + "Redstone Monstrosity");

                        loc.getNearbyPlayers(100).stream().forEach(p -> {
                            p.playSound(p.getLocation(), "laugh", 1, 1);
                        });
                    }
                        break;

                    case "clown": {
                        // HANDLE CLOWN KILL
                        e.setDeathMessage(
                                player.getName() + " was trampled by the " + ChatColor.DARK_PURPLE + "Death Clown");

                        loc.getNearbyPlayers(100).stream().forEach(p -> {
                            p.playSound(p.getLocation(), "muerte", 1, 1);
                        });

                    }
                        break;

                    case "fake": {
                        // HANDLE DED CLOWN KILL
                        e.setDeathMessage(player.getName() + " was trolled by the " + ChatColor.RED + "DED Clown");

                        loc.getNearbyPlayers(100).stream().forEach(p -> {
                            p.playSound(p.getLocation(), "muerte", 1, 0.7f);
                        });

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

        if (entity instanceof Zombie) {
            var intelligence = loc.getNearbyEntities(150, 30, 150).stream()
                    .filter(radius -> radius instanceof ArmorStand && radius.getCustomName() != null
                            && radius.getCustomName().contains("Raid"))
                    .map(ent -> (ArmorStand) ent).collect(Collectors.toList());

            var entry = game.getIntelligence().entrySet().stream().filter(val -> val.getValue()).findAny();

            if (!intelligence.isEmpty() && entry.isPresent()) {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (player.getName() == entry.get().getKey()) {
                        // ALL DISGUISE INTELLIGENCE

                        e.setCancelled(true);
                        player.setGameMode(GameMode.SURVIVAL);
                        player.teleport(entity.getLocation());

                        var zombiePlayers = game.getDeathPlayers().keySet().stream().collect(Collectors.toList());
                        var name = "&cZombie " + zombiePlayers.get(random.nextInt(zombiePlayers.size()));

                        Bukkit.dispatchCommand(player, "disguise zombie setcustomname \"" + name
                                + "\" setcustomnamevisible false setSelfDisguiseVisible false");

                        var inv = player.getInventory();
                        inv.clear();
                        inv.addItem(new ItemBuilder(Material.SHEARS).build());
                        inv.addItem(
                                new ItemBuilder(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 5).build());
                        inv.addItem(new ItemBuilder(Material.DIAMOND_SHOVEL).enchant(Enchantment.DIG_SPEED, 5).build());
                        inv.addItem(new ItemBuilder(Material.DIAMOND_AXE).enchant(Enchantment.DIG_SPEED, 5).build());

                        inv.addItem(new ItemBuilder(Material.WATER_BUCKET).amount(32).build());
                        inv.addItem(new ItemBuilder(Material.SPONGE).amount(32).build());
                        inv.addItem(new ItemBuilder(Material.COBBLESTONE).amount(64).build());

                        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 10000, 1));

                        game.getIntelligence().put(player.getName(), false);

                    }
                });
            }
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

    public void createDeath(Location loc) {

        var chain = PERMADED.newChain();

        chain.delay(20).sync(() -> {
            loc.getWorld().strikeLightning(loc);
        });

        chain.delay(20).sync(() -> {
            loc.getWorld().strikeLightning(loc);
        });

        chain.delay(20).sync(() -> {
            loc.getWorld().strikeLightning(loc);
        });

        chain.delay(20).sync(() -> {
            loc.getWorld().strikeLightning(loc);
        });

        chain.sync(TaskChain::abort).execute();
    }

    public void pisoton(Player player) {
        var loc = player.getLocation();
        var name = "Death Clown";
        var chain = PERMADED.newChain();

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded6" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded7" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded8" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded9" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded10" + "\" setcustomnamevisible false setSelfDisguiseVisible false");

            loc.createExplosion(10, false, false);

            loc.getNearbyPlayers(100).stream().forEach(p -> {
                p.playSound(p.getLocation(), "smash", 3, 2);
            });

        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded9" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded8" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded7" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded6" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \"" + name
                    + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.sync(TaskChain::abort).execute();

    }

    public void jump(Player player) {
        var loc = player.getLocation();
        var name = "Death Clown";
        var chain = PERMADED.newChain();

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 18 setcustomname \""
                    + "Ded11" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 18 setcustomname \""
                    + "Ded12" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 18 setcustomname \""
                    + "Ded13" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 18 setcustomname \""
                    + "Ded14" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 24, false, false));
            // sound jump

            loc.getNearbyPlayers(100).stream().forEach(p -> {
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 3, 2);
            });

            loc.createExplosion(10, false, false);

        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 18 setcustomname \""
                    + "Ded13" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 18 setcustomname \""
                    + "Ded12" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 18 setcustomname \""
                    + "Ded11" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 18 setcustomname \"" + name
                    + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.sync(TaskChain::abort).execute();

    }

    public void fangs(Player player) {
        var loc = player.getLocation();

        var name = "Death Clown";
        var chain = PERMADED.newChain();

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded2" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded3" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded4" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded5" + "\" setcustomnamevisible false setSelfDisguiseVisible false");

            List<String> list = new ArrayList<>();
            list.add("Bear Trap");
            list.add("Mouse Trap");
            list.add("Clap");

            var trapname = list.get(random.nextInt(list.size()));

            var fang1 = loc.getWorld().spawnEntity(loc.add(2, 0, 2), EntityType.EVOKER_FANGS);
            fang1.setCustomName(trapname);

            var fang2 = loc.getWorld().spawnEntity(loc.add(3, 0, 3), EntityType.EVOKER_FANGS);
            fang2.setCustomName(trapname);

            var fang3 = loc.getWorld().spawnEntity(loc.add(-2, 0, -2), EntityType.EVOKER_FANGS);
            fang3.setCustomName(trapname);

            var fang4 = loc.getWorld().spawnEntity(loc.add(-3, 0, -3), EntityType.EVOKER_FANGS);
            fang4.setCustomName(trapname);

            var fang5 = loc.getWorld().spawnEntity(loc.add(-4, 0, -4), EntityType.EVOKER_FANGS);
            fang5.setCustomName(trapname);

            var fang6 = loc.getWorld().spawnEntity(loc.add(4, 0, 4), EntityType.EVOKER_FANGS);
            fang6.setCustomName(trapname);

            var fang7 = loc.getWorld().spawnEntity(loc.add(-5, 0, -5), EntityType.EVOKER_FANGS);
            fang7.setCustomName(trapname);

            var fang8 = loc.getWorld().spawnEntity(loc.add(5, 0, 5), EntityType.EVOKER_FANGS);
            fang8.setCustomName(trapname);

        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded4" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded3" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \""
                    + "Ded2" + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \"" + name
                    + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.sync(TaskChain::abort).execute();

    }

    public void big(Player player) {
        var loc = player.getLocation();

        var name = "Death Clown";
        var chain = PERMADED.newChain();

        chain.delay(15).sync(() -> {
            loc.getNearbyPlayers(100).stream().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 3, 2);
            });
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 12 setcustomname \"" + name
                    + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            loc.getNearbyPlayers(100).stream().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 3, 2);
            });
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 14 setcustomname \"" + name
                    + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            loc.getNearbyPlayers(100).stream().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 3, 2);
            });
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 16 setcustomname \"" + name
                    + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            loc.getNearbyPlayers(100).stream().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 3, 2);
            });
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 18 setcustomname \"" + name
                    + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.sync(TaskChain::abort).execute();
    }

    public void small(Player player) {
        var loc = player.getLocation();

        var name = "Death Clown";

        var chain = PERMADED.newChain();

        chain.delay(15).sync(() -> {
            loc.getNearbyPlayers(100).stream().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 3, 2);
            });
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 16 setcustomname \"" + name
                    + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            loc.getNearbyPlayers(100).stream().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 3, 2);
            });
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 14 setcustomname \"" + name
                    + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            loc.getNearbyPlayers(100).stream().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 3, 2);
            });
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 12 setcustomname \"" + name
                    + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.delay(15).sync(() -> {
            loc.getNearbyPlayers(100).stream().forEach(p -> {
                p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 3, 2);
            });
            Bukkit.dispatchCommand(player, "disguise slime setSoundGroup IRON_GOLEM setSize 10 setcustomname \"" + name
                    + "\" setcustomnamevisible false setSelfDisguiseVisible false");
        });

        chain.sync(TaskChain::abort).execute();
    }

    @EventHandler
    public void powers(PlayerInteractEvent e) {
        var player = e.getPlayer();
        var item = e.getItem();
        if (item != null && player.hasPermission("mod.perm")) {
            var string = item.getItemMeta().getDisplayName().toString();
            var loc = player.getLocation();

            if (string.contains("Explosion")) {
                loc.createExplosion(10, false, false);

                loc.getNearbyPlayers(100).stream().forEach(p -> {
                    p.playSound(p.getLocation(), "smash", 3, 1);
                });

            } else if (string.contains("FangsSpec")) {
                List<String> list = new ArrayList<>();
                list.add("Bear Trap");
                list.add("Mouse Trap");
                list.add("Clap");

                var trapname = list.get(random.nextInt(list.size()));

                var fang1 = loc.getWorld().spawnEntity(loc.add(2, 0, 2), EntityType.EVOKER_FANGS);
                fang1.setCustomName(trapname);

                var fang2 = loc.getWorld().spawnEntity(loc.add(3, 0, 3), EntityType.EVOKER_FANGS);
                fang2.setCustomName(trapname);

                var fang3 = loc.getWorld().spawnEntity(loc.add(-2, 0, -2), EntityType.EVOKER_FANGS);
                fang3.setCustomName(trapname);

                var fang4 = loc.getWorld().spawnEntity(loc.add(-3, 0, -3), EntityType.EVOKER_FANGS);
                fang4.setCustomName(trapname);

                var fang5 = loc.getWorld().spawnEntity(loc.add(-4, 0, -4), EntityType.EVOKER_FANGS);
                fang5.setCustomName(trapname);

                var fang6 = loc.getWorld().spawnEntity(loc.add(4, 0, 4), EntityType.EVOKER_FANGS);
                fang6.setCustomName(trapname);

                var fang7 = loc.getWorld().spawnEntity(loc.add(-5, 0, -5), EntityType.EVOKER_FANGS);
                fang7.setCustomName(trapname);

                var fang8 = loc.getWorld().spawnEntity(loc.add(5, 0, 5), EntityType.EVOKER_FANGS);
                fang8.setCustomName(trapname);

            } else if (string.contains("Pisoton")) {
                // pisoton
                pisoton(player);

            } else if (string.contains("Big")) {
                // big
                big(player);

            } else if (string.contains("Small")) {
                // small
                small(player);

            } else if (string.contains("ClownJump")) {
                // jumpclown
                jump(player);

            } else if (string.contains("Fangs")) {
                // fangs
                fangs(player);

            } else if (string.contains("Stars")) {
                // sound stars

                Bukkit.dispatchCommand(player, "particle minecraft:end_rod ~ ~ ~ .1 .1 .1 .3 40 normal");

            } else if (string.contains("JumpWarden")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 24, false, false));
                // sound jump

                loc.getNearbyPlayers(100).stream().forEach(p -> {
                    p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 3, 2);
                });

                loc.createExplosion(10, false, false);

            } else if (string.contains("RoarWarden")) {
                loc.getNearbyPlayers(100).stream().filter(p -> !DisguiseAPI.isDisguised(p)).forEach(p -> {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 4, false, false));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 4, false, false));
                });

                // sound roarwarden

                if (random.nextBoolean()) {
                    loc.getNearbyPlayers(100).stream().forEach(p -> {
                        p.playSound(p.getLocation(), "warden_death", 1, 0.8f);
                    });
                } else {
                    loc.getNearbyPlayers(100).stream().forEach(p -> {
                        p.playSound(p.getLocation(), "warden_hurt", 1, 0.7f);
                    });
                }

            } else if (string.contains("SoundWarden")) {

                // sound sound

                if (random.nextBoolean()) {
                    loc.getNearbyPlayers(100).stream().forEach(p -> {
                        p.playSound(p.getLocation(), "warden_hurt", 1, 0.5f);
                    });
                } else {
                    loc.getNearbyPlayers(100).stream().forEach(p -> {
                        p.playSound(p.getLocation(), "warden_roar", 1, 0.5f);
                    });
                }

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
                loc.getNearbyPlayers(40).stream().filter(p -> !DisguiseAPI.isDisguised(p)).forEach(
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

                if (cause == DamageCause.WITHER) {
                    e.setCancelled(true);
                    return;
                }

                var loc = entity.getLocation();

                var disguises = instance.getGame().getDisguises();
                if (disguises.containsKey(player.getName())) {

                    if (cause == DamageCause.LAVA || cause == DamageCause.FIRE || cause == DamageCause.FIRE_TICK
                            || cause == DamageCause.HOT_FLOOR || cause == DamageCause.FALL
                            || cause == DamageCause.ENTITY_EXPLOSION || cause == DamageCause.BLOCK_EXPLOSION
                            || cause == DamageCause.MAGIC) {
                        e.setCancelled(true);
                        return;
                    }

                    var mob = disguises.get(player.getName());
                    var damage = e.getDamage();
                    var bossbars = instance.getGame().getBossbars();
                    switch (mob) {

                        case "warden": {
                            double point = 0.001;
                            var boss = bossbars.get("warden");
                            var health = boss.getProgress();

                            double finalHealth = health - (point * damage);
                            if (finalHealth <= 0) {
                                boss.setVisible(false);
                                player.damage(100);
                                boss.setProgress(1);
                                return;
                            }

                            boss.setProgress(finalHealth);

                            loc.getNearbyPlayers(100).stream().forEach(p -> {
                                p.playSound(p.getLocation(), "warden_hurt", 1, 1.5f);
                            });
                        }
                            break;

                        case "redstone": {
                            double point = 0.001;
                            var boss = bossbars.get("redstone");
                            var health = boss.getProgress();

                            double finalHealth = health - (point * damage);
                            if (finalHealth <= 0) {
                                boss.setVisible(false);
                                player.damage(100);
                                boss.setProgress(1);
                                return;
                            }

                            boss.setProgress(finalHealth);

                            loc.getNearbyPlayers(100).stream().forEach(p -> {
                                p.playSound(p.getLocation(), Sound.ENTITY_RAVAGER_HURT, 1, 0.5f);
                            });

                        }
                            break;

                        case "clown": {
                            // HANDLE CLOWN DAMAGE
                            double point = 0.0001;
                            var boss = bossbars.get("clown");
                            var health = boss.getProgress();

                            double finalHealth = health - (point * damage);
                            if (finalHealth <= 0) {
                                boss.setVisible(false);
                                player.damage(100);
                                boss.setProgress(1);
                                return;
                            }

                            boss.setProgress(finalHealth);

                            loc.getNearbyPlayers(100).stream().forEach(p -> {
                                p.playSound(p.getLocation(), "clown_damage", 1, 1);
                            });

                        }
                            break;

                        case "fake": {
                            double point = 0.001;
                            var boss = bossbars.get("fake");
                            var health = boss.getProgress();

                            double finalHealth = health - (point * damage);
                            if (finalHealth <= 0) {
                                boss.setVisible(false);
                                player.damage(100);
                                boss.setProgress(1);
                                return;
                            }

                            boss.setProgress(finalHealth);

                            loc.getNearbyPlayers(100).stream().forEach(p -> {
                                p.playSound(p.getLocation(), Sound.ENTITY_IRON_GOLEM_DEATH, 1, 0.1f);
                            });

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