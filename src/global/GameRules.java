package global;

import assets.Assets;
import gui.Play;
import sfx.Blood;

import java.awt.*;

public class GameRules {

    public static Font font(int size){
        return new Font("Arial", Font.BOLD, size);
    }

    public static Blood small_Blood(){
        return new Blood(20, 4, 5, 6, 20, 2);
    }

    public static Blood large_Blood(){
        return new Blood(50, 8, 12, 8, 40, 2);
    }

    public static int scoreToLevel() {
        return Play.level * Play.level * 12;
    }

    //SOUND VOLUMES
    public static float masterVolume = -10f;
    public static float gameVolume = -10f + masterVolume;
    public static float musicVolume = -30f + masterVolume;

}
