package rpg_stub;

public class GameObject {
	protected String name;
	protected float x;
	protected float y;
	
	protected Sprite sprite;
	
	protected boolean collision;
	protected float col_off_x;
	protected float col_off_y;
	protected float colradius;
	
	public GameObject(String name, float x, float y, String spritefile, boolean collision, float col_off_x, float col_off_y, float colradius) {
        initObject(name, x, y, spritefile, collision, col_off_x, col_off_y, colradius);
    }
    
    private void initObject(String name, float x, float y, String spritefile, boolean collision, float col_off_x, float col_off_y, float colradius) {
    	sprite = new Sprite((int) Math.round(x), (int) Math.round(y));
        sprite.loadImage(spritefile);
        sprite.setX( (int) (Math.round(x - (sprite.getWidth()/2))) );
        sprite.setY( (int) (Math.round(y - (sprite.getHeight()/2))) );
        this.col_off_x = col_off_x;
        this.col_off_y = col_off_y;
        this.colradius = colradius;
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    public void updateSprite() {
    	sprite.setX( (int) (Math.round(x - (sprite.getWidth()/2))) );
        sprite.setY( (int) (Math.round(y - (sprite.getHeight()/2))) );
    }
    
    public float getColX() {
    	return x + col_off_x;
    }
    
    public float getColY() {
    	return y + col_off_y;
    }
    
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    public float getColRadius() {
    	return colradius;
    }
    
    public String getName() {
    	return name;
    }	
}
