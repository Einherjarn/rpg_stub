package rpg_stub;

import java.util.List;

public class Map extends Sprite {
	private List<CollisionObject> CollisionObjects;
	
    public Map(int x, int y, String mapname) {
        super(x, y);

        initMap(mapname);
    }
    
    private void initMap(String mapname) {
        
        loadImage(mapname);
        getImageDimensions();        
    }
    
    public List<CollisionObject> getCollisionObjects() {
        return CollisionObjects;
    }
}