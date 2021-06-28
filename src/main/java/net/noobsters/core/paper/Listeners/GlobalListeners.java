package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.GameTickEvent;
import net.noobsters.core.paper.PERMADED;

public class GlobalListeners implements Listener{

    PERMADED instance;
    Random random = new Random();
    static public String NO_RP_EN = ChatColor.AQUA + "You need the custom resourcepack to play!\n " +
         ChatColor.GREEN + "1. Download the resource pack. \n" +
         ChatColor.YELLOW + "https://www.dropbox.com/s/4qotet9czhvkhv1/PERMADED.zip?dl=1 \n" + 
         ChatColor.GREEN + "2. Paste the file into the resource folder in Minecraft. \n" + 
         ChatColor.GREEN + "3. Select the resource pack in the game and select the custom language: 'PERMADED(NIGHTMARE)' \n";

    static public String NO_RP_ES = ChatColor.AQUA + "Necesitas el texture pack para jugar!\n " +
         ChatColor.GREEN + "1. Descarga el texture pack. \n" +
         ChatColor.YELLOW + "https://www.dropbox.com/s/4qotet9czhvkhv1/PERMADED.zip?dl=1 \n" + 
         ChatColor.GREEN + "2. Pega el archivo en el resource pack folder en la carpeta de Minecraft. \n" + 
         ChatColor.GREEN + "3. Activa el texture pack en el juego y después selecciona el lenguaje custom: 'PERMADED(NIGHTMARE)' \n";

    static public String NO_TXT = ChatColor.AQUA + "Necesitas el cliente para jugar!\n ";

    static public String NO_JUGAR = ChatColor.AQUA + "El server esta cerrado!\n ";

    GlobalListeners(PERMADED instance){
        this.instance = instance;
    }
    
    @EventHandler
    public void onResourcePackChange(PlayerLocaleChangeEvent e){
        var player = e.getPlayer();
        var locale = e.getLocale().toString();
        var game = instance.getGame();
        if(!locale.contains("NOOBSTERS")){
            player.kickPlayer(NO_TXT);
        }else if(!locale.contains("NOOBSTERS_7")){
            player.kickPlayer(NO_TXT + ChatColor.RED + "\n Hay otra actualizacion del texture pack, descarga la ultima!");
            
        }else if(!player.hasPermission("mod.perm") && game.isClosed()){
            player.kickPlayer(NO_JUGAR);
        }
    }

    /*
    @EventHandler
    public void onDrownedDeath(EntityDeathEvent e) {
        var entity = e.getEntity();

        if(entity instanceof Drowned){

            var drowned = (Drowned) e.getEntity();
            var equipment = drowned.getEquipment();
            var mainHand = drowned.getEquipment().getItemInMainHand().clone();
            var offHand = drowned.getEquipment().getItemInOffHand().clone();
            equipment.clear();
    
            if (mainHand.getType() != Material.AIR) {
                if (mainHand.getType() == Material.TRIDENT) {
                    Damageable dmg = (Damageable) mainHand.getItemMeta();
                    dmg.setDamage(new Random().nextInt(125));
                    mainHand.setItemMeta((ItemMeta) dmg);

                    if(random.nextBoolean()) return;
    
                }

                drowned.getLocation().getWorld().dropItemNaturally(drowned.getLocation(), mainHand);
    
            }
            if (offHand.getType() != Material.AIR) {
                if (offHand.getType() == Material.TRIDENT) {
                    Damageable dmg = (Damageable) offHand.getItemMeta();
                    dmg.setDamage(new Random().nextInt(125));
                    offHand.setItemMeta((ItemMeta) dmg);

                    if(random.nextBoolean()) return;
                }

                drowned.getLocation().getWorld().dropItemNaturally(drowned.getLocation(), offHand);
            }
        }
    }*/

