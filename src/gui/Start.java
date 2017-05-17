package gui;

import engine.game.GameState;
import engine.game.manager.GameClickEvent;
import engine.gui.BFrame;
import engine.gui.GUIListener;
import assets.Assets;
import global.GameRules;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Start extends GameState {

    public static GUIListener listener;

    private BufferedImage background;

    public Start(GUIListener listener) {
        super("start");
        this.listener = listener;
        background = Assets.get("start");
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(background, 0, 0, BFrame.screenWidth, BFrame.screenHeight, null);
        g.setColor(Color.white);
        g.setFont(GameRules.font(20));
        g.drawString("Insert coin to start (Enter) *Alpha Gameplay", BFrame.screenWidth/4, BFrame.screenHeight-10);
        g.setFont(GameRules.font(20));
        g.drawString("Instructions:", 30, 30);
        g.drawString("- Use the Left and Right Arrow Keys to move", 30, 65);
        g.drawString("- Press Space to shoot", 30, 90);
        g.drawString("- Some monsters have more health than others", 30, 115);
        g.drawString("- This demo only goes to level 5; beat level 5 to win.", 30, 140);
    }

    @Override
    public void onOpen(){
    }

    @Override
    public void onClose() {
    }

    @Override
    public void keyPressed(String s) {
        switch (s) {
            case "exit":
                System.exit(0);
                break;
            case "start":
                Frame.gsm.setState("play");
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
