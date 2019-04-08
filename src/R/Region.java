package R;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Region {
    private int maxX, maxY, maxZ;
    private int minX, minY, minZ;
    private String fileName;
    private HashMap<Integer, Region> regions;
    private ArrayList<String> tuples;

    int noOfRegions() {
        return this.regions.size();
    }

    void addRegion(Region region) {
        this.regions.put(this.regions.size(), region);
    }

    void updateRegion(int index, Region region) {
        this.regions.put(index, region);
    }

    Region getRegion(int index) {
        return this.regions.get(index);
    }

    int getRegionInRange(int x, int y, int z) {
        for (Map.Entry<Integer, Region> entry : this.regions.entrySet()) {
            int index = entry.getKey();
            Region region = entry.getValue();
            if (region.isInRange(x, y, z))
                return index;
        }

        return -1;
    }

    boolean isInRange(int x, int y, int z) {
        return this.inXRange(x) && this.inYRange(y) && this.inZRange(z);
    }

    private boolean inXRange(int x) {
        return x <= this.maxX && x >= this.minX;
    }

    private boolean inYRange(int y) {
        return y <= this.maxY && y >= this.minY;
    }

    private boolean inZRange(int z) {
        return z <= this.maxZ && z >= this.minZ;
    }

    Region(int maxX, int maxY, int maxZ, int minX, int minY, int minZ, String fileName) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        if (fileName == null)
            this.regions = new HashMap<>();
        else
            this.fileName = fileName;
    }

    void stretchRegion(int x, int y, int z, boolean isStretchMax) {
        if (isStretchMax) {
            this.maxX = x;
            this.maxY = y;
            this.maxZ = z;
        } else {
            this.minX = x;
            this.minY = y;
            this.minZ = z;
        }
    }

    void loadRegionInMemory() throws IOException {
        Scanner scanner = new Scanner(new FileReader(this.fileName));
        this.tuples = new ArrayList<>();

        while (scanner.hasNext()) {
            this.tuples.add(scanner.nextLine());
        }
    }

    ArrayList<String> getTuples() {
        return this.tuples;
    }

    static String[] getContentFromTuple(String tuple) {
        String contentToSplit = tuple.replace("(", "");
        contentToSplit = contentToSplit.replace(")", "");
        return contentToSplit.split(",");
    }
}
