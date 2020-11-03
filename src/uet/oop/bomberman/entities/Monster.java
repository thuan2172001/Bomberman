package uet.oop.bomberman.entities;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Monster extends Player {
    // thuộc tính
    private boolean dead;
    private boolean speed;

    // hoạt ảnh
    private BufferedImage[][] sprites;
    private int direction;  // 0: lên, 1: xuống, 2: trái, 3: phải
    private int spriteIndex;
    private int spriteTimer;

    Monster(Point2D.Float position, BufferedImage sprite) {
        super(position, sprite);
    }


    @Override
    public void update() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onCollisionEnter(Entity collidingObj) {

    }

    @Override
    public void handleCollision(Bomber collidingObj) {

    }

    @Override
    public void handleCollision(Wall collidingObj) {

    }

    @Override
    public void handleCollision(Explosion collidingObj) {

    }

    @Override
    public void handleCollision(Bomb collidingObj) {

    }

    @Override
    public void handleCollision(Powerup collidingObj) {

    }
}
