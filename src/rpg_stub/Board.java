package rpg_stub;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Player player;
    private Map activemap;
    private boolean ingame;
    private final int B_WIDTH = 700;
    private final int B_HEIGHT = 700;
    private final int DELAY = 15;
    
    private boolean show_inventory;
    private Sprite inventorysprite;
    protected List<InventoryItem> inventoryitems = new ArrayList<InventoryItem>();

    protected List<GameObject> objects = new ArrayList<GameObject>();
    protected List<Enemy> enemies = new ArrayList<Enemy>();
    
    // collision parameters for placeholder trees
    private int treecolx = -5;
    private int treecoly = 30;
    private int treerad = 20;
    
    private InventoryItem helditem;
    
    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new GameKeyAdapter());
        addMouseListener(new GameMouseAdapter());
        setFocusable(true);
        setBackground(Color.WHITE);
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        // public Player(String name, float x, float y, String spritefile, boolean collision, float col_off_x, float col_off_y, float colradius, float speed) {
        player = new Player("Player", 50, 50, "resources/player.png", true, 0, 0, 10, (float)1.5);
        activemap = new Map(0,0,"resources/map_01_devtest.png");
        
        // public Enemy(String name, float x, float y, String spritefile, boolean collision, float col_off_x, float col_off_y, float colradius,
		//	float speed, int damage, int health, float aggrorange)
        enemies.add(new Enemy("Icky Slime", 100, 100, "resources/enemy_slime_green.png", true, 0, 0, 10, (float)1.5, 2, 10, (float)250));
        
        // make some trees
        int[] trees_x = {125,50,75,261,337,297,330,268,300,474,531,434,320,414,337,336,459,360,362};
        int[] trees_y = {140,140,150,11,12,19,43,48,63,310,342,363,466,476,497,540,540,600,679};
        for(int i=0; i< trees_x.length; i++) {
        	objects.add(new GameObject("Tree", trees_x[i],trees_y[i], "resources/collision_object_shittytree.png", true, treecolx, treecoly, treerad));
        }
        
        timer = new Timer(DELAY, this);
        timer.start();
        inventorysprite = new Sprite(500, 400);
        inventorysprite.loadImage("resources/ui_inventory_crappy.png");
        show_inventory = false;
        
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
    	// draw sprite for map
    	if (activemap.isVisible()) {
            g.drawImage(activemap.getImage(), activemap.getX(), activemap.getY(), this);
        }
    	
    	// draw sprite for player
        if (player.sprite.isVisible()) {
        	player.updateSprite();
            g.drawImage(player.sprite.getImage(), player.sprite.getX(), player.sprite.getY(), this);
            g.setColor(Color.red);
            g.fillRect(player.sprite.getX()+5,player.sprite.getY()-5,(int) Math.round(20*((float) player.health/ (float) player.maxhealth)),5);
        }
        
        // draw sprites for enemies
        for(Enemy enm : enemies) {
        	if(enm.sprite.isVisible()) {
        		enm.updateSprite();
        		g.drawImage(enm.sprite.getImage(), enm.sprite.getX(), enm.sprite.getY(), this);
        		g.setColor(Color.red);
                g.fillRect(enm.sprite.getX()+5,enm.sprite.getY()-5,(int) Math.round(20*((float) enm.health/ (float) enm.maxhealth)),5);
        	}
        }
        
        // draw sprites for objects
        for(GameObject obj : objects) {
        	if(obj.sprite.isVisible()) {
        		obj.updateSprite();
        		g.drawImage(obj.sprite.getImage(), obj.sprite.getX(), obj.sprite.getY(), this);
        	}
        }
        
        // draw sprites for inventory and items
        if(show_inventory) {
        	inventorysprite.setVisible(show_inventory);
        	g.drawImage(inventorysprite.getImage(), inventorysprite.getX(), inventorysprite.getY(), this);
        	
        	for(InventoryItem item : inventoryitems) {
            	if(item.sprite.isVisible()) {
            		item.updateSprite();
            		g.drawImage(item.sprite.getImage(), item.sprite.getX(), item.sprite.getY(), this);
            	}
            }
	        // tooltips
	        Point mouse = MouseInfo.getPointerInfo().getLocation();
	        Point reference = getRootPane().getLocationOnScreen();
	        mouse.x -= reference.x;
	        mouse.y -= reference.y;
	        //System.out.println(mouse.x +", " +mouse.y);
	        for(InventoryItem item : inventoryitems) {
	        	if(item.sprite.isVisible()) {
	        		if((mouse.x > item.sprite.x) && (mouse.y > item.sprite.y)) {
	        			if((mouse.x < item.sprite.x+(37*item.sizeX)) && (mouse.y < item.sprite.y+(37*item.sizeY))) {
	        				g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
	        			    g.setColor(Color.black);
	        				g.drawString(item.name, 520, 470);
	        			}
	        		}
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
        updateGame();
        checkCollisions();

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    // checks input for movement
    private void updateGame() {
    	for(int i=0; i < enemies.size(); i++) {
    		if(!(enemies.get(i).sprite.isVisible())) {
    			System.out.println("removed " +enemies.get(i).name);
    			enemies.remove(i);
    		}
    	}
    	
        if (player.sprite.isVisible()) { 
        	player.move();
        	//System.out.println(player.x);
        }
        
        // for every enemy inside aggrorange..
        for(Enemy enm : enemies) {
			if(enm.health <= 0) {
				enm.sprite.setVisible(false);
			    continue;    		
        	}
        	double dist = Math.sqrt(Math.pow((player.getColX() - enm.getColX()), 2) + Math.pow((player.getColY() - enm.getColY()),2));
        	if (dist < enm.aggrorange) {
        		enm.move( (float) ((player.x - enm.x)/dist),(float) ((player.y - enm.y)/dist) );
        	}
        }
    }

    // calculates collisions
    public void checkCollisions() {
    	
    	// collisions between objects and player
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
    	
    	// collisions between enemies and player
    	for(Enemy enm : enemies) {
        	if(enm.collision = true) {
        		double dist = Math.sqrt(Math.pow((player.getColX() - enm.getColX()), 2) + Math.pow((player.getColY() - enm.getColY()),2));
        		double overlap = dist;
        		overlap -= player.getColRadius();
        		overlap -= enm.getColRadius();
        		if(overlap < 0) {
        			enm.x -= overlap * (enm.getColX() - player.getColX())/dist;
        			enm.y -= overlap * (enm.getColY() - player.getColY())/dist;
        		}
        	}
        }
    	
    	// collisions between enemies and objects
    	for(Enemy enm : enemies) {
        	if(enm.collision = true) {
        		for(GameObject obj : objects) {
                	if(obj.collision = true) {
                		double dist = Math.sqrt(Math.pow((enm.getColX() - obj.getColX()), 2) + Math.pow((enm.getColY() - obj.getColY()),2));
                		double overlap = dist;
                		overlap -= enm.getColRadius();
                		overlap -= obj.getColRadius();
                		if(overlap < 0) {
                			enm.x -= overlap * (enm.getColX() - obj.getColX())/dist;
                			enm.y -= overlap * (enm.getColY() - obj.getColY())/dist;
                		}
                	}
                }
        	}
        }
    	// collisions between enemies and other enemies
    	for(Enemy enm : enemies) {
        	if(enm.collision = true) {
        		for(Enemy enm2 : enemies) {
                	if(enm2.collision = true && enm != enm2) {
                		double dist = Math.sqrt(Math.pow((enm.getColX() - enm2.getColX()), 2) + Math.pow((enm.getColY() - enm2.getColY()),2));
                		double overlap = dist;
                		overlap -= enm.getColRadius();
                		overlap -= enm2.getColRadius();
                		if(overlap < 0) {
                			enm.x -= overlap * (enm.getColX() - enm2.getColX())/dist;
                			enm.y -= overlap * (enm.getColY() - enm2.getColY())/dist;
                		}
                	}
                }
        	}
        }
    }
    
    // key press detection (adapter)
    private class GameKeyAdapter extends KeyAdapter {
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
            	show_inventory = !show_inventory;}
            if (key == KeyEvent.VK_SHIFT) {
            	player.speed = (float) 3.0;}
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
            	player.dy = 0;}
            if (key == KeyEvent.VK_SHIFT) {
            	player.speed = (float) 1.5;}
        }
    }
    
    // mouse adapter
    private class GameMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            if (e.getButton() == MouseEvent.BUTTON1) {
            	for(Enemy enm : enemies) {
            		double dist = Math.sqrt(Math.pow((x - enm.getColX()), 2) + Math.pow((y - enm.getColY()),2));
            		if(dist < enm.colradius) {
            			enm.health -= 2;
            			System.out.println("debug, clicked (" +enm.name +"): " +enm.health +" health, " +x +", " +y);
            			break;
            		}
                }
            	// pick up inventoryitem
            	if(show_inventory && helditem == null) {
        	        Point mouse = MouseInfo.getPointerInfo().getLocation();
        	        Point reference = getRootPane().getLocationOnScreen();
        	        mouse.x -= reference.x;
        	        mouse.y -= reference.y;
        	        //System.out.println(mouse.x +", " +mouse.y);
        	        for(InventoryItem item : inventoryitems) {
        	        	if(item.sprite.isVisible()) {
        	        		if((mouse.x > item.sprite.x) && (mouse.y > item.sprite.y)) {
        	        			if((mouse.x < item.sprite.x+(37*item.sizeX)) && (mouse.y < item.sprite.y+(37*item.sizeY))) {
        	        				System.out.println("moving " +item.name +" from slot (" +item.row +", " +item.col +")");
        	        				helditem = item;
        	        			}
        	        		}
        	        	}
        	        }
            	} else {
            		helditem = null;
            	}
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
            	enemies.add(new Enemy("Icky Slime", x, y, "resources/enemy_slime_green.png", true, 0, 0, 10, (float)1.5, 2, 10, (float)250));
            }
            if (e.getButton() == MouseEvent.BUTTON2) {
            	objects.add(new GameObject("Tree", x-treecolx,y-treecoly, "resources/collision_object_shittytree.png", true, treecolx, treecoly, treerad));
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            if (e.getButton() == MouseEvent.BUTTON1) {
            	if(show_inventory && helditem != null) {
            		x = x -500 -15;
            		y = y -400 -77;
            		int row = (int) Math.round(x/37)+1;
            		int col = (int) Math.round(y/37)+1;
            		boolean slotfree = true;
            		for(InventoryItem item : inventoryitems) {
            			if(item.row == row && item.col == col) {
            				slotfree = false;
            			}
            		}
            		if(slotfree) {
            			System.out.println("placed " +helditem.name +" to slot (" +row +", " +col +")");
            			helditem.row = row;
            			helditem.col = col;
            			helditem = null;
            		}
            	}
            }
        }
    }
}