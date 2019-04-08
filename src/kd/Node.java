package kd;

import java.io.*;
import java.util.*;

class Node {
    private boolean isX = false, isY = false, isZ = false;
    private double value;
    Node left, right;
    private String fileName;
    private int tupleCount = 0;
    private ArrayList<String> tuples;

    Node(boolean isX, boolean isY, boolean isZ, double value) {
        this.isX = isX;
        this.isY = isY;
        this.isZ = isZ;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    Node(String fileName) {
        this.value = -1;
        this.fileName = fileName;
        this.tupleCount = 0;
    }

    public String getFileName() {
        return fileName;
    }

    int getTupleCount() {
        return tupleCount;
    }

    void writeTuple(String tuple) {
        try {
            this.readTuples();
            this.tuples.add(tuple);
            Collections.sort(this.tuples, Comparator.comparingDouble(((String o1) ->
                Double.parseDouble(Node.getContentFromTuple(o1)[0])))
                .thenComparing((String o2) -> Double.parseDouble(Node.getContentFromTuple(o2)[0])));
            this.tupleCount = this.tuples.size();
            this.writeTuplesBack();
        } catch (IOException e) {
            this.tuples = new ArrayList<>();
            this.tuples.add(tuple);
            this.tupleCount = this.tuples.size();
            this.writeTuplesBack();
        }
    }

    ArrayList<String> getTuples() throws IOException {
        this.readTuples();
        return this.tuples;
    }

    void flushTuples() {
        this.tuples = null;
    }

    boolean deleteFile() {
//        File file = new File(this.fileName);
//        return file.delete();
        return true;
    }

    private void readTuples() throws IOException {
        Scanner scanner = new Scanner(new FileReader(fileName));
        this.tuples = new ArrayList<>();
        while (scanner.hasNext()) {
            this.tuples.add(scanner.nextLine());
        }
        this.tupleCount = this.tuples.size();
        scanner.close();
    }

    private void writeTuplesBack() {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(this.fileName))) {
            for (String tuple : this.tuples) {
                printWriter.println(tuple);
            }
            this.tuples = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isX() {
        return isX;
    }

    boolean isY() {
        return isY;
    }

    boolean isZ() {
        return isZ;
    }

    double getValue() {
        return value;
    }

    static String[] getContentFromTuple(String tuple) {
        String toSplit = tuple.replace("(", "");
        toSplit = toSplit.replace(")", "");
        return toSplit.split(",");
    }

    static String makeFileName(double x, double y, double z) {
        return "data/x".concat(String.valueOf((int) x)).concat("y").concat(String.valueOf((int) y)).concat("z")
            .concat(String.valueOf((int) z)).concat(".txt");
    }
}
