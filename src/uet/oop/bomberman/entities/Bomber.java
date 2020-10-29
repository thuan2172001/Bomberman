package uet.oop.bomberman.entities;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Nhân vật đặt bom
 */
public class Bomber extends Player {

    private Bomb bomb;
    private boolean dead;

    // Hoạt ảnh
    private BufferedImage[][] sprites;
    private int direction;  // 0: lên, 1: xuống, 2: trái, 3: phải
    private int spriteIndex;
    private int spriteTimer;

    // Thuộc tính
    private float moveSpeed;
    private int firepower;
    private int maxBombs; //số bombs tối đa
    private int bombAmmount; //số bombs hiện tại, khởi tạo mặc định là maxBombs, mỗi lần đặt thì trừ đi 1
    private int bombTimer; //thời gian bomb nổ, khởi tạo mặc định là 250, tối thiểu là 160
    private boolean pierce;
    private boolean kick;
    private int level;

    /**
     * Tạo người đặt bom ở vị trí position với hoạt ảnh là mảng 2 chiều
     * @param position Tọa độ
     * @param spriteMap hoạt ảnh
     */
    public Bomber(Point2D.Float position, BufferedImage[][] spriteMap) {
        super(position, spriteMap[1][0]);
        this.collider.setRect(this.position.x + 3, this.position.y + 16 + 3,
                this.width - 6, this.height - 16 - 6);

        // Hoạt ảnh
        this.sprites = spriteMap;
        this.direction = 1;     // Mặc định hướng là xuống
        this.spriteIndex = 0;
        this.spriteTimer = 0;

        // Khởi tạo mặc định
        this.moveSpeed = 1;
        this.firepower = 1;
        this.maxBombs = 1;
        this.bombAmmount = this.maxBombs;
        this.bombTimer = 250;
        this.pierce = false;
        this.kick = false;
    }

    // --- Di chuyển ---
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

    // --- Hành động ---
    private void plantBomb() {
        // Đặt bom
        float x = Math.round(this.position.getX() / 32) * 32;
        float y = Math.round((this.position.getY() + 16) / 32) * 32;
        Point2D.Float spawnLocation = new Point2D.Float(x, y);

        // Chỉ cho phép 1 thứ tồn tại trên 1 ô
        for (int i = 0; i < GameObjectCollection.tileObjects.size(); i++) {
            Entity obj = GameObjectCollection.tileObjects.get(i);
            // kiểm tra xem đã có thứ gì nằm trên ô chưa, nếu có rồi thì out hàm
            if (obj.collider.contains(spawnLocation)) {
                return;
            }
        }

        // Đặt bomb
        this.bomb = new Bomb(spawnLocation, this.firepower, this.pierce, this.bombTimer, this);
        GameObjectCollection.spawn(bomb);
        this.bombAmmount--;
    }

    // tăng bomb, nếu bomb tối đa thì k tăng nữa
    public void restoreAmmount() {
        this.bombAmmount = Math.min(this.maxBombs, this.bombAmmount + 1);
    }

    // --- Tăng sức mạnh ---
    public void addAmount(int value) {
        System.out.print("Bomb đang có: " + this.maxBombs);
        this.maxBombs = Math.min(6, this.maxBombs + value);
        this.restoreAmmount();
        System.out.println(" tăng lên thành: " + this.maxBombs);
    }
    public void addFirepower(int value) {
        System.out.print("Độ dài bomb hiện tại: " + this.firepower);
        this.firepower = Math.min(6, this.firepower + value);
        System.out.println(" tăng lên thành: " + this.firepower);
    }
    public void addSpeed(float value) {
        System.out.print("Tốc độ di chuyển hiện tại: " + this.moveSpeed);
        this.moveSpeed = Math.min(4, this.moveSpeed + value);
        System.out.println(" tăng lên thành: " + this.moveSpeed);
    }
    public void setPierce(boolean value) {
        System.out.print("Khả năng đâm xuyên: " + this.pierce);
        this.pierce = value;
        System.out.println(" trở thành: " + this.pierce);
    }
    public void setKick(boolean value) {
        System.out.print("Khả năng sút bomb: " + this.kick);
        this.kick = value;
        System.out.println(" trở thành " + this.kick);
    }
    public void reduceTimer(int value) {
        System.out.print("Thời gian bomb hiện tại: " + this.bombTimer);
        this.bombTimer = Math.max(160, this.bombTimer - value);
        System.out.println(" thành: " + this.bombTimer);
    }
    public void nextMap(int value) {
        System.out.print("Level hiện tại: " + this.level);
        if (this.level < 3) this.level++;
        else this.level = 0;
        System.out.println(" thành: " + this.level);
    }

