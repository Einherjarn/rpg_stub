package rpg_stub;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Player player;
    private Map activemap;
    private boolean ingame;
    private final int B_WIDTH = 1000;
    private final int B_HEIGHT = 1000;
    private final int DELAY = 15;
    
    private boolean show_inventory;
    private Sprite inventorysprite;
    protected List<InventoryItem> inventoryitems = new ArrayList<InventoryItem>();

    protected List<GameObject> objects = new ArrayList<GameObject>();
    
    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.WHITE);
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        // public Player(String name, float x, float y, String spritefile, boolean collision, float col_off_x, float col_off_y, float colradius, float speed) {
        player = new Player("Player", 50, 50, "resources/player.png", true, 0, 0, 10, 2);
        activemap = new Map(0,0,"resources/map_01_devtest.png");
        
        // collision parameters for placeholder trees
        int treecolx = 0;
        int treecoly = 30;
        int treerad = 20;
        
        // make some trees
        int[] trees_x = {125,50,75,261,337,297,330,268,300,474,531,434,320,414,337,336,459,360,362};
        int[] trees_y = {140,140,150,11,12,19,43,48,63,310,342,363,466,476,497,540,540,600,679};
        for(int i=0; i< trees_x.length; i++) {
        	objects.add(new GameObject("Tree", trees_x[i],trees_y[i], "resources/collision_object_shittytree.png", true, treecolx, treecoly, treerad));
        }
        
        timer = new Timer(DELAY, this);
        timer.start();
        inventorysprite = new Sprite(800, 700);
        inventorysprite.loadImage("resources/ui_inventory_crappy.png");
        show_inventory = true;
        
        //InventoryItem(String name, int sizeX, int sizeY, int baseGoldValue, int row, int col, String spritefile)
        inventoryitems.add(new InventoryItem("Hp Potion",1,1, 100, 1,1, "resources/item_healthpotion.png"));
        inventoryitems.add(new InventoryItem("Shoddy Axe",2,1, 100, 2,1, "resources/item_axe01.png"));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ingame) {
            drawObjects(g);

        } else {
            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    // update sprite positions and draw objects
    private void drawObjects(Graphics g) {
    	if (activemap.isVisible()) {
            g.drawImage(activemap.getImage(), activemap.getX(), activemap.getY(), this);
        }
    	
        if (player.sprite.isVisible()) {
        	player.updateSprite();
            g.drawImage(player.sprite.getImage(), player.sprite.getX(), player.sprite.getY(), this);
        }
        
        for(GameObject obj : objects) {
        	if(obj.sprite.isVisible()) {
        		obj.updateSprite();
        		g.drawImage(obj.sprite.getImage(), obj.sprite.getX(), obj.sprite.getY(), this);
        	}
        }
        if(show_inventory) {
        	inventorysprite.setVisible(show_inventory);
        	g.drawImage(inventorysprite.getImage(), inventorysprite.getX(), inventorysprite.getY(), this);
        	
        	for(InventoryItem item : inventoryitems) {
            	if(item.sprite.isVisible()) {
            		item.updateSprite();
            		g.drawImage(item.sprite.getImage(), item.sprite.getX(), item.sprite.getY(), this);
            	}
            }
        }
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.BLACK);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();
        updatePlayer();
        checkCollisions();

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    // checks input for movement
    private void updatePlayer() {

        if (player.sprite.isVisible()) { 
        	player.move();
        	//System.out.println(player.x);
        }
    }

    // calculates collisions between player and all gameobjects
    public void checkCollisions() {
    	for(GameObject obj : objects) {
        	if(obj.collision = true) {
        		double dist = Math.sqrt(Math.pow((player.getColX() - obj.getColX()), 2) + Math.pow((player.getColY() - obj.getColY()),2));
        		double overlap = dist;
        		overlap -= player.getColRadius();
        		overlap -= obj.getColRadius();
        		if(overlap < 0) {
        			player.x -= overlap * (player.getColX() - obj.getColX())/dist;
        			player.y -= overlap * (player.getColY() - obj.getColY())/dist;
        		}
        	}
        }
    }
    
    // key press detection stuff
    private class TAdapter extends KeyAdapter {
    	@Override
        public void keyPressed(KeyEvent e) {
    		int key = e.getKeyCode();
            
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                player.dx = -1;}
            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            	player.dx = 1;}
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            	player.dy = -1;}
            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            	player.dy = 1;}
            if (key == KeyEvent.VK_I) {
            	show_inventory = !show_inventory;
            }
        }
    	
        @Override
        public void keyReleased(KeyEvent e) {
        	int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            	player.dx = 0;}
            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            	player.dx = 0;}
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            	player.dy = 0;}
            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            	player.dy = 0;
            }
        }
    }
}