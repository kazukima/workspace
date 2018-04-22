import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
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
public class Snek extends JPanel{//Extends: gives you already-working methods you can use
	public JFrame frame;
	public Random rand = new Random();
	public boolean running = true;//allows infinite while-loop in run()
	public boolean north = false;
	public boolean east = false;
	public boolean south = false;
	public boolean west = false;
	public boolean paused = false;
	
	public int fps = 8;//speed of game
	public long startTime;
	public int tempFPS = 0;//not in use yet
	public int snakeLen = 1;
	public int moves = 0;//number of total blocks/cells traveled
	public int gradient = 10;
	public int r = 255-gradient;
	public int g = 0;
	public int b = 0;
	
	public int snakeDim = 50;//size of snake and boxes
	public int numBox = 10;//number of boxes by boxes
	public int limit = (snakeDim * numBox)-(2*snakeDim);//find outer boundary of grid
	public int startX = rand.nextInt(numBox-2)*snakeDim;//random x starting location
	public int startY = rand.nextInt(numBox-2)*snakeDim;//random y starting location
	public int xDir = startX;//start moving from random x starting location
	public int yDir = startY;//start moving from random y starting location
	public int targetX = rand.nextInt(numBox-2)*snakeDim + (snakeDim/2);//random x location of target
	public int targetY = rand.nextInt(numBox-2)*snakeDim + (snakeDim/2);//random y location of target
	public ArrayList<ArrayList<Integer>> cells = new ArrayList<ArrayList<Integer>>(); //store positions of each cell
	public ArrayList<Integer> pos = new ArrayList<Integer>();//store x and y positions
	//public Grid grid;//"importing" another class
	
	public static void main(String args[]) {
		Snek snek = new Snek();
		snek.run();
	}
	
