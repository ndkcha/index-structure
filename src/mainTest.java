import java.io.*;
import java.util.List;
import java.util.Scanner;

public class mainTest {

    public static void main(String[] args) throws FileNotFoundException {
        // TODO Auto-generated method stub
        int quadrantWidth = 1000;
        quadTreeIndex quad = new quadTreeIndex(new Node(500, 500, 500, quadrantWidth));
        long start = System.currentTimeMillis();
        Scanner scanner = new Scanner(new FileReader("bin/LA2.txt"));
        int k = 0;
        while (scanner.hasNext()) {
            if (k % 100000 == 0) {
                System.out.println(k);
                System.gc();
            }
            String input3D_data = scanner.nextLine().trim();
            double x = Double.parseDouble(input3D_data.substring(1, 9));
            double y = Double.parseDouble(input3D_data.substring(11, 19));
            double z = Double.parseDouble(input3D_data.substring(21, 29));
            //System.out.print("x="+x);
            //System.out.print(", y="+y);
            //System.out.println(", z="+z);
            quad.addPoint(new Point(x,y,z));
            k++;
        }

        scanner.close();
        scanner = new Scanner(System.in);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed + " milliseconds to load the data!");

        System.gc();

        long indexSize = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 10000000;
        System.out.println(indexSize + " MB");

        while (true) {
            System.out.println("Lower bound for the range: e.g. 50 50 50 (with spaces in between, -1 to exit)");
            String lower = scanner.nextLine().trim();
            if (lower.equalsIgnoreCase("-1"))
                break;
            String lowerBounds[] = lower.split(" ");
            System.out.println("Upper bound for the range: (same as above)");
            String upper = scanner.nextLine().trim();
            if (upper.equalsIgnoreCase("-1"))
                break;
            String upperBounds[] = upper.split(" ");
            int x_LowBound = Integer.parseInt(lowerBounds[0]);
            int x_UpperBound = Integer.parseInt(upperBounds[0]);
            int y_LowBound = Integer.parseInt(lowerBounds[1]);
            int y_UpperBound = Integer.parseInt(upperBounds[1]);
            int z_LowBound = Integer.parseInt(lowerBounds[2]);
            int z_UpperBound = Integer.parseInt(upperBounds[2]);
            Point searchStart = new Point(x_LowBound, y_LowBound, z_LowBound);
            Point searchEnd = new Point(x_UpperBound, y_UpperBound, z_UpperBound);
            long start1 = System.currentTimeMillis();
            List<Point> foundPoints = quad.search(searchStart, searchEnd);
            long finish1 = System.currentTimeMillis();
            long timeElapsed1 = finish1 - start1;
            System.out.println("There are " + foundPoints.size() + " points in the given range!");
            System.out.println(timeElapsed1 + " milliseconds to find the range result!");

            System.out.println("Store the results in file?: (y/n)");
            String yesNo = scanner.nextLine();

            if (yesNo.equalsIgnoreCase("y")) {
                try {
                    System.out.println("File name: ");
                    String name = scanner.nextLine().trim();
                    PrintWriter printWriter = new PrintWriter(new FileWriter("src/" + name + ".txt"));
                    for (int i = 0; i < foundPoints.size(); i++) {
                        printWriter.println(foundPoints.get(i).toString());
                    }
                    printWriter.close();
                    System.out.println("Done! it's stored in data/" + name + ".txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        while (true) {
            System.out.println("Enter the point to search the neighbours for?: e.g. 50 50 50 (with spaces in between, " +
                "-1 to exit)");
            String pointString = scanner.nextLine().trim();
            if (pointString.equalsIgnoreCase("-1"))
                break;
            String pointContent[] = pointString.split(" ");
            Point givenPoint = new Point(Integer.parseInt(pointContent[0]), Integer.parseInt(pointContent[1]),
                Integer.parseInt(pointContent[2]));
            long start2 = System.currentTimeMillis();
            //Point NN_Point = quad.nearestNeighbor(givenPoint);
            List<Point> NN_foundPoints = quad.nearestNeighbor(givenPoint);
            long finish2 = System.currentTimeMillis();
            long timeElapsed2 = finish2 - start2;
            //System.out.println("The NN point to the given " + givenPoint + " is: " + NN_Point);
            System.out.println("The NN point to the given " + givenPoint + " is: ");
            for (int i = 0; i < NN_foundPoints.size(); i++) {
            	System.out.println(NN_foundPoints.get(i).toString());
            }
            System.out.println(timeElapsed2 + " milliseconds to find the NN point!");
        }
    }
}
