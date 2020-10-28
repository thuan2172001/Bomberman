package uet.oop.bomberman.entities;


/**
 * Các ràng buộc chính được sử dụng để điều khiển.
 * Tạo một đối tượng HashMap liên kết các phím này với các phím trên bàn phím để điều khiển.
 *
 * Vd:
 * HashMap <Integer, Key> control = new HashMap <> ();
 * control.put (KeyEvent.VK_UP, Key.up);
 * control.put (KeyEvent.VK_DOWN, Key.down);
 * control.put (KeyEvent.VK_LEFT, Key.left);
 * control.put (KeyEvent.VK_RIGHT, Key.right);
 * control.put (KeyEvent.VK_SLASH, Key.action);
 */
public class Key {

    public static Key up = new Key();       // Lên
    public static Key down = new Key();     // Xuống
    public static Key left = new Key();     // Trái
    public static Key right = new Key();    // Phải
    public static Key action = new Key();   // Đặt bomb

}
