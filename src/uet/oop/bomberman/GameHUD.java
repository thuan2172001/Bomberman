package uet.oop.bomberman;

import uet.oop.bomberman.entities.Bomber;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Displays various game information on the screen such as each player's score.
 */
public class GameHUD {

    private Bomber[] players;
    private BufferedImage[] playerInfo;
    private int[] playerScore;
    boolean matchSet;
    private int level;

    GameHUD() {
        this.players = new Bomber[2];
        this.playerInfo = new BufferedImage[2];
        this.playerScore = new int[2];
        this.matchSet = false;
        this.level = 1;
    }

    void init() {
        // Height of the HUD
        int height = GameWindow.HUD_HEIGHT;
        // Width of each player's information in the HUD, 2 players, 2 info boxes
        int infoWidth = GamePanel.panelWidth / 1;

        this.playerInfo[0] = new BufferedImage(infoWidth, height, BufferedImage.TYPE_INT_RGB);
    }

    void reset() {
        this.playerScore[0] = 0;
        this.playerScore[1] = 0;
        level = 1;
        this.matchSet = false;
    }

    public int getLevel() {
        return level;
    }

    // dùng để hack game
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Used by game panel to draw player info to the screen
     * @return Player info box
     */
    BufferedImage getP1info() {
        return this.playerInfo[0];
    }
    BufferedImage getP2info() {
        return this.playerInfo[1];
    }

    /**
     * Assign an info box to a player that shows the information on this player.
     * @param player The player to be assigned
     * @param playerID Used as an index for the array
     */
    void assignPlayer(Bomber player, int playerID) {
        this.players[playerID] = player;
    }

    /**
     * Checks if there is only one player alive left and increases their score.
     * The match set boolean is used to check if a point is already added so that the winner can freely
     * move around for a while before resetting the map. This also allows the winner to kill themselves without
     * affecting their score since the score was already updated.
     */
    public void updateScore() {
        try {
            // Count dead players
            int deadPlayers = 0;
            for (int i = 0; i < this.players.length; i++) {
                if (this.players[i].isDead()) {
                    deadPlayers++;
                }
                // ăn portal
                if(this.players[i].isSupreme()) {
                    for (int index = 0; index < this.players.length; index++) {
                        if (!this.players[index].isDead() && index != i) {
                            this.players[index].setDead();
                        }
                        else if (!this.players[index].isDead() && index == i) {
                            this.playerScore[index] += 200;
                        }
                    }
                    this.matchSet = true;
                    level++;
                    //this.players[i].setSupreme(false);
                    break;
                }

            }

            // Người chết thua luôn
            if (deadPlayers == this.players.length - 1) {
                for (int i = 0; i < this.players.length; i++) {
                    if (!this.players[i].isDead()) {
                        // level
                        this.matchSet = true;
                    }
                }
            } else if (deadPlayers >= this.players.length) {
                // This should only be reached two or more of the last players die at the same time
                this.matchSet = true;
            }

        } catch (Exception e) {
            System.out.println("Loi nhieu qua");
        }
    }

    /**
     * Continuously redraw player information such as score.
     */
    void drawHUD() {
        Graphics[] playerGraphics = {
                this.playerInfo[0].createGraphics()
        };

        // Clean info boxes
        playerGraphics[0].clearRect(0, 0, playerInfo[0].getWidth(), playerInfo[0].getHeight());

        // Set border color per player
        playerGraphics[0].setColor(Color.WHITE);    // Player 1 info box border color

        // Iterate loop for each player
        for (int i = 0; i < playerGraphics.length; i++) {
            Font font = new Font("Courier New", Font.BOLD, 24);
            // Draw border and sprite
            playerGraphics[i].drawRect(1, 1, this.playerInfo[0].getWidth() - 2, this.playerInfo[0].getHeight() - 2);
            playerGraphics[i].drawImage(this.players[0].getBaseSprite(), 0, 0, null);

            // Draw score
            playerGraphics[i].setFont(font);
            playerGraphics[i].setColor(Color.WHITE);
            playerGraphics[i].drawString("Score: " + this.playerScore[0],
                    this.playerInfo[i].getWidth() / 2 - 200, 32);

            playerGraphics[i].drawString("Level: " + this.level,
                    this.playerInfo[i].getWidth() / 2 + 45, 32);


            // Dispose
            playerGraphics[i].dispose();
        }
    }

}