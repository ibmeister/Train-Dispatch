package trainDispatch;

import java.util.ArrayList;

public class Controller {
	//This function has four parameters
	// grid : The train grid holding all the edges
	// start : The start location of the Train
	// end : The end location of the Train
	// baseCase : A boolean telling if the base case is used
	//
	//This function is used to compute the shortest path for a train
	//using Dijkstra's Algorithm. The function returns the path of the train.
	//This function also will lock the edges once the path is given back to the 
	//Main class.
	public ArrayList<Edge> routePathForTrain(Grid grid, int start, int end, boolean baseCase, Simulator sim){
		
		int[] prev = new int[14];
		//sorted list of stations to travel to
		PriorityQueue Q =  new PriorityQueue(start);
		//list of visited stations
		ArrayList<Integer> visited = new ArrayList<>();
		
		//add the first station to the visited list
		visited.add(start);
		
		prev[start] = -1;
		
		//While the queue is not empty do the loop
		while(!Q.isEmpty()){
			//take the frist element from the queue
			Station station = Q.dequeue();
			//for each edge that is adjacent calculate a new cost
			for(Edge e : grid.getStationPathsForStation(station.getStation() - 1)){
				//only consider the edge if the track is unlocked
				if(!e.getLocked()){
					Integer curStation = e.getNodeTwo();
					//if we have not visited this station then...
					if(visited.indexOf(curStation) == -1){
						//update the cost for the station and add it to visited
						int newDistance = station.getCost() + e.getCost();
						prev[curStation] = station.getStation();
						Q.queue(curStation, newDistance);
						visited.add(curStation);
					}
				}
			}
			//sort the queue after updating the costs
			Q.sortQueue();
		}
		
		int cur = end;
		ArrayList<Edge> trainPath = new ArrayList<>();
		//traverse backwards to create the path
		while(cur != start){
			int nOne = prev[cur];
			int nTwo = cur;
			//if the last station is valid then...
			if(nOne > 0){
				if(baseCase == false){
					if(nOne == start){
						//only lock the next move in the optimized case
						trainPath.add(grid.setEdgeStateWithStationNumbers(nOne, nTwo, true));
						sim.drawNodes(nOne, nTwo, true);
					} else {
						//don't lock future paths for the optimized case
						trainPath.add(grid.setEdgeStateWithStationNumbers(nOne, nTwo, false));
						sim.drawNodes(nOne, nTwo, false);
					}
				} else {
					//if we are in the base case then lock all paths
					trainPath.add(grid.setEdgeStateWithStationNumbers(nOne, nTwo, true));
					sim.drawNodes(nOne, nTwo, true);
				}
				cur = nOne;
			} else if(nOne == 0){
				//if our path doesn't exist then return an empty path
				trainPath.clear();
				return trainPath;
			}
		}
		return trainPath;
	}
}

