import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Ball extends JComponent{

	public int xpos, ypos, xvel, yvel, radius;
	
	public Ball(int xsize, int ysize) {
		xvel = 1;
		yvel = 1;
		radius = 50;

		Random rand = new Random();
		xpos = rand.nextInt(xsize);
		xpos = rand.nextInt(ysize);
	}
	
	public void update() {
		xpos += xvel;
		ypos += yvel;
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval(xpos, ypos, radius, radius);
	}

}
