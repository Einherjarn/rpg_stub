package rpg_stub;

public class InventoryItem {
	protected String name;
	protected int sizeX;
	protected int sizeY;
	protected int baseGoldValue;
	
	public InventoryItem(String name, int sizeX, int sizeY, int baseGoldValue) {
        initInventoryItem(name, sizeX, sizeY, baseGoldValue);
    }
	private void initInventoryItem(String name, int sizeX, int sizeY, int baseGoldValue) {
		this.name = name;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.baseGoldValue = baseGoldValue;
	}
}
