package uet.oop.bomberman.entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Tập hợp tất cả các tệp được tải vào chương trình.
 */
public class ResourceCollection {

    private static HashMap<Integer, BufferedImage> hardWallTiles;

    public enum Images {
        ICON,
        BACKGROUND,
        SOFT_WALL,
        POWER_BOMB,
        POWER_FIREUP,
        POWER_FIREMAX,
        POWER_SPEED,
        POWER_PIERCE,
        POWER_KICK,
        POWER_TIMER,
        PORTAL;

        private BufferedImage image = null;

        public BufferedImage getImage() {
            return this.image;
        }
    }

    public enum SpriteMaps {
        PLAYER_1,
        PLAYER_2,
        PLAYER_3,
        PLAYER_4,
        HARD_WALLS,
        BOMB,
        BOMB_PIERCE,
        EXPLOSION_SPRITEMAP;

        private BufferedImage image = null;
        private BufferedImage[][] sprites = null;

        public BufferedImage getImage() {
            return this.image;
        }

        public BufferedImage[][] getSprites() {
            return this.sprites;
        }
    }

    public enum Files {
        MAP1, MAP2, MAP3, MAP4, MAP5, MAP6, MAP7, MAP8, MAP9, MAP10;

        private InputStreamReader file = null;

        public InputStreamReader getFile() {
            return this.file;
        }
    }

    /**
     * Lấy đúng hướng cho các bức tường cứng được chỉ định bằng phím kí hiệu (biến code);
     * @param key phím kí hiệu (file excel)
     * @return Một bức tường cứng riêng lẻ
     */
    public static BufferedImage getHardWallTile(Integer key) {
        return hardWallTiles.get(key);
    }

    /**
     * Đọc tệp vào chương trình.
     */
    public static void readFiles() {
        try {
            Images.ICON.image = ImageIO.read(ResourceCollection.class.getResource("/textures/icon.png"));
            Images.BACKGROUND.image = ImageIO.read(ResourceCollection.class.getResource("/textures/grass.png"));
            Images.SOFT_WALL.image = ImageIO.read(ResourceCollection.class.getResource("/textures/softWall.png"));
            Images.POWER_BOMB.image = ImageIO.read(ResourceCollection.class.getResource("/textures/power_bomb.png"));
            Images.POWER_FIREUP.image = ImageIO.read(ResourceCollection.class.getResource("/textures/power_fireup.png"));
            Images.POWER_FIREMAX.image = ImageIO.read(ResourceCollection.class.getResource("/textures/power_firemax.png"));
            Images.POWER_SPEED.image = ImageIO.read(ResourceCollection.class.getResource("/textures/power_speed.png"));
            Images.POWER_PIERCE.image = ImageIO.read(ResourceCollection.class.getResource("/textures/power_pierce.png"));
            Images.POWER_KICK.image = ImageIO.read(ResourceCollection.class.getResource("/textures/power_kick.png"));
            Images.POWER_TIMER.image = ImageIO.read(ResourceCollection.class.getResource("/textures/power_timer.png"));
            Images.PORTAL.image = ImageIO.read(ResourceCollection.class.getResource("/textures/portal.png"));

            SpriteMaps.PLAYER_1.image = ImageIO.read(ResourceCollection.class.getResource("/textures/bomber1.png"));
            SpriteMaps.PLAYER_2.image = ImageIO.read(ResourceCollection.class.getResource("/textures/bomber2.png"));
            SpriteMaps.HARD_WALLS.image = ImageIO.read(ResourceCollection.class.getResource("/textures/hardWalls.png"));
            SpriteMaps.BOMB.image = ImageIO.read(ResourceCollection.class.getResource("/textures/bomb.png"));
            SpriteMaps.BOMB_PIERCE.image = ImageIO.read(ResourceCollection.class.getResource("/textures/bomb_pierce.png"));
            SpriteMaps.EXPLOSION_SPRITEMAP.image = ImageIO.read(ResourceCollection.class.getResource("/textures/explosion.png"));

            Files.MAP1.file = new InputStreamReader(ResourceCollection.class.getResourceAsStream("/textures/maps/level10.csv"));
            Files.MAP2.file = new InputStreamReader(ResourceCollection.class.getResourceAsStream("/textures/maps/level2.csv"));
            Files.MAP3.file = new InputStreamReader(ResourceCollection.class.getResourceAsStream("/textures/maps/level3.csv"));
            Files.MAP4.file = new InputStreamReader(ResourceCollection.class.getResourceAsStream("/textures/maps/level4.csv"));
            Files.MAP5.file = new InputStreamReader(ResourceCollection.class.getResourceAsStream("/textures/maps/level5.csv"));
            Files.MAP6.file = new InputStreamReader(ResourceCollection.class.getResourceAsStream("/textures/maps/level6.csv"));
            Files.MAP7.file = new InputStreamReader(ResourceCollection.class.getResourceAsStream("/textures/maps/level7.csv"));
            Files.MAP8.file = new InputStreamReader(ResourceCollection.class.getResourceAsStream("/textures/maps/level8.csv"));
            Files.MAP9.file = new InputStreamReader(ResourceCollection.class.getResourceAsStream("/textures/maps/level9.csv"));
            Files.MAP10.file = new InputStreamReader(ResourceCollection.class.getResourceAsStream("/textures/maps/level10.csv"));


        } catch (IOException e) {
            System.err.println(e + ": Không đọc được file ảnh");
            e.printStackTrace();
        }
    }

