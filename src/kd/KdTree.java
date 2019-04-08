package kd;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class KdTree {
    private static final int SIZE = 1000;
    private Node root;
    private boolean firstLeft = true;

    public KdTree() { }

    public void buildIndex(String inputFileName) throws IOException {
        Scanner scanner = new Scanner(new FileReader(inputFileName));
        String tuple = scanner.nextLine();

        this.insertFirstTuple(tuple);

//        for (int i = 0; i < 5; i++) {
//            this.root = this.insert(this.root, scanner.nextLine(), 0);
//        }

        int i = 0;
        long startTime = System.currentTimeMillis();
        while (scanner.hasNext()) {
            this.root = this.insert(this.root, scanner.nextLine(), 0);
            i++;
            if (i % 1000 == 0)
                System.out.println(i);
            System.gc();
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private void insertFirstTuple(String tuple) {
        String content[] = Node.getContentFromTuple(tuple);
        double x = Math.floor(Double.parseDouble(content[0].trim()));
        double y = Math.floor(Double.parseDouble(content[1].trim()));
        double z = Math.floor(Double.parseDouble(content[2].trim()));
        String fileName = Node.makeFileName(x, y, z);

        this.root = new Node(true, false, false, x);
        this.root.right = new Node(false, true, false, y);
        this.root.right.right = new Node(false, false, true, z);
        this.root.right.right.right  = new Node(fileName);
        this.root.right.right.right.writeTuple(tuple);
    }

    private Node insert(Node root, String tuple, int level) throws IOException {
        String content[] = Node.getContentFromTuple(tuple);
        double x = Double.parseDouble(content[0].trim());
        double y = Double.parseDouble(content[1].trim());
        double z = Double.parseDouble(content[2].trim());
        String fileName = Node.makeFileName(x, y, z);

        if (root.getValue() == -1) {
            root.writeTuple(tuple);
            return root;
        }

        if (root.isX()) {
            if (x < root.getValue()) {
                if (root.left == null) {
                    if (level < 2) {
                        root.left = new Node(false, true, false, Math.floor(y));
                        root.left.right = new Node(false, false, true, Math.floor(z));
                        root.left.right.right = new Node(fileName);
                        root.left.right.right.writeTuple(tuple);
                        return root;
                    }
                    root.left = new Node(fileName);
                    root.left.writeTuple(tuple);
                    return root;
                }
                if (root.left.getTupleCount() >= SIZE) {
                    ArrayList<String> intermediate = root.left.getTuples();
                    intermediate.add(tuple);
                    Collections.sort(intermediate, Comparator.comparingDouble(((String o1) ->
                        Double.parseDouble(Node.getContentFromTuple(o1)[0])))
                        .thenComparing((String o2) -> Double.parseDouble(Node.getContentFromTuple(o2)[0])));
                    int middleIdx = intermediate.size() / 2;
                    root.left.deleteFile();
                    root.left = new Node(false, true, false,
                        Double.parseDouble(Node.getContentFromTuple(intermediate.get(middleIdx))[1]));
                    for (String t : intermediate) {
                        root.left = this.insert(root.left, t, ++level);
                    }
                    return root;
                }
                root.left = insert(root.left, tuple, ++level);
                return root;
            } else {
                if (root.right == null) {
                    root.right = new Node(fileName);
                    root.right.writeTuple(tuple);
                    return root;
                }
                if (root.right.getTupleCount() >= SIZE) {
                    ArrayList<String> intermediate = root.right.getTuples();
                    intermediate.add(tuple);
                    Collections.sort(intermediate, Comparator.comparingDouble(((String o1) ->
                        Double.parseDouble(Node.getContentFromTuple(o1)[0])))
                        .thenComparing((String o2) -> Double.parseDouble(Node.getContentFromTuple(o2)[0])));
                    int middleIdx = intermediate.size() / 2;
                    root.right.deleteFile();
                    root.right = new Node(false, true, false,
                        Double.parseDouble(Node.getContentFromTuple(intermediate.get(middleIdx))[1]));
                    for (String t : intermediate) {
                        root.right = this.insert(root.right, t, ++level);
                    }
                    return root;
                }
                root.right = insert(root.right, tuple, ++level);
                return root;
            }
        }
        if (root.isY()) {
            if (y < root.getValue()) {
                if (root.left == null) {
                    if (level < 2) {
                        root.left = new Node(false, false, true, Math.floor(z));
                        root.left.right = new Node(fileName);
                        root.left.right.writeTuple(tuple);
                        return root;
                    }
                    root.left = new Node(fileName);
                    root.left.writeTuple(tuple);
                    return root;
                }
                if (root.left.getTupleCount() >= SIZE) {
                    ArrayList<String> intermediate = root.left.getTuples();
                    intermediate.add(tuple);
                    Collections.sort(intermediate, Comparator.comparingDouble(((String o1) ->
                        Double.parseDouble(Node.getContentFromTuple(o1)[0])))
                        .thenComparing((String o2) -> Double.parseDouble(Node.getContentFromTuple(o2)[0])));
                    int middleIdx = intermediate.size() / 2;
                    root.left.deleteFile();
                    root.left = new Node(false, false, true,
                        Double.parseDouble(Node.getContentFromTuple(intermediate.get(middleIdx))[2]));
                    for (String t : intermediate) {
                        root.left = this.insert(root.left, t, ++level);
                    }
                    return root;
                }
                root.left = insert(root.left, tuple, ++level);
                return root;
            } else {
                if (root.right == null) {
                    root.right = new Node(fileName);
                    root.right.writeTuple(tuple);
                    return root;
                }
                if (root.right.getTupleCount() >= SIZE) {
                    ArrayList<String> intermediate = root.right.getTuples();
                    intermediate.add(tuple);
                    Collections.sort(intermediate, Comparator.comparingDouble(((String o1) ->
                        Double.parseDouble(Node.getContentFromTuple(o1)[0])))
                        .thenComparing((String o2) -> Double.parseDouble(Node.getContentFromTuple(o2)[0])));
                    int middleIdx = intermediate.size() / 2;
                    root.right.deleteFile();
                    root.right = new Node(false, false, true,
                        Double.parseDouble(Node.getContentFromTuple(intermediate.get(middleIdx))[2]));
                    for (String t : intermediate) {
                        root.right = this.insert(root.right, t, ++level);
                    }
                    return root;
                }
                root.right = insert(root.right, tuple, ++level);
                return root;
            }
        }
        if (root.isZ()) {
            if (z < root.getValue()) {
                if (root.left == null) {
                    root.left = new Node(fileName);
                    root.left.writeTuple(tuple);
                    return root;
                }
                if (root.left.getTupleCount() >= SIZE) {
                    ArrayList<String> intermediate = root.left.getTuples();
                    intermediate.add(tuple);
                    Collections.sort(intermediate, Comparator.comparingDouble(((String o1) ->
                        Double.parseDouble(Node.getContentFromTuple(o1)[0])))
                        .thenComparing((String o2) -> Double.parseDouble(Node.getContentFromTuple(o2)[0])));
                    int middleIdx = intermediate.size() / 2;
                    root.left.deleteFile();
                    root.left = new Node(true, false, false,
                        Double.parseDouble(Node.getContentFromTuple(intermediate.get(middleIdx))[0]));
                    for (String t : intermediate) {
                        root.left = this.insert(root.left, t, ++level);
                    }
                    return root;
                }
                root.left = insert(root.left, tuple, ++level);
                return root;
            } else {
                if (root.right == null) {
                    root.right = new Node(fileName);
                    root.right.writeTuple(tuple);
                    return root;
                }
                if (root.right.getTupleCount() >= SIZE) {
                    ArrayList<String> intermediate = root.right.getTuples();
                    intermediate.add(tuple);
                    Collections.sort(intermediate, Comparator.comparingDouble(((String o1) ->
                        Double.parseDouble(Node.getContentFromTuple(o1)[0])))
                        .thenComparing((String o2) -> Double.parseDouble(Node.getContentFromTuple(o2)[0])));
                    int middleIdx = intermediate.size() / 2;
                    root.right.deleteFile();
                    root.right = new Node(true, false, false,
                        Double.parseDouble(Node.getContentFromTuple(intermediate.get(middleIdx))[0]));
                    for (String t : intermediate) {
                        root.right = this.insert(root.right, t, ++level);
                    }
                    return root;
                }
                root.right = insert(root.right, tuple, ++level);
                return root;
            }
        }

        return root;
    }
}
