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
    private static HashMap<Integer, BufferedImage> waterFallTiles;

    public enum Images {
        ICON,
        BACKGROUND, BLOCKTILE, SNOWTILE, PALACETILE, BISCUIT1, BISCUIT2,
        FOOD0, FOOD1, FOOD2, FOOD3,
        CANDY0, CANDY1, CANDY2, CANDY3,
        BOX0, BOX1, BOX2, BOX3, BOX4, BOX5, BOX6, BOX7, BOX8, BOX9, BOX10,
        ROSE,
        PIZZA1, PIZZA2, PIZZA3, PIZZA4,
        TREE, SNOWTREE, WATER, LAVA, CREEPY1, CREEPY2, CREEPY3, CREEPY4,
        SKE,
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
        WATER_FALL,
        HARD_WALLS,
        BOMB,
        BOMB_PIERCE,
        FIRE_MONSTER,
        DRAGON_MONSTER,
        DRAGON_RIDER, CACTUS,
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
            Images.BLOCKTILE.image = ImageIO.read(ResourceCollection.class.getResource("/textures/blocktile.jpg"));
            Images.PALACETILE.image = ImageIO.read(ResourceCollection.class.getResource("/textures/palacetile.png"));
            Images.BISCUIT1.image = ImageIO.read(ResourceCollection.class.getResource("/textures/biscuit1.png"));
            Images.BISCUIT2.image = ImageIO.read(ResourceCollection.class.getResource("/textures/biscuit2.png"));

            Images.FOOD0.image = ImageIO.read(ResourceCollection.class.getResource("/textures/food0.png"));
            Images.FOOD1.image = ImageIO.read(ResourceCollection.class.getResource("/textures/food1.png"));
            Images.FOOD2.image = ImageIO.read(ResourceCollection.class.getResource("/textures/food2.png"));
            Images.FOOD3.image = ImageIO.read(ResourceCollection.class.getResource("/textures/food3.png"));

            Images.BOX0.image = ImageIO.read(ResourceCollection.class.getResource("/textures/box0.png"));
            Images.BOX1.image = ImageIO.read(ResourceCollection.class.getResource("/textures/box1.png"));
            Images.BOX2.image = ImageIO.read(ResourceCollection.class.getResource("/textures/box2.png"));
            Images.BOX3.image = ImageIO.read(ResourceCollection.class.getResource("/textures/box3.png"));
            Images.BOX4.image = ImageIO.read(ResourceCollection.class.getResource("/textures/box4.png"));
            Images.BOX5.image = ImageIO.read(ResourceCollection.class.getResource("/textures/box5.png"));
            Images.BOX6.image = ImageIO.read(ResourceCollection.class.getResource("/textures/box6.png"));
            Images.BOX7.image = ImageIO.read(ResourceCollection.class.getResource("/textures/box7.png"));
            Images.BOX8.image = ImageIO.read(ResourceCollection.class.getResource("/textures/box8.png"));
            Images.BOX9.image = ImageIO.read(ResourceCollection.class.getResource("/textures/box9.png"));
            Images.BOX10.image = ImageIO.read(ResourceCollection.class.getResource("/textures/box10.png"));

            Images.ROSE.image = ImageIO.read(ResourceCollection.class.getResource("/textures/rose.png"));
            Images.TREE.image = ImageIO.read(ResourceCollection.class.getResource("/textures/tree.png"));
            Images.SKE.image = ImageIO.read(ResourceCollection.class.getResource("/textures/treasure.png"));
            Images.SNOWTREE.image = ImageIO.read(ResourceCollection.class.getResource("/textures/snowTree.png"));
            Images.WATER.image = ImageIO.read(ResourceCollection.class.getResource("/textures/water.png"));
            Images.LAVA.image = ImageIO.read(ResourceCollection.class.getResource("/textures/lava.png"));
            Images.SNOWTILE.image = ImageIO.read(ResourceCollection.class.getResource("/textures/snowtile.png"));

            Images.CANDY0.image = ImageIO.read(ResourceCollection.class.getResource("/textures/candy1.png"));
            Images.CANDY1.image = ImageIO.read(ResourceCollection.class.getResource("/textures/candy2.png"));
            Images.CANDY2.image = ImageIO.read(ResourceCollection.class.getResource("/textures/candy3.png"));
            Images.CANDY3.image = ImageIO.read(ResourceCollection.class.getResource("/textures/candy4.png"));

            Images.PIZZA1.image = ImageIO.read(ResourceCollection.class.getResource("/textures/pizza1.png"));
            Images.PIZZA2.image = ImageIO.read(ResourceCollection.class.getResource("/textures/pizza2.png"));
            Images.PIZZA3.image = ImageIO.read(ResourceCollection.class.getResource("/textures/pizza3.png"));
            Images.PIZZA4.image = ImageIO.read(ResourceCollection.class.getResource("/textures/pizza4.png"));

            Images.CREEPY1.image = ImageIO.read(ResourceCollection.class.getResource("/textures/part1.png"));
            Images.CREEPY2.image = ImageIO.read(ResourceCollection.class.getResource("/textures/part2.png"));
            Images.CREEPY3.image = ImageIO.read(ResourceCollection.class.getResource("/textures/part3.png"));
            Images.CREEPY4.image = ImageIO.read(ResourceCollection.class.getResource("/textures/part4.png"));

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
            SpriteMaps.WATER_FALL.image = ImageIO.read(ResourceCollection.class.getResource("/textures/waterFall.png"));
            SpriteMaps.BOMB.image = ImageIO.read(ResourceCollection.class.getResource("/textures/bomb.png"));
            SpriteMaps.BOMB_PIERCE.image = ImageIO.read(ResourceCollection.class.getResource("/textures/bomb_pierce.png"));
            SpriteMaps.EXPLOSION_SPRITEMAP.image = ImageIO.read(ResourceCollection.class.getResource("/textures/explosion.png"));
            SpriteMaps.DRAGON_MONSTER.image = ImageIO.read(ResourceCollection.class.getResource("/textures/dragonMonster.png"));
            SpriteMaps.FIRE_MONSTER.image = ImageIO.read(ResourceCollection.class.getResource("/textures/dragonRider.png"));
            //SpriteMaps.DRAGON_RIDER.image = ImageIO.read(ResourceCollection.class.getResource("/textures/dragonRider.png"));
            SpriteMaps.CACTUS.image = ImageIO.read(ResourceCollection.class.getResource("/textures/fireMonster.png"));


            Files.MAP1.file = new InputStreamReader(ResourceCollection.class.getResourceAsStream("/textures/maps/level1.csv"));
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
        SpriteMaps.DRAGON_MONSTER.sprites = sliceSpriteMap(SpriteMaps.DRAGON_MONSTER.image, 32, 48);
        SpriteMaps.FIRE_MONSTER.sprites = sliceSpriteMap(SpriteMaps.FIRE_MONSTER.image, 32, 48);
        SpriteMaps.CACTUS.sprites = sliceSpriteMap(SpriteMaps.CACTUS.image, 32, 48);
        SpriteMaps.HARD_WALLS.sprites = sliceSpriteMap(SpriteMaps.HARD_WALLS.image, 32, 32);
        SpriteMaps.BOMB.sprites = sliceSpriteMap(SpriteMaps.BOMB.image, 32, 32);
        SpriteMaps.BOMB_PIERCE.sprites = sliceSpriteMap(SpriteMaps.BOMB_PIERCE.image, 32, 32);
        SpriteMaps.EXPLOSION_SPRITEMAP.sprites = sliceSpriteMap(SpriteMaps.EXPLOSION_SPRITEMAP.image, 32, 32);
        loadHardWallTiles(SpriteMaps.HARD_WALLS.sprites);   // Load hard wall tiles into hashmap for bit masking
    }

    /**
     * Cắt nhỏ spriteSheet ra đề dùng.
     * @param spriteMap Sprite sheet
     * @param spriteWidth Width của từng sprite
     * @param spriteHeight Height của từng sprite
     * @return mảng 2 chiều chứa các sprite sau khi đc cắt
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
     * Hàm gọi tường cứng để phù hợp với hướng
     * phụ thuộc vào các vật thể tường cứng nằm cạnh.
     * @param tiles Double array of sliced tile map
     */
    private static void loadHardWallTiles(BufferedImage[][] tiles) {
        hardWallTiles = new HashMap<>();
        /*
            [NS][1] [NE]
            [3] [X] [2]
            [SW][4] [EW]
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
