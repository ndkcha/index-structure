package quadTreeIndex;


import java.util.ArrayList;
import java.util.List;

import quadTreeIndex.Node;
import quadTreeIndex.Point;

public class quadTreeIndex {
	Node searchSpace;
	int nodeCount=1;
	
    public quadTreeIndex(Node searchSpace) {
        this.searchSpace = searchSpace;
    }

    public void addPoint(Point point) {
        addPoint(point, searchSpace);
    }

    @Override
    public String toString() {
        return "QuadTree3D{" +
                "searchSpace=" + searchSpace +
                '}'+"\ntotal nodes: "+nodeCount;
    }


    public void addPoint(Point point, Node actualNode) {
        int quadIndex = actualNode.calculateQuadrantIndex(point, actualNode.getCenter());
        Node nodeForPoint = actualNode.children[quadIndex];
        if (nodeForPoint == null) {
            Node newNode = new Node(actualNode.calculateNewCenter(quadIndex), actualNode.getQuadrantWidth() / 2);
            newNode.associateObject(point);
            // sets appropriate child to new node and changes actualNode.leaf = false
            actualNode.setChild(quadIndex, newNode);
            newNode.levelNum = actualNode.levelNum+1;
            newNode.innerIndex = quadIndex;
            newNode.predecessorIndex = actualNode.innerIndex;
            nodeCount++;
        } else {
            // We need to add existing point to deeper level
        	nodeForPoint.levelNum = actualNode.levelNum+1;
        	nodeForPoint.innerIndex = quadIndex;
        	nodeForPoint.predecessorIndex = actualNode.innerIndex;
            Point toSplit = nodeForPoint.getPoint();
            if (toSplit!=null) {
                nodeForPoint.associateObject(null);
                addPoint(toSplit, nodeForPoint);
            }
            // We will also add a new point
            addPoint(point, nodeForPoint);

        }
    }

    public List<Point> search(Point leftBottomBack, Point rightTopFront) {
        if (
                leftBottomBack.getX() > rightTopFront.getX() ||
                leftBottomBack.getY() > rightTopFront.getY() ||
                leftBottomBack.getZ() > rightTopFront.getZ()
                ) {
            throw new IllegalArgumentException("Invalid search region, first search argument must be point at left bottom back corner of 3D space, second point at " +
                    "right top front.\n Points are: \n" + leftBottomBack + "\n" + rightTopFront);
        }
        List result = new ArrayList<Point>();
        searchSpace.search(result, searchSpace, leftBottomBack, rightTopFront);
        return result;
    }


    /**
     * Traverses whole quad tree, allowing visitor to perform some operations on the node
     * @param visitor
     */
    /*
    public void visit(Visitor visitor) {
        visit(visitor, searchSpace);
    }

    private void visit(Visitor visitor, Node node) {
        visitor.visit(node);
        if (!node.isLeaf()) {
            for (int quadIndex = 0; quadIndex < 8; quadIndex++) {
                Node quadrant = node.getChild(quadIndex);
                if (quadrant!=null) {
                    visit(visitor, quadrant);
                }
            }
        }
    }*/
}
