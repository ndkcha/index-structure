package quadTreeIndex;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

import quadTreeIndex.Point;
import quadTreeIndex.Node;
import quadTreeIndex.quadTreeIndex;

public class mainTest {
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		int quadrantWidth = 1000;
		quadTreeIndex quad = new quadTreeIndex(new Node(500, 500, 500, quadrantWidth));
		long start = System.currentTimeMillis();		
		Scanner scanner = new Scanner(new FileReader("src/LA2v1.txt"));
		while (scanner.hasNext()) {
			String input3D_data = scanner.nextLine().trim();
			double x = Double.parseDouble(input3D_data.substring(1, 9));
			double y = Double.parseDouble(input3D_data.substring(11, 19));
			double z = Double.parseDouble(input3D_data.substring(21, 29));
			//System.out.print("x="+x);
			//System.out.print(", y="+y);
			//System.out.println(", z="+z);
            quad.addPoint(new Point(x,y,z));
        }

        scanner.close();
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed + " milliseconds to load the data!");
        int x_LowBound = 50;
        int x_UpperBound = 100;
        int y_LowBound = 50;
        int y_UpperBound = 100;
        int z_LowBound = 50;
        int z_UpperBound = 100;
        Point searchStart = new Point(x_LowBound, y_LowBound, z_LowBound);
        Point searchEnd = new Point(x_UpperBound, y_UpperBound, z_UpperBound);
        long start1 = System.currentTimeMillis();
        List<Point> foundPoints = quad.search(searchStart, searchEnd);
        long finish1 = System.currentTimeMillis();
        long timeElapsed1 = finish1 - start1;
        for(int i = 0; i < foundPoints.size(); i++) {
            System.out.println(foundPoints.get(i).toString());
        }
        System.out.println("There are "+foundPoints.size()+" points!");
        System.out.println(timeElapsed1 + " milliseconds to find the result!");        
        System.out.println(quad.toString());
        //System.out.println("--==--total size: ");
	}	
}
