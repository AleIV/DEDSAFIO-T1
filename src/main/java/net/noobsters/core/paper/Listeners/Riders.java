package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Bee;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.Ravager;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class Riders implements Listener{

    PERMADED instance;
    Random random = new Random();

    Riders(PERMADED instance){
        this.instance = instance;
    }
    
    @EventHandler
    public void riders(CreatureSpawnEvent e) {
        var entity = e.getEntity();
        var difficulty = instance.getGame().getDifficultyChange();
        if (difficulty >= 6 && entity instanceof Evoker) {
            var evoker = (Evoker) entity;
            switch (random.nextInt(3)) {
                case 1:{
                    evoker.setCustomName(ChatColor.AQUA + "Warped Mage");
                }break;

                case 2:{
                    evoker.setCustomName(ChatColor.AQUA + "Purple Mage");
                }break;
            
                default:{
                    e.setCancelled(true);
                    var illusioner = (Illusioner) evoker.getWorld().spawnEntity(evoker.getLocation(), EntityType.ILLUSIONER);
                    illusioner.setCustomName(ChatColor.AQUA + "Illusioner");

                    var magicStaff = new ItemStack(Material.BOW);
                    var meta = magicStaff.getItemMeta();
                    meta.setCustomModelData(116);
                    meta.setDisplayName(ChatColor.DARK_AQUA + "Magic Staff");
                    meta.addEnchant(Enchantment.ARROW_DAMAGE, 4, true);
                    magicStaff.setItemMeta(meta);

                    illusioner.getEquipment().setItemInMainHand(magicStaff);
                }break;
            }

        }else if(difficulty >= 6 && entity instanceof Ravager){
            var ravager = (Ravager) entity;
            ravager.setCustomName(ChatColor.RED + "Ravager Powerful");
            ravager.setHealth(ravager.getHealth()/2);

        }else if(difficulty >= 6 && ((entity instanceof Witch) || (entity instanceof Bee) || (entity instanceof Villager))){
            e.setCancelled(true);
            entity.getWorld().spawnEntity(entity.getLocation(), EntityType.EVOKER);
        }
        

    }

}