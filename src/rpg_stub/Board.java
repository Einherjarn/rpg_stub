package rpg_stub;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel {
	private Image testimg;

    public Board() {

        initBoard();
    }
    
    private void initBoard() {
        
        loadImage();
        
        int w = testimg.getWidth(this);
        int h =  testimg.getHeight(this);
        setPreferredSize(new Dimension(w, h));        
    }
    
    private void loadImage() {
        
        ImageIcon ii = new ImageIcon("resources/test.png");
        testimg = ii.getImage();        
    }

    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(testimg, 0, 0, null);
    }
}
