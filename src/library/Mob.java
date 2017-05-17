package library;

import engine.action.QuickDraw;
import engine.game.GameObject;
import engine.gui.BFrame;
import assets.Assets;
import global.GameRules;
import gui.Play;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Mob extends GameObject {

    private String id;
    private int health;
    private int speed;
    private int damage;
    private ArrayList<String> tags;

    private boolean dead;

    private int score;

    private BufferedImage sprite;

    public Mob(String id, int health, int speed, int damage, int score, BufferedImage sprite) {
        setHealth(health);
        setSpeed(speed);
        setDamage(damage);
        setId(id);
        setTags(new ArrayList<>());
        setScore(score);
        setSprite(sprite);
    }

    public void spawn() {
        int x = (int) (Math.random() * (BFrame.appWidth - getWidth()));
        setLocation(x, -getHeight());
    }

    public abstract void onTouch(Player player);

    public abstract void onTouch(Mob mob);

    public abstract void onMove(Player player, ArrayList<Mob> mobs);

    public abstract void onDeath();

    public void draw(Graphics2D g) {
        checkHealth();
        if (isDead()) {
            if (getHealth() <= 0) {
                Play.score += getScore();
                onDeath();
            }
            Play.listener.onRemoveGUI(this);
            Play.mobs.remove(this);
        } else {
            QuickDraw.image(getSprite(), getBounds(), g);
            onMove(Play.player, Play.mobs);
            checkLocation();
            checkTouching(Play.player, Play.mobs);
        }
    }

    public void hitPlayer(Player player) {
        setDead(true);
        player.subHealth(getDamage());
        SFX s = GameRules.small_Blood();
        s.onStart(player.getX() + getWidth() / 2, player.getY() + getHeight() / 2);
        Play.sfx.add(s);
    }

    public void checkLocation() {
        setDead(getY() > BFrame.appHeight);
    }

    public void checkTouching(Player p, ArrayList<Mob> mobs) {
        if (this.getBounds().intersects(p.getBounds()))
            onTouch(p);
        for (Mob m : mobs)
            if (this.getBounds().intersects(m.getBounds()))
                onTouch(m);
    }

    public void checkHealth() {
        if (!isDead())
            setDead(health <= 0);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void subHealth(int value) {
        this.health -= value;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }
}
