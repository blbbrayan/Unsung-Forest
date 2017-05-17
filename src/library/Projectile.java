package library;

import engine.action.QuickDraw;
import engine.game.GameObject;
import engine.gui.BFrame;
import assets.Assets;
import gui.Play;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Projectile extends GameObject {

    private boolean dead;
    private BufferedImage sprite;

    public Projectile(BufferedImage sprite) {
        setDead(false);
        setSprite(sprite);
    }

    public abstract void onSpawn(GameObject caster, Player p, ArrayList<Mob> mobs);

    public abstract void onMove(Player p, ArrayList<Mob> mobs);

    public abstract void onTouch(Player p, ArrayList<Mob> mobs, Boss boss);

    public void checkLocation() {
        setDead(getY() > BFrame.appHeight || getY() < 0);
    }

    public void checkTouching(Player player, ArrayList<Mob> mobs, Boss boss) {
        Player p = null;
        Boss b = null;
        ArrayList<Mob> m = new ArrayList<>();
        if (this.getBounds().intersects(player.getBounds()))
            p = player;
        if (boss != null)
            for (GameObject e : boss.getHitBoxes())
                if (this.getBounds().intersects(e.getBounds()))
                    b = boss;
        if (mobs.size() > 0)
            for (Mob mob : mobs)
                if (this.getBounds().intersects(mob.getBounds()))
                    m.add(mob);
        if (p != null || m.size() > 0 || b != null)
            onTouch(p, m, b);
    }

    public void draw(Graphics2D g) {
        if (isDead()) {
            Play.listener.onRemoveGUI(this);
            Play.projectiles.remove(this);
        } else {
            QuickDraw.image(getSprite(), getBounds(), g);
            onMove(Play.player, Play.mobs);
            checkLocation();
            checkTouching(Play.player, Play.mobs, Play.boss);
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

}