    /**
     * Cắt và tải bản đồ sprite.
     */
    public static void init() {
        SpriteMaps.PLAYER_1.sprites = sliceSpriteMap(SpriteMaps.PLAYER_1.image, 32, 48);
        SpriteMaps.PLAYER_2.sprites = sliceSpriteMap(SpriteMaps.PLAYER_2.image, 32, 48);
        SpriteMaps.HARD_WALLS.sprites = sliceSpriteMap(SpriteMaps.HARD_WALLS.image, 32, 32);
        SpriteMaps.BOMB.sprites = sliceSpriteMap(SpriteMaps.BOMB.image, 32, 32);
        SpriteMaps.BOMB_PIERCE.sprites = sliceSpriteMap(SpriteMaps.BOMB_PIERCE.image, 32, 32);
        SpriteMaps.EXPLOSION_SPRITEMAP.sprites = sliceSpriteMap(SpriteMaps.EXPLOSION_SPRITEMAP.image, 32, 32);
        loadHardWallTiles(SpriteMaps.HARD_WALLS.sprites);   // Load hard wall tiles into hashmap for bit masking
    }

    /**
     * Slice sprite sheet into individual sprites stored in a two-dimensional array.
     * @param spriteMap Sprite sheet to be sliced
     * @param spriteWidth Width of each individual sprite
     * @param spriteHeight Height of each individual sprite
     * @return Two-dimensional array of sprites
     */
    private static BufferedImage[][] sliceSpriteMap(BufferedImage spriteMap, int spriteWidth, int spriteHeight) {
        int rows = spriteMap.getHeight() / spriteHeight;
        int cols = spriteMap.getWidth() / spriteWidth;
        BufferedImage[][] sprites = new BufferedImage[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                sprites[row][col] = spriteMap.getSubimage(col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
            }
        }

        return sprites;
    }

    /**
     * Called in loadMap to load tile map for hard wall sprites.
     * The correct sprite is chosen for hard wall based on adjacent hard walls.
     * @param tiles Double array of sliced tile map
     */
    private static void loadHardWallTiles(BufferedImage[][] tiles) {
        hardWallTiles = new HashMap<>();
        /*
            [ ][1][ ]
            [8][X][2]
            [ ][4][ ]
            1st bit = north
            2nd bit = east
            3rd bit = south
            4th bit = west
            These bits indicate if there is an adjacent hard wall in that direction
            (NOT THE DIRECTION THE WALL IS FACING)
         */
        hardWallTiles.put(0b0000, tiles[0][0]);  // 0

        hardWallTiles.put(0b0001, tiles[0][2]);  // N
        hardWallTiles.put(0b0010, tiles[0][3]);  // E
        hardWallTiles.put(0b0100, tiles[0][1]);  // S
        hardWallTiles.put(0b1000, tiles[0][4]);  // W

        hardWallTiles.put(0b0011, tiles[2][3]);  // N E
        hardWallTiles.put(0b1001, tiles[2][4]);  // N W
        hardWallTiles.put(0b0110, tiles[2][1]);  // S E
        hardWallTiles.put(0b1100, tiles[2][2]);  // S W

        hardWallTiles.put(0b1010, tiles[3][0]);  // W E
        hardWallTiles.put(0b0101, tiles[2][0]);  // N S

        hardWallTiles.put(0b1011, tiles[1][2]);  // N E W
        hardWallTiles.put(0b0111, tiles[1][3]);  // N E S
        hardWallTiles.put(0b1110, tiles[1][1]);  // S E W
        hardWallTiles.put(0b1101, tiles[1][4]);  // S W N

        hardWallTiles.put(0b1111, tiles[1][0]);  // N S W E
    }
}
