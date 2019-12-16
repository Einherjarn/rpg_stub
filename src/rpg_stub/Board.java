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
    
    private Sprite inventorysprite;

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
        
        objects.add(new GameObject("Tree01", 125, 140, "resources/collision_object_shittytree.png", true, 0, 20, 20));
        objects.add(new GameObject("Tree03", 50, 140, "resources/collision_object_shittytree.png", true, 0, 20, 20));
        objects.add(new GameObject("Tree02", 75, 150, "resources/collision_object_shittytree.png", true, 0, 20, 20));

        timer = new Timer(DELAY, this);
        timer.start();
        inventorysprite = new Sprite(800, 700);
        inventorysprite.loadImage("resources/ui_inventory_crappy.png");
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
        if(inventorysprite.isVisible()) {
        	g.drawImage(inventorysprite.getImage(), inventorysprite.getX(), inventorysprite.getY(), this);
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
    
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }
}