package library;

import engine.action.ControlledTimer;
import gui.Play;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Spawner {

    private Random r;
    private ArrayList<Mob> mobs;
    private ArrayList<BufferedImage> assets;
    private ControlledTimer timer;
    private int spawnTime;

    public Spawner(int spawnTime){
        setMobs(new ArrayList<>());
        r = new Random();
        setSpawnTime(spawnTime);
        setupSpawner();
    }

    public void setupSpawner() {
        timer = new ControlledTimer(10) {
            int t = 0;

            @Override
            public void tick() {
                t++;
                if (t >= spawnTime) {
                    t = 0;
                        Mob m = spawn();
                        m.spawn();
                        Play.mobs.add(m);
                        Play.listener.onAddGUI(m);
                }
            }
        };
    }

    public void start(){
        timer.start();
    }

    public void stop(){
        timer.stop();
    }

    public Mob spawn(){
        try {
            return mobs.get(r.nextInt(mobs.size())).getClass().newInstance();
        }catch (Exception e) {
            return null;
        }
    }

    public void addMob(Mob mob, int chance){
        for(int i = 0; i < chance; i++)
            mobs.add(mob);
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
    }

    public void setMobs(ArrayList<Mob> mobs) {
        this.mobs = mobs;
    }

    public int getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(int spawnTime) {
        this.spawnTime = spawnTime;
    }
}
