package projectiles;

import engine.game.GameObject;
import assets.Assets;
import library.Boss;
import library.Mob;
import library.Player;
import library.Projectile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Arrow extends Projectile{

    private int damage;

    public Arrow(int damage) {
        super(Assets.get("archer_arrow"));
        setSize(6, 40);
        this.damage = damage;
    }

    @Override
    public void onSpawn(GameObject caster, Player p, ArrayList<Mob> mobs){
        setLocation(caster.getX()+((caster.getWidth()/2) - getWidth()/2), caster.getY()-getHeight());
    }

    @Override
    public void onMove(Player p, ArrayList<Mob> mobs) {
        move("up", 6);
    }

    @Override
    public void onTouch(Player p, ArrayList<Mob> mobs, Boss boss) {
        if(mobs != null){
            for(Mob m: mobs){
                m.subHealth(damage);
                m.checkHealth();
            }
            setDead(true);
        }
        if(boss != null){
            boss.onHit(damage);
            setDead(true);
        }
    }
}
