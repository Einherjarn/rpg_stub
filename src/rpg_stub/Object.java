package rpg_stub;

public class Object extends Sprite {
	protected float colx;
	protected float coly;
	protected float colradius;
	
    public Object(int x, int y, String objname, float radius) {
        super(x, y);

        initObject(objname, radius);
    }
    
    private void initObject(String objname, float radius) {        
        loadImage(objname);
        colx = getX() + (getWidth() / 2);
        coly = getY() + (getHeight() / 2);
        colradius = radius;
    }
}