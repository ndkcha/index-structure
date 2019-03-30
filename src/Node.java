package quadTreeIndex;

import java.util.Arrays;
import java.util.List;

public class Node {
    public Node[] children = new Node[8];
    public int levelNum = 0;
    public int innerIndex = 0;
    public int predecessorIndex = 0;
    // Center of the quadrant associated with this searchSpace
    private Point center = new Point(0, 0, 0);
    private float quadrantWidth;
    // Precalculated halfWidth
    private float halfWidth;
    //public String nodeName = "Level"+levelNum+"_"+center.getX()+"_"+center.getY()+"_"+center.getZ();
    public String nodeName = "";
    public String[] childrenNodeName = new String[8];

    public Node(double centerX, double centerY, double centerZ, float quadrantWidth) {
        this(new Point(centerX, centerY, centerZ), quadrantWidth);
    }

    public Node(Point center, float quadrantWidth) {
        this.center = center;
        //this.levelNum = (int) Math.ceil(1000/quadrantWidth)-1;
        this.nodeName = "Key_"+this.center.getX()+"_"+this.center.getY()+"_"+this.center.getZ();
        setQuadrantWidth(quadrantWidth);
    }

    public float getQuadrantWidth() {
        return quadrantWidth;
    }

    public void setQuadrantWidth(float quadrantWidth) {
        this.quadrantWidth = quadrantWidth;
        this.halfWidth = quadrantWidth / 2;
    }

    public Node[] getChildren() {

        return children;
    }

    public void setChildren(Node[] children) {
        this.children = children;
    }

    private Point point;

    public boolean isLeaf() {
        return leaf;
    }

    private boolean leaf = true;

    @Override
    public String toString() {
    	String nodeInfor="";
    	if(leaf){
		nodeInfor = "Node{"+nodeName+ 
				"\n center=" + center +
				"\n children=" + (children == null ? null : Arrays.asList(children)) +
				"\n point=" + point +
				"\n leaf=" + leaf +
				"\n}\n";
    	}else{
    		nodeInfor = "Node{"+nodeName+"\n children="+(children == null ? null : Arrays.asList(children));
    	}    	
    	return nodeInfor;    	
    }

    protected int calculateQuadrantIndex(Point point, Point compareWith) {
        int result = 0;
        if (point == null) {
            throw new IllegalStateException("Point cannot be null");
        }
        if (compareWith == null) {
            throw new IllegalStateException("Point to be compared cannot be null");
        }
        if (point.getX() > compareWith.getX()) result += 1;
        if (point.getY() > compareWith.getY()) result += 2;
        if (point.getZ() > compareWith.getZ()) result += 4;
        return result;
    }

    public void associateObject(Point cube) {
        this.point = cube;
    }

    public Point getCenter() {
        return center;
    }

    public Point getPoint() {
        return point;
    }
    
    public void search(List<Point> result, Node node, Point leftBottomBack, Point rightTopFront) {
    	
    	if (node.isLeaf()) {
            Point quadPoint = node.getPoint();
            // Search region intersects with searchSpace, but point may not be in it
            // We will add the point only if it is within search region
            if (
                    leftBottomBack.getX() <=  quadPoint.getX() && quadPoint.getX() <= rightTopFront.getX() &&
                    leftBottomBack.getY() <=  quadPoint.getY() && quadPoint.getY() <= rightTopFront.getY() &&
                    leftBottomBack.getZ() <=  quadPoint.getZ() && quadPoint.getZ() <= rightTopFront.getZ()
                    ) {
                result.add(quadPoint);
                
            }
            // It is necessary to traverse deeper into the octal tree
        } else {
            for (int quadIndex = 0; quadIndex < 8; quadIndex++) {
                Node quadrant = node.getChild(quadIndex);
                if ((quadrant!= null) && quadrant.intersects(leftBottomBack, rightTopFront)) {
                    search(result, quadrant, leftBottomBack, rightTopFront);
                }
            }
        }

    }

    public boolean intersects(Point leftBottomBack, Point rightTopFront) {
        return !(
                center.getX() - halfWidth > rightTopFront.getX() ||
                center.getX() + halfWidth < leftBottomBack.getX() ||
                center.getY() - halfWidth > rightTopFront.getY() ||
                center.getY() + halfWidth < leftBottomBack.getY() ||
                center.getZ() - halfWidth > rightTopFront.getZ() ||
                center.getZ() + halfWidth < leftBottomBack.getZ()
        );
    }

    Node getChild(int quadrantIndex) {
        return children[quadrantIndex];
    }

    public void setChild(int quadIndex, Node node) {
        this.leaf = false;
        this.children[quadIndex] = node;
        this.childrenNodeName[quadIndex] = node.nodeName;
    }


    public Point calculateNewCenter(int quadIndex) {
        double x = center.getX();
        double y = center.getY();
        double z = center.getZ();
        float quaterWidth = halfWidth / 2;
        if (quadIndex % 2 >= 1) {
            x += quaterWidth;
        } else {
            x -= quaterWidth;
        }

        if (quadIndex % 4 >= 2) {
            y += quaterWidth;
        } else {
            y -= quaterWidth;
        }


        if (quadIndex >= 4) {
            z += quaterWidth;
        } else {
            z -= quaterWidth;
        }
        return new Point(x, y, z);
    }
}
