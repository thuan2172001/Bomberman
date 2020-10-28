package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Bombs, walls, powerups; những thứ trả về grass
 */
public abstract class TileObject extends Entity {

    protected boolean breakable;
    protected Explosion explosionContact;

    TileObject(Point2D.Float position, BufferedImage sprite) {
        super(position, sprite);
        this.snapToGrid();
    }

    public abstract boolean isBreakable();

    protected boolean checkExplosion() {
        return this.isBreakable() && this.explosionContact != null && this.explosionContact.isDestroyed();
    }

    /**
     * nạp lại object từ float
     */
    protected void snapToGrid() {
        // Snap bombs to the grid on the map
        float x = Math.round(this.position.getX() / 32) * 32;
        float y = Math.round(this.position.getY() / 32) * 32;
        this.position.setLocation(x, y);
    }


    /**
     * First explosionContact to collide this wall will destroy this object once its animation finishes
     * @param collidingObj First explosionContact to collide this wall
     */
    @Override
    public void handleCollision(Explosion collidingObj) {
        if (this.isBreakable()) {
            if (this.explosionContact == null) {
                this.explosionContact = collidingObj;
            }
        }
    }
}
