package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * JPanel chứa toàn bộ trò chơi và logic vòng lặp trò chơi.
 */
public class GamePanel extends JPanel implements Runnable {

    // Screen size is determined by the map size
    static int panelWidth;
    static int panelHeight;

    private Thread thread;
    private boolean running;
    int resetDelay;

    private BufferedImage world;
    private Graphics2D buffer;
    private BufferedImage bg;
    private GameHUD gameHUD;

    private int mapWidth;
    private int mapHeight;
    private ArrayList<ArrayList<String>> mapLayout;
    private ArrayList<ArrayList<ArrayList<String>>> mapArray;
    private BufferedReader[] bufferedReader;

    private HashMap<Integer, Key> controls1;
    private HashMap<Integer, Key> controls2;

    private static final double SOFTWALL_RATE = 0.65;

    public static BufferedImage[] SoftWall = {
            ResourceCollection.Images.BOX0.getImage(), ResourceCollection.Images.BOX1.getImage(),
            ResourceCollection.Images.BOX2.getImage(), ResourceCollection.Images.BOX3.getImage(),
            ResourceCollection.Images.BOX4.getImage()
    };

    BufferedImage[] getSoftWall(int id) {
        if (id == 0)
            return new BufferedImage[]{ResourceCollection.Images.BOX0.getImage(), ResourceCollection.Images.BOX1.getImage()};
        else if (id == 1)
            return new BufferedImage[]{ResourceCollection.Images.BOX2.getImage(), ResourceCollection.Images.BOX3.getImage()};
        else if (id == 2)
            return new BufferedImage[]{ResourceCollection.Images.BOX4.getImage(), ResourceCollection.Images.BOX5.getImage()};
        else if (id == 3)
            return new BufferedImage[]{ResourceCollection.Images.BOX6.getImage(), ResourceCollection.Images.BOX7.getImage()};
        else if (id == 4)
            return new BufferedImage[]{ResourceCollection.Images.FOOD0.getImage(), ResourceCollection.Images.FOOD1.getImage(),
                    ResourceCollection.Images.FOOD2.getImage(), ResourceCollection.Images.FOOD3.getImage()};
        else return SoftWall;
    }
    /**
     * Khởi tạo bảng điều khiển trò chơi và tải trong một tệp bản đồ.
     */
    GamePanel() {
        this.setFocusable(true);
        this.requestFocus();
        this.setControls();
        this.bg = ResourceCollection.Images.BACKGROUND.getImage();
        this.loadMapFile();
        this.addKeyListener(new GameController(this));
    }

    /**
     * Khởi tạo bảng điều khiển trò chơi với giao diện người dùng, kích thước cửa sổ, mảng các đối tượng trò chơi và bắt đầu vòng lặp trò chơi.
     */
    void init() {
        this.resetDelay = 0;
        GameObjectCollection.init();
        this.gameHUD = new GameHUD();
        this.generateMap(0);
        this.gameHUD.init();
        this.setPreferredSize(new Dimension(this.mapWidth * 32, (this.mapHeight * 32) + GameWindow.HUD_HEIGHT));
        System.gc();
        this.running = true;
    }

    /**
     * Đọc file, nếu file k đọc đc thì mặc định map default.
     * Dùng excel tên file.csv
     */
    private void loadMapFile() {
        try {
            this.bufferedReader = new BufferedReader[10];
            this.bufferedReader[0] = new BufferedReader(ResourceCollection.Files.MAP1.getFile());
            this.bufferedReader[1] = new BufferedReader(ResourceCollection.Files.MAP2.getFile());
            this.bufferedReader[2] = new BufferedReader(ResourceCollection.Files.MAP3.getFile());
            this.bufferedReader[3] = new BufferedReader(ResourceCollection.Files.MAP4.getFile());
            this.bufferedReader[4] = new BufferedReader(ResourceCollection.Files.MAP5.getFile());
            this.bufferedReader[5] = new BufferedReader(ResourceCollection.Files.MAP6.getFile());
            this.bufferedReader[6] = new BufferedReader(ResourceCollection.Files.MAP7.getFile());
            this.bufferedReader[7] = new BufferedReader(ResourceCollection.Files.MAP8.getFile());
            this.bufferedReader[8] = new BufferedReader(ResourceCollection.Files.MAP9.getFile());
            this.bufferedReader[9] = new BufferedReader(ResourceCollection.Files.MAP10.getFile());
        } catch (NullPointerException e) {
            System.err.println(e + ": Không đọc được file");
        }

        this.mapArray = new ArrayList<>();
        // Phân tích cú pháp dữ liệu từ tệp bản đồ
        for (int i = 0; i < 10; i++) {
            this.mapLayout = new ArrayList<>();
            try {
                String currentLine;
                while ((currentLine = bufferedReader[i].readLine()) != null) {
                    if (currentLine.isEmpty()) {
                        continue;
                    }
                    // Split row into array of strings and add to array list
                    mapLayout.add(new ArrayList<>(Arrays.asList(currentLine.split(","))));
                }
            } catch (IOException | NullPointerException e) {
                System.out.println(e + ": Lỗi phân tích cú pháp bản đồ");
                e.printStackTrace();
            }
            mapArray.add(mapLayout);
        }
    }

