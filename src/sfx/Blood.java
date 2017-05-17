package sfx;

import assets.Assets;
import engine.gui.GUIListener;
import gui.Play;
import library.Particle;
import library.SFX;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Blood extends SFX {

    private int amount;
    private int radius;
    private int size;
    private int life;
    private int bounce;
    private int standBy;
    private Random r;

    private BufferedImage blood;
    private BufferedImage puddle;

    public Blood(int amount, int radius, int size, int life,  int standBy, int bounce) {
        this.amount = amount;
        r = new Random();
        this.radius = radius;
        this.size = size;
        this.life = life;
        this.standBy = standBy;
        this.bounce = bounce;
        this.blood = Assets.get("blood");
        this.puddle = Assets.get("blood-puddle");
    }

    @Override
    public void onStart(int x, int y) {
        for(int i = 0; i < amount; i++){
            Particle p = new Particle(blood, new Rectangle(x, y, size, size), life+standBy, 0, 0);
            bounce(p);
            addParticle(p);
        }
        int splat = radius * life;
        addParticle(new Particle(puddle, new Rectangle(x-splat, y-splat, splat*2, splat*2), life+standBy, 0, 0));
    }

    public void bounce(Particle p){
        int xR = r.nextInt(radius);
        if(r.nextBoolean())
            xR*=-1;
        int yR = r.nextInt(radius);
        if(r.nextBoolean())
            yR*=-1;
        p.setxVal(xR);
        p.setyVal(yR);
    }

    @Override
    public void onTick() {
        boolean killAll = false;
        for(Particle p: getParticles()) {
            if(!(p.getxVal() ==0 && p.getyVal() == 0)) {
                int b = life / bounce;
                if ((p.getLife() - standBy) % b == 0) {
                    bounce(p);
                }
                if (p.getLife() > standBy)
                    p.onMove();
            }
            p.setLife(p.getLife() -1);
            if(p.getLife() <= 0) {
                killAll = true;
            }
        }
        if(killAll){
            for(Particle p: getParticles()){
                Play.listener.onRemoveGUI(p);
            }
            setParticles(new ArrayList<>());
        }
    }
}
