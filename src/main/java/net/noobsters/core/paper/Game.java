package net.noobsters.core.paper;

import lombok.Data;

@Data
public class Game {
    private int day = 0;
    private String fase = "VANILLA";
    private Stage stage = Stage.VANILLA;


    public enum Stage {
        VANILLA, FASE1, FASE2, FASE3;
    }

}
