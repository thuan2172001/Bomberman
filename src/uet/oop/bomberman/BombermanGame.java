package uet.oop.bomberman;

import uet.oop.bomberman.entities.ResourceCollection;
import uet.oop.bomberman.entities.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Contains the main method to launch the game.
 */
public class BombermanGame {

    // màn hình duy nhất chạy game
    static GameWindow window;

    public static void main(String[] args) {
        ResourceCollection.readFiles();
        ResourceCollection.init();

        GamePanel game;
        game = new GamePanel();
        game.init();

        Sound s = new Sound();
        try {
            s.play(Sound.BACKGROUNDMUSIC[1], 3);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        window = new GameWindow(game);

        System.gc();
    }

}

/**
 * Game window that contains the game panel seen by the user.
 */
class GameWindow extends JFrame {

    /**
     * Screen width and height is determined by the map size. Map size is set when loading the map in
     * the GamePanel class. For best results, do not use a map that is smaller than the default map
     * provided in resources.
     */

    static final int HUD_HEIGHT = 48;   // Size of the HUD. The HUD displays score.
    static final String TITLE = "Bomberman by GGAA TEAM";

    /**
     * Constructs a game window with the necessary configurations.
     * @param game Game panel that will be contained inside the game window
     */
    GameWindow(GamePanel game) {
        this.setTitle(TITLE);
        this.setIconImage(ResourceCollection.Images.ICON.getImage());
        this.setLayout(new BorderLayout());
        this.add(game, BorderLayout.CENTER);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
