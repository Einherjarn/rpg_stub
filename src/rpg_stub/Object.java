package rpg_stub;

public class Object extends Sprite {
	protected float colx;
	protected float coly;
	protected float colradius;
	protected String name;
	
    public Object(int x, int y, String objname, float radius) {
        super(x, y);
        initObject(objname, radius);
    }
    
    private void initObject(String objname, float radius) {  
        loadImage(objname);
        colx = getX() + (getWidth() / 2);
        coly = getY() + (getHeight() / 2);
        colradius = radius;
        name = objname;
    }
    
    public void updateObject() {
    	colx = getX() + (getWidth() / 2);
        coly = getY() + (getHeight() / 2);
    }
    
    public float getColX() {
    	return colx;
    }
    
    public float getColY() {
    	return coly;
    }
    
    public float getColRadius() {
    	return colradius;
    }
    
    public String getName() {
    	return name;
    }	
}