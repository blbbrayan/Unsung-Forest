package projectiles;

import assets.Assets;
import engine.game.GameObject;
import gui.Play;
import library.Boss;
import library.Mob;
import library.Player;
import library.Projectile;

import java.util.ArrayList;

public class Dragon_Fire extends Projectile{

    private int xVel;
    private int yVel;

    public Dragon_Fire(int xVel, int yVel, int x, int y){
        super(Assets.get("dragon_fire"));
        setSize(20, 20);
        setLocation(x, y);
        this.xVel = xVel;
        this.yVel = yVel;
    }

    @Override
    public void onSpawn(GameObject caster, Player p, ArrayList<Mob> mobs) {
        setLocation(caster.getX()+((caster.getWidth()/2) - getWidth()/2), caster.getY());
    }

    @Override
    public void onMove(Player p, ArrayList<Mob> mobs) {
        move("down", yVel);
        move("right", xVel);
    }

    @Override
    public void onTouch(Player p, ArrayList<Mob> mobs, Boss b) {
        if(p != null){
            p.subHealth(2);
            setDead(true);
        }
        ArrayList<Projectile> all = new ArrayList<>();
        for(Projectile e: Play.projectiles)
            if(e instanceof Dragon_Fire)
                all.add(e);
        for(Projectile e: all){
            Play.projectiles.remove(e);
        }
    }

}
