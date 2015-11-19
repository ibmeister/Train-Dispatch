package trainDispatch;

import java.util.ArrayList;

public class Train {

	private int start;//start station
	private int end;//end station
	private int startTime;// spawn time
	private ArrayList<Edge> path;//path assigned to the train
	private int status;//it's status for train queue
	private int curStation;//its last station
	private int moves;//used for train movement
	private double tempMove;//used for train movement
	private int speed;// speed of the train in mph
	private double disMoved;// total distance moved by the train
	private int waitTime;//time train has waited

	Train(int s, int e, int t){
		this.start = s;
		this.end = e;
		this.startTime = t;
		this.status = 0;
		this.moves = 1;
		this.tempMove = 0;
		this.speed = 30;
		this.disMoved = 0;
		this.waitTime = 0;
		this.curStation = start;
	}
	
	
	//This function returns the edge that was last moved by the train
	//if the train is still moving on the edge it returns null
	public Edge moveTrain(){
		
		Edge e = path.get(path.size() - moves);
		tempMove += (speed) * (1.0/3600.0);//move the train
		waitTime = 0;
		//if we have moved the entire dis of the track then return the edge
		if(tempMove >= e.getCost()){
			curStation = e.getNodeTwo();
			disMoved += e.getCost();
			moves ++;
			tempMove = 0;
			
			if(curStation == end){
				status = 2;
			}
			
			return e;
		}
		
		return null;
	}
	
	public void addWait(){
		waitTime += 1;
		if(waitTime > 3600){
			status = 3;
		}
	}
	
	public double getDistanceMoved(){
		return disMoved;
	}
	
	public int getStatus(){
		return status;
	}
	
	public void setStatus(int newStatus){
		status = newStatus;
	}
	
	public ArrayList<Edge> getTrainPath(){
		return path;
	}
	
	public void setTrainPath(ArrayList<Edge> newPath){
		path = newPath;
		moves = 1;
	}
	
	public void setNewStart(int s){
		start = s;
	}

	public int getStart(){
		return start;
	}

	public int getEnd(){
		return end;
	}

	public int getStartTime(){
		return startTime;
	}

}
