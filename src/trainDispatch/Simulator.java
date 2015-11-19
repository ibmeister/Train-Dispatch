package trainDispatch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Simulator {

	public Simulator() {
		// TODO Auto-generated method stub
		StdDraw.setCanvasSize(1350, 600);

		drawPath();

	}

	public static void drawPath() {
		StdDraw.setPenRadius(.01);
		StdDraw.setPenColor(StdDraw.GREEN);
		File file1 = new File("Stations_Path.txt");

		try{
			Scanner stdIn1 = new Scanner(file1);
			while (stdIn1.hasNextDouble()) {
				double x1 = stdIn1.nextDouble();
				double y1 = stdIn1.nextDouble();
				double x2 = stdIn1.nextDouble();
				double y2 = stdIn1.nextDouble();
				StdDraw.line(x1, y1, x2, y2);

			}
			stdIn1.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	void drawNodes(int station_start, int station_end, boolean locked) {
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(.06);
		File file = new File("Stations.txt");
		ArrayList<Double> coordinates= new ArrayList<Double>();
		Double start_x = 0.0;
		Double start_y=0.0;
		Double end_x=0.0;
		Double end_y=0.0;
		try{
			Scanner stdIn = new Scanner(file);
			while (stdIn.hasNextDouble()) {
				double x = stdIn.nextDouble();
				double y = stdIn.nextDouble();
				int station_number = stdIn.nextInt();
				coordinates.add(x);
				coordinates.add(y);
				coordinates.add((double) station_number);
				StdDraw.point(x, y);
				StdDraw.text(x+.02, y+.01, Integer.toString(station_number));
				//StdDraw.text(x+.05, y+.01, Double.toString(y));
				StdDraw.picture(x, y, "city-raiway-station-icon.png");
			}
			stdIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < coordinates.size(); i++) {

			if(coordinates.get(i)==station_start ) {
				start_x = coordinates.get(i-2);
				start_y = coordinates.get(i-1);
			}
			if(coordinates.get(i)==station_end ) {
				end_x = coordinates.get(i-2);
				end_y = coordinates.get(i-1);
			}


		}
		StdDraw.setPenColor(StdDraw.GREEN);
		if(locked){
			StdDraw.setPenColor(StdDraw.RED);
		}
		StdDraw.setPenRadius(.06);
		StdDraw.point(start_x, start_y);
		StdDraw.point(end_x, end_y);
		StdDraw.picture(start_x, start_y, "city-raiway-station-icon.png");
		StdDraw.picture(end_x, end_y, "city-raiway-station-icon.png");
		StdDraw.setPenRadius(0.01);
		StdDraw.line(start_x, start_y, end_x, end_y);
	}
}