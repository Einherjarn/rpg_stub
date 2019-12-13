package rpg_stub;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
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

    protected List<Object> objects = new ArrayList<Object>();
    
    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.WHITE);
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        player = new Player(50, 50, "resources/player.png", 10);
        activemap = new Map(0,0,"resources/map_01_devtest.png");
        
        objects.add(new Object(175,175,"resources/collision_object_shittytree.png", 20));

        timer = new Timer(DELAY, this);
        timer.start();
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

    private void drawObjects(Graphics g) {
    	
    	if (activemap.isVisible()) {
            g.drawImage(activemap.getImage(), activemap.getX(), activemap.getY(), this);
        }
    	
        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }
        for(Object obj : objects) {
        	if(obj.isVisible()) {
        		//System.out.print("debug");
        		g.drawImage(obj.getImage(), obj.getX(), obj.getY(), this);
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

    private void updatePlayer() {

        if (player.isVisible()) {
            
        	player.move();
        }
    }


    public void checkCollisions() {
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