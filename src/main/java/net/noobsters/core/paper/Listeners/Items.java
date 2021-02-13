package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.noobsters.core.paper.PERMADED;

public class Items implements Listener{

    PERMADED instance;
    Random random = new Random();

    Items(PERMADED instance){
        this.instance = instance;
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemConsume(PlayerItemConsumeEvent e) {
        var item = e.getItem().getType();
        if (item == Material.AIR || item != Material.CHORUS_FRUIT || !e.getItem().hasItemMeta())
            return;
        ItemMeta itemMeta = e.getItem().getItemMeta();
        if (itemMeta.hasCustomModelData() && itemMeta.getCustomModelData() == 136) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 2, 100));
        }
    }
    

}