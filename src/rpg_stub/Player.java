package rpg_stub;

public class Player extends GameObject {
    protected float speed;
    protected float dx;
    protected float dy;
    protected int health;
    protected int maxhealth;
    
    public Player(String name, float x, float y, String spritefile, boolean collision, float col_off_x, float col_off_y, float colradius,
    		float speed) {
        super(name, x, y, spritefile, collision, col_off_x, col_off_y, colradius);
        initPlayer(speed);
    }

    private void initPlayer(float speed) {
        this.speed = speed;
        health = 100;
        maxhealth = 100;
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