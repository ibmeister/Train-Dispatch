package trainDispatch ;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import trainDispatch.Edge;
import trainDispatch.Train;
import trainDispatch.Controller;
import trainDispatch.Grid;

public class Main {
	
	private static Grid grid = null;
	private static ArrayList<Train> trains = null;
	private static int time = 0;
	private static Simulator sim = null;
	
	public static void main(String[] args) {
		if(args.length < 3){
			System.out.println("Incorrect Number of Arguments!!");
			return;
		}
		//Generate our train sequence based on arguments
		sim = new Simulator();
		new TestCase(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		File file = new File("trainGrid.txt");
		File file2 = new File("TrainSequence.txt");
		Boolean baseCase = false;
		if(Integer.parseInt(args[2]) == 0){
			baseCase = true;
		}
		
		int edgeCount = 0;
		trains = new ArrayList<>();
		grid = new Grid();
		try {
			//read in the grid and create the structure
			Scanner stdIn = new Scanner(file);
			int lastStation = 1;
			ArrayList <Edge> stationPath = new ArrayList<>();
			while (stdIn.hasNextLine()) {
				// run through the rest of the file
				int start = stdIn.nextInt();
				int end = stdIn.nextInt();
				int dis = stdIn.nextInt();
				edgeCount += 1;
				if(lastStation != start){
					grid.addStationPaths(stationPath);
					stationPath = new ArrayList<>();
					stationPath.add(new Edge(start, end, dis, edgeCount));
					lastStation = start;
				} else {
					stationPath.add(new Edge(start, end, dis, edgeCount));
				}
			}
			grid.addStationPaths(stationPath);
			stdIn.close();
			//read in the train sequence and add it to the array
			stdIn = new Scanner(file2);
			while (stdIn.hasNextLine()) {
				// run through the rest of the file
				int start = stdIn.nextInt();
				int end = stdIn.nextInt();
				int time = stdIn.nextInt();
				//time = 0;
				trains.add(new Train(start,end,time));
			}
			grid.addStationPaths(stationPath);
			stdIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//initalize the controller
		Controller controller = new Controller();
		//while there are trains to be scheduled
		while(!trains.isEmpty()){
			ArrayList<Integer> removeTrains = new ArrayList<>();
			//sort the trains before we start the next iteration
			sort(trains);
			//for each train we must perform the appropriate action
			for(Train t: trains){
				//if the train is waiting
				if(t.getStartTime() <= time && (t.getStatus() == 0 || t.getStatus() >= 3)){
					//find the shortest path for the train
					ArrayList<Edge> tempPath = new ArrayList<>(controller.routePathForTrain(grid, t.getStart(), t.getEnd(), baseCase, sim));
					if(!tempPath.isEmpty()){
						//if the path exists then we need to assign the path to the train
						//them we can update the status of the train
						t.setStatus(1);
						t.setTrainPath(tempPath);
					}
				} else if(t.getStartTime() <= time && t.getStatus() == 1){
					//if the train is currently moving on a track,
					//then we need to continue to move it
					Edge e = t.moveTrain();
					//draw the path
					if(!baseCase){
						if(e != null){
							//if we have received a track from the train
							//then we need to free it(optimized case only)
							//we also need to calculate a new path for the train
							grid.setEdgeState(e.getEdgeID(), false);
							sim.drawNodes(e.getNodeOne(), e.getNodeTwo(), false);
							if(e.getNodeTwo() != t.getEnd()){
								ArrayList<Edge> tempPath = new ArrayList<>(controller.routePathForTrain(grid, e.getNodeTwo(), t.getEnd(), baseCase, sim));
								if(!tempPath.isEmpty()){
									//if a path exists,
									//then set the new path of the train
									t.setTrainPath(tempPath);
								} else {
									//if the path does not exist,
									//then we need to tell the train to wait
									//with priority 4
									t.setStatus(4);
									t.setNewStart(e.getNodeTwo());
								}
							}
						}
					}					
				} else if(t.getStartTime() <= time && t.getStatus() == 2){
					//if the train has completed moving then we need to remove it
					if(baseCase){
						//for the base case remove all locked edges
						for(Edge e : t.getTrainPath()){
							grid.setEdgeState(e.getEdgeID(), false);
							sim.drawNodes(e.getNodeOne(), e.getNodeTwo(), false);
						}
					}
					removeTrains.add(trains.indexOf(t));
				}
			}
			
			//remove all trains from the train list
			for(int i = removeTrains.size()-1; i >= 0; i--){
				int ii = removeTrains.get(i);
				trains.remove(ii);
			}
			time++;
		}
		
		System.out.println("WE HAVE COMPLETED THE ROUTE IN " + time + " SECONDS!!!!");
		
	}
	
	public static void sort(ArrayList<Train> trainList){
		int first = 0;
		for(int i = trainList.size() - 1; i>0;i--){
			first = 0;
			for(int j = 0; j <= i; j++){
				if(trainList.get(j).getStatus() > trainList.get(first).getStatus()){
					first = j;
				}
				Train t = trainList.get(first);
				trainList.set(first, trainList.get(i));
				trainList.set(i, t);
			}
		}
	}
}
