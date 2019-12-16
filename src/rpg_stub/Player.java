package rpg_stub;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    protected float speed;
    protected float dx;
    protected float dy;
    
    public Player(String name, float x, float y, String spritefile, boolean collision, float col_off_x, float col_off_y, float colradius, float speed) {
        super(name, x, y, spritefile, collision, col_off_x, col_off_y, colradius);
        initPlayer(speed);
    }

    private void initPlayer(float speed) {
        this.speed = speed;
    }

    public void move() {
        x += (dx * speed);
        y += (dy * speed);
        
    }
    
    public void setPos(float x, float y) {
    	this.x = x;
    	this.y = y;
    }
    
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            dy = 1;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            dy = 0;
        }
    }
}