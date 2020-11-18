package uet.oop.bomberman.entities;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Entity implements Observable, Collidable {

    // Thuộc tính chung vật thể
    Point2D.Float position;
    BufferedImage sprite;
    Rectangle2D.Float collider;
    float rotation;
    float width;
    float height;

    // Thuộc tính bị phá hủy (Dùng để phá hủy hoạt ảnh)
    private boolean destroyed;

    Entity(Point2D.Float position) {
        this.position = new Point2D.Float(position.x, position.y);
        this.rotation = 0;
    }

    Entity(Point2D.Float position, BufferedImage sprite) {
        this(sprite);
        this.position = new Point2D.Float(position.x, position.y);
        this.rotation = 0;
        this.collider = new Rectangle2D.Float(position.x, position.y, this.width, this.height);
    }

    private Entity(BufferedImage sprite) {
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
    }

    void destroy() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Xử lí va chạm vật rắn như tường
     * @param obj vật rắn như tường
     */
    void solidCollision(Entity obj) {
        Rectangle2D intersection = this.collider.createIntersection(obj.collider);
        // Vertical collision
        if (intersection.getWidth() >= intersection.getHeight()) {
            // From the top
            if (intersection.getMaxY() >= this.collider.getMaxY()) {
                this.position.setLocation(this.position.x, this.position.y - intersection.getHeight());
            }
            // From the bottom
            if (intersection.getMaxY() >= obj.collider.getMaxY()) {
                this.position.setLocation(this.position.x, this.position.y + intersection.getHeight());
            }

            // Smoothing around corners
            if (intersection.getWidth() < 16) {
                if (intersection.getMaxX() >= this.collider.getMaxX()) {
                    this.position.setLocation(this.position.x - 0.5, this.position.y);
                }
                if (intersection.getMaxX() >= obj.collider.getMaxX()) {
                    this.position.setLocation(this.position.x + 0.5, this.position.y);
                }
            }
        }

        // Va chạm ngang
        if (intersection.getHeight() >= intersection.getWidth()) {
            // From the left
            if (intersection.getMaxX() >= this.collider.getMaxX()) {
                this.position.setLocation(this.position.x - intersection.getWidth(), this.position.y);
            }
            // From the right
            if (intersection.getMaxX() >= obj.collider.getMaxX()) {
                this.position.setLocation(this.position.x + intersection.getWidth(), this.position.y);
            }

            // Smoothing around corners
            if (intersection.getHeight() < 16) {
                if (intersection.getMaxY() >= this.collider.getMaxY()) {
                    this.position.setLocation(this.position.x, this.position.y - 0.5);
                }
                if (intersection.getMaxY() >= obj.collider.getMaxY()) {
                    this.position.setLocation(this.position.x, this.position.y + 0.5);
                }
            }
        }
    }

    /**
     * Nhận lại vị trí của Collider
     * @return A Rectangle2D collider
     */
    public Rectangle2D.Float getCollider() {
        return this.collider;
    }

    /**
     * Nhận trung tâm của object's collider
     */
    public Point2D.Float getColliderCenter() {
        return new Point2D.Float((float) this.collider.getCenterX(), (float) this.collider.getCenterY());
    }

    public float getPositionY() {
        return this.position.y + this.height;
    }

    /**
     * Hàm vẽ một object.
     */
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.position.getX(), this.position.getY());
        rotation.rotate(Math.toRadians(this.rotation), this.sprite.getWidth() / 2.0, this.sprite.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

    @Override
    public int compareTo(Entity o) {
        return Float.compare(this.position.y, o.position.y);
    }
}

/**
 * interface để định hướng khả năng có thể được thấy của một thực thể.
 */
interface Observable {

    /**
     * render lại Entity mỗi game loop.
     */
    default void update() {

    }

    /**
     * Gọi khi mà một thực thể bị phá hủy.
     */
    default void onDestroy() {

    }

    int compareTo(Entity o);
}

/**
 * Va chạm Interface.
 */
interface Collidable {

    /**
     * Gọi khi 2 vật thể va chạm. Ghi đè định nghĩa trong các lớp con của Entity.
     * Cách dùng: collidingObj.handleCollision(this);   // Đặt hàm này trong phương thức
     * @param collidingObj Vật thể va chạm vào
     */
    void onCollisionEnter(Entity collidingObj);

    default void handleCollision(Monster collidingObj) {

    }

    default void handleCollision(DragonMonster collidingObj) {

    }

    default void handleCollision(FireMonster collidingObj) {

    }

    default void handleCollision(Bomber collidingObj) {

    }

    default void handleCollision(Wall collidingObj) {

    }

    default void handleCollision(Explosion collidingObj) {

    }

    default void handleCollision(Bomb collidingObj) {

    }

    default void handleCollision(Powerup collidingObj) {

    }

}