    @EventHandler
    public void onDamage(PlayerDeathEvent e){
        var cause = e.getEntity().getLastDamageCause().getCause();

        if(cause == DamageCause.CUSTOM || cause == DamageCause.WITHER){
            e.setDeathMessage(e.getEntity().getName() + " died from radiation");
        }
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        var entity = e.getEntity();
        var bow = e.getBow().getItemMeta();
        var proj = e.getProjectile();
        if (entity instanceof Player) {
            var player = (Player) entity;
            if (bow.getDisplayName().toString().contains("Meteor") && player.getGameMode() == GameMode.CREATIVE) {
                proj.setCustomName("Meteor");

                var armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
                armorStand.setCustomName("Meteor");
                armorStand.setGlowing(true);
                armorStand.setBodyPose(EulerAngle.ZERO);
                var meteor = new ItemStack(Material.WOODEN_HOE);
                var meta =  meteor.getItemMeta();
                meta.setDisplayName(ChatColor.BLACK + "Meteor");
                meta.setCustomModelData(6);
                meteor.setItemMeta(meta);
                armorStand.getEquipment().setItemInMainHand(meteor);
                proj.addPassenger(armorStand);



                Bukkit.getOnlinePlayers().forEach(p->{
                    p.playSound(p.getLocation(), "meteorito_cayendo", 1, 1);
                });
            }
        }
    }


    
    @EventHandler
    public void impact(ProjectileHitEvent e) {
        var entity = e.getHitEntity();
        var block = e.getHitBlock();
        var projectile = e.getEntity();
        
        if(projectile.getCustomName() != null && projectile.getCustomName().toString().contains("Meteor")){
            if(entity != null){
                var loc = entity.getLocation();
                loc.add(0, -10, 0).createExplosion(100, true);
                entity.getWorld().strikeLightning(entity.getLocation());
                entity.getWorld().strikeLightning(entity.getLocation());
                entity.getWorld().strikeLightning(entity.getLocation());

                Bukkit.getOnlinePlayers().forEach(p ->{
                    p.playSound(p.getLocation(), "meteorito_impacto", 1, 1);
                });
                
            }else if(block != null){
                var loc = block.getLocation();
                loc.add(0, -10, 0).createExplosion(100, true);
                block.getWorld().strikeLightning(block.getLocation());
                block.getWorld().strikeLightning(block.getLocation());
                block.getWorld().strikeLightning(block.getLocation());

                Bukkit.getOnlinePlayers().forEach(p ->{
                    p.playSound(p.getLocation(), "meteorito_impacto", 1, 1);
                });
                
            }

            
        }
    }

    public Integer chooseCoord(int radius) {
        var num = random.nextInt(radius);
        num = random.nextBoolean() ? ~(num) : num;
        return num;
    }

    public Location randomCoord(Location loc, int radius){
        var location = new Location(loc.getWorld(), chooseCoord(radius), 80, chooseCoord(radius));
        location.setY(loc.getWorld().getHighestBlockYAt(location)+3);
        return location;
    }

    public void spawnPiglinPatrol(Location loc) {

        var beast = (Hoglin) loc.getWorld().spawnEntity(loc, EntityType.HOGLIN);
        beast.setImmuneToZombification(true);
        beast.setAdult();

        var riding = loc.getWorld().spawnEntity(loc, EntityType.PIGLIN);
        beast.addPassenger(riding);

        loc.getWorld().spawnEntity(loc.add(5, 0, 5), EntityType.PIGLIN);

        loc.getWorld().spawnEntity(loc.add(-5, 0, 5), EntityType.PIGLIN);

        loc.getWorld().spawnEntity(loc.add(5, 0, -5), EntityType.PIGLIN);

        loc.getWorld().spawnEntity(loc.add(-5, 0, -5), EntityType.PIGLIN);

    }



    public void spawnPillagerPatrol(Location loc) {

        var beast = loc.getWorld().spawnEntity(loc, EntityType.RAVAGER);

        var riding = loc.getWorld().spawnEntity(loc, EntityType.EVOKER);
        beast.addPassenger(riding);

        loc.getWorld().spawnEntity(loc.add(-5, 0, 5), EntityType.PILLAGER);

        loc.getWorld().spawnEntity(loc.add(5, 0, -5), EntityType.PILLAGER);

        loc.getWorld().spawnEntity(loc.add(-5, 0, -5), EntityType.PILLAGER);

        loc.getWorld().spawnEntity(loc.add(5, 0, 5), EntityType.VINDICATOR);

    }

