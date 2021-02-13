package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Husk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class UnDead implements Listener{

    PERMADED instance;
    Random random = new Random();

    UnDead(PERMADED instance){
        this.instance = instance;
    }
    
    @EventHandler
    public void unDeadSpawns(CreatureSpawnEvent e) {
        var game = instance.getGame();
        if (e.getEntity() instanceof Husk && game.getDifficultyChange() >= 1) {
            var chef = (Husk) e.getEntity();

            var chefHat = new ItemStack(Material.CARVED_PUMPKIN);
            var chefHatMeta = chefHat.getItemMeta();
            chefHatMeta.setCustomModelData(71);
            chefHatMeta.setDisplayName(ChatColor.YELLOW + "Chef Hat");
            chefHatMeta.setUnbreakable(true);
            chefHat.setItemMeta(chefHatMeta);

            var chefOutfit = new ItemStack(Material.CHAINMAIL_LEGGINGS);
            var chefOutfitMeta = chefOutfit.getItemMeta();
            chefOutfitMeta.setCustomModelData(130);
            chefOutfitMeta.setDisplayName(ChatColor.YELLOW + "Chef Outfit");
            chefOutfitMeta.setUnbreakable(true);
            chefOutfit.setItemMeta(chefOutfitMeta);

            var taco = new ItemStack(Material.CHORUS_FRUIT);
            var tacoMeta = taco.getItemMeta();
            tacoMeta.setCustomModelData(136);
            tacoMeta.setDisplayName(ChatColor.DARK_GREEN + "Taco");
            taco.setItemMeta(tacoMeta);

            chef.setCustomName("Death Chef");
            chef.getEquipment().setHelmet(chefHat);
            chef.getEquipment().setLeggings(chefOutfit);
            chef.getEquipment().setItemInMainHand(taco);
            chef.getEquipment().setItemInMainHandDropChance(0.1f);

        }

    }

    @EventHandler
    public void sounds1(EntityDamageByEntityEvent e){
        var damager = e.getDamager();

        if(damager instanceof Husk){
            var loc = damager.getLocation();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound minecraft:burp ambient @a " + loc.getX() +" "+ loc.getY()+ " " + loc.getZ() + " 1 1");
        }

    }
}