    /**
     * Tạo bản đồ cho tệp bản đồ. Bản đồ dạng lưới và mỗi ô có kích thước 32x32.
     * Tạo các đối tượng trò chơi tùy thuộc vào kí tự trong file. id từ 0 - 9
     */
    private void generateMap(int id) {
        // Kích thước map
        this.mapLayout = this.mapArray.get(id);
        this.mapWidth = mapLayout.get(0).size();
        this.mapHeight = mapLayout.size();
        panelWidth = this.mapWidth * 32;
        panelHeight = this.mapHeight * 32;
        if (id == 1) this.bg = ResourceCollection.Images.BLOCKTILE.getImage(); // doi glass sang gach Map thu 2
        else if (id == 2) this.bg = ResourceCollection.Images.SNOWTILE.getImage();
        else if (id == 3) this.bg = ResourceCollection.Images.PALACETILE.getImage();
        else if (id == 4) this.bg = ResourceCollection.Images.BISCUIT1.getImage();
        else this.bg = ResourceCollection.Images.BACKGROUND.getImage();
        this.world = new BufferedImage(this.mapWidth * 32, this.mapHeight * 32, BufferedImage.TYPE_INT_RGB);

        // tạo toàn bộ map
        for (int y = 0; y < this.mapHeight; y++) {
            for (int x = 0; x < this.mapWidth; x++) {
                switch (mapLayout.get(y).get(x)) {
                    case ("S"):     // Soft wall; breakable
                        if (Math.random() < SOFTWALL_RATE) {
                            BufferedImage sprSoftWall = getSoftWall(id)[new Random().nextInt(getSoftWall(id).length)];
                            Wall softWall = new Wall(new Point2D.Float(x * 32, y * 32), sprSoftWall, true);
                            GameObjectCollection.spawn(softWall);
                        }
                        break;

                    case ("H"):     // Hard wall; unbreakable
                        // biến code sử dụng để chọn hướng gạch dựa trên các ô liền kề
                        int code = 0;
                        if (y > 0 && mapLayout.get(y - 1).get(x).equals("H")) {
                            code += 1;  // North-Bắc
                        }
                        if (y < this.mapHeight - 1 && mapLayout.get(y + 1).get(x).equals("H")) {
                            code += 4;  // South-Nam
                        }
                        if (x > 0 && mapLayout.get(y).get(x - 1).equals("H")) {
                            code += 8;  // West-Tây
                        }
                        if (x < this.mapWidth - 1 && mapLayout.get(y).get(x + 1).equals("H")) {
                            code += 2;  // East-Đông
                        }
                        BufferedImage sprHardWall = ResourceCollection.getHardWallTile(code);
                        Wall hardWall = new Wall(new Point2D.Float(x * 32, y * 32), sprHardWall, false);
                        GameObjectCollection.spawn(hardWall);
                        break;

                    case ("1"):     // Player 1; Bomber
                        BufferedImage[][] sprMapP1 = ResourceCollection.SpriteMaps.PLAYER_1.getSprites();
                        Bomber player1 = new Bomber(new Point2D.Float(x * 32, y * 32 - 16), sprMapP1);
                        PlayerController playerController1 = new PlayerController(player1, this.controls1);
                        this.addKeyListener(playerController1);
                        this.gameHUD.assignPlayer(player1, 0);
                        GameObjectCollection.spawn(player1);
                        break;

                        /*
                    case ("2"):     // Player 2; Bomber
                        BufferedImage[][] sprMapP2 = ResourceCollection.SpriteMaps.PLAYER_2.getSprites();
                        Bomber player2 = new Bomber(new Point2D.Float(x * 32, y * 32 - 16), sprMapP2);
                        PlayerController playerController2 = new PlayerController(player2, this.controls2);
                        this.addKeyListener(playerController2);
                        this.gameHUD.assignPlayer(player2, 1);
                        GameObjectCollection.spawn(player2);
                        break;

                         */

                    case ("AIC"):     // AI; Cactus
                        BufferedImage[][] sprMapP2 = ResourceCollection.SpriteMaps.CACTUS.getSprites();
                        Bomber player2 = new Bomber(new Point2D.Float(x * 32, y * 32 - 16), sprMapP2);
                        PlayerController playerController2 = new PlayerController(player2, this.controls2);
                        this.addKeyListener(playerController2);
                        this.gameHUD.assignPlayer(player2, 1);
                        GameObjectCollection.spawn(player2);
                        break;

                    case ("PB"):    // Powerup Bomb
                        Powerup powerBomb = new Powerup(new Point2D.Float(x * 32, y * 32), Powerup.Type.Bomb);
                        GameObjectCollection.spawn(powerBomb);
                        break;

                    case ("PU"):    // Powerup Fireup
                        Powerup powerFireup = new Powerup(new Point2D.Float(x * 32, y * 32), Powerup.Type.Fireup);
                        GameObjectCollection.spawn(powerFireup);
                        break;

                    case ("PM"):    // Powerup Firemax
                        Powerup powerFiremax = new Powerup(new Point2D.Float(x * 32, y * 32), Powerup.Type.Firemax);
                        GameObjectCollection.spawn(powerFiremax);
                        break;

                    case ("PS"):    // Powerup Speed
                        Powerup powerSpeed = new Powerup(new Point2D.Float(x * 32, y * 32), Powerup.Type.Speed);
                        GameObjectCollection.spawn(powerSpeed);
                        break;

                    case ("PP"):    // Powerup Pierce
                        Powerup powerPierce = new Powerup(new Point2D.Float(x * 32, y * 32), Powerup.Type.Pierce);
                        GameObjectCollection.spawn(powerPierce);
                        break;

                    case ("PK"):    // Powerup Kick
                        Powerup powerKick = new Powerup(new Point2D.Float(x * 32, y * 32), Powerup.Type.Kick);
                        GameObjectCollection.spawn(powerKick);
                        break;

                    case ("PT"):    // Powerup Timer
                        Powerup powerTimer = new Powerup(new Point2D.Float(x * 32, y * 32), Powerup.Type.Timer);
                        GameObjectCollection.spawn(powerTimer);
                        break;

                    case ("P"):    // Portal
                        Powerup portal = new Powerup(new Point2D.Float(x * 32, y * 32), Powerup.Type.Portal);
                        GameObjectCollection.spawn(portal);
                        break;

                    case ("R"):    // Rose
                        BufferedImage sprSoftWall = ResourceCollection.Images.ROSE.getImage();
                        Wall softWall = new Wall(new Point2D.Float(x * 32, y * 32), sprSoftWall, true);
                        GameObjectCollection.spawn(softWall);
                        break;

                    case ("W"):     // Water; unbreakable
                        BufferedImage waterWall = ResourceCollection.Images.WATER.getImage();
                        Wall water = new Wall(new Point2D.Float(x * 32, y * 32), waterWall, false);
                        GameObjectCollection.spawn(water);
                        break;

                    case ("LV"):     // Lava; unbreakable
                        BufferedImage lavaWall = ResourceCollection.Images.LAVA.getImage();
                        Wall lava = new Wall(new Point2D.Float(x * 32, y * 32), lavaWall, false);
                        GameObjectCollection.spawn(lava);
                        break;

                    case ("CR1"): //creapy1; unbreakable
                        BufferedImage creepyWall1 = ResourceCollection.Images.CREEPY1.getImage();
                        Wall creepy1 = new Wall(new Point2D.Float(x * 32, y * 32), creepyWall1, false);
                        GameObjectCollection.spawn(creepy1);
                        break;
                    case ("CR2"): //creapy2; unbreakable
                        BufferedImage creepyWall2 = ResourceCollection.Images.CREEPY2.getImage();
                        Wall creepy2 = new Wall(new Point2D.Float(x * 32, y * 32), creepyWall2, false);
                        GameObjectCollection.spawn(creepy2);
                        break;
                    case ("CR3"): //creapy3; unbreakable
                        BufferedImage creepyWall3 = ResourceCollection.Images.CREEPY3.getImage();
                        Wall creepy3 = new Wall(new Point2D.Float(x * 32, y * 32), creepyWall3, false);
                        GameObjectCollection.spawn(creepy3);
                        break;
                    case ("CR4"): //creapy4; unbreakable
                        BufferedImage creepyWall4 = ResourceCollection.Images.CREEPY4.getImage();
                        Wall creepy4 = new Wall(new Point2D.Float(x * 32, y * 32), creepyWall4, false);
                        GameObjectCollection.spawn(creepy4);
                        break;

                    case ("PZ1"): //pizza; unbreakable
                        BufferedImage pizzaWall1 = ResourceCollection.Images.PIZZA1.getImage();
                        Wall pizza1 = new Wall(new Point2D.Float(x * 32, y * 32), pizzaWall1, false);
                        GameObjectCollection.spawn(pizza1);
                        break;
                    case ("PZ2"): //pizza; unbreakable
                        BufferedImage pizzaWall2 = ResourceCollection.Images.PIZZA2.getImage();
                        Wall pizza2 = new Wall(new Point2D.Float(x * 32, y * 32), pizzaWall2, false);
                        GameObjectCollection.spawn(pizza2);
                        break;
                    case ("PZ3"): //pizza; unbreakable
                        BufferedImage pizzaWall3 = ResourceCollection.Images.PIZZA3.getImage();
                        Wall pizza3 = new Wall(new Point2D.Float(x * 32, y * 32), pizzaWall3, false);
                        GameObjectCollection.spawn(pizza3);
                        break;
                    case ("PZ4"): //pizza; unbreakable
                        BufferedImage pizzaWall4 = ResourceCollection.Images.PIZZA4.getImage();
                        Wall pizza4 = new Wall(new Point2D.Float(x * 32, y * 32), pizzaWall4, false);
                        GameObjectCollection.spawn(pizza4);
                        break;

                    case ("CD1"): //candy; unbreakable
                        BufferedImage candyWall1 = ResourceCollection.Images.CANDY0.getImage();
                        Wall candy1 = new Wall(new Point2D.Float(x * 32, y * 32), candyWall1, false);
                        GameObjectCollection.spawn(candy1);
                        break;
                    case ("CD2"): //candy; unbreakable
                        BufferedImage candyWall2 = ResourceCollection.Images.CANDY1.getImage();
                        Wall candy2 = new Wall(new Point2D.Float(x * 32, y * 32), candyWall2, false);
                        GameObjectCollection.spawn(candy2);
                        break;
                    case ("CD3"): //candy; unbreakable
                        BufferedImage candyWall3 = ResourceCollection.Images.CANDY2.getImage();
                        Wall candy3 = new Wall(new Point2D.Float(x * 32, y * 32), candyWall3, false);
                        GameObjectCollection.spawn(candy3);
                        break;
                    case ("CD4"): //candy; unbreakable
                        BufferedImage candyWall4 = ResourceCollection.Images.CANDY3.getImage();
                        Wall candy4 = new Wall(new Point2D.Float(x * 32, y * 32), candyWall4, false);
                        GameObjectCollection.spawn(candy4);
                        break;

                    case ("TR"):     // Tree; unbreakable
                        BufferedImage treeWall = ResourceCollection.Images.TREE.getImage();
                        Wall tree = new Wall(new Point2D.Float(x * 32, y * 32), treeWall, false);
                        GameObjectCollection.spawn(tree);
                        break;

                    case ("STR"):     // snow Tree; unbreakable
                        BufferedImage snowTreeWall = ResourceCollection.Images.SNOWTREE.getImage();
                        Wall snowTree = new Wall(new Point2D.Float(x * 32, y * 32), snowTreeWall, false);
                        GameObjectCollection.spawn(snowTree);
                        break;


                    case ("SKE"):     // Rương kho báu; unbreakable
                        // biến code sử dụng để chọn hướng gạch dựa trên các ô liền kề
                        BufferedImage skeletonHead = ResourceCollection.Images.SKE.getImage();
                        Wall skeletonHeadW = new Wall(new Point2D.Float(x * 32, y * 32), skeletonHead, false);
                        GameObjectCollection.spawn(skeletonHeadW);
                        break;


                    default:
                        break;
                }
            }
        }
    }

