package bosses;

import assets.Assets;
import engine.game.GameObject;
import engine.sound.BClip;
import global.GameRules;
import gui.Play;
import library.Boss;
import library.Projectile;
import projectiles.Dragon_Fire;

import java.util.Random;

public class Dragon extends Boss {

    Random r = new Random();
    private int attackCD;

    GameObject hb1;
    private boolean d1;

    private boolean roar;
    private BClip clip;
    private BClip fireballSound;

    public Dragon() {
        super("Dragon", 150, 300, 100, "dragon");
        getHitBoxes().add(new GameObject(getX(), getY()-50, 50, 50));
        hb1 = getHitBoxes().get(0);
        Play.listener.onAddGUI(hb1);
        d1 = true;
        clip = Assets.getClip("dragon");
        clip.volume(GameRules.masterVolume);
        fireballSound = Assets.getClip("fireball");
        clip.volume(GameRules.gameVolume - 30f);
        roar = false;
    }

    public int getXVel(){
        int xVel = r.nextInt(5);
        if(r.nextBoolean())
            xVel *= -1;
        return xVel;
    }

    public void attack(){
        for (int i = 0; i < 3; i++) {
            Projectile p = new Dragon_Fire(getXVel(), 5, getX() + getWidth()/2, getY()+getHeight());
            Play.listener.onAddGUI(p);
            Play.projectiles.add(p);
        }
    }

    @Override
    public void onTick() {
        if(!roar){
            clip.start();
            roar = !roar;
        }
        if(isSpawning()){
            move("down", getHeight()/15);
            if(getY() > 0)
                setSpawning(false);
        }else {
            if (attackCD == 0) {
                if(!fireballSound.getSound().isRunning())
                    fireballSound.start();
                if(r.nextInt(20) == 1)
                    for (int i = 0; i < 3; i++) {
                        attack();
                    }
                else
                    attack();
                attackCD = r.nextInt(getHealth()) + 20;
            } else {
                attackCD--;
            }
            if(d1) {
                hb1.setLocation(hb1.getX() - 2, getY()+getHeight()-50);
            }else{
                hb1.setLocation(hb1.getX() + 2, getY()+getHeight()-50);
            }
            if(hb1.getX() > getX()+getWidth()) {
                hb1.setLocation(getX()+getWidth(), hb1.getY());
                d1 = !d1;
            }
            if(hb1.getX() < getX()) {
                hb1.setLocation(getX(), hb1.getY());
                d1 = !d1;
            }
        }
    }

    @Override
    public void onHit(int damage) {
        subHealth(damage);
    }
}
