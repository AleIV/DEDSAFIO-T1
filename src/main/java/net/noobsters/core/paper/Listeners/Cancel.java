package net.noobsters.core.paper.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cod;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PolarBear;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Salmon;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Strider;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import net.noobsters.core.paper.PERMADED;

public class Cancel implements Listener {

    PERMADED instance;
    List<Material> items = new ArrayList<>();

    Cancel(PERMADED instance) {
        this.instance = instance;

        //CANCEL CRAFTS
        items.add(Material.CUT_RED_SANDSTONE);
        items.add(Material.CUT_RED_SANDSTONE_SLAB);
        items.add(Material.RED_SANDSTONE_WALL);
        items.add(Material.TRAPPED_CHEST);


        items.add(Material.GOLDEN_HELMET);
        items.add(Material.GOLDEN_CHESTPLATE);
        items.add(Material.GOLDEN_LEGGINGS);
        items.add(Material.GOLDEN_BOOTS);

        items.add(Material.NAME_TAG);
        items.add(Material.ARMOR_STAND);
    }

    @EventHandler
    public void onDrop(ItemSpawnEvent e){
        var item = e.getEntity().getItemStack();
        if(items.contains(item.getType())){
            e.setCancelled(true);
        }else if(item.getItemMeta().hasCustomModelData() && 
        item.getItemMeta().getCustomModelData() == 114){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onOpenChest(InventoryOpenEvent e){
        if(e.getInventory().getType().toString().equals("CHEST")){
            var stuff = e.getInventory().getContents();
            for (int i = 0; i < stuff.length; i++) {
                if(stuff[i] != null && items.contains(stuff[i].getType())){
                    stuff[i].setType(Material.DIAMOND);
                }
            }
        }
    }

    @EventHandler
    public void onTrade(VillagerAcquireTradeEvent e){
        if(items.contains(e.getRecipe().getResult().getType())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void cancelBlocks(PrepareItemCraftEvent e){
        if(e.getRecipe() == null || e.getRecipe().getResult() == null) return;

        var item = e.getRecipe().getResult().getType();

        if(items.contains(item)){
            e.getInventory().setResult(new ItemStack(Material.AIR));

        }else if(item.toString().contains("CONCRETE")){
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }

    }

    @EventHandler
    public void onCreature(CreatureSpawnEvent e){
        var entity = e.getEntity();
        final var difficulty = instance.getGame().getDifficultyChanges();

        if(entity instanceof Sheep){
            e.setCancelled(true);

        }else if(entity instanceof Panda){
            e.setCancelled(true);

        }else if(entity instanceof Horse && entity.getWorld() == Bukkit.getWorld("world")){
            e.setCancelled(true);

        }else if(entity instanceof Salmon){
            e.setCancelled(true);

        }else if(entity instanceof Cod){
            e.setCancelled(true);

        }else if(entity instanceof Fish){
            e.setCancelled(true);

        }else if(entity instanceof TropicalFish){
            e.setCancelled(true);

        }else if(entity instanceof Squid){
            e.setCancelled(true);

        }else if(entity instanceof Pig && !difficulty.get("pigs")){
            e.setCancelled(true);

        }else if(entity instanceof Cow && !difficulty.get("raiders")){
            e.setCancelled(true); 

        }else if(entity instanceof MushroomCow && !difficulty.get("raiders")){
            e.setCancelled(true); 

        }else if(entity instanceof Donkey){
            e.setCancelled(true);

        }else if(entity instanceof Cat){
            e.setCancelled(true);

        }else if(entity instanceof Bat){
            e.setCancelled(true);

        }else if(entity instanceof Parrot){
            e.setCancelled(true);

        }else if(entity instanceof Ocelot){
            e.setCancelled(true);

        }else if(entity instanceof Strider){
            e.setCancelled(true);

        }else if(entity instanceof WanderingTrader && !difficulty.get("villager")){
            e.setCancelled(true);

        }else if(entity instanceof Llama){
            e.setCancelled(true);

        }else if(entity instanceof Dolphin){
            e.setCancelled(true);

        }else if(entity instanceof Fox){
            e.setCancelled(true);

        }else if(entity instanceof Wolf){
            e.setCancelled(true);

        }else if(entity instanceof Villager && !difficulty.get("villager")){
            e.setCancelled(true);

        }else if(entity instanceof Chicken){
            e.setCancelled(true);

        }else if(entity instanceof PolarBear){
            e.setCancelled(true);

        }else if(entity instanceof Rabbit){
            e.setCancelled(true);
        }
    }

}