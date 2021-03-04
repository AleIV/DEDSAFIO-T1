package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Ravager;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class Riders implements Listener {

    PERMADED instance;
    Random random = new Random();

    Riders(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void riders(CreatureSpawnEvent e) {
        var entity = e.getEntity();
        var difficulty = instance.getGame().getDifficultyChange();
        if (difficulty >= 6 && entity instanceof Evoker) {
            var evoker = (Evoker) entity;
            switch (random.nextInt(3)) {
                case 1: {
                    evoker.setCustomName(ChatColor.AQUA + "Warped Mage");
                }
                    break;

                case 2: {
                    evoker.setCustomName(ChatColor.AQUA + "Purple Mage");
                }
                    break;

                default: {
                    e.setCancelled(true);
                    var illusioner = (Illusioner) evoker.getWorld().spawnEntity(evoker.getLocation(),
                            EntityType.ILLUSIONER);
                    illusioner.setCustomName(ChatColor.AQUA + "Illusioner");

                    var magicStaff = new ItemStack(Material.BOW);
                    var meta = magicStaff.getItemMeta();
                    meta.setCustomModelData(116);
                    meta.setDisplayName(ChatColor.DARK_AQUA + "Magic Staff");
                    meta.addEnchant(Enchantment.ARROW_DAMAGE, 4, true);
                    magicStaff.setItemMeta(meta);

                    illusioner.getEquipment().setItemInMainHand(magicStaff);
                }
                    break;
            }

        } else if (difficulty >= 6 && entity instanceof Ravager) {
            var ravager = (Ravager) entity;
            ravager.setCustomName(ChatColor.RED + "Ravager Powerful");
            ravager.setHealth(ravager.getHealth() / 2);

        } else if (difficulty >= 6 && ((entity instanceof Witch) || (entity instanceof Villager))) {
            e.setCancelled(true);
            entity.getWorld().spawnEntity(entity.getLocation(), EntityType.EVOKER);

        } else if (difficulty >= 7 && entity instanceof Pillager) {
            var pillager = (Pillager) entity;
            var gun = new ItemStack(Material.CROSSBOW);
            var meta = (CrossbowMeta) gun.getItemMeta();
            switch (random.nextInt(3)) {
                case 1: {
                    meta.setDisplayName(ChatColor.GRAY + "Sniper");
                    meta.setCustomModelData(108);

                    pillager.setCustomName(ChatColor.RED + "Servant");
                }
                    break;

                case 2: {
                    meta.setDisplayName(ChatColor.GRAY + "Pistol");
                    meta.setCustomModelData(107);

                    pillager.setCustomName(ChatColor.RED + "Blacksmith");
                }
                    break;

                default: {
                    meta.setDisplayName(ChatColor.GRAY + "Pistol");
                    meta.setCustomModelData(109);

                    pillager.setCustomName(ChatColor.RED + "Butler");
                }
                    break;
            }

            gun.setItemMeta(meta);
            pillager.getEquipment().setItemInMainHand(gun);

        } else if (difficulty >= 7 && entity instanceof Vindicator) {
            var vindicator = (Vindicator) entity;
            vindicator.setCustomName(ChatColor.RED + "Mountaineer");
            vindicator.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 300, 1));

            var axe = new ItemStack(Material.NETHERITE_AXE);

            vindicator.getEquipment().setItemInMainHand(axe);

        }

    }

}