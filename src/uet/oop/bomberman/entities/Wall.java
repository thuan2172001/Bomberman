package uet.oop.bomberman.entities;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Wall extends TileObject {

    /**
     * Khởi tạo tường
     * @param position Vị trí
     * @param sprite Hoạt ảnh
     * @param isBreakable true = mềm , false = cứng
     */
    public Wall(Point2D.Float position, BufferedImage sprite, boolean isBreakable) {
        super(position, sprite);
        this.breakable = isBreakable;
    }

    /**
     * Phá hủy animation tường khi nổ.
     */
    @Override
    public void update() {
        if (this.checkExplosion()) {
            this.destroy();
        }
    }

    /**
     * Ngẫu nhiên powerup xuất hiện
     */
    @Override
    public void onDestroy() {
        double random = Math.random(); // random từ 0.0 - 1.0
        if (random < 0.5) {
            Powerup powerup = new Powerup(this.position, Powerup.randomPower());
            GameObjectCollection.spawn(powerup);
        }
    }

    @Override
    public void onCollisionEnter(Entity collidingObj) {
        collidingObj.handleCollision(this);
    }

    /**
     * Kiểm tra xem tường cứng hay mềm.
     * @return true = mềm, false = cứng
     */
    @Override
    public boolean isBreakable() {
        return this.breakable;
    }

    @Override
    protected void handleCollision(Monster collidingObj) {

    }
}
