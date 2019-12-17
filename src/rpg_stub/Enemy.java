package rpg_stub;

public class Enemy extends GameObject {
    protected float speed;
    protected int damage;
    protected int health;
    protected int maxhealth;
    protected float aggrorange;
    
    public Enemy(String name, float x, float y, String spritefile, boolean collision, float col_off_x, float col_off_y, float colradius,
    		float speed, int damage, int health, float aggrorange) {
        super(name, x, y, spritefile, collision, col_off_x, col_off_y, colradius);
        initEnemy(speed, damage, health, aggrorange);
    }

    private void initEnemy(float speed, int damage, int health, float aggrorange) {
        this.speed = speed;
        this.damage = damage;
        this.health = health;
        maxhealth = health;
        this.aggrorange = aggrorange;
    }

    public void move(float dx, float dy) {
        x += (dx * speed);
        y += (dy * speed);
        
    }
    
    public void setPos(float x, float y) {
    	this.x = x;
    	this.y = y;
    }
}