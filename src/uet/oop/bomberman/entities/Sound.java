package uet.oop.bomberman.entities;

import javax.sound.sampled.*;
import java.io.IOException;



public class Sound {
    public static String EXPLORE = "/textures/Explosion.wav";
    public static String DEAD = "/textures/background.wav";
    public static String WIN = "/textures/background.wav";
    public static String LOSE = "/textures/background.wav";
    public static String BACKGROUND = "/textures/background.wav";
    public static String[] BACKGROUNDMUSIC = {"/textures/background.wav", "/textures/muathutrongmatem.wav"};

    public static synchronized void play(final String fileName, int loop_Timer)
    {
        // Note: use .wav files
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                                    Sound.class.getResourceAsStream(fileName));
                    clip.open(inputStream);
                    clip.start();
                    clip.loop(loop_Timer);
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                    System.out.println("play sound error: " + e.getMessage() + " for " + fileName);
                }
            }
        }).start();
    }

}
