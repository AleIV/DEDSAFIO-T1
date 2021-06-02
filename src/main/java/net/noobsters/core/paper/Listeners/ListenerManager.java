package net.noobsters.core.paper.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import net.noobsters.core.paper.PERMADED;

public class ListenerManager {
    private PERMADED instance;
    
    public ListenerManager(PERMADED instance) {
        this.instance = instance;
    
        registerListener(new GlobalListeners(instance));
        registerListener(new Animals(instance));
        registerListener(new Raiders(instance));
        registerListener(new Monsters(instance));
        registerListener(new UnDead(instance));
        registerListener(new Demons(instance));
        registerListener(new Items(instance));
        registerListener(new MiniBosses(instance));
        registerListener(new Disguise(instance));
        registerListener(new DedsafioListener(instance));
        registerListener(new Extra(instance));
        registerListener(new Cancel(instance));


    }

    public void unregisterListener(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, instance);
    }

}