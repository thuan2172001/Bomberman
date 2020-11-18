package uet.oop.bomberman.entities;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class FireMonster extends Monster {
    // thuộc tính
    private boolean dead;
    private double moveSpeed;

    // hoạt ảnh
    private BufferedImage[][] sprites;
    private int direction;  // 0: lên, 1: xuống, 2: trái, 3: phải
    private int spriteIndex;
    private int spriteTimer;
    private String typeMonster;
    private static int keyMove;

    public FireMonster(Point2D.Float position, BufferedImage[][] spriteMap) {
        super(position, spriteMap);
        this.collider.setRect(this.position.x + 3, this.position.y + 16 + 3,
                this.width - 6, this.height - 16 - 6);

        // Hoạt ảnh
        this.sprites = spriteMap;
        this.direction = 1;     // Mặc định hướng là xuống
        this.spriteIndex = 0;
        this.spriteTimer = 0;
        this.moveSpeed = 2.0;
        keyMove = 0;
    }

    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public double getMoveSpeed() {
        return this.moveSpeed;
    }

    private void moveUp() {
        this.direction = 0;     // lên (y giảm)
        this.position.setLocation(this.position.x, this.position.y - this.moveSpeed);
    }

    private void moveDown() {
        this.direction = 1;     // xuống (y tăng)
        this.position.setLocation(this.position.x, this.position.y + this.moveSpeed);
    }

    private void moveLeft() {
        this.direction = 2;     // trái (x giảm)
        this.position.setLocation(this.position.x - this.moveSpeed, this.position.y);
    }

    private void moveRight() {
        this.direction = 3;     // phải (x tăng)
        this.position.setLocation(this.position.x + this.moveSpeed, this.position.y);
    }

    public BufferedImage getBaseSprite() {
        return this.sprites[1][0];
    }

    public boolean isDead() {
        return this.dead;
    }

    public void setDead() {
        this.dead = true;
    }

    /**
     * Điều khiển, hành động, hoạt ảnh
     */
    @Override
    public void update() {
        this.AutoMove();
        this.collider.setRect(this.position.x + 3, this.position.y + 16 + 3,
                this.width - 6, this.height - 16 - 6);

        // Hoạt ảnh nếu còn sống
        if (!this.dead) {
            // thời gian chuyển hoạt ảnh = thời gian hoạt ảnh mặc định + tốc độ di chuyển
            if ((this.spriteTimer += this.moveSpeed) >= 12) {
                this.spriteIndex++;
                this.spriteTimer = 0;
            }

            if ((!this.UpPressed && !this.DownPressed && !this.LeftPressed && !this.RightPressed)
                    || (this.spriteIndex >= this.sprites[0].length)) {
                this.spriteIndex = 0;
            }

            this.sprite = this.sprites[this.direction][this.spriteIndex];

            // Di chuyển
            if (this.UpPressed) {
                this.moveUp();
            }
            if (this.DownPressed) {
                this.moveDown();
            }
            if (this.LeftPressed) {
                this.moveLeft();
            }
            if (this.RightPressed) {
                this.moveRight();
            }

        } else {
            // Hoạt ảnh chết
            // Cứ 0.3s chuyển ảnh
            this.setDead();
            if (this.spriteTimer++ >= 30) {
                this.spriteIndex++;
                if (this.spriteIndex < this.sprites[4].length) {
                    this.sprite = this.sprites[4][this.spriteIndex];
                    this.spriteTimer = 0;
                } else if (this.spriteTimer >= 120) {
                    this.destroy();
                }
            }
        }
        this.UnMove();
    }

    @Override
    public void AutoMove() {
        if (keyMove == 0) { // xuống
            this.toggleDownPressed();
        }
        if (keyMove == 1) { // lên
            this.toggleUpPressed();
        }
        if (keyMove == 2) { // trái
            this.toggleLeftPressed();
        }
        if (keyMove == 3) { // phải
            this.toggleRightPressed();
        }
    }

    @Override
    public void onCollisionEnter(Entity collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Bomber collidingObj) {
        if(!this.dead) collidingObj.setDead();
    }


    // đi xuyên vật thể mềm
    @Override
    public void handleCollision(Wall collidingObj) {
        if (!collidingObj.isBreakable()) {
            this.solidCollision(collidingObj);
            int keyRandom = (int) Math.round(Math.random() * 3);
            this.solidCollision(collidingObj);
            if (keyMove == 0) {
                keyMove = keyRandom;
            }
            else if (keyMove == 1) {
                keyMove = keyRandom;
            }
            else if (keyMove == 2) {
                keyMove = keyRandom;
            }
            else keyMove = keyRandom;
        }
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
        int keyRandom = (int) Math.round(Math.random() * 3);
        this.solidCollision(collidingObj);
        if (keyMove == 0) {
            keyMove = keyRandom;
        }
        else if (keyMove == 1) {
            keyMove = keyRandom;
        }
        else if (keyMove == 2) {
            keyMove = keyRandom;
        }
        else if (keyMove == 3) {
            keyMove = keyRandom;
        }
    }

    @Override
    public void handleCollision(Powerup collidingObj) {
        this.moveSpeed += 0.25;
        collidingObj.destroy();
    }

    public void setTypeMonster(String typeMonster) {
        this.typeMonster = typeMonster;
    }

    public String getTypeMonster() {
        return typeMonster;
    }
}
