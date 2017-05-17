package library;

import engine.game.GameObject;
import engine.gui.BFrame;
import assets.Assets;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Boss extends GameObject{

    private String id;
    private int maxHealth;
    private int health;
    private ArrayList<GameObject> hitBoxes;
    private boolean spawning;

    private BufferedImage sprite;

    public Boss(String id, int health, int width, int height, String sprite){
        setId(id);
        setHealth(health);
        setMaxHealth(health);
        setHitBoxes(new ArrayList<>());
        setSize(width, height);
        setSpawning(true);
        setSprite(Assets.get(sprite));
    }

    public void onSpawn(){
        setLocation((BFrame.appWidth/2)-(getWidth()/2), -getHeight());
    }

    public abstract void onTick();
    public abstract void onHit(int damage);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<GameObject> getHitBoxes() {
        return hitBoxes;
    }

    public void setHitBoxes(ArrayList<GameObject> hitBoxes) {
        this.hitBoxes = hitBoxes;
    }

    public boolean isSpawning() {
        return spawning;
    }

    public void setSpawning(boolean spawning) {
        this.spawning = spawning;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }
}
