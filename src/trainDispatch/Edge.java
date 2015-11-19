package trainDispatch;
public class Edge {

	private int nodeOne;//start station
	private int nodeTwo;//end station
	private int edgeID;//Reference to the edge in the grid
	private boolean locked;//its locked status
	private int cost;//distance in miles of the track

	public Edge(int start, int end, int dis, int id){
		this.nodeOne = start;
		this.nodeTwo = end;
		this.locked = false;
		this.cost = dis;
		this.edgeID = id;
	}
	
	public int getEdgeID(){
		return edgeID;
	}

	public int getNodeOne(){
		return nodeOne;
	}

	public int getNodeTwo(){
		return nodeTwo;
	}
	
	public boolean getLocked(){
		return locked;
	}
	
	public void setLocked(boolean b){
		locked = b;
	}
	
	public int getCost(){
		return cost;
	}
}
