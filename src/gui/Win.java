package gui;

import engine.action.QuickDraw;
import engine.game.GameObject;
import engine.game.GameState;
import engine.game.manager.GameClickEvent;
import engine.gui.BFrame;
import engine.gui.GUIListener;
import assets.Assets;
import global.GameRules;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Win extends GameState {

    public static GUIListener listener;

    GameObject title;
    Random r;

    int i;
    int xVal;
    int yVal;

    private BufferedImage background;

    public Win(GUIListener listener) {
        super("win");
        this.listener = listener;
        title = new GameObject(0, 0, 0, 0);
        i=0;
        xVal = 0;
        yVal = 0;
        r = new Random();
        background = Assets.get("win");
    }

    @Override
    public void render(Graphics2D g) {
        QuickDraw.image(background, new Rectangle(0, 0, BFrame.screenWidth, BFrame.screenHeight), g);
        g.setFont(GameRules.font(20));
        g.setColor(Color.white);
        g.drawString("Press ESC to exit, or Enter to restart DEMO *Alpha Gameplay", BFrame.screenWidth/4, BFrame.screenHeight-10);
        g.setFont(GameRules.font(100));
        g.setColor(Color.green);
        g.drawString("You Win!", title.getX(), title.getY());
        move();
    }

    public void move(){
        if(i == 0) {
            i = r.nextInt(50) + 20;
            xVal = r.nextInt(8);
            if (r.nextBoolean())
                xVal *= -1;
            yVal = r.nextInt(8);
            if (r.nextBoolean())
                yVal *= -1;
        }else{
            title.setLocation(title.getX() + xVal, title.getY() + yVal);
            i--;
        }
        if(title.getX() < 0) {
            title.setLocation(0, title.getY());
            i = 0;
        }
        if(title.getX() > BFrame.appWidth) {
            title.setLocation(BFrame.appWidth, title.getY());
            i = 0;
        }
        if(title.getY() < 100) {
            title.setLocation(title.getX(), 100);
            i = 0;
        }
        if(title.getY() > BFrame.appHeight) {
            title.setLocation(title.getX(), BFrame.appHeight);
            i = 0;
        }
    }

    @Override
    public void onOpen(){
        listener.onAddGUI(title);
    }

    @Override
    public void onClose() {
        listener.onRemoveGUI(title);
    }

    @Override
    public void keyPressed(String s) {
        switch (s) {
            case "exit":
                System.exit(0);
                break;
            case "start":
                Frame.gsm.setState("start");
                break;
        }
    }

    @Override
    public void keyReleased(String s) {

    }

    @Override
    public void mousePressed(GameClickEvent gameClickEvent) {

    }

    @Override
    public void mouseReleased(GameClickEvent gameClickEvent) {

    }
}
