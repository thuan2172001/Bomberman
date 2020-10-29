package uet.oop.bomberman.entities;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Powerup extends TileObject {

    public enum Type {
        // Bổ sung số lượng bomb
        Bomb(ResourceCollection.Images.POWER_BOMB.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.addAmount(1);
            }
        },

        // Bổ sung sức nổ bomb + 1
        Fireup(ResourceCollection.Images.POWER_FIREUP.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.addFirepower(1);
            }
        },

        // Tăng sức nổ bomb tối đa (6)
        Firemax(ResourceCollection.Images.POWER_FIREMAX.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.addFirepower(6);
            }
        },

        // Tăng tốc độ lên 0.5
        Speed(ResourceCollection.Images.POWER_SPEED.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.addSpeed(0.5f);
            }
        },

        // Kích hoạt khả năng xuyên vật thể mềm
        Pierce(ResourceCollection.Images.POWER_PIERCE.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.setPierce(true);
            }
        },

        // Kích hoạt khả năng đá bomb
        Kick(ResourceCollection.Images.POWER_KICK.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.setKick(true);
            }
        },

        // Giảm thời gian bomb nổ đi 0.15s
        Timer(ResourceCollection.Images.POWER_TIMER.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.reduceTimer(15);
            }
        },

        Portal(ResourceCollection.Images.PORTAL.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.nextMap(1);
            }
        }
        ;

        private BufferedImage sprite;

        /**
         * Đặt hoạt ảnh tương ứng với loại sức mạnh.
         * @param sprite Powerup sprite
         */
        Type(BufferedImage sprite) {
            this.sprite = sprite;
        }

        /**
         * Ghi đè bởi các loại sức mạnh.
         * @param bomber bomber được tăng sức mạnh
         */
        protected abstract void grantBonus(Bomber bomber);

    }

    private Type type;

    /**
     * Khởi tạo một loại powerup. Loại có thể là ngẫu nhiên.
     * @param position vị trí
     * @param type loại powerup
     */
    public Powerup(Point2D.Float position, Type type) {
        super(position, type.sprite);
        this.collider = new Rectangle2D.Float(position.x + 8, position.y + 8,
                this.width - 16, this.height - 16);
        this.type = type;
        this.breakable = true;
    }

    // Random loại sức mạnh
    private static Powerup.Type[] powerups = Powerup.Type.values();
    private static Random random = new Random();
    static final Powerup.Type randomPower() {
        // if(quái chết hết) return powerups[powerups.length - 1]; // tạo ra PORTAL
        return powerups[random.nextInt(powerups.length - 1)]; // không random ra PORTAL
    }

    /**
     * Tăng sức mạnh cho bomber.
     * @param bomber Bomber được tăng sức mạnh
     */
    void grantBonus(Bomber bomber) {
        this.type.grantBonus(bomber);
    }

    /**
     * Phá hủy hoạt ảnh khi bị bomb nổ mất.
     */
    @Override
    public void update() {
        if (this.checkExplosion()) {
            this.destroy();
        }
    }

    @Override
    public void onCollisionEnter(Entity collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Bomb collidingObj) {
        this.destroy();
    }

    @Override
    public boolean isBreakable() {
        return this.breakable;
    }

}

