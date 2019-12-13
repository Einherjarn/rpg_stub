package rpg_stub;
public class CollisionObject extends Sprite {
	// collision sphere info
	private float CollisionX;
	private float CollisionY;
	private float CollisionRadius;
    public CollisionObject(int x, int y, String objectfile) {
        super(x, y);

        initCollisionObject(objectfile);
    }
    
    private void initCollisionObject(String objectfile) {
        
        loadImage(objectfile);
        getImageDimensions();        
    }
    
    public float getCollisionX() {
    	return CollisionX;
    }
    public float getCollisionY() {
    	return CollisionY;
    }
    public float getCollisionRadius() {
    	return CollisionRadius;
    }
}