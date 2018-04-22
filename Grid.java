import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
@SuppressWarnings("serial")
public class Grid extends JComponent{

	public int dim = 50;
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				g.drawRect(i*dim+25, j*dim+25, dim, dim);
			}
		}
	}

}
