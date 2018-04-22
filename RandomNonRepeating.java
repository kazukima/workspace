import java.util.ArrayList;
import java.util.Random;

public class RandomNonRepeating {
	public Random rand = new Random();
	public int totalPick = 14;
	public int sorted = 0;
	public int person = 0;
	public ArrayList<Integer> numCheck = new ArrayList<Integer>();
	public ArrayList<String> order = new ArrayList<String>();
	String names[] = new String[] {"Sara", "Leonardo", "Josh", "Karajo", "Alisha", "Edwin", "Thomas", "Brandon", "Dylan", "Jeff", "Laura", "Ben", "Ryan", "Katie"};
	
	public RandomNonRepeating() {
		while (sorted < 14) {
			person = rand.nextInt(totalPick)+1;
			for (int i = 0; i < numCheck.size(); i++) {
				if ( (person == numCheck.get(i)) || (person == numCheck.get(0)) ){
					//System.out.println("Duplicated " + person);
					person = rand.nextInt(totalPick)+1;
					i = 0;
				}
			}
			sorted ++;
			numCheck.add(person);
			order.add(names[person-1]);
			//System.out.println(order + " " + sorted);
		}
		System.out.println("Random numbers generated: \n" + numCheck);
		System.out.println("Names corresponding nums: \n" + order);
	}
	
	public static void main(String args[]) {
		RandomNonRepeating randNon= new RandomNonRepeating();
	}

}
