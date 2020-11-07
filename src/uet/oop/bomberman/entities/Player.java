package uet.oop.bomberman.entities;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Lớp người chơi cho các đối tượng sẽ do người dùng điều khiển.
 */
public abstract class Player extends Entity {

    protected boolean UpPressed = false;
    protected boolean DownPressed = false;
    protected boolean LeftPressed = false;
    protected boolean RightPressed = false;
    protected boolean ActionPressed = false;

    /**
     * Thuộc tính người chơi.
     * @param position vị trí
     * @param sprite hoạt ảnh
     */
    Player(Point2D.Float position, BufferedImage sprite) {
        super(position, sprite);
    }

    public void toggleUpPressed() {
        this.UpPressed = true;
    }
    public void toggleDownPressed() {
        this.DownPressed = true;
    }
    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }
    public void toggleRightPressed() {
        this.RightPressed = true;
    }
    public void toggleActionPressed() {
        this.ActionPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }
    public void unToggleDownPressed() {
        this.DownPressed = false;
    }
    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }
    public void unToggleRightPressed() {
        this.RightPressed = false;
    }
    public void unToggleActionPressed() {
        this.ActionPressed = false;
    }

    // sau làm thêm 1 hàm tạo lại keyMove dựa vào map
    public void AutoMove() {
        int keyMove = (int) (Math.random() * 3);
        if (keyMove == 0) {
            this.toggleDownPressed();
        }
        if (keyMove == 1) {
            this.toggleUpPressed();
        }
        if (keyMove == 2) {
            this.toggleLeftPressed();
        }
        if (keyMove == 3) {
            this.toggleRightPressed();
        }
    }

    public void UnMove() {
        this.unToggleUpPressed();
        this.unToggleDownPressed();
        this.unToggleRightPressed();
        this.unToggleLeftPressed();
    }

}