    /**
     * Tạo key điều khiển cho người chơi.
     */
    private void setControls() {
        this.controls1 = new HashMap<>();
        this.controls2 = new HashMap<>();

        // Set Player 1 controls
        this.controls1.put(KeyEvent.VK_UP, Key.up);
        this.controls1.put(KeyEvent.VK_DOWN, Key.down);
        this.controls1.put(KeyEvent.VK_LEFT, Key.left);
        this.controls1.put(KeyEvent.VK_RIGHT, Key.right);
        this.controls1.put(KeyEvent.VK_ENTER, Key.action);

        // Set Player 2 controls
        this.controls2.put(KeyEvent.VK_W, Key.up);
        this.controls2.put(KeyEvent.VK_S, Key.down);
        this.controls2.put(KeyEvent.VK_A, Key.left);
        this.controls2.put(KeyEvent.VK_D, Key.right);
        this.controls2.put(KeyEvent.VK_J, Key.action);

    }

    /**
     * ESC tắt game
     */
    void exit() {
        this.running = false;
    }

    /**
     * F5 chơi lại từ đầu
     */
    void resetGame() {
        this.init();
    }

    void nextMap() {
        this.gameHUD.setLevel(this.gameHUD.getLevel() + 1);
        this.resetMap(this.gameHUD.getLevel() - 1);
    }

