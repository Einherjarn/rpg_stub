package rpg_stub;

public class Enemy extends GameObject {
    protected float speed;
    protected int damage;
    protected int health;
    protected float aggrorange;
    protected float dx;
    protected float dy;
    
    public Enemy(String name, float x, float y, String spritefile, boolean collision, float col_off_x, float col_off_y, float colradius,
    		float speed, int damage, int health, float aggrorange) {
        super(name, x, y, spritefile, collision, col_off_x, col_off_y, colradius);
        initEnemy(speed, damage, health, aggrorange);
    }

    private void initEnemy(float speed, int damage, int health, float aggrorange) {
        this.speed = speed;
        this.damage = damage;
        this.health = health;
        this.aggrorange = aggrorange;
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