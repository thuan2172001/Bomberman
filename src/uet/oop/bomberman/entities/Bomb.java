package uet.oop.bomberman.entities;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Bomb objects
 */
public class Bomb extends TileObject {

    private Bomber bomber;

    // Hoạt ảnh
    private BufferedImage[][] sprites;
    private int spriteIndex;
    private int spriteTimer;

    // Thuộc tính bom
    private int firepower;  // độ lớn bomb
    private boolean pierce; // khả năng đâm xuyên gạch
    private int timeToDetonate; // thời gian bomb nổ
    private int timeElapsed; // thời gian trôi qua

    // Đá bom
    private boolean kicked;
    private KickDirection kickDirection;

    /**
     * Thuộc tính bom.
     * @param position Tọa độ bom
     * @param firepower Độ dài bom
     * @param pierce Khả năng xuyên thủng gạch của bom
     * @param timeToDetonate Thời gian bomb nổ
     * @param bomber Người đặt bom
     */
    public Bomb(Point2D.Float position, int firepower, boolean pierce, int timeToDetonate, Bomber bomber) {
        // nếu bom có khả năng xuyên thì lấy ảnh bomb_pierce, không thì lấy ảnh bomb thường
        super(position, pierce ? ResourceCollection.SpriteMaps.BOMB_PIERCE.getSprites()[0][0]
                : ResourceCollection.SpriteMaps.BOMB.getSprites()[0][0]);
        this.collider.setRect(this.position.x, this.position.y, this.width, this.height);

        // hoạt ảnh bomb
        this.sprites = pierce ? ResourceCollection.SpriteMaps.BOMB_PIERCE.getSprites()
                : ResourceCollection.SpriteMaps.BOMB.getSprites();
        this.spriteIndex = 0;
        this.spriteTimer = 0;

        // Thuộc tính bomb
        this.firepower = firepower;
        this.pierce = pierce;
        this.timeToDetonate = timeToDetonate;
        this.bomber = bomber;
        this.timeElapsed = 0;
        this.breakable = true;

        // Khả năng sút
        this.kicked = false;
        this.kickDirection = KickDirection.Nothing;
    }

    /**
     * Bom nổ tạo ra các vụ nổ, +1 lại bomb sau mỗi bomb nổ.
     */
    private void explode() {
        this.snapToGrid();
        // nổ theo chiều ngang
        GameObjectCollection.spawn(new Explosion.Horizontal(this.position, this.firepower, this.pierce));
        // nổ theo chiều dọc
        GameObjectCollection.spawn(new Explosion.Vertical(this.position, this.firepower, this.pierce));
        // trả lại số lượng bomb cũ
        this.bomber.restoreAmmount();

        Sound.play(Sound.EXPLORE, 0);

    }

    public void setKicked(boolean kicked, KickDirection kickDirection) {
        this.kicked = kicked;
        this.kickDirection = kickDirection;
    }

    public boolean isKicked() {
        return this.kicked;
    }

    public void stopKick() {
        this.kicked = false;
        this.kickDirection = KickDirection.Nothing;
        this.snapToGrid();
    }

    @Override
    public void update() {
        this.collider.setRect(this.position.x, this.position.y, this.width, this.height);

        // Animate sprite
        if (this.spriteTimer++ >= 4) {
            this.spriteIndex++;
            this.spriteTimer = 0;
        }
        if (this.spriteIndex >= this.sprites[0].length) {
            this.spriteIndex = 0;
        }
        this.sprite = this.sprites[0][this.spriteIndex];

        // xóa bomb sau khi nổ
        if (this.timeElapsed++ >= this.timeToDetonate) {
            this.destroy();
        }

        // Tiếp tục di chuyển khi sút
        if (this.kicked) {
            this.position.setLocation(this.position.x + this.kickDirection.getVelocity().x,
                    this.position.y + this.kickDirection.getVelocity().y);
        }
    }

    @Override
    public void onDestroy() {
        this.explode();
    }

    @Override
    public void onCollisionEnter(Entity collidingObj) {
        collidingObj.handleCollision(this);
    }

    /**
     * Dừng quả bomb đang lăn khi đâm vào bomber khác
     */
    @Override
    public void handleCollision(Bomber collidingObj) {
        Point2D.Float temp = new Point2D.Float((float) this.collider.getCenterX() + this.kickDirection.getVelocity().x, (float) this.collider.getCenterY() + this.kickDirection.getVelocity().y);
        Rectangle2D intersection = this.collider.createIntersection(collidingObj.collider);
        if (this.kicked && intersection.contains(temp)) {
            System.out.println("Bomb bị chặn lại");
            this.stopKick();
            this.solidCollision(collidingObj);
            this.snapToGrid();
        }
    }

    @Override
    public void handleCollision(Wall collidingObj) {
        this.solidCollision(collidingObj);
        this.stopKick();
    }

    @Override
    public void handleCollision(Bomb collidingObj) {
        this.solidCollision(collidingObj);
        this.stopKick();
    }

    /**
     * Bomb va chạm với vụ nổ.
     * Nổ ngay lập tức
     */
    @Override
    public void handleCollision(Explosion collidingObj) {
        this.destroy();
    }

    @Override
    public boolean isBreakable() {
        return this.breakable;
    }

}

/**
 * Khả năng sút bomb. Speed để ở 6.
 */
enum KickDirection {

    FromTop(new Point2D.Float(0, 6)),
    FromBottom(new Point2D.Float(0, -6)),
    FromLeft(new Point2D.Float(6, 0)),
    FromRight(new Point2D.Float(-6, 0)),
    Nothing(new Point2D.Float(0, 0));

    private Point2D.Float velocity;

    KickDirection(Point2D.Float velocity) {
        this.velocity = velocity;
    }

    public Point2D.Float getVelocity() {
        return this.velocity;
    }

}