    /**
     * Reset map, điểm và level vẫn giữ. id từ 0 - 9.
     */
    private void resetMap(int id) {
        int real_id = id % 10;
        GameObjectCollection.init();
        this.generateMap(real_id);
        System.gc();
    }

    public void addNotify() {
        super.addNotify();

        if (this.thread == null) {
            this.thread = new Thread(this, "GameThread");
            this.thread.start();
        }
    }

    /**
     * The game loop.
     * The loop repeatedly calls update and repaints the panel.
     * Also reports the frames drawn per second and updates called per second (ticks).
     */
    @Override
    public void run() {
        long timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();

        final double NS = 1000000000.0 / 60.0; // Locked ticks per second to 60
        double delta = 0;

        // Count FPS, Ticks, and execute updates
        while (this.running) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / NS;
            lastTime = currentTime;

            if (delta >= 1) {
                try {
                    this.update();
                } catch (Exception e) {
                    System.out.println("Loi hack game");
                }
                delta--;
            }

            // vẽ lại tất cả
            this.repaint();

            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
            }
        }

        System.exit(0);
    }

    /**
     * The update method that loops through every game object and calls update.
     * Checks collisions between every two game objects.
     * Deletes game objects that are marked for deletion.
     * Checks if a player is a winner and updates score, then reset the map.
     */
    private void update() throws IndexOutOfBoundsException {
        GameObjectCollection.sortBomberObjects();
        // Loop through every game object arraylist
        for (int list = 0; list < GameObjectCollection.gameObjects.size(); list++) {
            for (int objIndex = 0; objIndex < GameObjectCollection.gameObjects.get(list).size(); ) {
                Entity obj = GameObjectCollection.gameObjects.get(list).get(objIndex);
                obj.update();
                if (obj.isDestroyed()) {
                    // Destroy and remove game objects that were marked for deletion
                    obj.onDestroy();
                    GameObjectCollection.gameObjects.get(list).remove(obj);
                } else {
                    try {
                        for (int list2 = 0; list2 < GameObjectCollection.gameObjects.size(); list2++) {
                            for (int objIndex2 = 0; objIndex2 < GameObjectCollection.gameObjects.get(list2).size(); objIndex2++) {
                                Entity collidingObj = GameObjectCollection.gameObjects.get(list2).get(objIndex2);
                                // Skip detecting collision on the same object as itself
                                if (obj == collidingObj) {
                                    continue;
                                }

                                // Visitor pattern collision handling
                                if (obj.getCollider().intersects(collidingObj.getCollider())) {
                                    // Use one of these
                                    collidingObj.onCollisionEnter(obj);
//                                obj.onCollisionEnter(collidingObj);
                                }
                            }
                        }
                        objIndex++;
                    } catch (Exception e) {
                        System.out.println("Lai loi");
                    }
                }
            }
        }

        // Check for the last bomber to survive longer than the others and increase score
        // Score is added immediately so there is no harm of dying when you are the last one
        // Reset map when there are 1 or less bombers left
        if (!this.gameHUD.matchSet) {
            this.gameHUD.updateScore();
        } else {
            boolean checkSupreme = false;
            for (Bomber bomber : GameObjectCollection.bomberObjects) {
                if (bomber.isSupreme()) {
                    checkSupreme = true;
                    break;
                }
            }
            // ĂN PORTAL
            if (GameObjectCollection.bomberObjects.size() == 1 && checkSupreme) {
                this.resetMap(this.gameHUD.getLevel() - 1);
                this.gameHUD.matchSet = false;
                System.out.println("an portal");
            }
            // Checking size of array list because when a bomber dies, they do not immediately get deleted
            // This makes it so that the next round doesn't start until the winner is the only bomber object on the map
            else if (GameObjectCollection.bomberObjects.size() == 1 && !checkSupreme) {
                this.resetMap(0);
                this.gameHUD.reset();
                System.out.println("ko an portal");
            }
        }

        // Used to prevent resetting the game really fast
        this.resetDelay++;

        try {
            Thread.sleep(500 / 144);
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        this.buffer = this.world.createGraphics();
        this.buffer.clearRect(0, 0, this.world.getWidth(), this.world.getHeight());
        super.paintComponent(g2);

        this.gameHUD.drawHUD();

        // Draw background
        for (int i = 0; i < this.world.getWidth(); i += this.bg.getWidth()) {
            for (int j = 0; j < this.world.getHeight(); j += this.bg.getHeight()) {
                this.buffer.drawImage(this.bg, i, j, null);
            }
        }

        // Draw background rieng map do an
        if (this.bg == ResourceCollection.Images.BISCUIT1.getImage()) {
            for (int i = 0; i < this.world.getWidth(); i += this.bg.getWidth()) {
                for (int j = 0; j < this.world.getHeight(); j += 2 * this.bg.getHeight()) {
                    this.buffer.drawImage(ResourceCollection.Images.BISCUIT2.getImage(), i, j, null);
                }
            }
        }


        // Draw game objects
        try {
            for (int i = 0; i < GameObjectCollection.gameObjects.size(); i++) {
                for (int j = 0; j < GameObjectCollection.gameObjects.get(i).size(); j++) {
                    Entity obj = GameObjectCollection.gameObjects.get(i).get(j);
                    obj.drawImage(this.buffer);
//                obj.drawCollider(this.buffer);
                }
            }
        } catch (Exception e) {
            System.out.println("1 xiu bug");
        }

        // Draw HUD
        int infoBoxWidth = panelWidth / 2;
        g2.drawImage(this.gameHUD.getP1info(), infoBoxWidth * 0, 0, null);
        g2.drawImage(this.gameHUD.getP2info(), infoBoxWidth * 1, 0, null);

        // Draw game world offset by the HUD
        g2.drawImage(this.world, 0, GameWindow.HUD_HEIGHT, null);

        g2.dispose();
        this.buffer.dispose();
    }

}

