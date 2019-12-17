package rpg_stub;

public class InventoryItem {
	protected String name;
	// size of item for inventory tetris
	protected int sizeX;
	protected int sizeY;
	protected int baseGoldValue;
	
	// row and col that this item occupies in inventory
	protected int row;
	protected int col;
	
	protected Sprite sprite;
	
	public InventoryItem(String name, int sizeX, int sizeY, int baseGoldValue, int row, int col, String spritefile) {
        initInventoryItem(name, sizeX, sizeY, baseGoldValue, row, col, spritefile);
    }
	private void initInventoryItem(String name, int sizeX, int sizeY, int baseGoldValue, int row, int col, String spritefile) {
		int sprite_x = 500 +15 -37 +row*37;
		int sprite_y = 400 +77 -37 +col*37;
		sprite = new Sprite(sprite_x, sprite_y);
        sprite.loadImage(spritefile);
        sprite.setX(sprite_x);
        sprite.setY(sprite_y);
        
		this.name = name;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.baseGoldValue = baseGoldValue;
		
		this.row = row;
		this.col = col;
	}
	
	public void updateSprite() {
		int sprite_x = 500 +15 -37 +row*37;
		int sprite_y = 400 +77 -37 +col*37;
		sprite.setX(sprite_x);
        sprite.setY(sprite_y);
    }
}
