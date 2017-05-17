package library;

import engine.action.QuickDraw;
import engine.game.GameObject;
import engine.gui.GUIListener;
import gui.Play;

import java.awt.*;
import java.util.ArrayList;

public abstract class SFX extends GameObject {

    private GUIListener listener;
    private ArrayList<Particle> particles;

    private boolean dead;

    public SFX() {
        this.listener = Play.listener;
        particles = new ArrayList<>();
        dead = false;
    }

    public abstract void onStart(int x, int y);

    public abstract void onTick();

    public void draw(Graphics2D g) {
        if (isDead()) {
            Play.sfx.remove(this);
        } else {
            for (Particle p : getParticles()) {
                QuickDraw.image(p.getSprite(), p.getBounds(), g);
            }
            onTick();
        }
    }

    public boolean isDead() {
        return particles.size() == 0;
    }

    public ArrayList<Particle> getParticles() {
        return this.particles;
    }

    public void setParticles(ArrayList<Particle> particles) {
        this.particles = particles;
    }

    public void addParticle(Particle p) {
        listener.onAddGUI(p);
        particles.add(p);
    }

    public void removeParticle(Particle p) {
        listener.onRemoveGUI(p);
        particles.remove(p);
    }

}
