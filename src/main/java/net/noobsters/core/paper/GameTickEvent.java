package net.noobsters.core.paper;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;

public class GameTickEvent extends Event {
    /*
     * Methods Required by BukkitAPI
     */
    private static final @Getter HandlerList HandlerList = new HandlerList();
    @SuppressWarnings({"java:S116", "java:S1170"})
    private final @Getter HandlerList Handlers = HandlerList;
    private final @Getter int second;


    public GameTickEvent(int second, boolean async) {
        super(async);
        this.second = second;
    }

    public GameTickEvent(int second) {
        this(second, false);
    }

}