    @EventHandler
    public void customSpawns(GameTickEvent e) {
        var game = instance.getGame();
        var difficulty = game.getDifficultyChanges();
        final var second = e.getSecond();

        if(difficulty.get("pigcapenable") && second % 300 == 0){
            difficulty.put("pigcap", !difficulty.get("pigcap"));
        }


        if (difficulty.get("raids") && second % instance.getGame().getSpawnPatrolDelay() == 0) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                var loc = player.getLocation();
                Bukkit.getScheduler().runTask(instance, () -> {
                    
                    if (player.getGameMode() != GameMode.SPECTATOR && player.getWorld() == Bukkit.getWorld("world") && player.getLocation().getY() > 45) {
                        switch (random.nextInt(2)) {
                            case 1: {
                                var spawn = player.getLocation().add(chooseCoord(20), 0, chooseCoord(20));
                                spawn.setY(player.getWorld().getHighestBlockAt(spawn).getY()+3);
                                spawnPillagerPatrol(spawn);
                            }
                                break;

                            default: {
                                var spawn = player.getLocation().add(chooseCoord(20), 3, chooseCoord(20));
                                spawn.setY(player.getWorld().getHighestBlockAt(spawn).getY());
                                spawnPiglinPatrol(spawn);
                            }
                                break;
                        }

                    } else if (player.getWorld().getEnvironment() == Environment.THE_END) {
                        // soul ghasts end
                        loc.getWorld().spawnEntity(loc.add(-15, 20, -15), EntityType.GHAST);

                        loc.getWorld().spawnEntity(loc.add(15, 20, 15), EntityType.GHAST);

                    }else if (player.getWorld().getEnvironment() == Environment.NORMAL && player.getWorld() != Bukkit.getWorld("valhalla") 
                        && (second % instance.getGame().getSpawnPatrolDelay()*2.5) == 0) {
                        // soul ghasts over
                        loc.getWorld().spawnEntity(loc.add(25, 20, -25), EntityType.GHAST);

                    }else if (player.getWorld().getEnvironment() != Environment.NETHER) {
                        // blaze over
                        loc.getWorld().spawnEntity(loc.add(-15, 10, 25), EntityType.BLAZE);
                    }

                });
            });
        }
    }

    @EventHandler
    public void onJoinHide(PlayerJoinEvent e) {
        var player = e.getPlayer();
        // If gamemode is Spectator, then hide him from all other non spectators
        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) {
            Bukkit.getOnlinePlayers().stream().filter(all -> all.getGameMode() == GameMode.SURVIVAL)
                    .forEach(all -> all.hidePlayer(instance, player));
        } else {
            // If gamemode isn't Spectator, then hide all spectators for him.
            Bukkit.getOnlinePlayers().stream().filter(it -> it.getGameMode() != GameMode.SURVIVAL)
                    .forEach(all -> player.hidePlayer(instance, all.getPlayer()));
        }
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e) {
        var player = e.getPlayer();
        // If gamemode to change is spectator
        if (e.getNewGameMode() != GameMode.SURVIVAL) {

            Bukkit.getOnlinePlayers().stream().forEach(all -> {
                // If players are not specs, hide them the player
                if (all.getGameMode() == GameMode.SURVIVAL) {
                    all.hidePlayer(instance, player);
                } else {
                    // If players are specs, then show them to the player
                    player.showPlayer(instance, all);
                }
            });
        } else {
            Bukkit.getOnlinePlayers().stream().forEach(all -> {
                // When switching to other gamemodes, show them if not visible to player
                if (!all.canSee(player)) {
                    all.showPlayer(instance, player);
                }
                // If one of the players is a spec, hide them from the player
                if (all.getGameMode() != GameMode.SURVIVAL) {
                    player.hidePlayer(instance, all);
                }
            });
        }
    }


}