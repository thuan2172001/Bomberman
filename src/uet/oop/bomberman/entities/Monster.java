
package uet.oop.bomberman.entities;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Monster extends Player {
    // thuộc tính
    private boolean dead;
    private double moveSpeed;
    private boolean stop;

    public void setDead(boolean dead) {
        this.dead = dead;
    }
    public boolean getDead() {
        return this.dead;
    }

    // hoạt ảnh
    private BufferedImage[][] sprites;
    private int direction;  // 0: lên, 1: xuống, 2: trái, 3: phải
    private int spriteIndex;
    private int spriteTimer;
    private String typeMonster;

    public Monster(Point2D.Float position, BufferedImage[][] spriteMap) {
        super(position, spriteMap[1][0]);
        this.collider.setRect(this.position.x + 3, this.position.y + 16 + 3,
                this.width - 6, this.height - 16 - 6);

        // Hoạt ảnh
        this.sprites = spriteMap;
        this.direction = 1;     // Mặc định hướng là xuống
        this.spriteIndex = 0;
        this.spriteTimer = 0;
        this.moveSpeed = 0.5;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public double getMoveSpeed() {
        return this.moveSpeed;
    }

    public void moveUp() {
        this.direction = 0;     // lên (y giảm)
        this.position.setLocation(this.position.x, this.position.y - this.moveSpeed);
    }

    public void moveDown() {
        this.direction = 1;     // xuống (y tăng)
        this.position.setLocation(this.position.x, this.position.y + this.moveSpeed);
    }

    public void moveLeft() {
        this.direction = 2;     // trái (x giảm)
        this.position.setLocation(this.position.x - this.moveSpeed, this.position.y);
    }

    public void moveRight() {
        this.direction = 3;     // phải (x tăng)
        this.position.setLocation(this.position.x + this.moveSpeed, this.position.y);
    }

    public BufferedImage getBaseSprite() {
        return this.sprites[1][0];
    }

    public boolean isDead() {
        return this.dead;
    }


    public boolean isStop() {
        return this.stop;
    }

    public void setDead() {
        this.dead = true;
    }

    /**
     * Điều khiển, hành động, hoạt ảnh
     */
    @Override
    public void update() {
        this.collider.setRect(this.position.x + 3, this.position.y + 16 + 3,
                this.width - 6, this.height - 16 - 6);

        // Hoạt ảnh nếu còn sống
        if (!this.dead) {
            // thời gian chuyển hoạt ảnh = thời gian hoạt ảnh mặc định + tốc độ di chuyển
            if ((this.spriteTimer += this.moveSpeed) >= 12) {
                this.spriteIndex++;
                this.spriteTimer = 0;
            }

            if ((!this.stop ) || (this.spriteIndex >= this.sprites[0].length)) {
                this.spriteIndex = 0;
            }

            this.sprite = this.sprites[this.direction][this.spriteIndex];

            // Di chuyển
            MonsterController monsterController = new MonsterController(this);
            monsterController.MonsterMove();

        } else {
            // Hoạt ảnh chết
            // Cứ 0.3s chuyển ảnh
            if (this.spriteTimer++ >= 30) {
                this.spriteIndex++;
                if (this.spriteIndex < this.sprites[4].length) {
                    this.sprite = this.sprites[4][this.spriteIndex];
                    this.spriteTimer = 0;
                } else if (this.spriteTimer >= 250) {
                    this.destroy();
                }
            }
        }
    }


    @Override
    public void onCollisionEnter(Entity collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Bomber collidingObj) {
        collidingObj.setDead();
    }

    @Override
    public void handleCollision(Wall collidingObj) {
        this.solidCollision(collidingObj);
        //this.stop = true;
    }

    @Override
    public void handleCollision(Monster collidingObj) {
        this.handleCollision(collidingObj);
    }

    @Override
    public void handleCollision(Explosion collidingObj) {
        if (!this.dead) {
            this.dead = true;
            this.spriteIndex = 0;
        }
    }

    @Override
    public void handleCollision(Bomb collidingObj) {
        this.solidCollision(collidingObj);
    }

    @Override
    public void handleCollision(Powerup collidingObj) {
        this.moveSpeed++;
        collidingObj.destroy();
    }

    public void setTypeMonster(String typeMonster) {
        this.typeMonster = typeMonster;
    }

    public String getTypeMonster() {
        return typeMonster;
    }
}