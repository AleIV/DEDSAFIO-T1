package net.noobsters.core.paper.Listeners;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Piglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.FireworkMeta;

import net.md_5.bungee.api.ChatColor;
import net.noobsters.core.paper.PERMADED;

public class Animals implements Listener {

    PERMADED instance;
    Random random = new Random();

    Animals(PERMADED instance) {
        this.instance = instance;
    }

    @EventHandler
    public void animals(CreatureSpawnEvent e) {
        var entity = e.getEntity();
        var game = instance.getGame();
        var difficulty = game.getDifficultyChanges();
        var pigCap = difficulty.get("pigcap");
        
        if (difficulty.get("pigs") && entity instanceof Pig) {


            if(Bukkit.getWorld("world_nether") == entity.getWorld()){

                if (random.nextInt(175) == 1 && entity.getLocation().getY() < 100) {
                    var pig = (Pig) entity;
                    pig.setCustomName(ChatColor.AQUA + "Muddy Pig");
                    pig.setRemoveWhenFarAway(true);
    
                }else if(random.nextInt(8) == 1){
                    e.setCancelled(true);
                    entity.getWorld().spawnEntity(entity.getLocation(), EntityType.PIGLIN);

                }else{
                    e.setCancelled(true);
                }

            }else if(pigCap && random.nextInt(70) == 1){
                e.setCancelled(true);
                entity.getWorld().spawnEntity(entity.getLocation(), EntityType.PIGLIN);

            }else{
                e.setCancelled(true);
            }

        }else if(difficulty.get("pigs") && entity.getType() == EntityType.ZOMBIFIED_PIGLIN){

            e.setCancelled(true);
            if(difficulty.get("demons") && random.nextInt(30) == 1){
                entity.getWorld().spawnEntity(entity.getLocation(), EntityType.BLAZE);
                
            }else{
                entity.getWorld().spawnEntity(entity.getLocation(), EntityType.PIG);
            }

        }else if(difficulty.get("raiders") && (entity instanceof Cow || entity instanceof MushroomCow)){

            if(random.nextInt(30) == 1 && !(entity instanceof MushroomCow)){
                var cow = (Cow) entity;
                cow.setCustomName(ChatColor.AQUA + "Moobloom");
                
            }else{
                e.setCancelled(true);
                entity.getWorld().spawnEntity(entity.getLocation(), EntityType.PILLAGER);
            }

        } else if (difficulty.get("pigs") && entity instanceof Piglin) {
            var piglin = (Piglin) entity;
            piglin.setImmuneToZombification(true);
            piglin.setAdult();
            switch (random.nextInt(4)) {
                case 1: {

                    piglin.setCustomName(ChatColor.BLUE + "Piglin Gentleman");
                    var hat = new ItemStack(Material.CARVED_PUMPKIN);
                    var hatMeta = hat.getItemMeta();
                    hatMeta.setCustomModelData(69);
                    hatMeta.setDisplayName(ChatColor.YELLOW + "Black Hat");
                    hat.setItemMeta(hatMeta);

                    var weapon = new ItemStack(Material.GOLDEN_SWORD);
                    var weaponMeta = weapon.getItemMeta();
                    weaponMeta.setCustomModelData(86);
                    weaponMeta.setDisplayName(ChatColor.YELLOW + "Walking Stick");
                    weaponMeta.setUnbreakable(true);
                    weapon.setItemMeta(weaponMeta);

                    var equipment = piglin.getEquipment();
                    equipment.setHelmet(hat);
                    equipment.setItemInMainHand(weapon);

                }
                    break;

                case 2: {
                    piglin.setCustomName(ChatColor.BLUE + "Wizard Piglin");

                    var weapon = new ItemStack(Material.CROSSBOW);
                    var weaponMeta = weapon.getItemMeta();
                    weaponMeta.setCustomModelData(112);
                    weaponMeta.setDisplayName(ChatColor.DARK_PURPLE + "Imploding Crossbow");
                    weapon.setItemMeta(weaponMeta);

                    var equipment = piglin.getEquipment();
                    equipment.setItemInMainHand(weapon);
                }
                    break;

                case 3: {
                    piglin.setCustomName(ChatColor.BLUE + "Piglin Rider");

                    var weapon = new ItemStack(Material.CROSSBOW);
                    var meta = (CrossbowMeta) weapon.getItemMeta();
                    meta.setCustomModelData(105);
                    meta.setDisplayName(ChatColor.DARK_GREEN + "Exploding Crossbow");

                    var rocket = new ItemStack(Material.FIREWORK_ROCKET);
                    var rocketMeta = (FireworkMeta) rocket.getItemMeta();
                    var fireworkEffect = FireworkEffect.builder().withColor(Color.OLIVE).withFade(Color.RED).build();
                    rocketMeta.addEffect(fireworkEffect);
                    rocket.setItemMeta(rocketMeta);

                    var proyectiles = List.of(rocket, rocket, rocket);
                    meta.setChargedProjectiles(proyectiles);
                    weapon.setItemMeta(meta);

                    var equipment = piglin.getEquipment();
                    equipment.setItemInMainHand(weapon);

                }
                    break;

                default: {
                    piglin.setCustomName(ChatColor.BLUE + "Piglin Blacksmith");

                    var hat = new ItemStack(Material.NETHERITE_HELMET);

                    var outfit = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                    var outfitMeta = outfit.getItemMeta();
                    outfitMeta.setCustomModelData(130);
                    outfitMeta.setDisplayName(ChatColor.YELLOW + "Blacksmith Outfit");
                    outfitMeta.setUnbreakable(true);
                    outfit.setItemMeta(outfitMeta);

                    var weapon = new ItemStack(Material.NETHERITE_SWORD);
                    var weaponMeta = weapon.getItemMeta();
                    weaponMeta.setCustomModelData(96);
                    weaponMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Great Hammer");

                    final AttributeModifier damage = new AttributeModifier(UUID.randomUUID(), "GENERIC.ATTACK.DAMAGE",
                            12.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                    weaponMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damage);

                    final AttributeModifier attackSpeed = new AttributeModifier(UUID.randomUUID(),
                            "GENERIC.ATTACK.SPEED", -0.90, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND);
                    weaponMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeed);

                    weapon.setItemMeta(weaponMeta);

                    var equipment = piglin.getEquipment();
                    equipment.setHelmet(hat);
                    equipment.setItemInMainHand(weapon);
                    equipment.setLeggings(outfit);

                }
                    break;
            }

        }

    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        var entity = e.getEntity();
        if (entity.getCustomName() != null && entity.getCustomName().toString().contains("Muddy")) {
            e.getDrops().forEach(drop -> drop.setType(Material.AIR));
            e.getDrops().add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));

        }else if(entity instanceof Horse && entity.getCustomName() != null && entity.isSilent()){
            e.getDrops().forEach(drop -> drop.setType(Material.AIR));
            

        }else if(entity instanceof Illusioner){
            entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.TOTEM_OF_UNDYING));
            
        }

    }

    @EventHandler
    public void convert(EntityDamageByEntityEvent e){
        var difficulty = instance.getGame().getDifficultyChanges();
        var entity = e.getEntity();

        if(difficulty.get("pigs") && entity instanceof Pig && entity.getCustomName() == null){
            e.setCancelled(true);
            entity.getWorld().strikeLightning(entity.getLocation());

        }else if(difficulty.get("raiders") && entity instanceof Cow && entity.getCustomName() == null){
            var cow = (Cow) entity;
            cow.damage(100);
            e.setCancelled(true);
            entity.getWorld().spawnEntity(entity.getLocation(), EntityType.COW);
        }

    }

    @EventHandler
    public void onGetMilk(PlayerInteractAtEntityEvent e){
        var difficulty = instance.getGame().getDifficultyChanges();
        var item = e.getPlayer().getEquipment().getItemInMainHand();
        
        if(difficulty.get("raiders") && e.getRightClicked() instanceof Cow && item.getType() == Material.BUCKET){
            var cow = (Cow) e.getRightClicked();
            if(cow.getCustomName() != null && cow.getCustomName().toString().contains("loom")){
                e.setCancelled(true);
                var milk = new ItemStack(Material.MILK_BUCKET);
                var meta = milk.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + "Magic Milk");
                meta.setCustomModelData(1);
                milk.setItemMeta(meta);

                var count = item.getAmount();

                if(count == 1) e.getPlayer().getInventory().remove(Material.BUCKET);
                else if(count >= 1) item.setAmount(count-1);
                 
                e.getPlayer().getInventory().addItem(milk);
                cow.getLocation().createExplosion(4);
            }
        }

    }

}