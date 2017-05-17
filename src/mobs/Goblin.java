package mobs;

import assets.Assets;
import global.GameRules;
import gui.Play;
import library.Mob;
import library.Player;
import library.SFX;
import sfx.Blood;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Goblin extends Mob {

    public Goblin() {
        super("Goblin", 1, 6, 1, 2, Assets.get("goblin"));
        setSize(30, 30);
        setDead(false);
    }

    public void onTouch(Player player) {
        hitPlayer(player);
    }

    public void onTouch(Mob mob) {

    }

    public void onMove(Player player, ArrayList<Mob> mobs) {
        move("down", getSpeed());
    }

    public void onDeath() {
        SFX s = GameRules.small_Blood();
        s.onStart(getX()+getWidth()/2, getY() + getHeight()/2);
        Play.sfx.add(s);
    }
}