    /**
     * Dùng trong GameHUD làm hoạt ảnh cơ sở.
     * @return ảnh bomber hướng xuống
     */
    public BufferedImage getBaseSprite() {
        return this.sprites[1][0];
    }

    /**
     * Kiểm tra xem người đặt bomb chết chưa.
     * @return true : chết, false : chưa
     */
    public boolean isDead() {
        return this.dead;
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

            // Hành động đặt bomb
            if (this.ActionPressed && this.bombAmmount > 0) {
                this.plantBomb();
            }
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

    // Gọi va chạm với VẬT THỂ
    @Override
    public void onCollisionEnter(Entity collidingObj) {
        collidingObj.handleCollision(this);
    }

    // Xử lí va chạm với TƯỜNG
    @Override
    public void handleCollision(Wall collidingObj) {
        this.solidCollision(collidingObj);
    }

    /**
     * Xử lí chết.
     * @param collidingObj Bomb nổ
     */
    @Override
    public void handleCollision(Explosion collidingObj) {
        if (!this.dead) {
            this.dead = true;
            this.spriteIndex = 0;
        }
    }

    /**
     * Bombs act as walls if the bomber is not already within the a certain distance as the bomb.
     * This is also the big and ugly kicking logic. Touching this code is very dangerous and can introduce
     * bugs to the kicking logic including stopping the bomb from moving.
     * (ie. if the bomber is not standing on the bomb)
     * @param collidingObj Solid bomb
     */
    @Override
    public void handleCollision(Bomb collidingObj) {
        // Khởi tạo trung tâm vụ nổ
        Rectangle2D intersection = this.collider.createIntersection(collidingObj.collider);
        // Xử lí chiều dọc
        if (intersection.getWidth() >= intersection.getHeight() && intersection.getHeight() <= 6 && Math.abs(this.collider.getCenterX() - collidingObj.collider.getCenterX()) <= 8) {
            if (this.kick && !collidingObj.isKicked()) {
                // From the top
                if (intersection.getMaxY() >= this.collider.getMaxY() && this.DownPressed) {
                    collidingObj.setKicked(true, KickDirection.FromTop);
                }
                // From the bottom
                if (intersection.getMaxY() >= collidingObj.collider.getMaxY() && this.UpPressed) {
                    collidingObj.setKicked(true, KickDirection.FromBottom);
                }
            }
            this.solidCollision(collidingObj);
        }
        // Horizontal collision
        if (intersection.getHeight() >= intersection.getWidth() && intersection.getWidth() <= 6 && Math.abs(this.collider.getCenterY() - collidingObj.collider.getCenterY()) <= 8) {
            if (this.kick && !collidingObj.isKicked()) {
                // From the left
                if (intersection.getMaxX() >= this.collider.getMaxX() && this.RightPressed) {
                    collidingObj.setKicked(true, KickDirection.FromLeft);
                }
                // From the right
                if (intersection.getMaxX() >= collidingObj.collider.getMaxX() && this.LeftPressed) {
                    collidingObj.setKicked(true, KickDirection.FromRight);
                }
            }
            this.solidCollision(collidingObj);
        }
    }

    /**
     * Xử lí va chạm với POWERUP
     * @param collidingObj loại sức mạnh
     */
    @Override
    public void handleCollision(Powerup collidingObj) {
        collidingObj.grantBonus(this);
        collidingObj.destroy();
    }

}
