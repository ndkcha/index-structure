package R;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Tree {
    private static int SIZE = 50;
    private int n;
    private Region root;

    public Tree(int n, int min, int max) {
        this.n = n;
        this.root = new Region(max, max, max, min, min, min, null);
    }

    public void buildIndex(String inputFileName) throws IOException {
        Scanner scanner = new Scanner(new FileReader(inputFileName));
        String tuple = scanner.nextLine();


    }

    private Region insert(Region root, String tuple, int level) {
        String content[] = Region.getContentFromTuple(tuple);
        int x = Integer.parseInt(content[0].trim());
        int y = Integer.parseInt(content[1].trim());
        int z = Integer.parseInt(content[2].trim());



        return root;
    }
}
