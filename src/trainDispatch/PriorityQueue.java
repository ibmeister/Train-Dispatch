package trainDispatch;

import java.util.ArrayList;

public class PriorityQueue {
	
	private ArrayList<Station> myQueue = null;//Array list structure
	
	//This function has one parameter:
	// start : the starting station of the train
	//
	//This function creates a queue with one Station(start)
	public PriorityQueue(int start){
		myQueue = new ArrayList<>();
		Station s = new Station(start, 0);
		myQueue.add(s);
	}
	
	//This function has two parameters:
	// st : the station number to add
	// cost : the cost of the station
	//
	// This function adds the new Station to the end of the queue
	public void queue(int st, int cost){
		Station s = new Station(st, cost);
		myQueue.add(s);
	}
	
	//This function returns the Station that is next in the queue
	//
	// This function gets the first index in the queue then shifts
	// all elements after it up one space. Then deletes the last
	// element in the queue.
	public Station dequeue(){
		Station val = myQueue.get(0);
		
		for(int i = 0; i<myQueue.size()-1;i++){
			myQueue.set(i, myQueue.get(i+1));
		}
		
		myQueue.remove(myQueue.size()-1);
		
		return val;
	}
	
	//This function uses selection sort to sort the queue
	public void sortQueue(){
		int first = 0;
		for(int i = myQueue.size() - 1; i>0;i--){
			first = 0;
			for(int j = 0; j <= i; j++){
				if(myQueue.get(j).getCost() > myQueue.get(first).getCost()){
					first = j;
				}
				Station s = new Station(myQueue.get(first).getStation(), myQueue.get(first).getCost());
				myQueue.set(first, myQueue.get(i));
				myQueue.set(i, s);
			}
		}
	}
	
	public boolean isEmpty(){
		return myQueue.isEmpty();
	}

}
