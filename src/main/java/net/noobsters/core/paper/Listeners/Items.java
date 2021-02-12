package net.noobsters.core.paper.Listeners;

import java.util.Random;

import org.bukkit.event.Listener;

import net.noobsters.core.paper.PERMADED;

public class Items implements Listener{

    PERMADED instance;
    Random random = new Random();

    Items(PERMADED instance){
        this.instance = instance;
    }
    

}