package library;

import engine.game.GameObject;
import assets.Assets;
import engine.gui.BFrame;
import engine.sound.BClip;
import global.GameRules;
import gui.Play;
import projectiles.Arrow;

import javax.sound.sampled.Clip;

public class Player extends GameObject{

    private String name;
    private int health;
    private int speed;
    private int damage;
    private int attackCD;

    private boolean left;
    private boolean right;
    private boolean fire;

    private BClip arrowSound;

    public void move(){
        String move;
        if(left) {
            move("left", speed);
        }
        if(right) {
            move("right", speed);
        }
        if(getX() < 0)
            setLocation(0, getY());
        if(getX() > BFrame.appWidth-getWidth())
            setLocation(BFrame.appWidth-getWidth(), getY());
    }

    public void fire(){
        if(isFire()) {
            if (getAttackCD() <= 0) {
                setAttackCD(15);
                Projectile p = new Arrow(Play.level/2 + 1);
                p.onSpawn(this, this, Play.mobs);
                Play.listener.onAddGUI(p);
                Play.projectiles.add(p);
                arrowSound.start();
                arrowSound.volume(GameRules.gameVolume);
            }
        }
        if(getAttackCD() > 0)
            setAttackCD(getAttackCD() - 1);
    }

    public Player(String name, int health, int speed, int damage){
        super(400, 525, 50, 50);
        setName(name);
        setHealth(health);
        setSpeed(speed);
        setDamage(damage);
        left=false;
        right=false;
        fire = false;
        arrowSound = Assets.getClip("arrow");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void subHealth(int value){
        this.health -= value;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public int getAttackCD() {
        return attackCD;
    }

    public void setAttackCD(int attackCD) {
        this.attackCD = attackCD;
    }

    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

}
