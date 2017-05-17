package gui;

import bosses.Dragon;
import engine.action.QuickDraw;
import engine.game.GameObject;
import engine.game.GameState;
import engine.game.manager.GameClickEvent;
import assets.Assets;
import engine.gui.BFrame;
import engine.gui.GUIListener;
import engine.gui.ResizeListener;
import engine.sound.BClip;
import global.GameRules;
import library.*;
import mobs.Goblin;
import mobs.Ogre;
import mobs.Wolf;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Play extends GameState {

    public static GUIListener listener;
    private ArrayList<ResizeListener> gui;

    private Spawner spawner;

    private GameObject healthBar;
    private GameObject energyBar;
    private GameObject bossHealthBar;
    private GameObject levelBar;

    public static Player player;
    public static ArrayList<Mob> mobs;
    public static ArrayList<Projectile> projectiles;
    public static ArrayList<SFX> sfx;
    public static Boss boss;
    public static int score;
    public static int level;

    private BufferedImage playerImage;
    private BufferedImage bossHBImage;
    private BufferedImage background;

    private BClip bgSound;

    public Play(BFrame frame) {
        //Constructors
        super("play");
        this.listener = frame;
        //GUI
        gui = frame.getGui();
        healthBar = new GameObject(0, BFrame.appHeight-10, BFrame.appWidth/6 * 5, 10);
        energyBar = new GameObject(BFrame.appWidth/6 * 5, BFrame.appHeight-10, BFrame.appWidth/6, 10);
        bossHealthBar = new GameObject(0, 0, BFrame.appWidth, 10);
        levelBar = new GameObject(0, BFrame.appHeight/2, 10, (BFrame.appHeight/2)-10);

        //Variables
        player = new Player("Dave", 10, 8, 1);

        mobs = new ArrayList<>();
        projectiles = new ArrayList<>();
        sfx = new ArrayList<>();
        spawner = new Spawner(20);
        spawner.addMob(new Goblin(), 19);
        spawner.addMob(new Ogre(), 1);
        level = 1;
        score = 0;

        //Assets
        playerImage = Assets.get("archer");
        bossHBImage = Assets.get("heart");
        background = Assets.get("background");
        bgSound = Assets.getClip("music");
        bgSound.volume(GameRules.musicVolume);
    }

    @Override
    public void render(Graphics2D g) {
        //Game Code
        player.move();
        player.fire();
        checkScore();

        //Draw
        QuickDraw.image(background, new Rectangle(0, 0, BFrame.screenWidth, BFrame.screenHeight), g);
        drawSFX(g);
        QuickDraw.image(playerImage, player.getBounds(), g);
        drawMobs(g);
        drawBoss(g);
        drawProjectiles(g);
        drawInfo(g);

        //Check State
        if(player.getHealth() <= 0) {
            g.setFont(GameRules.font(30));
            g.drawString("You died! Insert coin (Enter) to continue playing or press ESC to start over", 400, 400);
            Frame.pause();
        }
    }

    public void drawBoss(Graphics2D g){
        if(boss != null){
            if(boss.getHealth() > 0){
                boss.onTick();
                QuickDraw.image(boss.getSprite(), boss.getBounds(), g);
                for(GameObject e: boss.getHitBoxes()) {
                    QuickDraw.image(bossHBImage, e.getBounds(), g);
                }
            }else{
                onBossDeath();
                score += GameRules.scoreToLevel();
            }
        }
    }

    public void drawMobs(Graphics2D g){
        if(mobs.size() > 0) {
            for (int i = 0; i < mobs.size(); i++) {
                int size = mobs.size();
                mobs.get(i).draw(g);
                if(size > mobs.size()) {
                    size = mobs.size();
                    i--;
                }
            }
        }
    }

    public void drawProjectiles(Graphics2D g){
        if(projectiles.size() > 0) {
            for (int i = 0; i < projectiles.size(); i++) {
                int size = projectiles.size();
                projectiles.get(i).draw(g);
                if(size > projectiles.size()) {
                    size = projectiles.size();
                    i--;
                }
            }
        }
    }

    public void drawSFX(Graphics2D g){
        if(sfx.size() > 0) {
            for (int i = 0; i < sfx.size(); i++) {
                int size = sfx.size();
                sfx.get(i).draw(g);
                if(size > sfx.size()) {
                    size = sfx.size();
                    i--;
                }
            }
        }
    }

    public void checkScore(){
        if(score > GameRules.scoreToLevel()){
            Random r = new Random();
            level++;
            if(level % 2 == 0)
                spawner.setSpawnTime(spawner.getSpawnTime()-1);
            if(player.getHealth() < 10)
                player.subHealth(-1);
            if(r.nextBoolean())
                spawner.addMob(new Ogre(), 1);
            else
                spawner.addMob(new Wolf(), 2);
            if(level == 5)
                bossMode(new Dragon());
        }
    }



    public void drawInfo(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, 15));
        int particles = 0;
        for(SFX s: sfx)
            particles+=s.getParticles().size();
        g.drawString("Items Rendered: " + gui.size(), 10, 30);
        g.drawString("Assets Loaded: " + Assets.amountLoaded(), 10, 60);
        g.drawString("Score: " + score, 10, 150);
        g.drawString("Level: " + level, 10, 180);
        Color c = g.getColor();
        g.setColor(Color.green);
        QuickDraw.rect(healthBar.getBounds(), false, g);
        QuickDraw.rect(new Rectangle(healthBar.getBounds().x, healthBar.getBounds().y, (int)((double)player.getHealth()/10.0 * (double)healthBar.getBounds().width), healthBar.getBounds().height), true, g);
        g.setColor(Color.yellow);
        QuickDraw.rect(energyBar.getBounds(), false, g);
        QuickDraw.rect(new Rectangle(energyBar.getBounds().x, energyBar.getBounds().y, (int)((double)(15-player.getAttackCD())/15.0 * (double)energyBar.getBounds().width), energyBar.getBounds().height), true, g);
        if(boss != null) {
            g.setColor(Color.red);
            QuickDraw.rect(bossHealthBar.getBounds(), false, g);
            QuickDraw.rect(new Rectangle(bossHealthBar.getBounds().x, bossHealthBar.getBounds().y, (int) ((double) boss.getHealth() / (double)boss.getMaxHealth() * (double) bossHealthBar.getBounds().width), bossHealthBar.getBounds().height), true, g);
        }
        g.setColor(Color.lightGray);
        QuickDraw.rect(levelBar.getBounds(), false, g);
        int levelBarHeight = (int)((double)score/(double)GameRules.scoreToLevel() * (double)levelBar.getBounds().height);
        QuickDraw.rect(new Rectangle(levelBar.getBounds().x, levelBar.getBounds().y+levelBar.getBounds().height-levelBarHeight, levelBar.getBounds().width, levelBarHeight), true, g);
        g.setColor(c);
    }

    public void bossMode(Boss b){
        spawner.stop();
        for(Mob e: mobs){
            listener.onRemoveGUI(e);
        }
        mobs = new ArrayList<>();
        listener.onAddGUI(b);
        boss = b;
        b.onSpawn();
    }

    public void onBossDeath(){
        listener.onRemoveGUI(boss);
        for(GameObject hb: boss.getHitBoxes()){
            listener.onRemoveGUI(hb);
        }
        boss = null;
        //timer.start();
        Frame.gsm.setState("win");
    }

    public void removeAll(){
        listener.onRemoveGUI(player);
        listener.onRemoveGUI(healthBar);
        listener.onRemoveGUI(energyBar);
        listener.onRemoveGUI(bossHealthBar);
        listener.onRemoveGUI(levelBar);
        for(Mob e: mobs){
            listener.onRemoveGUI(e);
        }
        for(Projectile e: projectiles){
            listener.onRemoveGUI(e);
        }
        for(SFX s: sfx){
            for(Particle e: s.getParticles()) {
                listener.onRemoveGUI(e);
            }
        }
        if(boss != null) {
            for (GameObject e : boss.getHitBoxes()) {
                listener.onRemoveGUI(e);
            }
            listener.onRemoveGUI(boss);
        }
        boss = null;
        mobs = new ArrayList<>();
        projectiles = new ArrayList<>();
        sfx = new ArrayList<>();
        spawner.stop();
        player.setHealth(10);
        player.setLeft(false);
        player.setRight(false);
        player.setFire(false);
        level = 1;
        score = 0;
        spawner = new Spawner(20);
        spawner.addMob(new Goblin(), 19);
        spawner.addMob(new Ogre(), 1);
    }

    @Override
    public void onOpen() {
        Assets.clear();
        Assets.get("wolf");
        Assets.get("goblin");
        Assets.get("ogre");
        Assets.get("archer_arrow");
        Assets.get("dragon");
        Assets.get("dragon_fire");
        Assets.get("blood");
        Assets.get("blood_puddle");
        listener.onAddGUI(player);
        listener.onAddGUI(healthBar);
        listener.onAddGUI(energyBar);
        listener.onAddGUI(bossHealthBar);
        listener.onAddGUI(levelBar);
        spawner.start();
        bgSound.start();
        bgSound.getSound().loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void onClose() {
        removeAll();
        bgSound.stop();
    }

    @Override
    public void keyPressed(String s) {
        switch (s) {
            case "exit":
                if(player.getHealth()>0)
                    System.exit(0);
                else {
                    Frame.resume();
                    Frame.gsm.setState("start");
                }
                break;
            case "left":
                player.setLeft(true);
                break;
            case "right":
                player.setRight(true);
                break;
            case "fire":
                player.setFire(true);
                break;
            case "start":
                if(player.getHealth() <= 0){
                    player.setHealth(10);
                    for(Mob e: mobs){
                        listener.onRemoveGUI(e);
                    }
                    for(Projectile e: projectiles){
                        listener.onRemoveGUI(e);
                    }
                    mobs = new ArrayList<>();
                    projectiles = new ArrayList<>();
                    Frame.resume();
                }
                break;
        }
    }

    @Override
    public void keyReleased(String s) {
        switch (s) {
            case "left":
                player.setLeft(false);
                break;
            case "right":
                player.setRight(false);
                break;
            case "fire":
                player.setFire(false);
                break;
        }
    }

    @Override
    public void mousePressed(GameClickEvent gameClickEvent) {

    }

    @Override
    public void mouseReleased(GameClickEvent gameClickEvent) {

    }
}
