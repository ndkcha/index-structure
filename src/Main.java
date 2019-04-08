import kd.KdTree;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        KdTree tree = new KdTree();
        tree.buildIndex("data/LA2.txt");
    }
}
