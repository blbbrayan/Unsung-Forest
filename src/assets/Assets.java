package assets;

import engine.sound.BClip;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Assets {

    public static ArrayList<BufferedImage> sprites = new ArrayList<>();
    public static ArrayList<String> spriteNames = new ArrayList<>();

    public static int amountLoaded(){
        return sprites.size();
    }

    public static BufferedImage get(String name) {
        for (String e : spriteNames) {
            if (e.equals(name))
                return sprites.get(spriteNames.indexOf(e));
        }
        load(name);
        for (String e : spriteNames) {
            if (e.equals(name))
                return sprites.get(spriteNames.indexOf(e));
        }
        return null;
    }

    public static void clear() {
        sprites = new ArrayList<>();
        spriteNames = new ArrayList<>();
    }

    public static void load(String name) {
        try {
            sprites.add(ImageIO.read(new File("assets/" + name + ".png")));
            spriteNames.add(name);
        } catch (Exception e) {
        }
    }

    public static void load(String name, String extension) {
        try {
            sprites.add(ImageIO.read(new File("assets/" + name + "." + extension)));
            spriteNames.add(name);
        } catch (Exception e) {
        }
    }

    public static BClip getClip(String name) {
        try {
            File f = new File("assets/" + name + ".wav");
            if (f.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(f);
                // load the sound into memory (a Clip)
                Clip clip = AudioSystem.getClip();
                clip.open(sound);
                return new BClip(clip);
            } else {
                System.out.println("Sound file: " + name + ".wav does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
