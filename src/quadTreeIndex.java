
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class quadTreeIndex implements Serializable {
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

    public List<Point> nearestNeighbor(Point inputPoint) {
    	int range = 1;   	
    	List NN_Result = new ArrayList<Point>();
    	do{
	    	double x_LowBound = inputPoint.getX()-range;
	    	double x_UpperBound = inputPoint.getX()+range;
	    	double y_LowBound = inputPoint.getY()-range;
	    	double y_UpperBound = inputPoint.getY()+range;
	    	double z_LowBound = inputPoint.getZ()-range;
	    	double z_UpperBound = inputPoint.getZ()+range;
	    	Point searchStart = new Point(x_LowBound, y_LowBound, z_LowBound);
	        Point searchEnd = new Point(x_UpperBound, y_UpperBound, z_UpperBound);        
	        searchSpace.search(NN_Result, searchSpace, searchStart, searchEnd);
	        range++;
    	}while(NN_Result.size()==0);
        
    	Point closestPoint = (Point) NN_Result.get(0);
    	double distance = dist(inputPoint,closestPoint);
	List result = new ArrayList<Point>();
    	result.add(closestPoint);
        
        for(int i=0;i<NN_Result.size();i++){
        	double tempDist = dist(inputPoint,(Point) NN_Result.get(i));
        	if(tempDist<distance){
        		distance = tempDist;
        		closestPoint = (Point) NN_Result.get(i);
			result.clear();
			result.add(closestPoint);
        	}else if(tempDist==distance){
        		result.add(NN_Result.get(i));
        	}
        }     
        return result;
    }
    
    public double dist(Point pointA,Point pointB){
    	
    	double pointA_x = pointA.getX();
    	double pointA_y = pointA.getY();
    	double pointA_z = pointA.getZ();
    	double pointB_x = pointA.getX();
    	double pointB_y = pointA.getY();
    	double pointB_z = pointA.getZ();
    	double diff_x = pointA_x - pointB_x;
    	double diff_y = pointA_y - pointB_y;
    	double diff_z = pointA_z - pointB_z;
    	double dist = Math.sqrt(Math.pow(diff_x, 2)+Math.pow(diff_y, 2)+Math.pow(diff_z, 2));
    	
    	return dist;
    }
}
