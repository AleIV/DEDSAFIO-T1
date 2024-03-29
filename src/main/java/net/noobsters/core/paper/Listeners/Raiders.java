package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Ravager;
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

public class Raiders implements Listener {

    PERMADED instance;
    Random random = new Random();

    Raiders(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void raiders(CreatureSpawnEvent e) {
        var entity = e.getEntity();
        var difficulty = instance.getGame().getDifficultyChanges();
        if (difficulty.get("mages") && entity instanceof Evoker) {
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

        } else if (difficulty.get("raiders") && entity instanceof Ravager) {
            var ravager = (Ravager) entity;

            if(ravager.getLocation().getY() < 55){
                ravager.setCustomName(ChatColor.RED + "Redstone Golem");
                
            }else{
                ravager.setCustomName(ChatColor.BLUE + "Ravager Powerful");
            }

        }else if (difficulty.get("raiders") && entity instanceof Pillager) {
            
            var pillager = (Pillager) entity;
            var gun = new ItemStack(Material.CROSSBOW);
            var meta = (CrossbowMeta) gun.getItemMeta();
            switch (random.nextInt(3)) {
                case 1: {
                    meta.setDisplayName(ChatColor.GRAY + "Sniper");
                    meta.setCustomModelData(108);

                    pillager.setCustomName(ChatColor.GOLD + "Servant");
                }
                    break;

                case 2: {
                    meta.setDisplayName(ChatColor.GRAY + "Pistol");
                    meta.setCustomModelData(107);

                    pillager.setCustomName(ChatColor.GOLD + "Blacksmith");
                }
                    break;

                default: {
                    meta.setDisplayName(ChatColor.GRAY + "Pistol");
                    meta.setCustomModelData(109);

                    pillager.setCustomName(ChatColor.GOLD + "Butler");
                }
                    break;
            }

            gun.setItemMeta(meta);
            pillager.getEquipment().setItemInMainHand(gun);

        } else if (difficulty.get("raiders") && entity instanceof Vindicator) {
            var vindicator = (Vindicator) entity;
            vindicator.setCustomName(ChatColor.GOLD + "Mountaineer");
            vindicator.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 300, 1));

            var axe = new ItemStack(Material.NETHERITE_AXE);

            vindicator.getEquipment().setItemInMainHand(axe);

        }else if(difficulty.get("mages") && entity instanceof Witch){
            e.setCancelled(true);
            entity.getWorld().spawnEntity(entity.getLocation(), EntityType.EVOKER);
        }

    }

}