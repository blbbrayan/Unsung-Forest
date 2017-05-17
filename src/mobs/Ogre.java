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

public class Ogre extends Mob {

    public Ogre() {
        super("Ogre", 5, 3, 4, 14, Assets.get("ogre"));
        setSize(90, 90);
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
        SFX s = GameRules.large_Blood();
        s.onStart(getX()+getWidth()/2, getY() + getHeight()/2);
        Play.sfx.add(s);
    }
}
