package net.noobsters.core.paper.Listeners;

import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.noobsters.core.paper.GameTickEvent;
import net.noobsters.core.paper.PERMADED;

public class FinalBoss implements Listener {

    Location blood1 = new Location(Bukkit.getWorld("FINALFIGHT"), -1060, 146, 67);
    Location blood2 = new Location(Bukkit.getWorld("FINALFIGHT"), -945, 21, 141);

    PERMADED instance;
    Random random = new Random();

    FinalBoss(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onTick(GameTickEvent e) {
        var difficulty = instance.getGame().getDifficultyChanges();
        Bukkit.getScheduler().runTask(instance, () -> {
            if (difficulty.get("finalboss")) {

                playersRefresh();

            }

        });

    }

    public boolean isInCube(Location point, Location pos1, Location pos2) {

        var cX = pos1.getX() < pos2.getX();
        var cY = pos1.getZ() < pos2.getZ();
        var cZ = pos1.getZ() < pos2.getZ();

        var minX = cX ? pos1.getX() : pos2.getX();
        var maxX = cX ? pos2.getX() : pos1.getX();

        var minY = cY ? pos1.getY() : pos2.getY();
        var maxY = cY ? pos2.getY() : pos1.getY();

        var minZ = cZ ? pos1.getZ() : pos2.getZ();
        var maxZ = cZ ? pos2.getZ() : pos1.getZ();

        if (point.getX() < minX || point.getY() < minY || point.getZ() < minZ)
            return false;
        if (point.getX() > maxX || point.getY() > maxY || point.getZ() > maxZ)
            return false;

        return true;
    }

    public void playersRefresh() {

        Bukkit.getOnlinePlayers().stream().forEach(player -> {

            var slowfalling = player.getNearbyEntities(16, 16, 16).stream()
                    .filter(slowStand -> slowStand instanceof ArmorStand && slowStand.getCustomName() != null
                            && slowStand.getCustomName().contains("slow"))
                    .map(e -> (ArmorStand) e).collect(Collectors.toList());

            if (!slowfalling.isEmpty()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 5, 1));

            }

            if (player.getWorld() == Bukkit.getWorld("FINALFIGHT") && isInCube(player.getLocation(), blood1, blood2)) {

                var name = player.getName();
                var character = 92;
                var charac = Character.toString((char) character);

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "title " + name + " actionbar {\"text\":\"" + charac + "uE4A8" + "\"}");
            }

        });

    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        var entity = e.getEntity();

        if (entity.getWorld() == Bukkit.getWorld("FINALFIGHT")) {

        }
    }

}