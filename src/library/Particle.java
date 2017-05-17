package library;

import engine.game.GameObject;
import assets.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Particle extends GameObject{

    private int life;
    private int xVal;
    private int yVal;

    private BufferedImage sprite;

    public Particle(BufferedImage sprite, Rectangle bounds, int life, int xVal, int yVal){
        setBounds(bounds);
        setLife(life);
        setxVal(xVal);
        setyVal(yVal);
        setSprite(sprite);
    }

    public void onMove(){
        setLocation(getX() + xVal, getY() + yVal);
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getxVal() {
        return xVal;
    }

    public void setxVal(int xVal) {
        this.xVal = xVal;
    }

    public int getyVal() {
        return yVal;
    }

    public void setyVal(int yVal) {
        this.yVal = yVal;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }


}
