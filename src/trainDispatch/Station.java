package trainDispatch;

public class Station {
	private int station;//station number
	private int cost;//current cost in Dijkstra's Algorithm
	
	public Station(int s, int c){
		this.station = s;
		this.cost = c;
	}
	
	public void setStation(int s){
		station = s;
	}
	
	public void setCost(int c){
		cost = c;
	}
	
	public int getStation(){
		return station;
	}
	
	public int getCost(){
		return cost;
	}
}