/**
 * Dùng để điều khiển game
 */
class GameController implements KeyListener {

    private GamePanel gamePanel;

    /**
     * Xây dựng một bộ nghe phím điều khiển chung cho trò chơi.
     * @param gamePanel bộ điều khiển trò chơi
     */
    GameController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Key cài đặt.
     * @param e Phím ấn
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Close game
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("Escape key pressed: Closing game");
            this.gamePanel.exit();
        }

        // Display controls
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            System.out.println("F1 key pressed: Displaying help");

            String[] columnHeaders = { "", "Red", "Blue"};
            Object[][] controls = {
                    {"Up", "Up", "W"},
                    {"Down", "Down", "S"},
                    {"Left", "Left", "A"},
                    {"Right", "Right", "D"},
                    {"Bomb", "ENTER", "J"},
                    {"", "", ""},
                    {"Help", "F1", ""},
                    {"Reset", "F5", ""},
                    {"Exit", "ESC", ""}
            };

            JTable controlsTable = new JTable(controls, columnHeaders);
            JTableHeader tableHeader = controlsTable.getTableHeader();

            // Wrap JTable inside JPanel to display
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(tableHeader, BorderLayout.NORTH);
            panel.add(controlsTable, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(this.gamePanel, panel, "Controls", JOptionPane.PLAIN_MESSAGE);
        }

        // Reset game
        // Delay prevents resetting too fast which causes the game to crash
        if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (this.gamePanel.resetDelay >= 20) {
                System.out.println("F5 key pressed: Resetting game");
                this.gamePanel.resetGame();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_F9) {
            System.out.println("hack game");
            this.gamePanel.nextMap();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
