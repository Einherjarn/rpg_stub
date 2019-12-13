package rpg_stub;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Player extends Object {

    private float dx;
    private float dy;
    protected float speed;
    
    public Player(int x, int y, String name, float radius) {
        super(x, y, name, radius);

        initPlayer();
    }

    private void initPlayer() {
    	
    	//loadImage("resources/player.png");
        speed = 2;
    }

    public void move() {

        x += dx * speed;
        y += dy * speed;

        if (x < 0) {
            x = 0;
        }

        if (y < 0) {
            y = 0;
        }
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