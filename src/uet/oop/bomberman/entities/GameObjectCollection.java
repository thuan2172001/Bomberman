package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameObjectCollection {

    public static List<List<? extends Entity>> gameObjects;

    // Vật thể mềm là bombs, walls, và powerups
    public static ArrayList<TileObject> tileObjects;
    public static ArrayList<Explosion> explosionObjects;
    public static ArrayList<Bomber> bomberObjects;
    public static ArrayList<Monster> monsterObjects;
    public static ArrayList<DragonMonster> DragonmonsterObjects;
    public static ArrayList<FireMonster> FiremonsterObjects;

    /**
     * Khởi tạo các mảng sẽ chứa tất cả các đối tượng trò chơi.
     */
    public static void init() {
        gameObjects = new ArrayList<>();
        tileObjects = new ArrayList<>();
        explosionObjects = new ArrayList<>();
        bomberObjects = new ArrayList<>();
        monsterObjects = new ArrayList<>();
        DragonmonsterObjects = new ArrayList<>();
        FiremonsterObjects = new ArrayList<>();

        gameObjects.add(tileObjects);
        gameObjects.add(explosionObjects);
        gameObjects.add(bomberObjects);
        gameObjects.add(monsterObjects);
        gameObjects.add(DragonmonsterObjects);
        gameObjects.add(FiremonsterObjects);
    }

    /**
     * Thêm một đối tượng trò chơi vào mảng để quan sát và vẽ.
     * @param spawnObj Đối tượng được thêm
     */
    public static void spawn(TileObject spawnObj) {
        tileObjects.add(spawnObj);
    }
    public static void spawn(Explosion spawnObj) {
        explosionObjects.add(spawnObj);
    }
    public static void spawn(Bomber spawnObj) {
        bomberObjects.add(spawnObj);
    }
    public static void spawn(Monster spawnObj) {
        monsterObjects.add(spawnObj);
    }
    public static void spawn(DragonMonster spawnObj) {
        DragonmonsterObjects.add(spawnObj);
    }
    public static void spawn(FireMonster spawnObj) {
        FiremonsterObjects.add(spawnObj);
    }

    /**
     * Sắp xếp danh sách đối tượng theo vị trí y. Dùng để vẽ các đối tượng theo thứ tự vị trí y.
     */
    public static void sortTileObjects() {
        tileObjects.sort(Comparator.comparing(Entity::getPositionY));
    }
    public static void sortExplosionObjects() {
        explosionObjects.sort(Comparator.comparing(Entity::getPositionY));
    }
    public static void sortBomberObjects() {
        bomberObjects.sort(Comparator.comparing(Entity::getPositionY));
    }
    public static void sortMonsterObjects() {
        monsterObjects.sort(Comparator.comparing(Entity::getPositionY));
    }

}
