package net.noobsters.core.paper.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import net.noobsters.core.paper.PERMADED;

public class ListenerManager {
    private PERMADED instance;
    
    public ListenerManager(PERMADED instance) {
        this.instance = instance;
    
        Bukkit.getPluginManager().registerEvents(new GlobalListeners(instance), instance);

    }

    public void unregisterListener(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, instance);
    }

}