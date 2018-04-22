import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;//The actual window the canvas is on
import javax.swing.JPanel;//Used to organize smaller components as a group

/**
 * @author Karajo Kafka and Adam Loeffler
 * @Date: August 18, 2017
 * @Project: Snake
 */

@SuppressWarnings("serial")
public class SnekBeta extends JPanel{//Extends: gives you already-working methods you can use
	public JFrame frame;
	public Random rand = new Random();
	public boolean running = true;//allows infinite while-loop in run()
	public boolean north = false;
	public boolean east = false;
	public boolean south = false;
	public boolean west = false;
	
	public int fps = 4;//speed of game
	public long startTime;
	public int tempFPS = 0;//not in use yet
	public int blockLen = 1;
	
	public int snakeDim = 50;//size of snake and boxes
	public int numBox = 20;//number of boxes by boxes
	public int limit = (snakeDim * numBox)-(2*snakeDim);//find outer boundary of grid
	public int startX = rand.nextInt(numBox-2)*snakeDim;//random x starting location
	public int startY = rand.nextInt(numBox-2)*snakeDim;//random y starting location
	public int xDir = startX;//start moving from random x starting location
	public int yDir = startY;//start moving from random y starting location
	public int targetX = rand.nextInt(numBox-2)*snakeDim + (snakeDim/2);//random x location of target
	public int targetY = rand.nextInt(numBox-2)*snakeDim + (snakeDim/2);//random y location of target
	public ArrayList<Integer> cells = new ArrayList<Integer>(); 
	//public Grid grid;//"importing" another class
	
	public static void main(String args[]) {
		Snek snek = new Snek();
		snek.run();
	}
	
	public void run() {
		frame = new JFrame("Snek");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//When you close the window it exits program
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    //frame.setBounds(0,0,screenSize.width, screenSize.height);
		frame.setSize(limit+(limit/4), limit+(limit/3));
		frame.setVisible(true);
		this.setVisible(true);
		frame.add(this);
		cells.add(0);
		frame.addKeyListener(new keyListenerClass());
		
		//grid = new Grid();//setting parameters of ball
		//this.add(grid);//adding ball to the JPanel		
		
		while(running) {
			startTime = System.currentTimeMillis();
			if (north) { 
				if (yDir < snakeDim) { yDir = limit + snakeDim; } // hit top wall
				else { yDir -= snakeDim; }//move up
				cells.set(0, 12);
			} else if (south) { 
				if (yDir > limit) {	yDir = 0; } //hit bottom wall
				else { yDir += snakeDim; }//move down
				cells.set(0, 6);
			} 
			if (east) {	
				if (xDir > limit) {	xDir = 0; } //hit right wall
				else { xDir += snakeDim; }//move right
				cells.set(0, 3);
			} else if (west) {
				if (xDir < snakeDim) {	xDir = limit + snakeDim; } //hit left wall
				else { xDir -= snakeDim; }//move left
				cells.set(0, 9);
			}
			System.out.println(cells);
			//if stepped onto black target
			if ( (xDir + (snakeDim/2) == targetX) && (yDir + (snakeDim/2) == targetY)) {
				targetX = rand.nextInt(numBox-2)*snakeDim + (snakeDim/2);
				targetY = rand.nextInt(numBox-2)*snakeDim + (snakeDim/2);
				cells.add(cells.get(blockLen-1));
				blockLen++;
			}
			//b.update(); //update the position of ball
			frame.repaint();
			long diff = System.currentTimeMillis() - startTime;
			if(diff < 1500/fps) {//how long to wait between frames
				try {
					Thread.sleep((1500/fps)-diff);
				}catch(Exception e){}
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.red);//Draw new position of snake
		g.fillRect((snakeDim/2) + xDir, (snakeDim/2) + yDir, snakeDim, snakeDim);
		for (int i = blockLen-1; i > 0; i--) {
			for (int j = blockLen-1; j > 0; j--)
			//System.out.println(cells.get(i));
			cells.set(i, cells.get(i-1));
			System.out.println(cells.get(i-1) + " " + cells.get(i));
			if (cells.get(i) == 12){//facing north
				g.fillRect((snakeDim/2)+xDir, (snakeDim/2)+yDir+(snakeDim*(i)), snakeDim, snakeDim);
			} else if (cells.get(i) == 3) {//facing east
				g.fillRect((snakeDim/2)+xDir-(snakeDim*(i)), (snakeDim/2)+yDir, snakeDim, snakeDim);
			} else if (cells.get(i) == 6) {//facing south
				g.fillRect((snakeDim/2)+xDir, (snakeDim/2)+yDir-(snakeDim*(i)), snakeDim, snakeDim);
			} else if (cells.get(i) == 9) {//facing west
				g.fillRect((snakeDim/2)+xDir+(snakeDim*(i)), (snakeDim/2)+yDir, snakeDim, snakeDim);
			}
		}
		//grid.paintComponent(g);
		g.setColor(Color.black);//Setting the grid
		for (int i = 0; i < numBox; i++) {
			for (int j = 0; j < numBox; j++) {
				g.drawRect((i*snakeDim)+(snakeDim/2),(j*snakeDim)+(snakeDim/2), snakeDim, snakeDim);
			}
		}
		g.fillRect(targetX, targetY, snakeDim, snakeDim);//Making the target
		System.out.println(xDir+","+yDir+ " want " + targetX+","+targetY);
	}
	
	public class keyListenerClass implements KeyListener{ //Gives you blank methods you HAVE to implement
		@Override
		public void keyPressed(KeyEvent k) {
			switch (k.getKeyCode()){
			    case KeyEvent.VK_UP://turn north
			    	if (south) { break; } //can't turn 180
		        	north = true;
		        	east = south = west = false;
		        	break;
		        case KeyEvent.VK_RIGHT: //turn east
		        	if (west) { break; } //can't turn 180
		            east = true;
		            north = south = west = false;
		            break;
		        case KeyEvent.VK_DOWN: //turn south
		        	if (north) { break; } //can't turn 180
		        	south = true;
		        	north = east = west = false;
		        	break;
		        case KeyEvent.VK_LEFT: //turn west
		        	if (east) { break; } //can't turn 180
		        	west = true;
		        	north = east = south = false;
		        	break;
		        case KeyEvent.VK_EQUALS://increase speed
		        	System.out.println(fps);
		        	fps++;
		        	tempFPS = fps;
		        	break;
		        case KeyEvent.VK_MINUS://decrease speed
		        	System.out.println(fps);
		        	if (fps <= 1) { break; }
		        	try { fps--; }//Need to fix<<<<<<<<<<<
		        	catch (ArithmeticException ae) {}
		        	tempFPS = fps;
		        	break;
		    }	
		}
		@Override
		public void keyReleased(KeyEvent arg0) {}
		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}
}

/*Eventually:
 * Pause button
 * Change color of snake
 * 
 */
