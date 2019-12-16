package rpg_stub;

public class Enemy extends GameObject {
    protected float speed;
    protected float dx;
    protected float dy;
    
    public Enemy(String name, float x, float y, String spritefile, boolean collision, float col_off_x, float col_off_y, float colradius, float speed) {
        super(name, x, y, spritefile, collision, col_off_x, col_off_y, colradius);
        initEnemy(speed);
    }

    private void initEnemy(float speed) {
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
}