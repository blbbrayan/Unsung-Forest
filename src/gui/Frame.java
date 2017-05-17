package gui;

import engine.game.GameCanvas;
import engine.game.GameControls;
import engine.game.manager.GameStateManager;
import engine.gui.BFrame;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

public class Frame extends BFrame implements FocusListener{

    public static void main(String[] args){
        new Frame();
    }

    public static GameStateManager gsm;
    private static GameCanvas canvas;

    private Frame() {
        super("Unsung Forest", 800, 600, 1);
        addFocusListener(this);
        gsm = new GameStateManager(this);
        canvas = new GameCanvas(30, this) {
            @Override
            public void draw(Graphics2D g) {
                if(gsm.getState() != null) {
                    gsm.getState().render(g);
                }
            }
        };
        GameControls.add("exit", KeyEvent.VK_ESCAPE);
        GameControls.add("left", KeyEvent.VK_LEFT);
        GameControls.add("right", KeyEvent.VK_RIGHT);
        GameControls.add("fire", KeyEvent.VK_SPACE);
        GameControls.add("action", KeyEvent.VK_1);
        GameControls.add("ultimate", KeyEvent.VK_2);
        GameControls.add("start", KeyEvent.VK_ENTER);

        init(gsm, canvas);
    }

    public static void pause(){
        canvas.stop();
    }
    public static void resume(){
        canvas.start();
    }
    public static Graphics2D canvasGraphics(){
        return canvas.getG2D();
    }

    private void init(GameStateManager gsm, GameCanvas canvas){
        gsm.addState(new Start(this));
        gsm.addState(new Play(this));
        gsm.addState(new Win(this));

        gsm.setState("start");
        canvas.start();
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        this.requestFocus();
    }
}
