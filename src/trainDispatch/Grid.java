package trainDispatch;

import java.util.ArrayList;
import trainDispatch.Edge;

public class Grid {
	
	ArrayList<ArrayList<Edge>> grid = null;//Adjacency List of the edges
	
	public Grid (){
		grid = new ArrayList<>();
	}
	
	//This function has one parameter
	// paths : Adjacency list for one station
	//
	//This function is used to populate the adjacency list from
	//the main function
	public void addStationPaths(ArrayList<Edge> paths){
		grid.add(paths);
	}
	
	//This function has two parameters:
	// edgeID : The identifier for the edge
	// state  : The new locked state for the edge
	//
	//This function calls the setEdgeStateWithStationNumbers()
	//function to set the values of the edge.
	public void setEdgeState(int edgeID, boolean state){
		for(ArrayList<Edge> list : grid){
			for(Edge e : list){
				if(e.getEdgeID() == edgeID){
					//call this function to set the state of the edge
					setEdgeStateWithStationNumbers(e.getNodeOne(), e.getNodeTwo(), state);
					return;
				}
			}
		}
	}
	
	//This function has three parameters:
	// stationOne : The first station of the edge
	// stationTwo : The second station of the edge
	// state : the new lock state for the edge
	// 
	// This function finds the edge that is associated with the station numbers.
	// It then finds the edge that is the reverse of the edge and sets both to
	// the new edge state.
	public Edge setEdgeStateWithStationNumbers(int stationOne, int stationTwo, boolean state){
		Edge target = null;
		for(ArrayList<Edge> list : grid){
			for(Edge e : list){
				if(e.getNodeOne() == stationOne && e.getNodeTwo() == stationTwo){
					e.setLocked(state);
					target = e;
				}
				if(e.getNodeTwo() == stationOne && e.getNodeOne() == stationTwo){
					e.setLocked(state);
				}
			}
		}
		
		return target;
	}
	
	//This function returns the station adjacency for any station
	public ArrayList<Edge> getStationPathsForStation(int station){
		return grid.get(station);
	}
}