	public void run() {
		frame = new JFrame("Snek");//Creates a frame with the header in the parenthesis
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//When you close the window it exits program
		frame.setSize(limit+(limit/2), limit+(limit));
		frame.setVisible(true);
		this.setVisible(true);
		frame.add(this);
		pos.add(xDir);//setting initial x
		pos.add(yDir);//setting initial y
		cells.add(pos);//adding position to the first cell
		frame.addKeyListener(new keyListenerClass());		
		//grid = new Grid();//setting parameters of ball
		//this.add(grid);//adding ball to the JPanel		
		
		while(running) {
			startTime = System.currentTimeMillis();
			if (!paused) {
				if (north) {
					if (yDir < snakeDim) { yDir = limit + snakeDim; } // hit top wall
					else { yDir -= snakeDim; }//move up
				} else if (south) {
					if (yDir > limit) {	yDir = 0; } //hit bottom wall
					else { yDir += snakeDim; }//move down
				} 
				if (east) {
					if (xDir > limit) {	xDir = 0; } //hit right wall
					else { xDir += snakeDim; }//move right
				} else if (west) {
					if (xDir < snakeDim) {	xDir = limit + snakeDim; } //hit left wall
					else { xDir -= snakeDim; }//move left
				}
				moves++;
				updateCells();
				System.out.println(xDir + "," + yDir);
				//System.out.println(cells);
				//if stepped onto black target
				if ( (xDir + (snakeDim/2) == targetX) && (yDir + (snakeDim/2) == targetY)) {
					targetX = rand.nextInt(numBox-2)*snakeDim + (snakeDim/2);//setting new random x target position
					targetY = rand.nextInt(numBox-2)*snakeDim + (snakeDim/2);//setting new random y target position
					snakeLen++;
					for (int i = moves-1; i > (moves-snakeLen-1); i--) {
						System.out.println(((cells.get(i)).get(0)+(snakeDim/2)) + "," + ((cells.get(i)).get(1)+(snakeDim/2)) + ": " + targetX + "," + targetY);
						if ( (targetX == (cells.get(i)).get(0)+(snakeDim/2)) && (targetY == (cells.get(i)).get(1)+(snakeDim/2)) ) {
							//System.out.println("Target-Spawn Intercepts" + targetX + "," + targetY);
							targetX = rand.nextInt(numBox-2)*snakeDim + (snakeDim/2);//setting random x target position
							targetY = rand.nextInt(numBox-2)*snakeDim + (snakeDim/2);//setting random y target position
							i = moves-1;//checking new position with cells
							System.out.println("New Position " + targetX + "," + targetY);
						}
					}
					System.out.println();
				}
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
	
	public void updateCells() {
		ArrayList<Integer> pos2 = new ArrayList<Integer>();
		pos2.add(xDir);//adding new x position
		pos2.add(yDir);//adding new y position
		cells.add(pos2);//adding new cell location
	}
	
	public void paintComponent(Graphics t) {
		Color newColor = new Color(r, g, b);//initially creating color red
		t.setColor(newColor);//setting first length of snake as red
		t.fillRect((snakeDim/2) + xDir, (snakeDim/2) + yDir, snakeDim, snakeDim);
		for (int i = moves-1; i > (moves-snakeLen); i--) {//drawing each cell a different color
			if ( (r >= gradient) && (g <= gradient) && (b <= 255-gradient) ) { 
				b += gradient;} //color from red-pink
			else if ((r >= gradient) && (g <= 255-gradient) && (b >= gradient)) { 
				r -= gradient;} //color from pink-blue
			else if ((r <= 255-gradient) && (g <= 255-gradient) && (b >= gradient)) {
				g += gradient;} //color from blue-skyblue
			else if ((r <= 255-gradient) && (g >= gradient) && (b >= gradient)) { 
				b -= gradient;} //color from skyblue to green 
			else if ((r <= 255-gradient) && (g >= gradient) && (b <= 255-gradient)) { 
				r += gradient;} //color from green-yellow
			else if ((r >= gradient) && (g >= gradient) && (b <= 255-gradient)) {
				g -= gradient;} //color from yellow-red
			newColor = new Color(r, g, b);//combining r,g,b to create a new color
			t.setColor(newColor);//set new color for snake cell
			t.fillRect((snakeDim/2) + (cells.get(i)).get(0), (snakeDim/2) + (cells.get(i)).get(1), snakeDim, snakeDim);
			if ( (xDir == (cells.get(i)).get(0)) && (yDir == (cells.get(i)).get(1)) ) {
				//System.exit(0);
				System.out.println("SelfHit @ (" + xDir + "," + yDir + ") \n  Score:" + snakeLen);
				System.exit(0);
			}
		}
		//grid.paintComponent(g);
		t.setColor(Color.black);//Setting the grid
		for (int i = 0; i < numBox; i++) {
			for (int j = 0; j < numBox; j++) {
				t.drawRect((i*snakeDim)+(snakeDim/2),(j*snakeDim)+(snakeDim/2), snakeDim, snakeDim);
			}
		}		
		t.fillRect(targetX, targetY, snakeDim, snakeDim);//Making the target
		//System.out.println(xDir+","+yDir+ " want " + targetX+","+targetY);
	}
	
	public class keyListenerClass implements KeyListener{ //Gives you blank methods you HAVE to implement
		//@Override
		private long lastPressProcessed = 0;
		public void keyPressed(KeyEvent k) {
			if(System.currentTimeMillis() - lastPressProcessed > 100) {
	            //Do your work here...
	            lastPressProcessed = System.currentTimeMillis();
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
		        	fps++;
		        	tempFPS = fps;
		        	break;
		        case KeyEvent.VK_MINUS://decrease speed
		        	if (fps <= 1) { break; }
		        case KeyEvent.VK_P://pause the snake's movement
		        	paused = !paused;
		        	break;
	            }	
	        }   
		}
		@Override
		public void keyReleased(KeyEvent arg0) {}
		@Override
		public void keyTyped(KeyEvent arg0) {}	
	}
}
